package guru.qa.rococo.data.repository.museum;

import guru.qa.rococo.data.entity.museum.MuseumEntity;

public interface MuseumRepository {


    int addMuseum(MuseumEntity museumEntity);

    MuseumEntity findByName(MuseumEntity museumEntity);

    MuseumEntity findById(MuseumEntity museumEntity);

}
