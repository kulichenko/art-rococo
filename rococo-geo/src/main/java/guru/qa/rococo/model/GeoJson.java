package guru.qa.rococo.model;


import guru.qa.rococo.data.GeoEntity;

import java.util.UUID;

public record GeoJson(UUID id, String name) {
    public static GeoJson fromEntity(GeoEntity entity) {
        return new GeoJson(entity.getId(), entity.getName());
    }
}
