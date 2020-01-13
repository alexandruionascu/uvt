package com.javatechie.spring.camel.api.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Author {
    @XStreamAsAttribute
    @XStreamAlias("id")
    public String id;

    @XStreamAlias("name")
    public String name;

    @XStreamAlias("affiliations")
    public List<Affiliation> affiliations;
}