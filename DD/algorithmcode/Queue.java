public class Queue{

    private ArrayList<TimeSlot> timeLine = new ArrayList<TimeSlot>();

    //quantity of tickets that can be inside the shop at the same time
    private Integer shopCapacity;

    //amount of time slots in which to look for a ticket that leaves the store and mark it as linked to the entrance to a visit ticket
    //In this range of time slots a visit ticket has priority over queue tickets
    private Integer visitRange;

    public Queue(Integer shopCapacity, Integer visitRange, Arraylist<Ticket> InShopTickets,
                 ArrayList<Ticket> visitTickets, ArrayList<Ticket> queueTickets){
        this.shopCapacity = shopCapacity;
        this.visitRange = visitRange;

        this.buildQueue(InShopTickets, visitTickets, queueTickets);
    }

    private Integer existTimeSlot(Integer id){
        //returns the position of the time slot in the ArrayList of the time line if it exists, returns -1 if the time slot doesn't exist.
    }

    private TimeSlot getTimeSlot(Integer id){
        //uses the existTimeSlot method
        //returns the time slot with the corresponding id from the timeLine;
        //if the time slot doesn't exist, create one, add it to the timeLine, and returns it.
    }

    private void buildQueue(Arraylist<Ticket> InShopTickets, ArrayList<Ticket> visitTickets, ArrayList<Ticket> queueTickets){
        //segnare gli orari di uscita

        //represents the beginning of the time line
        TimeSlot firstTimeSlot = this.getTimeSlot(0);

        //if the shop is not full we place an exiting ticket in the first time slot for each empty entry
        Integer freeShopEntries = this.shopCapacity - InShopTickets.size();
        ArrayList<Ticket> fakeExitingTickets = new ArrayList<Ticket>();
        for (int i = 0; i < freeShopEntries; i++){
            fakeExitingTickets.add(new Ticket());
        }
        firstTimeSlot.addExpectedExitingTickets(fakeExitingTickets);

        //for each ticket inside the shop, calculate the expected exit time and mark the corresponding time slot
        for(Ticket inShopTicket: InShopTickets){
            Integer expectedExitTimeSlotID = inShopTicket.getEnteringTimeSlot + inShopTicket.getExpectedDuration;
            this.getTimeSlot(expectedExitTimeSlotID).addExpectedExitingTicket(inShopTicket);
        }

        for(Ticket visitTicket: visitTickets){
            //add the visit ticket scheduled time in the corresponding scheduled time slot
            this.getTimeSlot(visitTicket.getScheduledTimeSlot()).addVisitTicket(visitTicket);

            //add the visit ticket expected exiting time in the corresponding scheduled time slot
            this.getTimeSlot(visitTicket.getScheduledTimeSlot() + visitTicket.getExpectedDuration()).addExpectedExitingTicket(visitTicket);
        }

        //loop throught all the time slots in the time line, and, for each exiting ticket, reserve it for a visit ticket in range if there is any, otherwise insert an entering queue ticket if possible
        for(int k = 0; k< timeSlot.size(); k++){

            TimeSlot timeSlot = this.timeLine.get(k);

            //all the ticket exiting the shop during this time slot
            ArrayList<Ticket> expectedExitingtickets = timeSlot.getExpectedExitingTicket();

            //for each expected exiting ticket check if there is in range a slot with a visit ticket scheduled that hasn't already been handled
            outerLoop:
            for (Ticket expectedExitingTicket: expectedExitingTickets){

                //loops through the next slots till the visitRange, to find a visit ticket to pair with the exiting ticket
                for(int j = 1; j <= this.visitRange; j++;){
                    if (this.existTimeSlot(timeSlot.getId+j)){
                        //for each visit ticket in the time slot
                        for(Ticket visitTicket: this.getTimeSlot(timeSlot.getId+j).getVisitTickets()){
                            //if the visit ticket has not been handled before, meaning that there is not a matching previous ticket
                            if(visitTicket.getMatchingPreviousTicket() == null){
                                //pair the exiting ticket with the visit ticket
                                expectedExitingTicket.setMatchingFollowingTicket(visitTicket);
                                visitTicket.setMatchingPreviousTicket(expectedExiticgTicket);
                                continue outerLoop; //jump to the next iteration of the outer loop, since we are done analyzing the visit ticket (refactor in multiple methods is recommended, here is like this for readability)
                            }
                        }
                    }
                }

                //if the following coe is running it means that there was no visit ticket in the following slots, so we can handle place a queue ticket

                //loops through the queue tickets in this slot to find a queue ticket to place in the following time slot and to pair with the exiting ticket
                //the choosen queue ticket to place must be able to reach the shop before his turn arrives
                Ticket choosenQueueTicket = null;
                for(Ticket queueTicket: queueTickets){
                    if(queueTicket.getTimeToReachTheShop() <= (timeSlot.getId()+1)){
                        choosenQueueTicket = queueTicket;
                        break;
                    }
                }

                if(choosenQueueTicket != null){
                    //update the time line with the entering time of the queue ticket
                    this.getTimeSlot(timeSlot.getId+1).addQueueTicket(choosenQueueTicket);

                    //update the time line with the expected exiting time of the queue ticket
                    this.getTimeSlot(timeSlot.getId + 1 + choosenQueueTicket.getExpectedDuration())

                    //pair the exiting ticket with the queue ticket
                    choosenQueueTicket.setMatchingPreviousTicket(expectedExitingTicket);
                    expectedExitingTicket.setMatchingFollowingTicket(choosenQueueTicket);

                    //delete the queue ticket from the list of queue tickets, since it has been completely handled
                    queueTickets.remove(choosenQueueTicket);
                }

            }

        }
    }

    //TODO: getters, setters, statistics, informations, etc
}