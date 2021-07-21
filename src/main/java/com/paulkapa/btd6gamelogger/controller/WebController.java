package com.paulkapa.btd6gamelogger.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import com.paulkapa.btd6gamelogger.database.game.GameContainer;
import com.paulkapa.btd6gamelogger.database.system.UserInterface;
import com.paulkapa.btd6gamelogger.models.game.Map;
import com.paulkapa.btd6gamelogger.models.system.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <b>Web Servlet controller</b>
 * <p>
 * Provides Web Requests handling for URL's mapped by this application.
 * <p>
 * Handles all user session data and login information.
 *
 * @see org.springframework.boot.web.servlet.error.ErrorController
 * @see org.springframework.boot.CommandLineRunner
 * @see org.springframework.context.ApplicationContextAware
 */
@Controller
@Component
@Transactional(readOnly = false)
public class WebController implements ErrorController, CommandLineRunner, ApplicationContextAware {

    /**
     * org.slf4j.Logger accessor.
     * <p>
     * Prints logs to the console in the same format as the default Spring
     * Application logs.
     */
    private static final Logger logger = LoggerFactory.getLogger(WebController.class);
    /**
     * The ApplicationContext attached to the running Spring Application.
     */
    private ApplicationContext context;

    public static final String REDIRECT = "redirect:/";
    public static final String PAGE = "index";
    public static final String PAGE_TITLE_ATTR = "currentPageTitle";

    /**
     * Stores if login page access is allowed.
     */
    private boolean isLoginAllowed;
    /**
     * Stores if a failed login attempt was performed.
     */
    private boolean isFailedLoginAttempt;
    /**
     * Stores if an user is currently logged in.
     */
    private boolean isLoggedIn;
    /**
     * Stores if register page access is allowed.
     */
    private boolean isRegisterAllowed;
    /**
     * Stores if a failed register attempt was performed.
     */
    private boolean isFailedRegisterAttempt;
    /**
     * Stores if restricted access pages are allowed.
     */
    private boolean isApplicationAllowed;
    /**
     * Stores if user entered a game logging session.
     */
    private boolean isApplicationStarted;
    /**
     * Stores the last fail message.
     */
    private String failedMessage;
    /**
     * Stores the last Exception thrown by the application.
     */
    private Exception lastError;
    /**
     * Stores if the application shutdown was initiated.
     */
    private boolean isShutdownStarted;

    /**
     * Session container.
     */
    private GameContainer btd6;
    /**
     * Selected map for the current session.
     */
    private Map lastAppSelection;

    /**
     * The Persistance Context EntityManager used to send queries to the database.
     */
    @PersistenceContext
    private EntityManager em;
    /**
     * The UserInterface Repository used to query the "users" table.
     */
    @Autowired
    private UserInterface ui;

    /**
     * Default constructor.
     */
    @Autowired
    public WebController() {
        this.isLoginAllowed = false;
        this.isFailedLoginAttempt = false;
        this.isLoggedIn = false;
        this.isRegisterAllowed = false;
        this.isFailedRegisterAttempt = false;
        this.isApplicationAllowed = false;
        this.isApplicationStarted = false;
        this.failedMessage = null;
        this.lastError = new Exception("Application has no errors!");
        this.isShutdownStarted = false;
        this.btd6 = new GameContainer();
        this.lastAppSelection = null;
    }

