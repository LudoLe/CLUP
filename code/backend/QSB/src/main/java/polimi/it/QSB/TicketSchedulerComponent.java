package polimi.it.QSB;

import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.Ticket;
import polimi.it.DL.services.ShopService;
import polimi.it.DL.services.TicketService;

import javax.ejb.EJB;
import java.util.*;


public class TicketSchedulerComponent {



    @EJB(name = "TicketService")
    TicketService ticketService;
    
    class TicketTracker{
        private Ticket ticket;

        private Ticket matchingPreviousTicket;

        private Ticket matchingFollowingTicket;

        public TicketTracker(Ticket ticket){
            this.ticket = ticket;
        }

        public Ticket getTicket() {
            return ticket;
        }

        public void setTicket(Ticket ticket) {
            this.ticket = ticket;
        }

        public Ticket getMatchingPreviousTicket() {
            return matchingPreviousTicket;
        }

        public void setMatchingPreviousTicket(Ticket matchingPreviousTicket) {
            this.matchingPreviousTicket = matchingPreviousTicket;
        }

        public Ticket getMatchingFollowingTicket() {
            return matchingFollowingTicket;
        }

        public void setMatchingFollowingTicket(Ticket matchingFollowingTicket) {
            this.matchingFollowingTicket = matchingFollowingTicket;
        }
    }

    class TimeSlot{
        private int id;

        private Date startingTime;

        private Date endingTime;

        // tickets scheduled to enter in this time slot
        private ArrayList < TicketTracker > tickets = new ArrayList < TicketTracker >() ;

        // tickets that are expected to exit the shop during this time slot
        private ArrayList < TicketTracker > expectedExitingTickets = new ArrayList < TicketTracker >() ;

        public TimeSlot ( Integer id ){
            this.id = id ;
            this.startingTime = new Date(currentTime.getTime() + (id*shop.getTimeslotMinutesDuration()*6000L));
            this.endingTime = new Date(startingTime.getTime() + (shop.getTimeslotMinutesDuration()*6000L));
        }

        public Date getStartingTime() {
            return startingTime;
        }

        public void setStartingTime(Date startingTime) {
            this.startingTime = startingTime;
        }

        public Date getEndingTime() {
            return endingTime;
        }

        public void setEndingTime(Date endingTime) {
            this.endingTime = endingTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ArrayList<TicketTracker> getTickets() {
            return tickets;
        }

        public void setTickets(ArrayList<TicketTracker> tickets) {
            this.tickets = tickets;
        }

        public ArrayList<TicketTracker> getExpectedExitingTickets() {
            return expectedExitingTickets;
        }

        public void setExpectedExitingTickets(ArrayList<TicketTracker> expectedExitingTickets) {
            this.expectedExitingTickets = expectedExitingTickets;
        }
    }

    private Shop shop;

    private List<Ticket> tickets;

    private ArrayList <TimeSlot> timeLine = new ArrayList <TimeSlot>() ;

    private final String invalid = "invalid";
    private final String valid = "valid";
    private final String in_use = "in_use";
    private final String placeholder = "placeholder";

    // current time
    private Date currentTime;

    // constructor
    public TicketSchedulerComponent(Shop shop) throws Exception {
        this.shop = shop;
        this.tickets = shop.getTickets();
        this.currentTime = new Date();
    }

