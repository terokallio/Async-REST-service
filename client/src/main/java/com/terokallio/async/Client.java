package com.terokallio.async;

import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.*;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by tero.kallio on 17/04/16.
 *
 */
public class Client {

    AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();

    Future<String> future = asyncHttpClient.prepareGet("http://localhost:8080/async").execute(new AsyncHandler<String>() {
        private ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        @Override
        public State onStatusReceived(HttpResponseStatus status) throws Exception {
            int statusCode = status.getStatusCode();
            // The Status have been read
            // If you don't want to read the headers,body or stop processing the response
            if (statusCode >= 500) {
                return State.ABORT;
            }
            return State.CONTINUE;
        }

        @Override
        public State onHeadersReceived(HttpResponseHeaders h) throws Exception {
            HttpHeaders headers = h.getHeaders();
            // The headers have been read
            // If you don't want to read the body, or stop processing the response
            // return State.ABORT;
            return State.CONTINUE;
        }

        @Override
        public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
            bytes.write(bodyPart.getBodyPartBytes());
            return State.CONTINUE;
        }

        @Override
        public String onCompleted() throws Exception {
            // Will be invoked once the response has been fully read or a ResponseComplete exception
            // has been thrown.
            // NOTE: should probably use Content-Encoding from headers
            return bytes.toString("UTF-8");
        }

        @Override
        public void onThrowable(Throwable t) {
        }
    });
;

    public Client() {
        System.out.println("Async client initialized");
    }

    public String callServer() throws ExecutionException, InterruptedException {
        System.out.println("requesting asynchronously...");
        String response = future.get();
        System.out.printf(" done.");
        return response;
    }

    public String callSynchronousServer() throws ExecutionException, InterruptedException {
        System.out.println("requesting synced...");
        String response = future.get();
        System.out.printf(" done.");
        return response;
    }
}
