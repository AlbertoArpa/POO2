import Controllers.MatchmakingController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Tournament {
    private static final String ATTR_NAME_NAME = "name";
    private static final String ATTR_STARTDATE_NAME = "startDate";
    private static final String ATTR_ENDDATE_NAME = "endDate";
    private static final String ATTR_LEAGUE_NAME = "league";
    private static final String ATTR_SPORT_NAME = "sport";
    private static final String ATTR_CATEGORYRANK_NAME = "categoryRank";
    private static final String ATTR_PARTICIPANTS_NAME = "participants";
    private static final String ATTR_MATCHMAKING_NAME = "matchmaking";
    private String name;
    private Date startDate;
    private Date endDate;
    private String league;
    private String sport;
    private String categoryRank;
    private ArrayList<Participant> participants;
    private MatchmakingController matchmaking;

    public Tournament(String name, Date startDate, Date endDate, String league, String sport, String categoryRank) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.league = league;
        this.sport = sport;
        this.categoryRank = Categories.getCategory(categoryRank);
        this.participants = new ArrayList<>();
        this.matchmaking = new MatchmakingController();
    }

    public Participant getParticipant(Participant participant) {
        Participant result = null;
        int i = 0;
        while (i < participants.size() && result == null) {
            if (participants.get(i).equals(participant)){
                result = participants.get(i);
            }
            i++;
        }
        return result;
    }

    public boolean addParticipant(Participant participant) {
        if (getParticipant(participant)==null) {
            participants.add(participant);
            return true;
        } else return false;
    }

    public boolean removeParticipant(Participant participant) {
        boolean result = false;
        if (getParticipant(participant)!=null) {
            if (matchmaking.isMatchmaked(participant)) {
                matchmaking.removeMatchmaking(participant);
            }
            participants.remove(participant);
            result = true;
        }
        return result;
    }

    public ArrayList<Participant> getRandomizedParticipants() {
        ArrayList<Participant> result = new ArrayList<>(), aux = new ArrayList<>();
        for (int i = 0; i < participants.size(); i++) aux.add(participants.get(i));
        for (int i = 0; i < participants.size(); i++) {
            int random = (int) (Math.random() * aux.size());
            result.add(aux.get(random));
        }
        return result;
    }

    public ArrayList<Participant> getParticipantsRanked() {
        ArrayList<Participant> result = participants;
        result.sort(Comparator.comparingDouble(obj -> {
            List<Stat> items;
            if (obj instanceof Player) {
                items = ((Player) obj).getStats();
            } else if (obj instanceof Team) {
                items = ((Team) obj).getStats();
            } else {
                return Double.MIN_VALUE; // Valor por defecto si no es una clase válida
            }

            // Buscar el Item con el nombre especificado
            return items.stream()
                    .filter(item -> item.getCategory().equals(categoryRank))
                    .map(Stat::getValue)
                    .findFirst()
                    .orElse(Double.MIN_VALUE); // Valor por defecto si no se encuentra
        }).reversed());
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getCategoryRank() {
        return categoryRank;
    }

    public void setCategoryRank(String categoryRank) {
        this.categoryRank = categoryRank;
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public MatchmakingController getMatchmaking() {
        return matchmaking;
    }
}