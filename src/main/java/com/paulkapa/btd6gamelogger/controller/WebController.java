package com.paulkapa.btd6gamelogger.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.paulkapa.btd6gamelogger.Btd6GameLoggerApplication;
import com.paulkapa.btd6gamelogger.database.game.GameContainer;
import com.paulkapa.btd6gamelogger.database.system.UserInterface;
import com.paulkapa.btd6gamelogger.models.game.Round;
import com.paulkapa.btd6gamelogger.models.system.User;
import com.paulkapa.secret1.util.console.logging.CustomLoggerProvider;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javassist.NotFoundException;

/**
 * <b>Web Servlet controller</b>
 * <p>
 * Provides Web Requests handling for URL's mapped by this application.
 * <p>
 * Handles all user session data and other related information.
 *
 * @see org.springframework.boot.web.servlet.error.ErrorController
 * @see org.springframework.boot.CommandLineRunner
 * @see org.springframework.context.ApplicationContextAware
 */
@Component
@Controller
@Transactional(readOnly = false)
public class WebController implements CommandLineRunner, ApplicationContextAware {

    private static final Logger logger = CustomLoggerProvider.getLogger(Btd6GameLoggerApplication.logger.getName());

    /**
     * The ApplicationContext attached to the running Spring Application.
     */
    private ApplicationContext context;

    public static final String REDIRECT = "redirect:/";
    public static final String BACKUP = "backup";
    public static final String ERROR = "error";
    public static final String INDEX = "index";
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";
    public static final String HOME = "home";
    public static final String APP = "app";
    static final String PERSIST = "_persistent";
    public static final String USER_NAME_ATTR = "uname";
    public static final String EMAIL_ATTR = "uemail";
    public static final String ACC_AGE_ATTR = "uaccountage";
    public static final String PAGE_TITLE_ATTR = "currentPageTitle";
    private final Map<String, ModelAndViewImpl> modelAndViews = new LinkedHashMap<>();

    private boolean isLoginAllowed;
    private boolean isFailedLoginAttempt;
    private boolean isLoggedIn;
    private boolean isRegisterAllowed;
    private boolean isFailedRegisterAttempt;
    private boolean isApplicationAllowed;
    private boolean isApplicationStarted;
    private String failedMessage;
    private boolean isShutdownStarted;

    /**
     * Session container.
     */
    private GameContainer btd6;

