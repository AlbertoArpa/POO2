import java.util.ArrayList;
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

    public List<T> getParticipants() {
        return participants;
    }

    public List<List<T>> getMatchmaking() {
        return matchmaking;
    }

    public ArrayList<T> randomizeParticipants() {
        ArrayList<T> result = new ArrayList<>();
        for (int i = 0; i < participants.size(); i++) {

        }
        return result;
    }
}