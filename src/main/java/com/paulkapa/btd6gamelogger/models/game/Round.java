package com.paulkapa.btd6gamelogger.models.game;

import java.time.Duration;
import java.util.LinkedHashMap;

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

    private LinkedHashMap<String, Integer> bloons;

    private Duration duration;

    /**
     * Default constructor.
     */
    public Round() {
        super();
        this.nr = 0;
        this.rbe = 0l;
        this.bloons = null;
        this.duration = null;
    }

    /**
     * Preferred constructor.
     *
     * @param nr     the index of the round
     * @param rbe    the total health of bloons spawned in the round
     * @param bloons the list of bloons spawning throughout the round
     */
    public Round(int nr, long rbe, LinkedHashMap<String, Integer> bloons) {
        super();
        this.nr = nr;
        this.rbe = rbe;
        this.bloons = bloons;
    }

    /**
     * Complete constructor.
     *
     * @param name     a description of the round
     * @param type     a classification of the round
     * @param nr       the index of the round
     * @param RBE      the total health of bloons spawned in the round
     * @param bloons   the list of bloons spawning throughout the round
     * @param duration the time passed from round start until all bloons spawn
     */
    public Round(String name, String type, int nr, long rbe, LinkedHashMap<String, Integer> bloons, Duration duration) {
        super(name, type);
        this.nr = nr;
        this.rbe = rbe;
        this.bloons = bloons;
        this.duration = duration;
    }

    public static void getDefaultRounds(int from, int to) {
        // TODO
    }

    public static void getDefaultRounds(String diff, String mode) {
        // TODO
    }

    private static void initDefaultRounds() {
        // TODO
        /*
         * HashMap<String[], LinkedHashMap<Integer, Round>> defaultRounds = new
         * HashMap<>(); // aux variable to apply getClass() method on
         * LinkedHashMap<String, Map[]> mapType = new LinkedHashMap<>(0); // aux
         * variable to apply getClass() method on Map[] arrayType = new Map[0]; // aux
         * variable to apply getClass() method on Map type = new Map(); // opens a file
         * as read-only FileReader fr = null; try { fr = new FileReader(new
         * File(GameContainer.ABSOLUTE_PATH + GameContainer.SRC_RELATIVE_DATA_PATH +
         * "maps.json")); } catch (IOException e) { fr = new FileReader(new
         * File(GameContainer.ABSOLUTE_PATH + GameContainer.PROD_RELATIVE_DATA_PATH +
         * "maps.json")); } // opens a read stream from the file reader BufferedReader
         * br = new BufferedReader(fr); // gson object Gson gson = new
         * GsonBuilder().setPrettyPrinting().serializeNulls().
         * enableComplexMapKeySerialization().create(); // reads entire file contents as
         * a 'mapType' JsonElement element = gson.toJsonTree(new
         * LinkedHashMap<>(gson.fromJson(br, mapType.getClass()))); // converts the
         * element to an object JsonObject object = element.getAsJsonObject(); // parses
         * trough the entries read and saves them in the respective types while building
         * the method return value for(Entry<String, JsonElement> e : object.entrySet())
         * { JsonArray array = e.getValue().getAsJsonArray(); ArrayList<Map> currMaps =
         * new ArrayList<>(); for(int i = 0; i < array.size(); i++) { currMaps.add(new
         * Map(gson.fromJson(array.get(i), type.getClass()))); }
         * defaultMaps.put(e.getKey(), currMaps.toArray(arrayType)); } br.close();
         * fr.close(); // Default maps saved in memory! return defaultMaps;
         */
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
    public Duration getDuration() {
        return duration;
    }

    /**
     * Sets the round duration.
     *
     * @param duration the duration to set
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((bloons == null) ? 0 : bloons.hashCode());
        result = prime * result + ((duration == null) ? 0 : duration.hashCode());
        result = prime * result + nr;
        result = prime * result + (int) (rbe ^ (rbe >>> 32));
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Round other = (Round) obj;
        if (bloons == null) {
            if (other.bloons != null) {
                return false;
            }
        } else if (!bloons.equals(other.bloons)) {
            return false;
        }
        if (duration == null) {
            if (other.duration != null) {
                return false;
            }
        } else if (!duration.equals(other.duration)) {
            return false;
        }
        if (nr != other.nr) {
            return false;
        }
        if (rbe != other.rbe) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Round [bloons=");
        builder.append(bloons);
        builder.append(", duration=");
        builder.append(duration);
        builder.append(", nr=");
        builder.append(nr);
        builder.append(", rbe=");
        builder.append(rbe);
        builder.append("]");
        return builder.toString();
    }

}
