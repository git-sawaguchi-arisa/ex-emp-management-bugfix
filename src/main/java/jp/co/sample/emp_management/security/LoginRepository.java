package jp.co.sample.emp_management.security;


import jp.co.sample.emp_management.domain.Administrator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoginRepository extends CrudRepository<Administrator, Long> {
    Administrator findByMailAddress(String mailAddress);
}
