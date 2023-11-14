package guru.qa.rococo.controller;

import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.service.GeoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/country")
@RestController
public class GeoController {

    private final GeoClient geoClient;

    @Autowired
    public GeoController(@Qualifier("rest") GeoClient geoClient) {
        this.geoClient = geoClient;
    }

    @GetMapping
    public Page<CountryJson> allCountries(
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "0") Integer page) {
        return geoClient.allCountries(PageRequest.of(page, size));
    }

    @PostMapping("/findByIds")
    public List<CountryJson> findById(@RequestBody List<String> ids) {
        return geoClient.findById(ids);
    }

}
