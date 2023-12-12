package guru.qa.rococo.data.repository.museum;

import guru.qa.rococo.data.dao.MuseumDAO;
import guru.qa.rococo.data.entity.museum.MuseumEntity;

public abstract class AbstractMuseumRepository implements MuseumRepository {
    private final MuseumDAO museumDAO;

    protected AbstractMuseumRepository(MuseumDAO museumDAO) {
        this.museumDAO = museumDAO;
    }


    public int addMuseum(MuseumEntity museumEntity) {
        museumDAO.addMuseum(museumEntity);
        return 0;
    }

    public MuseumEntity findByName(MuseumEntity museumEntity) {
        return museumDAO.findById(museumEntity);
    }

    public MuseumEntity findById(MuseumEntity museumEntity) {
        return museumDAO.findById(museumEntity);
    }

}
