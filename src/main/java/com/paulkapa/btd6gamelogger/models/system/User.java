package com.paulkapa.btd6gamelogger.models.system;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.Gson;
import com.paulkapa.btd6gamelogger.models.BaseEntity;

/**
 * <b>Class that defines the properties of a real user</b>
 * <p>
 * May be used as database entity, defining {@code password}, {@code email},
 * and {@code creation_date} columns for a table named "users".
 * <p>
 * Inherits the columns {@code name} and {@code type} from {@link BaseEntity} class.
 *
 * @see BaseEntity
 */
@Entity(name = "User")
@Table(name = "users")
public class User extends BaseEntity {

    /**
     * The current password.
     */
    @Column(name = "password")
    private String password;

    /**
     * The current email.
     */
    @Column(name = "email")
    private String email;

    /**
     * The user creation date.
     */
    @Column(name = "creation_date")
    private Timestamp creationDate;

    /**
     * The current account age.
     */
    @Transient
    private long accountAge;

    /**
     * Default constructor.
     */
    public User() {
        super();
        this.password = null;
        this.email = null;
        this.creationDate = null;
        this.accountAge = 0l;
    }

    /**
     * Prefferd constructor.
     * @param name the user name
     * @param type the user type
     * @param password the user's password
     */
    public User(String name, String type, String password) {
        super(name, type);
        this.setPassword(password);
        this.email = null;
        this.creationDate = null;
        this.accountAge = 0l;
    }

    /**
     * Complete constructor.
     * @param name the user name
     * @param type the user type
     * @param password the user's password
     * @param email the user's email
     * @param creationDate the user creation date
     */
    public User(String name, String type, String password, String email, Timestamp creationDate) {
        super(name, type);
        this.setPassword(password);
        this.email = email;
        this.setCreationDate(creationDate);
        this.setAccountAge();
    }

    /**
     * Copy constructor.
     * @param other the user to copy data from
     */
    public User(User other) {
        super(other.getInstance());
        this.password = other.getPassword();
        this.email = other.getEmail();
        this.setCreationDate(other.getCreationDate());
        this.accountAge = other.getAccountAge();
    }

    /**
     * Return a new user initialized with the anonymous details accepted by the application.
     * @return a new default anonymous user
     */
    public static User getDefaultUser() {return new User("btd6gluser", "anonymous", "pass", null, null);}

    /**
     * Gets the current password.
     * @return a string representing the current password
     */
    public String getPassword() {return this.password;}

    /**
     * Sets the current password.
     * @param password the current password string
     */
    public void setPassword(String password) {
        try {this.password = User.encryptPassword(password);
        } catch (NoSuchAlgorithmException e) {e.printStackTrace();}
    }

    /**
     * Encrypts a password string provided as parameter.
     * @param password the password to be encrypted provided as a string
     * @return a string containing the encrypted password
     * @throws NoSuchAlgorithmException if the specified hashing algorithm is not recognized
     */
    private static String encryptPassword(String password) throws NoSuchAlgorithmException {
        var md = MessageDigest.getInstance("SHA-256");
        var sb = new StringBuilder();
        byte[] saltBytes = User.visualizeAccountAge(123456789).getBytes();
        md.update(saltBytes);
        byte[] passBytes = password.trim().getBytes();
        md.update(passBytes);
        byte[] bytes = md.digest();
        for(byte b : bytes) sb.append(b);
        return sb.toString().substring(2, sb.length() - 2);
    }

    /**
     * Gets the current email.
     * @return a stringrepresenting the current email
     */
    public String getEmail() {return this.email;}

    /**
     * Sets the current email.
     * @param email the current email
     */
    public void setEmail(String email) {this.email = email;}

    /**
     * Gets the creation date.
     * @return a timestamp representing the creation date
     */
    public Timestamp getCreationDate() {return this.creationDate;}

    /**
     * Sets the creation date.
     * @param creationDate the creation date
     */
    public void setCreationDate(Timestamp creationDate) {this.creationDate = creationDate == null ? new Timestamp(System.currentTimeMillis()) : creationDate;}

    /**
     * Gets the current account age.
     * @return a long value representing the current account age
     */
    public long getAccountAge() {return this.accountAge;}

    /**
     * Sets the current account age.
     * <p>
     * Account age is calculated using the formula 'current date' - 'creation date'.
     */
    public long setAccountAge() {
        this.accountAge = this.creationDate == null ? 0l : System.currentTimeMillis() - this.creationDate.getTime();
        return this.accountAge;
    }

    /**
     * Transforms the account age provided as parameter
     * into a readable time string, and returns it.
     * @param accountAge current account age in milliseconds
     * @return a string containing the converted time
     */
    public static String visualizeAccountAge(long accountAge) {
        var sb = new StringBuilder();
        long difference = accountAge;
        String y = null, d = null, h = "00 h ", m = ": 00 m ", s = ": 00 s", ms = ". 000 ms";
        while(difference != 0) {
            if(difference < 1000) {
                ms = String.format(". %03dms", difference);
                difference = 0;
            } else if(difference < 60000) {
                s = String.format(": %02ds ", difference/1000);
                difference = difference%1000;
            } else if(difference < 3600000l) {
                m = String.format(": %02dm ", difference/60000);
                difference = difference%60000;
            } else if(difference < 86400000l) {
                h = String.format("%02dh ", difference/3600000);
                difference = difference%3600000;
            } else if(difference < 31536000000l) {
                d = String.format("%d day(s)", difference/86400000l);
                difference = difference%86400000l;
            } else {
                y = String.format("%d year(s), ", difference/31536000000l);
                difference = difference%31536000000l;
            }
        }
        if(d != null) {
            if(y != null) sb.append(y).append(d);
            else sb.append(d);
        } else sb.append(h).append(m).append(s).append(ms);
        return sb.toString();
    }

    @Override
    public String createString() {
        var sb = new StringBuilder();
        sb.delete(0, sb.length());
        sb.append(super.createString());
        sb.append(", password=").append(this.password);
        sb.append(", email=").append(this.email);
        sb.append(", creation_date=").append(this.creationDate);
        sb.append(", account_age=").append(User.visualizeAccountAge(this.accountAge));
        return sb.toString();
    }

    @Override
    public String toString() {return new Gson().toJson(this);}
}