    /**
     * Root application controller.
     *
     * @param rootModel the list of attributes to be passed to the view engine
     * @return the view name to be displayed
     */
    @GetMapping("/")
    public String rootController(Model rootModel) {
        try {
            // Shutdown notifier
            rootModel.addAttribute("shutdown", this.isShutdownStarted);
            // Login
            rootModel.addAttribute("isLoginAllowed", this.isLoginAllowed);
            rootModel.addAttribute("isLoggedIn", this.isLoggedIn);
            rootModel.addAttribute("isFailedLoginAttempt", this.isFailedLoginAttempt);
            // Register
            rootModel.addAttribute("isRegisterAllowed", this.isRegisterAllowed);
            rootModel.addAttribute("isFailedRegisterAttempt", this.isFailedRegisterAttempt);
            // App
            rootModel.addAttribute("isApplicationAllowed", this.isApplicationAllowed);
            rootModel.addAttribute("isApplicationStarted", this.isApplicationStarted);
            rootModel.addAttribute("failedMessage", this.failedMessage);
            this.failedMessage = null;
            // User
            rootModel.addAttribute("uname", ((this.isLoggedIn) ? this.btd6.getUser().getName() : null));
            rootModel.addAttribute("uemail", ((this.isLoggedIn) ? this.btd6.getUser().getName() : null));
            rootModel.addAttribute("uaccountAge", null);
            rootModel.addAttribute("newUser", ((this.isLoggedIn) ? this.btd6.getUser() : User.getDefaultUser()));
            // Check shutdown status
            if (this.isShutdownStarted) {
                rootModel.addAttribute("context", (ConfigurableApplicationContext) context);
                return "backup";
            } else {
                // Get App page
                if (!this.isLoginAllowed && this.isLoggedIn && !this.isRegisterAllowed && this.isApplicationAllowed
                        && this.isApplicationStarted) {
                    rootModel.addAttribute("maps", this.btd6.getMap());
                    rootModel.addAttribute("towers", this.btd6.getTowers());
                    rootModel.addAttribute("upgrades", this.btd6.getUpgrades());
                    rootModel.addAttribute("diff", this.btd6.getDiff());
                    rootModel.addAttribute("mode", this.btd6.getMode());
                    rootModel.addAttribute("appSetup", this.lastAppSelection);
                    rootModel.addAttribute("appData", this.btd6);
                    rootModel.addAttribute(WebController.PAGE_TITLE_ATTR, "App");
                    logger.info("Application accessed. Enjoy!");
                    return WebController.PAGE;
                }
                // Get Home page
                else if (!this.isLoginAllowed && this.isLoggedIn && !this.isRegisterAllowed
                        && this.isApplicationAllowed) {
                    this.btd6.getUser().setAccountAge();
                    rootModel.addAttribute("uaccountAge",
                            User.visualizeAccountAge(this.btd6.getUser().getAccountAge()));
                    rootModel.addAttribute("maps", GameContainer.getDefaultMaps());
                    rootModel.addAttribute("diffs", GameContainer.DIFFICULTIES);
                    rootModel.addAttribute("modes", GameContainer.GAME_MODES);
                    rootModel.addAttribute("appSetup", new Map());
                    rootModel.addAttribute(WebController.PAGE_TITLE_ATTR, "Home");
                    logger.info("Sign up process complete for: " + this.btd6.getUser().getName() + ". Welcome!");
                    return WebController.PAGE;
                }
                // Get Registration Page
                else if (!this.isLoginAllowed && !this.isLoggedIn && this.isRegisterAllowed
                        && !this.isApplicationAllowed && !this.isApplicationStarted) {
                    rootModel.addAttribute(WebController.PAGE_TITLE_ATTR, "Register");
                    logger.warn("Registration status: {failed : " + this.isFailedRegisterAttempt
                            + "}. Returning to registration form.");
                    return WebController.PAGE;
                }
                // Get Login Page
                else if (this.isLoginAllowed && !this.isLoggedIn && !this.isRegisterAllowed
                        && !this.isApplicationAllowed && !this.isApplicationStarted) {
                    rootModel.addAttribute(WebController.PAGE_TITLE_ATTR, "Login");
                    logger.warn("Login status: {failed : " + this.isFailedLoginAttempt + "}. Returning to login form.");
                    return WebController.PAGE;
                }
                // Get First Login Page
                // TODO: change to welcome page
                else if (!this.isLoginAllowed && !this.isLoggedIn && !this.isRegisterAllowed
                        && !this.isApplicationAllowed && !this.isApplicationStarted) {
                    rootModel.addAttribute(WebController.PAGE_TITLE_ATTR, "Login");
                    logger.info("Performing First Time Login...");
                    return WebController.PAGE;
                }
                // Request /error
                else {
                    logger.error(
                            "Could not perform any view operations. Error controller called!" + "\nLogin Status:"
                                    + "\n\t{allowed : " + this.isLoginAllowed + "} {\n\tloggedIn : " + this.isLoggedIn
                                    + "}" + "\n\t{failed : " + this.isFailedLoginAttempt + "}"
                                    + "\nRegistration Status:" + "\n\t{allowed : " + this.isRegisterAllowed + "}"
                                    + "\n\t{failed : " + this.isFailedRegisterAttempt + "}"
                                    + "\nRegistration: {failed : " + this.isFailedRegisterAttempt + "}"
                                    + "\nApplication Status:" + "\n\t{allowed : " + this.isApplicationAllowed + "}"
                                    + "\n\t{started : " + this.isApplicationStarted + "}" + "\nLast error: ",
                            this.lastError);
                    return "/error";
                }
            }
        }
        // Catch any exception and request /error
        catch (Exception e) {
            this.isApplicationStarted = false;
            this.failedMessage = "Severe Application Error Detected!";
            this.lastError = e;
            logger.error("Exception in root controller!", e);
            return "/error";
        }
    }

