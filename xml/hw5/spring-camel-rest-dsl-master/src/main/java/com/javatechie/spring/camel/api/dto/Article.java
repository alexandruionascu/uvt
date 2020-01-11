package com.javatechie.spring.camel.api.dto;



import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Article {
    @XStreamAsAttribute
    private String id;

    @XStreamAsAttribute
    private String authorId;

    @XStreamAsAttribute
    private String categoryId;

    private String faculty;

    private String title;
    
    private String publish_date;

    private String isbn;
}
