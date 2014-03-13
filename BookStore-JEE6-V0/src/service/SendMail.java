package service;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail
{
   public static void send(String mailTo, String mailFrom,String subject, String text)
   {    
      // Assuming you are sending email from localhost
      String host = "localhost";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);

      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(mailFrom));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress(mailTo));

         // Set Subject: header field
         message.setSubject(subject);

         // Now set the actual message
         message.setText(text);

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }
   }
}