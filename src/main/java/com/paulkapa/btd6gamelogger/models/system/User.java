package com.paulkapa.btd6gamelogger.models.system;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.paulkapa.btd6gamelogger.models.base.BaseEntity;

/**
 * Class that defines the attributes of a real user of this application.
 * <p>
 * Extends <code>BaseEntity</code> class which provides the base attributes
 * and methods for handling user data.
 *
 * @see BaseEntity
 */
@Entity(name = "User")
@Table(name = "users")
public class User extends BaseEntity {

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
    @Column(name = "account_creation_date")
    private Timestamp creationDate;

    /**
     * Used to store the result of calculating the account age.
     */
    @Transient
    private long accountAge;

    /**
     * Default constructor.
     * <p>
     * Calls the super class constructor {@link BaseEntity#BaseEntity(String, String)}
     * providing as parameters "<code>User</code>" and <code>null</code>.
     * <p>
     * Sets the rest of the user attributes to null.
     */
    public User() {
        super("User", null);
        this.password = null;
        this.email = null;
        this.creationDate = null;
    }

    /**
     * Constructor that initializes a <code>User</code> object with
     * the provided <code>name</code> parameter.
     * <p>
     * Applies the same principles as the default constructor
     * {@link User#User()}
     */
    public User(String name) {
        super("User", name);
        this.password = null;
        this.email = null;
        this.creationDate = null;
    }

    /**
     * Constructor that initializes a <code>User</code> object with
     * the provided <code>name</code> and <code>password</code> parameters.
     * <p>
     * Applies the same principles as the default constructor
     * {@link User#User()}
     */
    public User(String name, String password) {
        super("User", name);
        this.password = password;
        this.email = null;
        this.creationDate = null;
    }

    /**
     * Constructor that initializes a <code>User</code> object with
     * the provided <code>name</code>, <code>password</code> and
     * <code>email</code> parameters.
     * <p>
     * Applies the same principles as the default constructor
     * {@link User#User()}
     */
    public User(String name, String password, String email, Timestamp creationDate) {
        super("User", name);
        this.password = password;
        this.email = email;
        this.creationDate = creationDate;
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
     * Gets the <code>password</code> attribute of this object.
     */
    public String getPassword() {
        return this.password;
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
     * Gets the <code>email</code> attribute of this object.
     */
    public String getEmail() {
        return this.email;
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
     * Gets the <code>creationDate</code> attribute of this object.
     */
    public Timestamp getCreationDate() {
        return this.creationDate;
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
     * Transforms the milliseconds stored in <code>accountAge</code>
     * to the highest whole unit of time possible, and returns it.
     * @param accountAge current account age in milliseconds
     * @return a <code>String</code> containing the converted time with
     * a label at the end specifying the unit of measure
     */
    public static String visualizeAccountAge(long accountAge) {
        if(accountAge <= 31536000000l) {
            if(accountAge <= 86400000) {
                if(accountAge <= 3600000) {
                    if(accountAge <= 60000) {
                        if(accountAge <= 1000) {
                            // return milliseconds
                            return String.format("%.2f ms", (double)accountAge/1.0d);
                        } else {
                            // return seconds
                            return String.format("%.2f sec", (double)accountAge/1000.0d);
                        }
                    } else {
                        // return minutes
                        return String.format("%.2f min", (double)accountAge/60000.0d);
                    }
                } else {
                    // return hours
                    return String.format("%.2f hr", (double)accountAge/3600000.0d);
                }
            } else {
                // return days
                return String.format("%.2f days", (double)accountAge/86400000.0d);
            }
        } else {
            // return years
            return String.format("%.2f years", (double)accountAge/31536000000.0d);
        }
    }

    /**
     * Gets the <code>User</code>'s account age.
     * @return current account age in milliseconds
     */
    public long getAccountAge() {
        return this.accountAge;
    }

    @Override
    public String createString() {
        return super.createString() + ", password=" + this.password + ", email=" + this.email + ", creationDate=" + this.creationDate;
    }

    /**
     * @see #createString()
     */
    @Override
    public String toString() {
        return "{User [" + this.createString() + "]}";
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
