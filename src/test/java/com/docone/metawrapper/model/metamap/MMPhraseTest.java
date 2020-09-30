package com.docone.metawrapper.model.metamap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MMPhraseTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testSerialize() throws JsonProcessingException {
        String expectedJson =
                "{" +
                    "\"mincoMan\":\"min commit result\"," +
                    "\"text\":\"phrase text\"," +
                    "\"position\":{\"x\":3,\"y\":4}" +
                "}";

        MMPosition position = new MMPosition();
        position.setX(3);
        position.setY(4);

        MMPhrase phrase = new MMPhrase();
        phrase.setMincoMan("min commit result");
        phrase.setText("phrase text");
        phrase.setPosition(position);

        String serialized = MAPPER.writeValueAsString(phrase);

        assertEquals(expectedJson, serialized);
    }

    @Test
    public void testDeserialize() throws IOException {
        String expectedJson =
                "{" +
                    "\"mincoMan\":\"min commit result\"," +
                    "\"text\":\"phrase text\"," +
                    "\"position\":{\"x\":3,\"y\":4}" +
                "}";

        MMPhrase phrase = MAPPER.readValue(expectedJson, MMPhrase.class);

        assertEquals("min commit result", phrase.getMincoMan());
        assertEquals("phrase text", phrase.getText());
        assertEquals(3, phrase.getPosition().getX());
        assertEquals(4, phrase.getPosition().getY());
    }
}
