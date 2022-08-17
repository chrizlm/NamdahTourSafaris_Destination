package com.chris.namdahtours_destination.Repository;

import com.chris.namdahtours_destination.Model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Integer> {
}
