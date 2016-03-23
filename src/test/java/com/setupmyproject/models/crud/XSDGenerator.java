package com.setupmyproject.models.crud;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public class XSDGenerator {

	public static class MySchemaOutputResolver extends SchemaOutputResolver {

		private String filePath;

		public MySchemaOutputResolver(String filePath) {
			this.filePath = filePath;
		}

		public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
	        File resourceFile = new File(filePath);
	        StreamResult result = new StreamResult(resourceFile);
	        result.setSystemId(resourceFile.toURI().toURL().toString());	        
	        return result;
	    }

	}

	public static void main(String[] args) throws JAXBException, IOException {
		JAXBContext jaxbContext = JAXBContext.newInstance(CrudModels.class);
		SchemaOutputResolver sor = new MySchemaOutputResolver("src/main/resources/crudModels.xsd");
		jaxbContext.generateSchema(sor);
		
		sor = new MySchemaOutputResolver("src/main/webapp/crud/crudModels.xsd");
		jaxbContext.generateSchema(sor);	
		System.out.println("xsds atualizados");
	}
}
