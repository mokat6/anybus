package info.anyksciaibus.services;

import info.anyksciaibus.entities.Trip1Way;
import info.anyksciaibus.repositories.Trip1WayRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Trip1WayService {

    Trip1WayRepo repo;

    public Trip1WayService(Trip1WayRepo repo){
        this.repo = repo;
    }

    public List<Trip1Way> getAll() {
        return repo.findAll();
    }

    public Optional<Trip1Way> get1Trip1WayById(Long id) {
        return repo.findById(id);
    }

    public List<Trip1Way> saveAll(List<Trip1Way> trip1WayList) {
        return repo.saveAll(trip1WayList);
    }



}
