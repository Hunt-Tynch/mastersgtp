package com.mastersgtp.mastersgtp;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mastersgtp.mastersgtp.entity.Dog;

public class DogPointsSerializer extends JsonSerializer<Map<Dog, Integer>> {
    @Override
    public void serialize(Map<Dog, Integer> dogPointsMap, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartArray();
        for (Map.Entry<Dog, Integer> entry : dogPointsMap.entrySet()) {
            gen.writeStartObject();
            gen.writeObjectField("dog", entry.getKey());
            gen.writeNumberField("points", entry.getValue());
            gen.writeEndObject();
        }
        gen.writeEndArray();
    }
}
