package jp.co.sample.emp_management.form;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * 管理者情報登録時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class InsertAdministratorForm {
	/** 名前 */
	@NotBlank(message = "氏名を入力してください")
	private String name;
	/** メールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式が不正です")
	private String mailAddress;
	/** パスワード */
	@NotBlank(message = "パスワードを入力してください")
	private String password;

	/** 確認用パスワード */
	@NotBlank(message = "確認用パスワードを入力してください")
	private String passwordForConfirmation;


	@AssertTrue(message = "パスワードが一致しません")
	public boolean isValidate(){
		if(Objects.isNull(password)) return true;
		if(Objects.isNull(passwordForConfirmation)) return true;
		if(Objects.equals(password, passwordForConfirmation)) return true;
		return false;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordForConfirmation() {
		return passwordForConfirmation;
	}

	public void setPasswordForConfirmation(String passwordForConfirmation) {
		this.passwordForConfirmation = passwordForConfirmation;
	}


	@Override
	public String toString() {
		return "InsertAdministratorForm{" +
				"name='" + name + '\'' +
				", mailAddress='" + mailAddress + '\'' +
				", password='" + password + '\'' +
				", passwordForConfirmation='" + passwordForConfirmation + '\'' +
				'}';
	}
}
