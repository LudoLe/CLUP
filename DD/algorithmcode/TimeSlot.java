public class TimeSlot{
    
    private Integer id;

    private ArrayList<Ticket> queueTickets = new ArrayList<Ticket>();
    
    private ArrayList<Ticket> visitTickets = new ArrayList<Ticket>();
    
    //tickets that are expected to exit the shop during this time slot
    private ArrayList<Ticket> expectedExitingTickets = new ArrayList<Ticket>();

    public TimeSlot(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return this.id;
    }

    public ArrayList<Ticket> getQueueTickets(){
        return this.queueTickets;
    }
    public void addQueueTicket(Ticket queueTicket){
        //...
    }
    public void addQueueTickets(ArrayList<Ticket> queueTickets){
        //...
    }

    public ArrayList<Ticket> getVisitTickets(){
        return this.visitTickets;
    }
    public void addVisitTicket(Ticket visitTicket){
        //...
    }
    public void addVisitTickets(ArrayList<Ticket> visitTickets){
        //...
    }

    public ArrayList<Ticket> getExpectedExitingTickets(){
        return this.expectedExitingTickets;
    }
    public void addExpectedExitingTicket(Ticket expectedExitingTicket){
        //...
    }
    public void addExpectedExitingTickets(ArrayList<Ticket> expectedExitingTickets){
        //...
    }

    //TODO: da togliere, tranquillo, non lo hai usato da nessuna parte questo metodo
    public ArrayList<Ticket> getAvailableExpectedExitingTicket(){
        //returns all the tickets from the expectedExitingTickets list that don't have matchingFollowingTickets
        //...
    }
}