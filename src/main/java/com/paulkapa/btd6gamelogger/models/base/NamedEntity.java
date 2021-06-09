package com.paulkapa.btd6gamelogger.models.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Top level class of all named entities. Extends <code>BaseEntity</code>
 * which provides basic <code>ID</code> attributes to child entities, adding
 * <code>name</code> attribute and different implementations for some methods.
 * 
 * @see BaseEntity
 */
@MappedSuperclass
public class NamedEntity extends BaseEntity {

    /**
     * The <code>name</code> value (representing either username, firstname, name, etc)
     * for the current instance.
     */
    @Column(name = "name")
    String name;

    /**
     * Deafault constructor.
     */
    public NamedEntity() {
        super("NamedEntity");
        this.name = null;
    }
    
    /**
     * Constructor that takes a variable <code>type</code> as parameter
     * containing the type of the child class that called the constructor
     * and saves it in memory.
     * <p>
     * The value is added up the chain to the top super class.
     */
    public NamedEntity(String type) {
        super(type);
        this.name = null;
    }

    /**
     * Constructor that takes two variables, <code>type</code> and <code>name</code>,
     * as parameters, containing the type of the child class that called the constructor
     * and the name of the child entity, and saves them in memory.
     * <p>
     * The <code>type</code> value is added up the chain to the top super class.
     */
    public NamedEntity(String type, String name) {
        super(type);
        this.name = name;
    }
    
    /**
     * Returns true if the current class has a not null <code>name</code> attribute,
     * otherwise false.
     */
    @Override
    public boolean isNamedEntity() {
        return (this.name == null) ? false : true;
    }

    /**
     * Sets the <code>name</code> attribute for the current instance
     * and returns it.
     */
    @Override
    public String setName(String name) {
        this.name = (name == null) ? null : name;
        return name;
    }

    /**
     * Gets the <code>name</code> attribute for the current instance.
     */
    @Override
    public String getName() {
        return isNamedEntity() ? this.name : null;
    }
    
    /**
     * Concatenates the private attributes of this instance into a string
     * and return it.
     */
    public String createString() {
        return super.createString() + ", name=" + this.name;
    }

    @Override
    public String toString() {
        return "{NamedEntity [" + super.createString() + ", name=" + name + "}]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        NamedEntity other = (NamedEntity) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
