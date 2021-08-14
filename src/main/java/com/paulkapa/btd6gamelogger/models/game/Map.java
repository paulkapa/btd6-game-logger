package com.paulkapa.btd6gamelogger.models.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.paulkapa.btd6gamelogger.database.game.GameContainer;
import com.paulkapa.btd6gamelogger.models.BaseEntity;

/**
 * <b>Class that defines the properties of a Map</b>
 * <p>
 * Provides static methods to query maps.
 *
 * @see BaseEntity
 */
public class Map extends BaseEntity {

    /**
     * Current difficulty that applies to this map.
     */
    private String difficulty;
    /**
     * Current game mode that applies to this map.
     */
    private String gameMode;
    /**
     * Current cash available on this map.
     * <p>
     * Defaults to starting cash if difficulty and game mode are set.
     */
    private long currentCash;
    /**
     * Current lives remaining on this map.
     * <p>
     * Defaults to starting lives if difficulty and game mode are set.
     */
    private int currentLives;

    /**
     * Default constructor.
     */
    public Map() {
        super();
        this.difficulty = null;
        this.gameMode = null;
        this.currentCash = 0l;
        this.currentLives = 0;
    }

    /**
     * Preffered constructor.
     *
     * @param name map name
     * @param type map type
     */
    public Map(String name, String type) {
        super(name, type);
        this.currentCash = 0l;
        this.currentLives = 0;
    }

    /**
     * Complete constructor.
     *
     * @param name         map name
     * @param type         map type
     * @param difficulty   current difficulty
     * @param gameMode     current game mode
     * @param currentCash  current cash available
     * @param currentLives current lives remaining
     */
    public Map(String name, String type, String difficulty, String gameMode, long currentCash, int currentLives) {
        super(name, type);
        this.difficulty = difficulty;
        this.gameMode = gameMode;
        this.currentCash = currentCash;
        this.currentLives = currentLives;
    }

    /**
     * Copy constructor.
     *
     * @param other the map to copy data from
     */
    public Map(Map other) {
        super(other.getInstance());
        this.difficulty = other.getDifficulty();
        this.gameMode = other.getGameMode();
        this.currentCash = other.getCurrentCash();
        this.currentLives = other.getCurrentLives();
    }

    /**
     * Initializes default maps from storage if not previously initialized.
     *
     * @return a list containing the default maps.
     * @throws IOException if maps cannot be found in storage
     */
    public static LinkedHashMap<String, Map[]> getDefaultMaps() throws IOException {
        if (!GameContainer.isInitDefaultMaps) {
            GameContainer.isInitDefaultMaps = true;
            return Map.initDefaultMaps();
        } else
            return null;
    }

