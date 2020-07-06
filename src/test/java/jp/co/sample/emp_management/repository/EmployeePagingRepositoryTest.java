package jp.co.sample.emp_management.repository;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.domain.Employee;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmployeePagingRepositoryTest {

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Autowired
    private EmployeePagingRepository employeePagingRepository;

    private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setImage(rs.getString("image"));
        employee.setGender(rs.getString("gender"));
        employee.setHireDate(rs.getDate("hire_date").toLocalDate());
        employee.setMailAddress(rs.getString("mail_address"));
        employee.setZipCode(rs.getString("zip_code"));
        employee.setAddress(rs.getString("address"));
        employee.setTelephone(rs.getString("telephone"));
        employee.setSalary(rs.getInt("salary"));
        employee.setCharacteristics(rs.getString("characteristics"));
        employee.setDependentsCount(rs.getInt("dependents_count"));
        return employee;
    };

    @Test
    void findAllByOrderByName(@PageableDefault(page=0,size=10) Pageable pageable) {
        String sql = "SELECT * FROM employees ORDER BY name";
        Employee employee = new Employee();
        SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
        List<Employee> employeeList = template.query(sql,param, EMPLOYEE_ROW_MAPPER);

        Page<Employee> actualEmployeeList = employeePagingRepository.findAllByOrderByName(pageable);

    }

    @Test
    void findByNameContainingOrderByName() {
    }
}