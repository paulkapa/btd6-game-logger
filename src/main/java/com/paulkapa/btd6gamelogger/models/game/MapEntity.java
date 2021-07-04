package com.paulkapa.btd6gamelogger.models.game;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.paulkapa.btd6gamelogger.models.base.BaseEntity;
import com.paulkapa.btd6gamelogger.models.system.SavedData;

/**
 * Class that defines the properties of a Map in BTD6.
 *
 * @see BaseEntity
 */
@Entity(name = "Map")
@Table(name = "maps")
public class MapEntity extends BaseEntity {

    @Transient
    private StringBuffer sb = new StringBuffer();

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
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="map")
    private List<SavedData> storedData;

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
    private Double currentCash;

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
        this.storedData = null;
    }

    /**
     *
     */
    public MapEntity(String name) {
        super("Map", name);
        this.mapType = null;
        this.baseStartingCash = 0.0d;
        this.baseStartingLives = 0;
        this.storedData = null;
    }

    /**
     *
     */
    public MapEntity(String name, String mapType, double baseStartingCash, int baseStartingLives) {
        super("Map", name);
        this.mapType = mapType;
        this.baseStartingCash = baseStartingCash;
        this.baseStartingLives = baseStartingLives;
        this.storedData = null;
    }

    /**
     *
     */
    public MapEntity(String name, String mapType, double baseStartingCash, int baseStartingLives, List<SavedData> storedData) {
        super("Map", name);
        this.mapType = mapType;
        this.baseStartingCash = baseStartingCash;
        this.baseStartingLives = baseStartingLives;
        this.storedData = storedData;
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

    public Double getStartingCash() {
        return startingCash;
    }

    public void setStartingCash(Double startingCash) {
        this.startingCash = startingCash;
    }

    public int getStartingLives() {
        return startingLives;
    }

    public void setStartingLives(int startingLives) {
        this.startingLives = startingLives;
    }

    public Double getCurrentCash() {
        return currentCash;
    }

    public void setCurrentCash(Double currentCash) {
        this.currentCash = currentCash;
    }

    public int getCurrentLives() {
        return currentLives;
    }

    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }

    public List<SavedData> getStoredData() {
        return storedData;
    }

    public void setStoredData(List<SavedData> storedData) {
        this.storedData = storedData;
    }

    @Override
    public String createString() {
        this.sb.delete(0, this.sb.length());
        this.sb.append(super.createString()).append(", mapType=").append(this.mapType).append(", baseStartingCash=").append(this.baseStartingLives).append(", currentDifficulty=").append(this.currentDifficulty).append(", currentGameMode=").append(this.currentGameMode);
        return this.sb.toString();
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
        result = prime * result + ((currentCash == null) ? 0 : currentCash.hashCode());
        result = prime * result + ((currentDifficulty == null) ? 0 : currentDifficulty.hashCode());
        result = prime * result + ((currentGameMode == null) ? 0 : currentGameMode.hashCode());
        result = prime * result + currentLives;
        result = prime * result + ((mapType == null) ? 0 : mapType.hashCode());
        result = prime * result + ((startingCash == null) ? 0 : startingCash.hashCode());
        result = prime * result + startingLives;
        result = prime * result + ((storedData == null) ? 0 : storedData.hashCode());
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
        if (currentCash == null) {
            if (other.currentCash != null)
                return false;
        } else if (!currentCash.equals(other.currentCash))
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
        if (storedData == null) {
            if (other.storedData != null)
                return false;
        } else if (!storedData.equals(other.storedData))
            return false;
        return true;
    }
}
