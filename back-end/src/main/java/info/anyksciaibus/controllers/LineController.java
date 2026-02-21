package info.anyksciaibus.controllers;

import jakarta.transaction.Transactional;
import info.anyksciaibus.dto.LineFullDto;
import info.anyksciaibus.dto.LinePreviewDto;
import info.anyksciaibus.dto.LinePreviewPublicDto;
import info.anyksciaibus.entities.Line;
import info.anyksciaibus.services.LineService;
import info.anyksciaibus.services.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/line")
@CrossOrigin
public class LineController {


    LineService service;
    ScheduleService scheduleService;
    public LineController(LineService service, ScheduleService scheduleService) {
        this.service = service;
        this.scheduleService = scheduleService;
    }


    @GetMapping("/get/all")
    public List<Line> getAll() {
        return service.getAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Line> getOne(@PathVariable Long id) {
        Optional<Line> data = service.get1LineById(id);
        if (data.isPresent())
            return ResponseEntity.ok(data.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/save/multi")
    public List<Line> saveMulti(@RequestBody List<Line> list) {
        return service.saveAll(list);
    }

    @PostMapping("/save/one")
    public Line saveOne(@RequestBody Line line) {
        return service.save1(line);
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
    public ResponseEntity<Void> putOne(@PathVariable Long id, @RequestBody Line updatedLine) {

        Optional<Line> existingLineOptional = service.get1LineById(id);
        if (existingLineOptional.isEmpty() || !id.equals(updatedLine.getId()))
            return ResponseEntity.badRequest().build();

        service.save1(updatedLine);
        return ResponseEntity.noContent().build();
    }

    //========================

    @GetMapping("/single")
    public LinePreviewDto getSingleDto(@RequestParam Long id){
        return service.getLinePreviewDto(id);
    }

    @GetMapping("/preview")
    public List<LinePreviewDto> getListLinePreviewDto(){
        List<Line> allLines = service.getAll();

        return allLines.stream().map(LinePreviewDto::convertToLinePreviewDto)
                .toList();
    }

//    @PostMapping("post-line-eager-dto")
//    public void postLineEagerDto(@RequestBody LineEagerDto dto){
//        Line line = LineEagerDto.dtoToLine(dto);
//        System.out.println("hello");
//        System.out.println(line);
//        service.save1(line);
//    }
    @Transactional
    @PostMapping("post-line-full-dto")
    public long postLineFullDto(@RequestBody LineFullDto dto){
        Line line = LineFullDto.dtoToLine(dto);
        System.out.println("hello");
        System.out.println(line);

        if (line.getId() != null && line.getId() > 0 && service.hasViolatedFKinTrip(line))
            return line.getId();

        Line returned = service.save1(line);
        return returned.getId();
    }

    //Transactional annotation ensures everything in this method will be executed
    //within the transactional context. To get lazy Lists, need to access them .size() or sth
    @Transactional
    @GetMapping("/get-line-full")
    public ResponseEntity<LineFullDto> getLineFull(@RequestParam Long id) {
        Optional<Line> data = service.get1LineById(id);
        if (data.isEmpty()) return ResponseEntity.notFound().build();

        var myData = data.get();
        //accessing to fetch lazy data
        myData.getRoutes().forEach(x->x.getDistanceMetersList().size());

//        scheduleService.

        LineFullDto dto = LineFullDto.lineToDto(myData);

        List<List<Long>> usage = myData.getRoutes().stream().map(route-> {
        return    scheduleService.getScheduleIdsByRoute(route);
        }).toList();

        dto.setRouteUsage(usage);

        return ResponseEntity.ok(dto);

    }
  //!!!! CHECK FOR routeUsage.  left unimplemented properly. static lineToDto doesn't work it. Should be passed as argument
    @GetMapping("get-empty")
    public LineFullDto getEmpty() {
        LineFullDto dto = LineFullDto.lineToDto(new Line());
        dto.setRouteUsage(Collections.emptyList());
        return dto;

//      return LineFullDto.lineToDto(new Line());
    }

    @GetMapping("/line-preview-withid-public")
    public List<LinePreviewPublicDto> getListLinePreviewPublicDto(){
        List<Line> allLines = service.getAll();

        return allLines.stream().map(LinePreviewPublicDto::convertToLinePreviewDto)
                .toList();
    }

}
