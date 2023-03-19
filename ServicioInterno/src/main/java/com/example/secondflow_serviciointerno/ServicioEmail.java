package com.example.secondflow_serviciointerno;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class ServicioEmail {
    @Autowired
    private JavaMailSender sender;

    public boolean send(email mensaje) {

        MimeMessage email = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(email,true);
            helper.setFrom("SecondFlowApp@gmail.com");
            helper.setTo(mensaje.getDestinatario());
            helper.setSubject(mensaje.getAsunto());
            helper.setText(mensaje.getTexto(), true);

            sender.send(email);

            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

    }
}
