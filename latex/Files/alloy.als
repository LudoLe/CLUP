lenght in the document:
open util / time
open util / time
open util / boolean
open util / ordering[Ticket] as ord

sig Position {
    latitude:one Int,
    longitude:one Int
}

abstract sig User {
    username:one Int,
    userTickets:set Ticket
} {
    username > 0
}

sig AppUser extends User {
    position:Position one -> Time
}

sig Totem extends User {}

sig Shop {
    name:one Int,
    maxCapacityShop:one Int,
    shopArea:set Area,
    shopTickets:set Ticket,
    shopTotems:set Totem,
    shopStatus:ShopStatus lone -> Time
} {
    maxCapacityShop > 0
    name > 0
}

abstract sig ShopStatus {}
one sig Close extends ShopStatus {}
one sig Open extends ShopStatus {}

sig Area {
    name:one Int,
    maxCapacityArea:one Int,
    areaItems:set Item
} {
    maxCapacityArea > 0
    name > 0
}

sig Item {
    name:one Int,
} {
    name > 0
}

abstract sig TicketStatus {}
one sig InUse extends TicketStatus {}
one sig Expired extends TicketStatus {}
one sig Valid extends TicketStatus {}

abstract sig Ticket {
    id:one Int,
    ticketStatus:TicketStatus lone -> Time,
    ticketArea:set Area
} {
    id > 0
}

sig VisitTicket extends Ticket {}
sig QueueTicket extends Ticket {}

-- -- STATIC MODEL-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

-- -- UNIQUENESS OF KEYS-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

fact ticketIdIsUnique {
    no disjoint t1, t2:Ticket | t1.id = t2.id
}

fact usernameIsUnique {
    no disjoint u1, u2:User | u1.username = u2.username
}

fact shopNameIsUnique {
    no disjoint s1, s2:Shop | s1.name = s2.name
    and no user:User, shop:Shop | user.username = shop.name
    and no ticket:Ticket, shop:Shop | ticket.id = shop.name and ticket.id = user.username
}

fact usernameIsUnique {
    no disjoint u1, u2:User | u1.username = u2.username
}

-- -- UNIQUENESS OF KEYS-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

-- -- begin TICKET constraints-- -- -- -- -- -- -- -- -- -- -- -- -- -- --

--Valid: the QR-code associated is scannable
--InUse: The QR-code associated has been scanned once
--Expired: QR-code associated no longer scannable

fact ticketStatusChart {
    --A ticket is always created as Valid
    all t:Ticket | one t1:Time | t.ticketStatus.t1 = Valid
    all t:Ticket, t2:Time |
        --Once a ticket is expired, it cannot change status
		(t.ticketStatus.t2 = Expired implies all t3:Time | gte[t3, t2] implies t.ticketStatus.t3 = Expired)
		and
		--Once a ticket is "InUse" it cannot go back to "Valid"
        (t.ticketStatus.t2 = InUse implies all t4:Time | gte[t4, t2] implies t.ticketStatus.t4 != Valid)
}

--all of the tickets has timestamp associated to an openState of a shop
fact ticketsToOpenShop {
    all ticket:Ticket | all t1:Time | (ticket.ticketStatus.t1 = Valid or ticket.ticketStatus.t1 = Expired or ticket.ticketStatus.t1 = InUse)
    implies
        (
            some shop:Shop | shop.shopStatus.t1 = Open and ticket in shop.shopTickets
        )
}

--ticket associated to Totems are visit ticket
fact VisitTicketToTotems {
    all totem:Totem | totem.userTickets = VisitTicket
}

--i ticket generati dal totem sono necessariamente associati allo shop a cui e' associato il totem
fact totemTicketTotemShop {
    all ticket:Ticket, shop:Shop, totem:Totem | (ticket in totem.userTickets and totem in shop.shopTotems) implies ticket in shop.shopTickets
}

--no ticket that isnt associated to a user and to a shop
fact ticketToUserantoShop {
    all ticket:Ticket | one user:User, shop:Shop | ticket in user.userTickets and ticket in shop.shopTickets
}


-- -- -- -- -- -- -- -- -- -- --end TICKET constraints-- -- -- -- -- -- --

-- -- -- -- -- -- -- -- -- -- --begin USER constraints-- -- -- -- -- -- --

--no interested in users no associated to tickets
fact noUserToTicket {}

--each totem must be associated to a shop
fact totemToShop {
    all totem:Totem | one shop:Shop | totem in shop.shopTotems
}

--two useres cannot have the same ticket
fact twoUsersDifferetTickets {
    no disjoint u1, u2:User, t:Ticket | t in u1.userTickets and t in u2.userTickets
}

--a user cannot have two "InUse" tickets active at the same time
fact noTwoTicketsAtTheSameTimeInUsePerUser {
    no timestamp:Time | one user:User | all t1, t2:Ticket | t1 in user.userTickets and t2 in user.userTickets and t1.ticketStatus.timestamp = InUse and t2.ticketStatus.timestamp = InUse
}

--personalmente introdurrei qualcosa tipo che un utente puo' avere al massimo tot ticket, ma vedi te
fact noMoreThan3TicketsAtTheTime {
    no user:User | #(user.userTickets) > 4
}
--a user can only be associated to a queue ticket per time
fact onlyOneQueueTicketPerUser {
    all user:User | no disjoint t1, t2:QueueTicket | t1 in user.userTickets and t2 in user.userTickets
}

