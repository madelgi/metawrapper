package com.docone.metawrapper.resource;

import com.codahale.metrics.annotation.Timed;
import com.docone.metawrapper.dao.MRConsoDao;
import com.docone.metawrapper.model.mesh.MeshResult;
import com.docone.metawrapper.model.TreeNode;
import com.docone.metawrapper.model.metamap.MMCandidate;
import com.docone.metawrapper.service.MeshTreeService;
import com.docone.metawrapper.service.MetamapService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("mesh")
public class MeshResource {

    private MetamapService metamapService;
    private MRConsoDao mrConsoDao;
    private MeshTreeService meshTreeService;

    public MeshResource(MetamapService metamapService, MRConsoDao mrConsoDao) {
        this.metamapService = metamapService;
        this.mrConsoDao = mrConsoDao;
        this.meshTreeService = new MeshTreeService(mrConsoDao);
    }

    @GET
    @Timed
    @Path("mesh_from_query")
    public Response getMeshTerms(@QueryParam("query") String query) throws Exception {
        if (query == null || query.trim().isEmpty()) {
            return Response.serverError().entity("query cannot be blank").build();
        }

        // TODO dumb, remove logic from resource and call associated service
        List<MMCandidate> umlsMesh = metamapService.getMeshResults(query);
        List<MeshResult> meshResults = new ArrayList<>();

        if (umlsMesh.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Unable to find corresponding mesh term for \"" + query + "\"").build();
        }

        // Query the MeSH API with our terms
        for (MMCandidate candidate : umlsMesh) {
            String code = mrConsoDao.getMeshFromCui(candidate.getConceptId());
            meshResults.add(new MeshResult(candidate.getScore(), code, candidate.getPreferredName()));
        }

        return Response.ok(meshResults, MediaType.APPLICATION_JSON).build();
    }


    @GET
    @Timed
    @Path("children")
    public Response getChildren(@QueryParam("code") String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            return Response.serverError().entity("query cannot be blank").build();
        }

        System.out.println(code);
        System.out.println(meshTreeService);
        List<TreeNode> children = meshTreeService.getAllChildren(code);

        return Response.ok(children, MediaType.APPLICATION_JSON).build();
    }
}
