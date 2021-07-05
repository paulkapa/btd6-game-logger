package com.paulkapa.btd6gamelogger.models.game;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.paulkapa.btd6gamelogger.models.base.BaseEntity;

/**
 * Class that defines the properties of a Map in BTD6.
 *
 * @see BaseEntity
 */
public class Map extends BaseEntity {
    
    public static final int EASY_STARTING_CASH = 0;
    public static final int MEDIUM_STARTING_CASH = 0;
    public static final int HARD_STARTING_CASH = 0;
    
    public static final int EASY_STARTING_LIVES = 0;
    public static final int MEDIUM_STARTING_LIVES = 0;
    public static final int HARD_STARTING_LIVES = 0;

    private StringBuffer sb = new StringBuffer();

    private static ArrayList<Map> inUseMaps = null;
    private static LinkedHashMap<String, Map> maps = null;

    private String difficulty;
    private String gameMode;
    private int startingCash;
    private int startingLives;
    private long currentCash;
    private int currentLives;

    /**
     * Default constructor.
     */
    public Map() {
        super(null, null);
        this.difficulty = null;
        this.gameMode = null;
        this.startingCash = 0;
        this.startingLives = 0;
        this.currentCash = 0l;
        this.currentLives = 0;
    }

    /**
     * Preffered constructor.
     * @param type
     * @param name
     * @param startingCash
     * @param startingLives
     */
    public Map(String type, String name, int startingCash, int startingLives) {
        super(type, name);
        this.startingCash = startingCash;
        this.startingLives = startingLives;
    }

    /**
     * Complete constructor.
     * @param type
     * @param name
     * @param difficulty
     * @param gameMode
     * @param startingCash
     * @param startingLives
     * @param currentCash
     * @param currentLives
     */
    public Map(String type, String name, String difficulty, String gameMode,
                int startingCash, int startingLives, long currentCash, int currentLives) {
        super(type, name);
        this.difficulty = difficulty;
        this.gameMode = gameMode;
        this.startingCash = startingCash;
        this.startingLives = startingLives;
        this.currentCash = currentCash;
        this.currentLives = currentLives;
    }

    /**
     * Copy constructor.
     * @param other
     */
    public Map(Map other) {
        super(other.getType(), other.getName());
        this.difficulty = other.getDifficulty();
        this.gameMode = other.getGameMode();
        this.startingCash = other.getStartingCash();
        this.startingLives = other.getStartingLives();
        this.currentCash = other.getCurrentCash();
        this.currentLives = other.getCurrentLives();
    }

    public static ArrayList<Map> getInUseMaps() {
        return inUseMaps;
    }

    public static void setInUseMaps(ArrayList<Map> inUseMaps) {
        Map.inUseMaps = inUseMaps;
    }

    public static void useMap(Map map) {

    }

