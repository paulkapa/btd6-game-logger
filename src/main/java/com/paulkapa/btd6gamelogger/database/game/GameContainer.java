package com.paulkapa.btd6gamelogger.database.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paulkapa.btd6gamelogger.models.BaseEntity;
import com.paulkapa.btd6gamelogger.models.game.Map;
import com.paulkapa.btd6gamelogger.models.game.Tower;
import com.paulkapa.btd6gamelogger.models.game.Upgrade;
import com.paulkapa.btd6gamelogger.models.system.User;

/**
 * <h4>Container class</h4>
 *
 * Stores in-memory or locally objects of type {@code User}
 * {@code Map}, {@code Tower} and {@code Upgrade}.
 * <p>
 * Provides static storage for default maps, towers with upgrades, and game modifiers, difficulties,
 * game modes.
 *
 * @see BaseEntity
 */
public class GameContainer extends BaseEntity {

    // Game options
    public static final String[] DIFFICULTIES = {"Easy", "Medium", "Hard"};
    public static final String[] GAME_MODES = {
        "Standard",
        "Primary Monkeys Only", "Deflation",
        "Military Monkeys Only", "Apopalypse", "Reverse",
        "Magic Monkeys Only", "Double HP MOABs", "Half Cash", "Alternate Bloon Rounds", "Impoppable", "CHIMPS",
        "Sandbox"
    };
    // Starting cash modifiers
    public static final int BASE_STARTING_CASH = 650;
    public static final int DEFLATION_STARTING_CASH = 20000;
    public static final int HALF_CASH_STARTING_CASH = 325;
    public static final int SANDBOX_STARTING_CASH = 9999999;
    public static final int MK_BONUS_STARTING_CASH = 200;
    // Lives modifiers
    public static final int EASY_STARTING_LIVES = 200;
    public static final int BASE_STARTING_LIVES = 150;
    public static final int HARD_STARTING_LIVES = 100;
    public static final int IMPOPPABLE_STARTING_LIVES = 1;
    public static final int CHIMPS_STARTING_LIVES = 1;
    public static final int SANDBOX_STARTING_LIVES = 9999999;
    public static final int MK_BONUS_STARTING_LIVES = 25;
    // Cost modifiers
    public static final double EASY_COST_MODIFIER = 0.85d;
    public static final double BASE_COST_MODIFIER = 1d;
    public static final double HARD_COST_MODIFIER = 1.08d;

    // Absolute path to the root application directory
    public static final String ABSOLUTE_PATH = new File("").getAbsolutePath();
    // Path to data folder, relative to application root directory
    public static final String RELATIVE_DATA_PATH = "\\src\\main\\java\\com\\paulkapa\\btd6gamelogger\\database\\local-data\\";
    // Path to save files folder, relative to application root directory
    public static final String RELATIVE_SAVE_DIR_PATH = "\\src\\main\\java\\com\\paulkapa\\btd6gamelogger\\database\\game\\saved-games";
    // Local saved games
    private static HashMap<String, String[]> savedGames = null;
    // Default maps
    private static LinkedHashMap<String, Map[]> defaultMaps = null;
    public static boolean isInitDefaultMaps = false;
    // Default towers
    private static LinkedHashMap<String, Tower[]> defaultTowers = null;
    public static boolean isInitDefaultTowers = false;
    // Defaul upgrades
    private static LinkedHashMap<String, Upgrade[][]> defaultUpgrades = null;
    public static boolean isInitDefaultUpgrades = false;

    private User user;
    private String saveName;
    private String diff;
    private String mode;
    // In use map
    private Map map;
    // In use towers
    private LinkedHashMap<String, Tower[]> towers;
    // In use upgrades
    private LinkedHashMap<String, Upgrade[][]> upgrades;

    public GameContainer() {
        super("BTD6", "Container");
        this.user = null;
        this.saveName = null;
        this.diff = null;
        this.mode = null;
        this.map = null;
        this.towers = null;
        this.upgrades = null;
        System.out.println("-----------------------------------------Absolute Path: " + GameContainer.ABSOLUTE_PATH);
        System.out.println("-----------------------------------------Data Path: " + GameContainer.RELATIVE_DATA_PATH);
        System.out.println("-----------------------------------------Save Files Dir Path: " + GameContainer.RELATIVE_SAVE_DIR_PATH);
        System.out.println("-----------------------------------------Save Files: ");
        GameContainer.savedGames = GameContainer.getSaveNames();
        GameContainer.savedGames.forEach((s, str) -> {
            for(int i = 0; i < str.length; i++) {
                System.out.println(" -> " + s + ": " + str[i]);
            }
        });
        System.out.println("-----------------------------------------");
    }

    /**
     * Preffered constructor.
     * @param user the logged in user
     * @param saveName the name of the game session picked by the user. Defaults to save_1
     */
    public GameContainer(User user, String saveName) {
        super("BTD6", "Container-" + saveName);
        this.user = user;
        this.saveName = saveName == null ? "save_1" : saveName;
        this.diff = null;
        this.mode = null;
        this.map = null;
        this.towers = null;
        this.upgrades = null;
    }

