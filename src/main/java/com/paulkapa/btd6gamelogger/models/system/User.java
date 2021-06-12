package com.paulkapa.btd6gamelogger.models.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
    }

    /**
     * Constructor that initializes a <code>User</code> object with
     * the provided <code>name</code>, <code>password</code> and
     * <code>email</code> parameters.
     * <p>
     * Applies the same principles as the default constructor
     * {@link User#User()}
     */
    public User(String name, String password, String email) {
        super("User", name);
        this.password = password;
        this.email = email;
    }

    /**
     * Sets the <code>password</code> attribute for this object.
     * <p>
     * Applies trim() on the string before it is stored.
     */
    public void setPassword(String password) {
        this.password = password.trim();
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
        this.email = email.trim();
    }

    /**
     * Gets the <code>password</code> attribute of this object.
     */
    public String getEmail() {
        return this.email;
    }

    @Override
    public String createString() {
        return super.createString() + ", password=" + this.password + ", email=" + this.email;
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
