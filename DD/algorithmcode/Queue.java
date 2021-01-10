public class Queue{

    //output of the algorithm
    private ArrayList<TimeSlot> timeLine = new ArrayList<TimeSlot>();

    //quantity of tickets that can be inside the shop at the same time
    private Integer shopCapacity;

    //In this range of time slots the algorithm will try to find a one-to-one correspondence with a visit ticket and an exiting ticket
    private Integer visitRange;

    public Queue(Integer shopCapacity, Integer visitRange, Arraylist<Ticket> InShopTickets,
                 ArrayList<Ticket> visitTickets, ArrayList<Ticket> queueTickets){
        this.shopCapacity = shopCapacity;
        this.visitRange = visitRange;

        this.buildQueue(InShopTickets, visitTickets, queueTickets);
    }

    //auxiliary methods
    private Integer existTimeSlot(Integer id){
        //if it exists, returns the position of the time slot in the ArrayList of the time line, returns -1 if the time slot doesn't exist.
    }
    private TimeSlot getTimeSlot(Integer id){
        //returns the time slot with the corresponding id from the timeLine;
        //if the time slot doesn't exist, create one, add it to the timeLine, and returns it.
    }

    //ALGORITHM
    private void buildQueue(Arraylist<Ticket> InShopTickets, ArrayList<Ticket> visitTickets, ArrayList<Ticket> queueTickets){

        //represents the beginning of the time line
        TimeSlot firstTimeSlot = this.getTimeSlot(0);

        //if the shop is not full we place an (placehoder) exiting ticket in the first time slot for each empty entry
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

        //for each visit ticket
        for(Ticket visitTicket: visitTickets){
            //add the visit ticket entry time in the corresponding scheduled time slot
            this.getTimeSlot(visitTicket.getScheduledTimeSlot()).addVisitTicket(visitTicket);

            //calculate and add the visit ticket expected exiting time in the corresponding scheduled time slot
            this.getTimeSlot(visitTicket.getScheduledTimeSlot() + visitTicket.getExpectedDuration()).addExpectedExitingTicket(visitTicket);
        }

        //loop throught all the time slots in the time line, and, 
        //reserve each exiting ticket for a visit ticket in range
        //if there is any, otherwise, if possible, insert an entering queue ticket
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
                                //jump to the next iteration of the outer loop, since we are done analyzing the visit ticket (refactor in multiple methods is recommended, here is like this for readability)
                                continue outerLoop;
                            }
                        }
                    }
                }

                //if the following code is running it means that there was no visit ticket in visitRange, so we can handle a queue ticket

                //loops through all the queue tickets to find one to place in the following time slot and to pair with the exiting ticket we are analyzing,
                //also the choosen queue ticket to place must be able to reach the shop before his turn arrives
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

                    //delete the queue ticket from the list of queue tickets, since it has been correctly placed in the time line
                    queueTickets.remove(choosenQueueTicket);
                }

            }

        }
    }

    //getters and setters...

    //methods to calculate estimates and usefull information about the time line
}