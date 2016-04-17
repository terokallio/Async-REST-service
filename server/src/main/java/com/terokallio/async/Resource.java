package com.terokallio.async;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.concurrent.Executor;
import org.glassfish.jersey.server.ManagedAsync;


/**
 * Asynchronous resource
 *
 * @author tero.kallio
 * @since 14/04/16.
 */
@Path("/")
public class Resource {
    @Inject
    private Executor executor;

    private Service service = new Service();

    @GET
    @ManagedAsync
    public void asyncGet(@Suspended final AsyncResponse asyncResponse) {
        executor.execute(() -> {
            String result = service.veryExpensiveOperation();
            asyncResponse.resume(result);
        });
    }
}

