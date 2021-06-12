package com.paulkapa.btd6gamelogger.models.base;

/**
 * Interface defining base methods for all entities.
 * <p>
 * <code>ID</code> and <code>type</code> attributes should
 * be set only trough constructors, hence only getters
 * are defined here.
 */
public interface iEntityType {

    /**
     * Returns the stored <code>ID</code> attribute.
     */
    public int getID();

    /**
     * Returns the stored <code>type</code> attribute.
     */
    public String getType();

    /**
     * Returns <code>true</code> if this object has a set,
     * not null, <code>name</code> attribute.
     */
    public boolean isNamedEntity();

    /**
     * Sets the <code>name</code> attribute for this object
     * and returns it.
     */
    public String setName(String name);

    /**
     * Gets the <code>name</code> attribute of this object.
     */
    public String getName();
}
