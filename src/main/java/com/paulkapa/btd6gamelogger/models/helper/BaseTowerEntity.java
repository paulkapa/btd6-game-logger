package com.paulkapa.btd6gamelogger.models.helper;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.paulkapa.btd6gamelogger.models.base.BaseEntity;

/**
 * Class that defines the base properties of a Tower in BTD6.
 *
 * @see BaseEntity
 */
@MappedSuperclass
public class BaseTowerEntity extends BaseEntity {

    /**
     *
     */
    @Column(name = "type")
    private String towerType;

    /**
     *
     */
    @Column(name = "base_cost")
    private double baseCost;

    /**
     *
     */
    @Column(name = "base_sell_value")
    private double baseSellValue;

    /**
     * Default constructor.
     */
    public BaseTowerEntity() {
        super("Tower", null);
        this.towerType = null;
        this.baseCost = 0.0d;
        this.baseSellValue = 0.0d;
    }

    /**
     *
     */
    public BaseTowerEntity(String name) {
        super("Tower", name);
        this.towerType = null;
        this.baseCost = 0.0d;
        this.baseSellValue = 0.0d;
    }

    /**
     *
     */
    public BaseTowerEntity(String name, String towerClass, double baseCost, double baseSellValue) {
        super("Tower", name);
        this.towerType = towerClass;
        this.baseCost = baseCost;
        this.baseSellValue = baseSellValue;
    }

    public String getTowerClass() {
        return towerType;
    }

    public void setTowerClass(String towerClass) {
        this.towerType = towerClass;
    }

    public double getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }

    public double getBaseSellValue() {
        return baseSellValue;
    }

    public void setBaseSellValue(double baseSellValue) {
        this.baseSellValue = baseSellValue;
    }

    @Override
    public String createString() {
        return super.createString() + ", towerType=" + this.towerType + ", baseCost=" + this.baseCost + ", baseSellValue=" + this.baseSellValue;
    }

    @Override
    public String toString() {
        return "{BaseTowerEntity [" + this.createString() + "]}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(baseCost);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(baseSellValue);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((towerType == null) ? 0 : towerType.hashCode());
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
        BaseTowerEntity other = (BaseTowerEntity) obj;
        if (Double.doubleToLongBits(baseCost) != Double.doubleToLongBits(other.baseCost))
            return false;
        if (Double.doubleToLongBits(baseSellValue) != Double.doubleToLongBits(other.baseSellValue))
            return false;
        if (towerType == null) {
            if (other.towerType != null)
                return false;
        } else if (!towerType.equals(other.towerType))
            return false;
        return true;
    }
}
