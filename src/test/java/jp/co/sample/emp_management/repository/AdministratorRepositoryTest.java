package jp.co.sample.emp_management.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.sample.emp_management.domain.Administrator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AdministratorRepositoryTest {

	@Autowired
	private AdministratorRepository administratorRepository;

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};

//	@BeforeEach
//	public void testInsert() {
//		System.out.println("DB初期化処理開始");
//		Administrator administrator = new Administrator();
//		administrator.setName("伊賀将之");
//		administrator.setMailAddress("testtest@sample.com");
//		administrator.setPassword("testtest");
//		administratorRepository.insert(administrator);
//		System.out.println("インサートが完了しました。");
//
//		System.out.println("DB初期化処理終了");
//	}



	@Test
	void insertTest() {
		Administrator insertAdministrator = new Administrator(null, "test", "testtest@test.com", "test");
		administratorRepository.insert(insertAdministrator);

		String sql = "SELECT * FROM administrators WHERE mail_address = :mail";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "test").addValue("mail", "testtest@test.com");

		Administrator getAdministrator = template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);


		assertEquals("test",getAdministrator.getName());
		assertEquals("testtest@test.com",getAdministrator.getMailAddress());
		assertTrue(passwordEncoder.matches("test", getAdministrator.getPassword()));

		String sqlAfter = "DELETE FROM administrators WHERE mail_address = 'testtest@test.com'";
		template.update(sqlAfter, param);
	}

	@Test
	void findByMailAddress() {
		String sql  = "INSERT INTO administrators(name, mail_address, password) VALUES(:name, :mailAddress, :password)";

		Administrator administrator = new Administrator(null, "test", "testtest@test.com", "test");

		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		template.update(sql,param);

		Administrator getAdministrator = administratorRepository.findByMailAddress("testtest@test.com");
		Administrator getNullAdministrator = administratorRepository.findByMailAddress("aaaaaaaaaaaaaaa");

		assertEquals("test",getAdministrator.getName());
		assertEquals("testtest@test.com",getAdministrator.getMailAddress());
		assertEquals("test", getAdministrator.getPassword());

		assertNull(getNullAdministrator);

		String sqlAfter = "DELETE FROM administrators WHERE mail_address = 'testtest@test.com'";
		template.update(sqlAfter, param);
	}
}
