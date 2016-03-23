package com.setupmyproject.infra;

import java.io.InputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Implementação que resolve o xml a partir de um endereco dentro do classpath.
 * 
 * @author alberto
 *
 */
public class InputStreamXmlSource implements XmlSource {

	private String xmlResourcePath;

	/**
	 * 
	 * @param xmlResourcePath
	 *            caminho completo dentro do buildpath para o arquivo. Ex:
	 *            /maven-dependencies/spring-security.xml
	 */
	public InputStreamXmlSource(String xmlResourcePath) {
		super();
		this.xmlResourcePath = xmlResourcePath;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unmarmshal(Unmarshaller deserialzer) throws JAXBException {
		InputStream xml = SetupMyProjectXMLReader.class
				.getResourceAsStream(xmlResourcePath);
		T object = (T) deserialzer.unmarshal(xml);
		return object;
	}

}
