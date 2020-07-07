package jp.co.sample.emp_management.controller;

import jp.co.sample.emp_management.repository.EmployeeRepository;
import jp.co.sample.emp_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mailAddressCheck")
public class EmployeeMailcheckController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/get-info")
    public String getMailAddress(String mailAddress){
        System.out.println(mailAddress);
        String getMailAddress;
        try {
            getMailAddress = employeeService.findByMailAddress(mailAddress).getMailAddress();
        }catch(Exception e){
            return null;
        }
        System.out.println(getMailAddress);
        return getMailAddress;
    }
}
