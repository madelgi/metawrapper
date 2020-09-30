package com.docone.metawrapper.resource;

import com.codahale.metrics.annotation.Timed;
import com.docone.metawrapper.dao.MRConsoDao;
import com.docone.metawrapper.model.TreeNode;
import com.docone.metawrapper.service.CPTTreeService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("cpt")
@Produces(MediaType.APPLICATION_JSON)
public class CPTResource {


    private CPTTreeService cptTreeService;

    public CPTResource(MRConsoDao mrConsoDao) throws Exception {
        this.cptTreeService = new CPTTreeService(mrConsoDao);
    }

    @GET
    @Timed
    @Path("children")
    public Response getChildren(@QueryParam("code") String code) {
        if (code == null || code.trim().isEmpty()) {
            return Response.serverError().entity("query cannot be blank").build();
        }

        List<TreeNode> children = cptTreeService.getAllChildrenFromCode(code);
        return Response.ok(children, MediaType.APPLICATION_JSON).build();
    }
}
