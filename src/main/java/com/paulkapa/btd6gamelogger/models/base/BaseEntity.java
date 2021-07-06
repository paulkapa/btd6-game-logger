package com.paulkapa.btd6gamelogger.models.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Top level class of all entities.
 * 
 * More complex entities should provide their own implementations of
 * the methods defined here.
 *
 * @MappedSuperClass
 */
@MappedSuperclass
public class BaseEntity {

    @Transient
    private StringBuffer sb = new StringBuffer();

    /**
     * The <code>ID</code> value matching the Auto-Incrementing ID field
     * value acting as Primary Key of the working table.
     * <p>
     * May be used as a default identifier for an object.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int ID;

    /**
     * The <code>name</code> value stored in the corresponding field of
     * the working table.
     * <p>
     * It may be used to store either one of:
     * <ul>
     * <li>Username;
     * <li>First Name;
     * <li>Last Name;
     * <li>Full Name;
     * <li>Name/Description;
     * <li>Other related naming attributes.
     * </ul>
     * depending on the situation.
     */
    @Column(name = "name")
    private String name;

    /**
     * Stores the type of entity that extends
     * this instance of <code>BaseEntity</code>.
     * <p>
     * Other use cases are allowed.
     */
    @Column(name = "type")
    private String type;

    /**
     * Default constructor.
     * <p>
     * Initializes a <code>BaseEntity</code> object with null
     * <code>type</code> and <code>name</code> attributes.
     */
    public BaseEntity() {
        this.name= null;
        this.type = null;
    }

    /**
     * Constructor that initializes a <code>BaseEntity</code> object
     * with the provided <code>type</code> parameter.
     */
    public BaseEntity(String type) {
        this.name = null;
        this.type = type;
    }

    /**
     * Constructor that initializes a <code>BaseEntity</code> object
     * with the provided <code>type</code> and <code>name</code> parameters.
     */
    public BaseEntity(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private boolean hasNotNullName() {
        if(this.name != null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean hasNotEmptyName() {
        if(!this.name.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkName() {
        if(this.hasNotNullName() && this.hasNotEmptyName()) {
            return true;
        } else {
            return false;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Concatenates the private attributes of this object instance into a string
     * and return it.
     * <p>
     * Format example: "attr1=<code>val1</code>, [...],
     * attrN=<code>valN</code>"
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
     * Format example: "{ {@link #getName()}=[{@link #createString()}]}"
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
