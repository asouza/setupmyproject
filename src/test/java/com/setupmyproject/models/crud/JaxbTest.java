package com.setupmyproject.models.crud;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.setupmyproject.infra.SetupMyProjectXMLReader;
import com.setupmyproject.infra.StringReaderXmlSource;
import com.setupmyproject.models.exceptions.InvalidXSDException;

public class JaxbTest {

	@Test
	public void shouldLoadValidMapping() {
		CrudModels models = SetupMyProjectXMLReader.read("/crudModel.xml",
				CrudModels.class);
		models.getList().forEach((model) -> {
			System.out.println("Model " + model.getName());			
			model.getFields().forEach((field) -> {
				System.out.println("Field name " + field.getName());
				System.out.println("Field type " + field.getType());
				System.out.println("Field javaType? " + field.isJavaType());
			});
		});
	}

	@SuppressWarnings("resource")
	@Test(expected=InvalidXSDException.class)
	public void shouldNotLoadInvalidateMapping() throws SAXException, IOException {
		URL url = SetupMyProjectXMLReader.class.getResource("/crudModel-invalid.xml");
		InputStream content = (InputStream) url.getContent();
		String xml = new Scanner(content).useDelimiter("\\$").next();
		try {
			CrudModels models = SetupMyProjectXMLReader.read(new StringReaderXmlSource(xml, "/crudModels.xsd"),CrudModels.class);
		} catch (InvalidXSDException e) {
			throw e;
		}
	}
}
