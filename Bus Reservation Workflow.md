# Bus Reservation System - Development Workflow

## Project Goal

Build an intermediate-level **Bus Reservation System** using Spring Boot
by implementing one module at a time. Each module should be completed,
tested, and verified before moving to the next.

------------------------------------------------------------------------

# Development Roadmap

    Phase 1
    │
    ├── User Management
    │
    Phase 2
    │
    ├── Bus Management
    │
    Phase 3
    │
    ├── Route Management
    │
    Phase 4
    │
    ├── Schedule Management
    │
    Phase 5
    │
    ├── Seat Management
    │
    Phase 6
    │
    ├── Booking Management
    │
    Phase 7
    │
    ├── Payment (Mock)
    │
    Phase 8
    │
    ├── Ticket Management
    │
    Phase 9
    │
    ├── Cancellation
    │
    Phase 10
    │
    └── Reports

------------------------------------------------------------------------

# Phase 1 --- User Management

## Goal

Manage customer information.

## Entity

``` text
User
----
id
name
email
phone
createdAt
updatedAt
```

## APIs

``` http
POST   /users
GET    /users
GET    /users/{id}
PUT    /users/{id}
DELETE /users/{id}
```

## Checklist

-   User Entity
-   Repository
-   Service
-   Controller
-   DTO
-   Validation
-   Global Exception Handling
-   Unit Testing

------------------------------------------------------------------------

# Phase 2 --- Bus Management

## Goal

Manage buses available for reservation.

## Entity

``` text
Bus
---
id
busNumber
busType
totalSeats
operatorName
status
```

## Bus Status

``` text
ACTIVE
INACTIVE
MAINTENANCE
RETIRED
```

### Status Meaning

  Status        Description                                   Can Be Scheduled
  ------------- --------------------------------------------- ------------------
  ACTIVE        Bus is operational and available for trips.   ✅ Yes
  INACTIVE      Bus is temporarily unavailable.               ❌ No
  MAINTENANCE   Bus is under repair or servicing.             ❌ No
  RETIRED       Bus is permanently removed from service.      ❌ No

> **Note:** A bus status represents the **availability of the vehicle**,
> not the progress of a trip. Trip progress belongs to the **Schedule**
> module.

## APIs

``` http
POST   /buses
GET    /buses
GET    /buses/{id}
PUT    /buses/{id}
DELETE /buses/{id}
```

## Checklist

-   Bus CRUD
-   Validation
-   Unique Bus Number
-   Bus Status Management

------------------------------------------------------------------------

# Phase 3 --- Route Management

## Goal

Manage travel routes.

## Entity

``` text
Route
-----
id
source
destination
distance
estimatedDuration
```

## APIs

``` http
POST   /routes
GET    /routes
GET    /routes/{id}
PUT    /routes/{id}
DELETE /routes/{id}
```

## Checklist

-   Route CRUD
-   Validation

------------------------------------------------------------------------

# Phase 4 --- Schedule Management

## Goal

Assign buses to routes on specific dates.

## Entity

``` text
Schedule
--------
id
busId
routeId
travelDate
departureTime
arrivalTime
fare
availableSeats
status
```

## Schedule Status

``` text
SCHEDULED
BOARDING
DEPARTED
IN_TRANSIT
ARRIVED
CANCELLED
```

### Status Meaning

  Status       Description
  ------------ -----------------------------------------------
  SCHEDULED    Trip is planned but boarding has not started.
  BOARDING     Passengers are boarding the bus.
  DEPARTED     Bus has left the departure point.
  IN_TRANSIT   Bus is currently travelling.
  ARRIVED      Bus has reached the destination.
  CANCELLED    Trip has been cancelled.

## APIs

``` http
POST   /schedules
GET    /schedules
GET    /schedules/{id}
PUT    /schedules/{id}
DELETE /schedules/{id}

GET    /schedules/search
```

### Search Parameters

``` text
source
destination
travelDate
```

## Checklist

-   Schedule CRUD
-   Search API
-   Bus--Route Relationship

------------------------------------------------------------------------

# Phase 5 --- Seat Management

## Goal

Manage seats for each bus.

## Entity

``` text
Seat
----
id
busId
seatNumber
seatType
status
```

## Seat Status

