package jp.co.sample.emp_management.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {


    @Autowired
    private MailSender sender;

    public void sendMail(String mailAddress) {
        SimpleMailMessage msg = new SimpleMailMessage();
        System.out.println("mail util");
        msg.setFrom("program.kiyomori@gmail.com");
        msg.setTo(mailAddress);
        msg.setSubject("テストメール"); //タイトルの設定
        msg.setText("Spring Boot より本文送信"); //本文の設定

        this.sender.send(msg);
    }
}
