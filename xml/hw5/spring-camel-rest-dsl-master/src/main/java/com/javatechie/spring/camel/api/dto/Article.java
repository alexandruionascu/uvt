package com.javatechie.spring.camel.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Article {
    private String faculty;
    private String title;
    private String publish_date;
    private String isbn;
}
