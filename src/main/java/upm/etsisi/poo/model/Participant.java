package upm.etsisi.poo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public interface Participant {
    @Id
    String getName();
    Stat getStat(String category);
}
