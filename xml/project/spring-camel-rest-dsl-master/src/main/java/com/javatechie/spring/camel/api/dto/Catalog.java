package com.javatechie.spring.camel.api.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.javatechie.spring.camel.api.dto.Song;
// @Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Catalog {
    @XStreamAlias("songs")
    public List<Song> songs;
    @XStreamAlias("authors")
    public List<Author> authors;
    @XStreamAlias("genres")
    public List<Genre> genres;
}