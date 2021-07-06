package com.paulkapa.btd6gamelogger.models.game;

import java.util.ArrayList;

import com.paulkapa.btd6gamelogger.models.base.BaseEntity;
import com.paulkapa.btd6gamelogger.models.system.User; 

/**
 * Container class for game entities and models, such as
 * maps, towers, upgrades and users.
 * @see BaseEntity
 */
public class GameContainer extends BaseEntity {

    private StringBuffer sb = new StringBuffer();

    //
    public static final String[] DIFFICULTIES = {
        "Easy", "Medium", "Hard"
    };
    public static final String[] GAME_MODES = {
        "Standard",
        "Primary Monkeys Only", "Deflation",
        "Military Monkeys Only", "Apopalypse", "Reverse",
        "Magic Monkeys Only", "Double HP MOABs", "Half Cash", "Alternate Bloon Rounds", "Impoppable", "CHIMPS",
        "Sandbox"
    };
    //
    public static final int STARTING_CASH = 650;
    public static final int DEFLATION = 20000;
    public static final int HALF_CASH = 325;
    public static final int SANDBOX = 9999999;
    //
    public static final int EASY_STARTING_LIVES = 200;
    public static final int MEDIUM_STARTING_LIVES = 150;
    public static final int HARD_STARTING_LIVES = 100;
    public static final int IMPOPPABLE_STARTING_LIVES = 1;
    public static final int CHIMPS_STARTING_LIVES = 1;
    public static final int SANDBOX_STARTING_LIVES = 9999999;
    //
    public static final int MK_BONUS_STARTING_CASH = 200;
    public static final int MK_BONUS_STARTING_LIVES = 25;
    //
    public static final double EASY_COST_MODIFIER = 0.85d;
    public static final double MEDIUM_COST_MODIFIER = 1d;
    public static final double HARD_COST_MODIFIER = 1.08d;

    /**
     * The current loggedin <code>User</code>.
     */
    private User user;

    /**
     * The in use <code>Map</code> objects.
     */
    private ArrayList<Map> maps;

    /**
     * The in use <code>Tower</code> objects.
     */
    private ArrayList<Tower> towers;
    
    /**
     * The in use <code>Upgrade</code> objects.
     */
    private ArrayList<Upgrade[][]> upgrades;

    /**
     * The currently selected difficulty.
     */
    private String diff;

    /**
     * The currently selected game mode.
     */
    private String mode;

    /**
     * Default constructor.
     */
    public GameContainer() {
        super("BTD6", "Container");
        super.setID(0);
        this.user = null;
        this.maps = null;
        this.towers = null;
        this.upgrades = null;
        this.diff = null;
        this.mode = null;
    }

    /**
     * Initializes instance with just an User.
     * @param user
     */
    public GameContainer(User user) {
        super("BTD6", "Container");
        super.setID(0);
        this.user = user;
        this.maps = null;
        this.towers = null;
        this.upgrades = null;
        this.diff = null;
        this.mode = null;
    }
    
    /**
     * Preffered constructor.
     * Initializes instance with an User and a Map.
     * @param user
     */
    public GameContainer(User user, Map map) {
        super("BTD6", "Container");
        super.setID(0);
        this.user = user;
        this.addMap(map);
        this.towers = null;
        this.upgrades = null;
        this.diff = map.getDifficulty();
        this.mode = map.getGameMode();
    }

