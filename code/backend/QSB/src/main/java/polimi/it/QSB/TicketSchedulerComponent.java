package polimi.it.QSB;

import polimi.it.DL.entities.Shop;
import polimi.it.DL.entities.Ticket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TicketSchedulerComponent {

    class TicketTracker {
        private Ticket ticket;

        private Ticket matchingPreviousTicket;

        private Ticket matchingFollowingTicket;

        public TicketTracker(Ticket ticket) {
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

    class TimeSlot {
        private int id;

        private Date startingTime;

        private Date endingTime;

        // tickets scheduled to enter in this time slot
        private ArrayList<TicketTracker> tickets = new ArrayList<TicketTracker>();

        // tickets that are expected to exit the shop during this time slot
        private ArrayList<TicketTracker> expectedExitingTickets = new ArrayList<TicketTracker>();

        public TimeSlot(Integer id) {
            this.id = id;
            this.startingTime = new Date(currentTime.getTime() + (id * shop.getTimeslotMinutesDuration() * 60000L));
            this.endingTime = new Date(startingTime.getTime() + (shop.getTimeslotMinutesDuration() * 60000L));
        }

        public Date getStartingTime() {
            return startingTime;
        }

        public Date getEndingTime() {
            return endingTime;
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

    private ArrayList<TimeSlot> timeLine = new ArrayList<TimeSlot>();

    private final String invalid = "invalid";
    private final String valid = "valid";
    private final String in_use = "in_use";
    private final String placeholder = "placeholder";
    private final String expired = "expired";
    private final String used = "used";

    // current time
    private Date currentTime;

    // constructor
    public TicketSchedulerComponent(List<Ticket> tickets, Shop shop) throws Exception {
        this.tickets = tickets;
        this.currentTime = new Date();
        this.shop = shop;
    }

    public List<Ticket> buildQueue() {

        System.out.println("\n.buildQueue() method started");
        System.out.println("\ncurrentTime is: " + currentTime);

        // general information
        int shopCapacity = shop.getShopCapacity();
        int timeSlotMinuteDuration = shop.getTimeslotMinutesDuration();

        System.out.println("\nshopCapacity is: " + shopCapacity);
        System.out.println("\ntimeSlotMinuteDuration is: " + timeSlotMinuteDuration);

        // divide the tickets in ticket inside the shop, ticket that need to be scheduled to enter the shop and
        // ticket expired
        List<TicketTracker> ticketsInsideShop = new ArrayList<TicketTracker>();
        List<TicketTracker> ticketsToSchedule = new ArrayList<TicketTracker>();
        List<TicketTracker> ticketsExpired = new ArrayList<TicketTracker>();
        List<TicketTracker> ticketsUsed = new ArrayList<TicketTracker>();
        for (Ticket ticket : tickets) {
            switch (ticket.getStatus()) {
                case invalid: //must be scheduled and at the end it will become a valid ticket
                    ticketsToSchedule.add(new TicketTracker(ticket));
                    break;
                case valid: //it could have entered the shop and become a in_use ticket
                            //it could have expired (if its not entered and its scheduled time is "one time slot" before the current time)
                            //it could be still valid and need to be rescheduled
                    if(ticket.getEnterTime()!=null){
                        ticketsInsideShop.add(new TicketTracker(ticket));
                    }
                    else if (ticket.getScheduledEnteringTime().before(new Date(currentTime.getTime() - 60000L*timeSlotMinuteDuration))) {
                        ticketsExpired.add(new TicketTracker(ticket));
                    } else {
                        ticketsToSchedule.add(new TicketTracker(ticket));
                    }
                    break;
                case in_use: //it could have exit the shop and become used
                             //it could still be in_use
                    if (ticket.getExitTime() != null){
                        ticketsUsed.add(new TicketTracker(ticket));
                    }
                    else{
                        TicketTracker ticketInsideShop = new TicketTracker(ticket);
                        Ticket placeHolderTicket = new Ticket();
                        placeHolderTicket.setStatus(placeholder);
                        ticketInsideShop.setMatchingPreviousTicket(placeHolderTicket); //...useless, but just to not have incomplete data in the future
                        ticketsInsideShop.add(ticketInsideShop);
                    }
                    break;
                default:
                    break;
            }
        }

        System.out.println("\n\n\nPrinting tickets inside shop...");
        //printTicketTrackerList(ticketsInsideShop);
        StringBuilder s = new StringBuilder("[ ");
        for (TicketTracker tt : ticketsInsideShop) {
            s.append(tt.getTicket().getId()).append(" ");
        }
        s.append("]");
        System.out.println(s);

        s = new StringBuilder("[ ");
        System.out.println("\n\n\nPrinting tickets to be scheduled...");
        //printTicketTrackerList(ticketsToSchedule);
        for (TicketTracker tt : ticketsToSchedule) {
            s.append(tt.getTicket().getId()).append(" ");
        }
        s.append("]");
        System.out.println(s);

        s = new StringBuilder("[ ");
        System.out.println("\n\n\nPrinting tickets expired...");
        //printTicketTrackerList(ticketsExpired);
        for (TicketTracker tt : ticketsExpired) {
            s.append(tt.getTicket().getId()).append(" ");
        }
        s.append("]");
        System.out.println(s + "\n\n");

        // beginning of the time line
        TimeSlot firstTimeSlot = this.getTimeSlot(-1);

        // if the shop is not full we place a ("placehoder") exiting ticket in the first
        // time slot for each empty entry
        int freeShopEntries = shopCapacity - ticketsInsideShop.size();
        ArrayList<TicketTracker> fakeExitingTickets = new ArrayList<TicketTracker>();
        for (int i = 0; i < freeShopEntries; i++) {
            Ticket placeHolderTicket = new Ticket();
            placeHolderTicket.setStatus(placeholder);
            fakeExitingTickets.add(new TicketTracker(placeHolderTicket));
        }
        firstTimeSlot.setExpectedExitingTickets(fakeExitingTickets);

        System.out.println("\nFirst time slot has been created and 'placeholders' added");

        // for each ticket inside the shop , calculate the expected exit time and mark the
        // corresponding time slot
        // if the corresponding time slot is before the current time, just mark the first time slot
        for (TicketTracker ticketTracker : ticketsInsideShop) {
            long sum = ticketTracker.getTicket().getEnterTime().getTime() +
                    ticketTracker.getTicket().getExpectedDuration().getTime();
            Date expectedExitTime = new Date(sum);

            int expectedExitTimeSlotId = calculateTimeSlotId(expectedExitTime);

            if (expectedExitTime.before(currentTime)) {
                expectedExitTimeSlotId = 0;
            }

            this.getTimeSlot(expectedExitTimeSlotId).getExpectedExitingTickets().add(ticketTracker);
        }

        System.out.println("\nAdded expected exiting time for tickets inside shop");

        // create a copy of the list of tickets to be scheduled since we'll work with it removing elements and we do not
        // want to lose track of some tickets
        ArrayList<TicketTracker> ticketsToScheduleCopy = new ArrayList<TicketTracker>(ticketsToSchedule);

        System.out.println("\nGIANT FOR STARTING:");

        // loop through all the time slots in the time line , and ,
        // if possible , insert an entering queue ticket
        for (int k = 0; k < this.timeLine.size(); k++) {

            System.out.println("\n    GIANT FOR ITERATION NUMBER: " + k);

            System.out.println("\n            remaining tickets to be scheduled: " + ticketsToScheduleCopy.size());
            //System.out.println("\nprinting tickets to be scheduled remaining...");
            //printTicketTrackerList(ticketsToScheduleCopy);

            // current timeslot
            TimeSlot timeSlot = this.timeLine.get(k);

            System.out.println("\n            resolving time slot (id: " + timeSlot.getId() + ")");
            //System.out.println("\nresolving time slot...");
            //printTimeSlot(timeSlot);

            // all expected exiting ticket in this time slot
            ArrayList<TicketTracker> expectedExitingTickets = timeSlot.getExpectedExitingTickets();

            // for each expected exiting ticket...
            for (int i = 0; i < expectedExitingTickets.size(); i++) {
                TicketTracker expectedExitingTicket = expectedExitingTickets.get(i);

                System.out.println("\n                        resolving expected exiting ticket (id:" + expectedExitingTicket.getTicket().getId() + ")");
                //System.out.println("\nresolving expected exiting ticket...");
                //printTicketTracker(expectedExitingTicket);

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

                    // 3. (we we'll not check for this condition) Also the ticket can be enqueued only if its expected exiting time is not exceeding
                    // the shop shifts.

                    // A ticket is considered chosen if it respect conditions:
                    //   (1 && 3)
                    // or
                    //   (2 && 3)

                    // 4. If none of the above conditions are matched we enqueue the ticket that has the shortest exiting time
                    // [(currentTime + timeToReachTheShop) oppure (arrivalTime) + expectedDuration)]

                    //lets find a ticket that respect the conditions listed above:

                    //  CONDITION 1
                    if ((timeSlot.getId() + 1) >= calculateTimeSlotId(ticketTracker.getTicket().getTimeToReachTheShop())) {

                        System.out.println("\n                                    found ticket with CONDITION 1");

                        chosenTicket = ticketTracker;

                        System.out.println("\n                                    chosen ticket is (id: " + chosenTicket.getTicket().getId() + ")");

                    }
                    // CONDITION 2
                    else if ((ticketTracker.getTicket().getStatus().equals(valid))
                            && ((timeSlot.getId() + 1) >= calculateTimeSlotId(getArrivalTime(ticketTracker.getTicket())))) {

                        System.out.println("\n                                    found ticket with CONDITION 2");

                        chosenTicket = ticketTracker;

                        System.out.println("\n                                    chosen ticket is (id: " + chosenTicket.getTicket().getId() + ")");

                    }

                    //TODO: CONDITION 3
                    //if we have found a ticket that respect condition 1 or 2, we check if it respect event condition 3
                    /* we are not checking condition 3
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
                    */

                    // at this point if we have found a ticket we only need to update the timeline and the TicketTrackers
                    if (chosenTicket != null) {

                        System.out.println("\n                                    UPDATING");

                        // insert the chosen ticket in the list of entering tickets of the correct timeslot
                        getTimeSlot(timeSlot.getId() + 1).getTickets().add(chosenTicket);
                        System.out.println("                                        chosen ticket set in entering tickets of time slot " + (timeSlot.getId() + 1));


                        // insert the chosen ticket in the list of exiting tickets of the correct timeslot
                        long sum = getTimeSlot(timeSlot.getId() + 1).getStartingTime().getTime()
                                + chosenTicket.getTicket().getExpectedDuration().getTime();
                        Date expectedExitingTime = new Date(sum);
                        getTimeSlot(calculateTimeSlotId(expectedExitingTime)).getExpectedExitingTickets().add(chosenTicket);
                        System.out.println("                                        chosen ticket set in exiting tickets of time slot " + (calculateTimeSlotId(expectedExitingTime)));


                        // pair the chosen ticket tracker previous ticket
                        chosenTicket.setMatchingPreviousTicket(expectedExitingTicket.getTicket());
                        System.out.println("                                        setting chosen (" + chosenTicket.getTicket().getId() + ") ticket matching PREVIOUS ticket as the expected exiting ticket (" + (expectedExitingTicket.getTicket().getId()) + ")");

                        // pair the exiting ticket tracker following ticket
                        expectedExitingTicket.setMatchingFollowingTicket(chosenTicket.getTicket());
                        System.out.println("                                        setting expected exiting ticket (" + (expectedExitingTicket.getTicket().getId()) + ") matching FOLLOWING ticket as the chosen ticket (" + (chosenTicket.getTicket().getId()) + ")");

                        // remove the chosen ticket from the list of tickets to schedule (we did a copy of the array list,
                        // so no problem of losing track of data), since it has been correctly placed in the time line.
                        ticketsToScheduleCopy.remove(chosenTicket);
                        System.out.println("                                        deleting chosen ticket from the list of ticket to schedule (new size is " + (ticketsToScheduleCopy.size()) + ")");

                        // now that we have found a ticket we can break out the cycle and repeat the process for the next
                        // exiting ticket
                        break;
                    }
                }

                //CONDITION 4
                // if no ticket has been found, we can use as chosenTicket the one in that has the shortest exiting time
                // [(currentTime + timeToReachTheShop) or (arrivalTime) + expectedDuration)]
                if (chosenTicket == null && !ticketsToScheduleCopy.isEmpty()) {

                    System.out.println("\n                                no chosen ticket found, picking the one that has the shortest exiting time");

                    chosenTicket = getShortestExitingTime(ticketsToScheduleCopy);

                    System.out.println("\n                                    chosen ticket is (id: " + chosenTicket.getTicket().getId() + ")");

                    System.out.println("\n                                    UPDATING");

                    // insert the chosen ticket in the list of entering tickets of the correct timeslot
                    long sum = currentTime.getTime() + chosenTicket.getTicket().getTimeToReachTheShop().getTime();
                    Date expectedEnteringTime = new Date(sum);
                    if (chosenTicket.getTicket().getStatus().equals(valid) && chosenTicket.getTicket().getScheduledEnteringTime().before(expectedEnteringTime)) {
                        expectedEnteringTime = chosenTicket.getTicket().getScheduledEnteringTime();
                    }
                    int expectedEnteringTimeSlotId = calculateTimeSlotId(expectedEnteringTime);
                    getTimeSlot(expectedEnteringTimeSlotId).getTickets().add(chosenTicket);
                    System.out.println("                                        chosen ticket set in entering tickets of time slot " + expectedEnteringTimeSlotId);


                    // insert the chosen ticket in the list of exiting tickets of the correct timeslot
                    sum = getTimeSlot(expectedEnteringTimeSlotId).getStartingTime().getTime()
                            + chosenTicket.getTicket().getExpectedDuration().getTime();
                    Date expectedExitingTime = new Date(sum);
                    getTimeSlot(calculateTimeSlotId(expectedExitingTime)).getExpectedExitingTickets().add(chosenTicket);
                    System.out.println("                                        chosen ticket set in exiting tickets of time slot " + calculateTimeSlotId(expectedExitingTime));


                    // pair the chosen ticket tracker previous ticket
                    chosenTicket.setMatchingPreviousTicket(expectedExitingTicket.getTicket());
                    System.out.println("                                        setting chosen (" + chosenTicket.getTicket().getId() + ") ticket matching PREVIOUS ticket as the expected exiting ticket (" + (expectedExitingTicket.getTicket().getId()) + ")");

                    // pair the exiting ticket tracker following ticket
                    expectedExitingTicket.setMatchingFollowingTicket(chosenTicket.getTicket());
                    System.out.println("                                        setting expected exiting ticket (" + (expectedExitingTicket.getTicket().getId()) + ") matching FOLLOWING ticket as the chosen ticket (" + (chosenTicket.getTicket().getId()) + ")");

                    // remove the chosen ticket from the list of tickets to schedule (we did a copy of the array list,
                    // so no problem of losing track of data), since it has been correctly placed in the time line.
                    ticketsToScheduleCopy.remove(chosenTicket);
                    System.out.println("                                        deleting chosen ticket from the list of ticket to schedule (new size is " + (ticketsToScheduleCopy.size()) + ")");

                }

            }
        }

        System.out.println("\nGIANT FOR ENDED, printing time line in JSON...");
        System.out.println("*****************************************************************");
        printTimeLine(timeLine);
        System.out.println("*****************************************************************");



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

        // update all scheduledEnteringTime and scheduledExitingTime
        for (TimeSlot timeslot : timeLine) {
            for (TicketTracker ticketTracker : timeslot.getTickets()) {
                Ticket t = ticketTracker.getTicket();
                Date d = timeslot.getStartingTime();
                t.setScheduledEnteringTime(d);
            }
            for (TicketTracker ticketTracker : timeslot.getExpectedExitingTickets()) {
                Ticket t = ticketTracker.getTicket();
                Date d = timeslot.getStartingTime();
                t.setScheduledExitingTime(d);
            }
        }

        // update the arrivalTime attribute for invalid tickets
        // arrivalTime = scheduledEnteringTime
        // obviously the scheduledEnteringTime has just been updated in previous for loop, and the status is still invalid because
        // it is being changed in the next loop
        for (Ticket t : this.tickets ) {
            if(t.getStatus().equals(invalid)){
                t.setArrivalTime(t.getScheduledEnteringTime());
            }
        }

        // correctly update ticket status
        // (invalid => valid)
        // (exipired)
        // (used)
        // (in use)
        for(TicketTracker t : ticketsToSchedule){
            if(t.getTicket().getStatus().equals(invalid)){
                t.getTicket().setStatus(valid);
            }
        }
        for (TicketTracker t : ticketsInsideShop){
            if(t.getTicket().getStatus().equals(valid)){
                t.getTicket().setStatus(in_use);
            }
        }
        for (TicketTracker t : ticketsExpired){
            t.getTicket().setStatus(expired);
        }
        for (TicketTracker t : ticketsUsed){
            t.getTicket().setStatus(used);
        }

        return this.tickets;

        //TODO: appunto per capire quando può entrare un ticket
        // a ticket can enter during time slot 0 if its a valid ticket and if his previous matching ticket
        // is a palceholder.

        //TODO: appunto per la parte web:
        // nel caso di /lineup, dopo aver fatto andare l'algoritmo bisogna informare l'utente se il biglietto è
        // stato validato oppure è rimasto invalido. Se è rimasto invalido bisogna anche toglierlo dal database.
        // in realtà queto caso non si verifica mai perchè ogni t icket trova posto siccome non sono considerati gli
        // shift dei negozi

        //TODO: cosa fare per scan to enter

        //TODO: cosa fare per scan to exit

        //TODO: cosa fare per dequeue

        //TODO: ci sono altre azioni da gestire?
    }

    private Date getArrivalTime(Ticket ticket){
        if(ticket.getStatus().equals(invalid)){
            long sum = currentTime.getTime() + ticket.getTimeToReachTheShop().getTime();
            return new Date(sum);
        }
        else{
            return ticket.getArrivalTime();
        }
    }

    private TicketTracker getShortestExitingTime(List<TicketTracker> ticketTrackers){
        TicketTracker shortest = ticketTrackers.get(0);
        for (TicketTracker tt: ticketTrackers) {
            if(getArrivalTime(tt.getTicket()).before(getArrivalTime(shortest.getTicket()))){
                shortest = tt;
            }
        }
        return shortest;
    }

    // if the time slot exists in the timeline, returns the position of the time slot in the ArrayList of the
    // time line
    // else returns the index in the time line of the next time slot or -1 if it has no successor.
    private int existTimeSlot(int timeSlotId) {
        for (int i = 0, timeLineSize = timeLine.size(); i < timeLineSize; i++) {
            TimeSlot timeSlot = timeLine.get(i);
            if (timeSlot.getId() == timeSlotId) {
                return i;
            } else if (timeSlot.getId() > timeSlotId) {
                return i;
            }
        }
        return -1;
    }

    // returns the time slot with the corresponding id from the timeLine ;
    // if the time slot doesn’t exist , create one , add it in order to the timeLine , and return it.
    private TimeSlot getTimeSlot(int timeSlotId) {
        TimeSlot returnValue;
        int timeLineIndex = existTimeSlot(timeSlotId);
        if (timeLineIndex == -1) {
            returnValue = new TimeSlot(timeSlotId);
            timeLine.add(returnValue);
        } else {
            if (timeLine.get(timeLineIndex).getId() == timeSlotId) {
                returnValue = timeLine.get(timeLineIndex);
            } else {
                returnValue = new TimeSlot(timeSlotId);
                timeLine.add(timeLineIndex, returnValue);
            }
        }
        return returnValue;
    }

    // given a date calculate the time slot it is in.
    // return 0 if it is in the time slot starting in current time
    // return -1 if it is in the time slot ending in current time
    // return -2 if the time slot is in the past (at least a timeslot duration before the currentTime)
    private int calculateTimeSlotId(Date date) {
        long timeSlotDurationInMilliSeconds = (shop.getTimeslotMinutesDuration() * 60000L);
        Date maxPastTime = new Date(currentTime.getTime() - timeSlotDurationInMilliSeconds);

        if (date.before(maxPastTime)) {
            return -2;
        }

        if (date.before(currentTime)) {
            return -1;
        }

        int timeSlotId;
        int counter = 0;
        Date startTimeSlot = new Date();
        Date endTimeSlot = new Date(startTimeSlot.getTime() + timeSlotDurationInMilliSeconds);
        while (true) {
            if (date.after(startTimeSlot) && date.before(endTimeSlot)) {
                timeSlotId = counter;
                break;
            }
            counter++;
            startTimeSlot = new Date(startTimeSlot.getTime() + timeSlotDurationInMilliSeconds);
            endTimeSlot = new Date(startTimeSlot.getTime() + timeSlotDurationInMilliSeconds);
        }

        return timeSlotId;
    }

    /* TODO:
    private boolean checkIfInsideShift(Ticket ticket) {
        // given a ticket it checks if its scheduledEnterignTime and scheduledExitingTime are compatible with at least
        // one shopShift.
        return true;
    }
    */


    /*auxiliar methods for debugging*/
    private void printTimeLine(List<TimeSlot> tl) {
        if (timeLine.isEmpty()) {
            System.out.println("null");
        } else {
            System.out.println("[");
            for (int i = 0; i < tl.size(); i++) {
                TimeSlot ts = tl.get(i);
                printTimeSlot(ts);
                if (!(i == tl.size() - 1)) {
                    System.out.println(",");
                }
            }
            System.out.println("]");
        }
    }

    private void printTimeSlot(TimeSlot ts) {
        System.out.println("{");
        System.out.println("\"id\":" + ts.getId() + ",");
        System.out.println("\"startingTime\":" + (ts.getStartingTime() != null ? "\"" + ts.getStartingTime() + "\"" : "null") + ",");
        System.out.println("\"endingTime\":" + (ts.getEndingTime() != null ? "\"" + ts.getEndingTime() + "\"" : "null") + ",");
        System.out.println("\"expectedEnteringTickets\":");
        printTicketTrackerList(ts.getTickets());
        System.out.println(",");
        System.out.println("\"expectedExitingTickets\":");
        printTicketTrackerList(ts.getExpectedExitingTickets());
        System.out.println("}");
    }

    private void printTicketTrackerList(List<TicketTracker> ticketTrackers) {
        if (ticketTrackers.isEmpty()) {
            System.out.println("null");
        } else {
            System.out.println("[");
            for (int i = 0; i < ticketTrackers.size(); i++) {
                TicketTracker tt = ticketTrackers.get(i);
                printTicketTracker(tt);
                if (!(i == ticketTrackers.size() - 1)) {
                    System.out.println(",");
                }
            }
            System.out.println("]");
        }
    }

    private void printTicketTracker(TicketTracker tt) {
        System.out.println("{");
        System.out.println("\"previousMatchingTicket\":" + ((tt.getMatchingPreviousTicket() != null && !tt.getMatchingPreviousTicket().getStatus().equals(placeholder)) ? tt.getMatchingPreviousTicket().getId() : "null") + ",");
        System.out.println("\"followingMatchingTicket\": " + ((tt.getMatchingFollowingTicket() != null && !tt.getMatchingFollowingTicket().getStatus().equals(placeholder)) ? tt.getMatchingFollowingTicket().getId() : "null") + ",");
        System.out.println("\"Ticket\":");
        printTicket(tt.getTicket());
        System.out.println("}");
    }

    private void printTicketList(List<Ticket> tickets) {
        if (tickets.isEmpty()) {
            System.out.println("null");
        } else {
            System.out.println("[");
            for (int i = 0; i < tickets.size(); i++) {
                Ticket t = tickets.get(i);
                printTicket(t);
                if (!(i == tickets.size() - 1)) {
                    System.out.println(",");
                }
            }
            System.out.println("]");
        }
    }

    private void printTicket(Ticket t) {
        if (t != null) {
            System.out.println("{");
            System.out.println("\"id\":" + t.getId() + ",");
            System.out.println("\"status\":" + (t.getStatus() != null ? "\"" + t.getStatus() + "\"" : "null") + ",");
            System.out.println("\"duration\":" + (t.getExpectedDuration() != null ? "\"" + t.getExpectedDuration() + "\"" : "null") + ",");
            System.out.println("\"ttr\":" + (t.getTimeToReachTheShop() != null ? "\"" + t.getTimeToReachTheShop() + "\"" : "null") + ",");
            System.out.println("\"enterTime\":" + (t.getEnterTime() != null ? "\"" + t.getEnterTime() + "\"" : "null") + ",");
            System.out.println("\"exit\":" + (t.getExitTime() != null ? "\"" + t.getExitTime() + "\"" : "null") + ",");
            System.out.println("\"scheduledEnter\":" + (t.getScheduledEnteringTime() != null ? "\"" + t.getScheduledEnteringTime() + "\"" : "null") + ",");
            System.out.println("\"scheduledExit\":" + (t.getScheduledExitingTime() != null ? "\"" + t.getScheduledExitingTime() + "\"" : "null"));
            System.out.println("}");
        } else {
            System.out.println("null");
        }
    }
}
