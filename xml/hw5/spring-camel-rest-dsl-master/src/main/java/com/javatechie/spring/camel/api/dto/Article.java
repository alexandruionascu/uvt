package com.javatechie.spring.camel.api.dto;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement(name = "Article")
public class Article {
    @XmlAttribute(name="id")
    private String id;
    
    @XmlAttribute(name="authorId")
    private String authorId;

    @XmlAttribute(name="categoryId")
    private String categoryId;

    @XmlElement(name = "faculty") 
    private String faculty;

    @XmlElement(name="title")
    private String title;

    @XmlElement(name="publish_date")
    private String publishDate;

    @XmlElement(name="isbn")
    private String isbn;
}
