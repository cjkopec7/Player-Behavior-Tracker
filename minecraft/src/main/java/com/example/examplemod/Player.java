package com.example.examplemod;

import java.io.*;
import java.util.*;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.io.Files;
import com.google.gson.*;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * Used to retrieve, hold, and update information on the Minecraft player's
 * current session to parsed into the Behavior Tracker Mod (see ExampleMod,
 * TestListener). Players are comparable by their score variable
 * 
 * @author Colby Kopec
 * @version 1.1
 * 
 */
public class Player implements Comparable<Player> {
	private String name, lastAct, uuidStr, dirr, js;
	private Integer score, natDeath = 0, natKill = 0;
	private File[] uuids;
	private String[] playerIds, stats, categories;
	private UUID uuid;

	/**
	 * The directory of the world's stat folder, typically
	 * minecraft/saves/[worldname]/stats
	 */
	private File folder;
	/**
	 * A Map of the Player's JSON file from UUID, broken into String[]s(see
	 * runJsonReader())
	 */
	private HashMap<String, String[]> statsMap;
	/**
	 * A Map of the Player's JSON file from UUID, broken into String/Integer
	 * keys/vals (see runJsonReader())
	 */
	private HashMap<String, HashMap<String, Integer>> statsMapValues;

	/**
	 * A constructor for the Player class
	 * <p>
	 * Used primarily for debugging and variable instantiation
	 * 
	 * @param namer  Player's name
	 * @param scorer Player's score
	 */
	public Player(String namer, int scorer) {
		name = namer;
		score = scorer;
		categories = new String[] { "mined", "custom", "broken", "crafted", "used", "picked_up", "dropped", "killed",
				"killed_by" };

	}

	/**
	 * A constructor for the Player class
	 * <p>
	 * Parses Player's UUID to a String, takes the directory to locate the stats
	 * folder, and gets the name of all JSON files there. Finally, the Player
	 * retrieves the contents of it's own JSON file (see runJSONReader())
	 * 
	 * @param uuid  Player's UUID object
	 * @param pname Player's name
	 * @param dir   The directory of the world's stats folder, to read .json files
	 * @exception NullPointerException Could be thrown if there are no .json files
	 *                                 present in the supplied directory
	 */
	public Player(UUID uuid, String pname, String dir) {
		this(pname, 0);
		dirr = dir;
		if (dirr.length() == 0) {
			dirr = "E:\\Coobs\\Documents\\Documents\\Colby\\project\\minecraft\\run\\saves\\Test\\stats";
		}
		uuidStr = uuid.toString();
		if (uuidStr.equals("00000000-0000-0002-0000-000000000002")) {
			/**
			 * For testing purposes, 'itsCoobs' UUID
			 */
			uuidStr = "9f795a3e-c5a4-46b6-80e3-05ebd4a4b735";
		}
		try {
			folder = new File(dirr);
			uuids = folder.listFiles();
			playerIds = new String[uuids.length];
			for (int i = 0; i < uuids.length; i++) {
				playerIds[i] = uuids[i].getName().replace(".json", "");
//debugging		System.out.println(playerIds[i]);

			}
		} catch (NullPointerException e) {
			System.out.println("No UUIDs found");
			e.printStackTrace();

		}
		runJSONreader();

	}

	/**
	 * Reads the Player's JSON file and parses the data to variables
	 * <p>
	 * Using Gson, BufferedReader, and FileReader, the Player's .json file is
	 * located and read, then parsed to a String and cleaned up. It is then passed
	 * to two Maps, statsMap & statsMapVales to be used by Scoreboard class for
	 * specific value retrieval. The Player's score is also updated based on the
	 * Map's contents (see updateScore())
	 * 
	 * @exception IOException           Upon failure to close BufferedReader
	 * @exception FileNotFoundException Upon failing to find the specified file
	 */
	public void runJSONreader() {
		try {
			Gson gson = new Gson();
			statsMap = new HashMap<String, String[]>();
			statsMapValues = new HashMap<String, HashMap<String, Integer>>();
			BufferedReader br = new BufferedReader(new FileReader(folder.toString() + "//" + uuidStr + ".json"));
			Object obj = gson.fromJson(br, Object.class);
			js = obj.toString();
			String json = js.replace("minecraft:", "").replace("{stats={", "").replace("}, DataVersion=2580.0}", "")
					.replace("{", "");
			stats = json.split("}");
			for (String s : stats) {
				String line = s;
				if (!line.equals(stats[0])) {
					line = line.substring(2);
				}
//debugging		System.out.println(line);
				String[] temp = line.split("=", 2);
				String cat = temp[0];
				String vl = temp[1].replace(" ", "");
				String[] values = vl.split(",");
				statsMap.put(cat, values);

				HashMap<String, Integer> tmpMap = new HashMap<String, Integer>();
				for (String r : values) {
					String[] tmp = r.split("=");
					double d = Double.parseDouble(tmp[1]);
					int v = new Integer((int) d);
					tmpMap.put(tmp[0], v);
					statsMapValues.put(cat, tmpMap);

				}

			}
			updateNaturalKillsDeaths();
			this.updateScore();

			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			System.out.println("Read file failed");
			e.printStackTrace();
		}
	}

