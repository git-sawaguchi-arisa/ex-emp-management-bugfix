package jp.co.sample.emp_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@SpringBootApplication
public class ExEmpManageAnswerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExEmpManageAnswerApplication.class, args);
	}
}
