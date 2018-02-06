package com.setupmyproject.daos;

import com.setupmyproject.models.RequestedSetup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ReportDao extends Repository<RequestedSetup, Integer> {

    long count();

    @Query("select r.bson from RequestedSetup r")
    List<String> allBsons();

}
