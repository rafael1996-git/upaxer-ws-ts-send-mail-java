package com.upaxer.serverless.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.upaxer.serverless.App;
import com.upaxer.serverless.models.domain.Request;
import com.upaxer.serverless.models.domain.RequestData;
import com.upaxer.serverless.models.domain.SQSRequest;
import com.upaxer.serverless.models.local.Correo;
import com.upaxer.serverless.models.local.Record;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrigenService {
	
	private static final Logger logging = LoggerFactory.getLogger(OrigenService.class);

    EmailService emailService = new EmailService();
    private static AmazonS3 amazonS3Client = null;

    String key="Template01.txt";
    String bucket="dev.upaxer.templates";


    public void sendMail(RequestData record) throws Exception {
        try {

        	initializeAmazons3Client();
        	logging.info("TO: " + record.getTo());
        	logging.info("Subject: " + record.getSubject());
        	logging.info("Body: " + record.getBody());

        	

        	
        	
            S3Object xFile = amazonS3Client.getObject(bucket, key);
            System.out.println("xFile: " + xFile);
            InputStream inputStream = new BufferedInputStream(xFile.getObjectContent());
            System.out.println("inputStream: " + inputStream);
            BufferedReader csvReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            System.out.println("csvReader: " + csvReader);
            String s3File = csvReader.toString();
            System.out.println("s3File: " + s3File);

            emailService.enviarCorreo(record.getTo(), record.getSubject(), record.getBody());

            System.out.println("Finalizo el envió de correos.");
        }catch(Exception e) {
        	 logging.error("ERROR: ",e.getCause());
        	 logging.error("ERROR: ",e);
            System.out.println("Error en servicio de correos");
        }
    }

    public void getDataFromS3(List<Record> records) throws Exception {
        try {
            for (Record record : records) {
                if (!record.getEventName().equalsIgnoreCase("ObjectCreated:Put")) {
                    System.out.println("Evento no válido: " + record.getEventName());
                    return;
                }
                bucket = record.getS3().getBucket().getName();
                System.out.println("bucket = " + bucket);
                key = URLDecoder.decode(record.getS3().getObjectData().getKey(), "utf-8");
                System.out.println("key = " + key);
                String [] ruta = key.split("/");
                String nombreArchivo = ruta[ruta.length-1];
                Correo correo = llenarCorreo(nombreArchivo);
                String property = System.getenv("CuentasEnvio");
                String [] correos = property.split(",");
                for (String para:correos) {
                    emailService.enviarCorreo(para,correo.getAsunto(),correo.getCuerpo());
                }
            }
            System.out.println("Finalizo el envió de correos.");
        }catch(Exception e) {
        	 logging.error("ERROR: ",e);
            System.out.println("Error en servicio de correos");
        }
    }

    private Correo llenarCorreo(String archivo) {
        Correo correo = new Correo();
        correo.setAsunto("Recepción del documento " + archivo);
        correo.setCuerpo("Buen día, \n" +
                "Se les notifica la recepción del documento " + archivo + "\n" +
                "¡Excelente día!");
        return correo;
    }
    
    private void initializeAmazons3Client() throws Exception {

		if (amazonS3Client == null) {
			amazonS3Client = AmazonS3ClientBuilder.standard().build();
		}
	}
}