    /**
     * Selected map for the current session.
     */
    private com.paulkapa.btd6gamelogger.models.game.Map lastAppSelection;

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
        this.isLoginAllowed = true;
        this.isFailedLoginAttempt = false;
        this.isLoggedIn = false;
        this.isRegisterAllowed = false;
        this.isFailedRegisterAttempt = false;
        this.isApplicationAllowed = false;
        this.isApplicationStarted = false;
        this.failedMessage = null;
        this.isShutdownStarted = false;
        this.btd6 = new GameContainer();
        this.lastAppSelection = null;
    }

    /**
     * Root application controller.
     *
     * @return the ModelAndView object
     * @throws IOException if game data cannot be read from storage
     */
    @GetMapping("/")
    public ModelAndViewImpl rootController() throws IOException {
        /**
         * Save attributes available globally.
         */
        Map<String, Object> globalData = new LinkedHashMap<>(0);
        // Shutdown
        globalData.put("shutdown", this.isShutdownStarted);
        // Login
        globalData.put("isLoginAllowed", this.isLoginAllowed);
        globalData.put("isLoggedIn", this.isLoggedIn);
        globalData.put("isFailedLoginAttempt", this.isFailedLoginAttempt);
        // Register
        globalData.put("isRegisterAllowed", this.isRegisterAllowed);
        globalData.put("isFailedRegisterAttempt", this.isFailedRegisterAttempt);
        // App
        globalData.put("isApplicationAllowed", this.isApplicationAllowed);
        globalData.put("isApplicationStarted", this.isApplicationStarted);
        globalData.put("failedMessage", this.failedMessage);
        // User
        globalData.put(USER_NAME_ATTR, ((this.isLoggedIn) ? this.btd6.getUser().getName() : null));
        globalData.put(EMAIL_ATTR, ((this.isLoggedIn) ? this.btd6.getUser().getName() : null));
        globalData.put(ACC_AGE_ATTR,
                ((this.isLoggedIn) ? User.visualizeAccountAge(this.btd6.getUser().setAccountAge()) : null));
        globalData.put("newUser", ((this.isLoggedIn) ? this.btd6.getUser() : User.getDefaultUser()));
        this.failedMessage = null;

        /**
         * Perform view selection operations.
         */
        if (this.isShutdownStarted) {
            /**
             * Backup page sent to the browser to start the application shutdown...
             */
            getViewData(BACKUP).addData(globalData);
            getViewData(BACKUP).addData("context", (ConfigurableApplicationContext) context);

            logger.log(Level.WARNING, "Sent Shutdown request to main application!");

            return getViewData(BACKUP);
        } else {
            if (this.isLoggedIn && !this.isLoginAllowed && !this.isRegisterAllowed && this.isApplicationAllowed
                    && this.isApplicationStarted) {
                /**
                 * --------- App page. ---------
                 */
                Map<String, Object> data = new LinkedHashMap<>(0);
                // Game
                data.put("maps", this.btd6.getMap());
                data.put("rounds", this.btd6.getRounds());
                data.put("towers", this.btd6.getTowers());
                data.put("upgrades", this.btd6.getUpgrades());
                data.put("diff", this.btd6.getDiff());
                data.put("mode", this.btd6.getMode());
                data.put("appSetup", this.lastAppSelection);
                data.put("appData", this.btd6);

                getViewData(APP).addData(globalData);
                getViewData(APP).addData(data);
                getViewData(APP).addData(PAGE_TITLE_ATTR, "App");
                getViewData(APP).changeViewName(INDEX);

                logger.log(Level.INFO, "Application accessed by {}!", getViewData(APP).getModel().get(USER_NAME_ATTR));

                return getViewData(APP);
            } else if (this.isLoggedIn && !this.isLoginAllowed && !this.isRegisterAllowed
                    && this.isApplicationAllowed) {
                /*
                 * ---------- Home page. ----------
                 */
                Map<String, Object> data = new LinkedHashMap<>(0);
                // Game
                data.put("maps", GameContainer.getDefaultMaps());
                data.put("diffs", GameContainer.DIFFICULTIES);
                data.put("modes", GameContainer.GAME_MODES);
                data.put("appSetup", new com.paulkapa.btd6gamelogger.models.game.Map());

                getViewData(HOME).addData(globalData);
                getViewData(HOME).addData(data);
                getViewData(HOME).addData(PAGE_TITLE_ATTR, "Home");
                getViewData(HOME).changeViewName(INDEX);

                logger.log(Level.INFO, "Sign up process complete for: {}. Welcome!",
                        getViewData(HOME).getModel().get(USER_NAME_ATTR));

                return getViewData(HOME);
            } else if (!this.isLoginAllowed && !this.isLoggedIn && this.isRegisterAllowed && !this.isApplicationAllowed
                    && !this.isApplicationStarted) {
                /*
                 * -------------- Register page. --------------
                 */
                getViewData(REGISTER).addData(globalData);
                getViewData(REGISTER).addData(PAGE_TITLE_ATTR, "Register");
                getViewData(REGISTER).changeViewName(INDEX);

                logger.log(Level.WARNING, "Registration status: [failed : {}]. Returning to registration form.",
                        this.isFailedRegisterAttempt);

                return getViewData(REGISTER);
            } else if (this.isLoginAllowed && !this.isLoggedIn && !this.isRegisterAllowed && !this.isApplicationAllowed
                    && !this.isApplicationStarted) {
                /*
                 * ----------- Login page. -----------
                 */
                getViewData(LOGIN).addData(globalData);
                getViewData(LOGIN).addData(PAGE_TITLE_ATTR, "Login");
                getViewData(LOGIN).changeViewName(INDEX);

                logger.log(Level.WARNING, "Login status: [failed : {}]. Returning to login form.",
                        this.isFailedLoginAttempt);

                return getViewData(LOGIN);
            }
            // Handle any other cases
            else {
                getViewData(ERROR).addData(globalData);
                getViewData(ERROR).addData(PAGE_TITLE_ATTR, "Error");

                logger.log(Level.SEVERE,
                        "Error in rootController() : No view could be selected! Error page returned...");

                return getViewData(ERROR);
            }
        }
    }

    /**
     * Perform register operations.
     *
     * @param formInfo the form data passed as an user
     * @return redirect to "/"
     */
    @PostMapping("/signup")
    public String registerForm(@ModelAttribute UserInfo formInfo) {
        this.isLoginAllowed = false;
        this.isLoggedIn = false;
        this.isRegisterAllowed = true;
        this.isApplicationAllowed = false;
        this.isApplicationStarted = false;

        logger.log(Level.INFO, "Register form info: {}", formInfo != null ? formInfo.toString() : "null");
        // If form data is empty or incomplete, perform no operations.
        if (formInfo != null && formInfo.getName() == null) {
            logger.log(Level.WARNING, "Request: redirect to registration form.");
        } else if (formInfo != null) {
            // New User constructed from form details
            var newUser = new User(formInfo.getName().trim(), "regular-new", formInfo.getPassword().trim(),
                    formInfo.getEmail().trim(), new Timestamp(System.currentTimeMillis()));

            // Check if account already exists in database
            if (this.ui.findByName(newUser.getName()) != null) {
                this.failedMessage = "Account already exists!";
                logger.log(Level.WARNING, "Register attempt: User {} already exists in database.", newUser.getName());
            } else {
                this.ui.save(newUser);
                if (this.ui.findByName(newUser.getName()) != null) {
                    this.isFailedRegisterAttempt = false;
                    this.isRegisterAllowed = false;
                    this.isLoginAllowed = true;
                }
                this.failedMessage = !this.isFailedRegisterAttempt ? "Success! You may login..."
                        : "Error creating account!";

                var attrs = new Object[] { newUser.getName(), this.isFailedLoginAttempt };
                logger.log(Level.INFO, "Register attempt: [{}] [failed : {}]", attrs);
            }
        }
        return REDIRECT;
    }

    /**
     * Perform login operations.
     *
     * @param formInfo the form data passed as an user
     * @return redirect to "/"
     */
    @PostMapping("/login")
    public String loginForm(@ModelAttribute UserInfo formInfo) {
        this.isLoggedIn = false;
        this.isLoginAllowed = true;
        this.isRegisterAllowed = false;
        this.isApplicationAllowed = false;
        this.isApplicationStarted = false;

        logger.log(Level.INFO, "Login form info: {}", formInfo != null ? formInfo.toString() : "null");
        // If form data is empty or incomplete, perform no operations.
        if (formInfo != null && formInfo.getName() == null) {
            logger.log(Level.WARNING, "Request: redirect to login form!");
        } else if (formInfo != null) {
            // New User constructed from form details
            var newUser = new User(formInfo.getName().trim(), "regular", formInfo.getPassword(), null, null);
            // Database query result for form details
            var result = ui.findByNameAndPassword(newUser.getName(), newUser.getPassword());
            // Check if account exists in database
            if (result != null) {
                newUser.setType(result.getType());
                newUser.setEmail(result.getEmail());
                newUser.setCreationDate(result.getCreationDate());
                this.btd6.replaceUser(newUser);
                this.isLoginAllowed = false;
                this.isLoggedIn = true;
                this.isApplicationAllowed = true;
                this.failedMessage = "Success! Welcome to your home page...";
            } else {
                this.failedMessage = String.format("Login failed. Cannot find user %s.", newUser.getName());
            }

            var attrs = new String[] { formInfo.getName().trim() != null ? formInfo.getName().trim() : "null",
                    ((this.isLoggedIn) ? "successful" : "failed") };
            logger.log(Level.INFO, "Sign in attempt: {} - [status : {}]", attrs);
        }
        return REDIRECT;
    }

    /**
     * Perform update user account operations.
     *
     * @param updatedUser the form data passed as an user
     * @return redirect to "/"
     */
    @PostMapping(value = "/updateUserInfo")
    public String updateUserInformation(@ModelAttribute UserInfo updatedUser) {
        // Updated user constructed from form details
        var updates = new User(updatedUser.getName().trim(), "regular-modified", updatedUser.getPassword(),
                updatedUser.getEmail(), this.btd6.getUser().getCreationDate());

        if (!this.btd6.getUser().getName().equalsIgnoreCase(updates.getName())
                || !this.btd6.getUser().getPassword().equals(updates.getPassword())
                || !this.btd6.getUser().getEmail().equalsIgnoreCase(updates.getEmail())) {
            try {
                // Delete existing information from database
                this.ui.delete(this.ui.findByName(this.btd6.getUser().getName()));
                this.ui.flush();
                // Add updated information to database
                this.ui.save(updates);
                this.btd6.replaceUser(updates);
                this.failedMessage = "Success! Account updated.";

                var attrs = new String[] { this.btd6.getUser().getName(), updates.getName() };
                logger.log(Level.INFO, "Update account attempt: User [{}] to User [{}] [status : success]", attrs);
            } catch (Exception e) {
                this.ui.save(this.btd6.getUser());
                this.failedMessage = "Update account information failed! No changes were made.";

                var attrs = new String[] { this.btd6.getUser().getName(), updates.getName() };
                logger.log(Level.WARNING,
                        "Update account attempt: User [{}] to User [{}] [status : failed]. Rolling back changes...",
                        attrs);
            }
        } else {
            this.failedMessage = "Info: No changes made to your account!";
        }
        return REDIRECT;
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
                this.isLoggedIn = false;
                this.isApplicationAllowed = false;
                this.isApplicationStarted = false;
                this.btd6.replaceUser(new User(null, "deleted", null));
                this.failedMessage = "Info: Your account has been deleted from the database...";
            }
        } catch (Exception e) {
            if (this.isLoggedIn && this.ui.findByName(this.btd6.getUser().getName()) == null)
                this.ui.save(this.btd6.getUser());
            this.failedMessage = "Info: Couldn't delete your account... Please try again!";
        }
        return REDIRECT;
    }

    /**
     * Handle logout request.
     *
     * @return redirect to "/"
     */
    @PostMapping("/logout")
    public String logoutButton() {
        this.isLoggedIn = false;
        this.isLoginAllowed = true;
        this.isRegisterAllowed = false;
        this.isApplicationAllowed = false;
        this.isApplicationStarted = false;
        this.failedMessage = "Success! You have been logged out...";
        this.btd6.resetContainer();
        return REDIRECT;
    }

    /**
     * Handle app access request.
     *
     * @param appSetup the form data passed as a map
     * @return redirect to "/"
     * @throws Exception if default maps could not be retrieved
     */
    @PostMapping("/logger")
    public String mainApplication(@ModelAttribute com.paulkapa.btd6gamelogger.models.game.Map appSetup)
            throws Exception {
        if (!this.isLoginAllowed && this.isLoggedIn && this.isApplicationAllowed && !this.isApplicationStarted
                && appSetup.getName() != null && !appSetup.getName().equals("Map...")) {
            var selectedMap = new com.paulkapa.btd6gamelogger.models.game.Map(
                    com.paulkapa.btd6gamelogger.models.game.Map.getMapByName(appSetup.getName(),
                            GameContainer.getDefaultMaps()));
            selectedMap.setDifficulty(appSetup.getDifficulty());
            selectedMap.setGameMode(appSetup.getGameMode());
            selectedMap.setCurrentCash(GameContainer.calculateStartingCash(selectedMap.getGameMode()));
            selectedMap.setCurrentLives(
                    GameContainer.calculateStartingLives(selectedMap.getDifficulty(), selectedMap.getGameMode()));
            this.lastAppSelection = selectedMap;
            this.btd6.setMap(new com.paulkapa.btd6gamelogger.models.game.Map(selectedMap));
            this.btd6.setRounds(Round.getDefaultRounds(1, 40));
            this.isApplicationStarted = true;
            this.failedMessage = "Info: If you encounter any problems please open an issue by accessing the link at the bottom of the page!";
        } else {
            this.isApplicationStarted = false;
            this.failedMessage = "App page is not available at the moment. Please try again!";
        }
        return REDIRECT;
    }

    /**
     * Perform game data save operations.
     *
     * @param savedAppData the form data passed as a session container
     * @return redirect to "/"
     * @throws Exception
     */
    @PostMapping(value = "/save")
    public String saveAppData(@ModelAttribute GameContainer savedAppData) throws NotFoundException {
        this.failedMessage = "Info: It is not yet possible to save your activity!";
        throw new NotFoundException("Method not allowed!");
    }

    /**
     * Handle go home request.
     *
     * @return redirect to "/"
     */
    @PostMapping(value = "/home")
    public String goHomeButton() {
        this.isApplicationStarted = false;
        return REDIRECT;
    }

    /**
     * Handle application errors.
     *
     * @return error view
     */
    @GetMapping("/error")
    @ExceptionHandler
    public ModelAndViewImpl handleError(Exception e) {
        getViewData(ERROR).addData("lastError", e);
        getViewData(ERROR).addData(PAGE_TITLE_ATTR, "Error");
        return getViewData(ERROR);
    }

    /**
     * Handle shutdown request.
     *
     * @return redirect to "/"
     */
    @PostMapping("/shutdown")
    public String shutdownContext() {
        this.isShutdownStarted = !this.isLoggedIn;

        logger.log(Level.INFO, "Application shutdown initiated...");

        return REDIRECT;
    }

    /**
     * Truncates the provided table.
     *
     * @param table the name of the table to truncate
     */
    public void truncate(String table) {
        try {
            em.createNativeQuery("TRUNCATE TABLE " + table).executeUpdate();
            logger.log(Level.WARNING, "TRUNCATED table {}!", table);
        } catch (Exception e) {
            this.isApplicationStarted = false;
            this.failedMessage = "SQL: Application Error Detected!";
            var attrs = new Object[] { table, e };
            logger.log(Level.SEVERE, "TRUNCATE ERROR on table {}!", attrs);
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
            logger.log(Level.INFO, "Saved default anonymous user into database!");
        } catch (Exception e) {
            this.isApplicationStarted = false;
            this.failedMessage = "CommandLineRunner: Application Error Detected!";
            logger.log(Level.SEVERE, "Run method error! Default user might not be initialized...", e);
        }
    }

    /**
     * Return the appropriate ModelAndView object with the specified view name.
     *
     * @param viewName the view name
     * @return a ModelAndViewImpl object
     */
    private ModelAndViewImpl getViewData(String viewName) {
        if (!modelAndViews.containsKey(viewName)) {
            modelAndViews.put(viewName, new ModelAndViewImpl(viewName));
            return modelAndViews.get(viewName);
        } else
            return modelAndViews.get(viewName);
    }

    /**
     * Something happens and the application context is magically passed here as
     * parameter. Spring Beans and Autowired comes in play.
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

/**
 * Class extending ModelAndView. Provides easier handling of view Data and
 * allows persistent data.
 */
