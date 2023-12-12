package guru.qa.rococo.data.repository.painting;

import guru.qa.rococo.data.dao.PaintingDAO;
import guru.qa.rococo.data.entity.painting.PaintingEntity;

public abstract class AbstractPaintingRepository implements PaintingRepository {


    private final PaintingDAO paintingDAO;

    protected AbstractPaintingRepository(PaintingDAO paintingDAO) {
        this.paintingDAO = paintingDAO;
    }


    public int addPainting(PaintingEntity paintingEntity) {
        paintingDAO.addPainting(paintingEntity);
        return 0;
    }

    public PaintingEntity findByName(PaintingEntity paintingEntity) {
        return paintingDAO.findByName(paintingEntity);
    }

    public PaintingEntity findById(PaintingEntity paintingEntity) {
        return paintingDAO.findById(paintingEntity);
    }
}
