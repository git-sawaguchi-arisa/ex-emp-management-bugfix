package jp.co.sample.emp_management.controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.form.UpdateEmployeeForm;
import jp.co.sample.emp_management.service.EmployeeService;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import static java.util.Objects.isNull;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpForm() {
		return new UpdateEmployeeForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員一覧画面を出力します.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(@PageableDefault(page=0,size=10) Pageable pageable, Model model) {
		Page<Employee> pages = employeeService.showList(pageable);
		model.addAttribute("page", pages);
		model.addAttribute("employeeList",pages.getContent());
		return "employee/list";
	}

	
	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 * 
	 * @param id リクエストパラメータで送られてくる従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		UpdateEmployeeForm form = new UpdateEmployeeForm();
		form.setId(employee.getId());
		form.setName(employee.getName());
		form.setImage(employee.getImage());
		form.setGender(employee.getGender());
		form.setHireDate(employee.getHireDate());
		form.setMailAddress(employee.getMailAddress());
		form.setZipCode(employee.getZipCode());
		form.setAddress(employee.getAddress());
		form.setTelephone(employee.getTelephone());
		form.setSalary(employee.getSalary());
		form.setCharacteristics(employee.getCharacteristics());
		form.setDependentsCount(employee.getDependentsCount());
		form.setVersion(employee.getVersion());
		model.addAttribute("updateEmployeeForm",form);
		return "employee/detail";
	}
	
	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を更新する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細(ここでは扶養人数のみ)を更新します.
	 * 
	 * @param form
	 *            従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@RequestMapping("/update")
	public String update(
			@Validated UpdateEmployeeForm form
			,BindingResult result
			,MultipartFile multipartFile
			,Model model) {

		Employee employee = new Employee();

		if(!multipartFile.isEmpty()) {
			try {
				StringBuffer data = new StringBuffer();
				String base64 = new String(Base64.encodeBase64(multipartFile.getBytes()), "ASCII");
				data.append("data:image/jpeg;base64,");
				data.append(base64);
				employee.setImage(data.toString());
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("imageUploadError", true);
				return showDetail(String.valueOf(form.getId()), model);
			}
		}

		employee.setId(form.getId());
		employee.setName(form.getName());
		employee.setGender(form.getGender());
		employee.setHireDate(form.getHireDate());
		employee.setMailAddress(form.getMailAddress());
		employee.setZipCode(form.getZipCode());
		employee.setAddress(form.getAddress());
		employee.setSalary(form.getSalary());
		employee.setTelephone(form.getTelephone());
		employee.setCharacteristics(form.getCharacteristics());
		employee.setDependentsCount(form.getDependentsCount());


		if(isNull(form.getVersion())){
			employee.setVersion(1);
			employeeService.update(employee);
			return "redirect:/employee/showList";
		}

		Employee checkEmployee = employeeService.showDetail(form.getId());

		if(checkEmployee.getVersion() == form.getVersion()){
			int newVersion = form.getVersion() + 1;
			employee.setVersion(newVersion);

			employeeService.update(employee);
		}else {
			model.addAttribute("updateError", true);
			return showDetail(String.valueOf(form.getId()), model);
		}

		return "redirect:/employee/showList";
	}

	@RequestMapping("/showList/search")
	public String showListFindByName(@PageableDefault(page=0,size=10)Pageable pageable,String partOfName,Model model){
		Page<Employee> employeeList = employeeService.findByPartOfName(partOfName,pageable);
		if(employeeList.getContent().isEmpty()){
			model.addAttribute("searchResult",true);
			return showList(pageable,model);
		}else {
			model.addAttribute("searchResult",false);
			model.addAttribute("employeeList", employeeList.getContent());
			model.addAttribute("page",employeeList);
		}
		return "employee/list";
	}

	@RequestMapping("/toInsert")
	public String toInsert(){
		return "/employee/detail";
	}
}