    public ArrayList<Map<Ticket, Date>> buildQueue() {

        System.out.println("\n.buildQueue() method started");
        System.out.println("\ncurrentTime is: " + currentTime);

        // general informations
        int shopCapacity = shop.getShopCapacity();
        int timeSlotMinuteDuration = shop.getTimeslotMinutesDuration();

        System.out.println("\nshopCapacity is: " + shopCapacity);
        System.out.println("\ntimeSlotMinuteDuration is: " + timeSlotMinuteDuration);

        // divide the tickets in ticket inside the shop, ticket that need to be scheduled to enter the shop and
        // ticket expired
        List<TicketTracker> ticketsInsideShop = new ArrayList<TicketTracker>();
        List<TicketTracker> ticketsToSchedule = new ArrayList<TicketTracker>();
        List<TicketTracker> ticketsExpired = new ArrayList<TicketTracker>();
        for (Ticket ticket: tickets) {
            switch (ticket.getStatus()){
                case invalid:
                    ticketsToSchedule.add(new TicketTracker(ticket));
                    break;
                case valid:
                    if(calculateTimeSlotId(ticket.getScheduledExitingTime()) >= -1) {
                        ticketsToSchedule.add(new TicketTracker(ticket));
                    }
                    else { // a ticket is expired if it was scheduled at least one time slot before the current time
                        ticketsExpired.add(new TicketTracker(ticket));
                    }
                    break;
                case in_use:
                    TicketTracker ticketInsideShop = new TicketTracker(ticket);
                    Ticket placeHolderTicket = new Ticket();
                    placeHolderTicket.setStatus(placeholder);
                    ticketInsideShop.setMatchingPreviousTicket(placeHolderTicket); //...useless, but just to not have incomplete data in the future
                    ticketsInsideShop.add(ticketInsideShop);
                    break;
                default:
                    break;
            }
        }

        System.out.println("\nPrinting tickets inside shop...");
        printTicketTrackerList(ticketsInsideShop);
        System.out.println("\nPrinting tickets to be scheduled...");
        printTicketTrackerList(ticketsToSchedule);
        System.out.println("\nPrinting tickets expired...");
        printTicketTrackerList(ticketsExpired);

        // beginning of the time line
        TimeSlot firstTimeSlot = this.getTimeSlot (0);

        // if the shop is not full we place a ("placehoder") exiting ticket in the first
        // time slot for each empty entry
        int freeShopEntries = shopCapacity - ticketsInsideShop.size();
        ArrayList < TicketTracker > fakeExitingTickets = new ArrayList < TicketTracker>() ;
        for (int i = 0; i < freeShopEntries ; i ++) {
            Ticket placeHolderTicket = new Ticket();
            placeHolderTicket.setStatus(placeholder);
            fakeExitingTickets.add(new TicketTracker(placeHolderTicket));
        }
        firstTimeSlot.setExpectedExitingTickets(fakeExitingTickets);

        System.out.println("\nFirst time slot has been created and 'placeholders' added, printing first time slot...");
        printTimeSlot(firstTimeSlot);

        // for each ticket inside the shop , calculate the expected exit time and mark the
        // corresponding time slot
        // if the corresponding time slot is before the current time, just mark the first time slot
        for ( TicketTracker ticketTracker : ticketsInsideShop ){
            long sum = ticketTracker.getTicket().getEnterTime().getTime() +
                    ticketTracker.getTicket().getExpectedDuration().getTime();
            Date expectedExitTime = new Date(sum);

            int expectedExitTimeSlotId = calculateTimeSlotId(expectedExitTime);

            if(expectedExitTime.before(currentTime)){
                expectedExitTimeSlotId = 0;
            }

            this.getTimeSlot(expectedExitTimeSlotId).getExpectedExitingTickets().add(ticketTracker);
        }

        System.out.println("\nAdded expected exiting time for tickets inside shop, printing time line...");
        printTimeLine(timeLine);

        // create a copy of the list of tickets to be scheduled since we'll work with it removing elements and we do not
        // want to lose track of some tickets
        ArrayList<TicketTracker> ticketsToScheduleCopy = new ArrayList<TicketTracker>(ticketsToSchedule);

        System.out.println("\nGIANT FOR STARTING:");

        // loop through all the time slots in the time line , and ,
        // if possible , insert an entering queue ticket
        for ( int k = 0; k < this.timeLine.size() ; k ++) {

            System.out.println("\nGIANT FOR ITERATION NUMBER: " + k);

            System.out.println("\nprinting tickets to be scheduled remaining...");
            printTicketTrackerList(ticketsToScheduleCopy);

            // current timeslot
            TimeSlot timeSlot = this.timeLine.get(k);

            System.out.println("\nresolving time slot...");
            printTimeSlot(timeSlot);

            // all expected exiting ticket in this time slot
            ArrayList<TicketTracker> expectedExitingTickets = timeSlot.getExpectedExitingTickets();

            // for each expected exiting ticket...
            for (int i = 0; i < expectedExitingTickets.size(); i++) {
                TicketTracker expectedExitingTicket = expectedExitingTickets.get(i);

                System.out.println("\nresolving expected exiting ticket...");
                printTicketTracker(expectedExitingTicket);

                TicketTracker chosenTicket = null;

                // ...loops through all the tickets to be scheduled to find one to place in the
                // following time slot and to pair with the expected exiting ticket we are analyzing.
                for (int j = 0; j < ticketsToScheduleCopy.size(); j++) {
                    TicketTracker ticketTracker = ticketsToScheduleCopy.get(j);
                    // Conditions for a ticket to be enqueued:

                    // 1. The chosen queue ticket to place must be able to reach the shop
                    // before his turn arrives;

                    // 2. But if it was already scheduled for a time slot that is before his time
                    // to reach the shop :
                    // in this case we can schedule the ticket in a time slot equals (preferred) or
                    // greater than the previously registered scheduled time.
                    // This condition can be verified with two option:
                    //     ((current time + time to reach) > previously registered scheduled time )
                    // or in a easier way:
                    //     ticket.status == "valid"
                    //     (because if a ticket is valid it means that it has already been processed by this algorithm)

                    // 3. Also the ticket can be enqueued only if its expected exiting time is not exceeding
                    // the shop shifts.

                    // A ticket is considered chosen if it respect conditions:
                    //   (1 && 3)
                    // or
                    //   (2 && 3)

                    //lets find a ticket that respect the conditions listed above:

                    //  CONDITION 1
                    if ((timeSlot.getId() + 1) <= calculateTimeSlotId(ticketTracker.getTicket().getTimeToReachTheShop())) {
                        chosenTicket = ticketTracker;
                    }
                    // CONDITION 2
                    else if ((ticketTracker.getTicket().getStatus().equals(valid))
                            && ((timeSlot.getId() + 1) >= calculateTimeSlotId(ticketTracker.getTicket().getScheduledEnteringTime()))) {
                        chosenTicket = ticketTracker;
                    }

                    // if we have found a ticket that respect condition 1 or 2, we check if it respect event condition 3
                    if (chosenTicket != null) {

                        // update the chosen ticket scheduledEnteringTime and scheduledExitingTime
                        chosenTicket.getTicket().setScheduledEnteringTime(getTimeSlot(timeSlot.getId() + 1)
                                .getStartingTime());
                        long sum = getTimeSlot(timeSlot.getId() + 1).getStartingTime().getTime()
                                + chosenTicket.getTicket().getExpectedDuration().getTime();
                        Date scheduledExitingTime = new Date(sum);
                        chosenTicket.getTicket().setScheduledExitingTime(scheduledExitingTime);

                        // CONDITION 3
                        if (!checkIfInsideShift(chosenTicket.getTicket())) {
                            chosenTicket = null;
                        }
                    }

                    // at this point if we have found a ticket we only need to update the timeline and the TicketTrackers
                    if (chosenTicket != null) {

                        // insert the chosen ticket in the list of entering tickets of the correct timeslot
                        getTimeSlot(timeSlot.getId() + 1).getTickets().add(chosenTicket);

                        // insert the chosen ticket in the list of exiting tickets of the correct timeslot
                        long sum = getTimeSlot(timeSlot.getId() + 1).getStartingTime().getTime()
                                + chosenTicket.getTicket().getExpectedDuration().getTime();
                        Date expectedExitingTime = new Date(sum);
                        getTimeSlot(calculateTimeSlotId(expectedExitingTime)).getExpectedExitingTickets().add(chosenTicket);

                        // pair the chosen ticket tracker previous ticket
                        chosenTicket.setMatchingPreviousTicket(expectedExitingTicket.getTicket());

                        // pair the exiting ticket tracker following ticket
                        expectedExitingTicket.setMatchingFollowingTicket(chosenTicket.getTicket());

                        // remove the chosen ticket from the list of tickets to schedule (we did a copy of the array list,
                        // so no problem of losing track of data), since it has been correctly placed in the time line.
                        ticketsToScheduleCopy.remove(chosenTicket);

                        // now that we have found a ticket we can break out the cycle and repeat the process for the next
                        // exiting ticket
                        break;
                    }
                }

                // if no ticket has been found, we can pick simply chose the first ticket and update the timeline and the ticketTrackers
                if (chosenTicket == null && !ticketsToSchedule.isEmpty()) {
                    chosenTicket = ticketsToScheduleCopy.get(0);
                    ticketsToScheduleCopy.remove(chosenTicket);

                    // insert the chosen ticket in the list of entering tickets of the correct timeslot
                    long sum = currentTime.getTime() + chosenTicket.getTicket().getTimeToReachTheShop().getTime();
                    Date expectedEnteringTime = new Date(sum);
                    int expectedEnteringTimeSlotId = calculateTimeSlotId(expectedEnteringTime);
                    getTimeSlot(expectedEnteringTimeSlotId).getTickets().add(chosenTicket);

                    // insert the chosen ticket in the list of exiting tickets of the correct timeslot
                    sum = getTimeSlot(expectedEnteringTimeSlotId).getStartingTime().getTime()
                            + chosenTicket.getTicket().getExpectedDuration().getTime();
                    Date expectedExitingTime = new Date(sum);
                    getTimeSlot(calculateTimeSlotId(expectedExitingTime)).getExpectedExitingTickets().add(chosenTicket);

                    // pair the chosen ticket tracker previous ticket
                    chosenTicket.setMatchingPreviousTicket(expectedExitingTicket.getTicket());

                    // pair the exiting ticket tracker following ticket
                    expectedExitingTicket.setMatchingFollowingTicket(chosenTicket.getTicket());
                }

                System.out.println("\nprinting chosen ticket...");
                printTicketTracker(chosenTicket);

            }
        }

        System.out.println("\nGIANT FOR ENDED, printing time line...");
        printTimeLine(timeLine);

        //TODO:
        // DEVO SETTARE GLI STATI DEI TICKET? SI FEDEEEE

        // handle expired tickets (sono già sengati in un ArrayList<>)

        // now that all the time line has been populated, we need to set the scheduled entering and exiting time of each
        // ticket in the timeline and update the db;
        /*
        for (TimeSlot timeslot : timeLine) {
            for (TicketTracker ticketTracker: timeslot.getTickets()) {
                ticketTracker.getTicket().setScheduledEnteringTime(timeslot.getStartingTime());
            }
            for (TicketTracker ticketTracker: timeslot.getExpectedExitingTickets()) {
                ticketTracker.getTicket().setScheduledExitingTime(timeslot.getStartingTime());
            }
        }
        */

        Map<Ticket, Date> enteringTimeMap = new HashMap<Ticket, Date>();
        Map<Ticket, Date> exitingTimeMap = new HashMap<Ticket, Date>();

        for (TimeSlot timeslot : timeLine) {
            for (TicketTracker ticketTracker: timeslot.getTickets()) {
                Ticket t = ticketTracker.getTicket();
                Date d = timeslot.getStartingTime();
                enteringTimeMap.put(t, d);
            }
            for (TicketTracker ticketTracker: timeslot.getExpectedExitingTickets()) {
                Ticket t = ticketTracker.getTicket();
                Date d = timeslot.getStartingTime();
                exitingTimeMap.put(t, d);
            }
        }

        ArrayList<Map<Ticket, Date>> result = new ArrayList<Map<Ticket, Date>>();
        result.add(enteringTimeMap);
        result.add(exitingTimeMap);

        return result;

       /*    How is the result structured?
        *    The result is of type "ArrayList<Map<Ticket, Date>>" and contains only two elements:
        *        First Map:   result.get(0) is a map representing the tickets that need to have the scheduledEnteringTime set,
        *                     where the key [.getKey()] is the Ticket to update, and the value [.getValue()] is the updated time
        *        Second Map:  result.get(1) is a map representing the tickets that need to have the scheduledExitingTIme set,
        *                     where the key [.getKey()] is the Ticket to update, and the value [.getValue()] is the updated time
        *
        *    How to iterate over a Map?
        *        Iterator it = map.entrySet().iterator();
        *        for (Map.Entry<Ticket, Date> entry : map.entrySet()) {
        *            Ticket t = entry.getKey();
        *            Date d = entry.getValue();
        *            // ... update the ticket
        *        }
        */


        //TODO: appunto per capire quando può entrare un ticket
        // a ticket can enter during time slot 0 and 1 and if his previous matching ticket is a palceholder.

        //TODO: appunto per la parte web: nel caso di /lineup, dopo aver fatto andare l'algoritmo bisogna informare l'utente se il biglietto è
        // stato validato oppure è rimasto invalido. Se è rimasto invalido bisogna anche toglierlo dal database.
    }