    /**
     * Complete constructor.
     * @param user
     * @param maps
     * @param towers
     * @param upgrades
     * @param diff
     * @param mode
     */
    public GameContainer(User user, ArrayList<Map> maps, ArrayList<Tower> towers, ArrayList<Upgrade[][]> upgrades,
            String diff, String mode) {
        super("BTD6", "Container");
        super.setID(0);
        this.user = user;
        this.maps = maps;
        this.towers = towers;
        this.upgrades = upgrades;
        this.diff = diff;
        this.mode = mode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Map> getMaps() {
        return maps;
    }

    public Map getMapByName(String nameSearch) {
        if(this.maps != null) {
            for(Map m : this.maps) {
                if(m.getName().equals(nameSearch)) {
                    return m;
                }
            }
        }
        return null;
    }
    
    public Map getMapByIndex(int index) {
        if(this.maps != null && index >= 0 && index < this.maps.size()) {
            return this.maps.get(index);
        }
        return null;
    }

    public void setMaps(ArrayList<Map> maps) {
        this.maps = maps;
    }

    public void addMap(Map m) {
        if(this.maps == null) {
            this.maps = new ArrayList<>();
        }
        this.maps.add(m);
    }

    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public Tower getTowerByName(String nameSearch) {
        if(this.towers != null) {
            for(Tower t : this.towers) {
                if(t.getName().equals(nameSearch)) {
                    return t;
                }
            }
        }
        return null;
    }
    
    public Tower getTowerByIndex(int index) {
        if(this.towers != null && index >= 0 && index < this.towers.size()) {
            return this.towers.get(index);
        }
        return null;
    }

    public void setTowers(ArrayList<Tower> towers) {
        this.towers = towers;
    }

    public void addTower(Tower t) {
        if(this.towers == null) {
            this.towers = new ArrayList<>();
        }
        this.towers.add(t);
    }

    public ArrayList<Upgrade[][]> getUpgrades() {
        return upgrades;
    }

    public Upgrade[][] getUpgradesByTowerName(String nameSearch) {
        if(this.upgrades != null) {
            for(Upgrade[][] u : this.upgrades) {
                if(u[0][0].getName().equals(nameSearch)) {
                    return u;
                }
            }
        }
        return null;
    }

    public void setUpgrades(ArrayList<Upgrade[][]> upgrades) {
        this.upgrades = upgrades;
    }

    public void addUpgrade(Upgrade[][] u) {
        if(this.upgrades == null) {
            this.upgrades = new ArrayList<>();
        }
        this.upgrades.add(u);
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public static int getStartingCashByGameMode(String gameMode) {
        for(String s : GameContainer.GAME_MODES) {
            if(s.equals(gameMode)) {
                switch(s) {
                    case "Deflation": {
                        return GameContainer.DEFLATION;
                    } case "Half Cash" : {
                        return GameContainer.HALF_CASH;
                    } default: {
                        break;
                    }
                }
            }
        }
        return GameContainer.STARTING_CASH;
    }

    private static int getStartingLivesByGameMode(String gameMode) {
        for(String s : GameContainer.GAME_MODES) {
            if(s.equals(gameMode)) {
                switch(s) {
                    case "Impoppable": {
                        return GameContainer.IMPOPPABLE_STARTING_LIVES;
                    } case "CHIMPS": {
                        return GameContainer.CHIMPS_STARTING_LIVES;
                    } case "Sandbox": {
                        return GameContainer.SANDBOX_STARTING_LIVES;
                    } default: {
                        break;
                    }
                }
            }
        }
        return 0;
    }

    public static int getStartingLivesByDifficultyAndGameMode(String difficulty, String gameMode) {
        for(String s : GameContainer.DIFFICULTIES) {
            if(s.equals(difficulty)) {
                switch(s) {
                    case "Easy": {
                        return GameContainer.EASY_STARTING_LIVES;
                    } case "Medium": {
                        return GameContainer.MEDIUM_STARTING_LIVES;
                    } case "Hard": {
                        int startingLives = GameContainer.getStartingLivesByGameMode(gameMode);
                        if(startingLives != 0) {
                            return startingLives;
                        } else {
                            return GameContainer.HARD_STARTING_LIVES;
                        }
                    } default: {
                        break;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public String createString() {
        this.sb.delete(0, this.sb.length());
        this.sb.append(super.createString());
        this.sb.append(", user=").append(this.user.getName());
        this.sb.append(", maps=").append(this.maps.toString());
        this.sb.append(", towers=").append(this.towers.toString());
        this.sb.append(", applied_upgrades=").append(Upgrade.countAppliedUpgrades(this.upgrades));
        this.sb.append(", remaining_upgrades=").append(this.upgrades.size()*15 - (Upgrade.countAppliedUpgrades(this.upgrades) + Upgrade.countNotLockedUpgrades(this.upgrades)));
        this.sb.append(", difficulty=").append(this.getDiff());
        this.sb.append(", game_mode=").append(this.getMode());
        return this.sb.toString();
    }

    /**
     * @see #createString()
     */
    @Override
    public String toString() {
        return "{ "+ this.getName() + "=[" + this.createString() + "]}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((diff == null) ? 0 : diff.hashCode());
        result = prime * result + ((maps == null) ? 0 : maps.hashCode());
        result = prime * result + ((mode == null) ? 0 : mode.hashCode());
        result = prime * result + ((towers == null) ? 0 : towers.hashCode());
        result = prime * result + ((upgrades == null) ? 0 : upgrades.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
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
        GameContainer other = (GameContainer) obj;
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
        if (upgrades == null) {
            if (other.upgrades != null)
                return false;
        } else if (!upgrades.equals(other.upgrades))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }
}
