public class CmdRequest extends RecordedCommand {
    private Member member;
    private Equipment equip;
    private EquipmentSet set;
//    private Day start;
//    private Day end;
    private Request request;
    private Club club = Club.getInstance();

    @Override
    public void execute(String cmdParts[]) {
        try {
            if (cmdParts.length < 5)
                throw new ExInsufficientCMD();
            String id = cmdParts[1];
            member = club.findMember(id);
            String code = cmdParts[2];
            set = club.findEquipSet(code);

            String sDay = cmdParts[3];
            if(!Day.valid(sDay))
                throw new ExInvalidDate();
            Day start = new Day(sDay);
            int period = Integer.parseInt(cmdParts[4]);
            if(period<=1)
                throw new ExWrongPeriod();
            Day end = start.plusDays(period);
            equip = set.getRequestedEquip(start,end);

            Request r = new Request(member, equip, start, end);
            request = r;
            member.request(r);
            set.request(r);
            equip.request(r);
            System.out.println(r);
            System.out.println("Done.");
            clearRedoList();
            addOneUndoCommamd(this);
        } catch (ExEquipmentSetCannotRequestOrBorrow|ExInsufficientCMD | ExMemberNotFound |ExWrongPeriod | ExInvalidDate e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Please provide an integer for the number of days.");
        } catch(ExEquipNotExist e){
            System.out.println("Equipment record not found.");
        }
    }

    @Override
    public void undoMe(){
        member.unrequest(request);
        set.unrequest(request);
        equip.unrequest(request);
        addOneRedoCommand(this);
    }

    @Override
    public void redoMe(){
        try {
            member.request(request);
            set.request(request);
            equip.request(request);
        } catch (ExEquipmentSetCannotRequestOrBorrow e) {
            e.printStackTrace();
        }
    }

}
