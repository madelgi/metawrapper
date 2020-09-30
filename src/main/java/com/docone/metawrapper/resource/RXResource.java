package com.docone.metawrapper.resource;

import com.codahale.metrics.annotation.Timed;
import com.docone.metawrapper.dao.MRConsoDao;
import com.docone.metawrapper.model.TreeNode;
import com.docone.metawrapper.model.umls.RXData;
import com.docone.metawrapper.service.RXService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;


@Path("/rx")
@Produces(MediaType.APPLICATION_JSON)
public class RXResource {

    private final RXService rxService;

    private static final Logger LOGGER = Logger.getLogger(RXResource.class.getName());

    public RXResource(MRConsoDao mrConsoDao) {
        this.rxService = new RXService(mrConsoDao);
    }

    @GET
    @Timed
    @Path("condition_to_drug")
    public Response getRxFromCondition(@QueryParam("condition") String condition,
                                       @DefaultValue("STR") @QueryParam("inputType") String inputType,
                                       @DefaultValue("MSH") @QueryParam("source") String source) {
        if (condition == null || condition.trim().isEmpty()) {
            return Response.serverError().entity("condition cannot be empty").build();
        }

        List<RXData> drugs;
        try {
            drugs = rxService.getRxFromCondition(condition, inputType, source);
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
            return Response.serverError().entity(ex.getMessage()).build();
        }

        return Response.ok(drugs, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Timed
    @Path("drug_to_condition")
    public Response getConditionFromRX(@QueryParam("drug") String drug,
                                       @DefaultValue("string") @QueryParam("inputType") String inputType,
                                       @DefaultValue("RXNORM") @QueryParam("source") String source) throws Exception {
        if (drug == null || drug.trim().isEmpty()) {
            return Response.serverError().entity("drug cannot be empty").build();
        }

        List<TreeNode> conditions;
        try {
            conditions = rxService.getConditionFromRX(drug, inputType, source);
        } catch (Exception ex) {
            return Response.serverError().entity("Unrecognized inputType \"" + inputType + "\".").build();
        }

        return Response.ok(conditions, MediaType.APPLICATION_JSON).build();
    }
}
