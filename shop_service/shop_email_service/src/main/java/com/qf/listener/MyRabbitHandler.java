package com.qf.listener;

import com.qf.entity.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by  .Life on 2019/07/18/0018 20:44
 */
@Component
public class MyRabbitHandler {
    //开一个线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;

    @RabbitListener(queues = "email_queue")
    public void handler(Email email){
        executorService.submit(()->{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //创建一个spring提供的邮件帮助对象
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            try {
                //设置标题
                mimeMessageHelper.setSubject(email.getSubject());
                mimeMessageHelper.setFrom(fromEmail);

                //发给谁
                mimeMessageHelper.setTo(email.getTo());
                //邮件内容
                mimeMessageHelper.setText(email.getContent(),true);
                //发送时间
                mimeMessageHelper.setSentDate(new Date());
                System.out.println("发送了邮件");
                //发送
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }
}
