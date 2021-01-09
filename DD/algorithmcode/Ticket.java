//its better if in the future we build different classes for each type of ticket !!
public class Ticket{

    //expected duration of the permanence, expressed in quantity of TimeSlots, 
    //a null value means that the ticket is a "fake" ticket
    private Integer expectedDuration;

    //indicates the time slot for which the ticket is booked
    //a null value means that the ticket is not a visit ticket, but a queue ticket or a "fake" ticket
    //if the sceduled time is prior the current time, than the scheduledTimeSlot value is 1 (the first available slot)
    private Integer scheduledTimeSlot;

    //null if the ticket has never entered the store, expressed with TimeSlots id, 
    //a negative value means that the ticket has entered the store
    //a positive value means that the ticket hasn't entered the store
    //a null value means the ticket is a "fake" ticket or that the entering time still hasn't been decided
    private Integer enteringTimeSlot;

    //all types of tickets
    enum TicketType {
        VISIT,
        QUEUE
    }

    private TicketType ticketType;

    //in order to let a ticket enter a shop, there must be an exiting ticket, this is a reference to the corresponding previous exiting ticket
    private Ticket matchingPreviousTicket;

    //in order to let a ticket enter a shop, there must be an exiting ticket, this is a reference to the corresponding following entering ticket
    private Ticket matchingFollowingTicket;

    //time needed to reach the shop expressed in quantity of TimeSlots
    //only for queue tickets
    private Integer timeToReachTheShop;

    //constructor for tickets (queue tickets or visit tickets) that have entered the store, but still not 
    public Ticket(Integer enteringTime, Integer expectedDuration){
        this.expectedDuration = expectedDuration;
        this.enteringTime = enteringTime;
        this.ticketType = null; //not relevant
        this.scheduledTimeSlot = null;
        this.matchingPreviousTicket = null;
        this.timeToReachTheShop = null;
    }

    //constructor for queue tickets that haven't already entered the store
    public Ticket(Integer expectedDuration, Integer timeToReachTheShop){
        this.enteringTimeSlot = null;
        this.expectedDuration = expectedDuration;
        this.ticketType = QUEUE;
        this.scheduledTimeSlot = null;
        this.matchingPreviousTicket = null;
        this.timeToReachTheShop = timeToReachTheShop;
    }

    //constructor for visit tickets that haven't already entered the store
    public Ticket(Integer expectedDuration, Integer scheduledTimeSlot){
        this.enteringTimeSlot = null;
        this.expectedDuration = expectedDuration;
        this.ticketType = VISIT;
        this.scheduledTimeSlot = scheduledTimeSlot;
        this.matchingPreviousTicket = null;
        this.timeToReachTheShop = null;
    }

    //constructor for "fake" (placeholders) tickets
    public Ticket(){
        this.expecteDuration = null;
        this.enteringTimeSlot = null;
        this.ticketType = null;
        this.scheduledTimeSlot = null;
        this.matchingPreviousTicket = null;
        this.timeToReachTheShop = null;
    }

    public Integer getExpectedDuration(){
        return this.expectedDuration;
    }

    public Integer getEnteringTimeSlot(){
        return this.enteringTimeSlot;
    }

    public TicketType getTicketType(){
        return this.ticketType;
    }

    public Integer getScheduledTimeSlot(){
        return this.scheduledTimeSlot;
    }

    public Ticket getMatchingPreviousTicket(){
        return this.matchingPreviousTicket;
    }

    public Ticket getMatchingFollowingTicket(){
        return this.matchingFollowingTicket;
    }

    public Integer getTimeToReachTheShop(){
        return this.timeToReachTheShop;
    }

    public void setExpectedDuration(Integer expectedDuration){
        this.expectedDuration = expectedDuration;
        return;
    }

    public void setEnteringTimeSlot(Integer enteringTimeSlot){
        this.enteringTimeSlot = enteringTimeSlot;
        return;
    }

    public void setMatchingPreviousTicket(){
        this.matchingPreviousTicket = matchingPreviousTicket;
        return;
    }

    public Ticket setMatchingFollowingTicket(){
        return this.matchingFollowingTicket;
    }
}