import java.util.*;
public abstract class RecordedCommand implements Command {

    public abstract void undoMe();
    public abstract void redoMe();

    private static ArrayList<RecordedCommand> undoList = new ArrayList<>();
    private static ArrayList<RecordedCommand> redoList = new ArrayList<>();

    protected static void undoCommand(){
        if(undoList.size()==0)
            System.out.println("Nothing to undo.");
        else{
            undoList.get(undoList.size()-1).undoMe();
            undoList.remove(undoList.size()-1);
        }

    }

    protected static void redoCommand(){
        if(redoList.size()==0)
            System.out.println("Nothing to redo.");
        else{
            redoList.get(redoList.size()-1).redoMe();
            redoList.remove(redoList.size()-1);
        }

    }

    protected static void clearRedoList(){
        redoList.clear();
    }

    public static void addOneUndoCommamd(RecordedCommand rc){
        undoList.add(rc);
    }

    public static void addOneRedoCommand(RecordedCommand rc){
        redoList.add(rc);
    }

}
