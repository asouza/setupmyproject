package com.setupmyproject.controllers;

import com.setupmyproject.daos.ReportDao;
import com.setupmyproject.models.ProjectDefinition;
import com.setupmyproject.models.SetupState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("lasjdfklaksjz")
public class ReportController {


    private final ReportDao reportDao;


    @Autowired
    public ReportController(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @GetMapping("/totalOfDownloads")
    public Long totalOfDownloads(){
        return reportDao.count();
    }


    @GetMapping("/byProject")
    public Map<String, Integer> allBsons(){
        return reportDao.allBsons().stream()
                .map(SetupState::new)
                .map(SetupState::getProjectType)
                .collect(Collectors.groupingBy(ProjectDefinition::name))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().size() ));
    }


}