    // if the time slot exists in the timeline, returns the position of the time slot in the ArrayList of the
    // time line
    // else returns the index in the time line of the next time slot or -1 if it has no successor.
    private int existTimeSlot(int timeSlotId){
        for (int i = 0, timeLineSize = timeLine.size(); i < timeLineSize; i++) {
            TimeSlot timeSlot = timeLine.get(i);
            if (timeSlot.getId() == timeSlotId) {
                return i;
            }
            else if(timeSlot.getId() > timeSlotId){
                return i;
            }
        }
        return -1;
    }

    // returns the time slot with the corresponding id from the timeLine ;
    // if the time slot doesn’t exist , create one , add it in order to the timeLine , and return it.
    private TimeSlot getTimeSlot ( int timeSlotId ){
        TimeSlot returnValue;
        int timeLineIndex = existTimeSlot(timeSlotId);
        if(timeLineIndex == -1){
            returnValue = new TimeSlot(timeSlotId);
            timeLine.add(returnValue);
        }
        else{
            if(timeLine.get(timeLineIndex).getId() == timeSlotId){
                returnValue = timeLine.get(timeLineIndex);
            }
            else{
                returnValue = new TimeSlot(timeSlotId);
                timeLine.add( timeLineIndex , returnValue);
            }
        }
        return returnValue;
    }

