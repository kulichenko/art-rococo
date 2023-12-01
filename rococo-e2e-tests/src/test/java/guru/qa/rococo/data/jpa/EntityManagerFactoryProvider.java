package guru.qa.rococo.data.jpa;

import guru.qa.rococo.config.Config;
import guru.qa.rococo.data.DataBase;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum EntityManagerFactoryProvider {
    INSTANCE;
    private static final Config CFG = Config.getInstance();
    private final Map<DataBase, EntityManagerFactory> dataSourceStore = new ConcurrentHashMap<>();

    public EntityManagerFactory getDataSource(DataBase db) {
        return dataSourceStore.computeIfAbsent(db, key -> {
            Map<String, Object> props = new HashMap<>();
            props.put("hibernate.connection.url", db.getUrl());
            props.put("hibernate.connection.user", CFG.dataBaseUser());
            props.put("hibernate.connection.password", CFG.dataBasePassword());
            props.put("hibernate.connection.driver_class", "org.postgresql.Driver");
            props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

            EntityManagerFactory entityManagerFactory =
                    new ThreadLocalEntityManagerFactory(
                            Persistence.createEntityManagerFactory("rococo", props)
                    );
            return entityManagerFactory;
        });
    }

    public Collection<EntityManagerFactory> allStoredEntityManagerFactories() {
        return dataSourceStore.values();
    }

}
