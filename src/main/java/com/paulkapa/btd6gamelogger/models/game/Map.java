package com.paulkapa.btd6gamelogger.models.game;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.google.gson.Gson;
import com.paulkapa.btd6gamelogger.models.BaseEntity;

/**
 * <h4>Class that defines the properties of a Map</h4>
 *
 * Provides static storage for all maps.
 *
 * @see BaseEntity
 */
public class Map extends BaseEntity {

	private static ArrayList<Map> inUseMaps = null;
	private static LinkedHashMap<String, Map> maps = null;
	private static boolean isMapsInit = false;

	private String difficulty;
	private String gameMode;
	private int startingCash;
	private int startingLives;
	private long currentCash;
	private int currentLives;

	public Map() {
		super();
		this.difficulty = null;
		this.gameMode = null;
		this.startingCash = 0;
		this.startingLives = 0;
		this.currentCash = 0l;
		this.currentLives = 0;
	}

	/**
	 * Preffered constructor.
	 * @param name
	 * @param type
	 * @param startingCash
	 * @param startingLives
	 */
	public Map(String name, String type, int startingCash, int startingLives) {
		super(name, type);
		this.startingCash = startingCash;
		this.startingLives = startingLives;
		this.currentCash = 0l;
		this.currentLives = 0;
	}

	public Map(String name, String type, String difficulty, String gameMode,
				int startingCash, int startingLives, long currentCash, int currentLives) {
		super(name, type);
		this.difficulty = difficulty;
		this.gameMode = gameMode;
		this.startingCash = startingCash;
		this.startingLives = startingLives;
		this.currentCash = currentCash;
		this.currentLives = currentLives;
	}

	/**
	 * Copy constructor.
	 * @param other
	 */
	public Map(Map other) {
		super(other.getInstance());
		this.difficulty = other.getDifficulty();
		this.gameMode = other.getGameMode();
		this.startingCash = other.getStartingCash();
		this.startingLives = other.getStartingLives();
		this.currentCash = other.getCurrentCash();
		this.currentLives = other.getCurrentLives();
	}

	public static ArrayList<Map> getInUseMaps() {return inUseMaps;}

	public static void setInUseMaps(ArrayList<Map> inUseMaps) {Map.inUseMaps = inUseMaps;}

	public static void useMap(Map map) {
		if(Map.inUseMaps == null) Map.inUseMaps = new ArrayList<>();
		Map.inUseMaps.add(map);
	}

	public static LinkedHashMap<String, Map> getMaps() {
		if(!Map.isMapsInit) Map.initMaps();
		return Map.maps;
	}

	public static Map getMapByName(String mapName, LinkedHashMap<String, Map> mapsSearch) throws Exception {
		ArrayList<Map> result = new ArrayList<>();
		result.add(0, null);
		if(mapsSearch == null) throw new Exception(
			"Cannot search in empty map!",
			new Throwable("Provided searchMap is null.", new Throwable()));
		Map.maps.forEach((n, m) -> {if(n.equals(mapName) && result.get(0) == null) result.add(0, m);});
		if(result.get(0) == null) throw new Exception(
			"No result matching the criteria!",
			new Throwable("No map with name '" + mapName + "' was found.", new Throwable()));
		return result.get(0);
	}