    public static Map[] getMapsAsArray() {
        try {
            List<Map> maps = new LinkedList<>();
            GameContainer.getDefaultMaps().forEach((s, m) -> Collections.addAll(maps, m));
            return maps.toArray(new Map[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return new Map[0];
        }
    }

    /**
     * Return a map found in the maps list that has a specified name.
     *
     * @param mapName    the map name to search for
     * @param mapsSearch a list containing the maps to search for
     * @return a map with the specified name
     * @throws Exception if the list provided as search location is null or empty,
     *                   or if no map could be found with the provided name
     */
    public static Map getMapByName(String mapName, LinkedHashMap<String, Map[]> mapsSearch) throws Exception {
        ArrayList<Map> result = new ArrayList<>(0);
        if (mapsSearch == null)
            throw new Exception("Cannot search in empty map!", new Throwable("Provided mapsSearch is null."));
        mapsSearch.forEach((n, m) -> {
            for (var i = 0; i < m.length; i++)
                if (result.isEmpty() && m[i].getName().equals(mapName))
                    result.add(m[i]);
        });
        if (result.get(0) == null)
            throw new Exception("No result matching the criteria!",
                    new Throwable("No map with name '" + mapName + "' was found."));
        return result.get(0);
    }

    /**
     * Reads the default maps from 'maps.json' file found in application local
     * storage.
     *
     * @return a list containing the default maps
     * @throws IOException if maps cannot be found in storage
     */
    private static LinkedHashMap<String, Map[]> initDefaultMaps() throws IOException {
        LinkedHashMap<String, Map[]> defaultMaps = new LinkedHashMap<>();
        // aux variable to apply getClass() method on
        LinkedHashMap<String, Map[]> mapType = new LinkedHashMap<>(0);
        // aux variable to apply getClass() method on
        var arrayType = new Map[0];
        // aux variable to apply getClass() method on
        var type = new Map();

        // opens a file as read-only
        FileReader fr = null;
        try {
            fr = new FileReader(
                    new File(GameContainer.ABSOLUTE_PATH + GameContainer.SRC_RELATIVE_DATA_PATH + "maps.json"));
        } catch (IOException e) {
            fr = new FileReader(
                    new File(GameContainer.ABSOLUTE_PATH + GameContainer.PROD_RELATIVE_DATA_PATH + "maps.json"));
        }
        // opens a read stream from the file reader
        var br = new BufferedReader(fr);
        // gson object
        var gson = new GsonBuilder().setPrettyPrinting().serializeNulls().enableComplexMapKeySerialization().create();
        // reads entire file contents as a 'mapType'
        JsonElement element = gson.toJsonTree(new LinkedHashMap<>(gson.fromJson(br, mapType.getClass())));
        // converts the element to an object
        var object = element.getAsJsonObject();
        // parses trough the entries read and saves them in the respective types while
        // building the method return value
        for (Entry<String, JsonElement> e : object.entrySet()) {
            var array = e.getValue().getAsJsonArray();
            ArrayList<Map> currMaps = new ArrayList<>();
            for (var i = 0; i < array.size(); i++) {
                currMaps.add(new Map(gson.fromJson(array.get(i), type.getClass())));
            }
            defaultMaps.put(e.getKey(), currMaps.toArray(arrayType));
        }
        br.close();
        fr.close();
        // Default maps saved in memory!
        return defaultMaps;
    }

    /**
     * Gets the current difficulty.
     *
     * @return a string representing the current difficulty
     */
    public String getDifficulty() {
        return this.difficulty;
    }

    /**
     * Sets the current difficulty.
     *
     * @param difficulty the current difficulty
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the current game mode.
     *
     * @return a string representing the current game mode
     */
    public String getGameMode() {
        return this.gameMode;
    }

    /**
     * Sets the current game mode.
     *
     * @param gameMode the current game mode
     */
    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Gets the current available cash.
     *
     * @return a long representing the current available cash
     */
    public long getCurrentCash() {
        return this.currentCash;
    }

    /**
     * Sets the current available cash.
     *
     * @param currentCash the current available cash
     */
    public void setCurrentCash(long currentCash) {
        this.currentCash = currentCash;
    }

    /**
     * Gets the current remaining lives.
     *
     * @return an int representing the current remaining lives
     */
    public int getCurrentLives() {
        return this.currentLives;
    }

    /**
     * Sets the current remaining lives.
     *
     * @param currentLives the current remaining lives
     */
    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }

    public int getTypeSize() {
        return new MapKey(super.getType()).getSize();
    }

    @Override
    public String createString() {
        var sb = new StringBuilder();
        sb.delete(0, sb.length());
        sb.append(super.createString());
        sb.append(", difficulty=").append(getDifficulty());
        sb.append(", game_mode=").append(getGameMode());
        sb.append(", current_cash=").append(getCurrentCash());
        sb.append(", current_lives=").append(getCurrentLives());
        return sb.toString();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

class MapKey {

    String[] key = new String[2];

    MapKey(String type) {
        this.key[0] = type;
        switch (type) {
        case "Begginer": {
            this.key[1] = Integer.toString(18);
            break;
        }
        case "Intermediate": {
            this.key[1] = Integer.toString(15);
            break;
        }
        case "Advanced": {
            this.key[1] = Integer.toString(12);
            break;
        }
        case "Expert": {
            this.key[1] = Integer.toString(10);
            break;
        }
        default: {
            this.key[1] = Integer.toString(0);
        }
        }
    }

    /**
     * @return the key
     */
    public String[] getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String[] key) {
        this.key = key;
    }

    public String getType() {
        return this.key[0];
    }

    public int getSize() {
        return Integer.parseInt(this.key[1]);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode() {
        final var prime = 31;
        var result = 1;
        result = prime * result + Arrays.hashCode(key);
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MapKey other = (MapKey) obj;
        return Arrays.equals(key, other.key);
    }
}
