package com.paulkapa.btd6gamelogger.models.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Base level class of all entities. Implements {@link iEntityType}
 * which provides methods for handling the base aspects of an entity.
 * <p>
 * More complex entities should provide their own implementations of
 * the interface methods implemented here.
 *
 * @see iEntityType
 * @MappedSuperClass
 */
@MappedSuperclass
public class BaseEntity implements iEntityType {

    @Transient
    private StringBuffer sb = new StringBuffer();

    /**
     * The <code>ID</code> value matching the Auto-Incrementing ID field
     * value acting as Primary Key of the working table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int ID;

    /**
     * The <code>name</code> value stored in the corresponding field of
     * the working table.
     * <p>
     * It may be used to store either as:
     * <ul>
     * <li>Username;
     * <li>First Name;
     * <li>Last Name;
     * <li>Full Name;
     * <li>Name/Description
     * </ul>
     * depending on the situation.
     * <p>
     * It is set explicitly through the constructor
     * {@link BaseEntity#BaseEntity(String, String)}, otherwise always null.
     *
     * @see BaseEntity#BaseEntity(String, String)
     */
    @Column(name = "name")
    private String name;

    /**
     * Stores the type of entity that extends
     * this instance of <code>BaseEntity</code>.
     * <p>
     * Other use cases are allowed.
     * <p>
     * It is set explicitly through the constructors
     * {@link BaseEntity#BaseEntity(String)} or {@link BaseEntity#BaseEntity(String, String)},
     * otherwise always null.
     *
     * @see BaseEntity#BaseEntity(String)
     * @see BaseEntity#BaseEntity(String, String)
     */
    @Transient
    private String type;

    /**
     * Default constructor.
     * <p>
     * Initializes a <code>BaseEntity</code> object with null
     * <code>type</code> and <code>name</code> attributes.
     */
    public BaseEntity() {
        this.type = null;
        this.name= null;
    }

    /**
     * Constructor that initializes a <code>BaseEntity</code> object
     * with the provided <code>type</code> parameter.
     */
    public BaseEntity(String type) {
        this.type = type;
        this.name = null;
    }

    /**
     * Constructor that initializes a <code>BaseEntity</code> object
     * with the provided <code>type</code> and <code>name</code> parameters.
     */
    public BaseEntity(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public boolean isNamedEntity() {
        if(this.name != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String setName(String name) {
        this.name = name;
        return this.name;
    }

    @Override
    public String getName() {
        if(this.isNamedEntity()) {
            return this.name;
        } else {
            return null;
        }
    }

    /**
     * Concatenates the private attributes of this object instance into a string
     * and return it.
     * <p>
     * Format example: "Attribute1=<code>Attribute1</code>,
     * Attribute2=<code>Attribute2</code>, ..."
     * @return a string representation of the object's attributes
     */
    public String createString() {
        this.sb.delete(0, this.sb.length());
        this.sb.append("ID=").append(this.ID);
        this.sb.append(", type=").append(this.type);
        this.sb.append(", name=").append(this.name);
        return this.sb.toString();
    }

    /**
     * Format example: "{{@link #getClass()} [ {@link #createString()} ] }"
     * @see #createString()
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "{ " + this.getName() + "=[" + this.createString() + "]}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ID;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