    public GameContainer(User user, String diff, String mode, String saveName,
            Map map,
            LinkedHashMap<String, Tower[]> towers,
            LinkedHashMap<String, Upgrade[][]> upgrades) {
        super("BTD6", "Container-" + saveName);
        this.user = user;
        this.saveName = saveName;
        this.diff = diff;
        this.mode = mode;
        this.map = map;
        this.towers = towers;
        this.upgrades = upgrades;
    }

    /**
     * Copy constructor.
     * @param other the container to copy data from
     */
    public GameContainer(GameContainer other) {
        super(other.getInstance());
        this.user = other.getUser();
        this.saveName = other.getSaveName();
        this.diff = other.getDiff();
        this.mode = other.getMode();
        this.map = other.getMap();
        this.towers = other.getTowers();
        this.upgrades = other.getUpgrades();
    }

    public static LinkedHashMap<String, Map[]> getDefaultMaps() throws IOException {
        if(!GameContainer.isInitDefaultMaps) GameContainer.defaultMaps = Map.getDefaultMaps();
        return GameContainer.defaultMaps;
    }

    public static LinkedHashMap<String, Tower[]> getDefaultTowers() throws IOException {
        if(!GameContainer.isInitDefaultTowers) GameContainer.defaultTowers = Tower.getDefaultTowers();
        return GameContainer.defaultTowers;
    }

    public static LinkedHashMap<String, Upgrade[][]> getDefaultUpgrades() throws Exception {
        if(!GameContainer.isInitDefaultUpgrades) GameContainer.defaultUpgrades = Upgrade.getDefaultUpgrades();
        return GameContainer.defaultUpgrades;
    }

    public User getUser() {return this.user;}

    /**
     * Replaces current logged in user and resets all other container data.
     * @param user the new logged in user
     */
    public void replaceUser(User user) {
        super.setID(0);
        super.setType("Container");
        this.saveName = null;
        this.diff = null;
        this.mode = null;
        this.map = null;
        this.towers = null;
        this.upgrades = null;
        this.user = new User(user);
    }

    public String getSaveName() {return this.saveName;}

    public void setSaveName(String saveName) {this.saveName = saveName;}

    public String getDiff() {return this.diff;}

    public void setDiff(String diff) {this.diff = diff;}

    public String getMode() {return this.mode;}

    public void setMode(String mode) {this.mode = mode;}

    public Map getMap() {return this.map;}

    public void setMap(Map map) {this.map = map;}

    public LinkedHashMap<String, Tower[]> getTowers() {return this.towers;}

    public void setTowers(LinkedHashMap<String, Tower[]> towers) {this.towers = towers;}

    public LinkedHashMap<String, Upgrade[][]> getUpgrades() {return this.upgrades;}

    public void setUpgrades(LinkedHashMap<String, Upgrade[][]> upgrades) {this.upgrades = upgrades;}

    /**
     * Calculates the starting cash based on the current game mode option chosen by the user.
     * @param gameMode the current game mode
     * @return an int representing the starting cash
     * @throws Exception if the game mode provided cannot be found or null
     */
    public static int calculateStartingCash(String gameMode) throws Exception {
        int mk = 0;
        if(!gameMode.equals("CHIMPS") && !gameMode.equals("Sandbox")) {
            mk = GameContainer.MK_BONUS_STARTING_CASH;
        }
        for(String s : GameContainer.GAME_MODES)
            if(s.equals(gameMode))
                switch(s) {
                    case "Deflation": {return GameContainer.DEFLATION_STARTING_CASH + mk;
                    } case "Half Cash" : {return GameContainer.HALF_CASH_STARTING_CASH + mk;
                    } case "Sandbox" : {return GameContainer.SANDBOX_STARTING_CASH + mk;
                    } default: {return GameContainer.BASE_STARTING_CASH + mk;}
                }
        throw new Exception("Couldn't find the provided game mode!",
            new Throwable("The value '" + gameMode + "' is not a valid game mode."));
    }

    /**
     * Calculates the starting lives based on the current difficutly and game mode options chosen by the user.
     * @param difficulty the current difficulty
     * @param gameMode the current game mode
     * @return an int representing the starting lives
     * @throws Exception if either the difficulty or game mode provided cannot be found or null
     */
    public static int calculateStartingLives(String difficulty, String gameMode) throws Exception {
        int mk = 0;
        if(!gameMode.equals("CHIMPS") || !gameMode.equals("Sandbox")) {
            mk = GameContainer.MK_BONUS_STARTING_LIVES;
        }
        for(String d : GameContainer.DIFFICULTIES)
            if(d.equals(difficulty))
                switch(d) {
                    case "Easy": {return gameMode.equals("Sandbox") ? GameContainer.SANDBOX_STARTING_LIVES : GameContainer.EASY_STARTING_LIVES + mk;
                    } case "Medium": {return gameMode.equals("Sandbox") ? GameContainer.SANDBOX_STARTING_LIVES : GameContainer.BASE_STARTING_LIVES + mk;
                    } case "Hard": {
                        for(String m : GameContainer.GAME_MODES)
                            if(m.equals(gameMode))
                                switch(m) {
                                    case "Impoppable": {return GameContainer.IMPOPPABLE_STARTING_LIVES + mk;
                                    } case "CHIMPS": {return GameContainer.CHIMPS_STARTING_LIVES + mk;
                                    } case "Sandbox": {return GameContainer.SANDBOX_STARTING_LIVES + mk;
                                    } default: {return GameContainer.HARD_STARTING_LIVES + mk;}
                                }
                        throw new Exception("Couldn't find the provided game mode!",
                            new Throwable("The value '" + gameMode + "' is not a valid game mode."));
                    } default: {throw new Exception("Couldn't find the provided difficulty!",
                    new Throwable("The value '" + difficulty + "' is not a valid difficulty."));}
                }
        throw new Exception("Couldn't calculate Starting Lives!",
        new Throwable("Parameters read error."));
    }

