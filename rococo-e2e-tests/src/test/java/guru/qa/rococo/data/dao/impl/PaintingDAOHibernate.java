package guru.qa.rococo.data.dao.impl;

import guru.qa.rococo.data.DataBase;
import guru.qa.rococo.data.dao.PaintingDAO;
import guru.qa.rococo.data.entity.painting.PaintingEntity;
import guru.qa.rococo.data.jpa.EntityManagerFactoryProvider;
import guru.qa.rococo.data.jpa.JpaService;

import java.util.UUID;

public class PaintingDAOHibernate extends JpaService implements PaintingDAO {
    public PaintingDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(DataBase.PICTURES).createEntityManager());
    }

    @Override
    public int addPainting(PaintingEntity paintingEntity) {
        persist(paintingEntity);
        return 0;
    }

    @Override
    public PaintingEntity findByName(PaintingEntity paintingEntity) {
        String title = paintingEntity.getTitle();
        return em.createQuery("SELECT p FROM PaintingEntity p WHERE p.title=:title", PaintingEntity.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    @Override
    public PaintingEntity findById(PaintingEntity paintingEntity) {
        UUID id = paintingEntity.getId();
        return em.createQuery("SELECT p FROM PaintingEntity p WHERE p.id=:id", PaintingEntity.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
