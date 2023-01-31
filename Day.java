
public class Day implements Cloneable, Comparable<Day> {

    private int year;
    private int month;
    private int day;

    private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";

    //Constructor
    public Day(int y, int m, int d) {
        this.year = y;
        this.month = m;
        this.day = d;
    }

    public Day(String sDay){
        set(sDay);
    } //Constructor, simply calls set(sDay)

    public void set(String sDay) { //Set year,month,day based on a string like 01-Mar-2021
            String[] sDayParts = sDay.split("-");
            this.year = Integer.parseInt(sDayParts[2]);
            this.day = Integer.parseInt(sDayParts[0]);
            this.month = MonthNames.indexOf(sDayParts[1]) / 3 + 1;
    }
    // check if a given year is a leap year
    static public boolean isLeapYear(int y) {
        if (y % 400 == 0)
            return true;
        else if (y % 100 == 0)
            return false;
        else if (y % 4 == 0)
            return true;
        else
            return false;
    }

    // check if y,m,d valid
    static public boolean valid(int y, int m, int d) {
        if (m < 1 || m > 12 || d < 1) return false;
        switch (m) {
            case 1: case 3: case 5: case 7:
            case 8: case 10: case 12:
                return d <= 31;
            case 4: case 6: case 9: case 11:
                return d <= 30;
            case 2:
                if (isLeapYear(y))
                    return d <= 29;
                else
                    return d <= 28;
        }
        return false;
    }

    public static boolean valid(String sDay){
        try {
            String[] sDayParts = sDay.split("-");
            int year = Integer.parseInt(sDayParts[2]);
            int day = Integer.parseInt(sDayParts[0]);
            if(!MonthNames.contains(sDayParts[1]))
                throw new ExInvalidDate();
            int month = MonthNames.indexOf(sDayParts[1]) / 3 + 1;
            return valid(year,month,day);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | ExInvalidDate e ){
            return false;
        }

    }

    public Day getNextDay(){
        if(valid(year,month,day+1)){
            return new Day(year, month, day+1);
        }
        else if (valid(year, month+1,1)){
            return new Day(year, month+1,1);
        }
        else{
            return new Day(year+1, 1, 1);
        }
    }

    public Day plusDays(int plus){//only consider small day overflow
        Day result= null;
        for (int i=0;i<plus;i++){
            result=this.getNextDay();
        }
        return result;
    }


    @Override
    public String toString() {
        return day + "-" + MonthNames.substring((month - 1) * 3, month * 3) + "-" + year; // (month-1)*3,month*3
    }

    @Override
    public Day clone() throws CloneNotSupportedException {
        Day clone = (Day) super.clone();
        return clone;
    }

    public static boolean donotOverlap(Day start, Day end, Day s, Day e){
        return (start.compareTo(e)==1||end.compareTo(s)==-1);
    }

    @Override
    public int compareTo(Day anotherDay) {//if larger or later than anotherday, return 1
        if (anotherDay.year > this.year)
            return -1;
        else if (anotherDay.year == this.year &&
                anotherDay.month > this.month)
            return -1;
        else if (anotherDay.year == this.year &&
                anotherDay.month == this.month &&
                anotherDay.day > this.day)
            return -1;
        else if (anotherDay.year == this.year &&
                anotherDay.month == this.month &&
                anotherDay.day == this.day)
            return 0;
        else
            return 1;
    }

    public static int compareTwoDays(String s1, String s2) {
        Day d1 = new Day(s1);
        Day d2 = new Day(s2);
        return d1.compareTo(d2);
    }


}
