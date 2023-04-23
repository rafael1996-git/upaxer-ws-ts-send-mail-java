package com.upaxer.serverless.models.base;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public abstract class BaseApp implements RequestHandler<Object, Object> {
    public BaseApp() {
    }

    protected abstract void init();

    protected abstract Object preHandler(Object var1);

    protected abstract Object handleRequest(Object var1);

    protected abstract Object postHandler(Object var1);

    protected abstract APIGatewayProxyResponseEvent handleRequestProxy(APIGatewayProxyRequestEvent var1);

    public Object handleRequest(Object receivedData, Context context) {
        context.getLogger();
        this.init();
        this.preHandler(receivedData);
        Object response = this.handleRequest(receivedData);
        this.postHandler(receivedData);
        return response;
    }
}
