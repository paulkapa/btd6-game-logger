package com.paulkapa.btd6gamelogger.models.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.paulkapa.btd6gamelogger.database.game.GameContainer;
import com.paulkapa.btd6gamelogger.models.BaseEntity;

/**
 * <b>Class that defines the attributes af an Upgrade</b>
 * <p>
 * Provides static methods to query upgrades.
 *
 * @see BaseEntity
 */
public class Upgrade extends BaseEntity {

    /**
     * Name of the tower this upgrade applies to.
     */
    private String towerName;
    /**
     * Cost of the upgrade
     */
    private int cost;
    /**
     * If this upgrade is applied on the tower it belongs to.
     */
    private boolean isApplied;
    /**
     * If this upgrade can be applied on the tower it belongs to.
     */
    private boolean isLocked;
    /**
     * The path this upgrade is included in.
     */
    private int path;
    /**
     * The path tier this upgrade is found at.
     */
    private int tier;

    /**
     * Default constructor.
     */
    public Upgrade() {
        super();
        this.towerName = null;
        this.cost = 0;
        this.isApplied = false;
        this.isLocked = false;
        this.path = 0;
        this.tier = 0;
    }

    /**
     * Preferred constructor.
     *
     * @param name      upgrade name
     * @param type      upgrade type
     * @param towerName name of the tower this upgrade applies to
     * @param cost      upgrade cost
     */
    public Upgrade(String name, String type, String towerName, int cost) {
        super(name, type);
        this.towerName = towerName;
        this.cost = cost;
        this.isApplied = false;
        this.isLocked = false;
        this.path = 0;
        this.tier = 0;
    }

    /**
     * Complete constructor.
     *
     * @param name      upgrade name
     * @param type      upgrade type
     * @param towerName name of the tower this upgrade applies to
     * @param cost      upgrade cost
     * @param isApplied if this upgrade can be applied on the tower it belongs to
     * @param isLocked  if this upgrade can be applied on the tower it belongs to
     * @param path      the path this upgrade is included in
     * @param tier      the path tier this upgrade is found at
     */
    public Upgrade(String name, String type, String towerName, int cost, boolean isApplied, boolean isLocked, int path,
            int tier) {
        super(name, type);
        this.towerName = towerName;
        this.cost = cost;
        this.isApplied = isApplied;
        this.isLocked = isLocked;
        this.path = path;
        this.tier = tier;
    }

    /**
     * Copy constructor.
     *
     * @param other the upgrade to copy data from
     */
    public Upgrade(Upgrade other) {
        super(other.getInstance());
        this.towerName = other.getTowerName();
        this.cost = other.getCost();
        this.isApplied = other.isApplied();
        this.isLocked = other.isLocked();
        this.path = other.getPath();
        this.tier = other.getTier();
    }

    /**
     * Counts the number of upgrades found in the provided list that can be applied
     * to the tower the belong to.
     *
     * @param upgradesList a list containing the upgrades to check
     * @return an int representing the number of unlocked upgrades
     */
    public static int countUnlockedUpgrades(LinkedHashMap<String, Upgrade[][]> upgradesList) {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(0, 0);
        if (upgradesList != null)
            upgradesList.forEach((s, u) -> {
                for (var i = 0; i < u.length; i++)
                    for (var j = 0; j < u[i].length; j++)
                        if (!u[i][j].isLocked())
                            result.add(0, result.get(0) + 1);
            });
        return result.get(0);
    }

    /**
     * Counts the number of upgrades found in the provided list that are applied to
     * the tower the belong to.
     *
     * @param upgradesList a list containing the upgrades to check
     * @return an int representing the number of applied upgrades
     */
    public static int countAppliedUpgrades(LinkedHashMap<String, Upgrade[][]> upgradesList) {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(0, 0);
        if (upgradesList != null)
            upgradesList.forEach((s, u) -> {
                for (var i = 0; i < u.length; i++)
                    for (var j = 0; j < u[i].length; j++)
                        if (!u[i][j].isApplied())
                            result.add(0, result.get(0) + 1);
            });
        return result.get(0);
    }

    /**
     * Initializes default upgrades from storage if not previously initialized.
     *
     * @return a list containing the default upgrades.
     * @throws Exception if towers names could not be retrieved or if upgrades
     *                   cannot be found in storage
     */
    public static LinkedHashMap<String, Upgrade[][]> getDefaultUpgrades() throws Exception {
        if (!GameContainer.isInitDefaultUpgrades) {
            GameContainer.isInitDefaultUpgrades = true;
            return Upgrade.initDefaultUpgrades();
        }
        return null;
    }

    /**
     * Return the upgrades found in the provided list that belong to a specific
     * tower.
     *
     * @param towerName      a string representing the tower name the search is
     *                       based upon
     * @param upgradesSearch a list containing the upgrades to check
     * @return a list containing the upgrades belonging to the provided tower name
     * @throws Exception if provided search list is null or empty, or if no results
     *                   were found for the tower name specified
     */
    public static ArrayList<Upgrade[]> getUpgradesByTowerName(String towerName,
            LinkedHashMap<String, Upgrade[][]> upgradesSearch) throws Exception {
        ArrayList<Upgrade[]> result = new ArrayList<>();
        result.add(0, null);
        if (upgradesSearch == null)
            throw new Exception("Cannot search in empty map!", new Throwable("Provided upgradesSearch is null."));
        upgradesSearch.forEach((n, u) -> {
            if (result.isEmpty() && n.equals(towerName))
                result.addAll(Arrays.asList(u));
        });
        if (result.isEmpty())
            throw new Exception("No result matching the criteria!",
                    new Throwable("No upgrades for tower with name '" + towerName + "' were found."));
        return result;
    }