    /**
     * Resets the current container to the default values.
     * <p>
     * Data related to this container will not be accessible after the method executes.
     */
    public void resetContainer() {
        super.setID(0);
        super.setType("Container");
        this.saveName = null;
        this.diff = null;
        this.mode = null;
        this.map = null;
        this.towers = null;
        this.upgrades = null;
        this.user = null;
    }

    /**
     * Saves current container data to a save file with the name:
     * <ul>
     * <li>'[user_name]-[save_name].json'.
     */
    public void saveGame() {
        try {
            FileWriter fwr = new FileWriter(new File(GameContainer.ABSOLUTE_PATH + GameContainer.RELATIVE_SAVE_DIR_PATH + "\\" + GameContainer.createFileName(this.user.getName(), this.saveName)));
            BufferedWriter bwr = new BufferedWriter(fwr);
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().enableComplexMapKeySerialization().create();
            gson.toJson(this, bwr);
            bwr.close();
            fwr.close();
        } catch (Exception e) {e.printStackTrace();}
    }

    /**
     * Restores saved data from a save file with the name '[u]-[s].json'.
     * @param u the user's name that created the file
     * @param s the save name
     * @return a new container initialized with the save file data
     */
    public static GameContainer restoreSave(String u, String s) {
        try {
            FileReader fr = new FileReader(new File(GameContainer.ABSOLUTE_PATH + GameContainer.RELATIVE_SAVE_DIR_PATH + "\\" + GameContainer.createFileName(u, s)));
            BufferedReader br = new BufferedReader(fr);
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().enableComplexMapKeySerialization().create();
            return new GameContainer(gson.fromJson(br, GameContainer.class));
        } catch (Exception e) {e.printStackTrace(); return null;}
    }

    /**
     * Provides template for file name, constructed from an user-name and a save-name.
     * @param u
     * @param s
     * @return a templated file name
     */
    private static String createFileName(String u, String s) {return u + "-" + s + ".json";}

    private static HashMap<String, String[]> getSaveNames() {
        HashMap<String, String[]> saveNames = new HashMap<>();
        try {
            Stream<Path> paths = Files.walk(Paths.get(GameContainer.ABSOLUTE_PATH + GameContainer.RELATIVE_SAVE_DIR_PATH));
            paths.forEach((filePath) -> {
                if(Files.isRegularFile(filePath)) {
                    final String path = filePath.toFile().getName();
                    final String user = path.substring(0, path.indexOf("-"));
                    final String save = path.substring(path.indexOf("-") + 1, path.indexOf(".json"));
                    if(!saveNames.containsKey(user)) saveNames.put(user, new String[]{save});
                    else {
                        final String[] strings = saveNames.get(user);
                        final ArrayList<String> stringsArray = new ArrayList<>();
                        stringsArray.addAll(Arrays.asList(strings));
                        stringsArray.add(save);
                        saveNames.replace(user, stringsArray.toArray(new String[0]));
                    }
                }
            });
        } catch (IOException e) {e.printStackTrace();}
        return saveNames;
    }

    @Override
    public String createString() {
        StringBuffer sb = new StringBuffer();
        sb.delete(0, sb.length());
        sb.append(super.createString());
        sb.append(", user=").append(this.user.getName());
        sb.append(", save_name=").append(this.getSaveName());
        sb.append(", difficulty=").append(this.getDiff());
        sb.append(", game_mode=").append(this.getMode());
        sb.append(", maps=").append(this.map.toString());
        sb.append(", towers=").append(this.towers.toString());
        sb.append(", upgrades=").append(this.upgrades.toString());
        return sb.toString();
    }

    @Override
    public String toString() {return new Gson().toJson(this);}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((diff == null) ? 0 : diff.hashCode());
        result = prime * result + ((map == null) ? 0 : map.hashCode());
        result = prime * result + ((mode == null) ? 0 : mode.hashCode());
        result = prime * result + ((saveName == null) ? 0 : saveName.hashCode());
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
        if (map == null) {
            if (other.map != null)
                return false;
        } else if (!map.equals(other.map))
            return false;
        if (mode == null) {
            if (other.mode != null)
                return false;
        } else if (!mode.equals(other.mode))
            return false;
        if (saveName == null) {
            if (other.saveName != null)
                return false;
        } else if (!saveName.equals(other.saveName))
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
