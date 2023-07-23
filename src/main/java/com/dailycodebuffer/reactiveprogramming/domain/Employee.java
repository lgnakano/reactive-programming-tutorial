package com.dailycodebuffer.reactiveprogramming.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
public class Employee {

    private String title;
    private int level;
    private List<Employee> directReports;

    public Employee(String title) {
        this.title = title;
        this.directReports = new ArrayList<>();
    }

    public void addDirectReports(Employee... employees){
        Stream.of(employees).forEach(e -> e.setLevel(this.getLevel() + 1));
        directReports.addAll(List.of(employees));
    }

    @Override
    public String toString() {
        return "\t".repeat(this.level) + this.title;
    }

    public Flux<Employee> getDirectReports(Employee employee){
        return Flux.fromIterable(employee.getDirectReports());
    }
}
