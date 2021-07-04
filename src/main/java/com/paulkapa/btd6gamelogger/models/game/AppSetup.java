package com.paulkapa.btd6gamelogger.models.game;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.paulkapa.btd6gamelogger.models.base.BaseEntity;
import com.paulkapa.btd6gamelogger.models.system.SavedData;

@Entity(name = "AppSetup")
@Table(name = "app_setup")
public class AppSetup extends BaseEntity {

    @Transient
    private StringBuffer sb = new StringBuffer();

    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="appSetup")
    private SavedData savedData;

    public AppSetup() {
        super("appsetup", null);
    }
    
    public AppSetup(SavedData savedData) {
        super("appsetup", savedData.getName());
        this.savedData = savedData;
    }

    public SavedData getStoredData() {
        return savedData;
    }

    public void setStoredData(SavedData savedData) {
        this.savedData = savedData;
    }

    @Override
    public String createString() {
        this.sb.delete(0, this.sb.length());
        this.sb.append(super.createString());
        return this.sb.toString();
    }

    /**
     * @see #createString()
     */
    @Override
    public String toString() {
        return "{AppSetup [" + this.createString() + "]}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((savedData == null) ? 0 : savedData.hashCode());
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
        AppSetup other = (AppSetup) obj;
        if (savedData == null) {
            if (other.savedData != null)
                return false;
        } else if (!savedData.equals(other.savedData))
            return false;
        return true;
    }
}
