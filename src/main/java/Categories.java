public class Categories {
    private enum categories {
        SCORED_POINTS, ASSIST_POINTS, WON_GAMES, WON_TOURNAMENTS, MONEY_GENERATED
    }

    public static String getCategory(String category){
        String categoryS = null;
        category = category.toUpperCase();
        if(category.contains(" ")) {
            String[] arg = category.split(" ");
            category = arg[0] + "_" + arg[1];
        }
        for (categories categoryA : categories.values()) {
            if (categoryA.name().equals(category)){
                categoryS = category;
            }
        }
        return categoryS;
    }

    public static String[] getCategories(){
        String[] categoryS = new String[categories.values().length];
        for (categories categoryA : categories.values()) {
            categoryS[categoryA.ordinal()] = categoryA.name();
        }
        return categoryS;
    }

}
