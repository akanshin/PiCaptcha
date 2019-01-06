package ru.nsu.picaptcha.dao;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.picaptcha.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
