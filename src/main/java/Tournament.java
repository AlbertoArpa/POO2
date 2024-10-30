import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tournament<T> {
    private String name;
    private Date startDate;
    private Date finalDate;
    private String league;
    private String sport;
    private String categoryRank;
    private List<T> participants;
    private List<List<T>> matchmaking;

    public Tournament(String name, Date startDate, Date finalDate, String league, String sport, String categoryRank){
        this.name = name;
        this.startDate = startDate;
        this.finalDate = finalDate;
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

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
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
}