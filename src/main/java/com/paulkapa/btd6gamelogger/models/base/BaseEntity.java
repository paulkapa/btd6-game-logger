package com.paulkapa.btd6gamelogger.models.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
//import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * Top level class of all entities. Implements <code>EntityTypeInterface</code>
 * which provides methods for handling the base aspects of an entity.
 * <p>
 * More complex entities provide their own implementations of the interface methods
 * implemented here.
 * 
 * @see EntityTypeInterface
 */
@MappedSuperclass
public class BaseEntity implements EntityTypeInterface {

    /**
     * The <code>ID</code> value matching the Auto-Incrementing ID field
     * value acting as Primary Key in the working Table. 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int ID;

    /**
     * The <code>type</code> field stores the type of the child
     * class that extends this instance of the <code>BaseEntity</code>.
     * <p>
     * Is set by the child class at instantiation, otherwise always null.
     */
    @Transient
    private String type;

    /**@Version
    private int version;*/

    /**
     * Default constructor.
     */
    public BaseEntity() {
        this.type = null;
    }

    /**
     * Constructor that takes a variable <code>type</code> as parameter
     * containing the type of the child class that called the constructor
     * and saves it in memory.
     */
    public BaseEntity(String type) {
        this.type = type;
    }

    /**
     * Returns the stored <code>ID</code> of the current object that has
     * <code>BaseEntity</code> as super class.
     */
    public int getID() {
        return this.ID;
    }
    
    /**
     * Returns the stored <code>type</code> of the child class.
     */
    @Override
    public String getType() {
        return this.type;
    }

    /**
     * Returns true if the current class has a <code>name</code> attribute.
     * <p>
     * This method always returns <code>false</code> since <code>BaseEntity</code>
     * has no <code>name</code> attribute.
     */
    @Override
    public boolean isNamedEntity() {
        return false;
    }

    /**
     * Sets the <code>name</code> attribute for the current instance
     * and returns it.
     * <p>
     * This method has no effect and return <code>null</code>
     * since <code>BaseEntity</code> has no <code>name</code>
     * attribute.
     */
    @Override
    public String setName(String name) {
        return null;
    }

    /**
     * Gets the <code>name</code> attribute for the current instance.
     * <p>
     * This method return <code>null</code> since <code>BaseEntity</code>
     * has no <code>name</code> attribute.
     */
    @Override
    public String getName() {
        return null;
    }
    
    /**
     * Concatenates the private attributes of this instance into a string
     * and return it.
     */
    public String createString() {
        return "ID=" + this.ID + ", type=" + this.type;
    }

    @Override
    public String toString() {
        return "{BaseEntity [ID=" + ID + ", type=" + type + "]}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseEntity other = (BaseEntity) obj;
        if (ID != other.ID)
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
