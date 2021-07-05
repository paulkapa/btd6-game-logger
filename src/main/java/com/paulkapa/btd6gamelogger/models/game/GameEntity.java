package com.paulkapa.btd6gamelogger.models.game;

import java.util.HashMap;

import com.paulkapa.btd6gamelogger.models.system.User;

/**
 * Container class game entities, such as
 * maps, towers, upgrades and users.
 */
public class GameEntity {

    private StringBuffer sb = new StringBuffer();

    /**
     * The current loggedin <code>User</code>.
     */
    private User user;

    /**
     * The <code>MapEntity</code> objects.
     */
    private HashMap<String, Map> maps;

    /**
     * The <code>TowerEntity</code>.
     */
    private HashMap<String, Tower> towers;

    /**
     * The current selected difficulty.
     */
    private String diff;

    /**
     * The current selected difficulty.
     */
    private String mode;

    /**
     * Default constructor.
     */
    public GameEntity() {
        this.user = null;
        this.maps = null;
        this.towers = null;
        this.diff = null;
        this.mode = null;
    }

    /**
     *
     */
    public GameEntity(User user) {
        this.user = user;
        this.maps = null;
        this.towers = null;
        this.diff = null;
        this.mode = null;
    }

    /**
     *
     */
    public GameEntity(User user, Map map, String diff, String mode) {
        this.user = user;
        this.addMap(map);
        this.towers = null;
        this.diff = diff;
        this.mode = mode;
    }

    /**
     *
     */
    public GameEntity(User user, HashMap<String, Map> maps) {
        this.user = user;
        this.maps = maps;
        this.towers = null;
    }

    /**
     *
     */
    public GameEntity(User user, HashMap<String, Map> maps, HashMap<String, Tower> towers) {
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
    public void setMaps(HashMap<String, Map> maps) {
        this.maps = maps;
    }

    /**
     * Adds a <code>MapEntity</code> object to the collection of
     * <code>MapEntity</code> objects.
     */
    public void addMap(Map e) {
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
    public HashMap<String, Map> getMaps() {
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
    public Map getMap(String name) {
        if(this.maps != null && this.maps.keySet().contains(name))
            return this.maps.get(name);
        else {
            return null;
        }
    }
    
    /**
     * Gets a <code>MapEntity</code> object using the
     * provided <code>index</code> parameter.
     * <p>
     * Returns <code>null</code> if not found.
     */
    public Map getMap(int index) {
        if(this.maps != null && (this.maps.get(this.maps.keySet().toArray()[index]) != null))
            return this.maps.get(this.maps.keySet().toArray()[index]);
        else {
            return null;
        }
    }

    /**
     * Sets the <codeTowerEntity</code> objects.
     */
    public void setTowers(HashMap<String, Tower> towers) {
        this.towers = towers;
    }

    /**
     * Adds a <code>TowerEntity</code> object to the collection of
     * <code>TowerEntity</code> objects.
     */
    public void addTower(Tower e) {
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
    public HashMap<String, Tower> getTowers() {
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
    public Tower getTower(String name) {
        if(this.towers != null && this.towers.keySet().contains(name))
            return this.towers.get(name);
        else {
            return null;
        }
    }

    /**
     * 
     */
    public void setDiff(String diff) {
        this.diff = diff;
    }

    /**
     * 
     * @return
     */
    public String getDiff() {
        return this.diff;
    }

    /**
     * 
     * @return
     */
    public String getMode() {
        return mode;
    }

    /**
     * 
     * @param mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    public String createString() {
        this.sb.delete(0, this.sb.length());
        this.sb.append(" [No base model] = ").append(this.user).append("\n").append(this.maps).append("\n").append(this.towers);
        return this.sb.toString();
    }

    /**
     * @see #createString()
     */
    @Override
    public String toString() {
        return "{GameEntity [" + this.createString() + "]}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((diff == null) ? 0 : diff.hashCode());
        result = prime * result + ((maps == null) ? 0 : maps.hashCode());
        result = prime * result + ((mode == null) ? 0 : mode.hashCode());
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
        if (diff == null) {
            if (other.diff != null)
                return false;
        } else if (!diff.equals(other.diff))
            return false;
        if (maps == null) {
            if (other.maps != null)
                return false;
        } else if (!maps.equals(other.maps))
            return false;
        if (mode == null) {
            if (other.mode != null)
                return false;
        } else if (!mode.equals(other.mode))
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
}
