package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;

/**
 * Provides the basic template for employee crud operations.
 */
public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);
    Employee update(Employee employee);
}
