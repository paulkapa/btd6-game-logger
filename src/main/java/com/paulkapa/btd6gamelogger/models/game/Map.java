package com.paulkapa.btd6gamelogger.models.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
            for (int i = 0; i < m.length; i++)
                if (result.size() == 0 && m[i].getName().equals(mapName))
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
        Map[] arrayType = new Map[0];
        // aux variable to apply getClass() method on
        Map type = new Map();

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
        BufferedReader br = new BufferedReader(fr);
        // gson object
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().enableComplexMapKeySerialization().create();
        // reads entire file contents as a 'mapType'
        JsonElement element = gson.toJsonTree(new LinkedHashMap<>(gson.fromJson(br, mapType.getClass())));
        // converts the element to an object
        JsonObject object = element.getAsJsonObject();
        // parses trough the entries read and saves them in the respective types while
        // building the method return value
        for (Entry<String, JsonElement> e : object.entrySet()) {
            JsonArray array = e.getValue().getAsJsonArray();
            ArrayList<Map> currMaps = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
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

    @Override
    public String createString() {
        StringBuilder sb = new StringBuilder();
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