class ModelAndViewImpl extends ModelAndView {

    private static final Logger logger = CustomLoggerProvider.getLogger(ModelAndViewImpl.class.getName());
    private String viewName;

    public ModelAndViewImpl(String viewName) {
        super(viewName);
        this.viewName = viewName;
        logger.info("New ModelAndViewImpl object created!");
    }

    void changeViewName(String viewName) {
        this.viewName = viewName;
        super.setViewName(viewName);
    }

    String getLocalViewName() {
        return this.viewName;
    }

    void addData(String attrName, Object obj) {
        super.addObject(attrName, obj);
    }

    void addData(Map<String, Object> data) {
        super.getModel().putAll(data);
    }

    void replaceData(Map<String, Object> data) {
        List<String> nonPersistentAttrNames = new ArrayList<>(0);
        super.getModel().forEach((s, o) -> {
            if (!s.contains(WebController.PERSIST))
                nonPersistentAttrNames.add(s);
        });
        nonPersistentAttrNames.forEach(s -> super.getModel().remove(s));
        super.getModel().putAll(data);
    }

    void addPersistentData(String attrName, Object obj) {
        super.addObject(attrName.concat(WebController.PERSIST), obj);
    }

    void addPersistentData(Map<String, Object> persistentData) {
        Map<String, Object> data = new HashMap<>(0);
        persistentData.forEach((s, o) -> data.put(s.contains("_peristent") ? s : s.concat(WebController.PERSIST), o));
        super.getModel().putAll(data);
    }

