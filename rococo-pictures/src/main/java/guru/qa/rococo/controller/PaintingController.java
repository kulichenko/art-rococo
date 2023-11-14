package guru.qa.rococo.controller;

import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.PaintingService;
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
@RequestMapping("/painting")
@RequiredArgsConstructor
public class PaintingController {

    private final PaintingService service;

    @GetMapping
    public List<PaintingJson> findAll() {
        return service.findAll();
    }

    @GetMapping(params = "title")
    public List<PaintingJson> findByTitle(@RequestParam("title") String title) {
        return service.findByTitle(title);
    }

    @GetMapping("/{id}")
    public PaintingJson findById(@PathVariable("id") UUID id) {
        return service.findById(id);
    }

    @GetMapping("/author/{id}")
    public List<PaintingJson> findByAuthor(@PathVariable("id") UUID id) {
        return service.findByAuthor(id);
    }

    @PostMapping
    public PaintingJson createPainting(@RequestBody PaintingJson paintingJson) {
        return service.createPainting(paintingJson);
    }

    @PatchMapping
    public PaintingJson editPainting(@RequestBody PaintingJson paintingJson) {
        return service.editPainting(paintingJson);
    }
}
