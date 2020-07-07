package jp.co.sample.emp_management.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class UpdateEmployeeForm {
	private Integer id;

	@NotBlank(message = "名前が空欄です")
	private String name;

	private String image;

	@NotNull(message = "性別を選択してください")
	private String gender;

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull(message = "日付を入力してください")
	@Past(message = "有効な日付を入力してください")
	private LocalDate hireDate;

	@Email(message = "メールアドレスを入力してください")
	private String mailAddress;

	@NotBlank(message = "郵便番号を入力してください")
	private String zipCode;

	@NotBlank(message = "郵便番号を入力してください")
	private String address;

	@NotBlank(message = "電話番号を入力してください")
	private String telephone;

	@NotNull(message = "給料を入力してください")
	@Max(value = 10000000 ,message = "給料を入力してください")
	private Integer salary;

	@NotBlank(message = "特性を入力してください")
	private String characteristics;

	@NotNull(message = "扶養人数を入力してください")
	private Integer dependentsCount;

	private Integer version;

	//    getter setter
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	public Integer getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(Integer dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
