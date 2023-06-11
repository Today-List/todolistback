package net.skhu.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {
    private final JavaMailSender emailsender; // Bean 등록해둔 MailConfig 를 emailsender 라는 이름으로 autowired
    private String authNum; // 인증번호

    //랜덤 인증 코드 생성
    public void createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i=0;i<8;i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 :
                    key.append((char) (random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) (random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        authNum = key.toString();
    }

    // 메일 양식 작성
    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
        createCode();
        String setFrom = "fr35wo@naver.com";
        String title = "회원가입 인증 번호";

        MimeMessage message = emailsender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, to); // 보내는 대상
        message.setSubject(title);
        message.setFrom(new InternetAddress(setFrom, "Your Name", "UTF-8"));

        String htmlContent = "<div style='margin:100px;'>";
        htmlContent += "<h1>안녕하세요 TodoRack입니다.</h1>";
        htmlContent += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요.</p>";
        htmlContent += "<br>";
        htmlContent += "<div align='center' style='border:1px solid black; font-family:verdana'>";
        htmlContent += "<h3 style='color:#9AC1D1;'>회원가입 인증 코드</h3>";
        htmlContent += "<div style='font-size:130%'>";
        htmlContent += "CODE : <strong>" + authNum + "</strong><div><br/>";
        htmlContent += "</div>";

        message.setContent(htmlContent, "text/html; charset=utf-8");

        return message;
    }

    // 메일 발송
    public String sendSimpleMessage(String to) throws Exception{
        MimeMessage message = createMessage(to);
        try{
            emailsender.send((message));
        } catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return authNum;
    }
}