``` text
AVAILABLE
BOOKED
BLOCKED
```

## APIs

``` http
POST   /seats
GET    /seats
GET    /seats/{id}
PUT    /seats/{id}
DELETE /seats/{id}

GET    /buses/{id}/seats
GET    /schedules/{id}/available-seats
```

## Checklist

-   Seat CRUD
-   Seat Availability

------------------------------------------------------------------------

# Phase 6 --- Booking Management

## Goal

Reserve seats for customers.

## Booking Entity

``` text
Booking
-------
id
userId
scheduleId
bookingDate
status
totalAmount
```

## Booking Status

``` text
PENDING
CONFIRMED
CANCELLED
```

## Booking Detail

``` text
BookingDetail
-------------
id
bookingId
seatId
passengerName
age
gender
```

## APIs

``` http
POST   /bookings
GET    /bookings
GET    /bookings/{id}
DELETE /bookings/{id}

GET    /users/{id}/bookings
```

## Booking Workflow

``` text
Search Schedule
        ↓
Select Schedule
        ↓
View Available Seats
        ↓
Choose Seats
        ↓
Create Booking
        ↓
Update Seat Status
        ↓
Booking Confirmed
```

## Checklist

-   Seat Availability Validation
-   Prevent Double Booking
-   Transaction Management

------------------------------------------------------------------------

# Phase 7 --- Payment (Mock)

## Goal

Store payment information.

## Entity

``` text
Payment
-------
id
bookingId
amount
paymentMethod
paymentStatus
paidAt
transactionId
```

## Payment Status

``` text
PENDING
SUCCESS
FAILED
```

## APIs

``` http
POST   /payments
GET    /payments
GET    /payments/{id}
```

## Workflow

``` text
Booking
    ↓
Payment
    ↓
Update Booking Status
```

------------------------------------------------------------------------

# Phase 8 --- Ticket Management

## Goal

Generate reservation tickets.

## Entity

``` text
Ticket
------
id
bookingId
ticketNumber
issuedAt
```

## APIs

``` http
GET /tickets/{id}
GET /bookings/{id}/ticket
```

## Checklist

-   Ticket Generation
-   Ticket Retrieval

------------------------------------------------------------------------

# Phase 9 --- Cancellation

## Goal

Cancel reservations and release seats.

## Entity

``` text
Cancellation
------------
id
bookingId
cancelledAt
reason
refundAmount
```

## APIs

``` http
POST /bookings/{id}/cancel
```

## Workflow

``` text
Cancel Booking
      ↓
Release Seats
      ↓
Update Booking Status
      ↓
Create Cancellation Record
```

------------------------------------------------------------------------

# Phase 10 --- Reports

## Goal

Generate business reports.

## APIs

``` http
GET /reports/bookings
GET /reports/revenue
GET /reports/popular-routes
GET /reports/bus-occupancy
```

------------------------------------------------------------------------

# Recommended Package Structure

``` text
src/main/java
│
├── user
├── bus
├── route
├── schedule
├── seat
├── booking
├── payment
├── ticket
├── cancellation
├── report
│
├── common
│   ├── config
│   ├── exception
│   ├── util
│   └── validation
│
└── BusReservationApplication
```

Each module should follow the same structure:

``` text
module
│
├── controller
├── service
├── repository
├── entity
├── dto
├── mapper
└── exception
```

------------------------------------------------------------------------

# Database Relationships

``` text
User
 │
 └──────────────< Booking >─────────────┐
                                        │
Bus ───────────< Schedule >──────── Route
 │                    │
 │                    │
 └────────< Seat >    │
                      │
                      └────< BookingDetail >──── Seat

Booking
 ├──── Payment
 ├──── Ticket
 └──── Cancellation
```

------------------------------------------------------------------------

# Final Checklist

-   [ ] User CRUD
-   [ ] Bus CRUD
-   [ ] Route CRUD
-   [ ] Schedule CRUD
-   [ ] Seat CRUD
-   [ ] Search Schedule
-   [ ] Booking Management
-   [ ] Prevent Double Booking
-   [ ] Mock Payment
-   [ ] Ticket Generation
-   [ ] Booking Cancellation
-   [ ] Reports
-   [ ] Global Exception Handling
-   [ ] Request Validation
-   [ ] API Documentation
