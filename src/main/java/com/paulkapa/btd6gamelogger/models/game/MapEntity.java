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
    private double startingCash;

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

    public double getBaseStartingCash() {
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
}
