package com.setupmyproject.controllers;

import com.setupmyproject.daos.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {


    private ReportDao reportDao;

    @Autowired
    public ReportController(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @GetMapping("/lasjdfklaksjz")
    public Long totalOfDownloads(){
        return reportDao.count();
    }

}
