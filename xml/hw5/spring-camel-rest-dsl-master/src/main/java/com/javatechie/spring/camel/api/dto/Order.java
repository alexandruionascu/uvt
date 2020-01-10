package com.javatechie.spring.camel.api.dto;

import javax.xml.bind.annotation.XmlAccessorType;
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
@XmlRootElement(name = "root")
public class Order {
	@XmlElement(name = "id")
	private int id;
	@XmlElement(name = "name")
	private String name;
	@XmlElement(name = "price")
	private double price;

}
