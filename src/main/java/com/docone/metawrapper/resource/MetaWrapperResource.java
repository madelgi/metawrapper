package com.docone.metawrapper.resource;

import com.codahale.metrics.annotation.Timed;
import com.docone.metawrapper.model.metamap.MMResult;
import com.docone.metawrapper.service.MetamapService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;


@Path("/metamap")
@Produces(MediaType.APPLICATION_JSON)
public class MetaWrapperResource {

    private static final Logger LOGGER = Logger.getLogger(MetaWrapperResource.class.getName());
    private final MetamapService metamapService;

    public MetaWrapperResource(MetamapService metamapService) {
        this.metamapService = metamapService;
    }

    @GET
    @Timed
    public Response getResults(@QueryParam("query") String metamapQuery) throws Exception {
        List<MMResult> meshResults = metamapService.getResults(metamapQuery);

        return Response.ok(meshResults, MediaType.APPLICATION_JSON).build();
    }
}
