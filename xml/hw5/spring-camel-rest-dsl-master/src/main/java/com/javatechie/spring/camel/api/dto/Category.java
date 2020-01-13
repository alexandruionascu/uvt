package com.javatechie.spring.camel.api.dto;



import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {

    @XStreamAsAttribute
    public String id;

    @XStreamAsAttribute
    public String type;

    @XStreamAsAttribute
    public String categoryId;

    public String name;

}
