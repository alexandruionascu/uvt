package com.javatechie.spring.camel.api.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
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

import com.javatechie.spring.camel.api.dto.Author;
import com.javatechie.spring.camel.api.dto.Catalog;
import com.javatechie.spring.camel.api.dto.Genre;
import com.javatechie.spring.camel.api.dto.Song;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service
public class MusicLibraryService {

	final static String DATA_PATH = "data_service.xml";
	private XStream xStream;

	private static String inputStreamToString(InputStream is) throws Exception {
		StringBuilder sb = new StringBuilder();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		return sb.toString();
	}

	private Catalog readCatalog(String xmlFilePath) throws Exception {
		File file = new File(xmlFilePath);
		String xml = inputStreamToString(new FileInputStream(file));

		Catalog catalog = (Catalog) xStream.fromXML(xml);
		return catalog;
	}

	@PostConstruct
	public void initDB() {
		xStream = new XStream(new DomDriver("UTF-8"));
		xStream.alias("catalog", Catalog.class);
		xStream.alias("song", Song.class);
		xStream.alias("songs", List.class);
		xStream.alias("author", Author.class);
		xStream.alias("authors", List.class);
		xStream.alias("genre", Genre.class);
		xStream.alias("genres", List.class);
		xStream.alias("authors", List.class);
	}

	public String getCatalog() {
		try {
			Catalog catalog = readCatalog(DATA_PATH);
			return xStream.toXML(catalog);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
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
	public Song addSong(Song article) {
		try {
			Catalog catalog = readCatalog(DATA_PATH);
			catalog.songs.add(article);
			String xml = xStream.toXML(catalog);
			BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_PATH));
			writer.write(xml);
			writer.close();
			return article;
		} catch (Exception ex) {
			return null;
		}
	}

	// DELETE
	public Song removeSong(Song song) {
		Song removed = null;
		try {
			Catalog catalog = readCatalog(DATA_PATH);
			System.out.println(xStream.toXML(catalog));
			System.out.println(xStream.toXML(song));
			int idx = -1;
			for (int i = 0; i < catalog.songs.size(); i++) {
				
				if (catalog.songs.get(i).id.equals(song.id)) {
					idx = i;
				}
			}
			removed = idx != -1 ? catalog.songs.get(idx) : null;
			if (idx != -1) {
				catalog.songs.remove(idx);
			}
			// catalog.getArticles().remove(catalog.getArticles().size() - 1);
			String xml = xStream.toXML(catalog);
			BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_PATH));
			writer.write(xml);
			writer.close();
			return removed;
		} catch (Exception ex) {
			// nothing
			return removed;
		}
	}


	// PUT
	public Song updateSong(Song song) {
		Song updated = null;
		try {
			Catalog catalog = readCatalog(DATA_PATH);
			System.out.println(xStream.toXML(catalog));
			System.out.println(xStream.toXML(song));
			int idx = -1;
			for (int i = 0; i < catalog.songs.size(); i++) {

				if (catalog.songs.get(i).id.equals(song.id)) {
					idx = i;
				}
			}
			updated = idx != -1 ? catalog.songs.get(idx) : null;
			if (idx != -1) {
				catalog.songs.set(idx, song);
			}
			String xml = xStream.toXML(catalog);
			BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_PATH));
			writer.write(xml);
			writer.close();
			return updated;
		} catch (Exception ex) {
			// nothing
			return updated;
		}
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
