/*
 * Copyright 2012-2014 Andrey Grigorov, Anton Grigorov
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.cherchgk.services;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Сервис отправки электронных писем.
 *
 * @author Andrey Grigorov (peneksglazami@gmail.com)
 */
public class MailService {

    private SettingsService settingsService;

    public MailService(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    public void sendMail(String recipientEmail, String subject, String content) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", settingsService.getMailServerHostName());
        props.put("mail.smtp.port", settingsService.getMailServerPort());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.mime.charset", "UTF-8");
        Session session = Session.getInstance(props, null);

        MimeMessage msg = new MimeMessage(session);
        msg.setHeader("Content-Type", "text/html; charset=UTF-8");
        msg.setFrom(settingsService.getMailServerUser());
        msg.setRecipients(Message.RecipientType.TO, recipientEmail);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(content, "UTF-8", "html");
        Transport.send(msg, settingsService.getMailServerUser(), settingsService.getMailServerPassword());
    }

}