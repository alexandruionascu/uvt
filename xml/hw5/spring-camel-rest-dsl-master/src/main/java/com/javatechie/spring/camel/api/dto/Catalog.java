package com.javatechie.spring.camel.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Catalog {
    private List<Article> articles;
    private List<Author> authors;
    private List<Category> categories;
}