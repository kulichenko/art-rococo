package guru.qa.rococo.data.repository.museum;

import guru.qa.rococo.data.dao.impl.MuseumDAOHibernate;

public class MuseumRepositoryHibernate extends AbstractMuseumRepository {

    public MuseumRepositoryHibernate() {
        super(new MuseumDAOHibernate());
    }
}
