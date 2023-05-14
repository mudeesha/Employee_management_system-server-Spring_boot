package com.example.FirstSpring.controller;

import com.example.FirstSpring.entity.Employee;
import com.example.FirstSpring.exceptions.UserNotFoundException;
import com.example.FirstSpring.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/getEmployees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("getEmployee/{id}")
    public Employee getEmployeeById(@PathVariable Long id){
//        return employeeService.getEmployeeById(id);
        Optional<Employee> employee = Optional.ofNullable(employeeService.getEmployeeById(id));
        if (employee.isPresent()){
            return employee.get();
        }
        else {
            throw new UserNotFoundException(id);
        }

    }

    @PostMapping("addEmployee")
    public Employee addEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }

    @PutMapping("updateEmployee/{id}")
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable Long id){
        employee.setId(id);
        return employeeService.updateEmployee(employee);
    }

    @DeleteMapping("deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Long id){
        if (!employeeService.deleteEmployee(id)){
            throw new UserNotFoundException(id);
            //return "User with id "+id+" not found";
        }
        else{
            employeeService.deleteEmployee(id);
            return "User with id " +id+" hab been deleted!";
        }

    }
}
