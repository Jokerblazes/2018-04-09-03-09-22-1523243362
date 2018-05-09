package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/companies")
public class CompanyController {
    //在此处完成Company API
    @Autowired
    private CompanyRepository companyRepository;
    @RequestMapping
    public List<Company> getCompanys() {
        return companyRepository.findAll();
    }
}
