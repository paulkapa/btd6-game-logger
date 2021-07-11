package com.paulkapa.btd6gamelogger.models.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import com.google.gson.Gson;
import com.paulkapa.btd6gamelogger.models.BaseEntity;

/**
 * <h4>Class that defines the attributes af an Upgrade</h4>
 *
 * Provides static storage for all upgrades.
 *
 * @see BaseEntity
 */
public class Upgrade extends BaseEntity {

    private static LinkedHashMap<String, Upgrade[][]> defaultUpgrades = null;
    private static boolean isInitDefaultUpgrades = false;

    private String towerName;
    private int cost;
    private boolean isApplied;
    private boolean isLocked;
    private short path;
    private short tier;

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
     * @param name
     * @param type
     * @param towerName
     * @param cost
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
     * @param name
     * @param type
     * @param towerName
     * @param cost
     * @param isApplied
     * @param isLocked
     * @param path
     * @param tier
    */
    public Upgrade(String name, String type, String towerName, int cost, boolean isApplied, boolean isLocked, short path, short tier) {
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
     * @param other
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

    public static int countUnlockedUpgrades(LinkedHashMap<String, Upgrade[][]> upgradesList) {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(0, 0);
        if(upgradesList != null)
            upgradesList.forEach((s, u) -> {
                for(int i = 0; i < u.length; i++)
                    for(int j = 0; j < u[i].length; j++)
                        if(!u[i][j].isLocked()) result.add(0, result.get(0) + 1);
            });
        return result.get(0);
    }

    public static int countAppliedUpgrades(LinkedHashMap<String, Upgrade[][]> upgradesList) {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(0, 0);
        if(upgradesList != null)
            upgradesList.forEach((s, u) -> {
                for(int i = 0; i < u.length; i++)
                    for(int j = 0; j < u[i].length; j++)
                        if(!u[i][j].isApplied()) result.add(0, result.get(0) + 1);
            });
        return result.get(0);
    }

    public static LinkedHashMap<String, Upgrade[][]> getDefaultUpgrades() throws Exception {
        if(!Upgrade.isInitDefaultUpgrades) Upgrade.initDefaultUpgrades();
        return Upgrade.defaultUpgrades;
    }

    public static ArrayList<Upgrade[]> getUpgradesByTowerName(String towerName, LinkedHashMap<String, Upgrade[][]> upgradesSearch) throws Exception {
        ArrayList<Upgrade[]> result = new ArrayList<>();
		result.add(0, null);
        if(upgradesSearch == null) throw new Exception(
			"Cannot search in empty map!",
			new Throwable("Provided upgradesSearch is null."));
		upgradesSearch.forEach((n, u) -> {if(result.size() == 0 && n.equals(towerName)) result.addAll(Arrays.asList(u));});
		if(result.size() == 0) throw new Exception(
			"No result matching the criteria!",
			new Throwable("No upgrades for tower with name '" + towerName + "' were found."));
		return result;
    }

    private static void initDefaultUpgrades() throws Exception {
        Upgrade.defaultUpgrades = new LinkedHashMap<>();
        Upgrade[][] thisUpgrades;
        ArrayList<String> towerNames = new ArrayList<>(Tower.getTowersNames(Tower.getDefaultTowers()));
        String[] types = {"Upgrade", "Ability"};
        // Dart Monkey
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Sharp Shots", types[0], towerNames.get(0), 140);
        thisUpgrades[0][1] = new Upgrade("Razor Sharp Shots", types[0], towerNames.get(0), 220);
        thisUpgrades[0][2] = new Upgrade("Spike-o-pult", types[0], towerNames.get(0), 300);
        thisUpgrades[0][3] = new Upgrade("Juggernaut", types[0], towerNames.get(0), 1800);
        thisUpgrades[0][4] = new Upgrade("Ultra-Juggernaut", types[0], towerNames.get(0), 15000);
        thisUpgrades[1][0] = new Upgrade("Quick Shots", types[0], towerNames.get(0), 100);
        thisUpgrades[1][1] = new Upgrade("Very Quick Shots", types[0], towerNames.get(0), 190);
        thisUpgrades[1][2] = new Upgrade("Triple Shot", types[0], towerNames.get(0), 400);
        thisUpgrades[1][3] = new Upgrade("Super Monkey Fan Club", types[1], towerNames.get(0), 8000);
        thisUpgrades[1][4] = new Upgrade("Plasma Monkey Fan Club", types[1], towerNames.get(0), 45000);
        thisUpgrades[2][0] = new Upgrade("Long Range Darts", types[0], towerNames.get(0), 90);
        thisUpgrades[2][1] = new Upgrade("Enhanced Eyesight", types[0], towerNames.get(0), 200);
        thisUpgrades[2][2] = new Upgrade("Crossbow", types[0], towerNames.get(0), 625);
        thisUpgrades[2][3] = new Upgrade("Sharp Shooter", types[0], towerNames.get(0), 2000);
        thisUpgrades[2][4] = new Upgrade("Crossbow Master", types[0], towerNames.get(0), 25000);
        Upgrade.defaultUpgrades.put(towerNames.get(0), thisUpgrades);
        // Boomerang Monkey
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Improved Rangs", types[0], towerNames.get(1), 200);
        thisUpgrades[0][1] = new Upgrade("Glaives", types[0], towerNames.get(1), 280);
        thisUpgrades[0][2] = new Upgrade("Glaives Ricochet", types[0], towerNames.get(1), 1300);
        thisUpgrades[0][3] = new Upgrade("M.O.A.R Glaives", types[0], towerNames.get(1), 3000);
        thisUpgrades[0][4] = new Upgrade("Glaive Lord", types[0], towerNames.get(1), 32400);
        thisUpgrades[1][0] = new Upgrade("Faster Throwing", types[0], towerNames.get(1), 175);
        thisUpgrades[1][1] = new Upgrade("Faster Rangs", types[0], towerNames.get(1), 250);
        thisUpgrades[1][2] = new Upgrade("Bionic Boomerang", types[0], towerNames.get(1), 1600);
        thisUpgrades[1][3] = new Upgrade("Turbo Charge", types[1], towerNames.get(1), 4000);
        thisUpgrades[1][4] = new Upgrade("Perma Charge", types[1], towerNames.get(1), 35000);
        thisUpgrades[2][0] = new Upgrade("Long Range Rangs", types[0], towerNames.get(1), 100);
        thisUpgrades[2][1] = new Upgrade("Red Hot Rangs", types[0], towerNames.get(1), 300);
        thisUpgrades[2][2] = new Upgrade("Kylie Boomerang", types[0], towerNames.get(1), 1300);
        thisUpgrades[2][3] = new Upgrade("MOAB Press", types[0], towerNames.get(1), 2200);
        thisUpgrades[2][4] = new Upgrade("MOAB Domination", types[0], towerNames.get(1), 60000);
        Upgrade.defaultUpgrades.put(towerNames.get(1), thisUpgrades);
        // Bomb Shooter
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Bigger Bombs", types[0], towerNames.get(2), 350);
        thisUpgrades[0][1] = new Upgrade("Heavy Bombs", types[0], towerNames.get(2), 650);
        thisUpgrades[0][2] = new Upgrade("Really Big Bombs", types[0], towerNames.get(2), 1200);
        thisUpgrades[0][3] = new Upgrade("Bloon Impact", types[0], towerNames.get(2), 3600);
        thisUpgrades[0][4] = new Upgrade("Bloon Crush", types[0], towerNames.get(2), 55000);
        thisUpgrades[1][0] = new Upgrade("Faster Reload", types[0], towerNames.get(2), 250);
        thisUpgrades[1][1] = new Upgrade("Missile Launcher", types[0], towerNames.get(2), 400);
        thisUpgrades[1][2] = new Upgrade("MOAB Mauler", types[0], towerNames.get(2), 900);
        thisUpgrades[1][3] = new Upgrade("MOAB Assassin", types[1], towerNames.get(2), 3200);
        thisUpgrades[1][4] = new Upgrade("MOAB Eliminator", types[1], towerNames.get(2), 25000);
        thisUpgrades[2][0] = new Upgrade("Extra Range", types[0], towerNames.get(2), 200);
        thisUpgrades[2][1] = new Upgrade("Frag Bombs", types[0], towerNames.get(2), 300);
        thisUpgrades[2][2] = new Upgrade("Cluster Bombs", types[0], towerNames.get(2), 800);
        thisUpgrades[2][3] = new Upgrade("Recursive Cluster", types[0], towerNames.get(2), 2800);
        thisUpgrades[2][4] = new Upgrade("Bomb Blitz", types[0], towerNames.get(2), 35000);
        Upgrade.defaultUpgrades.put(towerNames.get(2), thisUpgrades);
        // Tack Shooter
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Faster Shooting", types[0], towerNames.get(3), 150);
        thisUpgrades[0][1] = new Upgrade("Even Faster Shooting", types[0], towerNames.get(3), 300);
        thisUpgrades[0][2] = new Upgrade("Hot Shots", types[0], towerNames.get(3), 600);
        thisUpgrades[0][3] = new Upgrade("Ring of Fire", types[0], towerNames.get(3), 3500);
        thisUpgrades[0][4] = new Upgrade("Inferno Ring", types[0], towerNames.get(3), 45500);
        thisUpgrades[1][0] = new Upgrade("Long Range Tacks", types[0], towerNames.get(3), 100);
        thisUpgrades[1][1] = new Upgrade("Super Range Tacks", types[0], towerNames.get(3), 225);
        thisUpgrades[1][2] = new Upgrade("Blade Shooter", types[0], towerNames.get(3), 550);
        thisUpgrades[1][3] = new Upgrade("Blade Maelstorm", types[1], towerNames.get(3), 2700);
        thisUpgrades[1][4] = new Upgrade("Super Maelstorm", types[1], towerNames.get(3), 15000);
        thisUpgrades[2][0] = new Upgrade("More Tacks", types[0], towerNames.get(3), 100);
        thisUpgrades[2][1] = new Upgrade("Even More Tacks", types[0], towerNames.get(3), 100);
        thisUpgrades[2][2] = new Upgrade("Tack Sprayer", types[0], towerNames.get(3), 450);
        thisUpgrades[2][3] = new Upgrade("Overdrive", types[0], towerNames.get(3), 3200);
        thisUpgrades[2][4] = new Upgrade("The Tack Zone", types[0], towerNames.get(3), 20000);
        Upgrade.defaultUpgrades.put(towerNames.get(3), thisUpgrades);
        // Ice Monkey
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Permafrost", types[0], towerNames.get(4), 100);
        thisUpgrades[0][1] = new Upgrade("Metal Freeze", types[0], towerNames.get(4), 300);
        thisUpgrades[0][2] = new Upgrade("Ice Shards", types[0], towerNames.get(4), 1500);
        thisUpgrades[0][3] = new Upgrade("Embrittlement", types[0], towerNames.get(4), 2200);
        thisUpgrades[0][4] = new Upgrade("Super Brittle", types[0], towerNames.get(4), 28000);
        thisUpgrades[1][0] = new Upgrade("Enhanced Freeze", types[0], towerNames.get(4), 225);
        thisUpgrades[1][1] = new Upgrade("Deep Freeze", types[0], towerNames.get(4), 350);
        thisUpgrades[1][2] = new Upgrade("Arctic Wind", types[0], towerNames.get(4), 2900);
        thisUpgrades[1][3] = new Upgrade("Snowstorm", types[1], towerNames.get(4), 3000);
        thisUpgrades[1][4] = new Upgrade("Absolute Zero", types[1], towerNames.get(4), 26000);
        thisUpgrades[2][0] = new Upgrade("Larger Radius", types[0], towerNames.get(4), 100);
        thisUpgrades[2][1] = new Upgrade("Re-Freeze", types[0], towerNames.get(4), 200);
        thisUpgrades[2][2] = new Upgrade("Cry Cannon", types[0], towerNames.get(4), 1750);
        thisUpgrades[2][3] = new Upgrade("Icicles", types[0], towerNames.get(4), 2000);
        thisUpgrades[2][4] = new Upgrade("Icicle Impale", types[0], towerNames.get(4), 30000);
        Upgrade.defaultUpgrades.put(towerNames.get(4), thisUpgrades);
        // Glue Gunner
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Glue Soak", types[0], towerNames.get(5), 200);
        thisUpgrades[0][1] = new Upgrade("Corrosive Glue", types[0], towerNames.get(5), 300);
        thisUpgrades[0][2] = new Upgrade("Bloon Dissolver", types[0], towerNames.get(5), 2500);
        thisUpgrades[0][3] = new Upgrade("Bloon Liquefier", types[0], towerNames.get(5), 5000);
        thisUpgrades[0][4] = new Upgrade("The Bloon Solver", types[0], towerNames.get(5), 22000);
        thisUpgrades[1][0] = new Upgrade("Bigger Globs", types[0], towerNames.get(5), 100);
        thisUpgrades[1][1] = new Upgrade("Glue Splatter", types[0], towerNames.get(5), 1800);
        thisUpgrades[1][2] = new Upgrade("Glue Hose", types[0], towerNames.get(5), 3250);
        thisUpgrades[1][3] = new Upgrade("Glue Strike", types[1], towerNames.get(5), 3500);
        thisUpgrades[1][4] = new Upgrade("Glue Storm", types[1], towerNames.get(5), 15000);
        thisUpgrades[2][0] = new Upgrade("Stickier Glue", types[0], towerNames.get(5), 120);
        thisUpgrades[2][1] = new Upgrade("Stringer Glue", types[0], towerNames.get(5), 400);
        thisUpgrades[2][2] = new Upgrade("MOAB Glue", types[0], towerNames.get(5), 3400);
        thisUpgrades[2][3] = new Upgrade("Relentless Glue", types[0], towerNames.get(5), 3000);
        thisUpgrades[2][4] = new Upgrade("Super Glue", types[0], towerNames.get(5), 28000);
        Upgrade.defaultUpgrades.put(towerNames.get(5), thisUpgrades);
        // Sniper Monkey
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Full Metal Jacket", types[0], towerNames.get(6), 350);
        thisUpgrades[0][1] = new Upgrade("Large Calibre", types[0], towerNames.get(6), 1500);
        thisUpgrades[0][2] = new Upgrade("Deadly Precision", types[0], towerNames.get(6), 3000);
        thisUpgrades[0][3] = new Upgrade("Maim MOAB", types[0], towerNames.get(6), 5000);
        thisUpgrades[0][4] = new Upgrade("Cripple MOAB", types[0], towerNames.get(6), 34000);
        thisUpgrades[1][0] = new Upgrade("Night Vision Goggles", types[0], towerNames.get(6), 300);
        thisUpgrades[1][1] = new Upgrade("Sharpnel Shot", types[0], towerNames.get(6), 450);
        thisUpgrades[1][2] = new Upgrade("Bouncing Bullet", types[0], towerNames.get(6), 3200);
        thisUpgrades[1][3] = new Upgrade("Supply Drop", types[1], towerNames.get(6), 7200);
        thisUpgrades[1][4] = new Upgrade("Elite Sniper", types[1], towerNames.get(6), 13000);
        thisUpgrades[2][0] = new Upgrade("Fast Firing", types[0], towerNames.get(6), 400);
        thisUpgrades[2][1] = new Upgrade("Even Faster Firing", types[0], towerNames.get(6), 400);
        thisUpgrades[2][2] = new Upgrade("Semi-Automatic", types[0], towerNames.get(6), 3500);
        thisUpgrades[2][3] = new Upgrade("Full Auto Rifle", types[0], towerNames.get(6), 4750);
        thisUpgrades[2][4] = new Upgrade("Elite Defender", types[0], towerNames.get(6), 14000);
        Upgrade.defaultUpgrades.put(towerNames.get(6), thisUpgrades);
        // Monkey Sub
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Longer Range", types[0], towerNames.get(7), 130);
        thisUpgrades[0][1] = new Upgrade("Advanced Intel", types[0], towerNames.get(7), 500);
        thisUpgrades[0][2] = new Upgrade("Submerge and Support", types[0], towerNames.get(7), 500);
        thisUpgrades[0][3] = new Upgrade("Bloontonium Reactor", types[0], towerNames.get(7), 2500);
        thisUpgrades[0][4] = new Upgrade("Energizer", types[0], towerNames.get(7), 32000);
        thisUpgrades[1][0] = new Upgrade("Barbed Darts", types[0], towerNames.get(7), 450);
        thisUpgrades[1][1] = new Upgrade("Heat-tipped Darts", types[0], towerNames.get(7), 300);
        thisUpgrades[1][2] = new Upgrade("Balistic Missile", types[0], towerNames.get(7), 1400);
        thisUpgrades[1][3] = new Upgrade("First Strike Capability", types[1], towerNames.get(7), 13000);
        thisUpgrades[1][4] = new Upgrade("Pre-emptive Strike", types[1], towerNames.get(7), 32000);
        thisUpgrades[2][0] = new Upgrade("Twin Guns", types[0], towerNames.get(7), 450);
        thisUpgrades[2][1] = new Upgrade("Airburst Darts", types[0], towerNames.get(7), 1000);
        thisUpgrades[2][2] = new Upgrade("Triple Guns", types[0], towerNames.get(7), 1100);
        thisUpgrades[2][3] = new Upgrade("Armor Piercing Darts", types[0], towerNames.get(7), 3000);
        thisUpgrades[2][4] = new Upgrade("Sub Commander", types[0], towerNames.get(7), 25000);
        Upgrade.defaultUpgrades.put(towerNames.get(7), thisUpgrades);
        // Monkey Buccaneer
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Faster Shooting", types[0], towerNames.get(8), 350);
        thisUpgrades[0][1] = new Upgrade("Double Shot", types[0], towerNames.get(8), 550);
        thisUpgrades[0][2] = new Upgrade("Destroyer", types[0], towerNames.get(8), 2950);
        thisUpgrades[0][3] = new Upgrade("Aircraft Carrier", types[0], towerNames.get(8), 7200);
        thisUpgrades[0][4] = new Upgrade("Carrier Flagship", types[0], towerNames.get(8), 25000);
        thisUpgrades[1][0] = new Upgrade("Grape Shot", types[0], towerNames.get(8), 550);
        thisUpgrades[1][1] = new Upgrade("Hot Shot", types[0], towerNames.get(8), 500);
        thisUpgrades[1][2] = new Upgrade("Cannon Ship", types[0], towerNames.get(8), 900);
        thisUpgrades[1][3] = new Upgrade("Monkey Pirates", types[1], towerNames.get(8), 4500);
        thisUpgrades[1][4] = new Upgrade("Pirate Lord", types[1], towerNames.get(8), 21000);
        thisUpgrades[2][0] = new Upgrade("Long Range", types[0], towerNames.get(8), 180);
        thisUpgrades[2][1] = new Upgrade("Crow's Nest", types[0], towerNames.get(8), 400);
        thisUpgrades[2][2] = new Upgrade("Merchantman", types[0], towerNames.get(8), 2300);
        thisUpgrades[2][3] = new Upgrade("Favored Trades", types[0], towerNames.get(8), 5500);
        thisUpgrades[2][4] = new Upgrade("Trade Empire", types[0], towerNames.get(8), 23000);
        Upgrade.defaultUpgrades.put(towerNames.get(8), thisUpgrades);
        // Monkey Ace
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Rapid Fire", types[0], towerNames.get(9), 650);
        thisUpgrades[0][1] = new Upgrade("Lots More Darts", types[0], towerNames.get(9), 650);
        thisUpgrades[0][2] = new Upgrade("Fighter Plane", types[0], towerNames.get(9), 1000);
        thisUpgrades[0][3] = new Upgrade("Operation: Dart Storm", types[0], towerNames.get(9), 3000);
        thisUpgrades[0][4] = new Upgrade("Sky Shredder", types[0], towerNames.get(9), 40000);
        thisUpgrades[1][0] = new Upgrade("Exploding Pineapple", types[0], towerNames.get(9), 200);
        thisUpgrades[1][1] = new Upgrade("Spy Plane", types[0], towerNames.get(9), 350);
        thisUpgrades[1][2] = new Upgrade("Bomber Ace", types[0], towerNames.get(9), 900);
        thisUpgrades[1][3] = new Upgrade("Ground Zero", types[1], towerNames.get(9), 14000);
        thisUpgrades[1][4] = new Upgrade("Tsar Bomba", types[1], towerNames.get(9), 35000);
        thisUpgrades[2][0] = new Upgrade("Sharper Darts", types[0], towerNames.get(9), 500);
        thisUpgrades[2][1] = new Upgrade("Centered Path", types[0], towerNames.get(9), 300);
        thisUpgrades[2][2] = new Upgrade("Neva-Miss Targeting", types[0], towerNames.get(9), 2200);
        thisUpgrades[2][3] = new Upgrade("Spectre", types[0], towerNames.get(9), 24000);
        thisUpgrades[2][4] = new Upgrade("Flying Fortress", types[0], towerNames.get(9), 100000);
        Upgrade.defaultUpgrades.put(towerNames.get(9), thisUpgrades);
        // Heli Pilot
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Quad Darts", types[0], towerNames.get(10), 800);
        thisUpgrades[0][1] = new Upgrade("Pursuit", types[0], towerNames.get(10), 500);
        thisUpgrades[0][2] = new Upgrade("Razor Rotors", types[0], towerNames.get(10), 1750);
        thisUpgrades[0][3] = new Upgrade("Apache Dartship", types[0], towerNames.get(10), 19600);
        thisUpgrades[0][4] = new Upgrade("Apache Prime", types[0], towerNames.get(10), 45000);
        thisUpgrades[1][0] = new Upgrade("Bigger Jets", types[0], towerNames.get(10), 300);
        thisUpgrades[1][1] = new Upgrade("IFR", types[0], towerNames.get(10), 600);
        thisUpgrades[1][2] = new Upgrade("Downdraft", types[0], towerNames.get(10), 3000);
        thisUpgrades[1][3] = new Upgrade("Support Chinook", types[1], towerNames.get(10), 12000);
        thisUpgrades[1][4] = new Upgrade("Special Poperations", types[1], towerNames.get(10), 30000);
        thisUpgrades[2][0] = new Upgrade("Faster Darts", types[0], towerNames.get(10), 250);
        thisUpgrades[2][1] = new Upgrade("Faster Firing", types[0], towerNames.get(10), 350);
        thisUpgrades[2][2] = new Upgrade("MOAB Shove", types[0], towerNames.get(10), 3500);
        thisUpgrades[2][3] = new Upgrade("Comanche Defense", types[0], towerNames.get(10), 8500);
        thisUpgrades[2][4] = new Upgrade("Comanche Commander", types[0], towerNames.get(10), 35000);
        Upgrade.defaultUpgrades.put(towerNames.get(10), thisUpgrades);
        // Mortar Monkey
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Bigger Blast", types[0], towerNames.get(11), 500);
        thisUpgrades[0][1] = new Upgrade("Bloon Buster", types[0], towerNames.get(11), 650);
        thisUpgrades[0][2] = new Upgrade("Shell Shock", types[0], towerNames.get(11), 1100);
        thisUpgrades[0][3] = new Upgrade("The Big One", types[0], towerNames.get(11), 8000);
        thisUpgrades[0][4] = new Upgrade("The Biggest One", types[0], towerNames.get(11), 28000);
        thisUpgrades[1][0] = new Upgrade("Faster Reload", types[0], towerNames.get(11), 300);
        thisUpgrades[1][1] = new Upgrade("Rapid Reload", types[0], towerNames.get(11), 500);
        thisUpgrades[1][2] = new Upgrade("Heavy Shells", types[0], towerNames.get(11), 900);
        thisUpgrades[1][3] = new Upgrade("Artillery Battery", types[1], towerNames.get(11), 5500);
        thisUpgrades[1][4] = new Upgrade("Pop and Awe", types[1], towerNames.get(11), 30000);
        thisUpgrades[2][0] = new Upgrade("Increased Accuracy", types[0], towerNames.get(11), 200);
        thisUpgrades[2][1] = new Upgrade("Burny Stuff", types[0], towerNames.get(11), 500);
        thisUpgrades[2][2] = new Upgrade("Signal Flare", types[0], towerNames.get(11), 700);
        thisUpgrades[2][3] = new Upgrade("Shattering Shells", types[0], towerNames.get(11), 11000);
        thisUpgrades[2][4] = new Upgrade("Blooncineration", types[0], towerNames.get(11), 40000);
        Upgrade.defaultUpgrades.put(towerNames.get(11), thisUpgrades);
        // Dartling Gunner
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Focused Firing", types[0], towerNames.get(12), 300);
        thisUpgrades[0][1] = new Upgrade("Laser Shock", types[0], towerNames.get(12), 900);
        thisUpgrades[0][2] = new Upgrade("Laser Cannon", types[0], towerNames.get(12), 4250);
        thisUpgrades[0][3] = new Upgrade("Plasma Accelerator", types[0], towerNames.get(12), 11000);
        thisUpgrades[0][4] = new Upgrade("Ray of Doom", types[0], towerNames.get(12), 95000);
        thisUpgrades[1][0] = new Upgrade("Advanced Targeting", types[0], towerNames.get(12), 250);
        thisUpgrades[1][1] = new Upgrade("Faster Barrel Spin", types[0], towerNames.get(12), 950);
        thisUpgrades[1][2] = new Upgrade("Hydra Rocket pods", types[0], towerNames.get(12), 5250);
        thisUpgrades[1][3] = new Upgrade("Rocket Storm", types[1], towerNames.get(12), 5100);
        thisUpgrades[1][4] = new Upgrade("M.A.D", types[1], towerNames.get(12), 60000);
        thisUpgrades[2][0] = new Upgrade("Faster Swivel", types[0], towerNames.get(12), 150);
        thisUpgrades[2][1] = new Upgrade("Powerfull Darts", types[0], towerNames.get(12), 1200);
        thisUpgrades[2][2] = new Upgrade("Buckshot", types[0], towerNames.get(12), 3400);
        thisUpgrades[2][3] = new Upgrade("Bloon Area Denial System", types[0], towerNames.get(12), 12000);
        thisUpgrades[2][4] = new Upgrade("Bloon Exclusion Zone", types[0], towerNames.get(12), 58000);
        Upgrade.defaultUpgrades.put(towerNames.get(12), thisUpgrades);
        // Wizard Monkey
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Guided Magic", types[0], towerNames.get(13), 150);
        thisUpgrades[0][1] = new Upgrade("Arcane Blast", types[0], towerNames.get(13), 00);
        thisUpgrades[0][2] = new Upgrade("Arcane Mastery", types[0], towerNames.get(13), 1300);
        thisUpgrades[0][3] = new Upgrade("Arcane Spike", types[0], towerNames.get(13), 10900);
        thisUpgrades[0][4] = new Upgrade("Archmage", types[0], towerNames.get(13), 32000);
        thisUpgrades[1][0] = new Upgrade("Fireball", types[0], towerNames.get(13), 300);
        thisUpgrades[1][1] = new Upgrade("Wall of Fire", types[0], towerNames.get(13), 900);
        thisUpgrades[1][2] = new Upgrade("Dragon's Breath", types[0], towerNames.get(13), 3000);
        thisUpgrades[1][3] = new Upgrade("Summon Phoenix", types[1], towerNames.get(13), 4000);
        thisUpgrades[1][4] = new Upgrade("Wizard Lord Phoenix", types[1], towerNames.get(13), 54000);
        thisUpgrades[2][0] = new Upgrade("Intense Magic", types[0], towerNames.get(13), 300);
        thisUpgrades[2][1] = new Upgrade("Monkey Sense", types[0], towerNames.get(13), 300);
        thisUpgrades[2][2] = new Upgrade("Shimmer", types[0], towerNames.get(13), 1700);
        thisUpgrades[2][3] = new Upgrade("Necromancer: Unpopped Army", types[0], towerNames.get(13), 2800);
        thisUpgrades[2][4] = new Upgrade("Prince of Darkness", types[0], towerNames.get(13), 24000);
        Upgrade.defaultUpgrades.put(towerNames.get(13), thisUpgrades);
        // Super Monkey
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Laser Blasts", types[0], towerNames.get(14), 2500);
        thisUpgrades[0][1] = new Upgrade("Plasma Blasts", types[0], towerNames.get(14), 4500);
        thisUpgrades[0][2] = new Upgrade("Sun Avatar", types[0], towerNames.get(14), 22000);
        thisUpgrades[0][3] = new Upgrade("Sun Temple", types[0], towerNames.get(14), 100000);
        thisUpgrades[0][4] = new Upgrade("True Sun God", types[0], towerNames.get(14), 500000);
        thisUpgrades[1][0] = new Upgrade("Super Range", types[0], towerNames.get(14), 1000);
        thisUpgrades[1][1] = new Upgrade("Epic Range", types[0], towerNames.get(14), 1400);
        thisUpgrades[1][2] = new Upgrade("Robo Monkey", types[0], towerNames.get(14), 7000);
        thisUpgrades[1][3] = new Upgrade("Tech Terror", types[1], towerNames.get(14), 19000);
        thisUpgrades[1][4] = new Upgrade("The Anti-Bloon", types[1], towerNames.get(14), 90000);
        thisUpgrades[2][0] = new Upgrade("Knockback", types[0], towerNames.get(14), 3000);
        thisUpgrades[2][1] = new Upgrade("Ultravision", types[0], towerNames.get(14), 1200);
        thisUpgrades[2][2] = new Upgrade("Dark Knight", types[0], towerNames.get(14), 5500);
        thisUpgrades[2][3] = new Upgrade("Dark Champion", types[0], towerNames.get(14), 60000);
        thisUpgrades[2][4] = new Upgrade("Legend of the Night", types[0], towerNames.get(14), 240000);
        Upgrade.defaultUpgrades.put(towerNames.get(14), thisUpgrades);
        // Ninja Monkey
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Ninja Discipline", types[1], towerNames.get(14), 300);
        thisUpgrades[0][1] = new Upgrade("Sharp Shurikens", types[1], towerNames.get(14), 350);
        thisUpgrades[0][2] = new Upgrade("Double Shot", types[1], towerNames.get(14), 850);
        thisUpgrades[0][3] = new Upgrade("Bloonjitsu", types[1], towerNames.get(14), 2750);
        thisUpgrades[0][4] = new Upgrade("Grandmaster Ninja", types[1], towerNames.get(14), 35000);
        thisUpgrades[1][0] = new Upgrade("Distraction", types[1], towerNames.get(14), 350);
        thisUpgrades[1][1] = new Upgrade("Counter-Espionage", types[1], towerNames.get(14), 500);
        thisUpgrades[1][2] = new Upgrade("Shinobi Tactics", types[1], towerNames.get(14), 900);
        thisUpgrades[1][3] = new Upgrade("Bloon Sabotage", types[1], towerNames.get(15), 5200);
        thisUpgrades[1][4] = new Upgrade("Grand Saboteur", types[1], towerNames.get(15), 22000);
        thisUpgrades[2][0] = new Upgrade("Seeking Shurikens", types[1], towerNames.get(14), 250);
        thisUpgrades[2][1] = new Upgrade("Caltrops", types[1], towerNames.get(14), 400);
        thisUpgrades[2][2] = new Upgrade("Flash Bomb", types[1], towerNames.get(14), 750);
        thisUpgrades[2][3] = new Upgrade("Sticky Bomb", types[1], towerNames.get(14), 4500);
        thisUpgrades[2][4] = new Upgrade("Master Bomber", types[1], towerNames.get(14), 40000);
        Upgrade.defaultUpgrades.put(towerNames.get(15), thisUpgrades);
        // Alchemist
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Larger Potions", types[0], towerNames.get(16), 250);
        thisUpgrades[0][1] = new Upgrade("Acidic Mixture Dip", types[0], towerNames.get(16), 350);
        thisUpgrades[0][2] = new Upgrade("Berseker Brew", types[0], towerNames.get(16), 1250);
        thisUpgrades[0][3] = new Upgrade("Stronger Stimulant", types[0], towerNames.get(16), 3000);
        thisUpgrades[0][4] = new Upgrade("Permanent Brew", types[0], towerNames.get(16), 60000);
        thisUpgrades[1][0] = new Upgrade("Stronger Acid", types[0], towerNames.get(16), 250);
        thisUpgrades[1][1] = new Upgrade("Perishing Potions", types[0], towerNames.get(16), 475);
        thisUpgrades[1][2] = new Upgrade("Unstable Concotion", types[0], towerNames.get(16), 3000);
        thisUpgrades[1][3] = new Upgrade("Transforming Tonic", types[1], towerNames.get(16), 4500);
        thisUpgrades[1][4] = new Upgrade("Total Transformation", types[1], towerNames.get(16), 45000);
        thisUpgrades[2][0] = new Upgrade("Faster Throwing", types[0], towerNames.get(16), 650);
        thisUpgrades[2][1] = new Upgrade("Acid Pool", types[0], towerNames.get(16), 450);
        thisUpgrades[2][2] = new Upgrade("Lead to Gold", types[0], towerNames.get(16), 1000);
        thisUpgrades[2][3] = new Upgrade("Rubber to Gold", types[0], towerNames.get(16), 2750);
        thisUpgrades[2][4] = new Upgrade("Bloon Master Alchemist", types[0], towerNames.get(16), 40000);
        Upgrade.defaultUpgrades.put(towerNames.get(16), thisUpgrades);
        // Druid
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Hard Thorns", types[0], towerNames.get(17), 250);
        thisUpgrades[0][1] = new Upgrade("Heart of Thunder", types[0], towerNames.get(17), 1000);
        thisUpgrades[0][2] = new Upgrade("Druid of the Storm", types[0], towerNames.get(17), 1850);
        thisUpgrades[0][3] = new Upgrade("Ball Lightning", types[0], towerNames.get(17), 5100);
        thisUpgrades[0][4] = new Upgrade("Superstorm", types[0], towerNames.get(17), 90000);
        thisUpgrades[1][0] = new Upgrade("Thorn Swarm", types[0], towerNames.get(17), 250);
        thisUpgrades[1][1] = new Upgrade("Heart of Oak", types[0], towerNames.get(17), 350);
        thisUpgrades[1][2] = new Upgrade("Druid of the Jungle", types[0], towerNames.get(17), 950);
        thisUpgrades[1][3] = new Upgrade("Jungle's Bounty", types[1], towerNames.get(17), 5000);
        thisUpgrades[1][4] = new Upgrade("Spirit of the Forest", types[1], towerNames.get(17), 35000);
        thisUpgrades[2][0] = new Upgrade("Druidic Reachs", types[0], towerNames.get(17), 100);
        thisUpgrades[2][1] = new Upgrade("Heart of Vengeance", types[0], towerNames.get(17), 300);
        thisUpgrades[2][2] = new Upgrade("Druid of Wrath", types[0], towerNames.get(17), 600);
        thisUpgrades[2][3] = new Upgrade("Poplust", types[0], towerNames.get(17), 2500);
        thisUpgrades[2][4] = new Upgrade("Avatar of Wrath", types[0], towerNames.get(17), 45000);
        Upgrade.defaultUpgrades.put(towerNames.get(17), thisUpgrades);
        // Banana Farm
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Increased Production", types[0], towerNames.get(18), 500);
        thisUpgrades[0][1] = new Upgrade("Greater Production", types[0], towerNames.get(18), 600);
        thisUpgrades[0][2] = new Upgrade("Banana Plantation", types[0], towerNames.get(18), 3000);
        thisUpgrades[0][3] = new Upgrade("Banana Research Facility", types[0], towerNames.get(18), 19000);
        thisUpgrades[0][4] = new Upgrade("Banana Central", types[0], towerNames.get(18), 100000);
        thisUpgrades[1][0] = new Upgrade("Long Life Bananas", types[0], towerNames.get(18), 300);
        thisUpgrades[1][1] = new Upgrade("Valuable Bananas", types[0], towerNames.get(18), 800);
        thisUpgrades[1][2] = new Upgrade("Monkey Bank", types[0], towerNames.get(18), 3500);
        thisUpgrades[1][3] = new Upgrade("IMF Loan", types[1], towerNames.get(18), 7500);
        thisUpgrades[1][4] = new Upgrade("Monkey-Nomics", types[1], towerNames.get(18), 100000);
        thisUpgrades[2][0] = new Upgrade("EZ Collect", types[0], towerNames.get(18), 250);
        thisUpgrades[2][1] = new Upgrade("Banana Salvage", types[0], towerNames.get(18), 200);
        thisUpgrades[2][2] = new Upgrade("Marketplace", types[0], towerNames.get(18), 2900);
        thisUpgrades[2][3] = new Upgrade("Central Market", types[0], towerNames.get(18), 15000);
        thisUpgrades[2][4] = new Upgrade("Monkey Wall Street", types[0], towerNames.get(18), 60000);
        Upgrade.defaultUpgrades.put(towerNames.get(18), thisUpgrades);
        // Spike Factory
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Bigger Stacks", types[0], towerNames.get(19), 800);
        thisUpgrades[0][1] = new Upgrade("White Hot Spikes", types[0], towerNames.get(19), 600);
        thisUpgrades[0][2] = new Upgrade("Spiked Balls", types[0], towerNames.get(19), 2300);
        thisUpgrades[0][3] = new Upgrade("Spiked Mines", types[0], towerNames.get(19), 9500);
        thisUpgrades[0][4] = new Upgrade("Super Mines", types[0], towerNames.get(19), 162500);
        thisUpgrades[1][0] = new Upgrade("Faster Production", types[0], towerNames.get(19), 600);
        thisUpgrades[1][1] = new Upgrade("Even Faster Production", types[0], towerNames.get(19), 800);
        thisUpgrades[1][2] = new Upgrade("MOAB Shredder", types[0], towerNames.get(19), 2500);
        thisUpgrades[1][3] = new Upgrade("Spike Storm", types[1], towerNames.get(19), 5000);
        thisUpgrades[1][4] = new Upgrade("Carpet of Spikes", types[1], towerNames.get(19), 40000);
        thisUpgrades[2][0] = new Upgrade("Long Reach", types[0], towerNames.get(19), 150);
        thisUpgrades[2][1] = new Upgrade("Smart Spikes", types[0], towerNames.get(19), 400);
        thisUpgrades[2][2] = new Upgrade("Long Life Spikes", types[0], towerNames.get(19), 1400);
        thisUpgrades[2][3] = new Upgrade("Deadly Spikes", types[0], towerNames.get(19), 3500);
        thisUpgrades[2][4] = new Upgrade("Perma Spike", types[0], towerNames.get(19), 30000);
        Upgrade.defaultUpgrades.put(towerNames.get(19), thisUpgrades);
        // Monkey Village
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Bigger Radius", types[0], towerNames.get(20), 400);
        thisUpgrades[0][1] = new Upgrade("Jungle Drums", types[0], towerNames.get(20), 1500);
        thisUpgrades[0][2] = new Upgrade("Primary Training", types[0], towerNames.get(20), 800);
        thisUpgrades[0][3] = new Upgrade("Primary Mentoring", types[0], towerNames.get(20), 2500);
        thisUpgrades[0][4] = new Upgrade("Primary Expertise", types[0], towerNames.get(20), 25000);
        thisUpgrades[1][0] = new Upgrade("Grow Blocker", types[0], towerNames.get(20), 250);
        thisUpgrades[1][1] = new Upgrade("Radar Scanner", types[0], towerNames.get(20), 2000);
        thisUpgrades[1][2] = new Upgrade("Monkey Intelligence Bureau", types[0], towerNames.get(20), 7500);
        thisUpgrades[1][3] = new Upgrade("Call to Arms", types[1], towerNames.get(20), 20000);
        thisUpgrades[1][4] = new Upgrade("Homeland Defense", types[1], towerNames.get(20), 40000);
        thisUpgrades[2][0] = new Upgrade("Monkey Business", types[0], towerNames.get(20), 500);
        thisUpgrades[2][1] = new Upgrade("Monkey Comerce", types[0], towerNames.get(20), 500);
        thisUpgrades[2][2] = new Upgrade("Monkey Town", types[0], towerNames.get(20), 10000);
        thisUpgrades[2][3] = new Upgrade("Monkey City", types[0], towerNames.get(20), 3000);
        thisUpgrades[2][4] = new Upgrade("Monkeyopolis", types[0], towerNames.get(20), 5000);
        Upgrade.defaultUpgrades.put(towerNames.get(20), thisUpgrades);
        // Engineer Monkey
        thisUpgrades = new Upgrade[3][5];
        thisUpgrades[0][0] = new Upgrade("Sentry Gun", types[0], towerNames.get(21), 500);
        thisUpgrades[0][1] = new Upgrade("Faster Engineering", types[0], towerNames.get(21), 400);
        thisUpgrades[0][2] = new Upgrade("Sprockets", types[0], towerNames.get(21), 525);
        thisUpgrades[0][3] = new Upgrade("Sentry Expert", types[0], towerNames.get(21), 2500);
        thisUpgrades[0][4] = new Upgrade("Sentry Paragon", types[0], towerNames.get(21), 32000);
        thisUpgrades[1][0] = new Upgrade("Larger Service Area", types[0], towerNames.get(21), 250);
        thisUpgrades[1][1] = new Upgrade("Deconstruction", types[0], towerNames.get(21), 350);
        thisUpgrades[1][2] = new Upgrade("Cleansing Foam", types[0], towerNames.get(21), 800);
        thisUpgrades[1][3] = new Upgrade("Overclock", types[1], towerNames.get(21), 13500);
        thisUpgrades[1][4] = new Upgrade("Ultraboost", types[1], towerNames.get(21), 120000);
        thisUpgrades[2][0] = new Upgrade("Oversize Nails", types[0], towerNames.get(21), 450);
        thisUpgrades[2][1] = new Upgrade("Pin", types[0], towerNames.get(21), 220);
        thisUpgrades[2][2] = new Upgrade("Double Gun", types[0], towerNames.get(21), 500);
        thisUpgrades[2][3] = new Upgrade("Bloon Trap", types[0], towerNames.get(21), 3500);
        thisUpgrades[2][4] = new Upgrade("XXXL Trap", types[0], towerNames.get(21), 54000);
        Upgrade.defaultUpgrades.put(towerNames.get(21), thisUpgrades);
        // Default upgrades saved in memory!
        Upgrade.isInitDefaultUpgrades = true;
    }

    public String getTowerName() {return this.towerName;}

    public void setTowerName(String towerName) {this.towerName = towerName;}

    public int getCost() {return this.cost;}

    public void setCost(int baseCost) {this.cost = baseCost;}

    public boolean isApplied() {return this.isApplied;}

    public void setApplied(boolean isApplied) {this.isApplied = isApplied;}

    public boolean isLocked() {return this.isLocked;}

    public void setLocked(boolean isLocked) {this.isLocked = isLocked;}

    public short getPath() {return this.path;}

    public void setPath(short path) {this.path = path;}

    public short getTier() {return this.tier;}

    public void setTier(short tier)  {this.tier = tier;}

    @Override
    public String createString() {
        StringBuffer sb = new StringBuffer();
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
    public String toString() {return new Gson().toJson(this);}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + cost;
        result = prime * result + (isApplied ? 1231 : 1237);
        result = prime * result + (isLocked ? 1231 : 1237);
        result = prime * result + path;
        result = prime * result + tier;
        result = prime * result + ((towerName == null) ? 0 : towerName.hashCode());
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
        Upgrade other = (Upgrade) obj;
        if (cost != other.cost)
            return false;
        if (isApplied != other.isApplied)
            return false;
        if (isLocked != other.isLocked)
            return false;
        if (path != other.path)
            return false;
        if (tier != other.tier)
            return false;
        if (towerName == null) {
            if (other.towerName != null)
                return false;
        } else if (!towerName.equals(other.towerName))
            return false;
        return true;
    }
}
