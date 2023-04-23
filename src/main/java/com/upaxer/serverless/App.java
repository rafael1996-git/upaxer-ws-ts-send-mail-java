package com.upaxer.serverless;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.upaxer.serverless.models.base.BaseApp;
import com.upaxer.serverless.models.domain.Request;
import com.upaxer.serverless.models.domain.RequestData;
import com.upaxer.serverless.models.domain.SQSRequest;
import com.upaxer.serverless.services.OrigenService;

public class App extends BaseApp {
	private static final Logger logging = LoggerFactory.getLogger(App.class);

    OrigenService origenService = new OrigenService();
    @Override
    protected void init() {
        this.origenService = new OrigenService();
    }

    @Override
    protected Object preHandler(Object request) {
        return request;
    }

    @Override
    public Object handleRequest(Object request) {
        try {
            Gson gson = new Gson();
            System.out.println("event json --> " + request.toString());

            SQSRequest dato= gson.fromJson(request.toString(), SQSRequest.class);
            System.out.println("request json 3 dato--> " + dato.getRecords().get(0).getBody());
            dato.getRecords().forEach(record -> {
              try {
            	  Request body= gson.fromJson(dato.getRecords().get(0).getBody(), Request.class);
            	  RequestData parse = new RequestData(body.getTo(), body.getSubject(), body.getBody());
                  System.out.println("event parse --> " + parse);
            	  this.origenService.sendMail(parse);
              } catch (Exception e) {
            	  logging.error("ERROR: ",e);
              }
            });
            
        }
        catch (Exception e) {
        	 logging.error("ERROR: ",e);
            return "Error en servicio de correo.";
        }
        System.out.println("SQSRequest ----> "+request);
        return "succes";
    }

    @Override
    protected Object postHandler(Object response) {
        return response;
    }

    @Override
    protected APIGatewayProxyResponseEvent handleRequestProxy(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent) {
        System.out.println(apiGatewayProxyRequestEvent.toString());
        return null;
    }
 

public static void main(String[] args) {
	App obj=new App();
	Object data="{\r\n"
			+ "  \"Records\": [\r\n"
			+ "    {\r\n"
			+ "      \"messageId\": \"3e7b6a98-93a8-4265-9a80-195f18d7be8a\",\r\n"
			+ "      \"receiptHandle\": \"AQEB8XF0A54WeQXnrAX1t/ZVohoULT+JAluLgSXb1i9wi7IYYZC/FhjZrM/4UuylQNYKWZvVjvOc26mgeEH76C6Y7w2jiUm96A9lG2nQh3dtp/gP6pksaOl3OvEFtaowZj6NUKvFu5ghuAtUHXzfhHA5JNkL8hOUrNd2m33+UfUXn+OAMPC3WbdFOO2UK987MfQw0Y9P0GlgLLLIVMuYYDByLcswoWvK0XQR9Or4H4lmwC7VUQxQDogVbPMF+nTgC3j0xu/qoy/h1V+fNINVLzx50mE0yQ4W6VYWr2g/tSGnoMlaluYWCkT8TI8WxsoT7x+2r5ksDsreaE/e5l2rco4hxJVyeeqc76nBSUyNjaDJN2LDlJnZ8PMV64QD/JIe6AEe651yx5Qtf6WyGrpxk0BL8w==\",\r\n"
			+ "      \"body\": {\r\n"
			+ "        \"to\": \"nestor_presiado@hotmail.com\",\r\n"
			+ "        \"subject\": \"TEST\",\r\n"
			+ "        \"body\": \"Mensaje de prueba\"\r\n"
			+ "      },\r\n"
			+ "      \"attributes\": {\r\n"
			+ "        \"ApproximateReceiveCount\": 1,\r\n"
			+ "        \"SentTimestamp\": 1674945138568,\r\n"
			+ "        \"SenderId\": \"AIDARAIHZP7Z7YSMMIPS4\",\r\n"
			+ "        \"ApproximateFirstReceiveTimestamp\": 1674945138616\r\n"
			+ "      },\r\n"
			+ "      \"messageAttributes\": {\r\n"
			+ "\r\n"
			+ "      },\r\n"
			+ "      \"md5OfBody\": \"5bf35c671df97639bf407872d31fb373\",\r\n"
			+ "      \"eventSource\": \"aws:sqs\",\r\n"
			+ "      \"eventSourceARN\": \"arn:aws:sqs:us-east-1:069272698867:upaxer-ws-ts-send-mail\",\r\n"
			+ "      \"awsRegion\": \"us-east-1\"\r\n"
			+ "    }\r\n"
			+ "  ]\r\n"
			+ "}";
	obj.handleRequest(data);
	
	System.out.println(obj);
}
	
	


}

