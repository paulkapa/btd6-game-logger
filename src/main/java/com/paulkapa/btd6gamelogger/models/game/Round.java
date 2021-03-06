package com.paulkapa.btd6gamelogger.models.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.paulkapa.btd6gamelogger.database.game.GameContainer;
import com.paulkapa.btd6gamelogger.models.BaseEntity;

/**
 * <b>Class that defines the properties of a Round</b>
 * <p>
 * Provides static methods to query rounds.
 *
 * @see BaseEntity
 */
public class Round extends BaseEntity {

    private int nr;

    private long rbe;

    private long roundCash;

    private LinkedHashMap<String, Integer> bloons;

    private double duration;

    /**
     * Default constructor.
     */
    public Round() {
        super();
        this.nr = 0;
        this.rbe = 0l;
        this.roundCash = 0l;
        this.bloons = null;
        this.duration = 0.0d;
    }

    /**
     * Preferred constructor.
     *
     * @param nr        the index of the round
     * @param rbe       the total health of bloons spawned in the round
     * @param roundCash the total cash that could be earned in this round
     * @param bloons    the list of bloons spawning throughout the round
     */
    public Round(int nr, long rbe, long roundCash, LinkedHashMap<String, Integer> bloons) {
        super();
        this.nr = nr;
        this.rbe = rbe;
        this.roundCash = roundCash;
        this.bloons = bloons;
        this.duration = 0.0d;
    }

    /**
     * Complete constructor.
     *
     * @param name      a description of the round
     * @param type      a classification of the round
     * @param nr        the index of the round
     * @param rbe       the total health of bloons spawned in the round * @param
     * @param roundCash the total cash that could be earned in this round
     * @param bloons    the list of bloons spawning throughout the round
     * @param duration  the time passed from round start until all bloons spawn
     */
    public Round(String name, String type, int nr, long rbe, long roundCash, LinkedHashMap<String, Integer> bloons,
            double duration) {
        super(name, type);
        this.nr = nr;
        this.rbe = rbe;
        this.roundCash = roundCash;
        this.bloons = bloons;
        this.duration = duration;
    }

    /**
     * Copy constructor.
     *
     * @param other the Round to copy data from
     */
    public Round(Round other) {
        super(other.getInstance());
        this.nr = other.getNr();
        this.rbe = other.getRbe();
        this.roundCash = other.getRoundCash();
        this.bloons = other.getBloons();
        this.duration = other.getDuration();
    }

    public static Round getHighestRbeRound(LinkedHashMap<String, Round[]> rounds) {
        if (rounds.isEmpty()) {
            return null;
        } else {
            long maxRbe = 0;
            Round resultRound = null;
            for (Entry<String, Round[]> e : rounds.entrySet()) {
                Round[] curr = e.getValue();
                for (var i = 0; i < curr.length; i++) {
                    if (curr[i].getRbe() > maxRbe) {
                        maxRbe = curr[i].getRbe();
                        resultRound = new Round(curr[i]);
                    }
                }
            }
            if (maxRbe != 0) {
                return resultRound;
            } else {
                return null;
            }
        }
    }

    public static Round getMostDiverseRound(LinkedHashMap<String, Round[]> rounds) {
        if (rounds.isEmpty()) {
            return null;
        } else {
            var bloonDiversity = 0;
            Round resultRound = null;
            for (Entry<String, Round[]> e : rounds.entrySet()) {
                Round[] curr = e.getValue();
                for (var i = 0; i < curr.length; i++) {
                    if (curr[i].getBloons().size() > bloonDiversity) {
                        bloonDiversity = curr[i].getBloons().size();
                        resultRound = new Round(curr[i]);
                    }
                }
            }
            if (bloonDiversity != 0) {
                return resultRound;
            } else {
                return null;
            }
        }
    }

    public static Round getMostIncomeRound(LinkedHashMap<String, Round[]> rounds) {
        if (rounds.isEmpty()) {
            return null;
        } else {
            var income = 0l;
            Round resultRound = null;
            for (Entry<String, Round[]> e : rounds.entrySet()) {
                Round[] curr = e.getValue();
                for (var i = 0; i < curr.length; i++) {
                    if (curr[i].getRoundCash() > income) {
                        income = curr[i].getRoundCash();
                        resultRound = new Round(curr[i]);
                    }
                }
            }
            if (income != 0) {
                return resultRound;
            } else {
                return null;
            }
        }
    }

    public static LinkedHashMap<String, Round[]> getDefaultRounds() throws IOException {
        if (!GameContainer.isInitDefaultRounds) {
            GameContainer.isInitDefaultRounds = true;
            return Round.initDefaultRounds();
        } else {
            return null;
        }
    }

