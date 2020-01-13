package com.javatechie.spring.camel.api.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// @Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Catalog {
    @XStreamAlias("articles")
    public List<Article> articles;
    @XStreamAlias("authors")
    public List<Author> authors;
    @XStreamAlias("categories")
    public List<Category> categories;
}