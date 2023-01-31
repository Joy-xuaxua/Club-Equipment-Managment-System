public class Request implements Comparable<Request>{
    private Member member;
    private Equipment equip;
    private Day start;
    private Day end;

    public Request(Member m, Equipment e, Day sd, Day ed){
        member=m;
        equip = e;
        start = sd;
        end = ed;
    }

    public boolean compatiblewith(Request r){
        if(this.equip.getName().equals(r.equip.getName())){
            if(Day.donotOverlap(this.start,this.end,r.start,r.end))
                return true;
            else
                return false;
        }
        else
            return true;
    }

//    public boolean compatiblewithBorrowing(Request req){
//        if(this.member == req.member){
//            if(!Day.donotOverlap(this.start,this.end,req.start,req.end))
//                return false;
//        }
//        return true;
//    }
//
//    public boolean compatiblewithBorrowing(EquipmentSet set){
//        if(this.equip.getName().equals(set.getName())){
//            return set.compatiblewithBorrowing(this);
//        }
//        else
//            return true;
//    }


    public Equipment getEquipment(){
        return equip;
    }

    public String getEquipmentName(){
        return equip.getName();
    }

    //002 jason requests E1_1 (3D_Scanner) for 4-Feb-2022 to 14-Feb-2022
    @Override
    public String toString(){
        return String.format("%s %s requests %s (%s) for %s to %s",
                member.getId(),member.getName(),
                equip.getCodeandID(),equip.getName(),start.toString(),end.toString());
    }

    public String stringforMemberStatus(){
        return String.format("- requests %s (%s) for %s to %s",
                equip.getCodeandID(),equip.getName(),
                start,end);
    }

    public Day getStartDay() {
        return start;
    }
    public Day getEndDay() {
        return end;
    }

    @Override
    public int compareTo(Request another){
        return this.start.compareTo(another.start);
    }
}
