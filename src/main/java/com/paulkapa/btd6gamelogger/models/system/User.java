package com.paulkapa.btd6gamelogger.models.system;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.paulkapa.btd6gamelogger.models.base.BaseEntity;

/**
 * Class that defines the attributes of a real user of this application.
 * @see BaseEntity
 */
@Entity(name = "User")
@Table(name = "users")
public class User extends BaseEntity {

    @Transient
    private static ArrayList<User> inUseUsers = null;
    @Transient
    private static User defaultUser = null;

    @Transient
    private StringBuffer sb = new StringBuffer();

    /**
     * The <code>password</code> value associated with this <code>User</code>
     * entity.
     */
    @Column(name = "password")
    private String password;

    /**
     * The <code>email</code> value associated with this <code>User</code>
     * entity.
     */
    @Column(name = "email")
    private String email;

    /**
     * The date and time when this <code>User</code> was first created.
     */
    @Column(name = "creation_date")
    private Timestamp creationDate;

    /**
     * Used to store the result of calculating the account age.
     */
    @Transient
    private long accountAge;

    /**
     * Default constructor.
     */
    public User() {
        super(null, null);
        this.password = null;
        this.email = null;
        this.creationDate = null;
    }

    /**
     * Complete constructor.
     * Prefferd constructor.
     * @param name
     * @param type
     * @param password
     */
    public User(String name, String type, String password, String email, Timestamp creationDate) {
        super(name, type);
        this.password = password;
        this.email = email;
        this.creationDate = creationDate;
    }

    public static ArrayList<User> getInUseUsers() {
        return inUseUsers;
    }

    public static void setInUseUsers(ArrayList<User> inUseUsers) {
        User.inUseUsers = inUseUsers;
    }

    public static void useUser(User user) {
        
    }

    public static User getDefaultUser() {
        try {
            if(User.defaultUser == null) {
                if(!User.initDefaultUser()) {
                    throw new Exception("Could not reset default user!");
                }
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        return User.defaultUser;
    }

    public static void setDefaultUser(User user) {
        try {
            if(user != null) {
                User.defaultUser = user;
            } else {
                if(!User.initDefaultUser()) {
                    throw new Exception("Could not reset default user!");
                }
            }
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    private static boolean initDefaultUser() {
        try {
            User.defaultUser = new User("btd6gluser", "anonymous", User.encryptPassword("pass"), null, null);
            return true;
        } catch(Exception e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * Gets the <code>password</code> attribute of this object.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the <code>password</code> attribute for this object.
     * <p>
     * Applies trim() on the string before it is stored.
     */
    public void setPassword(String password) {
        this.password = (password == null) ? null : password.trim();
    }

    /**
     * Encrypts a password provided as parameter.
     * @param password the password to encrypt
     * @return encrypted password
     */
    public static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            StringBuffer sb = new StringBuffer();
            byte[] saltBytes = "this is a password".getBytes();
            md.update(saltBytes);
            byte[] passBytes = (password == null) ? "password".getBytes() : password.trim().getBytes();
            md.update(passBytes);
            byte[] bytes = md.digest();
            for(byte b : bytes) {
                sb.append(b);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return password;
        }
    }

    /**
     * Gets the <code>email</code> attribute of this object.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the <code>email</code> attribute for this object.
     * <p>
     * Applies trim() on the string before it is stored.
     */
    public void setEmail(String email) {
        this.email = (email == null) ? null : email.trim();
    }

    /**
     * Gets the <code>creationDate</code> attribute of this object.
     */
    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    /**
     * Sets the <code>creationDate</code> attribute for this object.
     * @param creationDate the timestamp of when the account was created.
     * If <code>null</code>, the current time is used.
     */
    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = (creationDate == null) ? new Timestamp(System.currentTimeMillis()) : creationDate;
    }

    /**
     * Gets the <code>User</code>'s account age.
     * @return current account age in milliseconds
     */
    public long getAccountAge() {
        return this.accountAge;
    }

    /**
     * Sets and returns this <code>User</code>'s account age.
     * @param accountAge current account age in milliseconds
     * @return the account age in milliseconds
     */
    public long setAccountAge(long accountAge) {
        this.accountAge = accountAge;
        return this.accountAge;
    }

    /**
     * Transforms the milliseconds provided in <code>accountAge</code>
     * to a readable time string, and returns it.
     * @param accountAge current account age in milliseconds
     * @return a <code>String</code> containing the converted time
     */
    public static String visualizeAccountAge(long accountAge) {
        if(accountAge != 0l) {
            long difference = accountAge;
            StringBuffer lsb = new StringBuffer();
            String y = null, d = null, h = "00 h ", m = ": 00 m ", s = ": 00 s", ms = ". 000 ms";
            while(difference != 0) {
                if(difference < 1000) {
                    // return milliseconds
                    ms = String.format(". %03dms", difference);
                    difference = 0;
                } else if(difference < 60000) {
                        // return seconds
                        s = String.format(": %02ds ", difference/1000);
                        difference = difference%1000;
                } else if(difference < 3600000l) {
                    // return minutes
                    m = String.format(": %02dm ", difference/60000);
                    difference = difference%60000;
                } else if(difference < 86400000l) {
                    // return hours
                    h = String.format("%02dh ", difference/3600000);
                    difference = difference%3600000;
                } else if(difference < 31536000000l) {
                    // return days
                    d = String.format("%d day(s)", difference/86400000l);
                    difference = difference%86400000l;
                } else {
                    // return years
                    y = String.format("%d year(s), ", difference/31536000000l);
                    difference = difference%31536000000l;
                }
            }
            if(d != null) {
                if(y != null) {
                    lsb.append(y).append(d);
                    return lsb.toString();
                } else {
                    lsb.append(d);
                    return lsb.toString();
                }
            } else {
                lsb.append(h).append(m).append(s).append(ms);
                return lsb.toString();
            }
        } else {
            return null;
        }
    }

    @Override
    public String createString() {
        this.sb.delete(0, this.sb.length());
        this.sb.append(super.createString());
        this.sb.append(", password=").append(this.password);
        this.sb.append(", email=").append(this.email);
        this.sb.append(", creation_date=").append(this.creationDate);
        this.sb.append(", account_age=").append(User.visualizeAccountAge(this.accountAge));
        return this.sb.toString();
    }

    /**
     * @see #createString()
     */
    @Override
    public String toString() {
        return "{ " + this.getName() + "=[" + this.createString() + "]}";

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (int) (accountAge ^ (accountAge >>> 32));
        result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (accountAge != other.accountAge)
            return false;
        if (creationDate == null) {
            if (other.creationDate != null)
                return false;
        } else if (!creationDate.equals(other.creationDate))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        return true;
    }
}
