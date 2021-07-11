package com.paulkapa.btd6gamelogger.models.game;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.google.gson.Gson;
import com.paulkapa.btd6gamelogger.models.BaseEntity;

/**
 * <h4>Class that defines the properties of a Map</h4>
 *
 * Provides static storage for all maps.
 *
 * @see BaseEntity
 */
public class Map extends BaseEntity {

    private static LinkedHashMap<String, Map[]> defaultMaps = null;
    private static boolean isInitDefaultMaps = false;

    private String difficulty;
    private String gameMode;
    private long currentCash;
    private int currentLives;

    public Map() {
        super();
        this.difficulty = null;
        this.gameMode = null;
        this.currentCash = 0l;
        this.currentLives = 0;
    }

    /**
     * Preffered constructor.
     * @param name
     * @param type
     * @param startingCash
     * @param startingLives
     */
    public Map(String name, String type) {
        super(name, type);
        this.currentCash = 0l;
        this.currentLives = 0;
    }

    public Map(String name, String type, String difficulty, String gameMode, long currentCash, int currentLives) {
        super(name, type);
        this.difficulty = difficulty;
        this.gameMode = gameMode;
        this.currentCash = currentCash;
        this.currentLives = currentLives;
    }

    /**
     * Copy constructor.
     * @param other
     */
    public Map(Map other) {
        super(other.getInstance());
        this.difficulty = other.getDifficulty();
        this.gameMode = other.getGameMode();
        this.currentCash = other.getCurrentCash();
        this.currentLives = other.getCurrentLives();
    }

    public static LinkedHashMap<String, Map[]> getDefaultMaps() {
        if(!Map.isInitDefaultMaps) Map.initDefaultMaps();
        return Map.defaultMaps;
    }

    public static Map getMapByName(String mapName, LinkedHashMap<String, Map[]> mapsSearch) throws Exception {
        ArrayList<Map> result = new ArrayList<>(0);
        result.add(0, null);
        if(mapsSearch == null) throw new Exception(
            "Cannot search in empty map!",
            new Throwable("Provided mapsSearch is null."));
        mapsSearch.forEach((n, m) -> {for(int i=0; i<m.length; i++)
            if(result.get(0) == null && m[i].getName().equals(mapName)) {result.add(0, m[i]); break;}});
        if(result.get(0) == null) throw new Exception(
            "No result matching the criteria!",
            new Throwable("No map with name '" + mapName + "' was found."));
        return result.get(0);
    }

