package com.paulkapa.btd6gamelogger.database.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paulkapa.btd6gamelogger.models.BaseEntity;
import com.paulkapa.btd6gamelogger.models.game.Map;
import com.paulkapa.btd6gamelogger.models.game.Tower;
import com.paulkapa.btd6gamelogger.models.game.Upgrade;
import com.paulkapa.btd6gamelogger.models.system.User;

/**
 * <h4>Container class</h4>
 *
 * Stores in-memory or locally objects of type {@code User}
 * {@code Map}, {@code Tower} and {@code Upgrade}.
 * <p>
 * Provides static storage for game modifiers, difficulties
 * and game modes.
 *
 * @see BaseEntity
 */
public class GameContainer extends BaseEntity {

    // Game options
    public static final String[] DIFFICULTIES = {"Easy", "Medium", "Hard"};
    public static final String[] GAME_MODES = {
        "Standard",
        "Primary Monkeys Only", "Deflation",
        "Military Monkeys Only", "Apopalypse", "Reverse",
        "Magic Monkeys Only", "Double HP MOABs", "Half Cash", "Alternate Bloon Rounds", "Impoppable", "CHIMPS",
        "Sandbox"
    };
    // Starting cash modifiers
    public static final int BASE_STARTING_CASH = 650;
    public static final int DEFLATION_STARTING_CASH = 20000;
    public static final int HALF_CASH_STARTING_CASH = 325;
    public static final int SANDBOX_STARTING_CASH = 9999999;
    public static final int MK_BONUS_STARTING_CASH = 200;
    // Lives modifiers
    public static final int EASY_STARTING_LIVES = 200;
    public static final int BASE_STARTING_LIVES = 150;
    public static final int HARD_STARTING_LIVES = 100;
    public static final int IMPOPPABLE_STARTING_LIVES = 1;
    public static final int CHIMPS_STARTING_LIVES = 1;
    public static final int SANDBOX_STARTING_LIVES = 9999999;
    public static final int MK_BONUS_STARTING_LIVES = 25;
    // Cost modifiers
    public static final double EASY_COST_MODIFIER = 0.85d;
    public static final double BASE_COST_MODIFIER = 1d;
    public static final double HARD_COST_MODIFIER = 1.08d;

    private User user;
    private String saveName;
    private String diff;
    private String mode;
    private LinkedHashMap<String, Map[]> maps;
    private LinkedHashMap<String, Tower[]> towers;
    private LinkedHashMap<String, Upgrade[][]> upgrades;

    public GameContainer() {
        super("BTD6", "Container");
        this.user = null;
        this.saveName = null;
        this.diff = null;
        this.mode = null;
        this.maps = null;
        this.towers = null;
        this.upgrades = null;
    }

    /**
     * Preffered constructor.
     * @param user
     * @param map
     */
    public GameContainer(User user, String saveName) {
        super("BTD6", "Container-" + saveName);
        this.user = user;
        this.saveName = saveName;
        this.diff = null;
        this.mode = null;
        this.maps = null;
        this.towers = null;
        this.upgrades = null;
    }

    public GameContainer(User user, String diff, String mode, String saveName,
            LinkedHashMap<String, Map[]> maps,
            LinkedHashMap<String, Tower[]> towers,
            LinkedHashMap<String, Upgrade[][]> upgrades) {
        super("BTD6", "Container-" + saveName);
        this.user = user;
        this.saveName = saveName;
        this.diff = diff;
        this.mode = mode;
        this.maps = maps;
        this.towers = towers;
        this.upgrades = upgrades;
    }

    /**
     * Copy constructor.
     * @param other
     */
    public GameContainer(GameContainer other) {
        super(other.getInstance());
        this.user = other.getUser();
        this.saveName = other.getSaveName();
        this.diff = other.getDiff();
        this.mode = other.getMode();
        this.maps = other.getMaps();
        this.towers = other.getTowers();
        this.upgrades = other.getUpgrades();
    }

    public User getUser() {return this.user;}

    public void replaceUser(User user) {
        super.setID(0);
        super.setType("Container");
        this.saveName = null;
        this.diff = null;
        this.mode = null;
        this.maps = null;
        this.towers = null;
        this.upgrades = null;
        this.user = new User(user);
    }

    public String getSaveName() {return saveName;}

    public void setSaveName(String saveName) {this.saveName = saveName;}

    public String getDiff() {return this.diff;}

    public void setDiff(String diff) {this.diff = diff;}

    public String getMode() {return this.mode;}

    public void setMode(String mode) {this.mode = mode;}

    public LinkedHashMap<String, Map[]> getMaps() {return maps;}

