package guru.qa.rococo.data.dao;

import guru.qa.rococo.data.entity.painting.PaintingEntity;

public interface PaintingDAO {

    int addPainting(PaintingEntity paintingEntity);

    PaintingEntity findByName(PaintingEntity paintingEntity);

    PaintingEntity findById(PaintingEntity paintingEntity);
}
