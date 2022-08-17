package com.chris.namdahtours_destination.Service;

import com.chris.namdahtours_destination.Model.Destination;
import com.chris.namdahtours_destination.Repository.DestinationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.util.List;

@Slf4j
@Service
public class DestinationService {
    private DestinationRepository destinationRepository;

    @Autowired
    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    /**
     * This service will perform
     * CRUD on destination
     * get destination(s)
     */

    //CREATE
    public String addDestination(Destination destination){
        destinationRepository.save(destination);
        log.info("destination saved");
        return "destination saved";
    }

    //GET DESTINATION
    public Destination getDestination(int id) throws InstanceNotFoundException {
        log.info("getting destination with id: ?", id);
        return destinationRepository.findById(id)
                .orElseThrow(()-> new InstanceNotFoundException("destination with such id not found"));
    }

    //GET ALL DESTINATIONS
    public List<Destination> getAllDestinations(){
        log.info("getting all destinations");
        return destinationRepository.findAll();
    }

    //UPDATE DESTINATION
    public String updateDestination(int id, String name, String location) throws InstanceNotFoundException {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(()-> new InstanceNotFoundException("destination with id not found"));

        if (name != null){
            destination.setName(name);
        }
        if (location != null){
            destination.setLocation(location);
        }
        destinationRepository.save(destination);
        log.info("destination details updated");

        return "destination details updated";
    }

    //DELETE DESTINATION
    public String deleteDestination(int id) throws InstanceNotFoundException {
        Boolean isDestinationPresent = destinationRepository.existsById(id);
        if (!isDestinationPresent){
            log.error("destination not found");
            throw new InstanceNotFoundException("destination with id not found");
        }else {
            destinationRepository.deleteById(id);
            log.info("destination deleted");
        }
        return "destination deleted";
    }
}
