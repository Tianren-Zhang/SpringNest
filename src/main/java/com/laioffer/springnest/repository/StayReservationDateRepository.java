package com.laioffer.springnest.repository;

import com.laioffer.springnest.model.StayReservedDate;
import com.laioffer.springnest.model.StayReservedDateKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;

// Provides a mechanism to interact with the database and retrieve StayReservedDate entities based on specific criteria without needing to write the actual database interaction code.
@Repository
public interface StayReservationDateRepository extends JpaRepository<StayReservedDate, StayReservedDateKey> {

    // To retrieve stay_id values from the StayReservedDate entity where the stay_id matches one of the provided list and the date is between two given dates.
    @Query(value = "SELECT srd.id.stay_id FROM StayReservedDate srd WHERE srd.id.stay_id IN ?1 AND srd.id.date BETWEEN ?2 AND ?3 GROUP BY srd.id.stay_id")
    Set<Long> findByIdInAndDateBetween(List<Long> stayIds, LocalDate startDate, LocalDate endDate);
}


