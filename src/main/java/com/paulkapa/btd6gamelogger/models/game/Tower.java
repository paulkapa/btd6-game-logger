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
 * <h4>Class that defines the properties of a Tower</h4>
 *
 * Provides static methods to query towers.
 *
 * @see BaseEntity
 */
public class Tower extends BaseEntity {

    private int cost;
    private int sellValue;
    private long pops;
    private int cashGenerated;

    public Tower() {
        super();
        this.cost = 0;
        this.sellValue = 0;
        this.pops = 0l;
        this.cashGenerated = 0;
    }

    /**
     * Preferred constructor.
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

    public Tower(String name, String type, int cost, int sellValue, long pops, int cashGenerated) {
        super(name, type);
        this.cost = cost;
        this.sellValue = sellValue;
        this.pops = pops;
        this.cashGenerated = cashGenerated;
    }

    /**
     * Copy constructor.
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
     * @return a {@code LinkedHashMap<String, Tower[]>} with the default towers.
     * @throws IOException if towers cannot be found in storage
     */
    public static LinkedHashMap<String, Tower[]> getDefaultTowers() throws IOException {
        if(!GameContainer.isInitDefaultTowers) {GameContainer.isInitDefaultTowers = true; return Tower.initDefaultTowers();}
        return null;
    }

    public static ArrayList<String> getTowersNames(LinkedHashMap<String, Tower[]> towersSearch) throws Exception {
        ArrayList<String> result = new ArrayList<>();
        if(towersSearch == null) throw new Exception(
            "Cannot search in empty map!",
            new Throwable("Provided towersSearch is null."));
        towersSearch.forEach((n, t) -> {for(int i=0; i<t.length; i++) result.add(t[i].getName());});
        if(result.size() == 0) throw new Exception(
            "No result matching the criteria!",
            new Throwable("No tower found with not null name in the provided map."));
        return result;
    }

    public static Tower getTowerByName(String towerName, LinkedHashMap<String, Tower[]> towersSearch) throws Exception {
        ArrayList<Tower> result = new ArrayList<>(0);
        result.add(0, null);
        if(towersSearch == null) throw new Exception(
            "Cannot search in empty map!",
            new Throwable("Provided towersSearch is null."));
        towersSearch.forEach((n, t) -> {for(int i=0; i<t.length; i++)
            if(result.get(0) == null && t[i].getName().equals(towerName)) {result.add(0, t[i]); break;}});
        if(result.get(0) == null) throw new Exception(
            "No result matching the criteria!",
            new Throwable("No tower found with name '" + towerName + "'."));
        return result.get(0);
    }

    public static ArrayList<Tower> getTowersByType(String towerType, LinkedHashMap<String, Tower[]> towersSearch) throws Exception {
        ArrayList<Tower> result = new ArrayList<>(0);
        result.add(0, null);
        if(towersSearch == null) throw new Exception(
            "Cannot search in empty map!",
            new Throwable("Provided towersSearch is null."));
        towersSearch.forEach((n, t) -> {if(result.size() == 0 && n.equals(towerType)) result.addAll(Arrays.asList(t));});
        if(result.size() == 0) throw new Exception(
            "No result matching the criteria!",
            new Throwable("No towers with type '" + towerType + "' were found."));
        return result;
    }

    public static ArrayList<Tower> getTowersByCost(int min, int max, LinkedHashMap<String, Tower[]> towersSearch) throws Exception {
        ArrayList<Tower> result = new ArrayList<>(0);
        if(towersSearch == null) throw new Exception(
            "Cannot search in empty map!",
            new Throwable("Provided towersSearch is null."));
        towersSearch.forEach((n, t) -> {for(int i=0; i<t.length; i++)
            if(t[i].getCost() >= min && t[i].getCost() <= max) result.add(t[i]);});
        if(result.size() == 0) throw new Exception(
            "No result matching the criteria!",
            new Throwable("No tower found with cost in range ['" + min + "','" +  max + "']."));
        return result;
    }

    public static Tower getTowerWithHighestSellValue(LinkedHashMap<String, Tower[]> towersSearch) throws Exception {
        ArrayList<Tower> result = new ArrayList<>();
        result.add(0, null);
        result.add(1, new Tower(null, null, 0, 0, 0l, 0));
        if(towersSearch == null) throw new Exception(
            "Cannot search in empty map!",
            new Throwable("Provided towersSearch is null."));
        towersSearch.forEach((n, t) -> {for(int i=0; i<t.length; i++)
            if(result.get(1).getSellValue() < t[i].getSellValue()) {result.get(1).setSellValue(t[i].getSellValue()); result.add(0, t[i]);}});
        if(result.get(0) == null) throw new Exception(
            "No result matching the criteria!",
            new Throwable("No tower found with sell value greater than 0."));
        return result.get(0);
    }

