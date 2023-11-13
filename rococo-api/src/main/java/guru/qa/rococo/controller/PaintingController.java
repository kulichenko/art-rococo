package guru.qa.rococo.controller;

import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.PaintingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/painting")
@RestController
public class PaintingController {

    private final PaintingClient paintingClient;

    @Autowired
    public PaintingController(@Qualifier("rest") PaintingClient paintingClient) {
        this.paintingClient = paintingClient;
    }

    @GetMapping
    public Page<PaintingJson> getAllPaintings(
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false, defaultValue = "0") Integer page) {
        return paintingClient.allPaintings(PageRequest.of(page, size));
    }


    @GetMapping(params = "title")
    public Page<PaintingJson> findByTitle(@RequestParam("title") String title,
                                          @RequestParam(required = false, defaultValue = "5") Integer size,
                                          @RequestParam(required = false, defaultValue = "0") Integer page) {
        return paintingClient.findByTitle(title, PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public PaintingJson findById(@PathVariable("id") String id) {
        return paintingClient.findById(id);
    }

    @GetMapping("/author/{id}")
    public Page<PaintingJson> findByAuthor(@PathVariable("id") String id,
                                           @RequestParam(required = false, defaultValue = "5") Integer size,
                                           @RequestParam(required = false, defaultValue = "0") Integer page) {
        return paintingClient.findByAuthor(id, PageRequest.of(page, size));
    }


//
//
//    @GetMapping("/{id}")
//    public PaintingJson findById(@PathVariable String id) {
//        return paintingClient.findById(id);
//    }
//
//    @PatchMapping
//    public PaintingJson editArtist(@RequestBody PaintingJson artist) {
//
//        return paintingClient.editArtist(artist);
//    }
//
//    @PostMapping
//    public PaintingJson createArtist(@RequestBody PaintingJson artistJson) {
//        return paintingClient.createArtist(artistJson);
//    }
}
