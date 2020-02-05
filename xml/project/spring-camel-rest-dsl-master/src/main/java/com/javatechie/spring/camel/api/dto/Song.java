package com.javatechie.spring.camel.api.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Song {

    @XStreamAlias("authors")
    public String authorids;

    public String id;

    @XStreamAlias("genreid")
    public String genreid;

    public String title;
    
    public String publishdate;
}
