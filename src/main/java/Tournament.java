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
}