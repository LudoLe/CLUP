public class TimeSlot{
    
    private Integer id;

    //queue tickets scheduled to enter in this time slot
    private ArrayList<Ticket> queueTickets = new ArrayList<Ticket>();
    
    //visit tickets scheduled to enter in this time slot
    private ArrayList<Ticket> visitTickets = new ArrayList<Ticket>();
    
    //tickets that are expected to exit the shop during this time slot
    private ArrayList<Ticket> expectedExitingTickets = new ArrayList<Ticket>();

    public TimeSlot(Integer id){
        this.id = id;
    }

    //getters and setters...
}