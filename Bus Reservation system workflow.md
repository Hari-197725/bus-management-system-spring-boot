\= Bus Reservation System - Development Workflow

## Project Goal

Build an intermediate-level Bus Reservation System using Spring Boot by completing one module at a time. Each module should be independently testable before moving to the next.

---

# Development Order

```
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
```

---

# Phase 1 — User Management

## Goal

Manage customer information.

### Entity

```
User
----
id
name
email
phone
createdAt
updatedAt
```

### APIs

```
POST   /users
GET    /users
GET    /users/{id}
PUT    /users/{id}
DELETE /users/{id}
```

### Completion Checklist

- User Entity
- Repository
- Service
- Controller
- DTO
- Validation
- Exception Handling
- Unit Testing

---

# Phase 2 — Bus Management

## Goal

Manage buses.

### Entity

```
Bus
---
id
busNumber
busType
totalSeats
operatorName
status
```

### APIs

```
POST   /buses
GET    /buses
GET    /buses/{id}
PUT    /buses/{id}
DELETE /buses/{id}
```

### Completion Checklist

- CRUD
- Validation
- Duplicate Bus Number Check

---

# Phase 3 — Route Management

## Goal

Create travel routes.

### Entity

```
Route
-----
id
source
destination
distance
estimatedDuration
```

### APIs

```
POST   /routes
GET    /routes
GET    /routes/{id}
PUT    /routes/{id}
DELETE /routes/{id}
```

### Completion Checklist

- CRUD
- Validation

---

# Phase 4 — Schedule Management

## Goal

Assign buses to routes on specific dates.

### Entity

```
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
```

### APIs

```
POST   /schedules
GET    /schedules
GET    /schedules/{id}
PUT    /schedules/{id}
DELETE /schedules/{id}

GET    /schedules/search
```

### Search Parameters

```
source
destination
travelDate
```

### Completion Checklist

- CRUD
- Search API
- Bus ↔ Route Relationship

---

# Phase 5 — Seat Management

## Goal

Manage seats for every bus.

### Entity

```
Seat
----
id
busId
seatNumber
seatType
status
```

### Seat Status

```
AVAILABLE
BOOKED
BLOCKED
```

### APIs

```
POST   /seats
GET    /seats
GET    /seats/{id}
PUT    /seats/{id}
DELETE /seats/{id}

GET    /buses/{id}/seats
GET    /schedules/{id}/available-seats
```

### Completion Checklist

- CRUD
- Seat Availability

---

# Phase 6 — Booking Management

## Goal

Reserve seats for customers.

### Booking Entity

```
Booking
-------
id
userId
scheduleId
bookingDate
status
totalAmount
```

### Booking Status

```
PENDING
CONFIRMED
CANCELLED
```

### Booking Detail

```
BookingDetail
-------------
id
bookingId
seatId
passengerName
age
gender
```

### APIs

```
POST   /bookings
GET    /bookings
GET    /bookings/{id}
DELETE /bookings/{id}

GET    /users/{id}/bookings
```

### Booking Workflow

```
Search Schedule

↓

Select Schedule

↓

View Available Seats

↓

Select Seats

↓

Create Booking

↓

Update Seat Status

↓

Booking Confirmed
```

### Completion Checklist

- Seat Availability Validation
- Prevent Double Booking
- Transaction Management

---

# Phase 7 — Payment (Mock)

## Goal

Record payment information.

### Entity

```
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

### Payment Status

```
SUCCESS
FAILED
PENDING
```

### APIs

```
POST   /payments

GET    /payments

GET    /payments/{id}
```

### Workflow

```
Booking

↓

Payment

↓

Update Booking Status
```

---

# Phase 8 — Ticket Management

## Goal

Generate tickets.

### Entity

```
Ticket
------
id
bookingId
ticketNumber
issuedAt
```

### APIs

```
GET /tickets/{id}

GET /bookings/{id}/ticket
```

### Completion Checklist

- Ticket Generation
- Ticket Lookup

---

# Phase 9 — Cancellation

## Goal

Cancel bookings.

### Entity

```
Cancellation
------------
id
bookingId
cancelledAt
reason
refundAmount
```

### APIs

```
POST /bookings/{id}/cancel
```

### Workflow

```
Cancel Booking

↓

Release Seats

↓

Update Booking Status

↓

Create Cancellation Record
```

---

# Phase 10 — Reports

## Goal

Generate business reports.

### APIs

```
GET /reports/bookings

GET /reports/revenue

GET /reports/popular-routes

GET /reports/bus-occupancy
```

---

# Suggested Package Structure

```
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
│   ├── exception
│   ├── validation
│   ├── config
│   └── util
│
└── BusReservationApplication
```

Each module should contain:

```
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

---

# Database Relationships

```
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

---

# Final Development Checklist

- [ ] User CRUD
- [ ] Bus CRUD
- [ ] Route CRUD
- [ ] Schedule CRUD
- [ ] Seat CRUD
- [ ] Search Schedule
- [ ] Booking Module
- [ ] Prevent Double Booking
- [ ] Mock Payment
- [ ] Ticket Generation
- [ ] Booking Cancellation
- [ ] Reports
- [ ] Global Exception Handling
- [ ] Request Validation
- [ ] API Documentation