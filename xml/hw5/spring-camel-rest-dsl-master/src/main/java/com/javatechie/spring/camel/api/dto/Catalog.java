package com.javatechie.spring.camel.api.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Catalog {
    @XStreamAlias("articles")
    private List<Article> articles;
    @XStreamAlias("authors")
    private List<Author> authors;
    @XStreamAlias("categories")
    private List<Category> categories;
}