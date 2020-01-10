package com.javatechie.spring.camel.api.service;

import java.io.StringWriter;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.javatechie.spring.camel.api.dto.Article;
import com.javatechie.spring.camel.api.dto.Catalog;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;


@Service
public class UniversityService {

    final static String DATA_PATH = "data.xml";
    private Catalog catalog;


	@PostConstruct
	public void initDB() {
		// pass
    }

    // GET
    
    public String listAll() {
        return applyTransform(DATA_PATH, "transform1.xml");
    }

    public String listArticles() {
        return applyTransform(DATA_PATH, "transform2.xml");
    }

    public String listAuthors() {
        return applyTransform(DATA_PATH, "transform3.xml");
    }

    public String listIds() {
        return applyTransform(DATA_PATH, "transform4.xml");
    }

    public String listCategories() {
        return applyTransform(DATA_PATH, "transform5.xml");
    }

    // POST
    public Article addArticle(Article article) {
        return article;
    }

	private static String applyTransform(String dataPth, String transformPath) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document xml = db.parse(dataPth);
			Document xslt = db.parse(transformPath);

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

	private static Document transformXML(Document xml, Document xslt) throws Exception {

		Source xmlSource = new DOMSource(xml);
		Source xsltSource = new DOMSource(xslt);
        DOMResult result = new DOMResult();
        
		TransformerFactory transFact = TransformerFactory.newInstance();
		Transformer trans = transFact.newTransformer(xsltSource);

		trans.transform(xmlSource, result);

		Document resultDoc = (Document) result.getNode();

		return resultDoc;
	}

}
