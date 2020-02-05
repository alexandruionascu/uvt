package com.javatechie.spring.camel.api.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javatechie.spring.camel.api.dto.Song;
import com.javatechie.spring.camel.api.service.MusicLibraryService;

@Component
public class UpdateSongProcessor implements Processor {
	
	@Autowired
	private MusicLibraryService service;

	@Override
	public void process(Exchange exchange) throws Exception {
		service.updateSong(exchange.getIn().getBody(Song.class));
	}
}
