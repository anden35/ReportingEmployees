package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

/**
 * Provides compensation details for a given employee. 
 */
@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    /**
     * Creates the compensation for an employee.
     */
    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation for [{}]", compensation.getEmployeeId());
        return compensationRepository.insert(compensation);
    }

    /**
     * Returns a compensation object for the given employee based off of
     * the employee's id.
     */
    @Override
    public Compensation read(String employeeId) {
        LOG.debug("Reading compensation for [{}]", employeeId);
        return compensationRepository.findByEmployeeId(employeeId);
    }

}
