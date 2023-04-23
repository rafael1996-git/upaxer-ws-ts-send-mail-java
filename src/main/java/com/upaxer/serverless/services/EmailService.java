package com.upaxer.serverless.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class EmailService {
	private static final Logger logging = LoggerFactory.getLogger(EmailService.class);

    static final String FROM = System.getenv("CuentaRemitente");;
    public void enviarCorreo(String para, String asunto, String cuerpo) {
    	logging.info("Email sent!"+para+" : "+asunto+" "+cuerpo);
    	logging.info("Email FROM! "+FROM);
        try {
        	logging.info("SEND EMAIL entro al try! "+FROM);

            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            .withRegion(Regions.US_EAST_1).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(para))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(cuerpo)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(asunto)))
                    .withSource(FROM);
            client.sendEmail(request);
            logging.info("Email sent!");
        } catch (Exception ex) {
        	logging.error("ERROR: ",ex);
            System.out.println("The email was not sent. Error message: "
                    + ex.getMessage());
        }
    }
}
