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
    public void setTowers(HashMap<String, TowerEntity> towers) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maps == null) ? 0 : maps.hashCode());
        result = prime * result + ((towers == null) ? 0 : towers.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
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
        GameEntity other = (GameEntity) obj;
        if (maps == null) {
            if (other.maps != null)
                return false;
        } else if (!maps.equals(other.maps))
            return false;
        if (towers == null) {
            if (other.towers != null)
                return false;
        } else if (!towers.equals(other.towers))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
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

    @Override
    public String toString() {
        return this.user.toString() + "\n" + this.maps.toString() + "\n" + this.towers.toString();
    }
}
