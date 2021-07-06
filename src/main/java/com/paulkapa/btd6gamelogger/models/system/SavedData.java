package com.paulkapa.btd6gamelogger.models.system;

import com.paulkapa.btd6gamelogger.models.base.BaseEntity;
import com.paulkapa.btd6gamelogger.models.game.AppSetup;
import com.paulkapa.btd6gamelogger.models.game.GameContainer;

/**
 * Auxilliary class - for development purposes.
 * @see BaseEntity
 */
public class SavedData extends BaseEntity {

    private StringBuffer sb = new StringBuffer();

    private GameContainer game;
    private AppSetup appSetup;

    public SavedData() {
        super("store", "aux");
        super.setID(0);
        this.game = null;
        this.appSetup = null;
    }

    public SavedData(GameContainer gameData) {
        super("store", "aux");
        super.setID(0);
        this.game = gameData;
        this.appSetup = null;
    }

    public SavedData(GameContainer gameData, AppSetup appSetup) {
        super("store", "aux");
        super.setID(0);
        this.game = gameData;
        this.appSetup = appSetup;
    }

    public GameContainer getGame() {
        return game;
    }

    public void setGame(GameContainer game) {
        this.game = game;
    }

    public AppSetup getAppSetup() {
        return appSetup;
    }

    public void setAppSetup(AppSetup appSetup) {
        this.appSetup = appSetup;
    }

    @Override
    public String createString() {
        this.sb.delete(0, this.sb.length());
        this.sb.append(super.createString());
        this.sb.append("\nSaved Game: {\n").append(this.game.toString()).append("\n}");
        this.sb.append("\nApp Setup: {\n").append(this.appSetup.toString()).append("\n}");
        return this.sb.toString();
    }

    /**
     * @see #createString()
     */
    @Override
    public String toString() {
        return "{ " + this.getName() + "=[" + this.createString() + "]}";

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((appSetup == null) ? 0 : appSetup.hashCode());
        result = prime * result + ((game == null) ? 0 : game.hashCode());
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
        if (game == null) {
            if (other.game != null)
                return false;
        } else if (!game.equals(other.game))
            return false;
        return true;
    }
}
