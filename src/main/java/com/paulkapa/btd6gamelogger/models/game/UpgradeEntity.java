package com.paulkapa.btd6gamelogger.models.game;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.paulkapa.btd6gamelogger.models.helper.BaseUpgradeEntity;

/**
 * Class that defines the attributes af an Upgrade Path in BTD6.
 *
 * @see BaseUpgradeEntity
 */
@Entity(name = "Upgrade")
@Table(name = "upgrades")
public class UpgradeEntity extends BaseUpgradeEntity {

    @Transient
    private StringBuffer sb = new StringBuffer();

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
        this.tower = null;
    }

    /**
     *
     */
    public UpgradeEntity(String name) {
        super(name);
        this.isLocked = false;
        this.tower = null;
    }

    /**
     *
     */
    public UpgradeEntity(String name, int path, int tier, double baseCost) {
        super(name, path, tier, baseCost);
        this.isLocked = false;
        this.tower = null;
    }

    /**
     *
     */
    public UpgradeEntity(String name, int path, int tier, double baseCost, TowerEntity tower) {
        super(name, path, tier, baseCost);
        this.isLocked = false;
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

    @Override
    public String createString() {
        this.sb.delete(0, this.sb.length());
        sb.append(super.createString());
        return super.createString();
    }

    @Override
    public String toString() {
        return "{UpgradeEntity " + this.createString() + "]}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (isLocked ? 1231 : 1237);
        result = prime * result + ((tower == null) ? 0 : tower.hashCode());
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
        UpgradeEntity other = (UpgradeEntity) obj;
        if (isLocked != other.isLocked)
            return false;
        if (tower == null) {
            if (other.tower != null)
                return false;
        } else if (!tower.equals(other.tower))
            return false;
        return true;
    }
}
