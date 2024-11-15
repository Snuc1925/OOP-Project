package entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import entity.Entity;
import entity.EntityBuilder;

public class EntityLoader {
    public static Entity loadEntity(String entityType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(new File("entities.json"));

        JsonNode entityNode = rootNode.path("entities").path(entityType);
        EntityBuilder builder = new EntityBuilder();

        if (entityNode.has("components")) {
            JsonNode components = entityNode.get("components");

            if (components.has("PositionComponent")) {
                JsonNode positionData = components.get("PositionComponent");
                builder.withPositionComponent(
                        positionData.get("x").asInt(),
                        positionData.get("y").asInt()
                );
            }

            if (components.has("AnimationComponent")) {
                JsonNode animationData = components.get("AnimationComponent");
                builder.withAnimationComponent(
                        animationData.get("totalAnimationFrame").asInt(),
                        animationData.get("frameDuration").asInt()
                );
            }
        }

        return builder.build();
    }
}
