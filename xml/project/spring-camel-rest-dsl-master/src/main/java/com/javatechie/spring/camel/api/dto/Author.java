package com.javatechie.spring.camel.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Author {
    public String id;
    public String name;
}