    private static void initDefaultMaps() {
        Map.defaultMaps = new LinkedHashMap<>();
        ArrayList<Map> thisMaps = new ArrayList<>(0);
        Map[] type = new Map[0];
        // Beginner
        thisMaps.clear();
        thisMaps.add(new Map("Monkey Meadow", "Beginner"));
        thisMaps.add(new Map("Tree Stump", "Beginner"));
        thisMaps.add(new Map("Town Center", "Beginner"));
        thisMaps.add(new Map("Resort", "Beginner"));
        thisMaps.add(new Map("Skates", "Beginner"));
        thisMaps.add(new Map("Lotus Island", "Beginner"));
        thisMaps.add(new Map("Candy Falls", "Beginner"));
        thisMaps.add(new Map("Winter Park", "Beginner"));
        thisMaps.add(new Map("Carved", "Beginner"));
        thisMaps.add(new Map("Park Path", "Beginner"));
        thisMaps.add(new Map("Alpine Run", "Beginner"));
        thisMaps.add(new Map("Frozen Over", "Beginner"));
        thisMaps.add(new Map("In The Loop", "Beginner"));
        thisMaps.add(new Map("Cubism", "Beginner"));
        thisMaps.add(new Map("Four Circles", "Beginner"));
        thisMaps.add(new Map("Hedge", "Beginner"));
        thisMaps.add(new Map("End Of The Road", "Beginner"));
        thisMaps.add(new Map("Logs", "Beginner"));
        defaultMaps.put("Begginner", thisMaps.toArray(type));
        // Intermediate
        thisMaps.clear();
        thisMaps.add(new Map("Balance", "Intermediate"));
        thisMaps.add(new Map("Encrypted", "Intermediate"));
        thisMaps.add(new Map("Bazaar", "Intermediate"));
        thisMaps.add(new Map("Adora's Temple", "Intermediate"));
        thisMaps.add(new Map("Spring Spring", "Intermediate"));
        thisMaps.add(new Map("KartsNDarts", "Intermediate"));
        thisMaps.add(new Map("Moon Landing", "Intermediate"));
        thisMaps.add(new Map("Haunted", "Intermediate"));
        thisMaps.add(new Map("Downstream", "Intermediate"));
        thisMaps.add(new Map("Firing Range", "Intermediate"));
        thisMaps.add(new Map("Cracked", "Intermediate"));
        thisMaps.add(new Map("Streambed", "Intermediate"));
        thisMaps.add(new Map("Chutes", "Intermediate"));
        thisMaps.add(new Map("Rake", "Intermediate"));
        thisMaps.add(new Map("Spice Islands", "Intermediate"));
        defaultMaps.put("Intermediate", thisMaps.toArray(type));
        // Advanced
        thisMaps.clear();
        thisMaps.add(new Map("X Factor", "Advanced"));
        thisMaps.add(new Map("Mesa", "Advanced"));
        thisMaps.add(new Map("Geared", "Advanced"));
        thisMaps.add(new Map("Spillway", "Advanced"));
        thisMaps.add(new Map("Cargo", "Advanced"));
        thisMaps.add(new Map("Pat's Pond", "Advanced"));
        thisMaps.add(new Map("Peninsula", "Advanced"));
        thisMaps.add(new Map("High Finance", "Advanced"));
        thisMaps.add(new Map("Another Brick", "Advanced"));
        thisMaps.add(new Map("Off The Coast", "Advanced"));
        thisMaps.add(new Map("Cornfield", "Advanced"));
        thisMaps.add(new Map("Underground", "Advanced"));
        defaultMaps.put("Advanced", thisMaps.toArray(type));
        // Expert
        thisMaps.clear();
        thisMaps.add(new Map("Sanctuary", "Expert"));
        thisMaps.add(new Map("Ravine", "Expert"));
        thisMaps.add(new Map("Flooded Valley", "Expert"));
        thisMaps.add(new Map("Infernal", "Expert"));
        thisMaps.add(new Map("Bloody Puddles", "Expert"));
        thisMaps.add(new Map("Workshop", "Expert"));
        thisMaps.add(new Map("Quad", "Expert"));
        thisMaps.add(new Map("Dark Castle", "Expert"));
        thisMaps.add(new Map("Muddy Puddles", "Expert"));
        thisMaps.add(new Map("#ouch", "Expert"));
        defaultMaps.put("Expert", thisMaps.toArray(type));
        // Default maps saved in memory!
        Map.isInitDefaultMaps = true;
    }

    public String getDifficulty() {return this.difficulty;}

    public void setDifficulty(String difficulty) {this.difficulty = difficulty;}

    public String getGameMode() {return this.gameMode;}

    public void setGameMode(String gameMode) {this.gameMode = gameMode;}

    public long getCurrentCash() {return this.currentCash;}

    public void setCurrentCash(long currentCash) {this.currentCash = currentCash;}

    public int getCurrentLives() {return this.currentLives;}

    public void setCurrentLives(int currentLives) {this.currentLives = currentLives;}

    @Override
    public String createString() {
        StringBuffer sb = new StringBuffer();
        sb.delete(0, sb.length());
        sb.append(super.createString());
        sb.append(", difficulty=").append(getDifficulty());
        sb.append(", game_mode=").append(getGameMode());
        sb.append(", current_cash=").append(getCurrentCash());
        sb.append(", current_lives=").append(getCurrentLives());
        return sb.toString();
    }

    @Override
    public String toString() {return new Gson().toJson(this);}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (int) (currentCash ^ (currentCash >>> 32));
        result = prime * result + currentLives;
        result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
        result = prime * result + ((gameMode == null) ? 0 : gameMode.hashCode());
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
        Map other = (Map) obj;
        if (currentCash != other.currentCash)
            return false;
        if (currentLives != other.currentLives)
            return false;
        if (difficulty == null) {
            if (other.difficulty != null)
                return false;
        } else if (!difficulty.equals(other.difficulty))
            return false;
        if (gameMode == null) {
            if (other.gameMode != null)
                return false;
        } else if (!gameMode.equals(other.gameMode))
            return false;
        return true;
    }
}
