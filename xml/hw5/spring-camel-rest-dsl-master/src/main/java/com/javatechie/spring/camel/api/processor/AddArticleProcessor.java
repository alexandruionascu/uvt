package com.javatechie.spring.camel.api.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javatechie.spring.camel.api.dto.Article;
import com.javatechie.spring.camel.api.service.UniversityService;

@Component
public class AddArticleProcessor implements Processor {
	
	@Autowired
	private UniversityService service;

	@Override
	public void process(Exchange exchange) throws Exception {
		service.addArticle(exchange.getIn().getBody(Article.class));
	}
}
