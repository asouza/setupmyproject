package com.setupmyproject.infra;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.SAXException;

public interface XmlSource {

	<T> T unmarmshal(Unmarshaller deserialzer) throws JAXBException, SAXException;

}