    // given a date calculate the time slot it is in.
    // return 0 if it is in the time slot starting in current time
    // return -1 if it is in the time slot ending in current time
    // return -2 if the time slot is in the past (at least a timeslot duration before the currentTime)
    private int calculateTimeSlotId (Date date){
        long timeSlotDurationInMilliSeconds = (shop.getTimeslotMinutesDuration()* 6000L);
        Date maxPastTime = new Date(currentTime.getTime() - timeSlotDurationInMilliSeconds);

        if(date.before(maxPastTime)){
            return -2;
        }

        if(date.before(currentTime)){
            return -1;
        }

        int timeSlotId = -1;
        int counter = 0;
        Date startTimeSlot = new Date();
        Date endTimeSlot = new Date(startTimeSlot.getTime() +timeSlotDurationInMilliSeconds);
        while(true) {
            if(date.after(startTimeSlot) && date.before(endTimeSlot)){
                timeSlotId = counter;
                break;
            }
            counter++;
            startTimeSlot = new Date(startTimeSlot.getTime() + timeSlotDurationInMilliSeconds);
            endTimeSlot = new Date(startTimeSlot.getTime() + timeSlotDurationInMilliSeconds);
        }

        return timeSlotId;
    }

    private boolean checkIfInsideShift(Ticket ticket){
        //TODO:
        // given a ticket it checks if its scheduledEnterignTime and scheduledExitingTime are compatible with at least
        // one shopShift.
        return true;
    }


