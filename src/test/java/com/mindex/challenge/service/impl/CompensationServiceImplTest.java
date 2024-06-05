package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationCreationUrl;
    private String compensationReadUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationCreationUrl = "http://localhost:" + port + "/compensation";
        compensationReadUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    /**
     * Tests the reading of a newly created Compensation object with a mock api.
     */
    @Test
    public void testCreateRead() {
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployeeId("b7839309-3348-463b-a7e3-234234");
        testCompensation.setEffectiveDate(LocalDate.now());
        testCompensation.setSalary(Double.valueOf(125000.00));

        Compensation createdCompensation = restTemplate.postForEntity(compensationCreationUrl, testCompensation, Compensation.class).getBody();

        // Create checks
        assertNotNull("Created compensation should not be null", createdCompensation);
        assertNotNull("Created compensation employee ID should not be null", createdCompensation.getEmployeeId());
        assertCompensationEquivalence(testCompensation, createdCompensation);

        // Read checks
        Compensation readCompensation = restTemplate.getForEntity(compensationReadUrl, Compensation.class, testCompensation.getEmployeeId()).getBody();

        assertNotNull("Read compensation should not be null", readCompensation);
        assertEquals("Employee IDs should match", createdCompensation.getEmployeeId(), readCompensation.getEmployeeId());
        assertCompensationEquivalence(createdCompensation, readCompensation);
    }

    /*
     * Helper method for modularity to compare full objects.  
     * Note: The Compensation class could also have the equals and hashcode
     * overridden to do assertEquals(expected, actual) and hide that logic in the class that should know how it would be equal
     * to another instance of Compensation.
     */
    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
