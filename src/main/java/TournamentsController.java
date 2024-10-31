import java.util.ArrayList;

public class TournamentsController {
    private ArrayList<Tournament> tournaments;

    public TournamentsController(){
        this.tournaments = new ArrayList<>();
    }

    public Tournament existTournament(String name) {
        for (Tournament tournament : tournaments) {
            if (tournament.getName().equals(name)) {
                return tournament;
            }
        }
        return null;
    }
}
