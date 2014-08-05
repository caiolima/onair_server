package com.five.onair.server.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.five.onair.server.persistence.model.Application;
import com.five.onair.server.utils.Constants;

public class AppListXMLManager {

	public static Vector<Application> loadInstalledApplications() {
		Vector<Application> applications = new Vector<Application>();
		try {
			File appFile = new File(Constants.FILE_NAME);

			if (appFile.exists()) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(appFile);
				doc.getDocumentElement().normalize();

				NodeList nodes = doc
						.getElementsByTagName(Constants.XML_APPLICATION_ELEMENT);
				for (int i = 0; i < nodes.getLength(); i++) {
					Node node = nodes.item(i);

					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;
						Application application = new Application();

						application.setName(getValue(
								Constants.XML_APPLICATION_NAME, element));
						application.setDescription(getValue(
								Constants.XML_APPLICATION_DESC, element));
						application.setUrl(getValue(
								Constants.XML_APPLICATION_URL, element));
						application.setInstalledLocation(getValue(
								Constants.XML_APPLICATION_INTALLED_LOCATION, element));
						application.setPort(getValue(
								Constants.XML_APPLICATION_PORT, element));
						application.setProtocol(getValue(
								Constants.XML_APPLICATION_PROTOCOL, element));

						applications.add(application);
					}
				}

			}
		} catch (Exception e) {
		}
		
		return applications;

	}

	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0)
				.getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

	public static boolean saveApplications(Vector<Application> applications) {
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(Constants.XML_ROOT_ELEMENT);
			doc.appendChild(rootElement);

			for (Application app : applications) {
				Element application = doc
						.createElement(Constants.XML_APPLICATION_ELEMENT);
				rootElement.appendChild(application);

				// name elements
				Element name = doc
						.createElement(Constants.XML_APPLICATION_NAME);
				name.appendChild(doc.createTextNode(app.getName()));
				application.appendChild(name);

				// description element
				Element description = doc
						.createElement(Constants.XML_APPLICATION_DESC);
				description
						.appendChild(doc.createTextNode(app.getDescription()));
				application.appendChild(description);

				// installed-location element
				Element location = doc
						.createElement(Constants.XML_APPLICATION_INTALLED_LOCATION);
				location.appendChild(doc.createTextNode(app.getInstalledLocation()));
				application.appendChild(location);

				// url element
				Element url = doc.createElement(Constants.XML_APPLICATION_URL);
				url.appendChild(doc.createTextNode(app.getUrl()));
				application.appendChild(url);
				
				// url element
				Element port = doc.createElement(Constants.XML_APPLICATION_PORT);
				port.appendChild(doc.createTextNode(app.getPort()));
				application.appendChild(port);
				
				// url element
				Element protocol = doc.createElement(Constants.XML_APPLICATION_PROTOCOL);
				protocol.appendChild(doc.createTextNode(app.getProtocol()));
				application.appendChild(protocol);

			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			File outFile = new File(Constants.FILE_NAME);
			if (outFile.exists())
				outFile.delete();

			StreamResult result = new StreamResult(outFile);

			transformer.transform(source, result);

			return true;

		} catch (ParserConfigurationException pce) {

		} catch (TransformerException tfe) {

		}

		return false;
	}

}
