public class CmdBorrow extends RecordedCommand{
    private Club club = Club.getInstance();
    private EquipmentSet equipSet;
    private Equipment equip;
    private Member member;
    private Day start;
    private Day end;
    @Override
    public void execute(String cmdParts[]){
        try {
            if(cmdParts.length<3)
                throw new ExInsufficientCMD();
            String code = cmdParts[2];
            equipSet = club.findEquipSet(code);
            String id = cmdParts[1];
            member = club.findMember(id);



            start = SystemDate.getInstance();
            if(cmdParts.length==4) {
                int period = Integer.parseInt(cmdParts[3]);
                if(period<=1)
                    throw new ExWrongPeriod();
                end = SystemDate.getInstance().plusDays(period);
            }
            else
                end = SystemDate.getInstance().plusDays(7);

            equipSet.checkAvaliablity();member.checkBorrow(equipSet,start,end);
            equip = equipSet.BorrowAndgetBorrowedEquip();

            equip.borrow(member,start,end);
            member.borrow(equip);



            System.out.println(String.format("%s %s borrows %s_%d (%s) for %s to %s",
                    member.getId(),member.getName(),equip.getCode(),equip.getID(),equip.getName(),start,end));
            System.out.println("Done.");

            clearRedoList();
            addOneUndoCommamd(this);


        } catch (ExWrongPeriod|ExMemberNotFound | ExInsufficientCMD | ExMemberHasBorrowedEquip | ExNoAvailableEquipment |ExEquipmentSetCannotRequestOrBorrow e) {
            System.out.println(e.getMessage());
        } catch(ExEquipNotExist e){
            System.out.println("Equipment record not found.");
        }
    }

    @Override
    public void undoMe(){
        equipSet.unBorrow();
        equip.unBorrow();
        member.unBorrow(equip);
        addOneRedoCommand(this);
    }

    @Override
    public void redoMe(){
            equipSet.BorrowAndgetBorrowedEquip();
            equip.borrow(member,start,end);
            member.borrow(equip);
            addOneUndoCommamd(this);
    }

}
