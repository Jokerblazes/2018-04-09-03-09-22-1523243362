package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {
    //在此处完成Company API
    @Autowired
    private CompanyRepository companyRepository;
    @RequestMapping
    public List<Company> getCompanys() {
        return companyRepository.findAll();
    }

    @RequestMapping(value = "/{id}")
    public Company getCompanyById(@PathVariable Long id) {
        return companyRepository.findOne(id);
    }

    @RequestMapping(value = "/{id}/employees")
    public List<Employee> getEmployeesByCompanyId(@PathVariable Long id) {
        Company company = companyRepository.findOne(id);
        return company.getEmployees();
    }
}
