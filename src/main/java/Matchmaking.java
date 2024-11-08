import java.util.ArrayList;

public class Matchmaking {
    private ArrayList<Participant> participants;
    public Matchmaking(Participant participant1, Participant participant2){
        this.participants = new ArrayList<>();
        participants.add(participant1);
        participants.add(participant2);
    }

    public boolean isMatchmaked(Participant participant){
        for (int i = 0; i< participants.size();i++){
            if (participants.get(i).equals(participant)){
                return true;
            }
        }
        return false;
    }

}
