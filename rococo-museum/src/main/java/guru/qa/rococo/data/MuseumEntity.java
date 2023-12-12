package guru.qa.rococo.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Table(name = "museum")
@Entity
public class MuseumEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    UUID id;

    @Column(nullable = false)
    String title;

    @Column(length = 2000)
    String description;

    @Column
    byte[] photo;

    @Column(nullable = false)
    String city;

    @Column(name = "country_id", nullable = false)
    UUID countryId;

}
