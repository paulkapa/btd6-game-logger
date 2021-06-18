package com.paulkapa.btd6gamelogger.models.system;

//import javax.persistence.Entity;
//import javax.persistence.Table;

//import com.paulkapa.btd6gamelogger.models.base.BaseEntity;
import com.paulkapa.btd6gamelogger.models.game.GameEntity;
import com.paulkapa.btd6gamelogger.models.game.MapEntity;

//@Entity(name = "store")
//@Table(name = "storage")
public class StoredData /*extends BaseEntity*/ {

    public StoredData() {
        //super("store", null);
    }

    public StoredData(User user) {
        //super("store", user.getName() + " - no-map");
    }

    public StoredData(User user, MapEntity map) {
        //super("store", user.getName() + " - map-" + map.getName());
    }

    public StoredData(GameEntity game) {
        //super("store", game.getUser().getName() + " - game-store");
    }
    
}
