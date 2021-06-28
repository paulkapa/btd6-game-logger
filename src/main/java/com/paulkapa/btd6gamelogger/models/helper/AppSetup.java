package com.paulkapa.btd6gamelogger.models.helper;

public class AppSetup {
    private String mapName;
    private String diff;

    public AppSetup() {
        
    }

    public AppSetup(String mapName, String diff) {
        this.mapName = mapName;
        this.diff = diff;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    @Override
    public String toString() {
        return "AppSetup [diff=" + diff + ", mapName=" + mapName + "]";
    }
}
