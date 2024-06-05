package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

/**
 * Provides the basic template for compensation crud operations.
 */
public interface CompensationService {
    Compensation create(Compensation compensation);
    Compensation read(String employeeId);
}