    /**
     * Perform register operations.
     *
     * @param formInfo the form data passed as an user
     * @return redirect to "/"
     */
    @PostMapping("/signup")
    public String registerForm(@ModelAttribute User formInfo) {
        this.isLoginAllowed = false;
        this.isFailedLoginAttempt = false;
        this.isLoggedIn = false;
        this.isRegisterAllowed = true;
        this.isFailedRegisterAttempt = true;
        this.isApplicationAllowed = false;
        this.isApplicationStarted = false;
        // If method called as GET
        if (formInfo.getName() == null) {
            this.isFailedRegisterAttempt = false;
            logger.warn("Request: redirect to registration form.");
        }
        // If method called as POST
        else {
            try {
                if (formInfo.getName().trim().equals("btd6gluser")) {
                    this.failedMessage = "You tried to copy the default anonymous account. Account already exists!";
                } else {
                    // New User constructed from form details
                    User newUser = new User(formInfo.getName().trim(), "regular", formInfo.getPassword().trim(),
                            formInfo.getEmail().trim(), null);
                    // Check if account already exists in database
                    if (this.ui.findByName(newUser.getName()) != null) {
                        this.failedMessage = "Account already exists!";
                        logger.warn("Register attempt: User [" + newUser.getName() + "] already exists in database.");
                    }
                    // Perform final check and save account
                    else {
                        this.ui.save(newUser);
                        this.btd6.replaceUser(this.ui.findByNameAndPassword(newUser.getName(), newUser.getPassword()));
                        this.isFailedRegisterAttempt = !(newUser.equals(this.btd6.getUser()));
                        this.btd6.replaceUser(null);
                        this.isRegisterAllowed = this.isFailedRegisterAttempt;
                        this.failedMessage = !this.isFailedRegisterAttempt ? "Success! You may login..."
                                : "Error creating account!";
                        logger.info("Register attempt: [" + newUser.getName() + "] {failed : "
                                + this.isFailedRegisterAttempt + "}");
                    }
                }
            } catch (Exception e) {
                this.failedMessage = "Registration failed. Application error detected, please try again!";
                this.lastError = e;
                logger.error("Register attempt failed due to exception.", e.getCause());
            }
        }
        return WebController.REDIRECT;
    }

