package com.docone.metawrapper.model.metamap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class MMPcmTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testSerialize() throws JsonProcessingException {
        String expectedJson =
            "{" +
                "\"candidates\":[" +
                    "{" +
                        "\"score\":-1000," +
                        "\"conceptId\":\"cid\"," +
                        "\"conceptName\":\"Lung cancer\"," +
                        "\"preferredName\":\"lung Cancer\"," +
                        "\"matchedWords\":[\"term1\",\"term2\"]," +
                        "\"semanticTypes\":[\"type1\",\"type2\"]," +
                        "\"head\":true," +
                        "\"overmatch\":false," +
                        "\"sources\":[\"CPT\",\"MSH\"]," +
                        "\"positions\":[{\"x\":3,\"y\":4}]" +
                    "}" +
                "]," +
                "\"phrase\":{" +
                    "\"mincoMan\":\"min commit result\"," +
                    "\"text\":\"phrase text\"," +
                    "\"position\":{\"x\":3,\"y\":4}" +
                "}" +
            "}";

        MMPcm mmPcm = new MMPcm();

        MMPosition position = new MMPosition();
        position.setX(3);
        position.setY(4);

        MMCandidate candidate = new MMCandidate();
        candidate.setScore(-1000);
        candidate.setConceptId("cid");
        candidate.setConceptName("Lung cancer");
        candidate.setPreferredName("lung Cancer");
        candidate.setMatchedWords(Arrays.asList("term1", "term2"));
        candidate.setSemanticTypes(Arrays.asList("type1", "type2"));
        candidate.setHead(true);
        candidate.setOvermatch(false);
        candidate.setSources(Arrays.asList("CPT", "MSH"));
        candidate.setPositions(Arrays.asList(position));

        MMPhrase phrase = new MMPhrase();
        phrase.setMincoMan("min commit result");
        phrase.setText("phrase text");
        phrase.setPosition(position);

        mmPcm.setCandidates(Arrays.asList(candidate));
        mmPcm.setPhrase(phrase);

        String serialized = MAPPER.writeValueAsString(mmPcm);

        assertEquals(expectedJson, serialized);
    }

    @Test
    public void testDeserialize() throws IOException {
        String json =
            "{" +
                "\"candidates\":[" +
                    "{" +
                        "\"score\":-1000," +
                        "\"conceptId\":\"cid\"," +
                        "\"conceptName\":\"Lung cancer\"," +
                        "\"preferredName\":\"lung Cancer\"," +
                        "\"matchedWords\":[\"term1\",\"term2\"]," +
                        "\"semanticTypes\":[\"type1\",\"type2\"]," +
                        "\"head\":true," +
                        "\"overmatch\":false," +
                        "\"sources\":[\"CPT\",\"MSH\"]," +
                        "\"positions\":[{\"x\":3,\"y\":4}]" +
                    "}" +
                "]," +
                "\"phrase\":{" +
                    "\"mincoMan\":\"min commit result\"," +
                    "\"text\":\"phrase text\"," +
                    "\"position\":{\"x\":3,\"y\":4}" +
                "}" +
            "}";

        MMPcm pcm = MAPPER.readValue(json, MMPcm.class);

        MMCandidate candidate = pcm.getCandidates().get(0);
        MMPhrase phrase = pcm.getPhrase();

        assertEquals(-1000, candidate.getScore());
        assertEquals("cid", candidate.getConceptId());
        assertEquals("Lung cancer", candidate.getConceptName());
        assertEquals("lung Cancer", candidate.getPreferredName());
        assertEquals(Arrays.asList("term1", "term2"), candidate.getMatchedWords());
        assertEquals(Arrays.asList("type1", "type2"), candidate.getSemanticTypes());
        assertEquals(true, candidate.isHead());
        assertEquals(false, candidate.isOvermatch());
        assertEquals(Arrays.asList("CPT", "MSH"), candidate.getSources());
        assertEquals(3, candidate.getPositions().get(0).getX());
        assertEquals(4, candidate.getPositions().get(0).getY());

        assertEquals("min commit result", phrase.getMincoMan());
        assertEquals("phrase text", phrase.getText());
        assertEquals(3, phrase.getPosition().getX());
        assertEquals(4, phrase.getPosition().getY());
    }
}
