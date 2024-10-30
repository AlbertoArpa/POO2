public class Stat {
    private String category;
    private double value;

    public Stat (String category){
        this.category = EnumCategory.getCategory(category);
        this.value = 0.0;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = EnumCategory.getCategory(category);
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