package com.paulkapa.btd6gamelogger.models.system;

import com.google.gson.Gson;
import com.paulkapa.btd6gamelogger.models.BaseEntity;
import com.paulkapa.btd6gamelogger.models.game.GameContainer;

/**
 * <h4>In-Dev</h4>
 * 
 * Will be used to save user activity locally.
 * <p>
 * Will have its functionality extended to save data in an
 * online database.
 *
 * @see BaseEntity
 */
public class SavedData extends BaseEntity {

	private GameContainer game;

	public SavedData() {
		super("Saved Data", "data-store");
		this.game = null;
		/*try {
			JsonWriter jsonWriter = new JsonWriter(new FileWriter("store.json"));
			jsonWriter.setIndent("    ");
			Gson gson = new GsonBuilder().serializeNulls().create();
			gson.toJson(GameContainer.gameContainerToLinkedHashMap(
				new GameContainer(
					User.getDefaultUser(),
					SavedData.mapToArray(Map.getMaps()),
					SavedData.mapToArray(Tower.getTowers()),
					SavedData.mapToArray(Upgrade.getUpgrades()),
					null,
					null
				)
			), GameContainer.class, jsonWriter);
			jsonWriter.close();
			FileReader fr = new FileReader(new File("store.json"));
			BufferedReader br = new BufferedReader(fr);
			JsonObject parser = JsonParser.parseReader(br).getAsJsonObject();
			//JsonObject user = parser.get("user").getAsJsonObject();
			//System.out.println(user.get("password"));
			JsonArray maps = parser.get("maps").getAsJsonArray();
			System.out.println(maps.get(1).getAsJsonObject().get("type"));
			System.out.println(parser.get("ID"));
			System.out.println(parser.get("name"));
			System.out.println(parser.get("type"));
			System.out.println(parser.get("user"));
			System.out.println(parser.get("diff"));
			System.out.println(parser.get("mode"));
			System.out.println(parser.get("maps"));
			System.out.println(parser.get("towers"));
			System.out.println(parser.get("upgrades"));
			br.close();
			fr.close();
		} catch(Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Preferred constructor.
	 * @param gameData
	 */
	public SavedData(GameContainer gameData) {
		super("store", "aux");
		this.game = gameData;
	}

	/**
	 * Copy constructor.
	 * @param other
	 */
	public SavedData(SavedData other) {
		super(other.getInstance());
		this.game = other.getGame();
	}

	public GameContainer getGame() {return this.game;}

	public void setGame(GameContainer game) {this.game = game;}

	@Override
	public String toString() {return new Gson().toJson(this);}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		if (game == null) {
			if (other.game != null)
				return false;
		} else if (!game.equals(other.game))
			return false;
		return true;
	}
}
