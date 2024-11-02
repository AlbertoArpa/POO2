import java.util.ArrayList;

public class TournamentsController<T> {
    private ArrayList<Tournament> tournaments;

    public TournamentsController() {
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
        if (getTournament(name) == null) {
            if (startDate.greaterThan(new Date()) && endDate.greaterThan(startDate)){
                if (EnumCategory.getCategory(categoryRank)!=null){
                    tournaments.add(new Tournament(name, startDate, endDate, league, sport, categoryRank));
                    result = true;
                }
            }
        }
        return result;
    }

    public boolean deleteTournament(String name) {
        boolean result = false;
        Date now = new Date();
        if (getTournament(name) != null) {
            tournaments.remove(getTournament(name));
            result = true;
        }
        return result;
    }

    private boolean deletePastTournaments() {
        boolean result = false;
        for (int i = tournaments.size()-1; i >= 0; i--) {
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
                ArrayList<T> randomizedParticipants = randomTournaments.get(random).getRandomizedParticipants();
                tournamentsAux.remove(random);
                result.append("\nNOMBRE: ").append(randomTournaments.get(i).getName())
                        .append("\nFECHA: ").append(randomTournaments.get(i).getStartDate()).append(" - ").append(randomTournaments.get(i).getEndDate())
                        .append("\nLIGA: ").append(randomTournaments.get(i).getLeague())
                        .append("\nDEPORTE: ").append(randomTournaments.get(i).getSport())
                        .append("\nCATEGORIA DE ORDEN: ").append(randomTournaments.get(i).getCategoryRank()).append("\n");
                for (int j = 0; j < randomizedParticipants.size(); j++) {
                    result.append("\t- ").append(randomTournaments.get(i).getParticipants()).append("\n");
                }
            }
        } else {
            if (type.equals("ADMIN")) {
                if (deletePastTournaments()) result.append("\nTORNEOS PASADOS BORRADOS.\n");
                else result.append("\nNO HAY TORNEOS PASADOS PARA BORRAR.\n");
            }
            for (int i = 0; i < tournaments.size(); i++) {
                ArrayList<T> rankedParticipants = tournaments.get(i).getParticipantsRanked();
                for (int j = 0; j < rankedParticipants.size(); j++) {
                    if (rankedParticipants.get(j) instanceof Player) result.append("\n\t\tNOMBRE: ").append(((Player) rankedParticipants.get(j)).getName());
                    if (rankedParticipants.get(j) instanceof Team) result.append("\n\t\tNOMBRE: ").append(((Team) rankedParticipants.get(j)).getName());
                    if (rankedParticipants.get(j) instanceof Player) result.append("\n\t\tNOMBRE: ").append(((Player) rankedParticipants.get(j)).getName());
                    if (rankedParticipants.get(j) instanceof Team) result.append("\n\t\t: ").append(((Team) rankedParticipants.get(j)).getName());
                }
            }
        }
        return result.toString();
    }
}
