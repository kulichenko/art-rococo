package guru.qa.rococo.data.jpa;

import guru.qa.rococo.config.Config;
import guru.qa.rococo.data.DataBase;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public enum EntityManagerFactoryProvider {
    INSTANCE;
    private static final Config CFG = Config.getInstance();
    private final Map<DataBase, EntityManagerFactory> dataSourceStore = new HashMap<>();

    public synchronized EntityManagerFactory getDataSource(DataBase db) {
        if (dataSourceStore.get(db) == null) {
            Map<String, String> props = new HashMap<>();
//            props.put("hibernate.connection.url", db.getUrl());
            props.put("hibernate.connection.url", db.getUrlForP6Spy());
            props.put("hibernate.connection.user", CFG.dataBaseUser());
            props.put("hibernate.connection.password", CFG.dataBasePassword());
//            props.put("hibernate.connection.driver_class", "org.postgresql.Driver");
            props.put("hibernate.connection.driver_class", "com.p6spy.engine.spy.P6SpyDriver");
            props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

            this.dataSourceStore.put(
                    db, new ThreadLocalEntityManagerFactory(
                            Persistence.createEntityManagerFactory("rococo", props)
                    )

            );
        }
        return dataSourceStore.get(db);
    }

    public Collection<EntityManagerFactory> allStoredEntityManagerFactories() {
        return dataSourceStore.values();
    }

}
