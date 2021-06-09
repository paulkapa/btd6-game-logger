package com.paulkapa.btd6gamelogger.database.system;

import javax.persistence.EntityManager;

import com.paulkapa.btd6gamelogger.models.system.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

//@Component
//@Transactional(readOnly = false)
public class UserRepo {

    private static final Logger logger = LoggerFactory.getLogger(UserRepo.class);

    //@PersistenceContext
    private EntityManager em;

    //@Autowired
    private UserInterface repo;

    //@Autowired
    public UserRepo() {
        this.repo.flush();
    }

    public void run() {
        
        repo.saveAndFlush(new User("user1", "password", "email"));
        repo.saveAndFlush(new User("user2", "password", "email"));
        repo.saveAndFlush(new User("user3", "password", "email"));
        repo.saveAndFlush(new User("user4", "password", "email"));

        logger.info("# of users: {}", repo.count());

        logger.info("All users unsorted:");
        logger.info("{}", repo.findAll());

        logger.info("------------------------");

        logger.info("All users sorted descending by name:");
        logger.info("{}", repo.findAll(Sort.by(Direction.DESC, "name")));

        logger.info("------------------------");

        logger.info("Deleting all users");
        repo.deleteAll();
        try {
            em.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
        } catch(Exception e) {
            logger.error("TRUNCATE ERROR!!!\n" + e);
        }

        logger.info("# of users: {}", repo.count());
    }

    public User save(User user) {
        return repo.save(user);
    }

    public boolean checkLogin(String name, String password) {
        if(repo.findByNameAndPassword(name, password) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void truncate() {
        try {
            em.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
        } catch(Exception e) {
            logger.error("TRUNCATE ERROR!!!\n" + e);
        }
    }

}
