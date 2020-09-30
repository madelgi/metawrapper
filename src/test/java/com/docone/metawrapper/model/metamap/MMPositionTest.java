package com.docone.metawrapper.model.metamap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MMPositionTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testSerialize() throws JsonProcessingException {
        String expectedJson = "{\"x\":3,\"y\":4}";

        MMPosition position = new MMPosition();
        position.setX(3);
        position.setY(4);
        String serialized = MAPPER.writeValueAsString(position);

        assertEquals(expectedJson, serialized);
    }

    @Test
    public void testDeserialize() throws IOException {
        String expectedJson = "{\"x\":3,\"y\":4}";
        MMPosition position = MAPPER.readValue(expectedJson, MMPosition.class);

        assertEquals(3, position.getX());
        assertEquals(4, position.getY());
    }
}
