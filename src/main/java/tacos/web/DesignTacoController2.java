package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import tacos.Taco;
import tacos.data.TacoRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RepositoryRestController
public class DesignTacoController2 {

    private final TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController2(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping(path="/tacos/recent", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<Taco>> recentTacos() {
        Iterable<Taco> tacos = tacoRepo.findAll();
        CollectionModel<Taco> tacoCollectionModel = CollectionModel.of(tacos);
        tacoCollectionModel.add(
                linkTo(methodOn(DesignTacoController2.class).recentTacos())
                        .withRel("recents"));
        return new ResponseEntity<>(tacoCollectionModel, HttpStatus.OK);
    }
}
