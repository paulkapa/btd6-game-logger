package com.paulkapa.btd6gamelogger.models.game;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.paulkapa.btd6gamelogger.models.helper.BaseTowerEntity;

/**
 * Class that defines the properties of a Tower in BTD6.
 *
 * @see BaseTowerEntity
 */
@Entity(name = "Tower")
@Table(name = "towers")
public class TowerEntity extends BaseTowerEntity {

    /**
     *
     */
    @Transient
    private double cost;

    /**
     *
     */
    @Transient
    private double sellValue;

    /**
     *
     */
    @Transient
    private long pops;

    /**
     *
     */
    @Transient
    private long cashGenerated;

    /**
     *
     */
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="tower")
    private List<UpgradeEntity> upgrades;

    /**
     * Default constructor.
     */
    public TowerEntity() {
        super();
        this.cost = 0.0d;
        this.sellValue = 0.0d;
        this.pops = 0;
        this.cashGenerated = 0;
        this.upgrades = new ArrayList<>();
    }

    /**
     *
     */
    public TowerEntity(String name) {
        super(name);
        this.cost = 0.0d;
        this.sellValue = 0.0d;
        this.pops = 0;
        this.cashGenerated = 0;
        this.upgrades = new ArrayList<>();
    }

    /**
     *
     */
    public TowerEntity(String name, String towerClass, double baseCost, double baseSellValue) {
        super(name, towerClass, baseCost, baseSellValue);
        this.cost = 0.0d;
        this.sellValue = 0.0d;
        this.pops = 0;
        this.cashGenerated = 0;
        this.upgrades = new ArrayList<>();
    }

    /**
     *
     */
    public TowerEntity(String name, String towerClass, double baseCost, double baseSellValue, double cost,
            double sellValue, long pops, long cashGenerated, List<UpgradeEntity> upgrades) {
        super(name, towerClass, baseCost, baseSellValue);
        this.cost = cost;
        this.sellValue = sellValue;
        this.pops = pops;
        this.cashGenerated = cashGenerated;
        this.upgrades = upgrades;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getSellValue() {
        return sellValue;
    }

    public void setSellValue(double sellValue) {
        this.sellValue = sellValue;
    }

    public long getPops() {
        return pops;
    }

    public void setPops(long pops) {
        this.pops = pops;
    }

    public long getCashGenerated() {
        return cashGenerated;
    }

    public void setCashGenerated(long cashGenerated) {
        this.cashGenerated = cashGenerated;
    }

    /**
     * Sets the <code>UpgradeEntity</code> objects belonging
     * to this instance of <code>TowerEntity</code>.
     */
    public void setUpgrades(ArrayList<UpgradeEntity> upgrades) {
        this.upgrades = (upgrades == null) ? new ArrayList<>() : upgrades;
    }

    /**
     * Adds an <code>UpgradeEntity</code> object to the list of <code>UpgradeEntity</code>
     * objects belonging to this instance of <code>TowerEntity</code>.
     */
    public void addUpgrade(UpgradeEntity e) {
        if(this.upgrades != null)
            this.upgrades.add(e);
        else {
            this.upgrades = new ArrayList<>();
            this.upgrades.add(e);
        }
    }

    /**
     * Gets the <code>UpgradeEntity</code> objects belonging
     * to this instance of <code>TowerEntity</code>.
     */
    public List<UpgradeEntity> getUpgrades() {
        if(this.upgrades != null)
            return this.upgrades;
        else {
            this.upgrades = new ArrayList<>();
            return this.upgrades;
        }
    }
}
