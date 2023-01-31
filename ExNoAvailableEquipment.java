public class ExNoAvailableEquipment extends Exception{
    public ExNoAvailableEquipment() {
        super("There is no available set of this equipment for the command.");
    }
}
