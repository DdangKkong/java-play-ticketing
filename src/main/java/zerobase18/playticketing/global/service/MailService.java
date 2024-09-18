package zerobase18.playticketing.global.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import zerobase18.playticketing.admin.entity.Admin;
import zerobase18.playticketing.admin.repository.AdminRepository;
import zerobase18.playticketing.company.entity.Company;
import zerobase18.playticketing.company.repository.CompanyRepository;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.customer.repository.CustomerRepository;
import zerobase18.playticketing.global.exception.CustomException;
import zerobase18.playticketing.troupe.entity.Troupe;
import zerobase18.playticketing.troupe.repository.TroupeRepository;

import java.util.Random;

import static zerobase18.playticketing.global.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MailService {


    private final JavaMailSender javaMailSender;

    private final RedisService redisService;

    private final CustomerRepository customerRepository;

    private final CompanyRepository companyRepository;

    private final TroupeRepository troupeRepository;

    private final AdminRepository adminRepository;

    private static final int CODE_LENGTH = 6;

    private static final Long EMAIL_TOKEN_EXPIRATION = 600000L;

    private static final String EMAIL_PREFIX = "Email-Auth: ";


    @Async
    public void sendAuthMail(String email) {

        String code = createRandomCode();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

        boolean customerExists = customerRepository.existsByEmail(email);

        boolean companyExists = companyRepository.existsByEmail(email);

        boolean adminExists = adminRepository.existsByEmail(email);

        boolean troupeExists = troupeRepository.existsByEmail(email);

        if (!customerExists && !companyExists & !adminExists & !troupeExists) {
            throw new CustomException(PRECEED_SIGNUP);
        }


        try {

            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("회원가입 이메일 인증코드입니다.");

            String msg = "<div style='margin:20px;'>"
                    + "<h1> 안녕하세요 연극의민족 입니다. </h1>"
                    + "<br>"
                    + "<p>아래 코드를 입력해주세요<p>"
                    + "<br>"
                    + "<p>감사합니다.<p>"
                    + "<br>"
                    + "<div align='center' style='border:1px solid black; font-family:verdana';>"
                    + "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>"
                    + "<div style='font-size:130%'>"
                    + "CODE : <strong>" + code + "</strong><div><br/> "
                    + "</div>";

            mimeMessageHelper.setText(msg, true);

            javaMailSender.send(mimeMessage);

            redisService.setDataExpire(EMAIL_PREFIX + email, code, EMAIL_TOKEN_EXPIRATION);

        } catch (MessagingException e) {
            throw new CustomException(INTERNAL_SERVER_ERROR);
        }

    }

    public void customerVerifyEmail(String email, String code) {

        if (!isVerify(email, code)) {
            throw new CustomException(INVALID_AUTH_CODE);
        }

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));

        customer.changeEmailAuth();
        customerRepository.save(customer);

        redisService.deleteData(EMAIL_PREFIX + email);
    }

    public void companyVerifyEmail(String email, String code) {

        if (!isVerify(email, code)) {
            throw new CustomException(INVALID_AUTH_CODE);
        }

        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));

        company.changeEmailAuth();
        companyRepository.save(company);

        redisService.deleteData(EMAIL_PREFIX + email);
    }


    public void adminVerifyEmail(String email, String code) {

        if (!isVerify(email, code)) {
            throw new CustomException(INVALID_AUTH_CODE);
        }

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));

        admin.changeEmailAuth();
        adminRepository.save(admin);

        redisService.deleteData(EMAIL_PREFIX + email);
    }


    public void troupeVerifyEmail(String email, String code) {

        if (!isVerify(email, code)) {
            throw new CustomException(INVALID_AUTH_CODE);
        }

        Troupe troupe = troupeRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));

        troupe.changeEmailAuth();
        troupeRepository.save(troupe);

        redisService.deleteData(EMAIL_PREFIX + email);
    }



    private String createRandomCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        while (sb.length() < CODE_LENGTH) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    private boolean isVerify(String email, String code) {
        String data = redisService.getData(EMAIL_PREFIX + email);


        if (data == null) {

            throw new CustomException(EMAIL_NOT_FOUND);
        }


        return data.equals(code);
    }


}
