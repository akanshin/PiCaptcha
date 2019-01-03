package ru.nsu.picaptcha.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.picaptcha.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long> { }
