package com.javatechie.spring.camel.api.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Author {
    @XStreamAsAttribute
    private String id;

    private String name;

    private List<Affiliation> affiliations;
    
}