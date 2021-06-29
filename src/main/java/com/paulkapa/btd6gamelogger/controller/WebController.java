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
import com.paulkapa.btd6gamelogger.models.helper.AppSetup;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Component
@Transactional(readOnly = false)
public class WebController implements ErrorController, CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(WebController.class);
    private boolean isLoginAllowed;
    private boolean isfailedLoginAttempt;
    private boolean isLoggedIn;
    private boolean isRegisterAllowed;
    private boolean isFailedRegisterAttempt;
    private String failedMessage;
    private boolean isApplicationAllowed;
    private boolean isApplicationStarted;
    private Exception lastError;

    private GameEntity btd6;
    private AppSetup lastAppSetup;

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
     */
    @Autowired
    public WebController() {
        try {
            this.isLoginAllowed = false;
            this.isfailedLoginAttempt = false;
            this.isLoggedIn = false;
            this.isRegisterAllowed = false;
            this.isFailedRegisterAttempt = false;
            this.failedMessage = "";
            this.isApplicationAllowed = false;
            this.isApplicationStarted = false;
            this.lastError = new Exception("Application has no errors!");
            this.user = null;
            this.maps = new ArrayList<>();
            this.towers = new ArrayList<>();
            this.upgrades = new ArrayList<>();
            this.btd6 = new GameEntity();
            this.lastAppSetup = new AppSetup();
        } catch(Exception e) {
            logger.error("Error when constructing \"com.paulkapa.btd6gamelogger.controller.WebController\". " +
                            "It is advised to check and fix any problems and then restart the application! " +
                            "If no solution can be found, contact developer.", e.getCause());
            this.lastError = e;
            this.isApplicationStarted = false;
        }
    }

    /**
     * Root application controller.
     * @param rootModel used to pass data to views
     * @return the view required to be displayed
     */
    @GetMapping("/")
    public String rootController(Model rootModel) {
        // Check all actions performed. Request /error if Exception thrown.
        try {
            rootModel.addAttribute("isLogedIn", this.isLoggedIn);
            rootModel.addAttribute("isApplicationAllowed", this.isApplicationAllowed);
            rootModel.addAttribute("uname", ((this.user == null) ? null : this.user.getName()));
            rootModel.addAttribute("uemail", ((this.user == null) ? null : this.user.getEmail()));
            rootModel.addAttribute("uaccountAge", null);
            rootModel.addAttribute("newUser", new User());
            // Get App page
            if(!this.isLoginAllowed && this.isLoggedIn && !this.isfailedLoginAttempt &&
                !this.isRegisterAllowed &&
                this.isApplicationAllowed && this.isApplicationStarted) {
                    rootModel.addAttribute("maps", this.maps);
                    rootModel.addAttribute("towers", this.towers);
                    rootModel.addAttribute("upgrades", this.upgrades);
                    rootModel.addAttribute("game", this.btd6);
                    rootModel.addAttribute("failedMessage", this.failedMessage);
                    rootModel.addAttribute("appSetup", this.lastAppSetup);
                    logger.info("root - app : " + this.lastAppSetup.toString());
                    rootModel.addAttribute("appData", btd6);
                    rootModel.addAttribute("currentPageTitle", "App");
                    logger.info("Application accessed. Enjoy!");
                return "index";
            }
            // Get Home page
            else if(!this.isLoginAllowed && this.isLoggedIn && !this.isfailedLoginAttempt &&
                !this.isRegisterAllowed &&
                this.isApplicationAllowed && !this.isApplicationStarted) {
                    this.user.setAccountAge(System.currentTimeMillis() - this.user.getCreationDate().getTime());
                    rootModel.addAttribute("newUser", null);
                    rootModel.addAttribute("uaccountAge", User.visualizeAccountAge(this.user.getAccountAge()));
                    rootModel.addAttribute("failedMessage", this.failedMessage);
                    rootModel.addAttribute("maps", this.maps);
                    rootModel.addAttribute("diff", new String[] {"Easy", "Medium", "Hard"});
                    rootModel.addAttribute("appSetup", new AppSetup());
                    logger.info("root - home : " + this.lastAppSetup.toString());
                    rootModel.addAttribute("currentPageTitle", "Home");
                    logger.info("Sign up process complete for: " + this.user.getName() + ". Welcome!");
                return "index";
            }
            // Get Registration Page
            else if(!this.isLoginAllowed && !this.isLoggedIn &&
                this.isRegisterAllowed &&
                !this.isApplicationAllowed && !this.isApplicationStarted) {
                    rootModel.addAttribute("isFailedRegisterAttempt", this.isFailedRegisterAttempt);
                    rootModel.addAttribute("failedMessage", this.failedMessage);
                    rootModel.addAttribute("currentPageTitle", "Register");
                    logger.warn("Registration status: {failed : " + this.isFailedRegisterAttempt + "}. Returning to registration form.");
                return "index";
            }
            // Get Login Page
            else if(this.isLoginAllowed && !this.isLoggedIn &&
                !this.isRegisterAllowed &&
                !this.isApplicationAllowed && !this.isApplicationStarted) {
                    rootModel.addAttribute("isfailedLoginAttempt", this.isfailedLoginAttempt);
                    rootModel.addAttribute("failedMessage", this.failedMessage);
                    rootModel.addAttribute("currentPageTitle", "Login");
                    logger.warn("Login status: {failed : " + this.isfailedLoginAttempt + "}. Returning to login form.");
                return "index";
            }
            // Get First Login Page -- switch to welcome page
            else if(!this.isLoginAllowed && !this.isLoggedIn &&
                !this.isRegisterAllowed && !this.isFailedRegisterAttempt &&
                !this.isApplicationAllowed && !this.isApplicationStarted) {
                    rootModel.addAttribute("isfailedLoginAttempt", this.isfailedLoginAttempt);
                    rootModel.addAttribute("failedMessage", this.failedMessage);
                    rootModel.addAttribute("currentPageTitle", "Login");
                    logger.info("Performing First Time Login...");
                return "index";
            }
            // Request /error
            else {
                logger.error("Could not perform any view operations. Error controller called!\n" +
                                "Login Status : " + "{allowed : " + this.isLoginAllowed + "} {isLoggedIn : " + this.isLoggedIn + "}" +
                                "\nLogin : {failed : " + this.isfailedLoginAttempt + "}" +
                                "\nRegistration Status: {allowed : " + this.isRegisterAllowed + "}" +
                                "\nRegistration: {failed : " + this.isFailedRegisterAttempt + "}" +
                                "\nApplication Status: {allowed : " + this.isApplicationAllowed + "}" +
                                "\nApplication: {started : " + this.isApplicationStarted + "}" +
                                "\nLast error: " + this.lastError.toString());
                rootModel.addAttribute("currentPageTitle", "Error");
                this.isApplicationStarted = false;
                return "/error";
            }
        }
        // Catch Exception and Request /error
        catch (Exception e) {
            logger.error("Exception in root controller!", e);
            this.lastError = e;
            this.isApplicationStarted = false;
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
        this.isLoginAllowed = false;
        this.isLoggedIn = false;
        this.isRegisterAllowed = true;
        this.isFailedRegisterAttempt = true;
        this.isApplicationAllowed = false;
        this.isApplicationStarted = false;
        // If method called for redirect
        if(formInfo.getName() == null) {
            logger.warn("Request: redirect to registration form.");
            return "redirect:/";
        }
        // If method called for handling form
        else {
            try {
                // New User constructed from form details
                User newUser = new User(formInfo.getName().trim(),
                                        User.encryptPassword(formInfo.getPassword().trim()),
                                        (formInfo.getEmail().trim() != null && formInfo.getEmail().trim() != "") ? formInfo.getEmail().trim() : null,
                                        new Timestamp(System.currentTimeMillis()));
                // Check if account already exists in database
                if(this.ui.findByNameAndPassword(newUser.getName(), newUser.getPassword()) != null) {
                    this.failedMessage = "Account already exists!";
                    logger.warn("Register attempt: User [" + newUser.getName() + "] already exists in database.");
                }
                // Perform final check and save account
                else {
                    this.ui.save(newUser);
                    this.user = this.ui.findByNameAndPassword(newUser.getName(), newUser.getPassword());
                    this.isFailedRegisterAttempt = !(newUser == this.user);
                    this.user = null;
                    this.isRegisterAllowed = !this.isFailedRegisterAttempt;
                    logger.info("Account tried to register: [" + newUser.getName() + "] {failed : " + this.isFailedRegisterAttempt + "}");
                }
            } catch(Exception e) {
                logger.error("Register attempt failed due to Exception.", e.getCause());
                this.lastError = e;
                this.failedMessage = "Registration failed. Application error detected, please try again!";
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
        this.isLoginAllowed = true;
        this.isLoggedIn = false;
        this.isfailedLoginAttempt = true;
        this.isRegisterAllowed = false;
        this.isApplicationAllowed = false;
        this.isApplicationStarted = false;
        // If method called for redirect
        if(formInfo.getName() == null) {
            logger.warn("Request: redirect to login form!");
            return "redirect:/";
        }
        // If method called for handling form
        else {
            try {
                // New User constructed from form details
                User newUser = new User(formInfo.getName().trim(), User.encryptPassword(formInfo.getPassword().trim()));
                // Database querry result for form details
                User result = ui.findByNameAndPassword(newUser.getName(), newUser.getPassword());
                // Check if account exists in database
                if(result != null) {
                    newUser.setEmail(result.getEmail());
                    newUser.setCreationDate(result.getCreationDate());
                    this.user = newUser;
                    this.isLoginAllowed = false;
                    this.isLoggedIn = true;
                    this.isfailedLoginAttempt = false;
                    this.isApplicationAllowed = true;
                }
                logger.info("Sign in attempt: [" + newUser.getName() + "] {" +
                                ((this.isLoggedIn && !this.isfailedLoginAttempt) ? "successful" : "failed") + "}");
            } catch(Exception e) {
                logger.error("Login attempt failed due to Exception.", e.getCause());
                this.lastError = e;
                this.failedMessage = "Login failed. Application error detected, please try again!";
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
        this.isLoginAllowed = true;
        this.isLoggedIn = false;
        this.isfailedLoginAttempt = false;
        this.isRegisterAllowed = false;
        this.isFailedRegisterAttempt = false;
        this.failedMessage = "You have been logged out";
        this.isApplicationAllowed = false;
        this.isApplicationStarted = false;
        this.user = null;
        return "redirect:/";
    }

    /**
     * Handles app access request. ^/
     * @return redirect to "/"
     */
    @PostMapping("/logger")
    public String mainApplication(@ModelAttribute AppSetup appSetup, Model model) {
        // Access allowed
        if(!this.isLoginAllowed && this.isLoggedIn && !this.isfailedLoginAttempt &&
                !this.isRegisterAllowed &&
                this.isApplicationAllowed) {
            this.isApplicationStarted = true;
            logger.info("post - app - last: " + this.lastAppSetup.toString());
            logger.info("post - app - form: " + appSetup.toString());
            if(appSetup.getMapName() != null && appSetup.getMapName() != "Select...") {
                this.lastAppSetup = appSetup;
                this.btd6 = new GameEntity(this.user, this.mi.findByName(lastAppSetup.getMapName()), lastAppSetup.getDiff());
            } else {
                this.lastAppSetup = new AppSetup();
                this.btd6 = new GameEntity();
            }
            model.addAttribute("appData", btd6);
        }
        // Else
        else {
            this.isApplicationStarted = false;
        }
        return "redirect:/";
    }

    /**
     * Handles go home request.
     * @return
     */
    @PostMapping(value="/home")
    public String goHomeButton() {
        this.isApplicationStarted = false;
        return "redirect:/";
    }

    /**
     * Handles application errors.
     * @param request the HTTP request information
     * @param errorInfo model that passes data to views
     * @return error page
     */
    @RequestMapping("/error")
    @ExceptionHandler
    public String handleError(HttpServletRequest request, Model errorInfo) {
        this.isApplicationStarted = false;
        errorInfo.addAttribute("isLoginAllowed", this.isLoginAllowed);
        errorInfo.addAttribute("isLoggedIn", this.isLoggedIn);
        errorInfo.addAttribute("isFailedLoginAttempt", this.isfailedLoginAttempt);
        errorInfo.addAttribute("isRegisterAllowed", this.isRegisterAllowed);
        errorInfo.addAttribute("isFailedRegisterAttempt", this.isFailedRegisterAttempt);
        errorInfo.addAttribute("isApplicationAllowed", this.isApplicationAllowed);
        errorInfo.addAttribute("isApplicationStarted", this.isApplicationStarted);
        errorInfo.addAttribute("failedMessage", this.failedMessage);
        errorInfo.addAttribute("currUser", this.user);
        // Get request info if exists
        try {
            Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
            Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
            errorInfo.addAttribute("statusCode", ((statusCode == null) ? "unknow" : statusCode));
            errorInfo.addAttribute("exceptionCause", ((exception == null) ? "unkown" : exception.getCause()));
            errorInfo.addAttribute("exceptionMessage", ((exception == null) ? "unknown" : exception.toString()));
        } catch (Exception e) {
            logger.error("Exception in handleError().", e);
            this.lastError = e;
        }
        errorInfo.addAttribute("lastError", this.lastError.toString());
        errorInfo.addAttribute("currentPageTitle", "Error");
        return "error";
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
            this.isApplicationStarted = false;
            logger.error("TRUNCATE ERROR on table " + table + "!\n", e);
        }
    }

    /**
     * Runs at application start and initializes the database with one single user.
     * <p>
     * May be called again at any time by classes with access to it.
     * <p>
     * <code>User created:
     * <li>Username: btd6gluser;
     * <li>Password: pass;
     * <li>Email: null.</code>
     */
    @Override
    public void run(String... args) {
        try {
            this.truncate("users");
            this.truncate("maps");
            this.truncate("upgrades");
            this.truncate("towers");
            this.ui.save(new User("btd6gluser", User.encryptPassword("pass"), "example@domain", new Timestamp(System.currentTimeMillis())));
            mi.save(new MapEntity("Monkey Meadow", "Begginer", 1.0d, 1));
            mi.save(new MapEntity("Balance", "Intermediate", 1.0d, 1));
            mi.save(new MapEntity("X Factor", "Advanced", 1.0d, 1));
            mi.save(new MapEntity("Sanctuary", "Expert", 1.0d, 1));
            ti.save(new TowerEntity("Dart Monkey", "Primary", 1.0d, 1.0d));
            ti.save(new TowerEntity("Boomerang Thrower", "Primary", 0.0d, 0.0d));
            upi.save(new UpgradeEntity("upgrade0.1", 1, 1, 1.0d, ti.findByName("Dart Monkey")));
            upi.save(new UpgradeEntity("upgrade1.1", 1, 1, 1.0d, ti.findByName("Boomerang Thrower")));
            upi.save(new UpgradeEntity("upgrade0.2", 1, 1, 1.0d, ti.findByName("Dart Monkey")));
            upi.save(new UpgradeEntity("upgrade1.2", 1, 1, 1.0d, ti.findByName("Boomerang Thrower")));
            upi.save(new UpgradeEntity("upgrade1.3", 1, 1, 1.0d, ti.findByName("Boomerang Thrower")));
            upi.save(new UpgradeEntity("upgrade0.3", 1, 1, 1.0d, ti.findByName("Dart Monkey")));
            upi.save(new UpgradeEntity("upgrade1.4", 1, 1, 1.0d, ti.findByName("Boomerang Thrower")));
            logger.info(ui.findByName("btd6gluser").toString());
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
            logger.error("Run method error: ", e);
            this.lastError = e;
            this.isApplicationStarted = false;
        }
    }
}
