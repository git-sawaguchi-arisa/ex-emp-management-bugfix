package jp.co.sample.emp_management.repository;

import jp.co.sample.emp_management.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePagingRepository extends JpaRepository<Employee,Integer> {
    public Page<Employee> findAllByOrderByName(Pageable pageable);

    public Page<Employee> findByNameContainingOrderByName(String name,Pageable pageable);

    Employee findByMailAddress(String emailAddress);
}

