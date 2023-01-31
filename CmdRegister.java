public class CmdRegister extends RecordedCommand {
    private Member member;
    private Club club = Club.getInstance();

    @Override
    public void execute(String cmdParts[]) throws CloneNotSupportedException{
        try{
            if (cmdParts.length < 3)
                throw new ExInsufficientCMD();
            if(club.checkMemberExist(cmdParts[1])){
                Member m = club.findMember(cmdParts[1]);
                throw new ExMemberIDUsed("Member ID already in use: "+m.getId()+" "+m.getName());
            }
            member = new Member(cmdParts[1], cmdParts[2]);
            System.out.println("Done.");
            clearRedoList();
            addOneUndoCommamd(this);
        }catch(ExInsufficientCMD | ExMemberIDUsed | ExMemberNotFound e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        club.removeMember(member);
        addOneRedoCommand(this);
    }

    @Override
    public void redoMe() {
        club.addMember(member);
        addOneUndoCommamd(this);
    }
}