    /**
     * Reads the default upgrades from 'upgrades.json' file found in application
     * local storage.
     *
     * @return a list containing the default upgrades
     * @throws Exception if towers names could not be retrieved or if upgrades
     *                   cannot be found in storage
     */
    private static LinkedHashMap<String, Upgrade[][]> initDefaultUpgrades() throws Exception {
        LinkedHashMap<String, Upgrade[][]> defaultUpgrades = new LinkedHashMap<>();
        // Type variables
        Type mapType = new TypeToken<LinkedHashMap<String, Upgrade[][]>>() {
        }.getType();
        var arrayType = new Upgrade[0][0];
        var objectType = new Upgrade[0];
        Type type = new TypeToken<Tower>() {
        }.getType();

        // opens a file as read-only
        FileReader fr = null;
        try {
            fr = new FileReader(
                    new File(GameContainer.ABSOLUTE_PATH + GameContainer.SRC_RELATIVE_DATA_PATH + "upgrades.json"));
        } catch (IOException e) {
            fr = new FileReader(
                    new File(GameContainer.ABSOLUTE_PATH + GameContainer.PROD_RELATIVE_DATA_PATH + "upgrades.json"));
        }
        // opens a read stream from the file reader
        var br = new BufferedReader(fr);
        // gson object
        var gson = new GsonBuilder().setPrettyPrinting().serializeNulls().enableComplexMapKeySerialization().create();
        // reads entire file contents as a 'mapType'
        JsonElement element = gson.toJsonTree(new LinkedHashMap<>(gson.fromJson(br, mapType)));
        // converts the element to an object
        var object = element.getAsJsonObject();
        // parses trough the entries read and saves them in the respective types while
        // building the method return value
        for (Entry<String, JsonElement> e : object.entrySet()) {
            var array = e.getValue().getAsJsonArray();
            ArrayList<Upgrade[][]> currUpgradePaths = new ArrayList<>();
            for (var i = 0; i < array.size(); i++) {
                var insideArray = e.getValue().getAsJsonArray();
                ArrayList<Upgrade[]> currUpgradeTiers = new ArrayList<>();
                for (var j = 0; j < insideArray.size(); j++) {
                    var objectsArray = insideArray.get(j).getAsJsonArray();
                    ArrayList<Upgrade> objects = new ArrayList<>();
                    for (var k = 0; k < objectsArray.size(); k++) {
                        objects.add(new Upgrade(gson.fromJson(objectsArray.get(k), type)));
                    }
                    currUpgradeTiers.add(objects.toArray(objectType));
                }
                currUpgradePaths.add(currUpgradeTiers.toArray(arrayType));
            }
            defaultUpgrades.put(e.getKey(), currUpgradePaths.get(0));
        }
        br.close();
        fr.close();
        // Default upgrades saved in memory!
        return defaultUpgrades;
    }

    /**
     * Gets the tower name this upgrade applies to.
     *
     * @return a string representing the tower name this upgrade applies to
     */
    public String getTowerName() {
        return this.towerName;
    }

    /**
     * Sets the tower name this upgrade applies to.
     *
     * @param towerName the tower name this upgrade applies to
     */
    public void setTowerName(String towerName) {
        this.towerName = towerName;
    }

    /**
     * Gets the upgrade cost.
     *
     * @return an int representing the cost of this upgrade
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Sets the upgrade cost.
     *
     * @param baseCost the upgrade cost
     */
    public void setCost(int baseCost) {
        this.cost = baseCost;
    }

    /**
     * Gets the value of the attribute {@code isApplied}.
     *
     * @return true if this upgrade is applied to the tower it belongs to, false
     *         otherwise
     */
    public boolean isApplied() {
        return this.isApplied;
    }

    /**
     * Sets the value of the attribute {@code isApplied}.
     *
     * @param isApplied true if this upgrade is applied to the tower it belongs to,
     *                  false otherwise
     */
    public void setApplied(boolean isApplied) {
        this.isApplied = isApplied;
    }

    /**
     * Gets the value of the attribute {@code isLocked}.
     *
     * @return true if this upgrade can be applied to the tower it belongs to, false
     *         otherwise
     */
    public boolean isLocked() {
        return this.isLocked;
    }

    /**
     * Sets the value of the attribute {@code isLocked}.
     *
     * @param isLocked true if this upgrade can be applied to the tower it belongs
     *                 to, false otherwise
     */
    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    /**
     * Gets the path this upgrade is included in.
     *
     * @return an int representing the upgrade path
     */
    public int getPath() {
        return this.path;
    }

    /**
     * Sets the path this upgrade is included in.
     *
     * @param path the upgrade path
     */
    public void setPath(int path) {
        this.path = path;
    }

    /**
     * Gets the path tier this upgrade is found at.
     *
     * @return an int representing the upgrade tier
     */
    public int getTier() {
        return this.tier;
    }

    /**
     * Sets the path tier this upgrade is found at.
     *
     * @param tier the upgrade tier
     */
    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public String createString() {
        var sb = new StringBuilder();
        sb.delete(0, sb.length());
        sb.append(super.createString());
        sb.append(", tower_name=").append(this.getCost());
        sb.append(", cost=").append(this.getCost());
        sb.append(", is_locked").append(this.isLocked());
        sb.append(", path").append(this.getPath());
        sb.append(", tier").append(this.getTier());
        return sb.toString();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
