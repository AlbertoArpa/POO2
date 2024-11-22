package upm.etsisi.poo.model;

public class Stat {
    private static final String ATTR_CATEGORY_NAME = "category";
    private static final String ATTR_VALUE_NAME = "value";
    private String category;
    private double value;

    public Stat(String category) {
        this.category = Categories.getCategory(category);
        this.value = 0.0;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = Categories.getCategory(category);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getCategory() + ": " + getValue();
    }
}