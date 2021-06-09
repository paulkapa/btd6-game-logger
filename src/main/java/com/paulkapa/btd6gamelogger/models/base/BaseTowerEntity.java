package com.paulkapa.btd6gamelogger.models.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Class that defines the base properties of a Tower in BTD6.
 * 
 * @see NamedEntity
 */
@MappedSuperclass
public class BaseTowerEntity extends NamedEntity {

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
        super("Tower");
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
}
