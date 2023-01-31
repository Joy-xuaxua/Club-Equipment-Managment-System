import java.util.*;
public class Member implements Comparable<Member>{
    private String id;
    private String name;
    private Day joinDate;
    private int borrowedNum;
    private List<Equipment> borrowedEquips;
    private List<Request> requests;

    public Member(String id, String name) throws CloneNotSupportedException {
        this.id = id;
        this.name = name;
        this.joinDate=SystemDate.getInstance().clone();
        borrowedEquips = new ArrayList<>();
        Club.getInstance().addMember(this);
        requests = new ArrayList<>();
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void borrow(Equipment equip) {
        borrowedEquips.add(equip);
        Collections.sort(borrowedEquips);
        borrowedNum++;
    }


    public void checkBorrow(EquipmentSet set, Day s, Day e) throws ExMemberHasBorrowedEquip, ExEquipmentSetCannotRequestOrBorrow {
        for(Equipment eq: borrowedEquips){
            if(set.getCode().equals(eq.getCode()))
                if(eq.getEndDay().compareTo(SystemDate.getInstance())==1)
                    throw new ExMemberHasBorrowedEquip();
        }
        for(Request req: requests){
            if(req.getEquipmentName().equals(set.getName())&&
                !Day.donotOverlap(req.getStartDay(),req.getEndDay(),s,e))
                throw new ExEquipmentSetCannotRequestOrBorrow("The period overlaps with a current period that the member requests the equipment.");
        }
    }

    public void unBorrow(Equipment equip) {
        borrowedEquips.remove(equip);
        borrowedNum--;
    }

    public void request(Request r) throws ExEquipmentSetCannotRequestOrBorrow {
        for(Equipment e: borrowedEquips){
            if(!e.compatiblewith(r))
                    throw new ExEquipmentSetCannotRequestOrBorrow("The period overlaps with a current period that the member borrows / requests the equipment.");
        }
        for(Request req: requests){
            if(!req.compatiblewith(r))
                throw new ExEquipmentSetCannotRequestOrBorrow("The period overlaps with a current period that the member borrows / requests the equipment.");
        }
        requests.add(r);
        Collections.sort(requests);
    }

    public void unrequest(Request r){
        requests.remove(r);
    }


    @Override
    public String toString() {
        //Learn: "-" means left-aligned
        return String.format("%-5s%-9s%11s%7d%13d", id, name, joinDate, borrowedEquips.size(),
                requests.size());
    }

    public void listStatus() {
        System.out.printf("[%s %s]\n",id,name);
        if(borrowedEquips.size()==0 &&
            requests.size()==0){
            System.out.println("No record.");
            return;
        }

        for(Equipment e:borrowedEquips){
            System.out.println(e.borrowedStringforMemberStatus());
        }
        for(Request r: requests){
            System.out.println(r.stringforMemberStatus());
        }

    }

    public static String getListingHeader() {
        return String.format("%-5s%-9s%11s%11s%13s", "ID", "Name", "Join Date ", "#Borrowed", "#Requested");
    }

    @Override
    public int compareTo(Member anotherMember){
        return this.id.compareTo(anotherMember.id);
    }



}