-- -- -- -- -- -- -- -- -- -- -- end USER constraints -- -- -- -- -- -- --

-- -- -- -- -- -- -- -- -- -- --begin SHOP constraints-- -- -- -- -- -- --

--the number of "InUse" tickets associated to a shop must be fewer than the MAX CAPACITY of the shop at all times
fact inUseTicketsInAShopLessThanMaxCapacity {
    all s:Shop | let x = s.shopTickets | all timeStamp:Time | x.ticketStatus.timeStamp = InUse implies gte[s.maxCapacityShop, #x]
}

--a totem belongs to one and just one shop
fact oneTotemOneShop {
    all totem:Totem | no disjoint shop1, shop2:Shop | totem in shop1.shopTotems and totem in shop2.shopTotems
}

-- -- -- -- -- -- -- -- -- -- -- end SHOP constraints -- -- -- -- -- -- --

-- -- -- -- -- -- -- -- -- -- --begin AREA & ITEMS constraints -- -- -- --

--each area must be associated to a shop
fact areaToShop {
    all area:Area | one shop:Shop | area in shop.shopArea
}

--each item must be associated to an area
fact itemToArea {
    all item:Item | one area:Area | item in area.areaItems
}

--an item belongs to one and only one Area
fact oneItemOneShop {
    all item:Item | no disjoint area1, area2:Area | item in area1.areaItems and item in area2.areaItems
}

--each item must be associated to an area
fact disjointCAPACITY {
    no disjoint area1, area2:Area | area1.maxCapacityArea = area2.maxCapacityArea
}

--the numbers of area associated to a ticket must be less or equal than all of the areas
fact areaToShop {
    all ticket:Ticket | let area = ticket.ticketArea | #area > 0
}
--an area belongs to one and only one shop
fact oneAreaOneShop {
    all area:Area | no disjoint s1, s2:Shop | area in s1.shopArea and area in s2.shopArea
}

--each area has a max capiency.the sum of the max capiency of all of the areas of a shop must be the same as the max capiency of the shop
fact sumCapiencyAreasEqualToCapiencyShop {
    all shop:Shop | shop.maxCapacityShop = sum(shop.shopArea.maxCapacityArea)
}

--the number of "InUse" tickets associated to a area must be fewer than the MAX CAPACITY of the area at all times
fact inUseTicketsInAShopLessThanMaxCapacity {
    all ticket:Ticket | let areas = ticket.ticketArea | all area:Area, time:Time | area in areas and ticket.ticketStatus.time = InUse implies #ticket <= area.maxCapacityArea
}

--every ticket is associated to an area and to a shop:the area to which that ticket is associated must belong to the shop the ticket is associated
fact ticketToAreaToShop {
    all ticket:Ticket, area:Area, shop:Shop | (area in ticket.ticketArea and ticket in shop.shopTickets) implies(area in shop.shopArea)
}

-- -- -- -- -- -- -- -- -- -- -- -- --end AREA & ITEMS constraints-- -- --

-- -- -- -- -- -- -- -- -- -- -- -- --MODELLO DINAMICO-- -- -- -- -- -- --


pred isShopFull[s:Shop, t:Time, x:s.shopTickets] {
    s.shopStatus.t = Open and x.ticketStatus.t = InUse and #x = s.maxCapacityShop
}

pred isShopOpen[s:Shop, t:Time] {
    s.shopStatus.t = Open
}
pred isATicketInUse[t:Ticket, time:Time] {
    t.ticketStatus.time = InUse
}

pred hasAUserAQueueTicket[user:User, t:QueueTicket] {
    t in user.userTickets
}

pred userEnqueue[shop:Shop, user:User, time:Time, ticket, ticket2:QueueTicket] {
// preconditions
    not isShopFull[shop, time, shop.shopTickets]
    not hasAUserAQueueTicket[user, ticket2]
// postconditions
    ticket in shop.shopTickets
    ticket in user.userTickets
    ticket.ticketStatus.time = Valid
}

pred userBookAVisit[shop:Shop, user:User, time:Time, ticket:VisitTicket] {
// preconditions
    not isShopFull[shop, time, shop.shopTickets]
// postconditions
    ticket in shop.shopTickets
    ticket in user.userTickets
    ticket.ticketStatus.time = Valid
}

pred userCancelAEnqueuement[shop:Shop, user:User, time:Time, ticket:QueueTicket] {
// preconditions
    isShopOpen[shop, time]
// postconditions
    ticket in shop.shopTickets
    ticket in user.userTickets
    ticket.ticketStatus.time = Expired
}

pred userCancelAVisit[shop:Shop, user:User, time:Time, ticket:VisitTicket] {
// postconditions
    ticket in shop.shopTickets
    ticket in user.userTickets
    ticket.ticketStatus.time = Expired
}

pred show {
    #Shop = 1
    #Totem = 1
    #User >= 1
    #Shop.shopTickets >= 1
    #User.userTickets >= 1
    #QueueTicket >= 1
    #VisitTicket > 1
    #InUse = 1
    #Expired = 1
    #Valid = 1
    #Area >= 1
    #Item >= 1
}