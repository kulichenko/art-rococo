package guru.qa.rococo.data.repository.painting;

import guru.qa.rococo.data.entity.painting.PaintingEntity;

public interface PaintingRepository {

    int addPainting(PaintingEntity paintingEntity);

    PaintingEntity findByName(PaintingEntity paintingEntity);

    PaintingEntity findById(PaintingEntity paintingEntity);

}
