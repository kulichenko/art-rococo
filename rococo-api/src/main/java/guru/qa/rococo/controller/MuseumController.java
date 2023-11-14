package guru.qa.rococo.controller;

import guru.qa.rococo.data.Mapper;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.service.MuseumClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/museum")
@RestController
public class MuseumController {

    private final MuseumClient museumClient;
    private final Mapper mapper;

    @Autowired
    public MuseumController(@Qualifier("rest") MuseumClient museumClient, Mapper mapper) {
        this.museumClient = museumClient;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<MuseumJson> getAllPaintings(
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false, defaultValue = "0") Integer page) {
        var museums = museumClient.allMuseums(PageRequest.of(page, size));
        return mapper.putGeoObjectsToMuseums(museums);
    }


    @GetMapping(params = "title")
    public Page<MuseumJson> findByTitle(@RequestParam("title") String title,
                                        @RequestParam(required = false, defaultValue = "5") Integer size,
                                        @RequestParam(required = false, defaultValue = "0") Integer page) {
        var museums = museumClient.findByTitle(title, PageRequest.of(page, size));
        return mapper.putGeoObjectsToMuseums(museums);
    }

    @GetMapping("/{id}")
    public MuseumJson findById(@PathVariable("id") String id) {
        var museum = museumClient.findById(id);
        return mapper.putGeoObjectToMuseum(museum);
    }

    @PostMapping
    public MuseumJson createMuseum(@RequestBody MuseumJson museumJson) {
        var museum = mapper.addCountryIdAndCityToMuseumFromGeo(museumJson);
        var result = museumClient.createMuseum(museum);
        return mapper.putGeoObjectToMuseum(result);
    }

    @PatchMapping
    public MuseumJson editMuseum(@RequestBody MuseumJson museumJson) {
        var museum = mapper.addCountryIdAndCityToMuseumFromGeo(museumJson);
        var result = museumClient.editMuseum(museum);
        return mapper.putGeoObjectToMuseum(result);
    }
}
