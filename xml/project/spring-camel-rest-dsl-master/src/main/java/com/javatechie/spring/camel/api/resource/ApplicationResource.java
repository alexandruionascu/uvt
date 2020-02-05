package com.javatechie.spring.camel.api.resource;

import com.javatechie.spring.camel.api.dto.Song;
import com.javatechie.spring.camel.api.processor.AddSongProcessor;
import com.javatechie.spring.camel.api.processor.RemoveSongProcessor;
import com.javatechie.spring.camel.api.processor.UpdateSongProcessor;
import com.javatechie.spring.camel.api.service.MusicLibraryService;

import org.apache.camel.BeanInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ApplicationResource extends RouteBuilder {

	@Autowired
	private MusicLibraryService service;

	@BeanInject
	private UpdateSongProcessor updateSongProcessor;

	@BeanInject
	private RemoveSongProcessor removeSongProcessor;

	@BeanInject
	private AddSongProcessor addSongProcessor;

	@Override
	public void configure() throws Exception {
		restConfiguration().component("servlet").port(9090)
        .enableCORS(true) // <-- Important
        .corsAllowCredentials(true) // <-- Important
        .corsHeaderProperty("Access-Control-Allow-Origin","*")
		.corsHeaderProperty("Access-Control-Allow-Headers","Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization")
		.host("localhost").bindingMode(RestBindingMode.auto);

		rest().get("/all").produces(MediaType.APPLICATION_XML_VALUE).route().setBody(() -> service.listAll()).endRest();

		rest().get("/xml-catalog").produces(MediaType.APPLICATION_XML_VALUE).route().setBody(() -> service.getCatalog())
				.endRest();
		rest().get("/xml-articles").produces(MediaType.APPLICATION_XML_VALUE).route()
				.setBody(() -> service.getCatalog()).endRest();

		rest().get("/articles").produces(MediaType.APPLICATION_XML_VALUE).route().setBody(() -> service.listArticles())
				.endRest();

		rest().get("/authors").produces(MediaType.APPLICATION_XML_VALUE).route().setBody(() -> service.listAuthors())
				.endRest();

		rest().get("/ids").produces(MediaType.APPLICATION_XML_VALUE).route().setBody(() -> service.listIds()).endRest();

		rest().get("/categories").produces(MediaType.APPLICATION_XML_VALUE).route()
				.setBody(() -> service.listCategories()).endRest();

		rest().get("/catalog").produces(MediaType.ALL_VALUE).route().setBody(() -> service.getCatalog()).endRest();

		rest().post("/song").consumes(MediaType.APPLICATION_JSON_VALUE).type(Song.class).outType(Song.class)
				.route().process(addSongProcessor).setHeader("Origin",constant("http://localhost:3000")).endRest();

		rest().put("/song").consumes(MediaType.APPLICATION_JSON_VALUE).type(Song.class).outType(Song.class)
				.route().process(updateSongProcessor).endRest();

		rest().delete("/song").consumes(MediaType.APPLICATION_JSON_VALUE).type(Song.class).outType(Song.class)
				.route().process(removeSongProcessor).endRest();
	}

}