    void replacePersistentData(Map<String, Object> persistentData) {
        Map<String, Object> data = new HashMap<>(0);
        persistentData.forEach((s, o) -> data.put(s.contains("_peristent") ? s : s.concat(WebController.PERSIST), o));
        List<String> persistentAttrNames = new ArrayList<>(0);
        super.getModel().forEach((s, o) -> {
            if (s.contains(WebController.PERSIST))
                persistentAttrNames.add(s);
        });
        persistentAttrNames.forEach(s -> super.getModel().remove(s));
        super.getModel().putAll(data);
    }

    void clearAllData() {
        super.clear();
        super.setViewName(this.viewName);
    }

    void clearNonPersistentData(String attrName) {
        super.getModel().remove(attrName);
    }

    void clearNonPersistentData() {
        List<String> nonPersistentAttrNames = new ArrayList<>(0);
        super.getModel().forEach((s, o) -> {
            if (!s.contains(WebController.PERSIST))
                nonPersistentAttrNames.add(s);
        });
        nonPersistentAttrNames.forEach(s -> super.getModel().remove(s));
    }

    void clearPersistentData(String attrName) {
        super.getModel().remove(attrName.concat(WebController.PERSIST));
    }

    void clearPersistentData() {
        List<String> persistentAttrNames = new ArrayList<>(0);
        super.getModel().forEach((s, o) -> {
            if (s.contains(WebController.PERSIST))
                persistentAttrNames.add(s);
        });
        persistentAttrNames.forEach(s -> super.getModel().remove(s));
    }

}

/**
 * Class used for temporary storage of form data. Implements toString() for easy
 * logging of object attributes.
 */
class UserInfo {
    private String name;
    private String type;
    private String password;
    private String email;

    public UserInfo(String name, String type, String password, String email) {
        this.name = name;
        this.type = type;
        this.password = password;
        this.email = email;
    }

    /**
     * @return the name
     */
    String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    void setType(String type) {
        this.type = type;
    }

    /**
     * @return the password
     */
    String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    void setEmail(String email) {
        this.email = email;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append("UserInfo [email=");
        builder.append(email);
        builder.append(", name=");
        builder.append(name);
        builder.append(", password=");
        builder.append(password);
        builder.append(", type=");
        builder.append(type);
        builder.append("]");
        return builder.toString();
    }
}
