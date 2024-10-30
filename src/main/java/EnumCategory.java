public class EnumCategory {
    private enum categories {
        SCORED_POINTS, ASSIST_POINTS, WON_GAMES, WON_TOURNAMENTS, MONEY_GENERATED
    }

    public static String getCategory(String category){
        String categoryS = null;
        if (category.equalsIgnoreCase("Scored points")){
            categoryS = categories.SCORED_POINTS.name();
        } else if (category.equalsIgnoreCase("Assist points")) {
            categoryS = categories.ASSIST_POINTS.name();
        } else if (category.equalsIgnoreCase("Won games")) {
            categoryS = categories.WON_GAMES.name();
        } else if (category.equalsIgnoreCase("Won tournaments")) {
            categoryS = categories.WON_TOURNAMENTS.name();
        } else if (category.equalsIgnoreCase("Money generated")) {
            categoryS = categories.MONEY_GENERATED.name();
        }
        return categoryS;
    }
}
