package com.restaurant.munchies.services.implementation;

import com.restaurant.munchies.entities.GroupOrder;
import com.restaurant.munchies.services.GroupOrderService;
import com.restaurant.munchies.services.MailService;
import com.restaurant.munchies.services.OrdersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.util.*;

@Service
public class MailServiceImpl implements MailService {

    //Template Engine
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private GroupOrderService groupOrderService;

    @Autowired
    private OrdersService ordersService;

    @Override
    public void sendEmailAboutOrders(GroupOrder groupOrder) throws MessagingException {

        Context context = new Context();
        context.setVariable("groupOrder", groupOrder);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(groupOrder.getRestaurantId().getEmail());
            messageHelper.setSubject("Munchies");
            messageHelper.setText(templateEngine.process("emailTemplate", context), true);
        };

//        List<Orders> listOrders = groupOrder.getOrdersList();
//        if(!listOrders.isEmpty()) {
//            mailSender.send(messagePreparator);
//        }
        mailSender.send(messagePreparator);
    }

    @Override
    public void emailTimer(long delay, GroupOrder groupOrder) {
        System.out.println("Calling method emailTimer. Current time: " + new Date());
        Timer timer = new Timer("email",true);
        timer.schedule(new TimerTask() {

            //GroupOrder groupOrderForMail = new GroupOrder(groupOrder);
            @Override
            public void run() {
                groupOrder.setOrdersList(ordersService.getAllOrdersForGroupOrder(groupOrder));
                if(groupOrder.getOrdersList().size() != 0) {
                    Context context = new Context();
                    context.setVariable("groupOrder", groupOrder);

                    MimeMessagePreparator messagePreparator = mimeMessage -> {
                        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                        messageHelper.setTo(groupOrder.getRestaurantId().getEmail());
                        messageHelper.setSubject("Munchies");
                        messageHelper.setText(templateEngine.process("emailTemplate", context), true);
                    };

                    mailSender.send(messagePreparator);
                }
            }
        },delay);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("mailcluster.loopia.se");
        mailSender.setPort(465);

        //https://www.google.com/settings/security/lesssecureapps  -link za odobravanje slanja poruka da te google ne blokira
        //ovo je ko salje
        mailSender.setUsername("jdev@m.ingsoftware.com");
        mailSender.setPassword("WKVTYVdqGMp9MjLR");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable",true); //ovo sam novo dodala

        //MOJA EMAIL ADRESA:
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);

//
//        https://www.google.com/settings/security/lesssecureapps  -link za odobravanje slanja poruka da te google ne blokira
//        ovo je ko salje
//        mailSender.setUsername("mmapicerija@gmail.com");
//        mailSender.setPassword("mmapicerija2018");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");

        return mailSender;
    }
}
