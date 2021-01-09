public class Ticket{

    //expected duration of the permanence, expressed in quantity of TimeSlots
    private Integer expectedDuration;

    //indicates the time slot for which the ticket is booked (only for visit tickets)
    private Integer scheduledTimeSlot;

    //null if the ticket has never entered the store
    //a negative value means that the ticket has entered the store
    //a positive value means that the ticket hasn't entered the store
    private Integer enteringTimeSlot;

    //all types of tickets
    enum TicketType {
        VISIT,
        QUEUE
    }
    private TicketType ticketType;

    //ONE-TO-ONE correspondence:
    //in order to let a ticket enter a shop, there must be an exiting ticket, this is a reference to the corresponding previous exiting ticket
    private Ticket matchingPreviousTicket;
    //in order to let a ticket enter a shop, there must be an exiting ticket, this is a reference to the corresponding following entering ticket
    private Ticket matchingFollowingTicket;

    //time needed to reach the shop expressed in quantity of TimeSlots, only for queue tickets
    private Integer timeToReachTheShop;

    //constructor for tickets (queue tickets or visit tickets) that have entered the store
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

    //constructor for "fake" (placeholders) tickets, all the properties are set to null
    public Ticket(){
        this.expecteDuration = null;
        this.enteringTimeSlot = null;
        this.ticketType = null;
        this.scheduledTimeSlot = null;
        this.matchingPreviousTicket = null;
        this.timeToReachTheShop = null;
    }
    
    //getters and setters
}