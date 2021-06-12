package com.paulkapa.btd6gamelogger.controller;

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
    private List<UpgradeEntity> upgradePaths;

    /**
     * Default constructor.
     * <p>
     * Performs <code>null</code> or <code>new</code> initializations.
     */
    @Autowired
    public WebController() {
        try {
            this.lastError = null;
            this.isLoggedIn = false;
            this.isfailedLoginAttempt = false;
            this.isFailedRegisterAttempt = false;
            this.user = null;
            this.maps = new ArrayList<>();
            this.towers = new ArrayList<>();
            this.upgradePaths = new ArrayList<>();
            this.btd6 = new GameEntity();
        } catch(Exception e) {
            this.lastError = e;
            logger.error("Error when construction \"com.paulkapa.btd6gamelogger.controller.WebController\"." +
                "\nIt is advised to check and fix any problems and then restart the application!\n",
                e.getCause());
            throw e;
        }
    }

    /**
     * Root application controller. ^/
     * @param rootModel used to pass data to views
     * @return the page that is supposed to be displayed
     */
    @GetMapping("/")
    public String rootController(Model rootModel) {
        try {
            rootModel.addAttribute("isFailedLoginAttempt", isfailedLoginAttempt);
            rootModel.addAttribute("isFailedRegisterAttempt", isFailedRegisterAttempt);
            if(this.isLoggedIn & !this.isfailedLoginAttempt & !this.isFailedRegisterAttempt & this.lastError == null) {
                logger.info("Sign up process complete for: " + this.user.toString() + ". Welcome!");
                rootModel.addAttribute("username", this.user.getName());
                rootModel.addAttribute("email", this.user.getEmail());
                return "homepage";
            } else if(!this.isLoggedIn & this.isFailedRegisterAttempt & this.lastError == null) {
                logger.warn("Registration status: {failed : " + this.isFailedRegisterAttempt + "}. Returning to registration form.");
                this.isFailedRegisterAttempt = false;
                rootModel.addAttribute("registerInfo", new User());
                return "register";
            } else if(!this.isLoggedIn & this.isfailedLoginAttempt & this.lastError == null) {
                logger.warn("Login status: {failed : " + this.isfailedLoginAttempt + "}. Returning to login form.");
                this.isfailedLoginAttempt = false;
                rootModel.addAttribute("loginInfo", new User());
                return "login";
            } else if(!this.isLoggedIn & this.lastError == null) {
                logger.warn("Performing first time login. Returning to login form.");
                rootModel.addAttribute("loginInfo", new User());
                return "login";
            } else {
                logger.error("Could not perform any view operations. Error controller called!\n" +
                    "Login Status : {isLoggedIn : " + this.isLoggedIn + "}" +
                    "\nLogin : {failed : " + this.isfailedLoginAttempt + "}" +
                    "\nRegistration : {failed : " + this.isFailedRegisterAttempt + "}" +
                    "\nLast error: " + this.lastError.toString());
                rootModel.addAttribute("lastError", ((this.lastError == null) ? "No error message available!" : this.lastError.toString()));
                this.lastError = null;
                return "/error";
            }
        } catch (Exception e) {
            this.lastError = e;
            throw e;
        }
    }

    /**
     * Performs register operations. ^/ checked ^/
     * @param formInfo used to retrieve information from login form
     * @param model used to pass a user template to the form
     * @return redirects to "/"
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
                this.lastError = e;
                logger.error("Register attempt failed: " +
                    "Creditentials typed in the register form might have been corrupted " +
                    "inside the database during processing. Please try again!",
                    e.getCause());
                throw e;
            }
            return "redirect:/";
        }
    }

    /**
     * Performs login operations. ^/ checked ^/
     * @param formInfo used to retrieve information from login form
     * @param model used to pass a user template to the form
     * @return redirects to "/"
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
                this.lastError = e;
                logger.error("Sign in attempt: " +
                    "Creditentials typed in the register form might have been corrupted " +
                    "inside the database during processing. Please try again!",
                    e.getCause());
                throw e;
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
            this.lastError = null;
            this.isLoggedIn = false;
            this.isfailedLoginAttempt = false;
            this.isFailedRegisterAttempt = false;
            this.user = null;
        } catch (Exception e) {
            this.lastError = e;
            throw e;
        }
        return "redirect:/";
    }

    /**
     * Handles main application page. ^/
     * @return redirect to "/"
     * @throws Exception
     */
    @PostMapping("/logger")
    public String mainApplication() throws Exception {
        throw new Exception("logger page not ready");
    }

    /**
     * Handles application errors.
     * @param request the request information
     * @param errorInfo model that passes data to views
     * @return error page
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model errorInfo) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        errorInfo.addAttribute("statusCode", "Status code: " + statusCode);
        errorInfo.addAttribute("exceptionCause", "Cause: " + exception == null ? null : exception.getCause());
        errorInfo.addAttribute("exceptionMessage", "Message: " + exception == null ? null : exception.getMessage());
        errorInfo.addAttribute("lastError", this.lastError == null ? "No previous error found! Probably you have manually accessed this page..." : this.lastError.toString());
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
            logger.error("TRUNCATE ERROR on table " + table + "!\n", e);
            throw e;
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
        this.ui.save(new User("user", "password"));
        this.lastError = null;
        this.isLoggedIn = false;
        this.isfailedLoginAttempt = false;
        this.isFailedRegisterAttempt = false;
        this.user = null;
        } catch (Exception e) {
            this.lastError = e;
            logger.error("Run method error: ", e);
            throw e;
        }
    }
}
