package com.javatechie.spring.camel.api.processor;

import com.javatechie.spring.camel.api.dto.Song;
import com.javatechie.spring.camel.api.service.MusicLibraryService;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AddSongProcessor implements Processor {
	
	@Autowired
	private MusicLibraryService service;

	@Override
	public void process(Exchange exchange) throws Exception {
		service.addSong(exchange.getIn().getBody(Song.class));
	}
}
