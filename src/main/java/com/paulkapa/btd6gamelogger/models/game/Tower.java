package com.paulkapa.btd6gamelogger.models.game;

import java.util.ArrayList;
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

    private static ArrayList<Tower> inUseTowers = null;
    private static LinkedHashMap<String, Tower> towers = null;
    private static boolean isTowersInit = false;

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

    public static ArrayList<Tower> getInUseTowers() {return Tower.inUseTowers;}

    public static void setInUseTowers(ArrayList<Tower> inUseTowers) {Tower.inUseTowers = inUseTowers;}

    public static void useTower(Tower tower) {
        if(Tower.inUseTowers == null) Tower.inUseTowers = new ArrayList<>();
        Tower.inUseTowers.add(tower);
    }

    public static LinkedHashMap<String, Tower> getTowers() {
        if(!Tower.isTowersInit) Tower.initTowersMap();
        return Tower.towers;
    }

    public static ArrayList<String> getTowersNameList() throws Exception {
        ArrayList<String> result = new ArrayList<>();
		if(!Tower.isTowersInit) Tower.initTowersMap();
        Tower.towers.forEach((n, t) -> {result.add(n);});
        if(result.size() == 0) throw new Exception(
			"No result matching the criteria!",
			new Throwable("No tower found with a not-null name.", new Throwable()));
        return result;
    }

    public static Tower getTowerByName(String towerName) throws Exception {
		ArrayList<Tower> result = new ArrayList<>();
        if(!Tower.isTowersInit) Tower.initTowersMap();
		Tower.towers.forEach((n, t) -> {if(n.equals(towerName)) result.add(0, t);});
		if(result.size() == 0) throw new Exception(
			"No result matching the criteria!",
			new Throwable("No tower found with name '" + towerName + "'.", new Throwable()));
		return result.get(0);
    }

    public static ArrayList<Tower> getTowersByType(String towerType) throws Exception {
        ArrayList<Tower> result = new ArrayList<>();
		if(!Tower.isTowersInit) Tower.initTowersMap();
        Tower.towers.forEach((n, t) -> {if(t.getType().equals(towerType)) result.add(t);});
		if(result.size() == 0) throw new Exception(
			"No result matching the criteria!",
			new Throwable("No towers found for tower with type '" + towerType + "'.", new Throwable()));
		return result;
    }

    public static ArrayList<Tower> getTowersByCost(int min, int max) throws Exception {
        ArrayList<Tower> result = new ArrayList<>();
		if(!Tower.isTowersInit) Tower.initTowersMap();
		Tower.towers.forEach((n, t) -> {if(t.getCost() >= min && t.getCost() <= max) result.add(t);});
		if(result.size() == 0) throw new Exception(
			"No result matching the criteria!",
			new Throwable("No tower found with cost in range ['" + min + "','" +  max + "'].", new Throwable()));
		return result;
    }

    public static Tower getTowerWithHighestSellValue() throws Exception {
        ArrayList<Tower> result = new ArrayList<>();
        result.add(0, null);
        result.add(1, new Tower(null, null, 0, 0, 0l, 0));
		if(!Tower.isTowersInit) Tower.initTowersMap();
        Tower.towers.forEach((n, t) -> {if(result.get(1).getSellValue() < t.getSellValue()) {result.get(1).setSellValue(t.getSellValue()); result.add(0, t);}});
        if(result.get(0) == null) throw new Exception(
			"No result matching the criteria!",
			new Throwable("No tower found with sell value greater than 0.", new Throwable()));
        return result.get(0);
    }

    public static Tower getTowerWithMostPops() throws Exception {
        ArrayList<Tower> result = new ArrayList<>();
        result.add(0, null);
        result.add(1, new Tower(null, null, 0, 0, 0l, 0));
		if(!Tower.isTowersInit) Tower.initTowersMap();
        Tower.towers.forEach((n, t) -> {if(result.get(1).getPops() < t.getPops()) {result.get(1).setPops(t.getPops()); result.add(0, t);}});
        if(result.get(0) == null) throw new Exception(
			"No result matching the criteria!",
			new Throwable("No tower found with pops greater than 0.", new Throwable()));
        return result.get(0);
    }

    public static Tower getTowerWithMostCashGenerated() throws Exception {
        ArrayList<Tower> result = new ArrayList<>();
        result.add(0, null);
        result.add(1, new Tower(null, null, 0, 0, 0l, 0));
		if(!Tower.isTowersInit) Tower.initTowersMap();
        Tower.towers.forEach((n, t) -> {if(result.get(1).getCashGenerated() < t.getCashGenerated()) {result.get(1).setCashGenerated(t.getCashGenerated()); result.add(0, t);}});
        if(result.get(0) == null) throw new Exception(
			"No result matching the criteria!",
			new Throwable("No tower found with cash generated greater than 0.", new Throwable()));
        return result.get(0);
    }

    private static void initTowersMap() {
        Tower.towers = new LinkedHashMap<>();
        //Primary
        towers.put("Dart Monkey", new Tower("Dart Monkey", "Primary", 200));
        towers.put("Boomerang Monkey", new Tower("Boomerang Monkey", "Primary", 325));
        towers.put("Bomb Shooter", new Tower("Bomb Shooter", "Primary", 600));
        towers.put("Tack Shooter", new Tower("Tack Shooter", "Primary", 280));
        towers.put("Ice Monkey", new Tower("Ice Monkey", "Primary", 500));
        towers.put("Glue Gunner", new Tower("Glue Gunner", "Primary", 275));
        //Military
        towers.put("Sniper Monkey", new Tower("Sniper Monkey", "Military", 350));
        towers.put("Monkey Sub", new Tower("Monkey Sub", "Military", 325));
        towers.put("Monkey Buccaneer", new Tower("Monkey Buccaneer", "Military", 500));
        towers.put("Monkey Ace", new Tower("Monkey Ace", "Military", 800));
        towers.put("Heli Pilot", new Tower("Heli Pilot", "Military", 1600));
        towers.put("Mortar Monkey", new Tower("Mortar Monkey", "Military", 750));
        towers.put("Dartling Gunner", new Tower("Dartling Gunner", "Military", 850));
        //Magic
        towers.put("Wizard Monkey", new Tower("Wizard Monkey", "Magic", 400));
        towers.put("Super Monkey", new Tower("Super Monkey", "Magic", 2500));
        towers.put("Ninja Monkey", new Tower("Ninja Monkey", "Magic", 500));
        towers.put("Alchemist", new Tower("Alchemist", "Magic", 550));
        towers.put("Druid", new Tower("Druid", "Magic", 425));
        //Support
        towers.put("Banana Farm", new Tower("Banana Farm", "Support", 1250));
        towers.put("Spike Factory", new Tower("Spike Factory", "Support", 1000));
        towers.put("Monkey Village", new Tower("Monkey Village", "Support", 1200));
        towers.put("Engineer Monkey", new Tower("Engineer Monkey", "Support", 450));
        //Heroes
        towers.put("Quincy", new Tower("Quincy", "Hero", 540));
        towers.put("Gwendolin", new Tower("Gwendolin", "Hero", 900));
        towers.put("Striker Jones", new Tower("Striker Jones", "Hero", 750));
        towers.put("Obyn Greenfoot", new Tower("Obyn Greenfoot", "Hero", 650));
        towers.put("Captain Churchill", new Tower("Captain Churchill", "Hero", 2000));
        towers.put("Benjamin", new Tower("Benjamin", "Hero", 1200));
        towers.put("Ezili", new Tower("Ezili", "Hero", 600));
        towers.put("Pat Fusty", new Tower("Pat Fusty", "Hero", 800));
        towers.put("Adora", new Tower("Adora", "Hero", 1000));
        towers.put("Admiral Brickell", new Tower("Admiral Brickell", "Hero", 750));
        towers.put("Etienne", new Tower("Etienne", "Hero", 850));
        towers.put("Sauda", new Tower("Sauda", "Hero", 600));
        towers.put("Psi", new Tower("Psi", "Hero", 800));
        Tower.isTowersInit = true;
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
