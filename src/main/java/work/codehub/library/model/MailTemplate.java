package work.codehub.library.model;

import lombok.Data;

import java.io.File;
import java.util.Map;

/**
 * 邮件模板 .<br>
 *
 * @author andy.sher
 * @date 2019/10/14 10:00
 */
@Data
public class MailTemplate {

    private String to;

    private String subject;

    private String template;

    private Map<String, String> params;

    private Map<String, File> attachment;

}
