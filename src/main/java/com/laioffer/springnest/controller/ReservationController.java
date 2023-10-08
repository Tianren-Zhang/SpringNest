package com.laioffer.springnest.controller;

import com.laioffer.springnest.exception.InvalidReservationDateException;
import com.laioffer.springnest.model.Reservation;
import com.laioffer.springnest.model.User;
import com.laioffer.springnest.service.ReservationService;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

// RESTful web controller that handles CRUD operations for reservations
@RestController
public class ReservationController {
    private final ReservationService reservationService;


    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // It returns a list of reservations for a specific guest.
    @GetMapping("/reservations")
    public List<Reservation> listReservations(Principal principal) {
        return reservationService.listByGuest(principal.getName());
    }

    // This method defines a POST request endpoint to add a reservation.
    // The method checks the validity of the check-in and check-out dates,
    // throws an exception if they're invalid, and then sets the guest and adds the reservation.
    @PostMapping("/reservations")
    public void addReservation(@RequestBody Reservation reservation, Principal principal) {
        LocalDate checkinDate = reservation.getCheckinDate();
        LocalDate checkoutDate = reservation.getCheckoutDate();
        if (!checkinDate.isBefore(checkoutDate) || checkinDate.isBefore(LocalDate.now())) {
            throw new InvalidReservationDateException("Invalid date for reservation");
        }
        reservation.setGuest(new User.Builder().setUsername(principal.getName()).build());
        reservationService.add(reservation);
    }

    // This method defines a DELETE request endpoint to remove a reservation based on its ID.
    @DeleteMapping("/reservations/{reservationId}")
    public void deleteReservation(@PathVariable Long reservationId, Principal principal) {
        reservationService.delete(reservationId, principal.getName());
    }
}

