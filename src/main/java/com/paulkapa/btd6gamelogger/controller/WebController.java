package com.paulkapa.btd6gamelogger.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.paulkapa.btd6gamelogger.database.game.MapInterface;
import com.paulkapa.btd6gamelogger.database.game.TowerInterface;
import com.paulkapa.btd6gamelogger.database.game.UpgradePathInterface;
import com.paulkapa.btd6gamelogger.database.system.UserInterface;
import com.paulkapa.btd6gamelogger.models.game.GameEntity;
import com.paulkapa.btd6gamelogger.models.game.MapEntity;
import com.paulkapa.btd6gamelogger.models.game.TowerEntity;
import com.paulkapa.btd6gamelogger.models.game.UpgradeEntity;
import com.paulkapa.btd6gamelogger.models.system.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@Component
@Transactional(readOnly = false)
public class WebController implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(WebController.class);
    private boolean isLoggedIn;
    private boolean isfailedLoginAttempt;
    private boolean isFailedRegisterAttempt;
    private String lastError;

    private GameEntity btd6;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserInterface ui;
    @Autowired
    private MapInterface mi;
    @Autowired
    private TowerInterface ti;
    @Autowired
    private UpgradePathInterface upi;

    private User user;
    private List<MapEntity> maps;
    private List<TowerEntity> towers;
    private List<UpgradeEntity> upgradePaths;

    /**
     * Default constructor. ^/ checked ^/
     */
    @Autowired
    public WebController() {
        try {
            this.lastError = "";
            this.isLoggedIn = false;
            this.isfailedLoginAttempt = false;
            this.isFailedRegisterAttempt = false;
            this.user = new User();
            this.maps = new ArrayList<>();
            this.towers = new ArrayList<>();
            this.upgradePaths = new ArrayList<>();
            this.btd6 = new GameEntity();
        } catch(Exception e) {
            this.lastError = e.toString();
            logger.error("Error when construction \"com.paulkapa.btd6gamelogger.controller.WebController\"." +
                "\nIt is advised to check and fix any problems and then restart the application!\n",
                e.getCause());
        }
    }

    /**
     * Root application controller. ^/ checked ^/
     */
    @GetMapping("/")
    public String rootController(Model appUser) {
        appUser.addAttribute("isFailedLoginAttempt", isfailedLoginAttempt);
        appUser.addAttribute("isFailedRegisterAttempt", isFailedRegisterAttempt);
        if(this.isLoggedIn & !this.isfailedLoginAttempt & !this.isFailedRegisterAttempt & this.lastError == "") {
            logger.info("Sign up process complete for: " + this.user.toString() + ". Welcome!");
            appUser.addAttribute("username", this.user.getName());
            appUser.addAttribute("email", this.user.getEmail());
            return "homepage";
        } else if(!this.isLoggedIn & this.isFailedRegisterAttempt & this.lastError == "") {
            logger.warn("Registration status: {failed : " + this.isFailedRegisterAttempt + "}. Returning to registration form.");
            this.isFailedRegisterAttempt = false;
            appUser.addAttribute("registerInfo", new User());
            return "register";
        } else if(!this.isLoggedIn & this.isfailedLoginAttempt & this.lastError == "") {
            logger.warn("Login status: {failed : " + this.isfailedLoginAttempt + "}. Returning to login form.");
            this.isfailedLoginAttempt = false;
            appUser.addAttribute("loginInfo", new User());
            return "login";
        } else if(!this.isLoggedIn & this.lastError == "") {
            logger.warn("Performing first time login. : {failed : " + this.isfailedLoginAttempt + "}. Returning to login form.");
            appUser.addAttribute("loginInfo", new User());
            return "login";
        } else {
            logger.error("Could not perform any view operations. Error controller called!\n" +
                "Login Status : {isLoggedIn : " + this.isLoggedIn + "}" +
                "\nLogin : {failed : " + this.isfailedLoginAttempt + "}" +
                "\nRegistration : {failed : " + this.isFailedRegisterAttempt + "}" +
                "\nLast error:" + this.lastError);
            appUser.addAttribute("lastError", ((this.lastError == "") ? "No error message available!" : this.lastError));
            this.lastError = "";
            return "error";
        }
    }

    /**
     * Performs register operations. ^/ checked ^/
     */
    @PostMapping("/signup")
    public String registerForm(@ModelAttribute User formInfo, Model model) {
        this.user = new User();
        model.addAttribute("registerInfo", this.user);
        this.isLoggedIn = false;
        if(formInfo.getName() == null) {
            this.isFailedRegisterAttempt = true;
            logger.warn("Request: redirect to registration page detected!");
            return "redirect:/";
        } else {
            try {
                var newUser = new User(
                    formInfo.getName().trim(),
                    formInfo.getPassword().trim(),
                    (formInfo.getEmail() != null & formInfo.getEmail().trim() != "") ? formInfo.getEmail().trim() : null
                    );
                if(this.ui.findByNameAndPassword(newUser.getName(), newUser.getPassword()) != null) {
                    this.isFailedRegisterAttempt = true;
                    logger.warn("Register attempt: User [" + newUser.getName() + "] already exists in database. " +
                        "Try logging in with these creditentials or register using another username!");
                } else {
                    this.ui.save(newUser);
                    this.user = this.ui.findByNameAndPassword(newUser.getName(), newUser.getPassword());
                    this.isFailedRegisterAttempt = !(newUser == this.user);
                    logger.info("Register attempt: [" +
                        newUser.getName() + "] [" +
                        newUser.getPassword() + "] [" +
                        newUser.getEmail() + "] {" +
                        ((this.isFailedRegisterAttempt) ? "failed" : "successful") + "}");
                }
            } catch(Exception e) {
                this.isFailedRegisterAttempt = true;
                this.lastError = e.toString();
                logger.error("Register attempt failed: " +
                    "Creditentials typed in the register form might have been corrupted " +
                    "inside the database during processing. Please try again!\n",
                    e.getCause());
            }
            return "redirect:/";
        }
    }

    /**
     * Performs login operations. ^/ checked ^/
     */
    @PostMapping("/login")
    public String loginForm(@ModelAttribute User formInfo, Model model) {
        this.user = new User();
        model.addAttribute("loginInfo", this.user);
        this.isLoggedIn = false;
        if(formInfo.getName() == null) {
            this.isfailedLoginAttempt = true;
            logger.warn("Request: redirect to login page detected!");
            return "redirect:/";
        } else {
            try {
                var newUser = new User(formInfo.getName().trim(), formInfo.getPassword().trim());
                var result = ui.findByNameAndPassword(newUser.getName(), newUser.getPassword());
                if(result == null) {
                    this.isLoggedIn = false;
                    this.isfailedLoginAttempt = true;
                } else {
                    newUser.setEmail(result.getEmail());
                    this.user = newUser;
                    this.isLoggedIn = true;
                    this.isfailedLoginAttempt = false;
                }
                logger.info("Sign in attempt: [" + newUser.getName() + "] [" +
                    newUser.getPassword() + "] {" +
                    ((this.isLoggedIn & !this.isfailedLoginAttempt) ? "successful" : "failed") + "}");
            } catch(Exception e) {
                this.isfailedLoginAttempt = true;
                this.lastError = e.toString();
                logger.error("Sign in attempt: " +
                    "Creditentials typed in the register form might have been corrupted " +
                    "inside the database during processing. Please try again!\n",
                    e.getCause());
            }
            return "redirect:/";
        }
    }

    /**
     * Handles logout request. ^/ checked ^/
     */
    @PostMapping("/logout")
    public String logoutButton() {
        this.lastError = "";
        this.isLoggedIn = false;
        this.isfailedLoginAttempt = false;
        this.isFailedRegisterAttempt = false;
        this.user = new User();
        return "redirect:/";
    }

    /**
     * Handles application errors.
     */
    @ExceptionHandler
    public String appError(Exception errorInfo, Model model) {
        this.lastError = "";
        this.lastError.concat("Error occurred:\n");
        this.lastError.concat((errorInfo != null) ? errorInfo.toString() : "Cannot trace error!");
        this.lastError.concat("\n");
        model.addAttribute("errorInfo", lastError.toString());
        logger.info(lastError);
        return "error";
    }

    /**
     * Truncates the provided table. ^/ checked ^/
     */
    public void truncate(String table) {
        try {
            em.createNativeQuery("TRUNCATE TABLE " + table).executeUpdate();
            logger.warn("TRUNCATED table " + table + "!");
        } catch(Exception e) {
            logger.error("TRUNCATE ERROR on table " + table + "!\n", e);
        }
    }

    /**
     * @
     * Runs at application start and initializes the database with one single user.
     * <p>
     * May be called again at any time by classes with access to it.
     * <p>
     * <ul>
     * <li>Username: user;
     * <li>Password: password;
     * <li>Email: null.
     */
    @Override
    public void run(String... args) throws Exception {
        this.truncate("users");
        this.ui.save(new User("user", "password"));
        this.user = new User();
        this.lastError = "";
        this.isLoggedIn = false;
        this.isfailedLoginAttempt = false;
        this.isFailedRegisterAttempt = false;
    }
}
