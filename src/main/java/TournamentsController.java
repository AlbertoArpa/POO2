import java.util.ArrayList;
import java.util.Date;

public class TournamentsController {
    private ArrayList<Tournament> tournaments;

    public TournamentsController(){
        this.tournaments = new ArrayList<>();
    }

    public Tournament getTournament(String name) {
        for (Tournament tournament : tournaments) {
            if (tournament.getName().equals(name)) {
                return tournament;
            }
        }
        return null;
    }

    public boolean createTournament(String name, Date startDate, Date endDate, String league, String sport, String categoryRank) {
        boolean result = false;
        if (getTournament(name) != null) {
            tournaments.add(new Tournament<>(name, startDate, endDate, league, sport, categoryRank));
            result = true;
        }
        return result;
    }

}
