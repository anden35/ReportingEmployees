package com.mindex.challenge;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

@Component
public class DataBootstrap {
    private static final String EMPLOYEE_DATASTORE_LOCATION = "/static/employee_database.json";
    private static final String COMPENSATION_DATASTORE_LOCATION = "/static/employee_compensation_database.json";

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompensationRepository compensationRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        InputStream employeeInputStream = this.getClass().getResourceAsStream(EMPLOYEE_DATASTORE_LOCATION);
        InputStream compensationInputStream = this.getClass().getResourceAsStream(COMPENSATION_DATASTORE_LOCATION);

        Employee[] employees = null;
        Compensation[] compensations = null;

        try {
            employees = objectMapper.readValue(employeeInputStream, Employee[].class);
            compensations = objectMapper.readValue(compensationInputStream, Compensation[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Employee employee : employees) {
            employeeRepository.insert(employee);
        }

        for (Compensation compensation : compensations) {
            compensationRepository.insert(compensation);
        }

    }
}
