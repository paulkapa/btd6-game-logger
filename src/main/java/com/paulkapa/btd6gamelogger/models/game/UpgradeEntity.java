package com.paulkapa.btd6gamelogger.models.game;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.paulkapa.btd6gamelogger.models.base.BaseUpgradeEntity;

/**
 * Class that defines the attributes af an Upgrade Path in BTD6.
 * 
 * @see BaseUpgradeEntity
 */
@Entity(name = "Upgrade")
@Table(name = "upgrades")
public class UpgradeEntity extends BaseUpgradeEntity {

    /**
     * 
     */
    @Transient
    private boolean isLocked;

    @ManyToOne
    @JoinColumn(name="tower_ID", nullable=false)
    private TowerEntity tower;

    /**
     * Default constructor.
     */
    public UpgradeEntity() {
        super();
        this.isLocked = false;
        this.tower = new TowerEntity();
    }

    /**
     * 
     */
    public UpgradeEntity(String name) {
        super(name);
        this.isLocked = false;
        this.tower = new TowerEntity();
    }

    /**
     * 
     */
    public UpgradeEntity(String name, short path, short tier, double baseCost) {
        super(name, path, tier, baseCost);
        this.isLocked = false;
        this.tower = new TowerEntity();
    }

    /**
     * 
     */
    public UpgradeEntity(String name, short path, short tier, double baseCost, boolean isLocked,
            TowerEntity tower) {
        super(name, path, tier, baseCost);
        this.isLocked = isLocked;
        this.tower = tower;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public TowerEntity getTower() {
        return tower;
    }

    public void setTower(TowerEntity tower) {
        this.tower = tower;
    }
}
