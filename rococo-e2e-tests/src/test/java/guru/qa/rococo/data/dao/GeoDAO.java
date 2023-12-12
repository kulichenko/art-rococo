package guru.qa.rococo.data.dao;

import guru.qa.rococo.data.entity.geo.GeoEntity;

//Паттерн DAO - Отвязать реализацию хранения данных от того как программа взаимодействует с этими данными.
public interface GeoDAO {

    GeoEntity getRandomCountry(int start, int end);

}