	/**
	 * Get an array of values from statMap
	 * 
	 * @param v Key
	 * @return String[] of values, or String[0] if key not found
	 */
	public String[] getList(String v) {
		String[] f = statsMap.get(v);
		String[] s = new String[0];
		if (f != null) {
			s = f;

		}
		return s;
	}

	/**
	 * To be used alongside getList() to return String for String[]
	 * 
	 * @param w String[]
	 * @return String of w, cleaned up
	 */
	public String getListString(String[] w) {
		String q = Arrays.toString(w);
		q = q.replace("]", "").replace("[", "").replace("=", " : ").replace(", ", "\n").replace(".0", "").replace("_",
				" ");
		if (w.length == 0) {
			q = "Nothing here yet";
		}
		return q;
	}

	/**
	 * Calculates and updates Player's score (see below methods)
	 */
	public void updateScore() {
		int s = 0;
		s += (int) getPlayTime() + (int) getDistance() + (int) getMobKills() + (int) getItemsCrafted()
				+ (int) getItemsDropped() + (int) getItemsPickedUp() + (int) getItemsUsed() + (int) getBrokeItems()
				+ (int) getAmountMined() - (int) getDeaths();
		score = s;

	}

	/***
	 * Updates the natKills and natDeaths values and adds them to statsMapValues
	 */
	public void updateNaturalKillsDeaths() {
		HashMap<String, Integer> k = statsMapValues.get("killed");
		HashMap<String, Integer> kb = statsMapValues.get("killed_by");
		HashMap<String, String[]> km = statsMap;

		int kc = 0;
		int kbc = 0;
		if (k != null) {
			for (String o : k.keySet()) {
				kc += k.get(o);

			}

		}
		natKill = getMobKills() - kc;
		if (kb != null) {
			for (String j : kb.keySet()) {
				kbc += kb.get(j);

			}

		}
		natDeath = getDeaths() - kbc;
		if (natKill > 0) {
			String[] old = statsMap.get("killed");
			String[] n = new String[] { ("natural forces kill: " + natKill) };
			String[] temp = ArrayUtils.addAll(old, n);
			k.put("natural forces kill", natKill);
			statsMapValues.put("killed", k);
			statsMap.put("killed", temp);
		}
		if (natDeath > 0) {
			String[] old = statsMap.get("killed_by");
			String[] n = new String[] { ("natural forces: " + natDeath) };
			String[] temp = ArrayUtils.addAll(old, n);
			kb.put("natural forces", natDeath);
			statsMapValues.put("killed_by", kb);
			statsMap.put("killed_by", temp);

		}
	}

