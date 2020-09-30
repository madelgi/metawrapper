package com.docone.metawrapper.service;

import com.docone.metawrapper.MetamapConnection;
import com.docone.metawrapper.model.metamap.MMCandidate;
import com.docone.metawrapper.model.metamap.MMPcm;
import com.docone.metawrapper.model.metamap.MMResult;
import com.docone.metawrapper.model.metamap.MMUtterance;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.Result;

import java.util.ArrayList;
import java.util.List;

public class MetamapService {

    private static final String MESH_SRC = "MSH";

    public List<MMResult> getResults(String queryString) throws Exception {
        List<MMResult> resultList = new ArrayList<>();
        MetaMapApi metamapConnection = MetamapConnection.getConnection();

        try {
            List<Result> results = metamapConnection.processCitationsFromString(String.valueOf(queryString));
            for (Result res : results) {
                resultList.add(new MMResult(res));
            }
        } finally {
            metamapConnection.disconnect();
        }

        return resultList;
    }

    public List<MMCandidate> getMeshResults(String queryString) throws Exception {

        // TODO dumb, remove logic from resource and call associated service
        List<MMResult> mmResults = getResults(queryString);
        List<MMCandidate> umlsMesh = new ArrayList<>();

        // Generate lists of valid MeSH concepts
        for (MMResult result : mmResults) {
            for (MMUtterance utterance : result.getUtterances()) {
                for (MMPcm pcm : utterance.getPcmList()) {
                    for (MMCandidate candidate : pcm.getCandidates()) {
                        List<String> sources = candidate.getSources();
                        if (!sources.contains(MESH_SRC)) {
                            continue;
                        }

                        umlsMesh.add(candidate);
                    }
                }
            }
        }

        return umlsMesh;
    }

}
