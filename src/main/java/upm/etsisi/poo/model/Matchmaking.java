package upm.etsisi.poo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;
@Entity
@Table(name = "matchmaking")
public class Matchmaking {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "matchmaking")
    private ArrayList<Participant> participants;

    public Matchmaking(Participant participant1, Participant participant2) {
        this.participants = new ArrayList<>();
        participants.add(participant1);
        participants.add(participant2);
    }

    public boolean isMatchmaked(Participant participant) {
        for (int i = 0; i < participants.size(); i++) {
            if (participants.get(i).equals(participant)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return participants.get(0) + " vs " + participants.get(1);
    }
}
