package tacos.web;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.Ingredient;
import tacos.IngredientUDT;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
class DesingTacoControllerTest {

    @Test
    public void shouldReturnTacos() {
        Taco[] tacos = {
                testTaco(UUID.fromString("1L")), testTaco(UUID.fromString("2L")),
                testTaco(UUID.fromString("3L")), testTaco(UUID.fromString("4L")),
                testTaco(UUID.fromString("5L")), testTaco(UUID.fromString("6L")),
                testTaco(UUID.fromString("7L")), testTaco(UUID.fromString("8L")),
                testTaco(UUID.fromString("9L")), testTaco(UUID.fromString("10L")),
                testTaco(UUID.fromString("11L")), testTaco(UUID.fromString("12L")),
                testTaco(UUID.fromString("13L")), testTaco(UUID.fromString("14L")),
                testTaco(UUID.fromString("15L")), testTaco(UUID.fromString("16L"))};
        Flux<Taco> tacoFlux = Flux.just(tacos);

        IngredientRepository ingredientRepo = Mockito.mock(IngredientRepository.class);

        TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);
        when(tacoRepo.findAll()).thenReturn(tacoFlux);

        WebTestClient testClient = WebTestClient.bindToController(
                new DesingTacoController(ingredientRepo, tacoRepo)).build();

        testClient.get().uri("/design/recent")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Taco.class)
                   .contains(Arrays.copyOf(tacos, 12));
    }

    @Test
    public void shouldSaveTaco() {
        Mono<Taco> unsavedTacoMono = Mono.just(testTaco(null));
        Taco savedTaco = testTaco(null);
        savedTaco.setId(UUID.randomUUID());
        Mono<Taco> savedTacoMono = Mono.just(savedTaco);

        IngredientRepository ingredientRepo = Mockito.mock(IngredientRepository.class);

        TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);
        when(tacoRepo.save(any())).thenReturn(savedTacoMono);

        WebTestClient testClient = WebTestClient.bindToController(
                new DesingTacoController(ingredientRepo, tacoRepo)).build();

        testClient.post()
                    .uri("/design")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(unsavedTacoMono, Taco.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Taco.class)
                    .isEqualTo(savedTaco);
    }

    private Taco testTaco(UUID id) {
        Taco taco = new Taco();
        taco.setId(id);
        taco.setName("Taco " + id);
        List<IngredientUDT> ingredients = new ArrayList<>();
        ingredients.add(
                new IngredientUDT("Ingredient A", Ingredient.Type.WRAP));
        ingredients.add(
                new IngredientUDT("Ingredient B", Ingredient.Type.PROTEIN));
        taco.setIngredients(ingredients);
        return taco;
    }
}