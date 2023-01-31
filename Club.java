import java.util.ArrayList;
import java.util.Collections; //Provides sorting
public class Club {
    private ArrayList<Member> allMembers;
    private ArrayList<EquipmentSet> equipSets;

    private static Club instance = new Club();

    private Club() { allMembers = new ArrayList<>(); equipSets = new ArrayList<>(); }

    public static Club getInstance() { return instance; }

    public void addMember(Member member) {// See how it is called in Member constructor (Step 3)
        allMembers.add(member);
        Collections.sort(allMembers); // allMembers
    }

    public Member findMember(String id) throws ExMemberNotFound{
        for(Member m: allMembers){
            if(m.getId().equals(id))
                return m;
        }
        throw new ExMemberNotFound();
    }
    public boolean checkMemberExist(String id) {
        for(Member m: allMembers){
            if(m.getId().equals(id))
                return true;
        }
        return false;
    }


    public void removeMember(Member member){
        allMembers.remove(member);
    }

    public void listClubMembers() {
        System.out.println(Member.getListingHeader()); // Member
        for (Member m: allMembers)
            System.out.println(m); // m or m.toString()
    }

    public void listMemeberStatus() {
        for (Member m: allMembers){
            m.listStatus();
            System.out.println();
        }
    }

    public void addEquipSet(EquipmentSet equipSet) {
        equipSets.add(equipSet);
    }

    public void removeEquipSet(EquipmentSet equipSet){
        equipSets.remove(equipSet);
    }
    public boolean checkEquipSetExist(String code){
        for(EquipmentSet set: equipSets){
            if(set.getCode().equals(code))
                return true;
        }
        return false;
    }

    public EquipmentSet findEquipSet(String code) throws ExEquipNotExist{
        for(EquipmentSet set: equipSets){
            if(set.getCode().equals(code))
                return set;
        }
        throw new ExEquipNotExist(("Missing record for Equipment "+code+"."+
                "  Cannot mark this item arrival."));
    }

    public void listEquipment(){
        System.out.println(String.format("%-5s%-17s%6s", "Code", "Name", "#sets"));
        for(EquipmentSet set: equipSets){
            System.out.println(set.statusString());
        }
    }

    public void listEquipmentStatus(){
        for(EquipmentSet set: equipSets){
            set.listStatus();
            System.out.println();
        }
    }




}
