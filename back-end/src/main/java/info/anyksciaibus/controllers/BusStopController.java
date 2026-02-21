package info.anyksciaibus.controllers;

import info.anyksciaibus.dto.BusStopsDto;
import info.anyksciaibus.entities.BusStop;
import info.anyksciaibus.services.BusStopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/busstop")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST}) // Allow CORS for all origins and both GET and POST methods
public class BusStopController {
    BusStopService service;

    public BusStopController(BusStopService service) {
        this.service = service;
    }


    @GetMapping("/get/all")
    public List<BusStop> getAll() {
        return service.getAll();
    }

    @GetMapping("/get-all-with-usage")
    public List<Map<String, ?>> getAllWithUsage() {
        List<BusStop> allStops = service.getAll();


        return allStops.stream().map(busStop -> {
            Map<String, Object> combo = new HashMap<>();
            combo.put("stop", busStop);
            combo.put("usedInLines", service.findLineIdNameDtoByBusStop(busStop));
            return combo;
        }).collect(Collectors.toList());

    }


    @GetMapping("/get2")
    public Map<String, BusStopsDto> getTwo(@RequestParam(value = "fromId", defaultValue = "1") Long fromId,
                                            Long toId) {

    return service.get2ById(fromId, toId);
    }

    @PostMapping("/save/multi")
    public List<Map<String, ?>> saveMulti(@RequestBody List<BusStop> list) {

        return service.saveAll(list).stream().map(busStop -> {
            Map<String, Object> combo = new HashMap<>();
            combo.put("stop", busStop);
            combo.put("usedInLines", service.findLineIdNameDtoByBusStop(busStop));
            return combo;
        }).collect(Collectors.toList());
    }

    @PostMapping("/save/one")
    public BusStop saveOne(@RequestBody BusStop busStop) {
        System.out.println(busStop);
        return service.save1(busStop);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOne (@PathVariable Long id){
        service.delete1byId(id);
    }


    @DeleteMapping("/delete/multi")
    public void deleteMulti(@RequestBody List<Long> ids){
        service.deleteMultiple(ids);
    }

    //replace or insert if not found
    @PutMapping ("/put/{id}")
    public ResponseEntity<Void> putOne(@PathVariable Long id, @RequestBody BusStop updatedBusStop){

        Optional<BusStop> existingBusStopOptional = service.get1BusStopById(id);
        if (existingBusStopOptional.isEmpty() || !id.equals(updatedBusStop.getId()))
            return ResponseEntity.badRequest().build();

        service.save1(updatedBusStop);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/put/updateAll")
    public ResponseEntity<Void> updateAllBusStops(@RequestBody List<BusStop> updatedBusStops) {
        // Check if all IDs in the list exist
        for (BusStop updatedBusStop : updatedBusStops) {
            Long id = updatedBusStop.getId();
            Optional<BusStop> existingBusStopOptional = service.get1BusStopById(id);

            if (existingBusStopOptional.isEmpty()) {
                // Resource with given ID not found, return 404 Not Found
                return ResponseEntity.notFound().build();
            }
        }

        // If all IDs exist, perform updates
        for (BusStop updatedBusStop : updatedBusStops) {
            // Update the existing bus stop with the new data
            service.save1(updatedBusStop);
        }

        return ResponseEntity.noContent().build(); // Return 204 No Content for successful update
    }
    //private, used by admin panel bus stop manager page
    @GetMapping("/searchresults")
    public List<Map<String, ?>> getSearchByName(@RequestParam String query){
        List<BusStop> allStops = service.getBySearchName(query);

        return allStops.stream().map(busStop -> {
            Map<String, Object> combo = new HashMap<>();
            combo.put("stop", busStop);
            combo.put("usedInLines", service.findLineIdNameDtoByBusStop(busStop));
            return combo;
        }).collect(Collectors.toList());


    }


//============?
    //public upen - used by header search bar
    @GetMapping("/search")
    public List<BusStopsDto> getinit(@RequestParam String str) {
        return service.getSearchOptions(str);
    }



    @GetMapping("/alldto")
    public List<BusStopsDto> getAllDto(){
        return service.getAllDto();
    }
}