    public static LinkedHashMap<String, Map> getMaps() {
        try {
            if(Map.maps == null) {
                if(!Map.initMapsMap()) {
                    throw new Exception("Could not reset towers map!");
                }
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        return Map.maps;
    }

    public static Map getMapByName(String nameSearch) {
        Map result = null;
        try {
            if(Map.maps == null) {
                if(!Map.initMapsMap()) {
                    throw new Exception("Could not reset maps map!");
                }
            }
            for(String name : Map.maps.keySet()) {
                if(name.equals(nameSearch)) {
                    if(result == null) {
                        result = Map.maps.get(name);
                        break;
                    }
                }
            }
            if(result == null) {
                throw new Exception("No result matching the criteria was found!");
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        return result;
    }

    public static void setMaps(LinkedHashMap<String, Map> maps) {
        Map.maps = maps;
    }

    public static boolean initMapsMap() {
        try{
            Map.maps = new LinkedHashMap<>();
            maps.put("Monkey Meadow", new Map("Beginner", "Monkey Meadow", 650, 150));
            maps.put("Tree Stump", new Map("Beginner", "Tree Stump", 650, 150));
            maps.put("Town Center", new Map("Beginner", "Town Center", 650, 150));
            maps.put("Resort", new Map("Beginner", "Resort", 650, 150));
            maps.put("Skates", new Map("Beginner", "Skates", 650, 150));
            maps.put("Lotus Island", new Map("Beginner", "Lotus Island", 650, 150));
            maps.put("Candy Falls", new Map("Beginner", "Candy Falls", 650, 150));
            maps.put("Winter Park", new Map("Beginner", "Winter Park", 650, 150));
            maps.put("Carved", new Map("Beginner", "Carved", 650, 150));
            maps.put("Park Path", new Map("Beginner", "Park Path", 650, 150));
            maps.put("Alpine Run", new Map("Beginner", "Alpine Run", 650, 150));
            maps.put("Frozen Over", new Map("Beginner", "Frozen Over", 650, 150));
            maps.put("In The Loop", new Map("Beginner", "In The Loop", 650, 150));
            maps.put("Cubism", new Map("Beginner", "Cubism", 650, 150));
            maps.put("Four Circles", new Map("Beginner", "Four Circles", 650, 150));
            maps.put("Hedge", new Map("Beginner", "Hedge", 650, 150));
            maps.put("End Of The Road", new Map("Beginner", "End Of The Road", 650, 150));
            maps.put("Logs", new Map("Beginner", "Logs", 650, 150));
            maps.put("Balance", new Map("Itermediate", "Balance", 650, 150));
            maps.put("Encrypted", new Map("Itermediate", "Encrypted", 650, 150));
            maps.put("Bazaar", new Map("Itermediate", "Bazaar", 650, 150));
            maps.put("Adora's Temple", new Map("Itermediate", "Adora's Temple", 650, 150));
            maps.put("Spring Spring", new Map("Itermediate", "Spring Spring", 650, 150));
            maps.put("KartsNDarts", new Map("Itermediate", "KartsNDarts", 650, 150));
            maps.put("Moon Landing", new Map("Itermediate", "Moon Landing", 650, 150));
            maps.put("Haunted", new Map("Itermediate", "Haunted", 650, 150));
            maps.put("Downstream", new Map("Itermediate", "Downstream", 650, 150));
            maps.put("Firing Range", new Map("Itermediate", "Firing Range", 650, 150));
            maps.put("Cracked", new Map("Itermediate", "Cracked", 650, 150));
            maps.put("Streambed", new Map("Itermediate", "Streambed", 650, 150));
            maps.put("Chutes", new Map("Itermediate", "Chutes", 650, 150));
            maps.put("Rake", new Map("Itermediate", "Rake", 650, 150));
            maps.put("Spice Islands", new Map("Intermediate", "Spice Islands", 650, 150));
            maps.put("X Factor", new Map("Advanced", "X Factor", 650, 150));
            maps.put("Mesa", new Map("Advanced", "Mesa", 650, 150));
            maps.put("Geared", new Map("Advanced", "Geared", 650, 150));
            maps.put("Spillway", new Map("Advanced", "Spillway", 650, 150));
            maps.put("Cargo", new Map("Advanced", "Cargo", 650, 150));
            maps.put("Pat's Pond", new Map("Advanced", "Pat's Pond", 650, 150));
            maps.put("Peninsula", new Map("Advanced", "Peninsula", 650, 150));
            maps.put("High Finance", new Map("Advanced", "High Finance", 650, 150));
            maps.put("Another Brick", new Map("Advanced", "Another Brick", 650, 150));
            maps.put("Off The Coast", new Map("Advanced", "Off The Coast", 650, 150));
            maps.put("Cornfield", new Map("Advanced", "Cornfield", 650, 150));
            maps.put("Underground", new Map("Advanced", "Underground", 650, 150));
            maps.put("Sanctuary", new Map("Expert", "Sanctuary", 650, 150));
            maps.put("Ravine", new Map("Expert", "Ravine", 650, 150));
            maps.put("Flooded Valley", new Map("Expert", "Flooded Valley", 650, 150));
            maps.put("Infernal", new Map("Expert", "Infernal", 650, 150));
            maps.put("Bloody Puddles", new Map("Expert", "Bloody Puddles", 650, 150));
            maps.put("Workshop", new Map("Expert", "Workshop", 650, 150));
            maps.put("Quad", new Map("Expert", "Quad", 650, 150));
            maps.put("Dark Castle", new Map("Expert", "Dark Castle", 650, 150));
            maps.put("Muddy Puddles", new Map("Expert", "Muddy Puddles", 650, 150));
            maps.put("#ouch", new Map("Expert", "#ouch", 650, 150));
            return true;
        } catch(Exception e) {
            System.err.println(e);
            return false;
        }
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public int getStartingCash() {
        return startingCash;
    }

    public void setStartingCash(int startingCash) {
        this.startingCash = startingCash;
    }

    public int getStartingLives() {
        return startingLives;
    }

    public void setStartingLives(int startingLives) {
        this.startingLives = startingLives;
    }

    public long getCurrentCash() {
        return currentCash;
    }

    public void setCurrentCash(long currentCash) {
        this.currentCash = currentCash;
    }

    public int getCurrentLives() {
        return currentLives;
    }

    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }

    @Override
    public String createString() {
        this.sb.delete(0, this.sb.length());
        this.sb.append(super.createString());
        this.sb.append(", difficulty=").append(this.getDifficulty());
        this.sb.append(", game_mode=").append(this.getGameMode());
        this.sb.append(", starting_cash=").append(this.getStartingCash());
        this.sb.append(", starting_lives=").append(this.getStartingLives());
        this.sb.append(", current_cash=").append(this.getCurrentCash());
        this.sb.append(", current_lives=").append(this.getCurrentLives());
        return this.sb.toString();
    }

    @Override
    public String toString() {
        return "{ " + this.getName() + "=[" + this.createString() + "]}";
    }
}
