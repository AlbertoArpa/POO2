import java.util.ArrayList;

public class TournamentsController<T> {
    private ArrayList<Tournament> tournaments;

    public TournamentsController() {
        this.tournaments = new ArrayList<>();
    }

    private Tournament getTournament(String name) {
        for (Tournament tournament : tournaments) {
            if (tournament.getName().equals(name)) {
                return tournament;
            }
        }
        return null;
    }

    public boolean createTournament(String name, Date startDate, Date endDate, String league, String sport, String categoryRank) {
        boolean result = false;
        if (getTournament(name) == null) {
            tournaments.add(new Tournament<>(name, startDate, endDate, league, sport, categoryRank));
            result = true;
        }
        return result;
    }

    public boolean deleteTournament(String name) {
        boolean result = false;
        Date now = new Date();
        if (getTournament(name) != null && ((now.lowerThan(getTournament(name).getStartDate()) && now.lowerThan(getTournament(name).getEndDate())) || (now.greaterThan(getTournament(name).getStartDate()) && now.greaterThan(getTournament(name).getEndDate())))) {
            tournaments.remove(getTournament(name));
            result = true;
        }
        return result;
    }

    public boolean isParticipating(Team team) {  // GENERICOS
        boolean result = false;
            return result;
    }

}
