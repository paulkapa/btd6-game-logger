package com.paulkapa.btd6gamelogger.models.game;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.paulkapa.btd6gamelogger.models.BaseEntity;
import com.paulkapa.btd6gamelogger.models.system.User;

/**
 * <h4>Container class</h4>
 *
 * Stores and handles objects of type {@code User}
 * {@code Map}, {@code Tower} and {@code Upgrade}.
 * <p>
 * Provides static storage for game modifiers, difficulties
 * and game modes.
 *
 * @see BaseEntity
 */
public class GameContainer extends BaseEntity {

	public static final String[] DIFFICULTIES = {
		"Easy", "Medium", "Hard"
	};
	public static final String[] GAME_MODES = {
		"Standard",
		"Primary Monkeys Only", "Deflation",
		"Military Monkeys Only", "Apopalypse", "Reverse",
		"Magic Monkeys Only", "Double HP MOABs", "Half Cash", "Alternate Bloon Rounds", "Impoppable", "CHIMPS",
		"Sandbox"
	};

	public static final int BASE_STARTING_CASH = 650;
	public static final int DEFLATION_STARTING_CASH = 20000;
	public static final int HALF_CASH_STARTING_CASH = 325;
	public static final int SANDBOX_STARTING_CASH = 9999999;

	public static final int EASY_STARTING_LIVES = 200;
	public static final int BASE_STARTING_LIVES = 150;
	public static final int HARD_STARTING_LIVES = 100;
	public static final int IMPOPPABLE_STARTING_LIVES = 1;
	public static final int CHIMPS_STARTING_LIVES = 1;
	public static final int SANDBOX_STARTING_LIVES = 9999999;

	public static final int MK_BONUS_STARTING_CASH = 200;
	public static final int MK_BONUS_STARTING_LIVES = 25;

	public static final double EASY_COST_MODIFIER = 0.85d;
	public static final double BASE_COST_MODIFIER = 1d;
	public static final double HARD_COST_MODIFIER = 1.08d;

	private User user;
	private ArrayList<Map> maps;
	private ArrayList<Tower> towers;
	private ArrayList<Upgrade[][]> upgrades;

	private String diff;
	private String mode;

	public GameContainer() {
		super("BTD6", "Container");
		this.user = null;
		this.maps = null;
		this.towers = null;
		this.upgrades = null;
		this.diff = null;
		this.mode = null;
	}

	public GameContainer(User user) {
		super("BTD6", "Container");
		this.user = user;
		this.maps = null;
		this.towers = null;
		this.upgrades = null;
		this.diff = null;
		this.mode = null;
	}

	/**
	 * Preffered constructor.
	 * @param user
	 * @param map
	 */
	public GameContainer(User user, Map map) {
		super("BTD6", "Container");
		this.user = user;
		this.addMap(map);
		this.towers = null;
		this.upgrades = null;
		this.diff = map.getDifficulty();
		this.mode = map.getGameMode();
	}

	public GameContainer(User user, ArrayList<Map> maps, ArrayList<Tower> towers, ArrayList<Upgrade[][]> upgrades,
			String diff, String mode) {
		super("BTD6", "Container");
		this.user = user;
		this.maps = maps;
		this.towers = towers;
		this.upgrades = upgrades;
		this.diff = diff;
		this.mode = mode;
	}

	/**
	 * Copy constructor.
	 * @param other
	 */
	public GameContainer(GameContainer other) {
		super(other.getInstance());
		this.user = other.getUser();
		this.maps = other.getMaps();
		this.towers = other.getTowers();
		this.upgrades = other.getUpgrades();
		this.diff = other.getDiff();
		this.mode = other.getMode();
	}

	public User getUser() {return this.user;}

	public void setUser(User user) {this.user = user;}

	public ArrayList<Map> getMaps() {return this.maps;}

	public void setMaps(ArrayList<Map> maps) {this.maps = maps;}

	public void addMap(Map m) {
		if(this.maps == null) this.maps = new ArrayList<>();
		this.maps.add(m);
	}

	public ArrayList<Tower> getTowers() {return this.towers;}