	private static void initMaps() {
		Map.maps = new LinkedHashMap<>();
		//Beginner
		maps.put("Monkey Meadow", new Map("Monkey Meadow", "Beginner", 650, 150));
		maps.put("Tree Stump", new Map("Tree Stump", "Beginner", 650, 150));
		maps.put("Town Center", new Map("Town Center", "Beginner", 650, 150));
		maps.put("Resort", new Map("Resort", "Beginner", 650, 150));
		maps.put("Skates", new Map("Skates", "Beginner", 650, 150));
		maps.put("Lotus Island", new Map("Lotus Island", "Beginner", 650, 150));
		maps.put("Candy Falls", new Map("Candy Falls", "Beginner", 650, 150));
		maps.put("Winter Park", new Map("Winter Park", "Beginner", 650, 150));
		maps.put("Carved", new Map("Carved", "Beginner", 650, 150));
		maps.put("Park Path", new Map("Park Path", "Beginner", 650, 150));
		maps.put("Alpine Run", new Map("Alpine Run", "Beginner", 650, 150));
		maps.put("Frozen Over", new Map("Frozen Over", "Beginner", 650, 150));
		maps.put("In The Loop", new Map("In The Loop", "Beginner", 650, 150));
		maps.put("Cubism", new Map("Cubism", "Beginner", 650, 150));
		maps.put("Four Circles", new Map("Four Circles", "Beginner", 650, 150));
		maps.put("Hedge", new Map("Hedge", "Beginner", 650, 150));
		maps.put("End Of The Road", new Map("End Of The Road", "Beginner", 650, 150));
		maps.put("Logs", new Map("Logs", "Beginner", 650, 150));
		//Intermediate
		maps.put("Balance", new Map("Balance", "Intermediate", 650, 150));
		maps.put("Encrypted", new Map("Encrypted", "Intermediate", 650, 150));
		maps.put("Bazaar", new Map("Bazaar", "Intermediate", 650, 150));
		maps.put("Adora's Temple", new Map("Adora's Temple", "Intermediate", 650, 150));
		maps.put("Spring Spring", new Map("Spring Spring", "Intermediate", 650, 150));
		maps.put("KartsNDarts", new Map("KartsNDarts", "Intermediate", 650, 150));
		maps.put("Moon Landing", new Map("Moon Landing", "Intermediate", 650, 150));
		maps.put("Haunted", new Map("Haunted", "Intermediate", 650, 150));
		maps.put("Downstream", new Map("Downstream", "Intermediate", 650, 150));
		maps.put("Firing Range", new Map("Firing Range", "Intermediate", 650, 150));
		maps.put("Cracked", new Map("Cracked", "Intermediate", 650, 150));
		maps.put("Streambed", new Map("Streambed", "Intermediate", 650, 150));
		maps.put("Chutes", new Map("Chutes", "Intermediate", 650, 150));
		maps.put("Rake", new Map("Rake", "Intermediate", 650, 150));
		maps.put("Spice Islands", new Map("Spice Islands", "Intermediate", 650, 150));
		//Advanced
		maps.put("X Factor", new Map("X Factor", "Advanced", 650, 150));
		maps.put("Mesa", new Map("Mesa", "Advanced", 650, 150));
		maps.put("Geared", new Map("Geared", "Advanced", 650, 150));
		maps.put("Spillway", new Map("Spillway", "Advanced", 650, 150));
		maps.put("Cargo", new Map("Cargo", "Advanced", 650, 150));
		maps.put("Pat's Pond", new Map("Pat's Pond", "Advanced", 650, 150));
		maps.put("Peninsula", new Map("Peninsula", "Advanced", 650, 150));
		maps.put("High Finance", new Map("High Finance", "Advanced", 650, 150));
		maps.put("Another Brick", new Map("Another Brick", "Advanced", 650, 150));
		maps.put("Off The Coast", new Map("Off The Coast", "Advanced", 650, 150));
		maps.put("Cornfield", new Map("Cornfield", "Advanced", 650, 150));
		maps.put("Underground", new Map("Underground", "Advanced", 650, 150));
		//Expert
		maps.put("Sanctuary", new Map("Sanctuary", "Expert", 650, 150));
		maps.put("Ravine", new Map("Ravine", "Expert", 650, 150));
		maps.put("Flooded Valley", new Map("Flooded Valley", "Expert", 650, 150));
		maps.put("Infernal", new Map("Infernal", "Expert", 650, 150));
		maps.put("Bloody Puddles", new Map("Bloody Puddles", "Expert", 650, 150));
		maps.put("Workshop", new Map("Workshop", "Expert", 650, 150));
		maps.put("Quad", new Map("Quad", "Expert", 650, 150));
		maps.put("Dark Castle", new Map("Dark Castle", "Expert", 650, 150));
		maps.put("Muddy Puddles", new Map("Muddy Puddles", "Expert", 650, 150));
		maps.put("#ouch", new Map("#ouch", "Expert", 650, 150));
		Map.isMapsInit = true;
	}

	public String getDifficulty() {return this.difficulty;}

	public void setDifficulty(String difficulty) {this.difficulty = difficulty;}

	public String getGameMode() {return this.gameMode;}

	public void setGameMode(String gameMode) {this.gameMode = gameMode;}

	public int getStartingCash() {return this.startingCash;}

	public void setStartingCash(int startingCash) {this.startingCash = startingCash;}

	public int getStartingLives() {return this.startingLives;}

	public void setStartingLives(int startingLives) {this.startingLives = startingLives;}

	public long getCurrentCash() {return this.currentCash;}

	public void setCurrentCash(long currentCash) {this.currentCash = currentCash;}

	public int getCurrentLives() {return this.currentLives;}

	public void setCurrentLives(int currentLives) {this.currentLives = currentLives;}

	@Override
	public String createString() {
		StringBuffer sb = new StringBuffer();
		sb.delete(0, sb.length());
		sb.append(super.createString());
		sb.append(", difficulty=").append(getDifficulty());
		sb.append(", game_mode=").append(getGameMode());
		sb.append(", starting_cash=").append(getStartingCash());
		sb.append(", starting_lives=").append(getStartingLives());
		sb.append(", current_cash=").append(getCurrentCash());
		sb.append(", current_lives=").append(getCurrentLives());
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
		result = prime * result + (int) (currentCash ^ (currentCash >>> 32));
		result = prime * result + currentLives;
		result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
		result = prime * result + ((gameMode == null) ? 0 : gameMode.hashCode());
		result = prime * result + startingCash;
		result = prime * result + startingLives;
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
		Map other = (Map) obj;
		if (currentCash != other.currentCash)
			return false;
		if (currentLives != other.currentLives)
			return false;
		if (difficulty == null) {
			if (other.difficulty != null)
				return false;
		} else if (!difficulty.equals(other.difficulty))
			return false;
		if (gameMode == null) {
			if (other.gameMode != null)
				return false;
		} else if (!gameMode.equals(other.gameMode))
			return false;
		if (startingCash != other.startingCash)
			return false;
		if (startingLives != other.startingLives)
			return false;
		return true;
	}
}
