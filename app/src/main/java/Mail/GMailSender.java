package Mail;
import android.content.Context;
import android.os.Environment;
import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;
import com.example.guardnet_lite_gabrovo.R;

public class GMailSender extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String user = "guardnetlite.no.reply@gmail.com";;
    private String password = "GuardNetLite0895162746";
    private Session session;
    private Context context;
    //https://myaccount.google.com/lesssecureapps
    static {
        Security.addProvider(new JSSEProvider());
    }

    public GMailSender(Context context) {

        this.context = context;
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");


        //================================
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");
        //===============================

            //    session = Session.getInstance(props, this);
      session = Session.getDefaultInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body, String recipients, String fName) throws Exception {
        try{

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.user, this.context.getResources().getString(R.string.app_name)));
            message.setSubject(subject);

            if (recipients.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            else
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
            //=========================================
            String file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), this.context.getResources().getString(R.string.app_name)).toString(); //"path of file to be attached";
            String fileName = fName;
            String FileDir = file.concat(File.separator).concat(fileName);

            MimeBodyPart attachmentBodyPart= new MimeBodyPart();
            attachmentBodyPart.setText(body);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachmentBodyPart); // add the attachement part

            if(fName!=null){
                attachmentBodyPart= new MimeBodyPart();
                DataSource source = new FileDataSource(FileDir); // ex : "C:\\test.pdf"
                attachmentBodyPart.setDataHandler(new DataHandler(source));
                attachmentBodyPart.setFileName(fileName); // ex : "test.pdf"
                multipart.addBodyPart(attachmentBodyPart); // add the attachement part
            }

            message.setContent(multipart);
            //=========================================

            Transport.send(message);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}