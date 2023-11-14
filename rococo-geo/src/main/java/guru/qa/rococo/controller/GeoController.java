package guru.qa.rococo.controller;

import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/geo")
public class GeoController {
    private final GeoService geoService;

    @Autowired
    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }

    @GetMapping()
    public List<GeoJson> findAll() {
        return geoService.findAll();
    }

    @PostMapping(path = "/findByIds")
    public List<GeoJson> findByIds(@RequestBody List<String> ids) {
        return geoService.findByIds(ids);
    }
}
