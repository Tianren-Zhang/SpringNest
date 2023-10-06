package com.laioffer.springnest.repository;

import com.laioffer.springnest.model.Stay;
import com.laioffer.springnest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

// Spring Data JPA automatically provides implementations for these methods
@Repository
public interface StayRepository extends JpaRepository<Stay, Long> {


    List<Stay> findByHost(User user);


    Stay findByIdAndHost(Long id, User host);


    List<Stay> findByIdInAndGuestNumberGreaterThanEqual(List<Long> ids, int guestNumber);


}

