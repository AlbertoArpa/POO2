import java.util.Calendar;

public class Date {
    private int year;
    private int month;
    private int day;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(String date) {
        String[] original = date.split("/");
        day = Integer.parseInt(original[0]);
        month = Integer.parseInt(original[1]);
        year = Integer.parseInt(original[2]);
    }

    /**
     * CONSTRUYE UNA FECHA CON LA FECHA DE HOY
     */
    public Date() {
        year = java.time.LocalDate.now().getYear();
        month = java.time.LocalDateTime.now().getMonthValue();
        day = java.time.LocalDate.now().getDayOfMonth();
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public boolean greaterThan(Date date) {
        boolean result = false;
        if (year == date.getYear()) {
            if (month == date.getMonth()) {
                result = day > date.getDay();
            } else {
                result = month > date.getMonth();
            }
        } else {
            result = year > date.getYear();
        }
        return result;
    }

    public boolean LowerThan(Date date) {
        return greaterThan(date) && !equals(date);
    }

}
