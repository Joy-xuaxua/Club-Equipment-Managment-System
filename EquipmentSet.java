import java.util.*;
public class EquipmentSet {
    private String name;
    private String code;
    private int borrowedNum;
    private List<Equipment> equips;
    private List<Request> requests;

    public EquipmentSet(String code, String name){
        this.name = name;
        this.code = code;
        equips = new ArrayList<>();
        requests = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public String getName(){return name;}
    @Override
    public String toString(){
        return code+" "+name;
    }

    public String statusString(){
        return String.format("%-5s%-17s%4d %s",
                code, name,equips.size(), genBorrowedString());
    }

    public String genBorrowedString(){
        String result = "";
        if(borrowedNum!=0){

            for(int i=0;i<borrowedNum;i++){
                result+=equips.get(i).toString();
                if(i!=borrowedNum-1)
                    result+=", ";
            }
            result = String.format("(Borrowed set(s): %s)", result);
        }
        return result;
    }
    //(Borrowed set(s): E1_1(001), E1_2(002))


    public void addEquip() {
        int num = equips.size()+1;
        equips.add(new Equipment(code, name, num));
    }

    public void removeEquip() {
        equips.remove(equips.size()-1);
    }

    public void checkAvaliablity() throws ExNoAvailableEquipment{
        if(borrowedNum>=equips.size()){
            throw new ExNoAvailableEquipment();
        }
    }

    public Equipment BorrowAndgetBorrowedEquip(){
        Equipment borrowed = equips.get(borrowedNum);
        borrowedNum++;
        return borrowed;
    }

    public Equipment getRequestedEquip(Day start, Day end) throws ExEquipmentSetCannotRequestOrBorrow {
        for(Equipment e: equips){
            if(e.canbeRequested(start,end)){
                return e;
            }
        }
        throw new ExEquipmentSetCannotRequestOrBorrow(
                "There is no available set of this equipment for the command.");
    }

    public void request(Request r){
        requests.add(r);
    }

    public void unrequest(Request r){
        requests.remove(r);
    }

    public void unBorrow() {
        borrowedNum--;
    }

//    public boolean compatiblewithBorrowing(Request request){
//        for(Request r:requests){
//            if(!r.compatiblewithBorrowing(request))
//                return false;
//        }
//        return true;
//    }

    public void listStatus(){
        System.out.printf("[%s %s]\n",code,name);
        if(equips.size()==0){
            System.out.println("  We do not have any sets " +
                    "for this equipment.");
            return;
        }
        for(Equipment e: equips){
            e.listStatus();
        }
    }
}
