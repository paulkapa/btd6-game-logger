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
    private short path;

    /**
     *
     */
    @Column(name = "tier")
    private short tier;

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
    public BaseUpgradeEntity(String name, short path, short tier, double baseCost) {
        super("Upgrade", name);
        this.path = path;
        this.tier = tier;
        this.baseCost = baseCost;
    }

    public short getPath() {
        return path;
    }

    public void setPath(short path) {
        this.path = path;
    }

    public short getTier() {
        return tier;
    }

    public void setTier(short tier) {
        this.tier = tier;
    }

    public double getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }
}
