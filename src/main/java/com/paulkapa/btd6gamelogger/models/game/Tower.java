package com.paulkapa.btd6gamelogger.models.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
 * <b>Class that defines the properties of a Tower</b>
 * <p>
 * Provides static methods to query towers.
 *
 * @see BaseEntity
 */
public class Tower extends BaseEntity {

    /**
     * Cost of the tower
     */
    private int cost;
    /**
     * The sell value of the tower
     */
    private int sellValue;
    /**
     * The pops made by this tower
     */
    private long pops;
    /**
     * The cash this tower generated
     */
    private int cashGenerated;

    /**
     * Default constructor.
     */
    public Tower() {
        super();
        this.cost = 0;
        this.sellValue = 0;
        this.pops = 0l;
        this.cashGenerated = 0;
    }

    /**
     * Preferred constructor.
     *
     * @param name tower name
     * @param type tower type
     * @param cost tower cost
     */
    public Tower(String name, String type, int cost) {
        super(name, type);
        this.cost = cost;
        this.sellValue = (int) Math.ceil((double) cost * 0.7d);
        this.pops = 0l;
        this.cashGenerated = 0;
    }

    /**
     * Complete constructor.
     *
     * @param name          tower name
     * @param type          tower type
     * @param cost          tower cost
     * @param sellValue     tower sell value
     * @param pops          tower pops
     * @param cashGenerated cash the tower generated
     */
    public Tower(String name, String type, int cost, int sellValue, long pops, int cashGenerated) {
        super(name, type);
        this.cost = cost;
        this.sellValue = sellValue;
        this.pops = pops;
        this.cashGenerated = cashGenerated;
    }

    /**
     * Copy constructor.
     *
     * @param other the tower to copy data from
     */
    public Tower(Tower other) {
        super(other.getInstance());
        this.cost = other.getCost();
        this.sellValue = other.getSellValue();
        this.pops = other.getPops();
        this.cashGenerated = other.getCashGenerated();
    }

    /**
     * Initializes default towers from storage if not previously initialized.
     *
     * @return a list containing the default towers.
     * @throws IOException if towers cannot be found in storage
     */
    public static LinkedHashMap<String, Tower[]> getDefaultTowers() throws IOException {
        if (!GameContainer.isInitDefaultTowers) {
            GameContainer.isInitDefaultTowers = true;
            return Tower.initDefaultTowers();
        }
        return null;
    }

    /**
     * Return the list of all tower names found in the towers list provided.
     *
     * @param towersSearch a list containing the towers to search for
     * @return a list containing all the tower names found
     * @throws Exception if the list provided as search location is null or empty,
     *                   or does not comply to the search
     */
    public static ArrayList<String> getTowersNames(LinkedHashMap<String, Tower[]> towersSearch) throws Exception {
        ArrayList<String> result = new ArrayList<>();
        if (towersSearch == null)
            throw new Exception("Cannot search in empty map!", new Throwable("Provided towersSearch is null."));
        towersSearch.forEach((n, t) -> {
            for (int i = 0; i < t.length; i++)
                result.add(t[i].getName());
        });
        if (result.isEmpty())
            throw new Exception("No result matching the criteria!",
                    new Throwable("No tower found with not null name in the provided map."));
        return result;
    }

    /**
     * Return the tower found in the towers list provided that has a specific name.
     *
     * @param towerName    the name of the tower to search for
     * @param towersSearch a list containing the towers to search for
     * @return a tower with the requested name
     * @throws Exception if the list provided as search location is null or empty,
     *                   or if no tower was found with the specified tower name
     */
    public static Tower getTowerByName(String towerName, LinkedHashMap<String, Tower[]> towersSearch) throws Exception {
        ArrayList<Tower> result = new ArrayList<>(0);
        result.add(0, null);
        if (towersSearch == null)
            throw new Exception("Cannot search in empty map!", new Throwable("Provided towersSearch is null."));
        towersSearch.forEach((n, t) -> {
            for (int i = 0; i < t.length; i++)
                if (result.get(0) == null && t[i].getName().equals(towerName)) {
                    result.add(0, t[i]);
                    break;
                }
        });
        if (result.get(0) == null)
            throw new Exception("No result matching the criteria!",
                    new Throwable("No tower found with name '" + towerName + "'."));
        return result.get(0);
    }

