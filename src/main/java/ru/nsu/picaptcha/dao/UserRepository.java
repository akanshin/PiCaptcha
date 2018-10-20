package ru.nsu.picaptcha.dao;

import org.springframework.data.repository.CrudRepository;

import ru.nsu.picaptcha.model.Session;

public interface UserRepository extends CrudRepository<Session ,Long> {

}
