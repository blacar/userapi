package com.blacar.spike.userapi.repositories;

import com.blacar.spike.userapi.domain.User;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.assertj.core.util.Lists;
import org.bson.types.ObjectId;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository users;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void setup() throws Exception {
        mongoTemplate.dropCollection(User.class);
    }

    @After
    public void tearDown() throws Exception {
        mongoTemplate.dropCollection(User.class);
    }

    @Test
    public void saveUserCreatesANewEntry() {
        final User result = users.save(this.createUser("name0"));
        MatcherAssert.assertThat(
            users.findAll(),
            Matchers.iterableWithSize(1)
        );
        MatcherAssert.assertThat(
            Lists.newArrayList(users.findAll()).get(0),
            Matchers.equalTo(result)
        );
    }

    @Test
    public void findAllReturnExpectedIterable() {
        final List<User> input = ImmutableList.of(
            users.save(this.createUser("name1")),
            users.save(this.createUser("name2"))
        );
        final List<User> result = Lists.newArrayList(users.findAll());
        MatcherAssert.assertThat(
            result, Matchers.iterableWithSize(2)
        );
        MatcherAssert.assertThat(
            result, Matchers.equalTo(input)
        );
    }

    @Test
    public void findOneReturnExpectedItem() {
        users.save(this.createUser("name1"));
        final User user2 = users.save(this.createUser("name2"));
        MatcherAssert.assertThat(
            users.findById(user2.getId().toString()).get(),
            Matchers.equalTo(user2)
        );
    }

    @Test
    public void deleteItemBehavesAsExpected() {
        final List<User> input = ImmutableList.of(
            users.save(this.createUser("name1")),
            users.save(this.createUser("name2"))
        );
        users.delete(input.get(0));
        final List<User> result = Lists.newArrayList(users.findAll());
        MatcherAssert.assertThat(
            result, Matchers.iterableWithSize(1)
        );
        MatcherAssert.assertThat(
            result.get(0), Matchers.equalTo(input.get(1))
        );
    }

    private final User createUser(final String name) {
        return new User(
            name,
            "fakemail",
            "fakephone"
        );
    }
}