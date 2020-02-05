import React from "react";
import {
  Box,
  Grommet,
  List,
  Heading,
  Form,
  FormField,
  Button,
  Tab,
  Tabs,
  Text
} from "grommet";

var convert = require("xml-js");

const AppBar = props => (
  <Box
    tag="header"
    direction="row"
    align="center"
    justify="between"
    background="brand"
    pad={{ left: "medium", right: "small", vertical: "small" }}
    elevation="medium"
    style={{ zIndex: "1" }}
    {...props}
  />
);

const theme = {
  global: {
    colors: {
      brand: "#228BE6"
    },
    font: {
      family: "Roboto",
      size: "18px",
      height: "20px"
    },
    animation: {
      duration: "1s",
      jiggle: { duration: "0.1s" }
    }
  }
};

const getCatalog = () => {
  return fetch("http://localhost:9090/xml-catalog", { method: "GET" })
    .then(response => response.text())
    .then(xmlStr => {
      return convert.xml2json(xmlStr, { compact: true });
    })
    .then(jsonStr => JSON.parse(jsonStr));
};

const postSong = songData => {
  return fetch("http://localhost:9090/song", {
    method: "POST",
    body: JSON.stringify(songData)
  });
};

const idsToAuthors = (csv, authors) => {
  const ids = csv.split(",").map(x => x.trim());
  let aut = [];
  for (let id of ids) {
    for (let author of authors) {
      if (id === author.id._text) {
        aut.push(author.name._text);
      }
    }
  }

  return aut.join(", ");
};

const App = () => {
  const [catalog, setCatalog] = React.useState(null);
  const songs = catalog ? catalog.songs.song : [];
  const genres = catalog ? catalog.genres.genre : [];
  const authors = catalog ? catalog.authors.author : [];

  console.log(songs);

  React.useEffect(() => {
    if (!catalog) {
      getCatalog().then(data => setCatalog(data.catalog));
    }
  });

  return (
    <Grommet theme={theme} full>
      <Box fill>
        <AppBar>Kings of Music</AppBar>

        <Box margin="medium" pad="large" flex align="center" justify="center">
          <Tabs>
            <Tab title="Songs">
              <Box flex align="center" justify="center">
                <Heading>List of Songs</Heading>
                <List
                  primaryKey={(song, i) => (
                    <Text key={i}>
                      #{song.id} {idsToAuthors(song.authorids, authors)} - {" "}
                      {song.title}
                    </Text>
                  )}
                  secondaryKey="date"
                  data={songs.map(x => ({
                    title: x.title._text,
                    date: x.publishdate._text,
                    id: x.id._text,
                    authorids: x.authorids._text
                  }))}
                />
              </Box>
            </Tab>
            <Tab title="Authors">
              <Box flex align="center" justify="center">
                <Heading>List of Authors</Heading>
                <List
                  primaryKey={author => (
                    <Text>
                      #{author.id} {author.name}{" "}
                    </Text>
                  )}
                  data={authors.map(x => ({
                    id: x.id._text,
                    name: x.name._text
                  }))}
                />
              </Box>
            </Tab>
            <Tab title="Genres">
              <Box flex align="center" justify="center">
                <Heading>List of Genres</Heading>
                <List
                  primaryKey="name"
                  secondaryKey="id"
                  data={genres.map(x => ({
                    id: x.id._text,
                    name: x.name._text
                  }))}
                ></List>
              </Box>
            </Tab>
            <Tab title="Add Song">
              <Form onSubmit={event => postSong(event.value)} margin="medium">
                <Heading>Add Song</Heading>
                <FormField name="title" label="title" required />
                <FormField name="id" label="Id" required />
                <FormField name="publishdate" label="Publish Date" required />
                <FormField name="authors" label="Author Ids in CSV" required />
                <FormField name="genreid" label="Genre Id" required />
                <Button type="submit" primary label="Submit" />
              </Form>
            </Tab>
          </Tabs>
        </Box>
      </Box>
    </Grommet>
  );
};

export default App;
