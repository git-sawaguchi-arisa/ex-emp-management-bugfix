package jp.co.sample.emp_management.repository;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.emp_management.domain.Employee;

import static java.util.Objects.isNull;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author igamasayuki
 * 
 */
@Repository
public class EmployeeRepository {

	/**
	 * Employeeオブジェクトを生成するローマッパー.
	 */
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
		employee.setVersion(rs.getInt("version"));
		return employee;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 従業員一覧情報を入社日順で取得します.
	 * 
	 * @return 全従業員一覧 従業員が存在しない場合はサイズ0件の従業員一覧を返します
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count,version FROM employees  ORDER BY hire_date ASC";

		List<Employee> developmentList = template.query(sql, EMPLOYEE_ROW_MAPPER);

		return developmentList;
	}

	/**
	 * 主キーから従業員情報を取得します.
	 * 
	 * @param id 検索したい従業員ID
	 * @return 検索された従業員情報
	 * @exception 従業員が存在しない場合は例外を発生します
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count,version FROM employees WHERE id=:id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		List<Employee> employees  = template.query(sql, param, EMPLOYEE_ROW_MAPPER);
		if(employees.isEmpty()){
			return null;
		}else {
			return employees.get(0);
		}
	}

	/**
	 * 従業員情報を変更します.
	 */
	public void update(Employee employee) {
		String sql = "UPDATE employees SET name = :name,image = :image, gender = :gender, hire_date = :hire_date,mail_address = :mail_address , zip_code = :zip_code , address = :address , telephone = :telephone, salary = :salary,characteristics = :characteristics,dependents_count = :dependents_count, version =:version where id = :id";



		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("id", employee.getId())
				.addValue("name", employee.getName())
				.addValue("image",employee.getImage())
				.addValue("gender",employee.getGender())
				.addValue("hire_date",employee.getHireDate())
				.addValue("mail_address",employee.getMailAddress())
				.addValue("zip_code",employee.getZipCode())
				.addValue("address",employee.getAddress())
				.addValue("telephone",employee.getTelephone())
				.addValue("salary",employee.getSalary())
				.addValue("characteristics",employee.getCharacteristics())
				.addValue("dependents_count",employee.getDependentsCount())
				.addValue("version",employee.getVersion());
		System.out.println(employee.getImage());
		template.update(sql, param);
	}

	public void updateWithoutImage(Employee employee) {
		String sql = "UPDATE employees SET name = :name,gender = :gender, hire_date = :hire_date,mail_address = :mail_address , zip_code = :zip_code , address = :address , telephone = :telephone, salary = :salary,characteristics = :characteristics,dependents_count = :dependents_count, version =:version where id = :id";


		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("id", employee.getId())
				.addValue("name", employee.getName())
				.addValue("gender",employee.getGender())
				.addValue("hire_date",employee.getHireDate())
				.addValue("mail_address",employee.getMailAddress())
				.addValue("zip_code",employee.getZipCode())
				.addValue("address",employee.getAddress())
				.addValue("telephone",employee.getTelephone())
				.addValue("salary",employee.getSalary())
				.addValue("characteristics",employee.getCharacteristics())
				.addValue("dependents_count",employee.getDependentsCount())
				.addValue("version",employee.getVersion());
		template.update(sql, param);
	}

	public List<Employee> findByPartOfName(String partOfName){
		String sql = "SELECT * FROM employees WHERE name like :partOfName ORDER BY hire_date ASC";

		SqlParameterSource param = new MapSqlParameterSource().addValue("partOfName", "%"+partOfName+"%");

		List<Employee> employees = template.query(sql,param,EMPLOYEE_ROW_MAPPER);

		return employees;
	}

	public void insert(Employee employee){
		String sql = "INSERT INTO employees(id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count,version)"+
				"VALUES((SELECT max(id)+1 FROM employees),:name,:image,:gender,:hire_date,:mail_address,:zip_code,:address,:telephone,:salary,:characteristics,:dependents_count,:version)";

		if(isNull(employee.getImage())){
			employee.setImage("no_image");
		}

		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", employee.getName())
				.addValue("image",employee.getImage())
				.addValue("gender",employee.getGender())
				.addValue("hire_date",employee.getHireDate())
				.addValue("mail_address",employee.getMailAddress())
				.addValue("zip_code",employee.getZipCode())
				.addValue("address",employee.getAddress())
				.addValue("telephone",employee.getTelephone())
				.addValue("salary",employee.getSalary())
				.addValue("characteristics",employee.getCharacteristics())
				.addValue("dependents_count",employee.getDependentsCount())
				.addValue("version",employee.getVersion());

		template.update(sql,param);
	}
}
