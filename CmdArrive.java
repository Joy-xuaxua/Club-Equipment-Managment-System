public class CmdArrive extends RecordedCommand{
    Club club = Club.getInstance();
    EquipmentSet equipSet;
    @Override
    public void execute(String cmdParts[]){
        try {
            String code = cmdParts[1];
            equipSet = club.findEquipSet(code);
            equipSet.addEquip();
            System.out.println("Done.");
            clearRedoList();
            addOneUndoCommamd(this);
        } catch (ExEquipNotExist e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe(){
        equipSet.removeEquip();
        addOneRedoCommand(this);
    }

    @Override
    public void redoMe(){
        equipSet.addEquip();
        addOneUndoCommamd(this);
    }
}
