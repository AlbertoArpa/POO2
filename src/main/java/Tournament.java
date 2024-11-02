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

    public Tournament(String name, Date startDate, Date endDate, String league, String sport, String categoryRank){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.league = league;
        this.sport = sport;
        this.categoryRank = EnumCategory.getCategory(categoryRank);
        this.participants = new ArrayList<>();
        this.matchmaking = new ArrayList<>();
    }

    private T existT(T participant){
        T exist = null;
        for (int i = 0; i< participants.size(); i++){
            if (participants.get(i).equals(participant)){
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

    public boolean addParticipant(T participant){
        if (existT(participant)==null){
            participants.add(participant);
            return true;
        } else return false;
    }
    public ArrayList<T> randomizeParticipants() {
        ArrayList<T> result = new ArrayList<>();
        for (int i = 0; i < participants.size(); i++) {

        }
        return result;
    }

    public ArrayList<T> getParticipantsRanked(){
        ArrayList<T> result = participants;
        result.sort(Comparator.comparingDouble(obj ->{
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