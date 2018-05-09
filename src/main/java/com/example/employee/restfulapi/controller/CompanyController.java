package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {
    //在此处完成Company API
    @Autowired
    private CompanyRepository companyRepository;
    @RequestMapping(method = RequestMethod.GET )
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Company getCompanyById(@PathVariable Long id) {
        return companyRepository.findOne(id);
    }

    @RequestMapping(value = "/{id}/employees",method = RequestMethod.GET)
    public List<Employee> getEmployeesByCompanyId(@PathVariable Long id) {
        Company company = companyRepository.findOne(id);
        return company.getEmployees();
    }

    @RequestMapping(value = "/page/{page}/pageSize/{pageSize}",method = RequestMethod.GET)
    public List<Company> getCompaniesByPage(@PathVariable Integer page, @PathVariable Integer pageSize) {
        Page<Company> companyPage = companyRepository.findAll(new PageRequest(page - 1, pageSize));
        return companyPage.getContent();
    }

}
