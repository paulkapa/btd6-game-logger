package com.paulkapa.btd6gamelogger.models.game;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.paulkapa.btd6gamelogger.models.base.BaseEntity;

/**
 * Class that defines the properties of a Tower in BTD6.
 * <p>
 * Provides static storage for all towers.
 * @see BaseEntity
 */
public class Tower extends BaseEntity {

    private StringBuffer sb = new StringBuffer();

    private static ArrayList<Tower> inUseTowers = null;
    private static LinkedHashMap<String, Tower> towers = null;

    private int cost;
    private int sellValue;
    private long pops;
    private int cashGenerated;

    /**
     * Default constructor.
     */
    public Tower() {
        super(null, null);
        this.cost = 0;
        this.sellValue = 0;
        this.pops = 0l;
        this.cashGenerated = 0;
        try {
            if(Tower.towers == null) {
                if(!Tower.initTowersMap()) {
                    throw new Exception("Could not initialize towers map!");
                }
            }
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Preferred constructor.
     * @param name
     * @param type
     * @param cost
     */
    public Tower(String type, String name, int cost) {
        super(name, type);
        this.cost = cost;
        try {
            if(Tower.towers == null) {
                if(!Tower.initTowersMap()) {
                    throw new Exception("Could not initialize towers map!");
                }
            }
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Complete constructor.
     * @param name
     * @param type
     * @param cost
     * @param sellValue
     * @param pops
     * @param cashGenerated
     */
    public Tower(String name, String type, int cost, int sellValue, long pops, int cashGenerated) {
        super(name, type);
        this.cost = cost;
        this.sellValue = sellValue;
        this.pops = pops;
        this.cashGenerated = cashGenerated;
        try {
            if(Tower.towers == null) {
                if(!Tower.initTowersMap()) {
                    throw new Exception("Could not initialize towers map!");
                }
            }
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Copy constructor.
     * @param other
     */
    public Tower(Tower other) {
        super(other.getName(), other.getType());
        this.cashGenerated = other.getCashGenerated();
        this.cost = other.getCost();
        this.pops = other.getPops();
        this.sellValue = other.getSellValue();
        try {
            if(Tower.towers == null) {
                if(!Tower.initTowersMap()) {
                    throw new Exception("Could not initialize towers map!");
                }
            }
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    public static ArrayList<Tower> getInUseTowers() {
        try {
            if(Tower.inUseTowers == null) {
                throw new Exception("No towers in use!");
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        return Tower.inUseTowers;
    }

    public static void setInUseTowers(ArrayList<Tower> inUseTowers) {
        Tower.inUseTowers = inUseTowers;
    }

    public static void useTower(Tower tower) {
        if(Tower.inUseTowers == null) {
            Tower.inUseTowers = new ArrayList<>();
        }
        Tower.inUseTowers.add(tower);
    }

    public static LinkedHashMap<String, Tower> getTowers() {
        try {
            if(Tower.towers == null) {
                if(!Tower.initTowersMap()) {
                    throw new Exception("Could not reset towers map!");
                }
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        return Tower.towers;
    }

    public static ArrayList<Tower> getTowersByType(String typeSearch) {
        try {
            if(Tower.towers == null) {
                if(!Tower.initTowersMap()) {
                    throw new Exception("Could not reset towers map!");
                }
            }
            ArrayList<Tower> result = new ArrayList<>();
            for(String name : Tower.towers.keySet()) {
                Tower curr = Tower.towers.get(name);
                if(curr.getType().equals(typeSearch)) {
                    result.add(curr);
                }
            }
            if(result.size() != 0) {
                return result;
            } else {
               throw new Exception("No result matching the criteria was found!");
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        return null;
    }

    public static Tower getTowerByName(String nameSearch) {
        try {
            if(Tower.towers == null) {
                if(!Tower.initTowersMap()) {
                    throw new Exception("Could not reset towers map!");
                }
            }
            Tower result = null;
            for(String name : Tower.towers.keySet()) {
                if(name.equals(nameSearch)) {
                    if(result == null) {
                        result = Tower.towers.get(name);
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
        return null;
    }

    public static Tower getTowerWithHighestSellValue() {
        try {
            if(Tower.towers == null) {
                if(!Tower.initTowersMap()) {
                    throw new Exception("Could not reset towers map!");
                }
            }
            Tower result = null;
            int sellValue = 0;
            for(Tower t : Tower.inUseTowers) {
                if(sellValue < t.getSellValue()) {
                    sellValue = t.getSellValue();
                    result = new Tower(t);
                }
            }
            if(result != null) {
                return result;
            } else {
                throw new Exception("No result matching the criteria was found!");
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        return null;
    }

    public static Tower getTowerWithMostPops() {
        try {
            if(Tower.inUseTowers == null) {
                throw new Exception("No towers in use!");
            }
            Tower result = null;
            long pops = 0l;
            for(Tower t : Tower.inUseTowers) {
                if(pops < t.getPops()) {
                    pops = t.getPops();
                    result = new Tower(t);
                }
            }
            if(result != null) {
                return result;
            } else {
                throw new Exception("No result matching the criteria was found!");
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        return null;
    }

    public static Tower getTowerWithMostCashGenerated() {
        try {
            if(Tower.inUseTowers == null) {
                throw new Exception("No towers in use!");
            }
            Tower result = null;
            int cashGenerated = 0;
            for(Tower t : Tower.inUseTowers) {
                if(cashGenerated < t.getCashGenerated()) {
                    cashGenerated = t.getCashGenerated();
                    result = new Tower(t);
                }
            }
            if(result != null) {
                return result;
            } else {
                throw new Exception("No result matching the criteria was found!");
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        return null;
    }

    public static ArrayList<Tower> getTowersByCost(int min, int max) {
        try {
            if(Tower.towers == null) {
                if(!Tower.initTowersMap()) {
                    throw new Exception("Could not reset towers map!");
                }
            }
            ArrayList<Tower> result = new ArrayList<>();
            for(String tower : Tower.towers.keySet()) {
                Tower curr = Tower.towers.get(tower);
                if(curr.getCost() >= min && curr.getCost() <= max) {
                    result.add(curr);
                }
            }
            if(result.size() != 0) {
                return result;
            } else {
                throw new Exception("No result matching the criteria was found!");
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        return null;
    }

    public static ArrayList<String> getTowersNameList() {
        try {
            if(Tower.towers == null) {
                if(!Tower.initTowersMap()) {
                    throw new Exception("Could not reset towers map!");
                }
            }
            ArrayList<String> result = new ArrayList<>();
            for(String tower : Tower.towers.keySet()) {
                result.add(tower);
            }
            if(result.size() != 0) {
                return result;
            } else {
                throw new Exception("No result matching the criteria was found!");
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        return null;
    }

    /**
     * Use with <code>null</code> parameter to set the <code>towersMap</code> to the default state.
     * @param towersMap
     */
    public static void setTowers(LinkedHashMap<String, Tower> towersMap) {
        try {
            if(towersMap != null) {
                Tower.towers = towersMap;
            } else {
                if(!Tower.initTowersMap()) {
                    throw new Exception("Could not reset towers map!");
                }
            }
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    private static boolean initTowersMap() {
        try {
            Tower.towers = new LinkedHashMap<>();
            //Primary
            towers.put("Dart Monkey", new Tower("Primary", "Dart Monkey", 200));
            towers.put("Boomerang Monkey", new Tower("Primary", "Boomerang Monkey", 325));
            towers.put("Bomb Shooter", new Tower("Primary", "Bomb Shooter", 600));
            towers.put("Tack Shooter", new Tower("Primary", "Tack Shooter", 280));
            towers.put("Ice Monkey", new Tower("Primary", "Ice Monkey", 500));
            towers.put("Glue Gunner", new Tower("Primary", "Glue Gunner", 275));
            //Military
            towers.put("Sniper Monkey", new Tower("Military", "Sniper Monkey", 350));
            towers.put("Monkey Sub", new Tower("Military", "Monkey Sub", 325));
            towers.put("Monkey Buccaneer", new Tower("Military", "Monkey Buccaneer", 500));
            towers.put("Monkey Ace", new Tower("Military", "Monkey Ace", 800));
            towers.put("Heli Pilot", new Tower("Military", "Heli Pilot", 1600));
            towers.put("Mortar Monkey", new Tower("Military", "Mortar Monkey", 750));
            towers.put("Dartling Gunner", new Tower("Military", "Dartling Gunner", 850));
            //Magic
            towers.put("Wizard Monkey", new Tower("Military", "Wizard Monkey", 400));
            towers.put("Super Monkey", new Tower("Military", "Super Monkey", 2500));
            towers.put("Ninja Monkey", new Tower("Military", "Ninja Monkey", 500));
            towers.put("Alchemist", new Tower("Military", "Alchemist", 550));
            towers.put("Druid", new Tower("Military", "Druid", 425));
            //Supprt
            towers.put("Banana Farm", new Tower("Military", "Banana Farm", 1250));
            towers.put("Spike Factory", new Tower("Military", "Spike Factory", 1000));
            towers.put("Monkey Village", new Tower("Military", "Monkey Village", 1200));
            towers.put("Engineer Monkey", new Tower("Military", "Engineer Monkey", 450));
            //Heroes
            towers.put("Quincy", new Tower("Hero", "Quincy", 540));
            towers.put("Gwendolin", new Tower("Hero", "Gwendolin", 900));
            towers.put("Striker Jones", new Tower("Hero", "Striker Jones", 750));
            towers.put("Obyn Greenfoot", new Tower("Hero", "Obyn Greenfoot", 650));
            towers.put("Captain Churchill", new Tower("Hero", "Captain Churchill", 2000));
            towers.put("Benjamin", new Tower("Hero", "Benjamin", 1200));
            towers.put("Ezili", new Tower("Hero", "Ezili", 600));
            towers.put("Pat Fusty", new Tower("Hero", "Pat Fusty", 800));
            towers.put("Adora", new Tower("Hero", "Adora", 1000));
            towers.put("Admiral Brickell", new Tower("Hero", "Admiral Brickell", 750));
            towers.put("Etienne", new Tower("Hero", "Etienne", 850));
            towers.put("Sauda", new Tower("Hero", "Sauda", 600));
            towers.put("Psi", new Tower("Hero", "Psi", 800));
            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getSellValue() {
        return sellValue;
    }

    public void setSellValue(int sellValue) {
        this.sellValue = sellValue;
    }

    public long getPops() {
        return pops;
    }

    public void setPops(long pops) {
        this.pops = pops;
    }

    public int getCashGenerated() {
        return cashGenerated;
    }

    public void setCashGenerated(int cashGenerated) {
        this.cashGenerated = cashGenerated;
    }

    @Override
    public String createString() {
        this.sb.delete(0, this.sb.length());
        sb.append(super.createString());
        sb.append(", cost=").append(this.getCost());
        sb.append(", sell_value=").append(this.getSellValue());
        sb.append(", pops=").append(this.getPops());
        sb.append(", cash_generated=").append(this.getCashGenerated());
        return sb.toString();
    }

    @Override
    public String toString() {
        return "{ " + this.getName() + "=[ " + this.createString() + "]}";
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
