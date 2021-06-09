package com.paulkapa.btd6gamelogger.models.base;

/**
 * Interface declaring base methods for all entities.
 * <p>
 * <code>ID</code> and <code>type</code> attributes should
 * be set only trough constructors, hence only getters
 * are defined here.
 */
public interface EntityTypeInterface {

    public int getID();
    public String getType();
    public boolean isNamedEntity();
    public String setName(String name);
    public String getName();
}
