package com.setupmyproject.components;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.undercouch.bson4jackson.BsonFactory;

@Component
public class CustomBsonGenerator {

	private ObjectMapper mapper = new ObjectMapper(new BsonFactory());
	/**
	 * 
	 * @param object
	 * @return array generated with bson
	 */
	public String write(Object object){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			mapper.writeValue(baos, object);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return Arrays.toString(baos.toByteArray());		
	}
	
	/**
	 * 
	 * Probably, you gonna use this method with the String generated for {@link CustomBsonGenerator.write}}}
	 * @param bsonArrayWay string in array way. Ex: [1,2,3,4,5].
	 * @param klass
	 * @return
	 */
	public <T> T read(String bsonArrayWay, Class<T> klass){
		String[] byteValues = bsonArrayWay.substring(1, bsonArrayWay.length() - 1).split(",");
		byte[] bytes = new byte[byteValues.length];

		for (int i = 0, len = bytes.length; i < len; i++) {
			bytes[i] = Byte.parseByte(byteValues[i].trim());
		}

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		try {
			return mapper.readValue(bais, klass);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
