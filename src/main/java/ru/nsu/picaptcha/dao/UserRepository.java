package ru.nsu.picaptcha.dao;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.picaptcha.model.User;

public interface UserRepository extends CrudRepository<User,Long> {

}
