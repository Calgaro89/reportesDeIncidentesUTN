package Managers;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
public class SendMail {
        public static void enviarCorreo(String destinatario, String nombreCliente, String nombreTecnico) {

            //ABRIR PUERTO 587 EN EL ROUTER PARA QUE FUNCIONE
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.office365.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.office365.com");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("incidentestp@hotmail.com", "trabajofinal123");
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("incidentestp@hotmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
                message.setSubject("Incidente Resuelto");
                message.setText("Estimado/a " + nombreCliente + ".\n\n Nos comunicamos con el agrado de informarle que el incidente reportado ya fue resulto " + ".\n\n Atentamente: " + nombreTecnico);

                Transport.send(message);
                System.out.println("Correo electronico enviado con exito");

            } catch (MessagingException e) {
                System.out.println("Correo no enviado");
                e.printStackTrace();
            }
        }
    }

