package info.anyksciaibus.services;

import info.anyksciaibus.dto.BusStopsDto;
import info.anyksciaibus.dto.LineIdNameDto;
import info.anyksciaibus.entities.BusStop;
import info.anyksciaibus.repositories.BusStopRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BusStopService {
    BusStopRepo busStopRepo;

    public BusStopService(BusStopRepo busStopRepo) {
        this.busStopRepo = busStopRepo;
    }

    public List<BusStop> getAll() {
        return busStopRepo.findAll();
    }

    public Optional<BusStop> get1BusStopById(Long id) {
        return busStopRepo.findById(id);
    }

    public List<BusStop> saveAll(List<BusStop> busStopList) {
        return busStopRepo.saveAll(busStopList);
    }

    public BusStop save1(BusStop busStop) {
        return busStopRepo.save(busStop);
    }

    public void delete1byId(Long id) {
    //Route holds List<BusStop> stopsArr, its elements hold bus stop FK
        if (!busStopRepo.existsRoutesForBusStop(id))
            busStopRepo.deleteById(id);
    }

    public void deleteMultiple(List<Long> ids) {
        busStopRepo.deleteAllById(ids);
    }

    //===========================
    public List<BusStopsDto> getSearchOptions(String str) {
        if (str.isEmpty())
            return busStopRepo.findByDefaultOptionTrue().stream().map(BusStop::mapToDto).toList();
        else if (str.length() <= 2)
            return busStopRepo.findTop10ByNameStartingWithIgnoreCase(str)
                    .stream().map(BusStop::mapToDto).toList();


        return busStopRepo.findTop10ByNameContainingIgnoreCase(str)
                .stream().map(BusStop::mapToDto).toList();
    }

    public List<BusStop> getBySearchName(String searchQuery){
        return busStopRepo.findByNameContaining(searchQuery);
    }

    public List<BusStopsDto> getAllDto(){
        return busStopRepo.findAllBusStops();
    }

    public List<LineIdNameDto> findLineIdNameDtoByBusStop(BusStop busStop) {
        return busStopRepo.findLineIdsAndNamesByBusStop(busStop);
    }

    public Map<String, BusStopsDto> get2ById (Long fromId, Long toId ) {
//        if (fromId == null)
//            return Map.of("from", new BusStopsDto(), "to", new BusStopsDto());


        BusStop from = new BusStop();
        BusStop to = new BusStop();

        //in front end, if FROM is not 1L then To is not needed, auto cleared
        if (fromId.equals(1L)){
            from = busStopRepo.findById(fromId).orElse(from);
            if (toId != null)
                to = busStopRepo.findById(toId).orElse(to);
        } else {
            from = busStopRepo.findById(fromId).orElse(
                    busStopRepo.findById(1L).orElse(from)
            );
        }
//        to = busStopRepo.findById(4L).get();
        return Map.of(
                "from", BusStopsDto.busStopToDto(from),
                "to", BusStopsDto.busStopToDto(to)
        );
    }


}