    public static LinkedHashMap<String, Round[]> getDefaultRounds(int from, int to) throws IOException {
        if (!GameContainer.isInitDefaultRounds) {
            LinkedHashMap<String, Round[]> rounds = Round.initDefaultRounds();
            LinkedHashMap<String, Round[]> resultRounds = new LinkedHashMap<>();
            for (Entry<String, Round[]> entry : rounds.entrySet()) {
                var r = new Round[to - from + 1];
                var index = -1;
                for (var i = from - 1; i < to; i++) {
                    index++;
                    r[index] = entry.getValue()[i];
                }
                resultRounds.put(entry.getKey(), r);
            }
            if (resultRounds.isEmpty()) {
                return null;
            } else {
                return resultRounds;
            }
        } else {
            return null;
        }
    }

    public static LinkedHashMap<String, Round[]> getDefaultRounds(String diff, String mode) throws IOException {
        // TODO - currently the same as Round.getDefaultRounds(void) | Has to provide access to a specific range of rounds
        if (!GameContainer.isInitDefaultRounds) {
            GameContainer.isInitDefaultRounds = true;
            return Round.initDefaultRounds();
        } else {
            return null;
        }
    }

    private static LinkedHashMap<String, Round[]> initDefaultRounds() throws IOException {
        LinkedHashMap<String, Round[]> defaultRounds = new LinkedHashMap<>();
        // Type variables
        Type mapType = new TypeToken<LinkedHashMap<String, Round[]>>() {
        }.getType();
        var arrayType = new Round[0];
        Type objectType = new TypeToken<Round>() {
        }.getType();

        // opens a file as read-only
        FileReader fr = null;
        try {
            fr = new FileReader(
                    new File(GameContainer.ABSOLUTE_PATH + GameContainer.SRC_RELATIVE_DATA_PATH + "rounds.json"));
        } catch (IOException e) {
            fr = new FileReader(
                    new File(GameContainer.ABSOLUTE_PATH + GameContainer.PROD_RELATIVE_DATA_PATH + "rounds.json"));
        }
        // opens a read stream from the file reader
        var br = new BufferedReader(fr);
        // gson object
        var gson = new GsonBuilder().setPrettyPrinting().serializeNulls().enableComplexMapKeySerialization().create();
        // reads entire file contents as a 'mapType'
        JsonElement element = gson.toJsonTree(new LinkedHashMap<>(gson.fromJson(br, mapType)));
        // converts the element to an object
        JsonObject object = element.getAsJsonObject();
        // parses trough the entries read and saves them in the respective types while
        // building the method return value
        for (Entry<String, JsonElement> e : object.entrySet()) {
            var array = e.getValue().getAsJsonArray();
            ArrayList<Round> currRounds = new ArrayList<>();
            for (var i = 0; i < array.size(); i++) {
                currRounds.add(new Round(gson.fromJson(array.get(i), objectType)));
            }
            defaultRounds.put(e.getKey(), currRounds.toArray(arrayType));
        }
        br.close();
        fr.close();
        // Default towers saved in memory!
        return defaultRounds;
    }

    /**
     * Gets the index of the round.
     *
     * @return the index of the round
     */
    public int getNr() {
        return nr;
    }

    /**
     * Sets the index of the round.
     *
     * @param nr the index to set
     */
    public void setNr(int nr) {
        this.nr = nr;
    }

    /**
     * Gets the RBE of the round.
     *
     * @return the RBE
     */
    public long getRbe() {
        return rbe;
    }

    /**
     * Sets the RBE of the round.
     *
     * @param rbe the RBE to set
     */
    public void setRbe(long rbe) {
        this.rbe = rbe;
    }

    /**
     * @return the roundCash
     */
    public long getRoundCash() {
        return roundCash;
    }

    /**
     * @param roundCash the roundCash to set
     */
    public void setRoundCash(long roundCash) {
        this.roundCash = roundCash;
    }

    /**
     * Gets the list of bloons spawning throughout the round.
     *
     * @return the list of bloons
     */
    public LinkedHashMap<String, Integer> getBloons() {
        return bloons;
    }

    /**
     * Sets the list of bloons spawning throughout the round
     *
     * @param bloons the list of bloons to set
     */
    public void setBloons(LinkedHashMap<String, Integer> bloons) {
        this.bloons = bloons;
    }

    /**
     * Gets the round duration.
     *
     * @return the duration
     */
    public double getDuration() {
        return duration;
    }

    /**
     * Sets the round duration.
     *
     * @param duration the duration to set
     */
    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
