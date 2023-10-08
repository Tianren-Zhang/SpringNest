package com.laioffer.springnest.service;

import com.laioffer.springnest.model.Stay;
import com.laioffer.springnest.repository.LocationRepository;
import com.laioffer.springnest.repository.StayRepository;
import com.laioffer.springnest.repository.StayReservationDateRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// Offers a mechanism to search for stays based on multiple criteria like location, date, and guest numbers.
// Integrates multiple repositories to achieve this comprehensive search functionality.
@Service
public class SearchService {
    private final StayRepository stayRepository;
    private final StayReservationDateRepository stayReservationDateRepository;
    private final LocationRepository locationRepository;


    public SearchService(StayRepository stayRepository, StayReservationDateRepository stayReservationDateRepository, LocationRepository locationRepository) {
        this.stayRepository = stayRepository;
        this.stayReservationDateRepository = stayReservationDateRepository;
        this.locationRepository = locationRepository;
    }


    public List<Stay> search(int guestNumber, LocalDate checkinDate, LocalDate checkoutDate, double lat, double lon, String distance) {
        List<Long> stayIds = locationRepository.searchByDistance(lat, lon, distance);
        if (stayIds == null || stayIds.isEmpty()) {
            return Collections.emptyList();
        }
        Set<Long> reservedStayIds = stayReservationDateRepository.findByIdInAndDateBetween(stayIds, checkinDate, checkoutDate.minusDays(1));
        List<Long> filteredStayIds = stayIds.stream()
                .filter(stayId -> !reservedStayIds.contains(stayId))
                .collect(Collectors.toList());
        return stayRepository.findByIdInAndGuestNumberGreaterThanEqual(filteredStayIds, guestNumber);
    }
}

