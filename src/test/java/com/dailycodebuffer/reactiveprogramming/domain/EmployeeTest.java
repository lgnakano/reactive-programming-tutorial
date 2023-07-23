package com.dailycodebuffer.reactiveprogramming.domain;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void getDirectReportsExpand() {

        // CEO - root object
        Employee CEO = new Employee("CEO");

// Directors reporting to CEO
        Employee directorA = new Employee("Director of Dept A");
        Employee directorB = new Employee("Director of Dept B");
        CEO.addDirectReports(directorA, directorB);

// Managers reporting to directors
        Employee managerA1 = new Employee("Manager 1 of Dept A");
        Employee managerA2 = new Employee("Manager 2 of Dept A");
        Employee managerB1 = new Employee("Manager 1 of Dept B");
        Employee managerB2 = new Employee("Manager 2 of Dept B");
        directorA.addDirectReports(managerA1, managerA2);
        directorB.addDirectReports(managerB1, managerB2);

        var employeesFlux = Mono.fromSupplier(() -> CEO)
                .expand(CEO::getDirectReports)
                .map(Employee::toString)
                .log();

        StepVerifier.create(employeesFlux)
                .expectNext("CEO")
                .expectNext("\tDirector of Dept A")
                .expectNext("\tDirector of Dept B")
                .expectNext("\t\tManager 1 of Dept A")
                .expectNext("\t\tManager 2 of Dept A")
                .expectNext("\t\tManager 1 of Dept B")
                .expectNext("\t\tManager 2 of Dept B")
                .verifyComplete();
    }


        @Test
        void getDirectReportsExpandDeep() {

            // CEO - root object
            Employee CEO = new Employee("CEO");

// Directors reporting to CEO
            Employee directorA = new Employee("Director of Dept A");
            Employee directorB = new Employee("Director of Dept B");
            CEO.addDirectReports(directorA, directorB);

// Managers reporting to directors
            Employee managerA1 = new Employee("Manager 1 of Dept A");
            Employee managerA2 = new Employee("Manager 2 of Dept A");
            Employee managerB1 = new Employee("Manager 1 of Dept B");
            Employee managerB2 = new Employee("Manager 2 of Dept B");
            directorA.addDirectReports(managerA1, managerA2);
            directorB.addDirectReports(managerB1, managerB2);

            var employeesFlux = Mono.fromSupplier(()->CEO)
                    .expandDeep(CEO::getDirectReports)
                    .map(Employee::toString)
                    .log();

            StepVerifier.create(employeesFlux)
                    .expectNext("CEO")
                    .expectNext("\tDirector of Dept A")
                    .expectNext("\t\tManager 1 of Dept A")
                    .expectNext("\t\tManager 2 of Dept A")
                    .expectNext("\tDirector of Dept B")
                    .expectNext("\t\tManager 1 of Dept B")
                    .expectNext("\t\tManager 2 of Dept B")
                    .verifyComplete();

        }
}
