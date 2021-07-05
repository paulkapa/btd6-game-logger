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
import com.paulkapa.btd6gamelogger.models.game.Map;

@Entity(name = "SavedData")
@Table(name = "saved_data")
public class SavedData extends BaseEntity {

    @Transient
    public Map MAP;

    @Transient
    private StringBuffer sb = new StringBuffer();

    @ManyToOne
    @JoinColumn(name="user_ID", nullable=false)
    private User user;

    @Column(name="map_ID")
    private int map;

    @Column(name = "map_diff")
    private String diff;

    @Column(name = "map_mode")
    private String mode;

    @OneToOne
    @JoinColumn(name="appsetup_ID", nullable=false)
    private AppSetup appSetup;

    @Transient
    private GameEntity game;

    public SavedData() {
        super("store", null);
        this.user = null;
        this.map = 0;
        this.diff = null;
        this.mode = null;
        this.appSetup = null;
        this.game = null;
    }

    public SavedData(User user) {
        super("store", user.getName() + " - no-map");
        this.user = user;
        this.map = 0;
        this.diff = null;
        this.mode = null;
        this.appSetup = null;
        this.game = null;
    }

    public SavedData(User user, Map map, AppSetup appSetup) {
        super("store", user.getName() + " - " + map.getName());
        this.user = user;
        this.MAP = map;
        this.map = map.getID();
        this.appSetup = appSetup;
        this.game = new GameEntity(user, map, this.diff, this.mode);
    }

    public SavedData(GameEntity gameData, AppSetup appSetup) {
        super("store", gameData.getUser().getName() + " - " + gameData.getMap(0).getName());
        this.game = gameData;
        this.user = gameData.getUser();
        this.MAP = gameData.getMap(0);
        this.map = this.MAP.getID();
        this.diff = gameData.getDiff();
        this.mode = gameData.getMode();
        this.appSetup = appSetup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map getMap() {
        return MAP;
    }

    public void setMap(Map map) {
        this.MAP = map;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public AppSetup getAppSetup() {
        return appSetup;
    }

    public void setAppSetup(AppSetup appSetup) {
        this.appSetup = appSetup;
    }

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }

    @Override
    public String createString() {
        this.sb.delete(0, this.sb.length());
        this.sb.append(super.createString()).append("\n").append(this.user).append("\n").append(this.map).append("\n").append(this.game);
        return this.sb.toString();
    }

    /**
     * @see #createString()
     */
    @Override
    public String toString() {
        return "{SavedData [" + this.createString() + "]}";

    }
}