	public void setTowers(ArrayList<Tower> towers) {this.towers = towers;}

	public void addTower(Tower t) {
		if(this.towers == null) this.towers = new ArrayList<>();
		this.towers.add(t);
	}

	public ArrayList<Upgrade[][]> getUpgrades() {return this.upgrades;}

	public void setUpgrades(ArrayList<Upgrade[][]> upgrades) {this.upgrades = upgrades;}

	public void addUpgrade(Upgrade[][] u) {
		if(this.upgrades == null)
			this.upgrades = new ArrayList<>();
		this.upgrades.add(u);
	}

	public String getDiff() {return this.diff;}

	public void setDiff(String diff) {this.diff = diff;}

	public String getMode() {return this.mode;}

	public void setMode(String mode) {this.mode = mode;}

	public static int getStartingCashByGameMode(String gameMode) {
		for(String s : GameContainer.GAME_MODES)
			if(s.equals(gameMode)) {
				switch(s) {
					case "Deflation": {return GameContainer.DEFLATION_STARTING_CASH;
					} case "Half Cash" : {return GameContainer.HALF_CASH_STARTING_CASH;
					} default: {break;}
				}
				break;
			}
		return GameContainer.BASE_STARTING_CASH;
	}

	private static int getStartingLivesByGameMode(String gameMode) {
		for(String s : GameContainer.GAME_MODES)
			if(s.equals(gameMode)) {
				switch(s) {
					case "Impoppable": {return GameContainer.IMPOPPABLE_STARTING_LIVES;
					} case "CHIMPS": {return GameContainer.CHIMPS_STARTING_LIVES;
					} case "Sandbox": {return GameContainer.SANDBOX_STARTING_LIVES;
					} default: {break;}
				}
				break;
			}
		return 0;
	}

	public static int getStartingLives(String difficulty, String gameMode) {
		for(String s : GameContainer.DIFFICULTIES)
			if(s.equals(difficulty)) {
				switch(s) {
					case "Easy": {return GameContainer.EASY_STARTING_LIVES;
					} case "Medium": {return GameContainer.BASE_STARTING_LIVES;
					} case "Hard": {return GameContainer.getStartingLivesByGameMode(gameMode) == 0 ?
						GameContainer.HARD_STARTING_LIVES : GameContainer.getStartingLivesByGameMode(gameMode);
					} default: {break;}
				}
				break;
			}
		return 0;
	}

	@Override
	public String createString() {
		StringBuffer sb = new StringBuffer();
		sb.delete(0, sb.length());
		sb.append(super.createString());
		sb.append(", user=").append(user.getName());
		sb.append(", difficulty=").append(getDiff());
		sb.append(", game_mode=").append(getMode());
		sb.append(", maps=").append(maps.toString());
		sb.append(", towers=").append(towers.toString());
		sb.append(", applied_upgrades=").append(Upgrade.countAppliedUpgrades(upgrades));
		sb.append(", remaining_upgrades=").append(
			upgrades.size()*15 - (Upgrade.countAppliedUpgrades(upgrades) + Upgrade.countUnlockedUpgrades(upgrades))
		);
		return sb.toString();
	}

	@Override
	public String toString() {return new Gson().toJson(this);}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((diff == null) ? 0 : diff.hashCode());
		result = prime * result + ((maps == null) ? 0 : maps.hashCode());
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
		result = prime * result + ((towers == null) ? 0 : towers.hashCode());
		result = prime * result + ((upgrades == null) ? 0 : upgrades.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		GameContainer other = (GameContainer) obj;
		if (diff == null) {
			if (other.diff != null)
				return false;
		} else if (!diff.equals(other.diff))
			return false;
		if (maps == null) {
			if (other.maps != null)
				return false;
		} else if (!maps.equals(other.maps))
			return false;
		if (mode == null) {
			if (other.mode != null)
				return false;
		} else if (!mode.equals(other.mode))
			return false;
		if (towers == null) {
			if (other.towers != null)
				return false;
		} else if (!towers.equals(other.towers))
			return false;
		if (upgrades == null) {
			if (other.upgrades != null)
				return false;
		} else if (!upgrades.equals(other.upgrades))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}
