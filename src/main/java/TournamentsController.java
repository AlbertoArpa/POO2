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
        if (getTournament(name) != null && (now.lowerThan(getTournament(name).getStartDate()) || now.greaterThan(getTournament(name).getEndDate()))) {
            tournaments.remove(getTournament(name));
            result = true;
        }
        return result;
    }

    public boolean isParticipating(Team team) {  // GENERICOS
        boolean result = false;
            return result;
    }

    private boolean deletePastTournaments() {
        boolean result = false;
        for (int i = 0; i < tournaments.size(); i++) {
            if (tournaments.get(i).getEndDate().lowerThan(new Date())) {
                tournaments.remove(i);
                i--;
                result = true;
            }
        }
        return result;
    }

    public String listTournaments(String type) {
        StringBuilder result = new StringBuilder();
        if (type == null) {
            ArrayList<Tournament> tournamentsAux = new ArrayList<>(), randomTournaments = new ArrayList<>();
            for (int i = 0; i < tournaments.size(); i++) tournamentsAux.add(tournaments.get(i));
            for (int i = 0; i < tournaments.size(); i++) {
                int random = (int) (Math.random() * tournamentsAux.size());
                randomTournaments.add(tournamentsAux.get(random));
                randomTournaments.get(random).randomizeParticipants();
                tournamentsAux.remove(random);
                result.append("\nNOMBRE: ").append(randomTournaments.get(i).getName())
                        .append("\nFECHA: ").append(randomTournaments.get(i).getStartDate()).append(" - ").append(randomTournaments.get(i).getEndDate())
                        .append("\nLIGA: ").append(randomTournaments.get(i).getLeague())
                        .append("\nDEPORTE: ").append(randomTournaments.get(i).getSport())
                        .append("\nCATEGORIA DE ORDEN: ").append(randomTournaments.get(i).getCategoryRank()).append("\n");
                for (int j = 0; j < randomTournaments.get(i).getParticipants().size(); j++) {
                    result.append("\t- ").append(randomTournaments.get(i).getParticipants()).append("\n");
                }
            }

        } else {
            if (type.equals("ADMIN")) {
                if (deletePastTournaments()) result.append("\nTORNEOS PASADOS BORRADOS.\n");
                else result.append("\nNO HAY TORNEOS PASADOS PARA BORRAR.\n");
            }
        }
        return result.toString();
    }
}
