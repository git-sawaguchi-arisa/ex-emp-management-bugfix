package jp.co.sample.emp_management.service;

import jp.co.sample.emp_management.security.LoginRepository;
import jp.co.sample.emp_management.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.repository.AdministratorRepository;

import java.util.Objects;

/**
 * 管理者情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public  class AdministratorService  implements UserDetailsService{

	@Autowired
	private AdministratorRepository administratorRepository;

	@Autowired
	private LoginRepository loginRepository;




	/**
	 * 管理者情報を登録します.
	 * @return DBに追加するオブジェクト内のEmailが存在していたらtrue,していなかったらfalseを返す
	 * @param administrator　管理者情報
	 */
	public boolean insert(Administrator administrator) {
		Administrator checkAdmin = administratorRepository.findByMailAddress(administrator.getMailAddress());

		if(Objects.nonNull(checkAdmin)){
			return true;
		}else {
			administratorRepository.insert(administrator);
			return false;
		}
	}
	
	/**
//	 * ログインをします.
//	 * @param mailAddress メールアドレス
//	 * @param password パスワード
//	 * @return 管理者情報　存在しない場合はnullが返ります
//	 */
//	public Administrator login(String mailAddress, String passward) {
//		return administratorRepository.findByMailAddressAndPassward(mailAddress, passward);
//	}

	@Override
	public UserDetails loadUserByUsername(String mailAddress) throws UsernameNotFoundException {
		if (mailAddress == null || "".equals(mailAddress)) {
			throw new UsernameNotFoundException("Username is empty");
		}

		Administrator administrator = loginRepository.findByMailAddress(mailAddress);
		if (administrator == null) {
			throw new UsernameNotFoundException("User not found: " + mailAddress);
		}
		UserDetailsImpl userDetailsImpl = new UserDetailsImpl();
		userDetailsImpl.setAdministrator(administrator);

		return userDetailsImpl;
	}

//	@Transactional
//	public void registerAdmin(String username, String password, String mailAddress) {
//		Administrator administrator = new Administrator(username, passwordEncoder.encode(password), mailAddress);
//		loginRepository.save(administrator);
//	}
}
