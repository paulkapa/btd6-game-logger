package com.paulkapa.btd6gamelogger.models.helper;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.paulkapa.btd6gamelogger.models.base.BaseEntity;

/**
 * Class that defines the base properties of an Upgrade in BTD6.
 *
 * @see NamedEntity
 */
@MappedSuperclass
public class BaseUpgradeEntity extends BaseEntity {

    /**
     *
     */
    @Column(name = "path")
    private int path;

    /**
     *
     */
    @Column(name = "tier")
    private int tier;

    /**
     *
     */
    @Column(name = "base_cost")
    private double baseCost;


    /**
     * Default constructor.
     */
    public BaseUpgradeEntity() {
        super("Upgrade", null);
        this.path = 0;
        this.tier = 0;
        this.baseCost = 0.0d;
    }

    /**
     *
     */
    public BaseUpgradeEntity(String name) {
        super("Upgrade", name);
        this.path = 0;
        this.tier = 0;
        this.baseCost = 0.0d;
    }

    /**
     *
     */
    public BaseUpgradeEntity(String name, int path, int tier, double baseCost) {
        super("Upgrade", name);
        this.path = path;
        this.tier = tier;
        this.baseCost = baseCost;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public double getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }

    @Override
    public String createString() {
        return super.createString() + ", path=" + this.path + ", tier=" + this.tier + ", baseCost=" + this.baseCost;
    }

    @Override
    public String toString() {
        return "{BaseUpgradeEntity [" + this.createString() + "]}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(baseCost);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + path;
        result = prime * result + tier;
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
        BaseUpgradeEntity other = (BaseUpgradeEntity) obj;
        if (Double.doubleToLongBits(baseCost) != Double.doubleToLongBits(other.baseCost))
            return false;
        if (path != other.path)
            return false;
        if (tier != other.tier)
            return false;
        return true;
    }
}
