package com.setupmyproject.daos;

import com.setupmyproject.models.RequestedSetup;
import org.springframework.data.repository.Repository;

public interface ReportDao extends Repository<RequestedSetup, Integer> {

    long count();

}
