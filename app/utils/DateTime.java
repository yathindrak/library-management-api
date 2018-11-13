package utils;

public class DateTime {
    private int year; // holds given year
    private int month; // holds given month
    private int day; // holds given day

    /**
     * Constructor of DateTime
     * @param year - year
     * @param month -  month
     * @param day - day
     */
    public DateTime(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Getter of the year
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Getter of the month
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Getter of the day
     * @return day
     */
    public int getDay() {
        return day;
    }

    /**
     * compare given 2 days
     * @param date - DateTime object
     * @return true when current DateTime is earlier than the date param
     */
    public boolean compareTo(DateTime date) {
        int tempDay = date.getDay();
        int tempMonth = date.getMonth();
        int tempYear = date.getYear();
        return (this.year <= tempYear) && (this.month <= tempMonth) && (this.day <= tempDay);
    }

    /**
     * convert to string Date Format(ISO 8601)
     * @return current date as a string
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        //year
        stringBuilder.append(String.valueOf(this.year));
        stringBuilder.append("-");
        // month
        if (this.month < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(String.valueOf(this.month));
        stringBuilder.append("-");
        // day
        if (this.day < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(String.valueOf(this.day));
        return stringBuilder.toString();
    }

    /**
     * Check whether a given date is valid or not
     * @param year- year
     * @param month - month
     * @param day - day
     * @return true the date is valid
     */
    public static boolean isValid(int year, int month, int day) {
        /*
        * Check for valid ranges*/

        // check year
        if (year < 0) {
            return false;
        }

        // check month
        if ((month < 1) || (month > 12)) {
            return false;
        }

        // check day
        if ((day < 1) || (day > 31)) {
            return false;
        }

        /*
        * Check for all months*/
        switch (month) {
            // january
            case 1: return true;
            // february
            case 2: return (isLeapYear(year) ? day <= 29 : day <= 28);
            // march
            case 3: return true;
            // april
            case 4: return day < 31;
            // may
            case 5: return true;
            // june
            case 6: return day < 31;
            // july
            case 7: return true;
            // august
            case 8: return true;
            // september
            case 9: return day < 31;
            // october
            case 10: return true;
            // november
            case 11: return day < 31;
            // december | default
            default: return true;
        }
    }

    /**
     * Check whether a given year is a leap year or not
     * @param year - year
     * @return true if the year is a leap year
     */
    public static boolean isLeapYear(int year) {
        // not divisible by 4
        if (year % 4 != 0) {
            return false;
        }
        // divisible by 400
        else if (year % 400 == 0) {
            return true;
        }
        // divisible by 100
        else if (year % 100 == 0) {
            return false;
        }
        // otherwise return true
        else {
            return true;
        }
    }
}
