package jp.co.sample.emp_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class MailController {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    @Autowired
    private MailSender mailSender;
    @RequestMapping(value = "/mail/test/eee", method = RequestMethod.GET)
    public String index() {
        return "/employee/list";
    }
    @RequestMapping(value = "/mail/test", method = RequestMethod.GET)
    public String sendMail() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("program.kiyomori@gmail.com");// To
        String insertMessage = "Test from Spring Mail" + LINE_SEPARATOR;
        insertMessage += "Test from Spring Mail" + LINE_SEPARATOR;
        msg.setSubject("Test from Spring Mail");// Set Title
        msg.setText(insertMessage);// Set Message
        mailSender.send(msg);
        return "/employee/list";
    }
}