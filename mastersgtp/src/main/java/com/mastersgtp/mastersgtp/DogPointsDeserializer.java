package com.mastersgtp.mastersgtp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.mastersgtp.mastersgtp.entity.Dog;

public class DogPointsDeserializer extends JsonDeserializer<Map<Dog, Integer>> {

    @Override
    public Map<Dog, Integer> deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        Map<Dog, Integer> dogPointsMap = new HashMap<>();

        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        for (JsonNode entryNode : rootNode) {
            JsonNode dogNode = entryNode.get(0); // first element is the Dog
            JsonNode pointsNode = entryNode.get(1); // second element is the Integer points

            Dog dog = new Dog();
            dog.setNumber(dogNode.get("number").asInt());

            int points = pointsNode.asInt();
            dogPointsMap.put(dog, points);
        }

        return dogPointsMap;
    }
}
