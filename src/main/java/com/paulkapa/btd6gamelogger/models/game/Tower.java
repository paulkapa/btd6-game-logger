package com.paulkapa.btd6gamelogger.models.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import com.google.gson.Gson;
import com.paulkapa.btd6gamelogger.models.BaseEntity;

/**
 * <h4>Class that defines the properties of a Tower</h4>
 *
 * Provides static storage for all towers.
 *
 * @see BaseEntity
 */
public class Tower extends BaseEntity {

    private static LinkedHashMap<String, Tower[]> defaultTowers = null;
    private static boolean isInitDefaultTowers = false;

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
     * @param name
     * @param type
     * @param cost
     */
    public Tower(String name, String type, int cost) {
        super(name, type);
        this.cost = cost;
        this.sellValue = 0;
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
     * @param other
     */
    public Tower(Tower other) {
        super(other.getInstance());
        this.cost = other.getCost();
        this.sellValue = other.getSellValue();
        this.pops = other.getPops();
        this.cashGenerated = other.getCashGenerated();
    }

    public static LinkedHashMap<String, Tower[]> getDefaultTowers() {
        if(!Tower.isInitDefaultTowers) Tower.initDefaultTowers();
        return Tower.defaultTowers;
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

    private static void initDefaultTowers() {
        Tower.defaultTowers = new LinkedHashMap<>();
        ArrayList<Tower> thisTowers = new ArrayList<>(0);
        Tower[] type = new Tower[0];
        // Primary
        thisTowers.clear();
        thisTowers.add(new Tower("Dart Monkey", "Primary", 200));
        thisTowers.add(new Tower("Boomerang Monkey", "Primary", 325));
        thisTowers.add(new Tower("Bomb Shooter", "Primary", 600));
        thisTowers.add(new Tower("Tack Shooter", "Primary", 280));
        thisTowers.add(new Tower("Ice Monkey", "Primary", 500));
        thisTowers.add(new Tower("Glue Gunner", "Primary", 275));
        defaultTowers.put("Primary", thisTowers.toArray(type));
        // Military
        thisTowers.clear();
        thisTowers.add(new Tower("Sniper Monkey", "Military", 350));
        thisTowers.add(new Tower("Monkey Sub", "Military", 325));
        thisTowers.add(new Tower("Monkey Buccaneer", "Military", 500));
        thisTowers.add(new Tower("Monkey Ace", "Military", 800));
        thisTowers.add(new Tower("Heli Pilot", "Military", 1600));
        thisTowers.add(new Tower("Mortar Monkey", "Military", 750));
        thisTowers.add(new Tower("Dartling Gunner", "Military", 850));
        defaultTowers.put("Military", thisTowers.toArray(type));
        // Magic
        thisTowers.clear();
        thisTowers.add(new Tower("Wizard Monkey", "Magic", 400));
        thisTowers.add(new Tower("Super Monkey", "Magic", 2500));
        thisTowers.add(new Tower("Ninja Monkey", "Magic", 500));
        thisTowers.add(new Tower("Alchemist", "Magic", 550));
        thisTowers.add(new Tower("Druid", "Magic", 425));
        defaultTowers.put("Magic", thisTowers.toArray(type));
        // Support
        thisTowers.clear();
        thisTowers.add(new Tower("Banana Farm", "Support", 1250));
        thisTowers.add(new Tower("Spike Factory", "Support", 1000));
        thisTowers.add(new Tower("Monkey Village", "Support", 1200));
        thisTowers.add(new Tower("Engineer Monkey", "Support", 450));
        defaultTowers.put("Support", thisTowers.toArray(type));
        // Heroes
        thisTowers.clear();
        thisTowers.add(new Tower("Quincy", "Hero", 540));
        thisTowers.add(new Tower("Gwendolin", "Hero", 900));
        thisTowers.add(new Tower("Striker Jones", "Hero", 750));
        thisTowers.add(new Tower("Obyn Greenfoot", "Hero", 650));
        thisTowers.add(new Tower("Captain Churchill", "Hero", 2000));
        thisTowers.add(new Tower("Benjamin", "Hero", 1200));
        thisTowers.add(new Tower("Ezili", "Hero", 600));
        thisTowers.add(new Tower("Pat Fusty", "Hero", 800));
        thisTowers.add(new Tower("Adora", "Hero", 1000));
        thisTowers.add(new Tower("Admiral Brickell", "Hero", 750));
        thisTowers.add(new Tower("Etienne", "Hero", 850));
        thisTowers.add(new Tower("Sauda", "Hero", 600));
        thisTowers.add(new Tower("Psi", "Hero", 800));
        defaultTowers.put("Heroes", thisTowers.toArray(type));
        // Default towers saved in memory!
        Tower.isInitDefaultTowers = true;
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
