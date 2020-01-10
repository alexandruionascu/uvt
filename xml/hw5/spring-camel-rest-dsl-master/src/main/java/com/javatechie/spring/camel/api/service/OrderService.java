package com.javatechie.spring.camel.api.service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.javatechie.spring.camel.api.dto.Order;

@Service
public class OrderService {

	private List<Order> list = new ArrayList<>();

	@PostConstruct
	public void initDB() {
		list.add(new Order(23, "Lol", 1300));
		list.add(new Order(67, "Mobile", 5000));
		list.add(new Order(89, "Book", 400));
		list.add(new Order(45, "AC", 15000));
		list.add(new Order(67, "Shoes", 4000));
	}

	public String getXML() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document xml = db.parse("data.xml");
			Document xslt = db.parse("transform3.xml");

			xml.appendChild(xml.createElementNS(null, "root"));
			Document result = transformXML(xml, xslt);
			StringWriter writer = new StringWriter();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			// transform document to string
			transformer.transform(new DOMSource(result), new StreamResult(writer));
			String xmlString = writer.getBuffer().toString();
			return xmlString;
		} catch (Exception e) {
			return "XML Transform failed";
		}
	}

	public Order addOrder(Order order) {
		list.add(order);
		return order;
	}

	public List<Order> getOrders() {
		return list;
	}

	public static Document transformXML(Document xml, Document xslt) throws Exception {

		Source xmlSource = new DOMSource(xml);
		Source xsltSource = new DOMSource(xslt);
		DOMResult result = new DOMResult();

		// the factory pattern supports different XSLT processors
		TransformerFactory transFact = TransformerFactory.newInstance();
		Transformer trans = transFact.newTransformer(xsltSource);

		trans.transform(xmlSource, result);

		Document resultDoc = (Document) result.getNode();

		return resultDoc;
	}

}