    /**
     * Return a list of towers found in the towers list provided that have a
     * specific type.
     *
     * @param towerType    the type of the towers to search for
     * @param towersSearch a list containing the towers to search for
     * @return a list containing towers with the requested type
     * @throws Exception if the list provided as search location is null or empty,
     *                   or if no tower was found with the specified type
     */
    public static ArrayList<Tower> getTowersByType(String towerType, LinkedHashMap<String, Tower[]> towersSearch)
            throws Exception {
        ArrayList<Tower> result = new ArrayList<>(0);
        result.add(0, null);
        if (towersSearch == null)
            throw new Exception("Cannot search in empty map!", new Throwable("Provided towersSearch is null."));
        towersSearch.forEach((n, t) -> {
            if (result.isEmpty() && n.equals(towerType))
                result.addAll(Arrays.asList(t));
        });
        if (result.isEmpty())
            throw new Exception("No result matching the criteria!",
                    new Throwable("No towers with type '" + towerType + "' were found."));
        return result;
    }

    /**
     * Return a list of towers found in the towers list provided that are included
     * in a specific cost range.
     *
     * @param min          min cost to search for
     * @param max          max cost to search for
     * @param towersSearch a list containing the towers to search for
     * @return a list of towers that are included in the specified cost range
     * @throws Exception if the list provided as search location is null or empty,
     *                   or if no tower was found within the specified cost range
     */
    public static ArrayList<Tower> getTowersByCost(int min, int max, LinkedHashMap<String, Tower[]> towersSearch)
            throws Exception {
        ArrayList<Tower> result = new ArrayList<>(0);
        if (towersSearch == null)
            throw new Exception("Cannot search in empty map!", new Throwable("Provided towersSearch is null."));
        towersSearch.forEach((n, t) -> {
            for (int i = 0; i < t.length; i++)
                if (t[i].getCost() >= min && t[i].getCost() <= max)
                    result.add(t[i]);
        });
        if (result.isEmpty())
            throw new Exception("No result matching the criteria!",
                    new Throwable("No tower found with cost in range ['" + min + "','" + max + "']."));
        return result;
    }

    /**
     * Return a tower found in the towers list provided that has the highest sell
     * value.
     *
     * @param towersSearch a list containing the towers to search for
     * @return the tower with highest sell value found in the provided list
     * @throws Exception if the list provided as search location is null or empty,
     *                   or if no tower has the sell value greater than 0
     */
    public static Tower getTowerWithHighestSellValue(LinkedHashMap<String, Tower[]> towersSearch) throws Exception {
        ArrayList<Tower> result = new ArrayList<>();
        result.add(0, null);
        result.add(1, new Tower(null, null, 0, 0, 0l, 0));
        if (towersSearch == null)
            throw new Exception("Cannot search in empty map!", new Throwable("Provided towersSearch is null."));
        towersSearch.forEach((n, t) -> {
            for (int i = 0; i < t.length; i++)
                if (result.get(1).getSellValue() < t[i].getSellValue()) {
                    result.get(1).setSellValue(t[i].getSellValue());
                    result.add(0, t[i]);
                }
        });
        if (result.get(0) == null)
            throw new Exception("No result matching the criteria!",
                    new Throwable("No tower found with sell value greater than 0."));
        return result.get(0);
    }

    /**
     * Return a tower found in the towers list provided that has the most pops made.
     *
     * @param towersSearch a list containing the towers to search for
     * @return the tower with the most pops made found in the provided list
     * @throws Exception if the list provided as search location is null or empty,
     *                   or if no tower has the pops value greater than 0
     */
    public static Tower getTowerWithMostPops(LinkedHashMap<String, Tower[]> towersSearch) throws Exception {
        ArrayList<Tower> result = new ArrayList<>();
        result.add(0, null);
        result.add(1, new Tower(null, null, 0, 0, 0l, 0));
        if (towersSearch == null)
            throw new Exception("Cannot search in empty map!", new Throwable("Provided towersSearch is null."));
        towersSearch.forEach((n, t) -> {
            for (int i = 0; i < t.length; i++)
                if (result.get(1).getPops() < t[i].getPops()) {
                    result.get(1).setPops(t[i].getPops());
                    result.add(0, t[i]);
                }
        });
        if (result.get(0) == null)
            throw new Exception("No result matching the criteria!",
                    new Throwable("No tower found with pops greater than 0."));
        return result.get(0);
    }

