package upm.etsisi.poo.controller;

import upm.etsisi.poo.model.Matchmaking;
import upm.etsisi.poo.model.Participant;

import java.util.ArrayList;

public class MatchmakingController {
    private ArrayList<Matchmaking> matchmaking;

    public MatchmakingController(){
        this.matchmaking = new ArrayList<>();
    }

    public boolean createMatchmake(Participant participant1, Participant participant2) {
        if (!isMatchmaked(participant1)&&!isMatchmaked(participant2)){
            Matchmaking matchmaking1 = new Matchmaking(participant1, participant2);
            matchmaking.add(matchmaking1);
            return true;
        }
        return false;
    }

    public boolean randomMatchmake(ArrayList<Participant> participants) {
        boolean result = false;
        if (participants.size() % 2 == 0 && matchmaking.size()!=participants.size()/2) {
            for (int i = 0; i < participants.size(); i++) {
                if (!isMatchmaked(participants.get(i))) {
                    if (i+1< participants.size() && !isMatchmaked(participants.get(i+1))){
                        Matchmaking matchmaking1 = new Matchmaking(participants.get(i), participants.get(i+1));
                        matchmaking.add(matchmaking1);
                        i++;
                    }
                }
            }
            result = true;
            for (int i = 0; i < participants.size(); i++) {
                if(!isMatchmaked(participants.get(i))){
                    result = false;
                }
            }
        }
        return result;
    }

    public boolean isMatchmaked(Participant participant) {
        for (int i = 0; i < matchmaking.size(); i++) {
            if (matchmaking.get(i).isMatchmaked(participant)){
                return true;
            }
        }
        return false;
    }

    public void removeMatchmaking(Participant participant) {
        int i = 0;
        while (i < matchmaking.size()) {
            if (matchmaking.get(i).isMatchmaked(participant)) {
                matchmaking.remove(i);
            }
            i++;
        }
    }
}