    /**
     * Perform login operations.
     *
     * @param formInfo the form data passed as an user
     * @return redirect to "/"
     */
    @PostMapping("/login")
    public String loginForm(@ModelAttribute User formInfo) {
        this.isLoginAllowed = true;
        this.isLoggedIn = false;
        this.isFailedLoginAttempt = true;
        this.isRegisterAllowed = false;
        this.isFailedRegisterAttempt = false;
        this.isApplicationAllowed = false;
        this.isApplicationStarted = false;
        // If method called as GET
        logger.info("Login form info: " + formInfo.createString());
        if (formInfo.getName() == null) {
            this.isFailedLoginAttempt = false;
            logger.warn("Request: redirect to login form!");
        }
        // If method called as POST
        else {
            try {
                if (formInfo.getName().trim().equals("btd6gluser")) {
                    if (formInfo.getPassword().equals(User.getDefaultUser().getPassword())) {
                        this.btd6.replaceUser(User.getDefaultUser());
                        this.isLoginAllowed = false;
                        this.isFailedLoginAttempt = false;
                        this.isLoggedIn = true;
                        this.isApplicationAllowed = true;
                        this.failedMessage = "Info: Current login is anonymous. You won't be able to save your activity.";
                    }
                } else {
                    // New User constructed from form details
                    User newUser = new User(formInfo.getName().trim(), "regular", formInfo.getPassword(), null, null);
                    // Database query result for form details
                    User result = ui.findByNameAndPassword(newUser.getName(), newUser.getPassword());
                    // Check if account exists in database
                    if (result != null) {
                        newUser.setType(result.getType());
                        newUser.setEmail(result.getEmail());
                        newUser.setCreationDate(result.getCreationDate());
                        this.btd6.replaceUser(newUser);
                        this.isLoginAllowed = false;
                        this.isFailedLoginAttempt = false;
                        this.isLoggedIn = true;
                        this.isApplicationAllowed = true;
                        this.failedMessage = "Success! Welcome to your home page...";
                    } else {
                        this.failedMessage = "Login failed. Provided information doesn't exist.";
                    }
                }
                logger.info("Sign in attempt: [" + formInfo.getName().trim() + "] {status : "
                        + ((this.isLoggedIn && !this.isFailedLoginAttempt) ? "successful" : "failed") + "}");
            } catch (Exception e) {
                this.failedMessage = "Login failed. Application error detected, please try again!";
                this.lastError = e;
                logger.error("Login attempt failed due to exception.", e.getCause());
            }
        }
        return WebController.REDIRECT;
    }

    /**
     * Perform update user account operations.
     *
     * @param updatedUser the form data passed as an user
     * @return redirect to "/"
     */
    @PostMapping(value = "/updateUserInfo")
    public String updateUserInformation(@ModelAttribute User updatedUser) {
        try {
            // Updated user constructed from form details
            User updates = new User(updatedUser.getName().trim(), "regular-modified", updatedUser.getPassword(),
                    updatedUser.getEmail(), this.btd6.getUser().getCreationDate());
            if (!this.btd6.getUser().getName().equals(updates.getName())
                    || !this.btd6.getUser().getPassword().equals(updates.getPassword())
                    || !this.btd6.getUser().getEmail().equals(updates.getEmail())) {
                try {
                    // Delete existing information from database
                    this.ui.delete(this.ui.findByName(this.btd6.getUser().getName()));
                    this.ui.flush();
                    // Add updated information to database
                    this.ui.save(updates);
                    this.btd6.replaceUser(updates);
                    this.failedMessage = "Success! Account updated.";
                    logger.info("Update account attempt: User [" + this.btd6.getUser().getName() + "] to User ["
                            + updates.getName() + "] + {status : success}");
                } catch (Exception e) {
                    if (this.ui.findById(this.btd6.getUser().getId()) == null)
                        this.ui.save(this.btd6.getUser());
                    this.lastError = e;
                    this.failedMessage = "Update account information failed! No changes were made.";
                    logger.warn("Update account attempt: User [" + this.btd6.getUser().getName() + "] to User ["
                            + updates.getName() + "] {status : failed}. Rolling back changes...");
                }
            } else {
                this.failedMessage = "Info: No changes made to your account!";
            }
        } catch (Exception e) {
            this.lastError = e;
            this.failedMessage = "Update account failed. Application error detected, please try again!";
            logger.error("Update account attempt failed due to Exception. No changes will be made.", e.getCause());
        }
        return WebController.REDIRECT;
    }

