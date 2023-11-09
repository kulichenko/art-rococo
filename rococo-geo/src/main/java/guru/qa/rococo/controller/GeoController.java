package guru.qa.rococo.controller;

import guru.qa.rococo.data.GeoEntity;
import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/geo")
public class GeoController {
    private final GeoService geoService;

    @Autowired
    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }

    @GetMapping()
    public Page<GeoEntity> getAllGeo(@RequestParam(required = false, defaultValue = "20") Integer size,
                                     @RequestParam(required = false, defaultValue = "0") Integer page) {
        return geoService.getAllGeo(PageRequest.of(page, size));
    }

    @PostMapping(path = "/findByIds")
    public List<GeoJson> findCountriesByIds(@RequestBody List<String> ids) {
        return geoService.findCountryByIds(ids);
    }
}
