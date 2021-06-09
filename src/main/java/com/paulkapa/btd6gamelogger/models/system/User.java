package com.paulkapa.btd6gamelogger.models.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.paulkapa.btd6gamelogger.models.base.NamedEntity;

/**
 * Class that defines the attributes of a real user of this application.
 */
@Entity(name = "User")
@Table(name = "users")
public class User extends NamedEntity {

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
     */
    public User() {
        super("User");
        this.password = null;
        this.email = null;
    }

    /**
     * Constructor that takes a variable <code>name</code> as parameter
     * containing the name of the <code>User</code> entity that is being created
     * and saves it in memory.
     * <p>
     * The value is added up the chain to the top super class.
     */
    public User(String name) {
        super("User", name);
        this.password = null;
        this.email = null;
    }

    /**
     * Constructor that takes two variables, <code>name</code> and <code>password</code>,
     * as parameters, containing the name and the password of the <code>User</code>
     * entity that is being created, and saves them in memory.
     * <p>
     * The <code>name</code> value is added up the chain to the top super class.
     */
    public User(String name, String password) {
        super("User", name);
        this.password = password;
        this.email = null;
    }

    /**
     * Constructor that takes 3 variables, <code>name</code>, <code>password</code> and
     * <code>email</code>, as parameters, containing the name, password and email of the
     * <code>User</code> entity that is being created, and saves them in memory.
     * <p>
     * The <code>name</code> value is added up the chain to the top super class.
     */
    public User(String name, String password, String email) {
        super("User", name);
        this.password = password;
        this.email = email;
    }

    /**
     * Sets the <code>password</code> attribute of the current instance.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the <code>password</code> attribute of the current instance.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the <code>email</code> attribute of the current instance.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the <code>password</code> attribute of the current instance.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Concatenates the private attributes of this instance into a string
     * and return it.
     */
    public String createString() {
        return "password=" + this.password + ", email=" + this.email;
    }

    @Override
    public String toString() {
        return "{User [" + super.createString() + ", password=" + this.password + ", email=" + this.email + "]}";
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
