package ru.nsu.picaptcha.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.picaptcha.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
