public class CmdCreateEquipSet extends RecordedCommand{
    private Club club = Club.getInstance();
    private EquipmentSet equipSet;
    @Override
    public void execute(String cmdParts[]){
        try{
            if(cmdParts.length<3)
                throw new ExInsufficientCMD();
            if(club.checkEquipSetExist(cmdParts[1]))
                throw new ExEquipCodeUsed("Equipment code already in use: "+club.findEquipSet(cmdParts[1]));
            equipSet = new EquipmentSet(cmdParts[1], cmdParts[2]);
            club.addEquipSet(equipSet);
            addOneUndoCommamd(this);
            clearRedoList();
            System.out.println("Done.");
        }catch(ExInsufficientCMD | ExEquipCodeUsed | ExEquipNotExist e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe(){
        club.removeEquipSet(equipSet);
        addOneRedoCommand(this);
    }

    @Override
    public void redoMe(){
        club.addEquipSet(equipSet);
        addOneUndoCommamd(this);
    }
}
