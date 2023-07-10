package app.web.utb.alarm.infrastructure.email;

import app.web.utb.alarm.application.service.AlarmEmailNotifier;
import app.web.utb.chargingSystem.domain.ChargingSystem;
import app.web.utb.chargingSystem.domain.ChargingSystemRepository;
import app.web.utb.notifiaction.domain.NotificationAddress;
import app.web.utb.notifiaction.domain.NotificationAddressRepository;
import app.web.utb.notifiaction.domain.NotificationAddressSnapshot;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Profile({"production"})
@AllArgsConstructor
@Slf4j
class AlarmEmailNotifierImpl implements AlarmEmailNotifier {
    private static final String SOURCE_ADDRESS = "system-utb@bispol.pl";
    private final JavaMailSender javaMailSender;
    private final ChargingSystemRepository chargingSystemRepository;
    private final NotificationAddressRepository notificationAddressRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public void notifyAboutNewAlarm(long chargingSystemId, int chargerPosition) {
        ChargingSystem chargingSystem = chargingSystemRepository.findByIdOrThrowException(chargingSystemId);
        String messageContent = "<h2>System UTB - alarm</h2>\n" +
                "Wykryto alarm: <h3><i>" +
                " System: " + chargingSystem.getSnapshot().getName() +
                "\n Prostownik: " + chargerPosition +
                "</i></h3>\n" +
                messageFooter();
        notificationAddressRepository.findByChargingSystemId(chargingSystemId)
                .stream()
                .map(NotificationAddress::getSnapshot)
                .map(NotificationAddressSnapshot::getEmail)
                .forEach(email -> sendMsgTo(email, String.format("%s - ALARM", chargingSystem.getSnapshot().getName()), messageContent));
    }


    private void sendMsgTo(String email, String subject, String content) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(SOURCE_ADDRESS);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);

            sendAsync(mimeMessage, email);
        } catch (MessagingException e) {
            log.error("Sending email msg to {} with subject {} failed", email, subject);
        }
    }

    private void sendAsync(MimeMessage mimeMessage, String email) {
        CompletableFuture.runAsync(() -> {
            javaMailSender.send(mimeMessage);
            log.info("Email sent to: {}", email);
        }, executorService);
    }

    private String messageFooter() {
        return "------------------------------------------------------------------------------------------------------------------------------------------------------------<br>" +
                "<i>Wiadomość wygenerowana przez system UTB - proszę nie odpisywać";
    }
}
