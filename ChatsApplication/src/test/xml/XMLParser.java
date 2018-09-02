package test.xml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
	
	private Document document;
	private String fileName;
	
	public XMLParser (String file){
		this.fileName=file;
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = builder.parse(fileName);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} 
		
	}
	
	public void addItem(String rootName, String title, String content){
		boolean success = addChildNode(rootName, title, content);
		if (success){
			writeDocumentToFile();
		}
	}
	
	public void addItem(Node node, String title, String content){
		boolean success = addChildNode(node, title, content);
		if (success){
			writeDocumentToFile();
		}
	}
	
	
	private void writeDocumentToFile() {
		try(FileOutputStream stream = new FileOutputStream(fileName)) {
			Transformer tr = TransformerFactory.newInstance().newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(stream);
			tr.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private boolean addChildNode(String rootName, String title, String content){
		Node root = findElement(rootName);
		if (root == null){
			return false;
		}
		Node node = document.createElement(title);
		node.setTextContent(content);
		root.appendChild(node);
		return true;
	}
	
	private boolean addChildNode(Node root, String title, String content){
		if (root == null){
			return false;
		}
		Node node = document.createElement(title);
		node.setTextContent(content);
		root.appendChild(node);
		return true;
	}


	private Node findElement(String tagName) {
		Node node = null;
		NodeList nodes = document.getElementsByTagName(tagName);
		if (nodes != null){
			node = nodes.item(0);
		}
		return node;
	}

}
