package guru.qa.rococo.data.entity.painting;

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
@Entity
@Table(name = "pictures")
public class PaintingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    UUID id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false, length = 2000)
    String description;

    @Column(nullable = false)
    byte[] content;

    @Column(name = "museum_id", nullable = false)
    UUID museumId;

    @Column(name = "artist_id", nullable = false)
    UUID artistId;

}
