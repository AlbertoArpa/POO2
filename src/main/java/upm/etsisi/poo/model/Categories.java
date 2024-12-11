package upm.etsisi.poo.model;

public enum Categories {
    SCORED_POINTS, ASSIST_POINTS, WON_GAMES, WON_TOURNAMENTS, MONEY_GENERATED;

    public static String getCategory(String category) {
        String categoryS = null;
        if (category!=null){
            category = category.toUpperCase();
            if (category.contains(" ")) {
                String[] arg = category.split(" ");
                category = arg[0] + "_" + arg[1];
            }
            for (Categories categoryA : Categories.values()) {
                if (categoryA.name().equals(category)) {
                    categoryS = category;
                }
            }
        }
        return categoryS;
    }

    public static String[] getCategories() {
        String[] categoryS = new String[Categories.values().length];
        for (Categories categoryA : Categories.values()) {
            categoryS[categoryA.ordinal()] = categoryA.name();
        }
        return categoryS;
    }

}
