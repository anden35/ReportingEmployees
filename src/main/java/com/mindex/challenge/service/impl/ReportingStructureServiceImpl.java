package com.mindex.challenge.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Querying total number of reporting employees under the employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        ReportingStructure report = new ReportingStructure();
        report.setEmployee(employee);
        int numberOfReports = countAllReports(employee);
        report.setNumberOfReports(numberOfReports);
        return report;
    }

    private int countAllReports(Employee employee) {
        if (employee == null) {
            return 0;
        }

        int count = 0;
        Queue<Employee> queue = new LinkedList<>();
        queue.add(employee);

        while (!queue.isEmpty()) {
            Employee currentEmployee = queue.poll();
            if (currentEmployee.getDirectReports() != null) {
                for (Employee directReport : currentEmployee.getDirectReports()) {
                    count++;
                    Employee directReportDetails = employeeRepository.findByEmployeeId(directReport.getEmployeeId());
                    if (directReportDetails != null) {
                        queue.add(directReportDetails);
                    }
                }
            }
        }

        return count;
    }

}