	/**
	 * Gets Player's play time as float from statsMapValues
	 * 
	 * @return time in minutes
	 */
	public float getPlayTime() {
		HashMap<String, Integer> f = statsMapValues.get("custom");
		if (f != null) {
			if (f.get("play_one_minute") != null) {
				float v = (float) f.get("play_one_minute") / 1200;
				return v;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/**
	 * Gets Player's total distance travled, including all transport types (see
	 * vals), as float from statsMapValues
	 * 
	 * @return Total distance in meters
	 */
	public float getDistance() {
		HashMap<String, Integer> f = statsMapValues.get("custom");
		float dis = 0;
		if (f != null) {
			for (String s : f.keySet()) {

				if (s.contains("one_cm")) {
					dis += f.get(s);

				}
			}
		}
		return dis / 1000;

	}

	/**
	 * Gets Player's total mined count, including all block types, as float from
	 * statsMapValues
	 * 
	 * @return Amount of blocks mined
	 */
	public int getAmountMined() {
		int mined = 0;
		HashMap<String, Integer> f = statsMapValues.get("mined");
		if (f != null) {
			for (String s : f.keySet()) {
				mined += f.get(s);
			}
		}
		return mined;
	}

	/**
	 * Gets Player's kill count as integer from statsMapValues
	 * 
	 * @return Kill count
	 */
	public int getMobKills() {
		int kills = 0;
		HashMap<String, Integer> f = statsMapValues.get("custom");
		if (f != null) {
			if (f.get("mob_kills") != null) {
				kills += f.get("mob_kills");
			}
		}
		return kills;
	}

	/**
	 * Gets Player's death count as integer from statsMapValues
	 * 
	 * @return Death count
	 */
	public int getDeaths() {
		int deaths = 0;
		HashMap<String, Integer> f = statsMapValues.get("custom");
		if (f != null) {
			if (f.get("deaths") != null) {
				deaths += f.get("deaths");
			}
		}
		return deaths;
	}

	/**
	 * Gets amount of items the Player has broken, including all types, as integer
	 * from statMapValues
	 * 
	 * @return Amount of items broken
	 */
	public int getBrokeItems() {
		int broke = 0;
		HashMap<String, Integer> f = statsMapValues.get("broken");
		if (f != null) {
			for (String s : f.keySet()) {
				broke += f.get(s);
			}
		}

		return broke;
	}

	/**
	 * Gets amount of items the Player has crafted, including all types, as integer
	 * from statMapValues
	 * 
	 * @return Amount of items crafted
	 */
	public int getItemsCrafted() {
		int crafted = 0;
		HashMap<String, Integer> f = statsMapValues.get("crafted");
		if (f != null) {
			for (String s : f.keySet()) {
				crafted += f.get(s);
			}
		}
		return crafted;
	}

	/**
	 * Gets amount of items the Player has picked up, including all types, as
	 * integer from statMapValues
	 * 
	 * @return Amount of items picked up
	 */
	public int getItemsPickedUp() {
		int picked = 0;
		HashMap<String, Integer> f = statsMapValues.get("picked_up");
		if (f != null) {
			for (String s : f.keySet()) {
				picked += f.get(s);
			}
		}
		return picked;
	}

	/**
	 * Gets amount of times the Player has used items, including all types, as
	 * integer from statMapValues
	 * 
	 * @return Used amount
	 */
	public int getItemsUsed() {
		int used = 0;
		HashMap<String, Integer> f = statsMapValues.get("used");
		if (f != null) {
			for (String s : f.keySet()) {
				used += f.get(s);
			}
		}
		return used;
	}

	/**
	 * Gets amount of times the Player has dropped an item, including all types, as
	 * integer from statMapValues
	 * 
	 * @return Dropped amount
	 */
	public int getItemsDropped() {
		int drop = 0;
		HashMap<String, Integer> f = statsMapValues.get("dropped");
		if (f != null) {
			for (String s : f.keySet()) {
				drop += f.get(s);
			}
		}
		return drop;
	}

	/**
	 * Returns the highest value from the supplied key in statsMapValues
	 * 
	 * @param val Key (stat category)
	 * @return The highest value for the category, or 'None' if key is null
	 */
	public String getMost(String val) {
		String s = "None";
		int amt = 0;
		HashMap<String, Integer> f = statsMapValues.get(val);
		if (f != null) {
			for (String r : f.keySet()) {
				if (f.get(r) > amt) {
					amt = f.get(r);
					s = r;
					s = s.substring(0, 1).toUpperCase() + s.substring(1) + " : " + amt;
				}
			}
		}

		return s;
	}

	/**
	 * Copies and renames the Player's JSON file to testers folder
	 * 
	 * @param s Name to be include in file name (player alias)
	 */
	public void newJson(String s) {
		SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy_hh-mm-ss");
		String date = df.format(new Date());
		try {
			File dir = new File(folder.toString() + "//testers");
			dir.mkdir();
			File f = new File(folder.toString() + "//" + uuidStr + ".json");
			Files.copy(f, new File(folder.toString() + "//testers//" + s + "_" + date + ".json"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int compareTo(Player o) {
		return this.score.compareTo(o.score);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastAct() {
		return lastAct;
	}

	public void setLastAct(String lastAct) {
		this.lastAct = lastAct;
	}

	public String getUuidStr() {
		return uuidStr;
	}

	public void setUuidStr(String uuidStr) {
		this.uuidStr = uuidStr;
	}

	public Integer getScore() {
		return score;
	}

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public Integer getNatDeath() {
		return natDeath;
	}

	public void setNatDeath(Integer natDeath) {
		this.natDeath = natDeath;
	}

	public Integer getNatKill() {
		return natKill;
	}

	public void setNatKill(Integer natKill) {
		this.natKill = natKill;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public File[] getUuids() {
		return uuids;
	}

	public void setUuids(File[] uuids) {
		this.uuids = uuids;
	}

	public String[] getPlayerIds() {
		return playerIds;
	}

	public void setPlayerIds(String[] playerIds) {
		this.playerIds = playerIds;
	}

	public String[] getStats() {
		return stats;
	}

	public void setStats(String[] stats) {
		this.stats = stats;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public File getFolder() {
		return folder;
	}

	public void setFolder(File folder) {
		this.folder = folder;
	}

	public HashMap<String, String[]> getStatsMap() {
		return statsMap;
	}

	public void setStatsMap(HashMap<String, String[]> statsMap) {
		this.statsMap = statsMap;
	}

	public HashMap<String, HashMap<String, Integer>> getStatsMapValues() {
		return statsMapValues;
	}

	public void setStatsMapValues(HashMap<String, HashMap<String, Integer>> statsMapValues) {
		this.statsMapValues = statsMapValues;
	}

	public String getDirr() {
		return dirr;
	}

	public void setDirr(String dirr) {
		this.dirr = dirr;
	}

	public static void main(String[] args) {

	}

}
