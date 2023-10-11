package com.laioffer.springnest.controller;

import com.laioffer.springnest.model.Reservation;
import com.laioffer.springnest.model.Stay;
import com.laioffer.springnest.model.User;
import com.laioffer.springnest.service.ReservationService;
import com.laioffer.springnest.service.StayService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.security.Principal;
import java.util.List;


@RestController
public class StayController {


    private final StayService stayService;
    private final ReservationService reservationService;


    public StayController(StayService stayService, ReservationService reservationService) {
        this.stayService = stayService;
        this.reservationService = reservationService;
    }

    // Defines an endpoint that handles HTTP GET requests to "/stays".
    // It returns a list of Stay objects associated with the currently authenticated user.
    @GetMapping(value = "/stays")
    public List<Stay> listStays(Principal principal) {
        return stayService.listByUser(principal.getName());
    }

    // Defines an endpoint that handles HTTP GET requests to "/stays/{stayId}".
    // It returns the details of a specific Stay based on the provided stayId and the authenticated user.
    @GetMapping(value = "/stays/{stayId}")
    public Stay getStay(@PathVariable Long stayId, Principal principal) {
        return stayService.findByIdAndHost(stayId, principal.getName());
    }

    // Defines an endpoint that handles HTTP POST requests to "/stays". This is used to add a new Stay entry.
    @PostMapping("/stays")
    public void addStay(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("description") String description,
            @RequestParam("guest_number") int guestNumber,
            @RequestParam("images") MultipartFile[] images,
            Principal principal) {


        Stay stay = new Stay.Builder()
                .setName(name)
                .setAddress(address)
                .setDescription(description)
                .setGuestNumber(guestNumber)
                .setHost(new User.Builder().setUsername(principal.getName()).build())
                .build();
        stayService.add(stay, images);
    }

    // Delete a stay
    @DeleteMapping("/stays/{stayId}")
    public void deleteStay(@PathVariable Long stayId, Principal principal) {
        stayService.delete(stayId, principal.getName());
    }

    // Defines an endpoint that handles HTTP GET requests to "/stays/reservations/{stayId}".
    // It returns a list of Reservation objects associated with a specific Stay.
    @GetMapping(value = "/stays/reservations/{stayId}")
    public List<Reservation> listReservations(@PathVariable Long stayId) {
        return reservationService.listByStay(stayId);
    }
}

