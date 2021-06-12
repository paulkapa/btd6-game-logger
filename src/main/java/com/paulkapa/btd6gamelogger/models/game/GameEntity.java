package com.paulkapa.btd6gamelogger.models.game;

import java.util.HashMap;

import com.paulkapa.btd6gamelogger.models.system.User;

/**
 * Container class game entities, such as
 * maps, towers, upgrades and users.
 */
public class GameEntity {

    /**
     * The current loggedin <code>User</code>.
     */
    private User user;

    /**
     * The <code>MapEntity</code> objects.
     */
    private HashMap<String, MapEntity> maps;

    /**
     * The <code>TowerEntity</code>.
     */
    private HashMap<String, TowerEntity> towers;

    /**
     * Default constructor.
     */
    public GameEntity() {
        this.user = null;
        this.maps = null;
        this.towers = null;
    }

    /**
     *
     */
    public GameEntity(User user) {
        this.user = user;
        this.maps = null;
        this.towers = null;
    }

    /**
     *
     */
    public GameEntity(User user, MapEntity map) {
        this.user = user;
        this.addMap(map);
        this.towers = null;
    }

    /**
     *
     */
    public GameEntity(User user, HashMap<String, MapEntity> maps) {
        this.user = user;
        this.maps = maps;
        this.towers = null;
    }

    /**
     *
     */
    public GameEntity(User user, HashMap<String, MapEntity> maps, HashMap<String, TowerEntity> towers) {
        this.user = user;
        this.maps = maps;
        this.towers = towers;
    }

    /**
     * Sets the <code>User</code> object.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the <code>User</code> object.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Sets the <code>MapEntity</code> objects.
     */
    public void setMaps(HashMap<String, MapEntity> maps) {
        this.maps = maps;
    }

    /**
     * Adds a <code>MapEntity</code> object to the collection of
     * <code>MapEntity</code> objects.
     */
    public void addMap(MapEntity e) {
        if(this.maps != null)
            this.maps.put(e.getName(), e);
        else {
            this.maps = new HashMap<>();
            this.maps.put(e.getName(), e);
        }
    }

    /**
     * Gets the <code>MapEntity</code> objects.
     */
    public HashMap<String, MapEntity> getMaps() {
        if(this.maps != null)
            return this.maps;
        else {
            this.maps = new HashMap<>();
            return this.maps;
        }
    }

    /**
     * Gets a <code>MapEntity</code> object using the
     * provided <code>name</code> parameter.
     * <p>
     * Returns <code>null</code> if not found.
     */
    public MapEntity getMap(String name) {
        if(this.maps != null && this.maps.keySet().contains(name))
            return this.maps.get(name);
        else {
            return null;
        }
    }

    /**
     * Sets the <codeTowerEntity</code> objects.
     */
    public void SetTowers(HashMap<String, TowerEntity> towers) {
        this.towers = towers;
    }

    /**
     * Adds a <code>TowerEntity</code> object to the collection of
     * <code>TowerEntity</code> objects.
     */
    public void addTower(TowerEntity e) {
        if(this.towers != null)
            this.towers.put(e.getName(), e);
        else {
            this.towers = new HashMap<>();
            this.towers.put(e.getName(), e);
        }
    }

    /**
     * Gets the <code>TowerEntity</code> objects.
     */
    public HashMap<String, TowerEntity> getTowers() {
        if(this.towers != null)
            return this.towers;
        else {
            this.towers = new HashMap<>();
            return this.towers;
        }
    }

    /**
     * Gets a <code>TowerEntity</code> object using the
     * provided <code>name</code> parameter.
     * <p>
     * Returns <code>null</code> if not found.
     */
    public TowerEntity getTower(String name) {
        if(this.towers != null && this.towers.keySet().contains(name))
            return this.towers.get(name);
        else {
            return null;
        }
    }
}
