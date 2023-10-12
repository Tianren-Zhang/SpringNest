package com.laioffer.springnest.service;

import com.laioffer.springnest.exception.StayDeleteException;
import com.laioffer.springnest.exception.StayNotExistException;
import com.laioffer.springnest.model.*;
import com.laioffer.springnest.repository.LocationRepository;
import com.laioffer.springnest.repository.ReservationRepository;
import com.laioffer.springnest.repository.StayRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StayService {


    private final ImageStorageService imageStorageService;
    private final StayRepository stayRepository;
    private final GeoCodingService geoCodingService;
    private final LocationRepository locationRepository;
    private final ReservationRepository reservationRepository;


    public StayService(ImageStorageService imageStorageService, StayRepository stayRepository, GeoCodingService geoCodingService, LocationRepository locationRepository, ReservationRepository reservationRepository) {
        this.imageStorageService = imageStorageService;
        this.stayRepository = stayRepository;
        this.geoCodingService = geoCodingService;
        this.locationRepository = locationRepository;
        this.reservationRepository = reservationRepository;
    }

    // It uses the stayRepository to fetch the data based on the provided username.
    public List<Stay> listByUser(String username) {
        return stayRepository.findByHost(new User.Builder().setUsername(username).build());
    }

    // Fetches a stay by its ID and associated host or user. If the stay doesn't exist, it throws a StayNotExistException.
    public Stay findByIdAndHost(Long stayId, String username) throws StayNotExistException {
        Stay stay = stayRepository.findByIdAndHost(stayId, new User.Builder().setUsername(username).build());
        if (stay == null) {
            throw new StayNotExistException("Stay doesn't exist");
        }
        return stay;
    }

    //  It processes the given images, associates them with the stay, saves the stay in the repository,
    //  and also uses the geocoding service to get location data and save it.
    @Transactional
    public void add(Stay stay, MultipartFile[] images) {
        List<StayImage> stayImages = Arrays.stream(images)
                .filter(image -> !image.isEmpty())
                .parallel()
                .map(imageStorageService::save)
                .map(mediaLink -> new StayImage(mediaLink, stay))
                .collect(Collectors.toList());
        stay.setImages(stayImages);
        stayRepository.save(stay);


        Location location = geoCodingService.getLatLng(stay.getId(), stay.getAddress());
        locationRepository.save(location);
    }

    // This method deletes a stay with the given ID and associated host (user).
    // Before deletion, checks if the stay exists and if there are any active reservations for the stay.
    public void delete(Long stayId, String username) throws StayNotExistException, StayDeleteException {
        Stay stay = stayRepository.findByIdAndHost(stayId, new User.Builder().setUsername(username).build());
        if (stay == null) {
            throw new StayNotExistException("Stay doesn't exist");
        }


        List<Reservation> reservations = reservationRepository.findByStayAndCheckoutDateAfter(stay, LocalDate.now());
        if (reservations != null && !reservations.isEmpty()) {
            throw new StayDeleteException("Cannot delete stay with active reservation");
        }
        stayRepository.deleteById(stayId);
    }
}
