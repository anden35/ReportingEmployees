package com.mindex.challenge.service.impl;

import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

/**
 * Provides insight into how many other employees report to a given employee.
 */
@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Returns the details of the reporting employees of the given employee id.
     */
    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Querying total number of reporting employees under the employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);
        //There isn't a reason to continue processing if there isn't an employee found with that id.
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        ReportingStructure report = new ReportingStructure();
        report.setEmployee(employee);
        int numberOfReports = countAllReports(employee);
        report.setNumberOfReports(numberOfReports);
        return report;
    }


    /*
     * Helper method to count the reporting employees of an employee.
     */
    private int countAllReports(Employee employee) {
        //Need to do our guard clauses.
        if (employee == null) {
            return 0;
        }

        int count = 0;
        Queue<Employee> queue = new LinkedList<>();
        queue.add(employee);
         // Traverse through the employee hierarchy.
        while (!queue.isEmpty()) {
            //Process an Employee's direct reports
            Employee currentEmployee = queue.poll();
            if (currentEmployee.getDirectReports() != null) {
                for (Employee directReport : currentEmployee.getDirectReports()) {
                    // Increment the count for each direct report
                    count++;
                    Employee directReportDetails = employeeRepository.findByEmployeeId(directReport.getEmployeeId());
                    if (directReportDetails != null) {
                        // Add the report to the queue to keep traversing over the hierarchy.
                        queue.add(directReportDetails);
                    }
                }
            }
        }

        return count;
    }

}