    public void setMaps(LinkedHashMap<String, Map[]> maps) {this.maps = maps;}

    public LinkedHashMap<String, Tower[]> getTowers() {return this.towers;}

    public void setTowers(LinkedHashMap<String, Tower[]> towers) {this.towers = towers;}

    public LinkedHashMap<String, Upgrade[][]> getUpgrades() {return this.upgrades;}

    public void setUpgrades(LinkedHashMap<String, Upgrade[][]> upgrades) {this.upgrades = upgrades;}

    public static int calculateStartingCash(String gameMode) throws Exception {
        for(String s : GameContainer.GAME_MODES)
            if(s.equals(gameMode))
                switch(s) {
                    case "Deflation": {return GameContainer.DEFLATION_STARTING_CASH;
                    } case "Half Cash" : {return GameContainer.HALF_CASH_STARTING_CASH;
                    } default: {return GameContainer.BASE_STARTING_CASH;}
                }
            else {throw new Exception(
                "Couldn't find the provided game mode!",
                new Throwable("The value '" + gameMode + "' is not a valid game mode."));}
        return 0;
    }

    public static int calculateStartingLives(String difficulty, String gameMode) throws Exception {
        for(String d : GameContainer.DIFFICULTIES)
            if(d.equals(difficulty))
                switch(d) {
                    case "Easy": {return gameMode.equals("Sandbox") ? GameContainer.SANDBOX_STARTING_LIVES : GameContainer.EASY_STARTING_LIVES;
                    } case "Medium": {return gameMode.equals("Sandbox") ? GameContainer.SANDBOX_STARTING_LIVES : GameContainer.BASE_STARTING_LIVES;
                    } case "Hard": {
                        for(String m : GameContainer.GAME_MODES)
                            if(m.equals(gameMode))
                                switch(m) {
                                    case "Impoppable": {return GameContainer.IMPOPPABLE_STARTING_LIVES;
                                    } case "CHIMPS": {return GameContainer.CHIMPS_STARTING_LIVES;
                                    } case "Sandbox": {return GameContainer.SANDBOX_STARTING_LIVES;
                                    } default: {return GameContainer.HARD_STARTING_LIVES;}
                                }
                            else {throw new Exception(
                                "Couldn't find the provided game mode!",
                                new Throwable("The value '" + gameMode + "' is not a valid game mode."));}
                    } default: {return 0;}
                }
            else {throw new Exception(
                "Couldn't find the provided difficulty!",
                new Throwable("The value '" + gameMode + "' is not a valid difficulty."));}
        return 0;
    }

    public void resetContainer() {
        super.setID(0);
        super.setType("Container");
        this.saveName = null;
        this.diff = null;
        this.mode = null;
        this.maps = null;
        this.towers = null;
        this.upgrades = null;
        this.user = null;
    }

    public void saveGame() {
        try {
            FileWriter fwr = new FileWriter(new File(GameContainer.createFileName(this.user.getName(), this.saveName)));
            BufferedWriter bwr = new BufferedWriter(fwr);
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(this, bwr);
            bwr.close();
            fwr.close();
            this.replaceUser(this.user);
        } catch (Exception e) {e.printStackTrace();}
    }

    public static GameContainer restoreSave(String u, String s) {
        try {
            FileReader fr = new FileReader(new File(GameContainer.createFileName(u, s)));
            BufferedReader br = new BufferedReader(fr);
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            return new GameContainer(gson.fromJson(br, GameContainer.class));
        } catch (Exception e) {e.printStackTrace(); return null;}
    }

    private static String createFileName(String u, String s) {return "data-store-" + u + "-" + s + ".json";}

    @Override
    public String createString() {
        StringBuffer sb = new StringBuffer();
        sb.delete(0, sb.length());
        sb.append(super.createString());
        sb.append(", user=").append(this.user.getName());
        sb.append(", save_name=").append(this.getSaveName());
        sb.append(", difficulty=").append(this.getDiff());
        sb.append(", game_mode=").append(this.getMode());
        sb.append(", maps=").append(this.maps.toString());
        sb.append(", towers=").append(this.towers.toString());
        sb.append(", upgrades=").append(this.upgrades.toString());
        return sb.toString();
    }

    @Override
    public String toString() {return new Gson().toJson(this);}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((diff == null) ? 0 : diff.hashCode());
        result = prime * result + ((maps == null) ? 0 : maps.hashCode());
        result = prime * result + ((mode == null) ? 0 : mode.hashCode());
        result = prime * result + ((saveName == null) ? 0 : saveName.hashCode());
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
        if (saveName == null) {
            if (other.saveName != null)
                return false;
        } else if (!saveName.equals(other.saveName))
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
