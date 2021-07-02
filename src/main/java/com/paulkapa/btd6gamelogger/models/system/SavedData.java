package com.paulkapa.btd6gamelogger.models.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.paulkapa.btd6gamelogger.models.base.BaseEntity;
import com.paulkapa.btd6gamelogger.models.game.AppSetup;
import com.paulkapa.btd6gamelogger.models.game.GameEntity;
import com.paulkapa.btd6gamelogger.models.game.MapEntity;

@Entity(name = "SavedData")
@Table(name = "saved_data")
public class SavedData extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="user_ID", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="map_ID", nullable=false)
    private MapEntity map;

    @Column(name = "map_diff")
    private String diff;

    @OneToOne
    @JoinColumn(name="appsetup_ID", nullable=false)
    private AppSetup appSetup;

    @Transient
    private GameEntity game;

    public SavedData() {
        super("store", null);
        this.user = null;
        this.map = null;
        this.diff = null;
        this.appSetup = null;
        this.game = null;
    }

    public SavedData(User user) {
        super("store", user.getName() + " - no-map");
        this.user = user;
        this.map = null;
        this.diff = null;
        this.appSetup = null;
        this.game = null;
    }

    public SavedData(User user, MapEntity map, String diff, AppSetup appSetup) {
        super("store", user.getName() + " - " + map.getName());
        this.user = user;
        this.map = map;
        this.diff = null;
        this.appSetup = appSetup;
        this.game = new GameEntity(user, map, diff);
    }

    public SavedData(GameEntity gameData, AppSetup appSetup) {
        super("store", gameData.getUser().getName() + " - " + gameData.getMap(0).getName());
        this.game = gameData;
        this.user = gameData.getUser();
        this.map = gameData.getMap(0);
        this.appSetup = appSetup;
        this.diff = gameData.getDiff();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MapEntity getMap() {
        return map;
    }

    public void setMap(MapEntity map) {
        this.map = map;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public AppSetup getAppSetup() {
        return appSetup;
    }

    public void setAppSetup(AppSetup appSetup) {
        this.appSetup = appSetup;
    }

    public GameEntity getGameData() {
        return game;
    }

    public void setGameData(GameEntity gameData) {
        this.game = gameData;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((appSetup == null) ? 0 : appSetup.hashCode());
        result = prime * result + ((diff == null) ? 0 : diff.hashCode());
        result = prime * result + ((game == null) ? 0 : game.hashCode());
        result = prime * result + ((map == null) ? 0 : map.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
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
        SavedData other = (SavedData) obj;
        if (appSetup == null) {
            if (other.appSetup != null)
                return false;
        } else if (!appSetup.equals(other.appSetup))
            return false;
        if (diff == null) {
            if (other.diff != null)
                return false;
        } else if (!diff.equals(other.diff))
            return false;
        if (game == null) {
            if (other.game != null)
                return false;
        } else if (!game.equals(other.game))
            return false;
        if (map == null) {
            if (other.map != null)
                return false;
        } else if (!map.equals(other.map))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }
}