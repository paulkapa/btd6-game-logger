package com.paulkapa.btd6gamelogger.database.system;

import com.paulkapa.btd6gamelogger.models.system.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h4>User database repository</h4>
 *
 * Extends JpaRepository interface that provides methods for performing
 * CRUD operations on database objects.
 *
 * @Repository
 * @Transactional readOnly = true
 */
@Repository
@Transactional(readOnly = true)
public interface UserInterface extends JpaRepository<User, Integer> {
	User findByName(String name);
	User findByNameAndPassword(String name, String password);
}
