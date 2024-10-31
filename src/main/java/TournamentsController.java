import java.util.List;

public class TournamentsController {
    private List<Tournament> tournaments;

    Tournament existTournament(String name) {
        for (Tournament tournament : tournaments) {
            if (tournament.getName().equals(name)) {
                return tournament;
            }
        }
        return null;
    }
}
