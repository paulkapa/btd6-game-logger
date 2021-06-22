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
    private Double cost;

    /**
     *
     */
    @Transient
    private Double sellValue;

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
        this.upgrades = null;
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
        this.upgrades = null;
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
        this.upgrades = null;
    }

    /**
     *
     */
    public TowerEntity(String name, String towerClass, double baseCost, double baseSellValue, List<UpgradeEntity> upgrades) {
        super(name, towerClass, baseCost, baseSellValue);
        this.cost = 0.0d;
        this.sellValue = 0.0d;
        this.pops = 0;
        this.cashGenerated = 0;
        this.upgrades = upgrades;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Double getSellValue() {
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

    @Override
    public String createString() {
        return super.createString();
    }

    @Override
    public String toString() {
        return "{TowerEntity " + this.createString() + "]}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (int) (cashGenerated ^ (cashGenerated >>> 32));
        result = prime * result + ((cost == null) ? 0 : cost.hashCode());
        result = prime * result + (int) (pops ^ (pops >>> 32));
        result = prime * result + ((sellValue == null) ? 0 : sellValue.hashCode());
        result = prime * result + ((upgrades == null) ? 0 : upgrades.hashCode());
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
        TowerEntity other = (TowerEntity) obj;
        if (cashGenerated != other.cashGenerated)
            return false;
        if (cost == null) {
            if (other.cost != null)
                return false;
        } else if (!cost.equals(other.cost))
            return false;
        if (pops != other.pops)
            return false;
        if (sellValue == null) {
            if (other.sellValue != null)
                return false;
        } else if (!sellValue.equals(other.sellValue))
            return false;
        if (upgrades == null) {
            if (other.upgrades != null)
                return false;
        } else if (!upgrades.equals(other.upgrades))
            return false;
        return true;
    }
}
