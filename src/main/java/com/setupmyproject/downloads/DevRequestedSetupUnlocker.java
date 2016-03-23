package com.setupmyproject.downloads;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.setupmyproject.daos.RequestedSetupDAO;
import com.setupmyproject.exceptions.http.NotFoundStatusException;
import com.setupmyproject.models.RequestedSetup;

/**
 * Libera o {@link RequestedSetup} sem checar pagamento nem nada
 * @author alberto
 *
 */
@Component
@Profile({"dev"})
public class DevRequestedSetupUnlocker implements RequestedSetupUnlocker{
	
	@Autowired
	private RequestedSetupDAO requestedSetupDAO;

	@Override
	public void unlock(String token, Function<RequestedSetup, Void> code)
			throws NotFoundStatusException {
		RequestedSetup requestedSetup = requestedSetupDAO.searchByToken(token);
		code.apply(requestedSetup);
	}

}
