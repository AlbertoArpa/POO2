package upm.etsisi.poo.model;

import jakarta.persistence.*;
import upm.etsisi.poo.controller.MatchmakingController;

import java.util.ArrayList;
@Entity
@Table(name = "matchmakings")
public class Matchmaking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_name")
    private Tournament tournament;

    private ArrayList<Participant> participants;

    public Matchmaking(){

    }

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

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @Override
    public String toString() {
        return participants.get(0) + " vs " + participants.get(1);
    }
}
