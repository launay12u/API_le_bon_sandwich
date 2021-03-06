package provider;

import java.io.IOException;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;


/**
 * Created by debian on 14/02/17.
 */

@Provider
public class CORSResponseFilter  implements ContainerResponseFilter {


    private final static Logger log = Logger.getLogger(CORSResponseFilter.class.getName());
    @Override
    public void filter(ContainerRequestContext requestCtx, ContainerResponseContext responseCtx) throws IOException {


        log.info("Execution du filtre Response");

        responseCtx.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseCtx.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseCtx.getHeaders().add("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT");
        responseCtx.getHeaders().add("Access-Control-Allow-Headers", "content-type,Authorization");
    }
}
