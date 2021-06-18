package com.paulkapa.btd6gamelogger.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Component
@Transactional(readOnly = false)
public class WebController implements ErrorController, CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(WebController.class);
    private boolean isLoggedIn;
    private boolean isfailedLoginAttempt;
    private boolean isFailedRegisterAttempt;
    private boolean isApplicationStarted;
    private Exception lastError;

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
    private List<UpgradeEntity> upgrades;

    /**
     * Default constructor.
     * <p>
     * Performs <code>false</code>, <code>null</code> or <code>new</code> initializations.
     */
    @Autowired
    public WebController() {
        try {
            this.lastError = null;
            this.isLoggedIn = false;
            this.isfailedLoginAttempt = false;
            this.isFailedRegisterAttempt = false;
            this.isApplicationStarted = false;
            this.user = null;
            this.maps = new ArrayList<>();
            this.towers = new ArrayList<>();
            this.upgrades = new ArrayList<>();
            this.btd6 = null;
        } catch(Exception e) {
            this.lastError = e;
            logger.error("Error when constructing \"com.paulkapa.btd6gamelogger.controller.WebController\". " +
                            "It is advised to check and fix any problems and then restart the application! " +
                            "If no solution can be found, contact developer.", e.getCause());
        }
    }

    /**
     * Root application controller.
     * @param rootModel used to pass data to views
     * @return the page that is supposed to be displayed
     */
    @GetMapping("/")
    public String rootController(Model rootModel) {
        try {
            rootModel.addAttribute("isFailedLoginAttempt", isfailedLoginAttempt);
            rootModel.addAttribute("isFailedRegisterAttempt", isFailedRegisterAttempt);
            rootModel.addAttribute("isApplicationStarted", isApplicationStarted);
            rootModel.addAttribute("currUser", ((this.user == null) ? new User() : this.user));
            rootModel.addAttribute("newUser", new User());
            if(!this.isApplicationStarted && this.lastError == null) {
                if(this.isLoggedIn && !this.isfailedLoginAttempt && !this.isFailedRegisterAttempt && this.lastError == null) {
                    logger.info("Sign up process complete for: " + this.user.toString() + ". Welcome!");
                    long accountAge = this.user.setAccountAge(System.currentTimeMillis() - this.user.getCreationDate().getTime());
                    rootModel.addAttribute("accountAge", User.visualizeAccountAge(accountAge));
                    rootModel.addAttribute("currentPageTitle", "Home");
                    return "index";
                } else if(!this.isLoggedIn && this.isFailedRegisterAttempt && this.lastError == null) {
                    logger.warn("Registration status: {failed : " + this.isFailedRegisterAttempt + "}. Returning to registration form.");
                    this.isFailedRegisterAttempt = false;
                    rootModel.addAttribute("currentPageTitle", "Register");
                    return "index";
                } else if(!this.isLoggedIn && this.isfailedLoginAttempt && this.lastError == null) {
                    logger.warn("Login status: {failed : " + this.isfailedLoginAttempt + "}. Returning to login form.");
                    this.isfailedLoginAttempt = false;
                    rootModel.addAttribute("currentPageTitle", "Login");
                    return "index";
                } else if(!this.isLoggedIn && this.lastError == null) {
                    logger.warn("Performing first time login. Returning to login form.");
                    rootModel.addAttribute("currentPageTitle", "Login");
                    return "index";
                } else {
                    logger.error("Could not perform any view operations. Error controller called!\n" +
                                    "Login Status : {isLoggedIn : " + this.isLoggedIn + "}" +
                                    "\nLogin : {failed : " + this.isfailedLoginAttempt + "}" +
                                    "\nRegistration : {failed : " + this.isFailedRegisterAttempt + "}" +
                                    "\nApplication : {started : " + this.isApplicationStarted + "}" +
                                    "\nLast error: " + this.lastError.toString());
                    rootModel.addAttribute("lastError", ((this.lastError == null) ? "unknown" : this.lastError.toString()));
                    rootModel.addAttribute("currentPageTitle", "Error");
                    return "/error";
                }
            } else if(this.isApplicationStarted && this.lastError == null) {
                logger.info("Request to access application detected! Enabling functionality allowed. Enjoy!");
                rootModel.addAttribute("currentPageTitle", "App");
                return "index";
            } else if(this.lastError != null) {
                logger.error("Could not perform any view operations. Error controller called!\n" +
                                "Login Status : {isLoggedIn : " + this.isLoggedIn + "}" +
                                "\nLogin : {failed : " + this.isfailedLoginAttempt + "}" +
                                "\nRegistration : {failed : " + this.isFailedRegisterAttempt + "}" +
                                "\nApplication : {started : " + this.isApplicationStarted + "}" +
                                "\nLast error: " + this.lastError.toString());
                rootModel.addAttribute("lastError", ((this.lastError == null) ? "unknown" : this.lastError.toString()));
                rootModel.addAttribute("currentPageTitle", "Error");
                return "/error";
            } else {
                logger.error("Error in root controller! No exception detected but no view could be selected.");
                rootModel.addAttribute("lastError", ((this.lastError == null) ? "unknown" :
                                            "Exception in root controller! No view could be selected." + this.lastError.toString()));
                rootModel.addAttribute("currentPageTitle", "Error");
                return "/error";
            }
        } catch (Exception e) {
            logger.error("Exception in root controller!", e);
            this.lastError = e;
            rootModel.addAttribute("lastError", ((this.lastError == null) ? "unknown" : this.lastError.toString()));
            rootModel.addAttribute("currentPageTitle", "Error");
            return "/error";
        }
    }

    /**
     * Performs register operations.
     * @param formInfo used to retrieve information from register form
     * @return redirects to "/"
     */
    @PostMapping("/signup")
    public String registerForm(@ModelAttribute User formInfo) {
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
                    (formInfo.getEmail().trim() != null && formInfo.getEmail().trim() != "") ? formInfo.getEmail().trim() : null,
                    new Timestamp(System.currentTimeMillis()));
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
                                    newUser.getEmail() + "] [" +
                                    newUser.getCreationDate() + "] {" +
                                    ((this.isFailedRegisterAttempt) ? "failed" : "successful") + "}");
                }
            } catch(Exception e) {
                this.isFailedRegisterAttempt = true;
                this.lastError = e;
                logger.error("Register attempt failed: " +
                                "Creditentials typed in the register form might have been corrupted " +
                                "inside the database during processing. Please try again!", e.getCause());
            }
            return "redirect:/";
        }
    }

    /**
     * Performs login operations.
     * @param formInfo used to retrieve information from login form
     * @return redirects to "/"
     */
    @PostMapping("/login")
    public String loginForm(@ModelAttribute User formInfo) {
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
                    this.isfailedLoginAttempt = true;
                } else {
                    newUser.setEmail(result.getEmail());
                    newUser.setCreationDate(result.getCreationDate());
                    this.user = newUser;
                    this.isLoggedIn = true;
                    this.isfailedLoginAttempt = false;
                }
                logger.info("Sign in attempt: [" + newUser.getName() + "] [" +
                                newUser.getPassword() + "] {" +
                                ((this.isLoggedIn && !this.isfailedLoginAttempt) ? "successful" : "failed") + "}");
            } catch(Exception e) {
                this.isfailedLoginAttempt = true;
                this.lastError = e;
                logger.error("Sign in attempt: " +
                                "Creditentials typed in the register form might have been corrupted " +
                                "inside the database during processing. Please try again!", e.getCause());
            }
            return "redirect:/";
        }
    }

    /**
     * Handles logout request.
     * @return redirects to "/"
     */
    @PostMapping("/logout")
    public String logoutButton() {
        try {
            this.isLoggedIn = false;
            this.isfailedLoginAttempt = false;
            this.isFailedRegisterAttempt = false;
            this.isApplicationStarted = false;
            this.user = null;
        } catch (Exception e) {
            this.lastError = e;
        }
        return "redirect:/";
    }

    /**
     * Handles main application page. ^/
     * @return redirect to "/"
     */
    @PostMapping("/logger")
    public String mainApplication() {
        try {
            if(this.isLoggedIn && !this.isfailedLoginAttempt) {
                this.isApplicationStarted = true;
            } else {
                this.isApplicationStarted = false;
            }
        } catch (Exception e) {
            this.lastError = e;
        }
        return "redirect:/";
    }

    /**
     * Handler for return to home request.
     * @return redirect to "/"
     */
    @PostMapping(value="/home")
    public String homePage() {
        try {
            this.isApplicationStarted = false;
        } catch (Exception e) {
            this.lastError = e;
            logger.error("Error when redirecting to home page:", e);
        }
        return "redirect:/";
    }

    /**
     * Handles application errors.
     * @param request the HTTP request information
     * @param errorInfo model that passes data to views
     * @return error page
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model errorInfo) {
        errorInfo.addAttribute("isFailedLoginAttempt", isfailedLoginAttempt);
        errorInfo.addAttribute("isFailedRegisterAttempt", isFailedRegisterAttempt);
        errorInfo.addAttribute("currUser", this.user);
        errorInfo.addAttribute("newUser", new User());
        try {
            Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
            Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
            errorInfo.addAttribute("statusCode", ((statusCode == null) ? "unknow" : statusCode));
            errorInfo.addAttribute("exceptionCause", ((exception == null) ? "unkown" : exception.getCause()));
            errorInfo.addAttribute("exceptionMessage", ((exception == null) ? "unknown" : exception.toString()));
        } catch (Exception e) {
            this.lastError = e;
            logger.error("Error in error controller: ", e);
        }
        errorInfo.addAttribute("lastError", ((this.lastError == null) ? "unknown" : this.lastError.toString()));
        errorInfo.addAttribute("currentPageTitle", "Error");
        return "index";
    }

    /**
     * Truncates the provided table.
     */
    public void truncate(String table) {
        try {
            em.createNativeQuery("TRUNCATE TABLE " + table).executeUpdate();
            logger.warn("TRUNCATED table " + table + "!");
        } catch(Exception e) {
            this.lastError = e;
            logger.error("TRUNCATE ERROR on table " + table + "!\n", e);
        }
    }

    /**
     * Runs at application start and initializes the database with one single user.
     * <p>
     * May be called again at any time by classes with access to it.
     * <p>
     * <code>User created:
     * <li>Username: user;
     * <li>Password: password;
     * <li>Email: null.</code>
     */
    @Override
    public void run(String... args) {
        try {
            this.truncate("users");
            this.truncate("maps");
            this.truncate("upgrades");
            this.truncate("towers");
            this.ui.save(new User("user", "password", null, new Timestamp(System.currentTimeMillis())));
            mi.save(new MapEntity("Monkey Lane", "Begginer", 800.0d, 100));
            ti.save(new TowerEntity("Dart Monkey", "Primary", 0.0d, 0.0d));
            upi.save(new UpgradeEntity("Sharper Darts", 1, 1, 170.0d, ti.findByName("Dart Monkey")));
            logger.info(ui.findByName("user").toString());
            logger.info("Saved default user in database!");
            this.maps = mi.findAll();
            logger.info(this.maps.toString());
            logger.info("Retrieved maps from database!");
            this.towers = ti.findAll();
            logger.info(this.towers.toString());
            logger.info("Retrieved towers from database!");
            this.upgrades = upi.findAll();
            logger.info(this.upgrades.toString());
            logger.info("Retrieved upgrades from database!");
        } catch (Exception e) {
            this.lastError = e;
            logger.error("Run method error: ", e);
        }
    }
}
