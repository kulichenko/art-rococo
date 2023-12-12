package guru.qa.rococo.data.dao;

import guru.qa.rococo.data.entity.museum.MuseumEntity;

public interface MuseumDAO {

    int addMuseum(MuseumEntity museumEntity);

    MuseumEntity findByName(MuseumEntity museumEntity);

    MuseumEntity findById(MuseumEntity museumEntity);
}
