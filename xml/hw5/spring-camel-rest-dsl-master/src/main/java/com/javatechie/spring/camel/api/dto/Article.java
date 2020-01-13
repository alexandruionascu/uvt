package com.javatechie.spring.camel.api.dto;



import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Article {
    @XStreamAsAttribute
    public String id;

    @XStreamAsAttribute
    public String authorId;

    @XStreamAsAttribute
    public String categoryId;

    public String faculty;

    public String title;
    
    public String publish_date;

    public String isbn;
}
