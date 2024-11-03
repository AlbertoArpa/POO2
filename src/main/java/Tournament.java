import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Tournament<T> {
    private String name;
    private Date startDate;
    private Date endDate;
    private String league;
    private String sport;
    private String categoryRank;
    private ArrayList<T> participants;
    private ArrayList<List<T>> matchmaking;

    public Tournament(String name, Date startDate, Date endDate, String league, String sport, String categoryRank) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.league = league;
        this.sport = sport;
        this.categoryRank = EnumCategory.getCategory(categoryRank);
        this.participants = new ArrayList<>();
        this.matchmaking = new ArrayList<>();
    }

    private T existT(T participant) {
        T exist = null;
        for (int i = 0; i < participants.size(); i++) {
            if (participants.get(i).equals(participant)) {
                exist = participants.get(i);
            }
        }
        return exist;
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

    public ArrayList<T> getParticipants() {
        return participants;
    }

    public ArrayList<List<T>> getMatchmaking() {
        return matchmaking;
    }

    public T getParticipant(String name) {
        T result = null;
        int i = 0;
        while (i < participants.size() && result == null) {
            if (participants.get(i) instanceof Player && ((Player) (participants.get(i))).getName().equals(name))
                result = participants.get(i);
            if (participants.get(i) instanceof Team && ((Team) (participants.get(i))).getName().equals(name))
                result = participants.get(i);
            i++;
        }
        return result;
    }

    public boolean addParticipant(T participant) {
        if (existT(participant) == null) {
            participants.add(participant);
            return true;
        } else return false;
    }

    public boolean matchmake(String participant1, String participant2) {
        boolean result = false;
        int i = 0;
        if (getParticipant(participant1) != null && getParticipant(participant2) != null) {
            while (i < participants.size() && !result) {
                if ((!(matchmaking.get(i).get(0).equals(getParticipant(participant1)) && matchmaking.get(i).get(1).equals(getParticipant(participant2)))) ||
                        (!(matchmaking.get(i).get(1).equals(getParticipant(participant1)) && matchmaking.get(i).get(0).equals(getParticipant(participant2))))) {
                    matchmaking.add(new ArrayList<T>());
                    matchmaking.get(matchmaking.size() - 1).add(getParticipant(participant1));
                    matchmaking.get(matchmaking.size() - 1).add(getParticipant(participant2));
                    result = true;
                }
            }
        }
        return result;
    }

    public boolean matchmake() {
        boolean result = false;
        if (participants.size() % 2 == 0) {
            ArrayList<T> randomized = getRandomizedParticipants();
            for (int i = 0; i < randomized.size(); i++) {
                if (!isMatchmaked(randomized.get(i))) {
                    if (matchmaking.get(matchmaking.size() - 1).size() == 2) {
                        matchmaking.add(new ArrayList<>());
                    }
                    matchmaking.get(matchmaking.size() - 1).add(randomized.get(i));
                }
            }
        }
        return result;
    }

    private boolean isMatchmaked(T participant) {
        boolean result = false;
        for (int i = 0; i < matchmaking.size(); i++) {
            result = matchmaking.get(i).get(0).equals(participant) || matchmaking.get(i).get(1).equals(participant);
        }
        return result;
    }

    public ArrayList<T> getRandomizedParticipants() {
        ArrayList<T> result = new ArrayList<>(), aux = new ArrayList<>();
        for (int i = 0; i < participants.size(); i++) aux.add(participants.get(i));
        for (int i = 0; i < participants.size(); i++) {
            int random = (int) (Math.random() * aux.size());
            result.add(aux.get(random));
        }
        return result;
    }

    public ArrayList<T> getParticipantsRanked() {
        ArrayList<T> result = participants;
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
}