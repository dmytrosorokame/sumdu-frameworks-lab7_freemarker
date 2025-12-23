package sumdu.edu.ua.web.service;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import sumdu.edu.ua.core.domain.Book;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender mailSender;
    private final EmailTemplateProcessor templateProcessor;

    public MailService(JavaMailSender mailSender,
                       EmailTemplateProcessor templateProcessor) {
        this.mailSender = mailSender;
        this.templateProcessor = templateProcessor;
    }

    public void sendNewBookEmail(Book book) {
        try {
            logger.info("Preparing email for book: {} by {}", book.getTitle(), book.getAuthor());

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            
            Map<String, Object> model = new HashMap<>();
            model.put("title", book.getTitle());
            model.put("author", book.getAuthor());
            model.put("year", book.getPubYear());
            model.put("added", now.format(formatter));

            String html = templateProcessor.process("new_book.ftl", model);
            logger.debug("Email template processed successfully");

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);

            helper.setTo("YOUR_EMAIL@gmail.com");
            helper.setSubject("New Book in Catalog");
            helper.setText(html, true);
            helper.setFrom("bookapp@erlkonig.sumdu.edu.ua");

            logger.info("Sending email");
            mailSender.send(message);
            logger.info("Email sent successfully");

        } catch (Exception e) {
            logger.error("Failed to send email for book: {} by {}", book.getTitle(), book.getAuthor(), e);
            throw new RuntimeException("Email sending failed: " + e.getMessage(), e);
        }
    }

}