    /*auxiliar methods for debugging*/

    private void printTimeLine (List<TimeSlot> tl){
        System.out.println("    __printing time line__");
        if(timeLine.isEmpty()){
            System.out.println("    time line is empty");
        }
        else {
            for (TimeSlot ts : tl) {
                printTimeSlot(ts);
            }
        }
        System.out.println("    ______________________timeline");
    }

    private void printTimeSlot(TimeSlot ts){
        System.out.println("    __printing a time slot__");
        System.out.println("    id: " + ts.getId());
        System.out.println("     starting time: " + (ts.getStartingTime()!=null ? ts.getStartingTime() : "null"));
        System.out.println("     ending time: " + (ts.getEndingTime()!=null ? ts.getEndingTime() : "null"));
        System.out.println("     expected entering tickets:");
        printTicketTrackerList(ts.getTickets());
        System.out.println("     expected exiting tickets:");
        printTicketTrackerList(ts.getExpectedExitingTickets());
        System.out.println("    ________________________ts");
    }

    private void printTicketTrackerList(List<TicketTracker> ticketTrackers){
        System.out.println("    __printing a ticket tracker list__");
        if(ticketTrackers.isEmpty()){
            System.out.println("    List is empty");
        }
        else {
            for (TicketTracker tt : ticketTrackers) {
                printTicketTracker(tt);
            }
        }
        System.out.println("    _____________________________ttl");
    }
    private void printTicketTracker(TicketTracker tt){
        System.out.println("        __printing a ticket tracker__");
        System.out.println("       previous matching ticket: " + ((tt.getMatchingPreviousTicket()!=null && tt.getMatchingPreviousTicket().getStatus()!=placeholder) ? tt.getMatchingPreviousTicket().getId() : "null"));
        System.out.println("       following matching ticket: " + ((tt.getMatchingFollowingTicket()!=null && tt.getMatchingFollowingTicket().getStatus()!=placeholder) ? tt.getMatchingFollowingTicket().getId() : "null"));
        System.out.println("       Ticket:");
        printTicket(tt.getTicket());
        System.out.println("        _____________________________tt");
    }
    private void printTicketList(List<Ticket> tickets){
        System.out.println("    __printing a ticket list__");
        if(tickets.isEmpty()){
            System.out.println("    List is empty");
        }
        else {
            for (Ticket t : tickets) {
                printTicket(t);
            }
        }
        System.out.println("    __________________________tl");
    }
    private void printTicket(Ticket t){
        System.out.println("         __printing a ticket__");
        if(t != null){
            System.out.println("         id: " + t.getId());
            System.out.println("          status: " + (t.getStatus()!=null ? t.getStatus() : "null"));
            System.out.println("          duration: " + (t.getExpectedDuration()!=null ? t.getExpectedDuration() : "null"));
            System.out.println("          ttr: " + (t.getTimeToReachTheShop()!=null ? t.getTimeToReachTheShop() : "null"));
            System.out.println("          enter: " + (t.getEnterTime()!=null ? t.getEnterTime() : "null"));
            System.out.println("          exit: " + (t.getExitTime()!=null ? t.getExitTime() : "null"));
            System.out.println("          scheduled enter: " + (t.getScheduledEnteringTime()!=null ? t.getScheduledEnteringTime() : "null"));
            System.out.println("          scheduled exit: " + (t.getScheduledExitingTime()!=null ? t.getScheduledExitingTime() : "null"));
        }
        else{
            System.out.println("         ticket is null");
        }
        System.out.println("         _____________________t");
    }
}
