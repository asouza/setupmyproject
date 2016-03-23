package com.setupmyproject.infra;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.setupmyproject.models.exceptions.InvalidXSDException;

public class SetupMyProjectXMLReader {

	/**
	 * 
	 * @param xmlResourcePath
	 *            caminho completo dentro do buildpath para o arquivo. Ex:
	 *            /maven-dependencies/spring-security.xml
	 * @param klass
	 *            classe usada para gerar o objeto a partir do xml
	 * @return
	 */
	public static <T> T read(String xmlResourcePath, Class<T> klass) {
		return read(new InputStreamXmlSource(xmlResourcePath),klass);
	}
	
	/**
	 * 
	 * @param xmlSource implementacao que resolver a deserializacao do xml            
	 * @param klass
	 *            classe usada para gerar o objeto a partir do xml
	 * @return
	 * @throws InvalidXSDException lança essa caso uma falha de validação do xsd seja encontrada
	 * @throws JAXBException caso uma falha genérica de deserialização seja encontrada
	 */
	public static <T> T read(XmlSource xmlSource, Class<T> klass) {
		try {
			JAXBContext context = JAXBContext.newInstance(klass);
			Unmarshaller deserialzer = context.createUnmarshaller();
			return xmlSource.unmarmshal(deserialzer);
		} catch (JAXBException e) {
			if(e.getLinkedException() instanceof SAXParseException){
				throw new InvalidXSDException((SAXParseException)e.getLinkedException());
			}
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
	}
}