    /**
     * Perform delete user account operations.
     * <p>
     * Receives no id or attribute value, instead deletes the currently logged in
     * user.
     *
     * @return redirect to "/"
     */
    @PostMapping(value = "/deleteUserAccount")
    public String deleteUserAccount() {
        try {
            if (this.isLoggedIn) {
                this.ui.delete(this.ui.findByName(this.btd6.getUser().getName()));
                this.ui.flush();
                this.isLoginAllowed = false;
                this.isFailedLoginAttempt = false;
                this.isLoggedIn = false;
                this.isRegisterAllowed = false;
                this.isFailedRegisterAttempt = false;
                this.isApplicationAllowed = false;
                this.isApplicationStarted = false;
                this.btd6.replaceUser(new User(null, "deleted", null));
                this.failedMessage = "Info: Your account has been deleted from database...";
            }
        } catch (Exception e) {
            if (this.isLoggedIn && this.ui.findByName(this.btd6.getUser().getName()) == null)
                this.ui.save(this.btd6.getUser());
            this.lastError = e;
            this.failedMessage = "Info: Couldn't delete your account... Please try again!";
        }
        return WebController.REDIRECT;
    }

    /**
     * Handle logout request.
     *
     * @return redirect to "/"
     */
    @PostMapping("/logout")
    public String logoutButton() {
        this.isLoginAllowed = false;
        this.isLoggedIn = false;
        this.isFailedLoginAttempt = false;
        this.isRegisterAllowed = false;
        this.isFailedRegisterAttempt = false;
        this.isApplicationAllowed = false;
        this.isApplicationStarted = false;
        this.failedMessage = "Success! You have been logged out...";
        this.btd6.resetContainer();
        return WebController.REDIRECT;
    }

    /**
     * Handle app access request.
     *
     * @param appSetup the form data passed as a map
     * @return redirect to "/"
     * @throws Exception if default maps could not be retrieved
     */
    @PostMapping("/logger")
    public String mainApplication(@ModelAttribute Map appSetup) throws Exception {
        if (!this.isLoginAllowed && this.isLoggedIn && !this.isFailedLoginAttempt && !this.isRegisterAllowed
                && !this.isFailedRegisterAttempt && this.isApplicationAllowed && !this.isApplicationStarted
                && appSetup.getName() != null && !appSetup.getName().equals("Map...")) {
            Map selectedMap = new Map(Map.getMapByName(appSetup.getName(), GameContainer.getDefaultMaps()));
            selectedMap.setDifficulty(appSetup.getDifficulty());
            selectedMap.setGameMode(appSetup.getGameMode());
            selectedMap.setCurrentCash(GameContainer.calculateStartingCash(selectedMap.getGameMode()));
            selectedMap.setCurrentLives(
                    GameContainer.calculateStartingLives(selectedMap.getDifficulty(), selectedMap.getGameMode()));
            this.lastAppSelection = selectedMap;
            this.btd6.setMap(new Map(selectedMap));
            this.isApplicationStarted = true;
            this.failedMessage = "Info: If you encounter any problems please open an issue by accessing the link at the bottom of the page!";
        } else {
            this.isApplicationStarted = false;
            this.failedMessage = "App page is not available at the moment. Please try again!";
        }
        return WebController.REDIRECT;
    }

    /**
     * Perform game data save operations.
     *
     * @param savedAppData the form data passed as a session container
     * @return redirect to "/"
     */
    @PostMapping(value = "/save")
    public String saveAppData(@ModelAttribute GameContainer savedAppData) {
        this.failedMessage = "Info: It is not yet possible to save your activity!";
        return WebController.REDIRECT;
    }

