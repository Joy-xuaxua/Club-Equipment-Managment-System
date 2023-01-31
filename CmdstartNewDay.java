public class CmdstartNewDay extends RecordedCommand{
    String oldSDay;
    String newSDay;
    @Override
    public void execute(String cmdParts[]){
        try {
            if(cmdParts.length<2){
                throw new ExInsufficientCMD();
            }
            if(SystemDate.getInstance() == null){
                SystemDate.createTheInstance(cmdParts[1]);
            }
            else{
                oldSDay=SystemDate.getInstance().toString();
                newSDay=cmdParts[1];
                if(!Day.valid(newSDay))
                    throw new ExInvalidDate();
                if(Day.compareTwoDays(oldSDay,newSDay)==1)
                    throw new ExInvalidDate("Invalid new day." +
                              " The new day has to be later than the current date "+oldSDay+".");
                SystemDate.setTheInstance(newSDay);
                System.out.println("Done.");
                clearRedoList();
                addOneUndoCommamd(this);
            }
        } catch (ExInsufficientCMD | ExInvalidDate e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void undoMe(){
        SystemDate.setTheInstance(oldSDay);
        addOneRedoCommand(this);
    }

    @Override
    public void redoMe(){
        SystemDate.setTheInstance(newSDay);
        addOneUndoCommamd(this);
    }
}