    /**
     * Return a tower found in the towers list provided that has the cash generated.
     *
     * @param towersSearch a list containing the towers to search for
     * @return the tower with the most cash generated found in the provided list
     * @throws Exception if the list provided as search location is null or empty,
     *                   or if no tower has the cashGenerated value greater than 0
     */
    public static Tower getTowerWithMostCashGenerated(LinkedHashMap<String, Tower[]> towersSearch) throws Exception {
        ArrayList<Tower> result = new ArrayList<>();
        result.add(0, null);
        result.add(1, new Tower(null, null, 0, 0, 0l, 0));
        if (towersSearch == null)
            throw new Exception("Cannot search in empty map!", new Throwable("Provided towersSearch is null."));
        towersSearch.forEach((n, t) -> {
            for (int i = 0; i < t.length; i++)
                if (result.get(1).getCashGenerated() < t[i].getCashGenerated()) {
                    result.get(1).setCashGenerated(t[i].getCashGenerated());
                    result.add(0, t[i]);
                }
        });
        if (result.get(0) == null)
            throw new Exception("No result matching the criteria!",
                    new Throwable("No tower found with cash generated greater than 0."));
        return result.get(0);
    }

    /**
     * Reads the default towers from 'towers.json' file found in application local
     * storage.
     *
     * @return a list containing the default towers
     * @throws IOException if towers cannot be found in storage
     */
    private static LinkedHashMap<String, Tower[]> initDefaultTowers() throws IOException {
        LinkedHashMap<String, Tower[]> defaultTowers = new LinkedHashMap<>();
        // aux variable to apply getClass() method on
        LinkedHashMap<String, Tower[]> mapType = new LinkedHashMap<>(0);
        // aux variable to apply getClass() method on
        Tower[] arrayType = new Tower[0];
        // aux variable to apply getClass() method on
        Tower objectType = new Tower();

        // opens a file as read-only
        FileReader fr = null;
        try {
            fr = new FileReader(
                    new File(GameContainer.ABSOLUTE_PATH + GameContainer.SRC_RELATIVE_DATA_PATH + "towers.json"));
        } catch (IOException e) {
            fr = new FileReader(
                    new File(GameContainer.ABSOLUTE_PATH + GameContainer.PROD_RELATIVE_DATA_PATH + "towers.json"));
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
            ArrayList<Tower> currTowers = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                currTowers.add(new Tower(gson.fromJson(array.get(i), objectType.getClass())));
            }
            defaultTowers.put(e.getKey(), currTowers.toArray(arrayType));
        }
        br.close();
        fr.close();
        // Default towers saved in memory!
        return defaultTowers;
    }

    /**
     * Gets the tower cost.
     *
     * @return an int representing the tower cost
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Sets the tower cost.
     *
     * @param cost the tower cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Gets the tower sell value.
     *
     * @return an int representing the tower sell value
     */
    public int getSellValue() {
        return this.sellValue;
    }

    /**
     * Sets the tower sell value.
     *
     * @param sellValue the tower sell value
     */
    public void setSellValue(int sellValue) {
        this.sellValue = sellValue;
    }

    /**
     * Gets the pops made by the tower.
     *
     * @return a long representing the pops made by the tower
     */
    public long getPops() {
        return this.pops;
    }

    /**
     * Sets the pops made by the tower.
     *
     * @param pops the pops made by the tower
     */
    public void setPops(long pops) {
        this.pops = pops;
    }

    /**
     * Gets the cash generated by the tower.
     *
     * @return an int representing the cash genereated by the tower
     */
    public int getCashGenerated() {
        return this.cashGenerated;
    }

    /**
     * Sets the cash generated by the tower.
     *
     * @param cashGenerated the cash generated by the tower
     */
    public void setCashGenerated(int cashGenerated) {
        this.cashGenerated = cashGenerated;
    }

    @Override
    public String createString() {
        StringBuilder sb = new StringBuilder();
        sb.delete(0, sb.length());
        sb.append(super.createString());
        sb.append(", cost=").append(this.getCost());
        sb.append(", sell_value=").append(this.getSellValue());
        sb.append(", pops=").append(this.getPops());
        sb.append(", cash_generated=").append(this.getCashGenerated());
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
        result = prime * result + cashGenerated;
        result = prime * result + cost;
        result = prime * result + (int) (pops ^ (pops >>> 32));
        result = prime * result + sellValue;
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
        Tower other = (Tower) obj;
        if (cashGenerated != other.cashGenerated)
            return false;
        if (cost != other.cost)
            return false;
        if (pops != other.pops)
            return false;
        if (sellValue != other.sellValue)
            return false;
        return true;
    }
}
