package upm.etsisi.poo.model;


public interface Participant {
    void addMatch(Matchmaking matchmaking);
    void addTournament(Tournament tournament);
    String getName();
    Stat getStat(String category);
}
