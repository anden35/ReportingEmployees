package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String reportToEmployeeIDUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportToEmployeeIDUrl = "http://localhost:" + port + "/report-to/employee/{id}";
    }

     /**
     * Tests the reading of a newly created ReportingStructure object with a mock api.
     */
    @Test
    public void testReadReportingsForEmployee() {

        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
       
        ResponseEntity<ReportingStructure> employeeReports = restTemplate.getForEntity(reportToEmployeeIDUrl,
                ReportingStructure.class, testEmployee.getEmployeeId());

        assertEquals(200, employeeReports.getStatusCodeValue());
        ReportingStructure reportingStructure = employeeReports.getBody();
        assertEquals(4, (int) reportingStructure.getNumberOfReports());

    }
}
