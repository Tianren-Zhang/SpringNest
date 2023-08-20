package com.laioffer.springnest.service;

import com.laioffer.springnest.exception.StayNotExistException;
import com.laioffer.springnest.model.Stay;
import com.laioffer.springnest.model.StayImage;
import com.laioffer.springnest.model.User;
import com.laioffer.springnest.repository.StayRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StayService {


    private final ImageStorageService imageStorageService;
    private final StayRepository stayRepository;


    public StayService(ImageStorageService imageStorageService, StayRepository stayRepository) {
        this.imageStorageService = imageStorageService;
        this.stayRepository = stayRepository;
    }


    public List<Stay> listByUser(String username) {
        return stayRepository.findByHost(new User.Builder().setUsername(username).build());
    }


    public Stay findByIdAndHost(Long stayId, String username) throws StayNotExistException {
        Stay stay = stayRepository.findByIdAndHost(stayId, new User.Builder().setUsername(username).build());
        if (stay == null) {
            throw new StayNotExistException("Stay doesn't exist");
        }
        return stay;
    }


    public void add(Stay stay, MultipartFile[] images) {
        List<StayImage> stayImages = Arrays.stream(images)
                .filter(image -> !image.isEmpty())
                .parallel()
                .map(imageStorageService::save)
                .map(mediaLink -> new StayImage(mediaLink, stay))
                .collect(Collectors.toList());
        stay.setImages(stayImages);
        stayRepository.save(stay);
    }


    public void delete(Long stayId, String username) throws StayNotExistException {
        Stay stay = stayRepository.findByIdAndHost(stayId, new User.Builder().setUsername(username).build());
        if (stay == null) {
            throw new StayNotExistException("Stay doesn't exist");
        }
        stayRepository.deleteById(stayId);
    }
}



