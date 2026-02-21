package info.anyksciaibus.services;

import jakarta.transaction.Transactional;
import info.anyksciaibus.dto.LinePreviewDto;
import info.anyksciaibus.entities.*;
import info.anyksciaibus.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LineService {

    LineRepo lineRepo;
    BusStopRepo busStopRepo;
    RouteRepo routeRepo;
    ScheduleRepo scheduleRepo;
    Trip1WayRepo trip1WayRepo;

    public LineService(LineRepo lineRepo, BusStopRepo busStopRepo, RouteRepo routeRepo, ScheduleRepo scheduleRepo, Trip1WayRepo trip1WayRepo) {
        this.busStopRepo = busStopRepo;
        this.lineRepo = lineRepo;
        this.routeRepo = routeRepo;
        this.scheduleRepo = scheduleRepo;
        this.trip1WayRepo = trip1WayRepo;
    }


    public List<Line> getAll() {
        return lineRepo.findAll();
    }

    public Optional<Line> get1LineById(Long id) {
        return lineRepo.findById(id);
    }

    public List<Line> saveAll(List<Line> lineList) {
        return lineRepo.saveAll(lineList);
    }


    public boolean hasViolatedFKinTrip(Line line){
        //checking if violates FK constrained in Trip1Way, can't delete route if trip has route.
        List<Route> newRouteList = line.getRoutes();
        List<Trip1Way> ogTrips = trip1WayRepo.findByRouteLine(line);


        for (Trip1Way ogTrip : ogTrips) {
            if ( !newRouteList.contains(ogTrip.getRoute())){
                System.out.println("FOREIGN KEY got violated - can't delete routes because used in trip1way");
                return true;
            }
        }
        return false;
    }

    public Line save1(Line line) {
        for (Route route : line.getRoutes()) {
            for (BusStop busStop : route.getStopsArr()) {
                if (busStop.getId() < 0) { // Assuming negative IDs indicate new entities
                    // Persist the BusStop to the database to get a new ID
                    BusStop persistedBusStop = busStopRepo.save(busStop);
                    // Update the BusStop object with the new ID
                    busStop.setId(persistedBusStop.getId());
                }
            }
        }

        line.getRoutes().forEach(route -> {
            if (route.getId() < 0) route.setId(null);
        });

        //if id negative, assign null; negative id assigned at front end means new entry
//        for (Route route : line.getRoutes()) {
//            if (route.getId()<0) route.setId(null);
//        }

        return lineRepo.save(line);
    }

    @Transactional
    public void delete1byId(Long id) {

        lineRepo.deleteSchedulesByLineId(id);

        List<Route> routes = routeRepo.findByLineId(id);
        lineRepo.deleteTrip1WaysByRoutes(routes);
//        System.out.println(routes);
//        for (Route route : routes) {
//            scheduleRepo.deleteByTripsRoute(route);
//        }


        lineRepo.deleteById(id);
    }

    public void deleteMultiple(List<Long> ids) {
        lineRepo.deleteAllById(ids);
    }

    //================

    public LinePreviewDto getLinePreviewDto(Long id) {
        Optional<Line> optionalLine = lineRepo.findById(id);
        if (optionalLine.isEmpty()) {
            return new LinePreviewDto();
    }
        Line line = optionalLine.get();
        return LinePreviewDto.convertToLinePreviewDto(line);
    }

    public Optional<Line> myLineById(Long id) {
        return lineRepo.findById(id);
    }


}
