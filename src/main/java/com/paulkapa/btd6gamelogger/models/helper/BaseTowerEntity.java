package com.paulkapa.btd6gamelogger.models.helper;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.paulkapa.btd6gamelogger.models.base.BaseEntity;

/**
 * Class that defines the base properties of a Tower in BTD6.
 *
 * @see BaseEntity
 */
@MappedSuperclass
public class BaseTowerEntity extends BaseEntity {

    @Transient
    private StringBuffer sb = new StringBuffer();

    /**
     *
     */
    @Column(name = "type")
    private String towerType;

    /**
     *
     */
    @Column(name = "base_cost")
    private Double baseCost;

    /**
     *
     */
    @Column(name = "base_sell_value")
    private Double baseSellValue;

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

    public Double getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }

    public Double getBaseSellValue() {
        return baseSellValue;
    }

    public void setBaseSellValue(double baseSellValue) {
        this.baseSellValue = baseSellValue;
    }

    @Override
    public String createString() {
        this.sb.delete(0, this.sb.length());
        this.sb.append(super.createString()).append(", towerType=").append(this.towerType).append(", baseCost=").append(this.baseCost).append(", baseSellValue=").append(this.baseSellValue);
        return this.sb.toString();
    }

    @Override
    public String toString() {
        return "{BaseTowerEntity " + this.createString() + "]}";

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((baseCost == null) ? 0 : baseCost.hashCode());
        result = prime * result + ((baseSellValue == null) ? 0 : baseSellValue.hashCode());
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
        if (baseCost == null) {
            if (other.baseCost != null)
                return false;
        } else if (!baseCost.equals(other.baseCost))
            return false;
        if (baseSellValue == null) {
            if (other.baseSellValue != null)
                return false;
        } else if (!baseSellValue.equals(other.baseSellValue))
            return false;
        if (towerType == null) {
            if (other.towerType != null)
                return false;
        } else if (!towerType.equals(other.towerType))
            return false;
        return true;
    }
}
