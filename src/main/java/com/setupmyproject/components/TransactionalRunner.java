package com.setupmyproject.components;

import java.util.concurrent.Callable;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

/**
 * Essa classe foi criada para que um código transacional possa rodar dentro de um contexto assíncrono
 * @author alberto
 *
 */
@Component
@Transactional
public class TransactionalRunner {

	public <T> T run(Callable<T> code) {
		try {
			return code.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
