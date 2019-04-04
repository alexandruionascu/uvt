import time
from spade.agent import Agent
from spade.message import Message
from spade.behaviour import FSMBehaviour, OneShotBehaviour, State
import random

SEND_RUMOR_STATE = "SEND_RANDOM_RUMOR_TO_RANDOM_GUEST"
INTRODUCE_GUESTS_STATE = "INTRODUCE_TWO_RANDOM_GUESTS"
CHECK_FINISH_STATE = "CHECK_ALL_GUESTS_KNOW_THE_RUMOR"

STATE_THREE = "STATE_THREE"

RUMORS = [
    "The night",
    "is dark",
    "and",
    "full of",
    "terrors"
]

MAX_GUESTS = 10

class PartyHostBehaviour(FSMBehaviour):
    async def on_start(self):
        print(f"Party started in the state: {self.current_state}")

    async def on_end(self):
        print(f"Party ended in the state: {self.current_state}")
        await self.agent.stop()

class SendRandomRumor(State):
    async def run(self):
        for _ in range(len(RUMORS)):
            guest_idx = random.randint(1, MAX_GUESTS + 1)
            print("RANDOM GUEST: ", guest_idx)
            msg = Message(to="partyguest{0}@localhost".format(guest_idx))
            msg.body = random.choice(RUMORS)
            await self.send(msg)
        
        self.set_next_state(INTRODUCE_GUESTS_STATE)


class IntroduceRandomGuests(State):
    def __init__(self, agent):
        self.agent = agent

    async def run(self):
        # pick 2 random guests
        guest_idx_1, guest_idx_2 = random.sample(range(0, MAX_GUESTS), 2)

        agent_1 = self.agent.guests[guest_idx_1]
        agent_2 = self.agent.guests[guest_idx_2]

        await agent_1.introduce_to(agent_2)
        await agent_2.introduce_to(agent_1)

        self.set_next_state(CHECK_FINISH_STATE)


class CheckFinish(State):

    def __init__(self, agent):
        self.agent = agent

    async def run(self):
        for guest in self.agent.guests:
            if not guest.is_aware_of_rumor():
                self.set_next_state(INTRODUCE_GUESTS_STATE)

        # otherwise the party stops
        # no other state is being set
        self.agent.stop()
        


class PartyGuest(Agent):
    known_rumors = set() # empty set

    def is_aware_of_rumor(self):
        return len(self.known_rumors) > 0 # or == len(rumors)

    async def introduce_to(self, agent):
        # send a random rumor
        if self.is_aware_of_rumor():
            print("Sending to {0}".format(agent.jid))
            msg = Message(to=str(agent.jid)) 
            msg.body = random.choice(self.known_rumors)                   
            await self.send(msg)
    
    class RecvBehav(OneShotBehaviour):
        def __init__(self, agent):
            self.agent = agent

        async def run(self):   
            msg = await self.receive(timeout=10) # seconds
            rumor = str(msg.body)
            # store this rumor in the set
            self.agent.known_rumos.add(rumor)

    def setup(self):
        self.add_behaviour(self.RecvBehav(self))

class PartyHost(Agent):

    guests = [] # agents
    
    def setup(self):
        # finite state machine
        fsm = PartyHostBehaviour()
        fsm.add_state(name=SEND_RUMOR_STATE, state=SendRandomRumor(), initial=True)
        fsm.add_state(name=INTRODUCE_GUESTS_STATE, state=IntroduceRandomGuests(self))
        fsm.add_state(name=CHECK_FINISH_STATE, state=CheckFinish(self))
        # spread initial rumors
        fsm.add_transition(source=SEND_RUMOR_STATE, dest=INTRODUCE_GUESTS_STATE)
        # check if party is over
        fsm.add_transition(source=INTRODUCE_GUESTS_STATE, dest=CHECK_FINISH_STATE)
        # if party isn't over -> introduce guests again
        fsm.add_transition(source=CHECK_FINISH_STATE, dest=INTRODUCE_GUESTS_STATE)

        # create the party guests
        for i in range(MAX_GUESTS):
            party_agent = PartyGuest("partyguest{0}@localhost".format(i + 1), "")
            party_agent.start(auto_register=False)
            self.guests.append(party_agent)

        self.add_behaviour(fsm)

if __name__ == "__main__":
    party_host = PartyHost("partyhost@localhost", "")
    party_host.start(auto_register=True)

    while party_host.is_alive():
        try:
            time.sleep(1)
        except KeyboardInterrupt:
            party_host.stop()
            break
