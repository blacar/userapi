package com.blacar.spike.userapi.repositories;

import com.blacar.spike.userapi.UserApiApplication;
import com.blacar.spike.userapi.config.MongoConfig;
import com.blacar.spike.userapi.controllers.UserController;
import com.blacar.spike.userapi.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Arrays;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public final class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository users;

    @Test
    public void whenSaveThenReturnExpectedJson() throws Exception {
        // given
        final String name = "User1";
        final User user = this.mockUser(name);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(user);
        //when
        this.mockSave(user);
        //then
        mvc.perform(
            MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.id", Matchers.is("1")
                )
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.name", Matchers.is(name)
                )
            );
    }

    @Test
    public void whenGetAllThenReturnExpectedJsonArray() throws Exception {
        // given
        final String name = "User1";
        //when
        this.mockFindAll(name);
        //then
        mvc.perform(MockMvcRequestBuilders.get("/user")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1))
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$[0].id", Matchers.is("1")
                )
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$[0].name", Matchers.is(name)
                )
            );
    }

    @Test
    public void whenGetOneThenReturnExpectedJson() throws Exception {
        // given
        final String name = "User1";
        //when
        this.mockFindOne(name);
        //then
        mvc.perform(MockMvcRequestBuilders.get("/user/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.id", Matchers.is("1")
                )
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$.name", Matchers.is(name)
                )
            );
    }

    private void mockFindOne(final String name) {
        final User user = this.mockUser(name);
        user.setId("1");
        given(users.findById(Mockito.anyString()))
            .willReturn(Optional.of(user));
    }

    private void mockSave(final User user) {
        user.setId("1");
        given(users.save(Mockito.any(User.class)))
            .willReturn(user);
    }

    private void mockFindAll(final String name) {
        final User user = this.mockUser(name);
        user.setId("1");
        given(users.findAll())
            .willReturn(Arrays.asList(user));
    }

    private User mockUser(final String name) {
        return new User(
            name,
            "fakemail",
            "fakephone"
        );
    }
}

