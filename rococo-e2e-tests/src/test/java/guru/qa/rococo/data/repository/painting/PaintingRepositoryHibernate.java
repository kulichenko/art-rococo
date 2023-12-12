package guru.qa.rococo.data.repository.painting;

import guru.qa.rococo.data.dao.impl.PaintingDAOHibernate;

public class PaintingRepositoryHibernate extends AbstractPaintingRepository {

    public PaintingRepositoryHibernate() {
        super(new PaintingDAOHibernate());
    }
}
