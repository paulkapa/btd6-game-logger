package com.paulkapa.btd6gamelogger.models.game;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.paulkapa.btd6gamelogger.models.base.BaseEntity;

/**
 * Class that defines the properties of a Map in BTD6.
 *
 * @see BaseEntity
 */
@Entity(name = "Map")
@Table(name = "maps")
public class MapEntity extends BaseEntity {

    /**
     *
     */
    @Column(name = "type")
    private String mapType;

    /**
     *
     */
    @Column(name = "base_starting_cash")
    private double baseStartingCash;

    /**
     *
     */
    @Column(name = "base_starting_lives")
    private int baseStartingLives;

    /**
     *
     */
    @Transient
    private String currentDifficulty;

    /**
     *
     */
    @Transient
    private String currentGameMode;

    /**
     *
     */
    @Transient
    private Double startingCash;

    /**
     *
     */
    @Transient
    private int startingLives;

    /**
     *
     */
    @Transient
    private int currentCash;

    /**
     *
     */
    @Transient
    private int currentLives;

    /**
     * Default constructor.
     */
    public MapEntity() {
        super("Map", null);
        this.mapType = null;
        this.baseStartingCash = 0.0d;
        this.baseStartingLives = 0;
    }

    /**
     *
     */
    public MapEntity(String name) {
        super("Map", name);
        this.mapType = null;
        this.baseStartingCash = 0.0d;
        this.baseStartingLives = 0;
    }

    /**
     *
     */
    public MapEntity(String name, String mapType, double baseStartingCash, int baseStartingLives) {
        super("Map", name);
        this.mapType = mapType;
        this.baseStartingCash = baseStartingCash;
        this.baseStartingLives = baseStartingLives;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public Double getBaseStartingCash() {
        return baseStartingCash;
    }

    public void setBaseStartingCash(double baseStartingCash) {
        this.baseStartingCash = baseStartingCash;
    }

    public int getBaseStartingLives() {
        return baseStartingLives;
    }

    public void setBaseStartingLives(int baseStartingLives) {
        this.baseStartingLives = baseStartingLives;
    }

    public String getCurrentDifficulty() {
        return currentDifficulty;
    }

    public void setCurrentDifficulty(String currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }

    public String getCurrentGameMode() {
        return currentGameMode;
    }

    public void setCurrentGameMode(String currentGameMode) {
        this.currentGameMode = currentGameMode;
    }

    public double getStartingCash() {
        return startingCash;
    }

    public void setStartingCash(double startingCash) {
        this.startingCash = startingCash;
    }

    public int getStartingLives() {
        return startingLives;
    }

    public void setStartingLives(int startingLives) {
        this.startingLives = startingLives;
    }

    public int getCurrentCash() {
        return currentCash;
    }

    public void setCurrentCash(int currentCash) {
        this.currentCash = currentCash;
    }

    public int getCurrentLives() {
        return currentLives;
    }

    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }

    @Override
    public String createString() {
        return super.createString() + ", mapType=" + this.mapType + ", baseStartingCash=" + this.baseStartingCash + ", baseStartingLives=" + this.baseStartingLives;
    }

    @Override
    public String toString() {
        return "{MapEntity " + this.createString() + "]}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(baseStartingCash);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + baseStartingLives;
        result = prime * result + currentCash;
        result = prime * result + ((currentDifficulty == null) ? 0 : currentDifficulty.hashCode());
        result = prime * result + ((currentGameMode == null) ? 0 : currentGameMode.hashCode());
        result = prime * result + currentLives;
        result = prime * result + ((mapType == null) ? 0 : mapType.hashCode());
        result = prime * result + ((startingCash == null) ? 0 : startingCash.hashCode());
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
        MapEntity other = (MapEntity) obj;
        if (Double.doubleToLongBits(baseStartingCash) != Double.doubleToLongBits(other.baseStartingCash))
            return false;
        if (baseStartingLives != other.baseStartingLives)
            return false;
        if (currentCash != other.currentCash)
            return false;
        if (currentDifficulty == null) {
            if (other.currentDifficulty != null)
                return false;
        } else if (!currentDifficulty.equals(other.currentDifficulty))
            return false;
        if (currentGameMode == null) {
            if (other.currentGameMode != null)
                return false;
        } else if (!currentGameMode.equals(other.currentGameMode))
            return false;
        if (currentLives != other.currentLives)
            return false;
        if (mapType == null) {
            if (other.mapType != null)
                return false;
        } else if (!mapType.equals(other.mapType))
            return false;
        if (startingCash == null) {
            if (other.startingCash != null)
                return false;
        } else if (!startingCash.equals(other.startingCash))
            return false;
        if (startingLives != other.startingLives)
            return false;
        return true;
    }
}
