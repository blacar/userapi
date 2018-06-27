package com.blacar.spike.userapi.controllers;

import com.blacar.spike.userapi.domain.User;
import com.blacar.spike.userapi.exceptions.ResourceNotFoundException;
import com.blacar.spike.userapi.repositories.UserRepository;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * NOTE this class cannot be final since
 * Could not generate CGLIB subclass problem
 * @checkstyle DesignForExtension (100 lines)
 */
@RestController
public class UserController {

    private final UserRepository users;

    @Autowired
    public UserController(final UserRepository usersparam) {
        this.users = usersparam;
    }

    @RequestMapping(
        value = "/user",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public User save(@Valid @RequestBody final User user) {
        return this.users.save(user);
    }

    @RequestMapping(
        value = "/user",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<User> getAll() {
        return Lists.newArrayList(this.users.findAll());
    }

    @RequestMapping(
        value = "/user/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @Cacheable("user")
    public User getOne(@PathVariable("id") final String id) {
        return this.users.findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
            );
    }

    @RequestMapping(
        value = "/user/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public User update(
        @PathVariable("id") final String id,
        @Valid @RequestBody final User user
    ) {
        return this.users.findById(id)
            .map(actual -> this.users.save(actual.update(user)))
            .orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
            );
    }

    @RequestMapping(
        value = "/user/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void delete(@PathVariable("id") final String id) {
        final User actual = this.users.findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
            );
        this.users.delete(actual);
    }
}
