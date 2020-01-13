package com.javatechie.spring.camel.api.resource;

import org.apache.camel.BeanInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.javatechie.spring.camel.api.dto.Article;
import com.javatechie.spring.camel.api.processor.AddArticleProcessor;
import com.javatechie.spring.camel.api.processor.RemoveArticleProcessor;
import com.javatechie.spring.camel.api.processor.UpdateArticleProcessor;
import com.javatechie.spring.camel.api.service.UniversityService;

@Component
public class ApplicationResource extends RouteBuilder {

	@Autowired
	private UniversityService service;

	@BeanInject
	private UpdateArticleProcessor updateArticleProcessor;

	@BeanInject
	private RemoveArticleProcessor removeArticleProcessor;

	@BeanInject
	private AddArticleProcessor addArticleProcessor;

	@Override
	public void configure() throws Exception {
		restConfiguration().component("servlet").port(9090).host("localhost").bindingMode(RestBindingMode.auto);

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

		rest().post("/article").consumes(MediaType.APPLICATION_JSON_VALUE).type(Article.class).outType(Article.class)
				.route().process(addArticleProcessor).endRest();

		rest().put("/article").consumes(MediaType.APPLICATION_JSON_VALUE).type(Article.class).outType(Article.class)
				.route().process(updateArticleProcessor).endRest();

		rest().delete("/article").consumes(MediaType.APPLICATION_JSON_VALUE).type(Article.class).outType(Article.class)
				.route().process(removeArticleProcessor).endRest();
	}

}
