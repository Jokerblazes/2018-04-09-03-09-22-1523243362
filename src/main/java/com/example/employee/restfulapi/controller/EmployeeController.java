package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    //在此处完成Employee API

    @RequestMapping(method = RequestMethod.GET)
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/page/{page}/pageSize/{pageSize}")
    public List<Employee> getEmployeesByPage(@PathVariable Integer page,@PathVariable Integer pageSize) {
        Page<Employee> employeePage = employeeRepository.findAll(new PageRequest(page - 1, pageSize));
        return employeePage.getContent();
    }
}
