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

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public static boolean greaterThan(Date date1, Date date2) {
        boolean result = false;
        if (date1.getYear() == date2.getYear()) {
            if (date1.getMonth() == date2.getMonth()) {
                result = date1.getDay() > date2.getDay();
            } else {
                result = date1.getMonth() > date2.getMonth();
            }
        } else {
            result = date1.getYear() > date2.getYear();
        }
        return result;
    }

    public static boolean LowerThan(Date date1, Date date2) {
        return !greaterThan(date1, date2) && !date1.equals(date2);
    }

}
