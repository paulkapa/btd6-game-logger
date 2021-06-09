package com.paulkapa.btd6gamelogger.models.game;

import java.util.HashMap;

import com.paulkapa.btd6gamelogger.models.system.User;

/**
 * Container class for top level game entities, such as
 * maps, and real entities, such as users.
 * <p>
 * One game can have only one real entity <code>User</code>.
 */
public class GameEntity {

    /**
     * The <code>User</code> loggedin into this <code>Game</code>.
     */
    private User user;
    /**
     * The <code>Map</code> entities belonging to the <code>Game</code>
     * instance.
     */
    private HashMap<String, MapEntity> maps;
    /**
     * The <code>BaseTowerEntity</code> objects belonging to the <code>Game</code>
     * instance.
     */
    private HashMap<String, TowerEntity> towers;

    /**
     * Default constructor.
     */
    public GameEntity() {
        this.user = new User();
        this.maps = new HashMap<>();
        this.towers = new HashMap<>();
    }

    /**
     * 
     */
    public GameEntity(User user) {
        this.user = user;
        this.maps = new HashMap<>();
        this.towers = new HashMap<>();
    }
    
    /**
     * 
     */
    public GameEntity(User user, MapEntity map) {
        this.user = user;
        this.addMap(map);
        this.towers = new HashMap<>();
    }
    
    /**
     * 
     */
    public GameEntity(User user, HashMap<String, MapEntity> maps) {
        this.user = user;
        this.maps = maps;
        this.towers = new HashMap<>();
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
     * Sets the <code>User</code> loggedin into
     * this instance of <code>Game</code>.
     */
    public void setUser(User user) {
        this.user = (user == null) ? new User() : user;
    }

    /**
     * Gets the <code>User</code> loggedin into
     * this instance of <code>Game</code>.
     */
    public User getUser() {
        return (this.user == null) ? new User() : this.user;
    }

    /**
     * Sets the <code>Map</code> entities belonging
     * to this instance of <code>Game</code>.
     */
    public void setMaps(HashMap<String, MapEntity> maps) {
        this.maps = (maps == null) ? new HashMap<>() : maps;
    }

    /**
     * Adds a <code>Map</code> to the list of <code>Map</code>
     * entities belonging to this instance of <code>Game</code>.
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
     * Gets the <code>Map</code> entities belonging
     * to this instance of <code>Game</code>.
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
     * Gets a <code>Map</code> entity by specifying it's name.
     */
    public MapEntity getMap(String name) {
        if(this.maps != null)
            return this.maps.get(name);
        else {
            this.maps = new HashMap<>();
            return new MapEntity();
        }
    }
    
    /**
     * Sets the <code>BaseTowerEntity</code> objects belonging
     * to this instance of <code>Game</code>.
     */
    public void SetTowers(HashMap<String, TowerEntity> towers) {
        this.towers = (towers == null) ? new HashMap<>() : towers;
    }

    /**
     * Adds a <code>BaseTowerEntity</code> to the list of <code>BaseTowerEntity</code>
     * objects belonging to this instance of <code>Game</code>.
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
     * Gets the <code>BaseTowerEntity</code> objects belonging
     * to this instance of <code>Game</code>.
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
     * Gets a <code>BaseTowerEntity</code> object by specifying it's name.
     */
    public TowerEntity getTower(String name) {
        if(this.towers != null)
            return this.towers.get(name);
        else {
            this.towers = new HashMap<>();
            return new TowerEntity();
        }
    }
}
