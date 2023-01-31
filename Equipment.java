import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Equipment implements Comparable<Equipment> {
    private String code;
    private int id;
    private String name;
    private Member m;
    private Day start;
    private Day end;
    private List<Request> requests;

    public Equipment(String c, String n, int i){
        code = c;
        name = n;
        id = i;
        requests = new ArrayList<>();
        m=null;
    }
    public void borrow(Member m, Day s, Day e)  {
        try{
            this.m = m;
            start = s.clone();
            end = e.clone();
        }catch(CloneNotSupportedException ex){
            ex.printStackTrace();
        }
    }

    public void unBorrow() {
        m=null;start=null;end=null;
    }

    public boolean compatiblewith(Request r){//to test whether a member can request this equipment
        Equipment e = r.getEquipment();
        if(this.getName().equals(e.getName())){
            if(Day.donotOverlap(start,end,r.getStartDay(),r.getEndDay()))
                return true;
            else
                return false;
        }
        else
            return true;
    }

    public boolean canbeRequested(Day s, Day e) {//Cannot throw ExEquipmentSetCannotRequestOrBorrow
        if(start == null && end == null) {
            //if this equipment hasnot been borrowed.
        }
        else{
            if(!Day.donotOverlap(s,e,start,end))
                return false;
        }
        for(Request r: requests){
            if(!Day.donotOverlap(r.getStartDay(),r.getEndDay(),s,e))
                return false;
        }
        return true;
    }

    public void request(Request r){
        requests.add(r);
        Collections.sort(requests);
    }

    public void unrequest(Request r){
        requests.remove(r);
    }

    public String getCode(){
        return code;
    }

    public int getID(){
        return id;
    }

    public String getCodeandID(){
        return String.format("%s_%d",code,id);
    }

    public String getName(){
        return name;
    }

    public Day getEndDay(){return end;}

    @Override
    public int compareTo(Equipment another){
        return this.start.compareTo(another.start);
    }


    @Override
    public String toString(){
        return String.format("%s_%d(%s)",code,id,m.getId());
    }

    public String borrowedStringforMemberStatus(){
        //- borrows E1_1 (3D_Scanner) for 3-Jan-2022 to 6-Jan-2022
        return String.format("- borrows %s (%s) for %s to %s",
                getCodeandID(),name,start,end);
    }
    public void listStatus(){
        System.out.println("  "+getCodeandID());
        System.out.print("    Current status: ");
        if(m==null)
            System.out.println("Available");
        else{
            System.out.println(String.format("%s %s borrows for %s to %s",
                    m.getId(),m.getName(),start,end));
        }
        if(requests.size()==0)
            return;
        System.out.print("    Requested period(s): ");
        boolean first = true;
        for(int i=0;i<requests.size();i++){
            Request r=requests.get(i);
            if(!first)
                System.out.print(", ");
            else{ first = false;}
            System.out.printf("%s to %s",r.getStartDay(),r.getEndDay());
        }
        System.out.println();
    }



}
