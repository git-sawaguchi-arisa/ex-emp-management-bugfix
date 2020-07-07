package jp.co.sample.emp_management.service;

import java.util.List;
import java.util.Objects;

import jp.co.sample.emp_management.repository.EmployeePagingRepository;
import lombok.Data;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.repository.EmployeeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 従業員情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */

@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeePagingRepository employeePagingRepository;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 従業員情報を全件取得します.
	 *
	 * @return　従業員情報一覧
	 */
	public Page<Employee> showList(Pageable pageable) {
		Page<Employee> employeeList = employeePagingRepository.findAllByOrderByName(pageable);
		return employeeList;
	}

	/**
	 * 従業員情報を取得します.
	 *
	 * @param id ID
	 * @return 従業員情報                 vvvvvvvvvvvvvvvvv
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}

	/**
	 * 従業員情報を更新します.
	 *
	 * @param employee 　更新した従業員情報
	 */
	@Synchronized
	synchronized public void update(Employee employee) {

		if (Objects.isNull(employee.getId())) {
			employeeRepository.insert(employee);
		} else {


			if (Objects.isNull(employee.getImage())) {
				employeeRepository.updateWithoutImage(employee);
			} else {
				employeeRepository.update(employee);
			}
		}
	}

	public Page<Employee> findByPartOfName(String partOfName, Pageable pageable) {
		return employeePagingRepository.findByNameContainingOrderByName(partOfName, pageable);
	}

	public void insert(Employee employee) {
		employeeRepository.insert(employee);
	}

	public Employee findByMailAddress(String emailAddress) {
		return employeePagingRepository.findByMailAddress(emailAddress);
	}
}
