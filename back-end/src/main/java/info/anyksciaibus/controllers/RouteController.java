package info.anyksciaibus.controllers;

import info.anyksciaibus.dto.RouteDto;
import info.anyksciaibus.entities.Route;
import info.anyksciaibus.services.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/route")
@CrossOrigin
public class RouteController {


    RouteService service;

    public RouteController(RouteService service) {
        this.service = service;
    }


    @GetMapping("/get/all")
    public List<Route> getAll() {
        return service.getAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Route> getOne(@PathVariable Long id) {
        Optional<Route> data = service.get1RouteById(id);
        if (data.isPresent())
            return ResponseEntity.ok(data.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/save/multi")
    public List<Route> saveMulti(@RequestBody List<Route> list) {
        return service.saveAll(list);
    }

    @PostMapping("/save/one")
    public Route saveOne(@RequestBody Route route) {
        return service.save1(route);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOne(@PathVariable Long id) {
        service.delete1byId(id);
    }


    @DeleteMapping("/delete/multi")
    public void deleteMulti(@RequestBody List<Long> ids) {
        service.deleteMultiple(ids);
    }

    //UPSERT
    @PutMapping("/put/{id}")
    public ResponseEntity<Void> putOne(@PathVariable Long id, @RequestBody Route updatedRoute) {

        Optional<Route> existingRouteOptional = service.get1RouteById(id);
        if (existingRouteOptional.isEmpty() || !id.equals(updatedRoute.getId()))
            return ResponseEntity.badRequest().build();

        service.save1(updatedRoute);
        return ResponseEntity.noContent().build();
    }

    //==============================================

    @GetMapping("/byline/{lineId}")
    public ResponseEntity<List<RouteDto>> getRoutesByLineId(@PathVariable Long lineId) {
        List<RouteDto> routeDTOs = service.getRoutesByLineId(lineId);
        return new ResponseEntity<>(routeDTOs, HttpStatus.OK);
    }

}
