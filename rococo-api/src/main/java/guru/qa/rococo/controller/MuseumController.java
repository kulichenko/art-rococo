package guru.qa.rococo.controller;

import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.service.MuseumClient;
import guru.qa.rococo.service.MuseumClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/museum")
@RestController
public class MuseumController {

    private final MuseumClient museumClient;

    @Autowired
    public MuseumController(@Qualifier("rest") MuseumClient museumClient) {
        this.museumClient = museumClient;
    }

    @GetMapping
    public Page<MuseumJson> getAllPaintings(
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false, defaultValue = "0") Integer page) {
        return museumClient.allMuseums(PageRequest.of(page, size));
    }


    @GetMapping(params = "title")
    public Page<MuseumJson> findByTitle(@RequestParam("title") String title,
                                        @RequestParam(required = false, defaultValue = "5") Integer size,
                                        @RequestParam(required = false, defaultValue = "0") Integer page) {
        return museumClient.findByTitle(title, PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public MuseumJson findById(@PathVariable("id") String id) {
        return museumClient.findById(id);
    }

//    @GetMapping("/author/{id}")
//    public Page<MuseumJson> findByAuthor(@PathVariable("id") String id,
//                                           @RequestParam(required = false, defaultValue = "5") Integer size,
//                                           @RequestParam(required = false, defaultValue = "0") Integer page) {
//        return museumClient.findByAuthor(id, PageRequest.of(page, size));
//    }


//
//
//    @GetMapping("/{id}")
//    public MuseumJson findById(@PathVariable String id) {
//        return paintingClient.findById(id);
//    }
//
//    @PatchMapping
//    public MuseumJson editArtist(@RequestBody MuseumJson artist) {
//
//        return paintingClient.editArtist(artist);
//    }
//
//    @PostMapping
//    public MuseumJson createArtist(@RequestBody MuseumJson artistJson) {
//        return paintingClient.createArtist(artistJson);
//    }
}
