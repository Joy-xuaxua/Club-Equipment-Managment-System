public class ExMemberNotFound extends Exception{
    public ExMemberNotFound(){
        super("Member not found.");
    }
    public ExMemberNotFound(String msg){
        super(msg);
    }
}
