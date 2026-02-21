package info.anyksciaibus.services;

import info.anyksciaibus.dto.RouteDto;
import info.anyksciaibus.entities.Route;
import info.anyksciaibus.repositories.RouteRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    RouteRepo routeRepo;

    public RouteService(RouteRepo routeRepo){
        this.routeRepo = routeRepo;
    }

    public List<Route> getAll() {
        return routeRepo.findAll();
    }

    public Optional<Route> get1RouteById(Long id) {
        return routeRepo.findById(id);
    }

    public List<Route> saveAll(List<Route> routeList) {
        return routeRepo.saveAll(routeList);
    }

    public Route save1(Route route) {
        return routeRepo.save(route);
    }

    public void delete1byId(Long id) {
        routeRepo.deleteById(id);
    }

    public void deleteMultiple(List<Long> ids) {
        routeRepo.deleteAllById(ids);
    }



    //==========================

    public List<RouteDto> getRoutesByLineId(Long lineId) {
        List<Route> routes = routeRepo.findByLineId(lineId);
        List<RouteDto> routeDtos = new ArrayList<>();

        for (Route route : routes) {
            RouteDto routeDto = new RouteDto();
            routeDto.setId(route.getId());
            routeDto.setRouteNotes(route.getRouteNotes());
            routeDto.setLineId(lineId);
            routeDto.setStopsArr(route.getStopsArr());
            routeDto.setDistanceMetersArr(route.getDistanceMetersList());

            routeDtos.add(routeDto);
        }

        return routeDtos;
    }

}
