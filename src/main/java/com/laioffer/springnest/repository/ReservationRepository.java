package com.laioffer.springnest.repository;

import com.laioffer.springnest.model.Reservation;
import com.laioffer.springnest.model.Stay;
import com.laioffer.springnest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

// Provide 4 methods that find the Reservation in several cases
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    List<Reservation> findByGuest(User guest);


    List<Reservation> findByStay(Stay stay);


    Reservation findByIdAndGuest(Long id, User guest);


    List<Reservation> findByStayAndCheckoutDateAfter(Stay stay, LocalDate date);
}

