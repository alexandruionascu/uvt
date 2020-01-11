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
public class Category {

    @XStreamAsAttribute
    private String id;

    @XStreamAsAttribute
    private String type;

    @XStreamAsAttribute
    private String categoryId;

    private String name;

}
