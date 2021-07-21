package com.paulkapa.btd6gamelogger.database.system;

import com.paulkapa.btd6gamelogger.models.system.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <b>User database repository</b>
 * <p>
 * Extends JpaRepository interface that defines methods for performing CRUD
 * operations on database objects.
 */
@Repository
@Transactional(readOnly = true)
public interface UserInterface extends JpaRepository<User, Integer> {
    /**
     * Returns an user object with the specified name.
     *
     * @param name the user name to search for
     * @return an user object if found, otherwise null
     */
    User findByName(String name);

    /**
     * Returns an user object with the specified name and password.
     *
     * @param name     the user name to search for
     * @param password the user password to search for
     * @return an user object if found, otherwise null
     */
    User findByNameAndPassword(String name, String password);
}
