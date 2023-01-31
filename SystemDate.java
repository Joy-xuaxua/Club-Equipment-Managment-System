public class SystemDate extends Day {

    private static SystemDate instance;

    private SystemDate(String sDay) {
        super(sDay);
    }

    public static SystemDate getInstance() {
        return instance;
    }

    public static void createTheInstance(String sDay){
        if(instance==null){
            if(sDay==null){
                instance = new SystemDate("01-Jan-2021");
            }
            else{
                instance = new SystemDate(sDay);
            }
        }
        else
            System.out.println("Cannot create one more system date instance.");
    }
    public Day clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setTheInstance(String sDay){
        instance.set(sDay);
    }
}