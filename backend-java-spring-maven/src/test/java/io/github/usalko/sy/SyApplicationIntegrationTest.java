package io.github.usalko.sy;

import io.github.usalko.sy.controller.HealthCheckController;
import io.github.usalko.sy.controller.MoodController;
import io.github.usalko.sy.controller.TokenController;
import io.github.usalko.sy.model.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Autowired
    private TokenController tokenController;

    //Cached token
    private String token;

    private String getToken() {
        if (this.token != null) {
            return this.token;
        }
        String token = restTemplate.exchange(
                "http://localhost:" + port + "/api/Token?user-agent-hash=0&seed=0",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                }).getBody();
        this.token = token;
        return token;
    }

    @Test
    public void contextLoads() {
        Assertions
                .assertThat(healthCheckController)
                .isNotNull();
        Assertions
                .assertThat(moodController)
                .isNotNull();
        Assertions
                .assertThat(tokenController)
                .isNotNull();
    }

    @Test
    public void givenHealthCheckApiCall_whenGeometryShapeListRetrieved_thenSizeMatchAndListContainsGeometryShapeNames() {
        ResponseEntity<Iterable<String>> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/Geometry",
                HttpMethod.GET, null, new ParameterizedTypeReference<Iterable<String>>() {
                });
        Iterable<String> geometryShapes = responseEntity.getBody();
        Assertions
                .assertThat(geometryShapes)
                .hasSize(3);

        assertThat(geometryShapes, hasItem("triangle"));
        assertThat(geometryShapes, hasItem("square"));
        assertThat(geometryShapes, hasItem("circle"));
    }

    @Test
    public void givenTokenApiCall_whenGenerateNewToken_thenSizeMatch() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/Token?user-agent-hash=0&seed=0",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                });

        String token = responseEntity.getBody();
        Assertions
                .assertThat(token)
                .isNotNull();


        assertEquals(token.length(), 36);
    }

    @Test
    public void givenGetSharedMoodsApiCall_thenSizeMatchAndListContainsAtLeastOneSharedMood() {
        this.shareMood_thenResponseIsOk();
        ResponseEntity<Iterable<SharedMood>> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/GetSharedMoods",
                HttpMethod.GET, null, new ParameterizedTypeReference<Iterable<SharedMood>>() {
                });

        Iterable<SharedMood> moods = responseEntity.getBody();
        Assertions
                .assertThat(moods)
                .isNotNull()
                .hasAtLeastOneElementOfType(SharedMood.class);

        SharedMood mood = moods.iterator().next();
        assertThat(mood, hasProperty("id", notNullValue()));
        assertThat(mood.getMoodGeometryShapes(), hasItem(hasProperty("color", is(0))));
    }

    @Test
    public void givenGetHistoryApiCall_thenSizeMatchAndListContainsAtLeastOneSharedMood() {
        this.keepMoodForNow_thenResponseIsOk();
        ResponseEntity<Iterable<OwnMood>> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/api/GetHistory?token=" + this.getToken(),
                HttpMethod.GET, null, new ParameterizedTypeReference<Iterable<OwnMood>>() {
                });

        Iterable<OwnMood> moods = responseEntity.getBody();
        Assertions
                .assertThat(moods)
                .isNotNull()
                .hasAtLeastOneElementOfType(OwnMood.class);

        OwnMood mood = moods.iterator().next();
        assertThat(mood, hasProperty("id", is(1L)));
        assertThat(mood.getMoodGeometryShapes(), hasItem(hasProperty("color", is(0))));
    }

    @Test
    public void shareMood_thenResponseIsOk() {
        final ResponseEntity<?> postResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/ShareMood?token=" + getToken(), createSharedMood(), Object.class);
        Assertions
                .assertThat(postResponse.getStatusCode())
                .isEqualByComparingTo(HttpStatus.OK);
    }

    private SharedMood createSharedMood() {
        // Shared mood sample
        SharedMood sharedMood = new SharedMood();
        sharedMood.setGeometryShape(new GeometryShape(null, "circle"));
        SharedMoodGeometryShape sharedMoodGeometryShape = new SharedMoodGeometryShape(sharedMood,
                new GeometryShape(null, "circle"), 0, 0);
        sharedMood.setMoodGeometryShapes(Collections.singletonList(sharedMoodGeometryShape));
        return sharedMood;
    }

    @Test
    public void keepMoodForNow_thenResponseIsOk() {
        final ResponseEntity<?> postResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/KeepMoodForNow?token=" + getToken(), createOwnMood(), Object.class);
        Assertions
                .assertThat(postResponse.getStatusCode())
                .isEqualByComparingTo(HttpStatus.OK);
    }

    private OwnMood createOwnMood() {
        // Own mood sample
        OwnMood ownMood = new OwnMood();
        ownMood.setGeometryShape(new GeometryShape(null, "triangle"));
        OwnMoodGeometryShape ownMoodGeometryShape = new OwnMoodGeometryShape(ownMood,
                new GeometryShape(null, "triangle"), 0, 0);
        ownMood.setMoodGeometryShapes(Collections.singletonList(ownMoodGeometryShape));
        return ownMood;
    }

    @Test
    public void validationTokenApiCall_thenResponseIsOk() {
        ResponseEntity<?> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/Token/Validation?token=" + getToken(),
                HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {
                });

        Assertions
                .assertThat(response.getStatusCode())
                .isEqualByComparingTo(HttpStatus.OK);
    }

}