package com.paulkapa.btd6gamelogger.models.game;

import java.util.ArrayList;
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

	private static ArrayList<Upgrade> inUseUpgrades = null;
	private static LinkedHashMap<String, Upgrade[][]> upgrades = null;
	private static boolean isUpgradesInit = false;

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

	public static int countUnlockedUpgrades(ArrayList<Upgrade[][]> upgradesList) {
		int count = 0;
		if(upgradesList != null)
			for(Upgrade[][] u : upgradesList)
				for(int i = 0; i < u.length; i++)
					for(int j = 0; j < u[i].length; i++)
						if(!u[i][j].isLocked()) count++;
		return count;
	}

	public static int countAppliedUpgrades(ArrayList<Upgrade[][]> upgradesList) {
		int count = 0;
		if(upgradesList != null)
			for(Upgrade[][] u : upgradesList)
				for(int i = 0; i < u.length; i++)
					for(int j = 0; j < u[i].length; i++)
						if(!u[i][j].isApplied()) count++;
		return count;
	}

	public static ArrayList<Upgrade> getInUseUpgrades() {return Upgrade.inUseUpgrades;}

	public static void setInUseUpgrades(ArrayList<Upgrade> inUseUpgrades) {Upgrade.inUseUpgrades = inUseUpgrades;}

	public static void useUpgrade(Upgrade upgrade) {
		if(Upgrade.inUseUpgrades == null) Upgrade.inUseUpgrades = new ArrayList<>();
		Upgrade.inUseUpgrades.add(upgrade);
	}

	public static LinkedHashMap<String, Upgrade[][]> getUpgrades() throws Exception {
		if(!Upgrade.isUpgradesInit()) Upgrade.initUpgrades();
		return Upgrade.upgrades;
	}

	public static Upgrade[][] getUpgradesByTowerName(String towerName) throws Exception {
		Upgrade[][] result = null;
		if(!Upgrade.isUpgradesInit()) Upgrade.initUpgrades();
		for(String name : Upgrade.upgrades.keySet())
			if(name.equals(towerName) && result == null) {result = Upgrade.upgrades.get(name); break;}
		if(result == null) throw new Exception(
			"No result matching the criteria!",
			new Throwable("No upgrades found for tower with name '" + towerName + "'.", new Throwable()));
		return result;
	}

	private static void initUpgrades() throws Exception {
		Upgrade.upgrades = new LinkedHashMap<>();
		Upgrade[][] thisUpgrades;
		String[] types = {"Upgrade", "Ability"};
		ArrayList<String> names = Tower.getTowersNameList();
		//Dart Monkey
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(0), types[0], "Sharp Shots", 140);
		thisUpgrades[0][1] = new Upgrade(names.get(0), types[0], "Razor Sharp Shots", 220);
		thisUpgrades[0][2] = new Upgrade(names.get(0), types[0], "Spike-o-pult", 300);
		thisUpgrades[0][3] = new Upgrade(names.get(0), types[0], "Juggernaut", 1800);
		thisUpgrades[0][4] = new Upgrade(names.get(0), types[0], "Ultra-Juggernaut", 15000);
		thisUpgrades[1][0] = new Upgrade(names.get(0), types[0], "Quick Shots", 100);
		thisUpgrades[1][1] = new Upgrade(names.get(0), types[0], "Very Quick Shots", 190);
		thisUpgrades[1][2] = new Upgrade(names.get(0), types[0], "Triple Shot", 400);
		thisUpgrades[1][3] = new Upgrade(names.get(0), types[1], "Super Monkey Fan Club", 8000);
		thisUpgrades[1][4] = new Upgrade(names.get(0), types[1], "Plasma Monkey Fan Club", 45000);
		thisUpgrades[2][0] = new Upgrade(names.get(0), types[0], "Long Range Darts", 90);
		thisUpgrades[2][1] = new Upgrade(names.get(0), types[0], "Enhanced Eyesight", 200);
		thisUpgrades[2][2] = new Upgrade(names.get(0), types[0], "Crossbow", 625);
		thisUpgrades[2][3] = new Upgrade(names.get(0), types[0], "Sharp Shooter", 2000);
		thisUpgrades[2][4] = new Upgrade(names.get(0), types[0], "Crossbow Master", 25000);
		Upgrade.upgrades.put(names.get(0), thisUpgrades);
		//Boomerang Monkey
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(1), types[0], "Improved Rangs", 200);
		thisUpgrades[0][1] = new Upgrade(names.get(1), types[0], "Glaives", 280);
		thisUpgrades[0][2] = new Upgrade(names.get(1), types[0], "Glaives Ricochet", 1300);
		thisUpgrades[0][3] = new Upgrade(names.get(1), types[0], "M.O.A.R Glaives", 3000);
		thisUpgrades[0][4] = new Upgrade(names.get(1), types[0], "Glaive Lord", 32400);
		thisUpgrades[1][0] = new Upgrade(names.get(1), types[0], "Faster Throwing", 175);
		thisUpgrades[1][1] = new Upgrade(names.get(1), types[0], "Faster Rangs", 250);
		thisUpgrades[1][2] = new Upgrade(names.get(1), types[0], "Bionic Boomerang", 1600);
		thisUpgrades[1][3] = new Upgrade(names.get(1), types[1], "Turbo Charge", 4000);
		thisUpgrades[1][4] = new Upgrade(names.get(1), types[1], "Perma Charge", 35000);
		thisUpgrades[2][0] = new Upgrade(names.get(1), types[0], "Long Range Rangs", 100);
		thisUpgrades[2][1] = new Upgrade(names.get(1), types[0], "Red Hot Rangs", 300);
		thisUpgrades[2][2] = new Upgrade(names.get(1), types[0], "Kylie Boomerang", 1300);
		thisUpgrades[2][3] = new Upgrade(names.get(1), types[0], "MOAB Press", 2200);
		thisUpgrades[2][4] = new Upgrade(names.get(1), types[0], "MOAB Domination", 60000);
		Upgrade.upgrades.put(names.get(1), thisUpgrades);
		//Bomb Shooter
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(2), types[0], "Bigger Bombs", 350);
		thisUpgrades[0][1] = new Upgrade(names.get(2), types[0], "Heavy Bombs", 650);
		thisUpgrades[0][2] = new Upgrade(names.get(2), types[0], "Really Big Bombs", 1200);
		thisUpgrades[0][3] = new Upgrade(names.get(2), types[0], "Bloon Impact", 3600);
		thisUpgrades[0][4] = new Upgrade(names.get(2), types[0], "Bloon Crush", 55000);
		thisUpgrades[1][0] = new Upgrade(names.get(2), types[0], "Faster Reload", 250);
		thisUpgrades[1][1] = new Upgrade(names.get(2), types[0], "Missile Launcher", 400);
		thisUpgrades[1][2] = new Upgrade(names.get(2), types[0], "MOAB Mauler", 900);
		thisUpgrades[1][3] = new Upgrade(names.get(2), types[1], "MOAB Assassin", 3200);
		thisUpgrades[1][4] = new Upgrade(names.get(2), types[1], "MOAB Eliminator", 25000);
		thisUpgrades[2][0] = new Upgrade(names.get(2), types[0], "Extra Range", 200);
		thisUpgrades[2][1] = new Upgrade(names.get(2), types[0], "Frag Bombs", 300);
		thisUpgrades[2][2] = new Upgrade(names.get(2), types[0], "Cluster Bombs", 800);
		thisUpgrades[2][3] = new Upgrade(names.get(2), types[0], "Recursive Cluster", 2800);
		thisUpgrades[2][4] = new Upgrade(names.get(2), types[0], "Bomb Blitz", 35000);
		Upgrade.upgrades.put(names.get(2), thisUpgrades);
		//Tack Shooter
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(3), types[0], "Faster Shooting", 150);
		thisUpgrades[0][1] = new Upgrade(names.get(3), types[0], "Even Faster Shooting", 300);
		thisUpgrades[0][2] = new Upgrade(names.get(3), types[0], "Hot Shots", 600);
		thisUpgrades[0][3] = new Upgrade(names.get(3), types[0], "Ring of Fire", 3500);
		thisUpgrades[0][4] = new Upgrade(names.get(3), types[0], "Inferno Ring", 45500);
		thisUpgrades[1][0] = new Upgrade(names.get(3), types[0], "Long Range Tacks", 100);
		thisUpgrades[1][1] = new Upgrade(names.get(3), types[0], "Super Range Tacks", 225);
		thisUpgrades[1][2] = new Upgrade(names.get(3), types[0], "Blade Shooter", 550);
		thisUpgrades[1][3] = new Upgrade(names.get(3), types[1], "Blade Maelstorm", 2700);
		thisUpgrades[1][4] = new Upgrade(names.get(3), types[1], "Super Maelstorm", 15000);
		thisUpgrades[2][0] = new Upgrade(names.get(3), types[0], "More Tacks", 100);
		thisUpgrades[2][1] = new Upgrade(names.get(3), types[0], "Even More Tacks", 100);
		thisUpgrades[2][2] = new Upgrade(names.get(3), types[0], "Tack Sprayer", 450);
		thisUpgrades[2][3] = new Upgrade(names.get(3), types[0], "Overdrive", 3200);
		thisUpgrades[2][4] = new Upgrade(names.get(3), types[0], "The Tack Zone", 20000);
		Upgrade.upgrades.put(names.get(3), thisUpgrades);
		//Ice Monkey
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(4), types[0], "Permafrost", 100);
		thisUpgrades[0][1] = new Upgrade(names.get(4), types[0], "Metal Freeze", 300);
		thisUpgrades[0][2] = new Upgrade(names.get(4), types[0], "Ice Shards", 1500);
		thisUpgrades[0][3] = new Upgrade(names.get(4), types[0], "Embrittlement", 2200);
		thisUpgrades[0][4] = new Upgrade(names.get(4), types[0], "Super Brittle", 28000);
		thisUpgrades[1][0] = new Upgrade(names.get(4), types[0], "Enhanced Freeze", 225);
		thisUpgrades[1][1] = new Upgrade(names.get(4), types[0], "Deep Freeze", 350);
		thisUpgrades[1][2] = new Upgrade(names.get(4), types[0], "Arctic Wind", 2900);
		thisUpgrades[1][3] = new Upgrade(names.get(4), types[1], "Snowstorm", 3000);
		thisUpgrades[1][4] = new Upgrade(names.get(4), types[1], "Absolute Zero", 26000);
		thisUpgrades[2][0] = new Upgrade(names.get(4), types[0], "Larger Radius", 100);
		thisUpgrades[2][1] = new Upgrade(names.get(4), types[0], "Re-Freeze", 200);
		thisUpgrades[2][2] = new Upgrade(names.get(4), types[0], "Cry Cannon", 1750);
		thisUpgrades[2][3] = new Upgrade(names.get(4), types[0], "Icicles", 2000);
		thisUpgrades[2][4] = new Upgrade(names.get(4), types[0], "Icicle Impale", 30000);
		Upgrade.upgrades.put(names.get(4), thisUpgrades);
		//Glue Gunner
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(5), types[0], "Glue Soak", 200);
		thisUpgrades[0][1] = new Upgrade(names.get(5), types[0], "Corrosive Glue", 300);
		thisUpgrades[0][2] = new Upgrade(names.get(5), types[0], "Bloon Dissolver", 2500);
		thisUpgrades[0][3] = new Upgrade(names.get(5), types[0], "Bloon Liquefier", 5000);
		thisUpgrades[0][4] = new Upgrade(names.get(5), types[0], "The Bloon Solver", 22000);
		thisUpgrades[1][0] = new Upgrade(names.get(5), types[0], "Bigger Globs", 100);
		thisUpgrades[1][1] = new Upgrade(names.get(5), types[0], "Glue Splatter", 1800);
		thisUpgrades[1][2] = new Upgrade(names.get(5), types[0], "Glue Hose", 3250);
		thisUpgrades[1][3] = new Upgrade(names.get(5), types[1], "Glue Strike", 3500);
		thisUpgrades[1][4] = new Upgrade(names.get(5), types[1], "Glue Storm", 15000);
		thisUpgrades[2][0] = new Upgrade(names.get(5), types[0], "Stickier Glue", 120);
		thisUpgrades[2][1] = new Upgrade(names.get(5), types[0], "Stringer Glue", 400);
		thisUpgrades[2][2] = new Upgrade(names.get(5), types[0], "MOAB Glue", 3400);
		thisUpgrades[2][3] = new Upgrade(names.get(5), types[0], "Relentless Glue", 3000);
		thisUpgrades[2][4] = new Upgrade(names.get(5), types[0], "Super Glue", 28000);
		Upgrade.upgrades.put(names.get(5), thisUpgrades);
		//Sniper Monkey
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(6), types[0], "Full Metal Jacket", 350);
		thisUpgrades[0][1] = new Upgrade(names.get(6), types[0], "Large Calibre", 1500);
		thisUpgrades[0][2] = new Upgrade(names.get(6), types[0], "Deadly Precision", 3000);
		thisUpgrades[0][3] = new Upgrade(names.get(6), types[0], "Maim MOAB", 5000);
		thisUpgrades[0][4] = new Upgrade(names.get(6), types[0], "Cripple MOAB", 34000);
		thisUpgrades[1][0] = new Upgrade(names.get(6), types[0], "Night Vision Goggles", 300);
		thisUpgrades[1][1] = new Upgrade(names.get(6), types[0], "Sharpnel Shot", 450);
		thisUpgrades[1][2] = new Upgrade(names.get(6), types[0], "Bouncing Bullet", 3200);
		thisUpgrades[1][3] = new Upgrade(names.get(6), types[1], "Supply Drop", 7200);
		thisUpgrades[1][4] = new Upgrade(names.get(6), types[1], "Elite Sniper", 13000);
		thisUpgrades[2][0] = new Upgrade(names.get(6), types[0], "Fast Firing", 400);
		thisUpgrades[2][1] = new Upgrade(names.get(6), types[0], "Even Faster Firing", 400);
		thisUpgrades[2][2] = new Upgrade(names.get(6), types[0], "Semi-Automatic", 3500);
		thisUpgrades[2][3] = new Upgrade(names.get(6), types[0], "Full Auto Rifle", 4750);
		thisUpgrades[2][4] = new Upgrade(names.get(6), types[0], "Elite Defender", 14000);
		Upgrade.upgrades.put(names.get(6), thisUpgrades);
		//Monkey Sub
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(7), types[0], "Longer Range", 130);
		thisUpgrades[0][1] = new Upgrade(names.get(7), types[0], "Advanced Intel", 500);
		thisUpgrades[0][2] = new Upgrade(names.get(7), types[0], "Submerge and Support", 500);
		thisUpgrades[0][3] = new Upgrade(names.get(7), types[0], "Bloontonium Reactor", 2500);
		thisUpgrades[0][4] = new Upgrade(names.get(7), types[0], "Energizer", 32000);
		thisUpgrades[1][0] = new Upgrade(names.get(7), types[0], "Barbed Darts", 450);
		thisUpgrades[1][1] = new Upgrade(names.get(7), types[0], "Heat-tipped Darts", 300);
		thisUpgrades[1][2] = new Upgrade(names.get(7), types[0], "Balistic Missile", 1400);
		thisUpgrades[1][3] = new Upgrade(names.get(7), types[1], "First Strike Capability", 13000);
		thisUpgrades[1][4] = new Upgrade(names.get(7), types[1], "Pre-emptive Strike", 32000);
		thisUpgrades[2][0] = new Upgrade(names.get(7), types[0], "Twin Guns", 450);
		thisUpgrades[2][1] = new Upgrade(names.get(7), types[0], "Airburst Darts", 1000);
		thisUpgrades[2][2] = new Upgrade(names.get(7), types[0], "Triple Guns", 1100);
		thisUpgrades[2][3] = new Upgrade(names.get(7), types[0], "Armor Piercing Darts", 3000);
		thisUpgrades[2][4] = new Upgrade(names.get(7), types[0], "Sub Commander", 25000);
		Upgrade.upgrades.put(names.get(7), thisUpgrades);
		//Monkey Buccaneer
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(8), types[0], "Faster Shooting", 350);
		thisUpgrades[0][1] = new Upgrade(names.get(8), types[0], "Double Shot", 550);
		thisUpgrades[0][2] = new Upgrade(names.get(8), types[0], "Destroyer", 2950);
		thisUpgrades[0][3] = new Upgrade(names.get(8), types[0], "Aircraft Carrier", 7200);
		thisUpgrades[0][4] = new Upgrade(names.get(8), types[0], "Carrier Flagship", 25000);
		thisUpgrades[1][0] = new Upgrade(names.get(8), types[0], "Grape Shot", 550);
		thisUpgrades[1][1] = new Upgrade(names.get(8), types[0], "Hot Shot", 500);
		thisUpgrades[1][2] = new Upgrade(names.get(8), types[0], "Cannon Ship", 900);
		thisUpgrades[1][3] = new Upgrade(names.get(8), types[1], "Monkey Pirates", 4500);
		thisUpgrades[1][4] = new Upgrade(names.get(8), types[1], "Pirate Lord", 21000);
		thisUpgrades[2][0] = new Upgrade(names.get(8), types[0], "Long Range", 180);
		thisUpgrades[2][1] = new Upgrade(names.get(8), types[0], "Crow's Nest", 400);
		thisUpgrades[2][2] = new Upgrade(names.get(8), types[0], "Merchantman", 2300);
		thisUpgrades[2][3] = new Upgrade(names.get(8), types[0], "Favored Trades", 5500);
		thisUpgrades[2][4] = new Upgrade(names.get(8), types[0], "Trade Empire", 23000);
		Upgrade.upgrades.put(names.get(8), thisUpgrades);
		//Monkey Ace
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(9), types[0], "Rapid Fire", 650);
		thisUpgrades[0][1] = new Upgrade(names.get(9), types[0], "Lots More Darts", 650);
		thisUpgrades[0][2] = new Upgrade(names.get(9), types[0], "Fighter Plane", 1000);
		thisUpgrades[0][3] = new Upgrade(names.get(9), types[0], "Operation: Dart Storm", 3000);
		thisUpgrades[0][4] = new Upgrade(names.get(9), types[0], "Sky Shredder", 40000);
		thisUpgrades[1][0] = new Upgrade(names.get(9), types[0], "Exploding Pineapple", 200);
		thisUpgrades[1][1] = new Upgrade(names.get(9), types[0], "Spy Plane", 350);
		thisUpgrades[1][2] = new Upgrade(names.get(9), types[0], "Bomber Ace", 900);
		thisUpgrades[1][3] = new Upgrade(names.get(9), types[1], "Ground Zero", 14000);
		thisUpgrades[1][4] = new Upgrade(names.get(9), types[1], "Tsar Bomba", 35000);
		thisUpgrades[2][0] = new Upgrade(names.get(9), types[0], "Sharper Darts", 500);
		thisUpgrades[2][1] = new Upgrade(names.get(9), types[0], "Centered Path", 300);
		thisUpgrades[2][2] = new Upgrade(names.get(9), types[0], "Neva-Miss Targeting", 2200);
		thisUpgrades[2][3] = new Upgrade(names.get(9), types[0], "Spectre", 24000);
		thisUpgrades[2][4] = new Upgrade(names.get(9), types[0], "Flying Fortress", 100000);
		Upgrade.upgrades.put(names.get(9), thisUpgrades);
		//Heli Pilot
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(10), types[0], "Quad Darts", 800);
		thisUpgrades[0][1] = new Upgrade(names.get(10), types[0], "Pursuit", 500);
		thisUpgrades[0][2] = new Upgrade(names.get(10), types[0], "Razor Rotors", 1750);
		thisUpgrades[0][3] = new Upgrade(names.get(10), types[0], "Apache Dartship", 19600);
		thisUpgrades[0][4] = new Upgrade(names.get(10), types[0], "Apache Prime", 45000);
		thisUpgrades[1][0] = new Upgrade(names.get(10), types[0], "Bigger Jets", 300);
		thisUpgrades[1][1] = new Upgrade(names.get(10), types[0], "IFR", 600);
		thisUpgrades[1][2] = new Upgrade(names.get(10), types[0], "Downdraft", 3000);
		thisUpgrades[1][3] = new Upgrade(names.get(10), types[1], "Support Chinook", 12000);
		thisUpgrades[1][4] = new Upgrade(names.get(10), types[1], "Special Poperations", 30000);
		thisUpgrades[2][0] = new Upgrade(names.get(10), types[0], "Faster Darts", 250);
		thisUpgrades[2][1] = new Upgrade(names.get(10), types[0], "Faster Firing", 350);
		thisUpgrades[2][2] = new Upgrade(names.get(10), types[0], "MOAB Shove", 3500);
		thisUpgrades[2][3] = new Upgrade(names.get(10), types[0], "Comanche Defense", 8500);
		thisUpgrades[2][4] = new Upgrade(names.get(10), types[0], "Comanche Commander", 35000);
		Upgrade.upgrades.put(names.get(10), thisUpgrades);
		//Mortar Monkey
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(11), types[0], "Bigger Blast", 500);
		thisUpgrades[0][1] = new Upgrade(names.get(11), types[0], "Bloon Buster", 650);
		thisUpgrades[0][2] = new Upgrade(names.get(11), types[0], "Shell Shock", 1100);
		thisUpgrades[0][3] = new Upgrade(names.get(11), types[0], "The Big One", 8000);
		thisUpgrades[0][4] = new Upgrade(names.get(11), types[0], "The Biggest One", 28000);
		thisUpgrades[1][0] = new Upgrade(names.get(11), types[0], "Faster Reload", 300);
		thisUpgrades[1][1] = new Upgrade(names.get(11), types[0], "Rapid Reload", 500);
		thisUpgrades[1][2] = new Upgrade(names.get(11), types[0], "Heavy Shells", 900);
		thisUpgrades[1][3] = new Upgrade(names.get(11), types[1], "Artillery Battery", 5500);
		thisUpgrades[1][4] = new Upgrade(names.get(11), types[1], "Pop and Awe", 30000);
		thisUpgrades[2][0] = new Upgrade(names.get(11), types[0], "Increased Accuracy", 200);
		thisUpgrades[2][1] = new Upgrade(names.get(11), types[0], "Burny Stuff", 500);
		thisUpgrades[2][2] = new Upgrade(names.get(11), types[0], "Signal Flare", 700);
		thisUpgrades[2][3] = new Upgrade(names.get(11), types[0], "Shattering Shells", 11000);
		thisUpgrades[2][4] = new Upgrade(names.get(11), types[0], "Blooncineration", 40000);
		Upgrade.upgrades.put(names.get(11), thisUpgrades);
		//Dartling Gunner
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(12), types[0], "Focused Firing", 300);
		thisUpgrades[0][1] = new Upgrade(names.get(12), types[0], "Laser Shock", 900);
		thisUpgrades[0][2] = new Upgrade(names.get(12), types[0], "Laser Cannon", 4250);
		thisUpgrades[0][3] = new Upgrade(names.get(12), types[0], "Plasma Accelerator", 11000);
		thisUpgrades[0][4] = new Upgrade(names.get(12), types[0], "Ray of Doom", 95000);
		thisUpgrades[1][0] = new Upgrade(names.get(12), types[0], "Advanced Targeting", 250);
		thisUpgrades[1][1] = new Upgrade(names.get(12), types[0], "Faster Barrel Spin", 950);
		thisUpgrades[1][2] = new Upgrade(names.get(12), types[0], "Hydra Rocket pods", 5250);
		thisUpgrades[1][3] = new Upgrade(names.get(12), types[1], "Rocket Storm", 5100);
		thisUpgrades[1][4] = new Upgrade(names.get(12), types[1], "M.A.D", 60000);
		thisUpgrades[2][0] = new Upgrade(names.get(12), types[0], "Faster Swivel", 150);
		thisUpgrades[2][1] = new Upgrade(names.get(12), types[0], "Powerfull Darts", 1200);
		thisUpgrades[2][2] = new Upgrade(names.get(12), types[0], "Buckshot", 3400);
		thisUpgrades[2][3] = new Upgrade(names.get(12), types[0], "Bloon Area Denial System", 12000);
		thisUpgrades[2][4] = new Upgrade(names.get(12), types[0], "Bloon Exclusion Zone", 58000);
		Upgrade.upgrades.put(names.get(12), thisUpgrades);
		//Wizard Monkey
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(13), types[0], "Guided Magic", 150);
		thisUpgrades[0][1] = new Upgrade(names.get(13), types[0], "Arcane Blast", 600);
		thisUpgrades[0][2] = new Upgrade(names.get(13), types[0], "Arcane Mastery", 1300);
		thisUpgrades[0][3] = new Upgrade(names.get(13), types[0], "Arcane Spike", 10900);
		thisUpgrades[0][4] = new Upgrade(names.get(13), types[0], "Archmage", 32000);
		thisUpgrades[1][0] = new Upgrade(names.get(13), types[0], "Fireball", 300);
		thisUpgrades[1][1] = new Upgrade(names.get(13), types[0], "Wall of Fire", 900);
		thisUpgrades[1][2] = new Upgrade(names.get(13), types[0], "Dragon's Breath", 3000);
		thisUpgrades[1][3] = new Upgrade(names.get(13), types[1], "Summon Phoenix", 4000);
		thisUpgrades[1][4] = new Upgrade(names.get(13), types[1], "Wizard Lord Phoenix", 54000);
		thisUpgrades[2][0] = new Upgrade(names.get(13), types[0], "Intense Magic", 300);
		thisUpgrades[2][1] = new Upgrade(names.get(13), types[0], "Monkey Sense", 300);
		thisUpgrades[2][2] = new Upgrade(names.get(13), types[0], "Shimmer", 1700);
		thisUpgrades[2][3] = new Upgrade(names.get(13), types[0], "Necromancer: Unpopped Army", 2800);
		thisUpgrades[2][4] = new Upgrade(names.get(13), types[0], "Prince of Darkness", 24000);
		Upgrade.upgrades.put(names.get(13), thisUpgrades);
		//Super Monkey
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(14), types[0], "Laser Blasts", 2500);
		thisUpgrades[0][1] = new Upgrade(names.get(14), types[0], "Plasma Blasts", 4500);
		thisUpgrades[0][2] = new Upgrade(names.get(14), types[0], "Sun Avatar", 22000);
		thisUpgrades[0][3] = new Upgrade(names.get(14), types[0], "Sun Temple", 100000);
		thisUpgrades[0][4] = new Upgrade(names.get(14), types[0], "True Sun God", 500000);
		thisUpgrades[1][0] = new Upgrade(names.get(14), types[0], "Super Range", 1000);
		thisUpgrades[1][1] = new Upgrade(names.get(14), types[0], "Epic Range", 1400);
		thisUpgrades[1][2] = new Upgrade(names.get(14), types[0], "Robo Monkey", 7000);
		thisUpgrades[1][3] = new Upgrade(names.get(14), types[1], "Tech Terror", 19000);
		thisUpgrades[1][4] = new Upgrade(names.get(14), types[1], "The Anti-Bloon", 90000);
		thisUpgrades[2][0] = new Upgrade(names.get(14), types[0], "Knockback", 3000);
		thisUpgrades[2][1] = new Upgrade(names.get(14), types[0], "Ultravision", 1200);
		thisUpgrades[2][2] = new Upgrade(names.get(14), types[0], "Dark Knight", 5500);
		thisUpgrades[2][3] = new Upgrade(names.get(14), types[0], "Dark Champion", 60000);
		thisUpgrades[2][4] = new Upgrade(names.get(14), types[0], "Legend of the Night", 240000);
		Upgrade.upgrades.put(names.get(14), thisUpgrades);
		//Ninja Monkey
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(15), types[0], "Ninja Discipline", 300);
		thisUpgrades[0][1] = new Upgrade(names.get(15), types[0], "Sharp Shurikens", 350);
		thisUpgrades[0][2] = new Upgrade(names.get(15), types[0], "Double Shot", 850);
		thisUpgrades[0][3] = new Upgrade(names.get(15), types[0], "Bloonjitsu", 2750);
		thisUpgrades[0][4] = new Upgrade(names.get(15), types[0], "Grandmaster Ninja", 35000);
		thisUpgrades[1][0] = new Upgrade(names.get(15), types[0], "Distraction", 350);
		thisUpgrades[1][1] = new Upgrade(names.get(15), types[0], "Counter-Espionage", 500);
		thisUpgrades[1][2] = new Upgrade(names.get(15), types[0], "Shinobi Tactics", 900);
		thisUpgrades[1][3] = new Upgrade(names.get(15), types[1], "Bloon Sabotage", 5200);
		thisUpgrades[1][4] = new Upgrade(names.get(15), types[1], "Grand Saboteur", 22000);
		thisUpgrades[2][0] = new Upgrade(names.get(15), types[0], "Seeking Shurikens", 250);
		thisUpgrades[2][1] = new Upgrade(names.get(15), types[0], "Caltrops", 400);
		thisUpgrades[2][2] = new Upgrade(names.get(15), types[0], "Flash Bomb", 2750);
		thisUpgrades[2][3] = new Upgrade(names.get(15), types[0], "Sticky Bomb", 4500);
		thisUpgrades[2][4] = new Upgrade(names.get(15), types[0], "Master Bomber", 40000);
		Upgrade.upgrades.put(names.get(15), thisUpgrades);
		//Alchemist
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(16), types[0], "Larger Potions", 250);
		thisUpgrades[0][1] = new Upgrade(names.get(16), types[0], "Acidic Mixture Dip", 350);
		thisUpgrades[0][2] = new Upgrade(names.get(16), types[0], "Berseker Brew", 1250);
		thisUpgrades[0][3] = new Upgrade(names.get(16), types[0], "Stronger Stimulant", 3000);
		thisUpgrades[0][4] = new Upgrade(names.get(16), types[0], "Permanent Brew", 60000);
		thisUpgrades[1][0] = new Upgrade(names.get(16), types[0], "Stronger Acid", 250);
		thisUpgrades[1][1] = new Upgrade(names.get(16), types[0], "Perishing Potions", 475);
		thisUpgrades[1][2] = new Upgrade(names.get(16), types[0], "Unstable Concotion", 3000);
		thisUpgrades[1][3] = new Upgrade(names.get(16), types[1], "Transforming Tonic", 4500);
		thisUpgrades[1][4] = new Upgrade(names.get(16), types[1], "Total Transformation", 45000);
		thisUpgrades[2][0] = new Upgrade(names.get(16), types[0], "Faster Throwing", 650);
		thisUpgrades[2][1] = new Upgrade(names.get(16), types[0], "Acid Pool", 450);
		thisUpgrades[2][2] = new Upgrade(names.get(16), types[0], "Lead to Gold", 1000);
		thisUpgrades[2][3] = new Upgrade(names.get(16), types[0], "Rubber to Gold", 2750);
		thisUpgrades[2][4] = new Upgrade(names.get(16), types[0], "Bloon Master Alchemist", 40000);
		Upgrade.upgrades.put(names.get(16), thisUpgrades);
		//Druid
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(17), types[0], "Hard Thorns", 250);
		thisUpgrades[0][1] = new Upgrade(names.get(17), types[0], "Heart of Thunder", 1000);
		thisUpgrades[0][2] = new Upgrade(names.get(17), types[0], "Druid of the Storm", 1850);
		thisUpgrades[0][3] = new Upgrade(names.get(17), types[0], "Ball Lightning", 5100);
		thisUpgrades[0][4] = new Upgrade(names.get(17), types[0], "Superstorm", 90000);
		thisUpgrades[1][0] = new Upgrade(names.get(17), types[0], "Thorn Swarm", 250);
		thisUpgrades[1][1] = new Upgrade(names.get(17), types[0], "Heart of Oak", 350);
		thisUpgrades[1][2] = new Upgrade(names.get(17), types[0], "Druid of the Jungle", 950);
		thisUpgrades[1][3] = new Upgrade(names.get(17), types[1], "Jungle's Bounty", 5000);
		thisUpgrades[1][4] = new Upgrade(names.get(17), types[1], "Spirit of the Forest", 35000);
		thisUpgrades[2][0] = new Upgrade(names.get(17), types[0], "Druidic Reachs", 100);
		thisUpgrades[2][1] = new Upgrade(names.get(17), types[0], "Heart of Vengeance", 300);
		thisUpgrades[2][2] = new Upgrade(names.get(17), types[0], "Druid of Wrath", 600);
		thisUpgrades[2][3] = new Upgrade(names.get(17), types[0], "Poplust", 2500);
		thisUpgrades[2][4] = new Upgrade(names.get(17), types[0], "Avatar of Wrath", 45000);
		Upgrade.upgrades.put(names.get(17), thisUpgrades);
		//Banana Farm
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(18), types[0], "Increased Production", 500);
		thisUpgrades[0][1] = new Upgrade(names.get(18), types[0], "Greater Production", 600);
		thisUpgrades[0][2] = new Upgrade(names.get(18), types[0], "Banana Plantation", 3000);
		thisUpgrades[0][3] = new Upgrade(names.get(18), types[0], "Banana Research Facility", 19000);
		thisUpgrades[0][4] = new Upgrade(names.get(18), types[0], "Banana Central", 100000);
		thisUpgrades[1][0] = new Upgrade(names.get(18), types[0], "Long Life Bananas", 300);
		thisUpgrades[1][1] = new Upgrade(names.get(18), types[0], "Valuable Bananas", 800);
		thisUpgrades[1][2] = new Upgrade(names.get(18), types[0], "Monkey Bank", 3500);
		thisUpgrades[1][3] = new Upgrade(names.get(18), types[1], "IMF Loan", 7500);
		thisUpgrades[1][4] = new Upgrade(names.get(18), types[1], "Monkey-Nomics", 100000);
		thisUpgrades[2][0] = new Upgrade(names.get(18), types[0], "EZ Collect", 250);
		thisUpgrades[2][1] = new Upgrade(names.get(18), types[0], "Banana Salvage", 200);
		thisUpgrades[2][2] = new Upgrade(names.get(18), types[0], "Marketplace", 2900);
		thisUpgrades[2][3] = new Upgrade(names.get(18), types[0], "Central Market", 15000);
		thisUpgrades[2][4] = new Upgrade(names.get(18), types[0], "Monkey Wall Street", 60000);
		Upgrade.upgrades.put(names.get(18), thisUpgrades);
		//Spike Factory
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(19), types[0], "Bigger Stacks", 800);
		thisUpgrades[0][1] = new Upgrade(names.get(19), types[0], "White Hot Spikes", 600);
		thisUpgrades[0][2] = new Upgrade(names.get(19), types[0], "Spiked Balls", 2300);
		thisUpgrades[0][3] = new Upgrade(names.get(19), types[0], "Spiked Mines", 9500);
		thisUpgrades[0][4] = new Upgrade(names.get(19), types[0], "Super Mines", 162500);
		thisUpgrades[1][0] = new Upgrade(names.get(19), types[0], "Faster Production", 600);
		thisUpgrades[1][1] = new Upgrade(names.get(19), types[0], "Even Faster Production", 800);
		thisUpgrades[1][2] = new Upgrade(names.get(19), types[0], "MOAB Shredder", 2500);
		thisUpgrades[1][3] = new Upgrade(names.get(19), types[1], "Spike Storm", 5000);
		thisUpgrades[1][4] = new Upgrade(names.get(19), types[1], "Carpet of Spikes", 40000);
		thisUpgrades[2][0] = new Upgrade(names.get(19), types[0], "Long Reach", 150);
		thisUpgrades[2][1] = new Upgrade(names.get(19), types[0], "Smart Spikes", 400);
		thisUpgrades[2][2] = new Upgrade(names.get(19), types[0], "Long Life Spikes", 1400);
		thisUpgrades[2][3] = new Upgrade(names.get(19), types[0], "Deadly Spikes", 3500);
		thisUpgrades[2][4] = new Upgrade(names.get(19), types[0], "Perma Spike", 30000);
		Upgrade.upgrades.put(names.get(19), thisUpgrades);
		//Monkey Village
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(20), types[0], "Bigger Radius", 400);
		thisUpgrades[0][1] = new Upgrade(names.get(20), types[0], "Jungle Drums", 1500);
		thisUpgrades[0][2] = new Upgrade(names.get(20), types[0], "Primary Training", 800);
		thisUpgrades[0][3] = new Upgrade(names.get(20), types[0], "Primary Mentoring", 2500);
		thisUpgrades[0][4] = new Upgrade(names.get(20), types[0], "Primary Expertise", 25000);
		thisUpgrades[1][0] = new Upgrade(names.get(20), types[0], "Grow Blocker", 250);
		thisUpgrades[1][1] = new Upgrade(names.get(20), types[0], "Radar Scanner", 2000);
		thisUpgrades[1][2] = new Upgrade(names.get(20), types[0], "Monkey Intelligence Bureau", 7500);
		thisUpgrades[1][3] = new Upgrade(names.get(20), types[1], "Call to Arms", 20000);
		thisUpgrades[1][4] = new Upgrade(names.get(20), types[1], "Homeland Defense", 40000);
		thisUpgrades[2][0] = new Upgrade(names.get(20), types[0], "Monkey Business", 500);
		thisUpgrades[2][1] = new Upgrade(names.get(20), types[0], "Monkey Comerce", 500);
		thisUpgrades[2][2] = new Upgrade(names.get(20), types[0], "Monkey Town", 10000);
		thisUpgrades[2][3] = new Upgrade(names.get(20), types[0], "Monkey City", 3000);
		thisUpgrades[2][4] = new Upgrade(names.get(20), types[0], "Monkeyopolis", 5000);
		Upgrade.upgrades.put(names.get(20), thisUpgrades);
		//Engineer Monkey
		thisUpgrades = new Upgrade[3][5];
		thisUpgrades[0][0] = new Upgrade(names.get(21), types[0], "Sentry Gun", 500);
		thisUpgrades[0][1] = new Upgrade(names.get(21), types[0], "Faster Engineering", 400);
		thisUpgrades[0][2] = new Upgrade(names.get(21), types[0], "Sprockets", 525);
		thisUpgrades[0][3] = new Upgrade(names.get(21), types[0], "Sentry Expert", 2500);
		thisUpgrades[0][4] = new Upgrade(names.get(21), types[0], "Sentry Paragon", 32000);
		thisUpgrades[1][0] = new Upgrade(names.get(21), types[0], "Larger Service Area", 250);
		thisUpgrades[1][1] = new Upgrade(names.get(21), types[0], "Deconstruction", 350);
		thisUpgrades[1][2] = new Upgrade(names.get(21), types[0], "Cleansing Foam", 800);
		thisUpgrades[1][3] = new Upgrade(names.get(21), types[1], "Overclock", 13500);
		thisUpgrades[1][4] = new Upgrade(names.get(21), types[1], "Ultraboost", 120000);
		thisUpgrades[2][0] = new Upgrade(names.get(21), types[0], "Oversize Nails", 450);
		thisUpgrades[2][1] = new Upgrade(names.get(21), types[0], "Pin", 220);
		thisUpgrades[2][2] = new Upgrade(names.get(21), types[0], "Double Gun", 500);
		thisUpgrades[2][3] = new Upgrade(names.get(21), types[0], "Bloon Trap", 3500);
		thisUpgrades[2][4] = new Upgrade(names.get(21), types[0], "XXXL Trap", 54000);
		Upgrade.upgrades.put(names.get(21), thisUpgrades);
		Upgrade.isUpgradesInit = true;
	}

	private static boolean isUpgradesInit() {return Upgrade.isUpgradesInit;}

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
