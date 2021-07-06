package com.paulkapa.btd6gamelogger.models.game;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.paulkapa.btd6gamelogger.models.base.BaseEntity;

/**
 * Class that defines the attributes af an Upgrade Path in BTD6.
 * <p>
 * Provides static storage for all upgrades.
 * @see BaseEntity
 */
public class Upgrade extends BaseEntity {

    private StringBuffer sb = new StringBuffer();

    private static ArrayList<Upgrade> inUseUpgrades = null;
    private static LinkedHashMap<String, Upgrade[][]> upgrades = null;

    private String towerName;
    private int cost;
    private boolean isApplied;
    private boolean isLocked;
    private short path;
    private short tier;

    /**
     * Default constructor.
     */
    public Upgrade() {
        super(null, null);
        this.towerName = null;
        this.cost = 0;
        this.isApplied = true;
        this.isLocked = true;
        this.path = 0;
        this.tier = 0;
        try {
            if(Upgrade.upgrades == null) {
                if(!Upgrade.initUpgradesMap()) {
                    throw new Exception("Could not initialize upgrades map!");
                }
            }
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Preferred constructor.
     * @param type
     * @param towerName
     * @param name
     * @param cost
     */
    public Upgrade(String type, String towerName, String name, Integer cost) {
        super(name, type);
        this.towerName = towerName;
        this.cost = cost;
        try {
            if(Upgrade.upgrades == null) {
                if(!Upgrade.initUpgradesMap()) {
                    throw new Exception("Could not initialize upgrades map!");
                }
            }
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Complete constructor.
     * @param type
     * @param towerName
     * @param name
     * @param cost
     * @param isLocked
     * @param path
     * @param tier
    */
    public Upgrade(String type, String towerName, String name, Integer cost, boolean isApplied, boolean isLocked, short path, short tier) {
        super(name, type);
        this.towerName = towerName;
        this.cost = cost;
        this.isApplied = isApplied;
        this.isLocked = isLocked;
        this.path = path;
        this.tier = tier;
        try {
            if(Upgrade.upgrades == null) {
                if(!Upgrade.initUpgradesMap()) {
                    throw new Exception("Could not initialize upgrades map!");
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
    public Upgrade(Upgrade other) {
        super(other.getName(), other.getType());
        this.towerName = other.getTowerName();
        this.cost = other.getCost();
        this.isApplied = other.isApplied();
        this.isLocked = other.isLocked();
        this.path = other.getPath();
        this.tier = other.getTier();
        try {
            if(Upgrade.upgrades == null) {
                if(!Upgrade.initUpgradesMap()) {
                    throw new Exception("Could not initialize upgrades map!");
                }
            }
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    public static ArrayList<Upgrade> getInUseUpgrades() {
        try {
            if(Upgrade.inUseUpgrades == null) {
                throw new Exception("No upgrades in use!");
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        return Upgrade.inUseUpgrades;
    }

    public static void setInUseUpgrades(ArrayList<Upgrade> inUseUpgrades) {
        Upgrade.inUseUpgrades = inUseUpgrades;
    }

    public static void useUpgrade(Upgrade upgrade) {
        if(Upgrade.inUseUpgrades == null) {
            Upgrade.inUseUpgrades = new ArrayList<>();
        }
        Upgrade.inUseUpgrades.add(upgrade);
    }

    public static int countNotLockedUpgrades(ArrayList<Upgrade[][]> upgradesList) {
        int count = 0;
        if(upgradesList != null) {
            for(Upgrade[][] u : upgradesList) {
                for(int i = 0; i < u.length; i++) {
                    for(int j = 0; j < u[i].length; i++) {
                        if(!u[i][j].isLocked()) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }
    
    public static int countAppliedUpgrades(ArrayList<Upgrade[][]> upgradesList) {
        int count = 0;
        if(upgradesList != null) {
            for(Upgrade[][] u : upgradesList) {
                for(int i = 0; i < u.length; i++) {
                    for(int j = 0; j < u[i].length; i++) {
                        if(!u[i][j].isApplied()) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    public static LinkedHashMap<String, Upgrade[][]> getUpgrades() {
        try {
            if(Upgrade.upgrades == null) {
                if(!Upgrade.initUpgradesMap()) {
                    throw new Exception("Could not reset upgrades map!");
                }
            }
        } catch(Exception e) {
            System.err.println(e);
        }
        return Upgrade.upgrades;
    }

    public static Upgrade[][] getUpgradesByTowerName(String towerName) {
        try {
            if(Upgrade.upgrades == null) {
                if(!Upgrade.initUpgradesMap()) {
                    throw new Exception("Could not reset upgrades map!");
                }
            }
            return Upgrade.upgrades.get(towerName);
        } catch(Exception e) {
            System.err.println(e);
        }
        return null;
    }

    public static void setUpgrades(LinkedHashMap<String, Upgrade[][]> upgradesMap) {
        try {
            if(upgradesMap != null) {
                Upgrade.upgrades = upgradesMap;
            } else {
                if(!Upgrade.initUpgradesMap()) {
                    throw new Exception("Could not reset upgrades map!");
                }
            }
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    private static boolean initUpgradesMap() {
        try {
            Upgrade.upgrades = new LinkedHashMap<>();
            Upgrade[][] thisUpgrades;
            String[] types = {"Upgrade", "Ability"};
            ArrayList<String> names = Tower.getTowersNameList();
            //Dart Monkey
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(0), "Sharp Shots", 140);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(0), "Razor Sharp Shots", 220);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(0), "Spike-o-pult", 300);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(0), "Juggernaut", 1800);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(0), "Ultra-Juggernaut", 15000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(0), "Quick Shots", 100);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(0), "Very Quick Shots", 190);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(0), "Triple Shot", 400);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(0), "Super Monkey Fan Club", 8000);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(0), "Plasma Monkey Fan Club", 45000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(0), "Long Range Darts", 90);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(0), "Enhanced Eyesight", 200);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(0), "Crossbow", 625);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(0), "Sharp Shooter", 2000);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(0), "Crossbow Master", 25000);
            Upgrade.upgrades.put(names.get(0), thisUpgrades);
            //Boomerang Monkey
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(1), "Improved Rangs", 200);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(1), "Glaives", 280);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(1), "Glaives Ricochet", 1300);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(1), "M.O.A.R Glaives", 3000);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(1), "Glaive Lord", 32400);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(1), "Faster Throwing", 175);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(1), "Faster Rangs", 250);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(1), "Bionic Boomerang", 1600);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(1), "Turbo Charge", 4000);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(1), "Perma Charge", 35000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(1), "Long Range Rangs", 100);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(1), "Red Hot Rangs", 300);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(1), "Kylie Boomerang", 1300);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(1), "MOAB Press", 2200);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(1), "MOAB Domination", 60000);
            Upgrade.upgrades.put(names.get(1), thisUpgrades);
            //Bomb Shooter
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(2), "Bigger Bombs", 350);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(2), "Heavy Bombs", 650);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(2), "Really Big Bombs", 1200);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(2), "Bloon Impact", 3600);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(2), "Bloon Crush", 55000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(2), "Faster Reload", 250);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(2), "Missile Launcher", 400);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(2), "MOAB Mauler", 900);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(2), "MOAB Assassin", 3200);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(2), "MOAB Eliminator", 25000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(2), "Extra Range", 200);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(2), "Frag Bombs", 300);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(2), "Cluster Bombs", 800);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(2), "Recursive Cluster", 2800);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(2), "Bomb Blitz", 35000);
            Upgrade.upgrades.put(names.get(2), thisUpgrades);
            //Tack Shooter
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(3), "Faster Shooting", 150);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(3), "Even Faster Shooting", 300);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(3), "Hot Shots", 600);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(3), "Ring of Fire", 3500);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(3), "Inferno Ring", 45500);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(3), "Long Range Tacks", 100);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(3), "Super Range Tacks", 225);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(3), "Blade Shooter", 550);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(3), "Blade Maelstorm", 2700);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(3), "Super Maelstorm", 15000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(3), "More Tacks", 100);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(3), "Even More Tacks", 100);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(3), "Tack Sprayer", 450);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(3), "Overdrive", 3200);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(3), "The Tack Zone", 20000);
            Upgrade.upgrades.put(names.get(3), thisUpgrades);
            //Ice Monkey
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(4), "Permafrost", 100);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(4), "Metal Freeze", 300);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(4), "Ice Shards", 1500);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(4), "Embrittlement", 2200);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(4), "Super Brittle", 28000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(4), "Enhanced Freeze", 225);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(4), "Deep Freeze", 350);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(4), "Arctic Wind", 2900);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(4), "Snowstorm", 3000);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(4), "Absolute Zero", 26000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(4), "Larger Radius", 100);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(4), "Re-Freeze", 200);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(4), "Cry Cannon", 1750);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(4), "Icicles", 2000);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(4), "Icicle Impale", 30000);
            Upgrade.upgrades.put(names.get(4), thisUpgrades);
            //Glue Gunner
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(5), "Glue Soak", 200);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(5), "Corrosive Glue", 300);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(5), "Bloon Dissolver", 2500);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(5), "Bloon Liquefier", 5000);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(5), "The Bloon Solver", 22000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(5), "Bigger Globs", 100);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(5), "Glue Splatter", 1800);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(5), "Glue Hose", 3250);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(5), "Glue Strike", 3500);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(5), "Glue Storm", 15000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(5), "Stickier Glue", 120);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(5), "Stringer Glue", 400);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(5), "MOAB Glue", 3400);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(5), "Relentless Glue", 3000);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(5), "Super Glue", 28000);
            Upgrade.upgrades.put(names.get(5), thisUpgrades);
            //Sniper Monkey
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(6), "Full Metal Jacket", 350);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(6), "Large Calibre", 1500);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(6), "Deadly Precision", 3000);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(6), "Maim MOAB", 5000);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(6), "Cripple MOAB", 34000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(6), "Night Vision Goggles", 300);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(6), "Sharpnel Shot", 450);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(6), "Bouncing Bullet", 3200);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(6), "Supply Drop", 7200);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(6), "Elite Sniper", 13000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(6), "Fast Firing", 400);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(6), "Even Faster Firing", 400);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(6), "Semi-Automatic", 3500);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(6), "Full Auto Rifle", 4750);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(6), "Elite Defender", 14000);
            Upgrade.upgrades.put(names.get(6), thisUpgrades);
            //Monkey Sub
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(7), "Longer Range", 130);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(7), "Advanced Intel", 500);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(7), "Submerge and Support", 500);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(7), "Bloontonium Reactor", 2500);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(7), "Energizer", 32000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(7), "Barbed Darts", 450);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(7), "Heat-tipped Darts", 300);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(7), "Balistic Missile", 1400);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(7), "First Strike Capability", 13000);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(7), "Pre-emptive Strike", 32000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(7), "Twin Guns", 450);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(7), "Airburst Darts", 1000);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(7), "Triple Guns", 1100);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(7), "Armor Piercing Darts", 3000);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(7), "Sub Commander", 25000);
            Upgrade.upgrades.put(names.get(7), thisUpgrades);
            //Monkey Buccaneer
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(8), "Faster Shooting", 350);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(8), "Double Shot", 550);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(8), "Destroyer", 2950);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(8), "Aircraft Carrier", 7200);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(8), "Carrier Flagship", 25000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(8), "Grape Shot", 550);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(8), "Hot Shot", 500);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(8), "Cannon Ship", 900);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(8), "Monkey Pirates", 4500);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(8), "Pirate Lord", 21000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(8), "Long Range", 180);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(8), "Crow's Nest", 400);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(8), "Merchantman", 2300);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(8), "Favored Trades", 5500);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(8), "Trade Empire", 23000);
            Upgrade.upgrades.put(names.get(8), thisUpgrades);
            //Monkey Ace
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(9), "Rapid Fire", 650);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(9), "Lots More Darts", 650);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(9), "Fighter Plane", 1000);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(9), "Operation: Dart Storm", 3000);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(9), "Sky Shredder", 40000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(9), "Exploding Pineapple", 200);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(9), "Spy Plane", 350);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(9), "Bomber Ace", 900);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(9), "Ground Zero", 14000);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(9), "Tsar Bomba", 35000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(9), "Sharper Darts", 500);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(9), "Centered Path", 300);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(9), "Neva-Miss Targeting", 2200);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(9), "Spectre", 24000);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(9), "Flying Fortress", 100000);
            Upgrade.upgrades.put(names.get(9), thisUpgrades);
            //Heli Pilot
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(10), "Quad Darts", 800);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(10), "Pursuit", 500);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(10), "Razor Rotors", 1750);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(10), "Apache Dartship", 19600);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(10), "Apache Prime", 45000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(10), "Bigger Jets", 300);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(10), "IFR", 600);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(10), "Downdraft", 3000);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(10), "Support Chinook", 12000);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(10), "Special Poperations", 30000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(10), "Faster Darts", 250);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(10), "Faster Firing", 350);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(10), "MOAB Shove", 3500);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(10), "Comanche Defense", 8500);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(10), "Comanche Commander", 35000);
            Upgrade.upgrades.put(names.get(10), thisUpgrades);
            //Mortar Monkey
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(11), "Bigger Blast", 500);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(11), "Bloon Buster", 650);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(11), "Shell Shock", 1100);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(11), "The Big One", 8000);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(11), "The Biggest One", 28000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(11), "Faster Reload", 300);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(11), "Rapid Reload", 500);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(11), "Heavy Shells", 900);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(11), "Artillery Battery", 5500);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(11), "Pop and Awe", 30000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(11), "Increased Accuracy", 200);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(11), "Burny Stuff", 500);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(11), "Signal Flare", 700);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(11), "Shattering Shells", 11000);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(11), "Blooncineration", 40000);
            Upgrade.upgrades.put(names.get(11), thisUpgrades);
            //Dartling Gunner
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(12), "Focused Firing", 300);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(12), "Laser Shock", 900);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(12), "Laser Cannon", 4250);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(12), "Plasma Accelerator", 11000);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(12), "Ray of Doom", 95000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(12), "Advanced Targeting", 250);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(12), "Faster Barrel Spin", 950);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(12), "Hydra Rocket pods", 5250);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(12), "Rocket Storm", 5100);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(12), "M.A.D", 60000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(12), "Faster Swivel", 150);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(12), "Powerfull Darts", 1200);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(12), "Buckshot", 3400);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(12), "Bloon Area Denial System", 12000);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(12), "Bloon Exclusion Zone", 58000);
            Upgrade.upgrades.put(names.get(12), thisUpgrades);
            //Wizard Monkey
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(13), "Guided Magic", 150);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(13), "Arcane Blast", 600);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(13), "Arcane Mastery", 1300);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(13), "Arcane Spike", 10900);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(13), "Archmage", 32000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(13), "Fireball", 300);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(13), "Wall of Fire", 900);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(13), "Dragon's Breath", 3000);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(13), "Summon Phoenix", 4000);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(13), "Wizard Lord Phoenix", 54000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(13), "Intense Magic", 300);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(13), "Monkey Sense", 300);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(13), "Shimmer", 1700);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(13), "Necromancer: Unpopped Army", 2800);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(13), "Prince of Darkness", 24000);
            Upgrade.upgrades.put(names.get(13), thisUpgrades);
            //Super Monkey
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(14), "Laser Blasts", 2500);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(14), "Plasma Blasts", 4500);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(14), "Sun Avatar", 22000);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(14), "Sun Temple", 100000);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(14), "True Sun God", 500000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(14), "Super Range", 1000);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(14), "Epic Range", 1400);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(14), "Robo Monkey", 7000);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(14), "Tech Terror", 19000);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(14), "The Anti-Bloon", 90000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(14), "Knockback", 3000);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(14), "Ultravision", 1200);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(14), "Dark Knight", 5500);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(14), "Dark Champion", 60000);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(14), "Legend of the Night", 240000);
            Upgrade.upgrades.put(names.get(14), thisUpgrades);
            //Ninja Monkey
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(15), "Ninja Discipline", 300);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(15), "Sharp Shurikens", 350);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(15), "Double Shot", 850);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(15), "Bloonjitsu", 2750);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(15), "Grandmaster Ninja", 35000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(15), "Distraction", 350);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(15), "Counter-Espionage", 500);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(15), "Shinobi Tactics", 900);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(15), "Bloon Sabotage", 5200);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(15), "Grand Saboteur", 22000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(15), "Seeking Shurikens", 250);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(15), "Caltrops", 400);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(15), "Flash Bomb", 2750);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(15), "Sticky Bomb", 4500);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(15), "Master Bomber", 40000);
            Upgrade.upgrades.put(names.get(15), thisUpgrades);
            //Alchemist
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(16), "Larger Potions", 250);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(16), "Acidic Mixture Dip", 350);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(16), "Berseker Brew", 1250);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(16), "Stronger Stimulant", 3000);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(16), "Permanent Brew", 60000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(16), "Stronger Acid", 250);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(16), "Perishing Potions", 475);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(16), "Unstable Concotion", 3000);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(16), "Transforming Tonic", 4500);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(16), "Total Transformation", 45000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(16), "Faster Throwing", 650);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(16), "Acid Pool", 450);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(16), "Lead to Gold", 1000);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(16), "Rubber to Gold", 2750);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(16), "Bloon Master Alchemist", 40000);
            Upgrade.upgrades.put(names.get(16), thisUpgrades);
            //Druid
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(17), "Hard Thorns", 250);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(17), "Heart of Thunder", 1000);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(17), "Druid of the Storm", 1850);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(17), "Ball Lightning", 5100);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(17), "Superstorm", 90000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(17), "Thorn Swarm", 250);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(17), "Heart of Oak", 350);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(17), "Druid of the Jungle", 950);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(17), "Jungle's Bounty", 5000);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(17), "Spirit of the Forest", 35000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(17), "Druidic Reachs", 100);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(17), "Heart of Vengeance", 300);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(17), "Druid of Wrath", 600);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(17), "Poplust", 2500);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(17), "Avatar of Wrath", 45000);
            Upgrade.upgrades.put(names.get(17), thisUpgrades);
            //Banana Farm
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(18), "Increased Production", 500);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(18), "Greater Production", 600);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(18), "Banana Plantation", 3000);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(18), "Banana Research Facility", 19000);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(18), "Banana Central", 100000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(18), "Long Life Bananas", 300);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(18), "Valuable Bananas", 800);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(18), "Monkey Bank", 3500);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(18), "IMF Loan", 7500);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(18), "Monkey-Nomics", 100000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(18), "EZ Collect", 250);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(18), "Banana Salvage", 200);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(18), "Marketplace", 2900);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(18), "Central Market", 15000);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(18), "Monkey Wall Street", 60000);
            Upgrade.upgrades.put(names.get(18), thisUpgrades);
            //Spike Factory
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(19), "Bigger Stacks", 800);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(19), "White Hot Spikes", 600);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(19), "Spiked Balls", 2300);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(19), "Spiked Mines", 9500);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(19), "Super Mines", 162500);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(19), "Faster Production", 600);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(19), "Even Faster Production", 800);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(19), "MOAB Shredder", 2500);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(19), "Spike Storm", 5000);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(19), "Carpet of Spikes", 40000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(19), "Long Reach", 150);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(19), "Smart Spikes", 400);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(19), "Long Life Spikes", 1400);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(19), "Deadly Spikes", 3500);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(19), "Perma Spike", 30000);
            Upgrade.upgrades.put(names.get(19), thisUpgrades);
            //Monkey Village
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(20), "Bigger Radius", 400);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(20), "Jungle Drums", 1500);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(20), "Primary Training", 800);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(20), "Primary Mentoring", 2500);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(20), "Primary Expertise", 25000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(20), "Grow Blocker", 250);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(20), "Radar Scanner", 2000);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(20), "Monkey Intelligence Bureau", 7500);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(20), "Call to Arms", 20000);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(20), "Homeland Defense", 40000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(20), "Monkey Business", 500);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(20), "Monkey Comerce", 500);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(20), "Monkey Town", 10000);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(20), "Monkey City", 3000);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(20), "Monkeyopolis", 5000);
            Upgrade.upgrades.put(names.get(20), thisUpgrades);
            //Engineer Monkey
            thisUpgrades = new Upgrade[3][5];
            thisUpgrades[0][0] = new Upgrade(types[0], names.get(21), "Sentry Gun", 500);
            thisUpgrades[0][1] = new Upgrade(types[0], names.get(21), "Faster Engineering", 400);
            thisUpgrades[0][2] = new Upgrade(types[0], names.get(21), "Sprockets", 525);
            thisUpgrades[0][3] = new Upgrade(types[0], names.get(21), "Sentry Expert", 2500);
            thisUpgrades[0][4] = new Upgrade(types[0], names.get(21), "Sentry Paragon", 32000);
            thisUpgrades[1][0] = new Upgrade(types[0], names.get(21), "Larger Service Area", 250);
            thisUpgrades[1][1] = new Upgrade(types[0], names.get(21), "Deconstruction", 350);
            thisUpgrades[1][2] = new Upgrade(types[0], names.get(21), "Cleansing Foam", 800);
            thisUpgrades[1][3] = new Upgrade(types[1], names.get(21), "Overclock", 13500);
            thisUpgrades[1][4] = new Upgrade(types[1], names.get(21), "Ultraboost", 120000);
            thisUpgrades[2][0] = new Upgrade(types[0], names.get(21), "Oversize Nails", 450);
            thisUpgrades[2][1] = new Upgrade(types[0], names.get(21), "Pin", 220);
            thisUpgrades[2][2] = new Upgrade(types[0], names.get(21), "Double Gun", 500);
            thisUpgrades[2][3] = new Upgrade(types[0], names.get(21), "Bloon Trap", 3500);
            thisUpgrades[2][4] = new Upgrade(types[0], names.get(21), "XXXL Trap", 54000);
            Upgrade.upgrades.put(names.get(21), thisUpgrades);
            return true;
        } catch(Exception e) {
            System.err.println(e);
            return false;
        }
    }

    public String getTowerName() {
        return towerName;
    }

    public void setTowerName(String towerName) {
        this.towerName = towerName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int baseCost) {
        this.cost = baseCost;
    }

    public boolean isApplied() {
        return isApplied;
    }

    public void setApplied(boolean isApplied) {
        this.isApplied = isApplied;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public short getPath() {
        return path;
    }

    public void setPath(short path) {
        this.path = path;
    }

    public short getTier() {
        return tier;
    }

    public void setTier(short tier) {
        this.tier = tier;
    }

    @Override
    public String createString() {
        this.sb.delete(0, this.sb.length());
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
        return "{ " + this.getName() + "=[" + this.createString() + "]}";
    }
}