    /**
     * Handle go home request.
     *
     * @return redirect to "/"
     */
    @PostMapping(value = "/home")
    public String goHomeButton() {
        this.isApplicationStarted = false;
        return WebController.REDIRECT;
    }

    /**
     * Handle application errors.
     *
     * @param request   the Servlet HTTP Request information
     * @param errorInfo the list of attributes to be passed to the view engine
     * @return error view
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model errorInfo) {
        errorInfo.addAttribute("isLoginAllowed", this.isLoginAllowed);
        errorInfo.addAttribute("isFailedLoginAttempt", this.isFailedLoginAttempt);
        errorInfo.addAttribute("isLoggedIn", this.isLoggedIn);
        errorInfo.addAttribute("isRegisterAllowed", this.isRegisterAllowed);
        errorInfo.addAttribute("isFailedRegisterAttempt", this.isFailedRegisterAttempt);
        errorInfo.addAttribute("isApplicationAllowed", this.isApplicationAllowed);
        errorInfo.addAttribute("isApplicationStarted", this.isApplicationStarted);
        this.isApplicationStarted = false;
        errorInfo.addAttribute("failedMessage", "Latest alert message: [" + this.failedMessage + "]");
        this.failedMessage = null;
        errorInfo.addAttribute("currUser", (this.isLoggedIn) ? this.btd6.getUser() : null);
        // Get request info if exists
        try {
            Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
            Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
            errorInfo.addAttribute("statusCode", ((statusCode == null) ? "unknow" : statusCode));
            errorInfo.addAttribute("exceptionCause", ((exception == null) ? "unkown" : exception.getCause()));
            errorInfo.addAttribute("exceptionMessage", ((exception == null) ? "unknown" : exception.toString()));
        } catch (Exception e) {
            this.lastError = e;
            this.failedMessage = "Severe Application Error Detected!";
            logger.error("Exception in handleError().", e);
        }
        errorInfo.addAttribute("lastError", this.lastError.toString());
        errorInfo.addAttribute("shutdown", this.isShutdownStarted);
        errorInfo.addAttribute(WebController.PAGE_TITLE_ATTR, "Error");
        return "error";
    }

    /**
     * Handle shutdown request.
     *
     * @return redirect to "/"
     */
    @PostMapping("/shutdown")
    public String shutdownContext() {
        logger.info("Application shutdown initiated...");
        this.isShutdownStarted = !this.isLoggedIn;
        return WebController.REDIRECT;
    }

    /**
     * Truncates the provided table.
     *
     * @param table the name of the table to truncate
     */
    public void truncate(String table) {
        try {
            em.createNativeQuery("TRUNCATE TABLE " + table).executeUpdate();
            logger.warn("TRUNCATED table " + table + "!");
        } catch (Exception e) {
            this.isApplicationStarted = false;
            this.lastError = e;
            this.failedMessage = "SQL: Application Error Detected!";
            logger.error("TRUNCATE ERROR on table " + table + "!", e);
        }
    }

    /**
     * Runs at application start and initializes the database with the default,
     * anonymous user.
     * <p>
     * May be called again at any time by classes with access to it, though it is
     * mostly use to initiate database or to perform testing.
     * <p>
     * User created:
     * <ul>
     * <li>Username: btd6gluser;
     * <li>Password: pass;
     * <li>Type: anonymous;
     * <li>Email: null.
     * </ul>
     */
    @Override
    public void run(String... args) {
        try {
            this.truncate("users");
            this.ui.save(User.getDefaultUser());
            logger.info("Saved default anonymous user into database!");
            new GameContainer();
        } catch (Exception e) {
            this.isApplicationStarted = false;
            this.failedMessage = "CommandLineRunner: Application Error Detected!";
            this.lastError = e;
            logger.error("Run method error: ", e);
        }
    }

    /**
     * Something happens and the application context is magically passed here as
     * parameter.
     *
     * @param arg0 the application context attached to the running spring
     *             application
     * @throws BeansException if the application context cannot be resolved
     */
    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        this.context = arg0;
    }
}
