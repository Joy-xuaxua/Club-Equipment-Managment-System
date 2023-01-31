public class ExHandling {
}
class ExUnknownCMD extends Exception {
    public ExUnknownCMD(){
        super("Unknown command - ignored.");
    }
    public ExUnknownCMD(String msg){
        super(msg);
    }
}
class ExInsufficientCMD extends Exception {
    public ExInsufficientCMD(){
        super("Insufficient command arguments.");
    }
    public ExInsufficientCMD(String msg){
        super(msg);
    }
}
class ExInvalidDate extends Exception{
    public ExInvalidDate(){super("Invalid date.");}
    public ExInvalidDate(String msg){super(msg);}

}