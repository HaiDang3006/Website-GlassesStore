package com.example.tranhaidang_pc08348_java_4_asm.utils;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class EmailUtil {

    public static void sendEmail(String recipient, String otp) {
        Properties pro = new Properties();
        pro.put("mail.smtp.auth", true);
        pro.put("mail.smtp.starttls.enable", "true");
        pro.put("mail.smtp.host", "smtp.gmail.com");
        pro.put("mail.smtp.port", "587");
        pro.put("mail.smtp.socketFactory.port", "587");
        pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        pro.put("mail.smtp.ssl.protocols", "TLSv1.2");
        String Email = "dangthpc08348@gmail.com";
        String Pass = "zlegkulzajugsxkb";

        Session session = Session.getInstance(pro,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Email, Pass);
                    }
                }
        );
        try {
            Message myMessage = new MimeMessage(session);
            myMessage.setFrom(new InternetAddress(Email));
            myMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            myMessage.setSubject("Mã OTP xác nhận đặt lại mật khẩu");
            myMessage.setText("Mã OTP của bạn là: " + otp);
            Transport.send(myMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
