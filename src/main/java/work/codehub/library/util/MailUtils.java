package work.codehub.library.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import work.codehub.library.config.LibraryProperties;
import work.codehub.library.model.MailTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**
 * 邮件工具类 .<br>
 *
 * @author andy.sher
 * @date 2019/10/14 9:56
 */
@Slf4j
public class MailUtils {

    /**
     * 发送带附件邮件 .
     *
     * @param mailTemplate 邮件模板
     * @return void
     * @author andy.sher
     * @date 2019/10/14 10:03
     */
    public static void send(MailTemplate mailTemplate) {
        JavaMailSender javaMailSender = SpringContextUtils.getBean(JavaMailSender.class);
        LibraryProperties libraryProperties = SpringContextUtils.getBean(LibraryProperties.class);

        String text = MailUtils.loadTemplate(mailTemplate);

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(libraryProperties.getMailUsername());
            helper.setTo(mailTemplate.getTo());
            helper.setSubject(mailTemplate.getSubject());
            helper.setText(text, true);
            // 设置附件
            if (null != mailTemplate.getAttachment() && !mailTemplate.getAttachment().isEmpty()) {
                mailTemplate.getAttachment().forEach((k, v) -> {
                    try {
                        helper.addAttachment(k, new FileSystemResource(v));
                    } catch (MessagingException e) {
                        log.error(MailUtils.class.getName(), e);
                    }
                });
            }
            // 发送邮件
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error(MailUtils.class.getName(), e);
        }
    }

    /**
     * 加载模板内容 .
     *
     * @param mailTemplate 邮件模板
     * @return java.lang.String 模板内容
     * @author andy.sher
     * @date 2019/10/14 10:27
     */
    private static String loadTemplate(MailTemplate mailTemplate) {
        Context context = new Context();
        if (null != mailTemplate.getParams() && !mailTemplate.getParams().isEmpty()) {
            mailTemplate.getParams().forEach((k, v) -> {
                context.setVariable(k, v);
            });
        }
        String tmpPath = System.getProperty("java.io.tmpdir") + UUID.randomUUID().toString();
        String text = StringUtils.EMPTY;
        try (FileWriter writer = new FileWriter(tmpPath);) {
            TemplateEngine templateEngine = SpringContextUtils.getBean(TemplateEngine.class);
            templateEngine.process(mailTemplate.getTemplate(), context, writer);
            text = FileUtils.readFileToString(new File(tmpPath));
        } catch (IOException e) {
            log.error(MailUtils.class.getName(), e);
        }
        return text;
    }

}
