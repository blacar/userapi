package com.blacar.spike.userapi.repositories;

import com.blacar.spike.userapi.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
