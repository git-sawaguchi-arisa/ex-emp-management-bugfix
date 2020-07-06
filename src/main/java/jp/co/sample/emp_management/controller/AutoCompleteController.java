package jp.co.sample.emp_management.controller;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/autoComplete")
public class AutoCompleteController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/get-info")
    public List<String> autoComplete(String partOfName,Pageable pageable){
        List<Employee> employees = employeeService.findByPartOfName(partOfName,pageable).getContent();
        List<String> nameList = new ArrayList<String>();
        employees.forEach(e -> nameList.add(e.getName()));

        return nameList;
    }
}