    public static Tower getTowerWithMostPops(LinkedHashMap<String, Tower[]> towersSearch) throws Exception {
        ArrayList<Tower> result = new ArrayList<>();
        result.add(0, null);
        result.add(1, new Tower(null, null, 0, 0, 0l, 0));
        if(towersSearch == null) throw new Exception(
            "Cannot search in empty map!",
            new Throwable("Provided towersSearch is null."));
        towersSearch.forEach((n, t) -> {for(int i=0; i<t.length; i++)
            if(result.get(1).getPops() < t[i].getPops()) {result.get(1).setPops(t[i].getPops()); result.add(0, t[i]);}});
        if(result.get(0) == null) throw new Exception(
            "No result matching the criteria!",
            new Throwable("No tower found with pops greater than 0."));
        return result.get(0);
    }

    public static Tower getTowerWithMostCashGenerated(LinkedHashMap<String, Tower[]> towersSearch) throws Exception {
        ArrayList<Tower> result = new ArrayList<>();
        result.add(0, null);
        result.add(1, new Tower(null, null, 0, 0, 0l, 0));
        if(towersSearch == null) throw new Exception(
            "Cannot search in empty map!",
            new Throwable("Provided towersSearch is null."));
        towersSearch.forEach((n, t) -> {for(int i=0; i<t.length; i++)
            if(result.get(1).getCashGenerated() < t[i].getCashGenerated()) {result.get(1).setCashGenerated(t[i].getCashGenerated()); result.add(0, t[i]);}});
        if(result.get(0) == null) throw new Exception(
            "No result matching the criteria!",
            new Throwable("No tower found with cash generated greater than 0."));
        return result.get(0);
    }

    private static LinkedHashMap<String, Tower[]> initDefaultTowers() throws IOException {
        LinkedHashMap<String, Tower[]> defaultTowers = new LinkedHashMap<>();
        // aux variable to apply getClass() method on
        LinkedHashMap<String, Tower[]> mapType = new LinkedHashMap<>(0);
        // aux variable to apply getClass() method on
        Tower[] arrayType = new Tower[0];
        // aux variable to apply getClass() method on
        Tower objectType = new Tower();

        // opens a file as read-only
        FileReader fr = new FileReader(new File(GameContainer.ABSOLUTE_PATH + GameContainer.RELATIVE_DATA_PATH + "towers.json"));
        // opens a read stream from the file reader
        BufferedReader br = new BufferedReader(fr);
        // gson object
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().enableComplexMapKeySerialization().create();
        // reads entire file contents as a 'mapType'
        JsonElement element = gson.toJsonTree(new LinkedHashMap<>(gson.fromJson(br, mapType.getClass())));
        // converts the element to an object
        JsonObject object = element.getAsJsonObject();
        // parses trough the entries read and saves them in the respective types while building the method return value
        for(Entry<String, JsonElement> e : object.entrySet()) {
            JsonArray array = e.getValue().getAsJsonArray();
            ArrayList<Tower> currTowers = new ArrayList<>();
            for(int i = 0; i < array.size(); i++) {
                currTowers.add(new Tower(gson.fromJson(array.get(i), objectType.getClass())));
            }
            defaultTowers.put(e.getKey(), currTowers.toArray(arrayType));
        }
        br.close();
        fr.close();
        // Default towers saved in memory!
        return defaultTowers;
    }

    public int getCost() {return this.cost;}

    public void setCost(int cost) {this.cost = cost;}

    public int getSellValue() {return this.sellValue;}

    public void setSellValue(int sellValue) {this.sellValue = sellValue;}

    public long getPops() {return this.pops;}

    public void setPops(long pops) {this.pops = pops;}

    public int getCashGenerated() {return this.cashGenerated;}

    public void setCashGenerated(int cashGenerated) {this.cashGenerated = cashGenerated;}

    @Override
    public String createString() {
        StringBuffer sb = new StringBuffer();
        sb.delete(0, sb.length());
        sb.append(super.createString());
        sb.append(", cost=").append(this.getCost());
        sb.append(", sell_value=").append(this.getSellValue());
        sb.append(", pops=").append(this.getPops());
        sb.append(", cash_generated=").append(this.getCashGenerated());
        return sb.toString();
    }

    @Override
    public String toString() {return new Gson().toJson(this);}

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
