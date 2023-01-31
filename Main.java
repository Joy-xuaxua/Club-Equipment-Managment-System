import java.util.*;
import java.io.*;
//System.out.println();

public class Main {
    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
        try {
            System.out.print("Please input the file pathname: ");
            Scanner in = new Scanner(System.in);
            String filePathName = in.nextLine();
            Scanner inFile = new Scanner(new File(filePathName));
            while (inFile.hasNextLine()) {
                String line = inFile.nextLine();
                if (line.equals(""))
                    continue;
                System.out.print("\n> ");
                System.out.println(line);
                String cmdParts[] = line.split(" ");
                String cmdName = cmdParts[0];

                if (cmdName.equals("startNewDay")) {
                    (new CmdstartNewDay()).execute(cmdParts);
                } else if (cmdName.equals("register"))
                    new CmdRegister().execute(cmdParts);
                else if (cmdName.equals("listMembers"))
                    Club.getInstance().listClubMembers();
                else if(cmdName.equals("listMemberStatus"))
                    Club.getInstance().listMemeberStatus();


                else if(cmdName.equals("create"))
                    (new CmdCreateEquipSet()).execute(cmdParts);
                else if(cmdName.equals("listEquipment"))
                    Club.getInstance().listEquipment();
                else if(cmdName.equals("listEquipmentStatus"))
                    Club.getInstance().listEquipmentStatus();
                else if(cmdName.equals("arrive"))
                    (new CmdArrive()).execute(cmdParts);
                else if(cmdName.equals("borrow"))
                    (new CmdBorrow()).execute(cmdParts);
                else if(cmdName.equals("request"))
                    (new CmdRequest()).execute(cmdParts);


                else if (cmdName.equals("undo"))
                    RecordedCommand.undoCommand();
                else if (cmdName.equals("redo"))
                    RecordedCommand.redoCommand();

                else {
                    throw new ExUnknownCMD();
                }
            }
        } catch (ExUnknownCMD e) {
            System.out.println(e.getMessage());
        }

    }
}
