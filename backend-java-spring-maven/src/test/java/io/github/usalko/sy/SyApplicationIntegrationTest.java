package io.github.usalko.sy;

import io.github.usalko.sy.controller.HealthCheckController;
import io.github.usalko.sy.controller.MoodController;
import io.github.usalko.sy.model.GeometryShape;
import io.github.usalko.sy.model.Mood;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SyApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SyApplicationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private HealthCheckController healthCheckController;

    @Autowired
    private MoodController moodController;

    @Test
    public void contextLoads() {
        Assertions
                .assertThat(healthCheckController)
                .isNotNull();
        Assertions
                .assertThat(moodController)
                .isNotNull();
    }

    @Test
    public void givenGetGeometryShapesApiCall_whenGeometryShapeListRetrieved_thenSizeMatchAndListContainsGeometryShapeNames() {
        ResponseEntity<Iterable<GeometryShape>> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/geometryShapes", HttpMethod.GET, null, new ParameterizedTypeReference<Iterable<GeometryShape>>() {
        });
        Iterable<GeometryShape> geometryShapes = responseEntity.getBody();
        Assertions
                .assertThat(geometryShapes)
                .hasSize(7);

        assertThat(geometryShapes, hasItem(hasProperty("name", is("TV Set"))));
        assertThat(geometryShapes, hasItem(hasProperty("name", is("Game Console"))));
        assertThat(geometryShapes, hasItem(hasProperty("name", is("Sofa"))));
        assertThat(geometryShapes, hasItem(hasProperty("name", is("Icecream"))));
        assertThat(geometryShapes, hasItem(hasProperty("name", is("Beer"))));
        assertThat(geometryShapes, hasItem(hasProperty("name", is("Phone"))));
        assertThat(geometryShapes, hasItem(hasProperty("name", is("Watch"))));
    }

    @Test
    public void givenGetMoodsApiCall_whenGeometryShapeListRetrieved_thenSizeMatchAndListContainsGeometryShapeNames() {
        ResponseEntity<Iterable<Mood>> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/moods", HttpMethod.GET, null, new ParameterizedTypeReference<Iterable<Mood>>() {
        });

        Iterable<Mood> moods = responseEntity.getBody();
        Assertions
                .assertThat(moods)
                .hasSize(0);
    }

//    @Test
//    public void givenPostMood_whenBodyRequestMatcherJson_thenResponseContainsEqualObjectProperties() {
//        final ResponseEntity<Mood> postResponse = restTemplate.postForEntity("http://localhost:" + port + "/api/moods", prepareMoodForm(), Mood.class);
//        Mood mood = postResponse.getBody();
//        Assertions
//                .assertThat(postResponse.getStatusCode())
//                .isEqualByComparingTo(HttpStatus.CREATED);
//
//        assertThat(mood, hasProperty("status", is("PAID")));
//        assertThat(mood.getMoodGeometryShapes(), hasItem(hasProperty("quantity", is(2))));
//    }

//    private MoodController.MoodForm prepareMoodForm() {
//        MoodController.MoodForm moodForm = new MoodController.MoodForm();
//        MoodGeometryShapeDto geometryShapeDto = new MoodGeometryShapeDto();
//        geometryShapeDto.setGeometryShape(new GeometryShape(1L, "TV Set", 300.00, "http://placehold.it/200x100"));
//        geometryShapeDto.setColor(2);
//        moodForm.setGeometryShapeMoods(Collections.singletonList(geometryShapeDto));
//
//        return moodForm;
//    }
}