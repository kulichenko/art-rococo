package guru.qa.rococo.controller;

import guru.qa.rococo.data.Mapper;
import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.PaintingClient;
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

@RequestMapping("/api/painting")
@RestController
public class PaintingController {

    private final PaintingClient paintingClient;
    private final Mapper mapper;

    @Autowired
    public PaintingController(@Qualifier("rest") PaintingClient paintingClient, Mapper mapper) {
        this.paintingClient = paintingClient;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<PaintingJson> getAllPaintings(
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false, defaultValue = "0") Integer page) {
        var paintingJsonPage = paintingClient.allPaintings(PageRequest.of(page, size));
        return mapper.putMuseumsAndArtistsToPaintings(paintingJsonPage);
    }


    @GetMapping(params = "title")
    public Page<PaintingJson> findByTitle(@RequestParam("title") String title,
                                          @RequestParam(required = false, defaultValue = "5") Integer size,
                                          @RequestParam(required = false, defaultValue = "0") Integer page) {
        var paintingJsonPage = paintingClient.findByTitle(title, PageRequest.of(page, size));
        return mapper.putMuseumsAndArtistsToPaintings(paintingJsonPage);
    }

    @GetMapping("/{id}")
    public PaintingJson findById(@PathVariable("id") String id) {

        var painting = paintingClient.findById(id);
        return mapper.putMuseumAndArtistObjectsToPainting(painting);
    }

    @GetMapping("/author/{id}")
    public Page<PaintingJson> findByAuthor(@PathVariable("id") String id,
                                           @RequestParam(required = false, defaultValue = "5") Integer size,
                                           @RequestParam(required = false, defaultValue = "0") Integer page) {
        var paintingJsonPage = paintingClient.findByAuthor(id, PageRequest.of(page, size));
        return mapper.putMuseumsAndArtistsToPaintings(paintingJsonPage);
    }

    @PatchMapping
    public PaintingJson editPainting(@RequestBody PaintingJson paintingJson) {
        var painting = mapper.setMuseumIdAndArtistIdToPainting(paintingJson);
        return mapper.putMuseumAndArtistObjectsToPainting(paintingClient.editPaint(painting));
    }

    @PostMapping
    public PaintingJson createPaint(@RequestBody PaintingJson paintingJson) {
        var painting = mapper.setMuseumIdAndArtistIdToPainting(paintingJson);
        return mapper.putMuseumAndArtistObjectsToPainting(paintingClient.createPaint(painting));
    }
}
