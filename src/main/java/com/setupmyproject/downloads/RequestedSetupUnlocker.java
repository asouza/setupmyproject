package com.setupmyproject.downloads;

import java.util.function.Function;

import com.setupmyproject.exceptions.http.NotFoundStatusException;
import com.setupmyproject.exceptions.http.UnauthorizedStatusException;
import com.setupmyproject.models.RequestedSetup;

/**
 * A ideia é que as implementações retornem o RequestedSetup verificando se o pagamento está correto. Só que para dev, pode 
 * ser interessante liberar de todo jeito.
 * @author alberto
 *
 */
public interface RequestedSetupUnlocker {

	/**
	 * 
	 * @param token para buscar pelo {@link RequestedSetup}
	 * @param code que deve ser executado se o requestedSetup estiver liberado
	 * @return {@link RequestedSetup} liberado
	 * @throws NotFoundStatusException caso o pagamento não tenha sido liberado
	 * @throws UnauthorizedStatusException caso o pagamento não tenha sido autorizado
	 */
	void unlock(String token,Function<RequestedSetup, Void> code) throws NotFoundStatusException,UnauthorizedStatusException;

}
