package com.techit.withus.web.users.service;

import com.techit.withus.redis.hashes.Email;
import com.techit.withus.redis.repository.EmailRepository;
import com.techit.withus.web.users.domain.dto.EmailDTO;
import com.techit.withus.web.users.repository.UserRepository;
import io.netty.util.internal.ThreadLocalRandom;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.random.RandomGenerator;

@Service
@RequiredArgsConstructor
public class EmailService
{
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     * 1) 입력 받은 이메일이 이미 인증이 완료된 회원의 이메일과 중복되는지 확인한다.
     * -> 상세: 이미 인증이 완료된 회원이라 함은 role이 ROLE_USER인 회원을 말한다.
     * 2) 중복되지 않는다면 난수를 생성하여 입력 받은 이메일로 전송하고, Redis에도 이메일을 키 값, 난수를 밸류 값으로 저장한다.
     * -> 상세: 사용자가 난수를 입력했을 때 입력 받은 이메일을 키 값으로 밸류를 꺼내서 확인한다.
     * 3) 인증에 성공하면 users의 email을 업데이트 하고, role을 'ROLE_INVALIDATE_USER'에서 'ROLE_USER'으로 변경한다.
     */

    // 이메일을 보낸다.
    public void sendEmail(EmailDTO emailDTO) throws MessagingException
    {
        String address = emailDTO.getAddress();

        this.checkDuplicateEmail(address);

        // 이미 해당 이메일로 보낸 적이 있으면 이전 기록을 지운다.
        if (emailRepository.existsById(address))
            emailRepository.deleteById(address);

        MimeMessage email = this.createEmail(address);
        javaMailSender.send(email);
    }

    // 이메일의 내용을 정의하고, 내용을 Redis에 저장한다.
    private MimeMessage createEmail(String address) throws MessagingException {
        // 난수 8자리인 인증 번호를 만든다.
        String code = this.createCode();

        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, address);
        message.setSubject("WITH US 인증번호");
        message.setFrom(sender);
        message.setText(code);

        // 이메일과 코드는 Redis에 저장한다.
        this.saveEmailInRedis(address, code);

        return message;
    }

    // 입력 받은 인증번호가 해당 이메일에 발급된 인증번호인지 확인한다.
    public boolean validateCode(EmailDTO emailDTO)
    {
        Optional<Email> optionalEntity
                = emailRepository.findById(emailDTO.getAddress());
        if (optionalEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else {
            String savedCodeInRedis = optionalEntity.get().getCode();
            if (!savedCodeInRedis.equals(emailDTO.getCode()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            return true;
        }
    }

    // 8자리의 인증 번호를 만든다.
    private String createCode()
    {
        RandomGenerator randomGenerator = ThreadLocalRandom.current();
        return String.valueOf(randomGenerator.nextLong(10000000L, 99999999L));
    }

    // 이메일과 인증 번호를 Redis에 저장한다.
    private void saveEmailInRedis(String address, String code)
    {
        Email entity = new Email(address, code);
        emailRepository.save(entity);
    }

    // 입력 받은 이메일이 이미 인증이 완료된 회원의 이메일과 중복되는지 확인한다.
    private boolean checkDuplicateEmail(String email)
    {
        if (userRepository.existsByEmail(email))
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        return false;
    }
}
