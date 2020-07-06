package jp.co.sample.emp_management.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;

/**
 * 管理者情報を表すドメイン.
 * 
 * @author igamasayuki
 * 
 */
@Data
@Entity
@Table(name = "administrators")
public class Administrator{
	/** id(主キー) */
	@Id
	@Column(name="id",nullable = false, unique = true)
	private Integer id;
	/** 名前 */
	@Column(name="name",nullable = false)
	private String name;
	/** メールアドレス */
	@Column(name="mail_address",nullable = false, unique = true)
	private String mailAddress;
	/** パスワード */
	@Column(name="password",nullable = false)
	private String password;

	/**
	 * 引数無しのコンストラクタ.
	 */
	public Administrator() {
	}

	/**
	 * 初期化用コンストラクタ.
	 * 
	 * @param id
	 *            id(主キー)
	 * @param name
	 *            名前
	 * @param mailAddress
	 *            メールアドレス
	 * @param password
	 *            パスワード
	 */
	public Administrator(Integer id, String name, String mailAddress, String password) {
		this.id = id;
		this.name = name;
		this.mailAddress = mailAddress;
		this.password = password;
	}

	public Administrator(String name, String mailAddress, String password) {
		this.name = name;
		this.mailAddress = mailAddress;
		this.password = password;
	}
}
