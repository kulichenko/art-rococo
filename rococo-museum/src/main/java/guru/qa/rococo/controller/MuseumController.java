package guru.qa.rococo.controller;

import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.service.MuseumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/museum")
@RequiredArgsConstructor
public class MuseumController {

    private final MuseumService service;

    @GetMapping
    public List<MuseumJson> findAll() {
        return service.findAll();
    }

    @GetMapping(params = "title")
    public List<MuseumJson> findByTitle(@RequestParam("title") String title) {
        return service.findByTitle(title);
    }

    @GetMapping("/{id}")
    public MuseumJson findById(@PathVariable("id") UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public MuseumJson createMuseum(@RequestBody MuseumJson museumJson) {
        return service.createMuseum(museumJson);
    }

    @PatchMapping
    public MuseumJson editMuseum(@RequestBody MuseumJson museumJson) {
        return service.editMuseum(museumJson);
    }
}
