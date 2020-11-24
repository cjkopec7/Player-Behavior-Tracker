package com.example.examplemod;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.*;

import javax.swing.*;

import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.SimpleDateFormat;

public class Scoreboard extends JFrame implements ActionListener {
	/**
	 * A GUI representation of the data from a Player object, in the form of a swing
	 * application. Allows one to update information as they play Minecraft, showing
	 * statistics and also a score representation of the data set.
	 */
	private static final long serialVersionUID = 1L;
	private Date date;
	private JFrame gui;
	private JButton log, term, refresh, minedB, usedB, dropB, pickB, brokeB, craftB, killB, deathB, miscB;
	private JPanel north, west, east, south, center;
	private JLabel version, nameLabel, timeLabel, actLabel, scoreLabel, statsLabel, activity, gameTime, disTrav,
			blocksMined, kills, deaths, itemCraft, itemUsed, itemDrop, itemBroken, itemPicked, misc;
	private String playerName, playerAlias;
	private int sc, scoreChecks = 0, acts, act;
	private Player player;
	private DecimalFormat df = new DecimalFormat("#.###");

	/**
	 * A constructor for the Scoreboard Class
	 * <p>
	 * A GUI, it takes a Player as an argument and display's it's data upon the
	 * interface. The swing application is a JFrame, accompanied by several JPanels
	 * and JButtons. The main panel displays a summary of the Player's JSON file.
	 * The buttons below refresh the interface, generate and show a logfile, or
	 * terminate the software (which also creates and displays a log)
	 * 
	 * @param plyr Player object
	 */
	public Scoreboard(Player plyr) {

		JOptionPane.showMessageDialog(this, "   Welcome to the CSC-450 Psychology Minecraft Mod, " + plyr.getName()
				+ ".\n\nThe following GUI will show your score and statistics for the session\n PAUSE MINECRAFT BEFORE EACH SCORE REFRESH. Click OK to begin",
				"Welcome", JOptionPane.INFORMATION_MESSAGE);

		player = plyr;
		player.updateScore();
		sc = plyr.getScore();
		URL iconURL = getClass().getResource("/images/sword.png");
		ImageIcon icon = new ImageIcon(iconURL);
		this.setIconImage(icon.getImage());
		playerName = plyr.getName();
		playerAlias = JOptionPane.showInputDialog(this, "Please enter your name or alias", "Register",
				JOptionPane.INFORMATION_MESSAGE);
		date = new Date();
		timeLabel = new JLabel(date.toString());
		this.setTitle(playerName + " Scoreboard");
		this.setLayout(new BorderLayout());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // Gets the user's screen resolution
		setPreferredSize(new Dimension(550, 600));
		this.setVisible(true);
		north = northPanel();
		south = bottomPanel();
		east = eastPanel();
		west = westPanel();
		center = centerPanel();
		this.add(north, BorderLayout.NORTH);
		this.add(south, BorderLayout.SOUTH);
		this.add(center, BorderLayout.CENTER);
		this.add(east, BorderLayout.EAST);
		this.add(west, BorderLayout.WEST);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pack();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2); // Centers
																												// GUI

	}

	/**
	 * Generates the northern panel
	 * 
	 * @return Panel
	 */
	private JPanel northPanel() {
		JPanel n = new JPanel();
		JPanel one = new JPanel();
		JPanel two = new JPanel();
		JPanel three = new JPanel();
		n.setLayout(new GridLayout(0, 1));
		nameLabel = new JLabel(playerAlias);
		version = new JLabel("Player Behavior Tracker Interface v1.1");
		version.setFont((new Font("Georgia", Font.ITALIC, 10)));
		nameLabel.setFont((new Font("Georgia", Font.BOLD, 30)));
		timeLabel.setFont((new Font("Georgia", Font.PLAIN, 12)));
		one.add(version);
		two.add(nameLabel);
		three.add(timeLabel);
		n.add(one);
		n.add(two);
		n.add(three);
		n.setVisible(true);
		return n;
	}

	/**
	 * Generates the center panel
	 * 
	 * @return Panel
	 */

	private JPanel centerPanel() {
		DecimalFormat df = new DecimalFormat("###.##");
		JPanel c = new JPanel();
		c.setLayout(new GridLayout(0, 1));
		JPanel one = new JPanel();
		JPanel two = new JPanel();
		JPanel three = new JPanel();
		JPanel four = new JPanel();
		JPanel five = new JPanel();
		JPanel six = new JPanel();
		JPanel sevn = new JPanel();
		JPanel eght = new JPanel();
		JPanel nine = new JPanel();
		JPanel ten = new JPanel();
		JPanel elev = new JPanel();
		JPanel twel = new JPanel();
		JPanel thirt = new JPanel();

		statsLabel = new JLabel("Statistics");
		scoreLabel = new JLabel("Current Score: " + sc);
		gameTime = new JLabel("Time Played: " + df.format(player.getPlayTime()) + " minutes");
		disTrav = new JLabel("Distance Traveled: " + (df.format(player.getDistance())) + " meters");
		blocksMined = new JLabel("Blocks Mined: " + player.getAmountMined());
		itemBroken = new JLabel("Items Broken: " + player.getBrokeItems());
		itemCraft = new JLabel("Items Crafted: " + player.getItemsCrafted());
		itemUsed = new JLabel("Items Used: " + player.getItemsUsed());
		itemDrop = new JLabel("Items Dropped: " + player.getItemsDropped());
		itemPicked = new JLabel("Items Picked Up: " + player.getItemsPickedUp());
		kills = new JLabel("Kills: " + player.getMobKills());
		deaths = new JLabel("Deaths: " + player.getDeaths());
		misc = new JLabel("Miscellaneous");

		statsLabel.setFont((new Font("Georgia", Font.PLAIN, 22)));
		scoreLabel.setFont((new Font("Georgia", Font.BOLD, 19)));
		gameTime.setFont((new Font("Georgia", Font.PLAIN, 12)));
		disTrav.setFont((new Font("Georgia", Font.PLAIN, 12)));
		blocksMined.setFont((new Font("Georgia", Font.PLAIN, 12)));
		itemBroken.setFont((new Font("Georgia", Font.PLAIN, 12)));
		itemCraft.setFont((new Font("Georgia", Font.PLAIN, 12)));
		itemUsed.setFont((new Font("Georgia", Font.PLAIN, 12)));
		itemDrop.setFont((new Font("Georgia", Font.PLAIN, 12)));
		itemPicked.setFont((new Font("Georgia", Font.PLAIN, 12)));
		kills.setFont((new Font("Georgia", Font.PLAIN, 12)));
		deaths.setFont((new Font("Georgia", Font.PLAIN, 12)));
		misc.setFont((new Font("Georgia", Font.ITALIC, 12)));

		setLabelToolTips();

		minedB = new JButton("");
		minedB.setSize(50, 50);
		minedB.addActionListener(addListener("mined", c));
		usedB = new JButton("");
		usedB.setSize(50, 50);
		usedB.addActionListener(addListener("used", c));
		dropB = new JButton("");
		dropB.setSize(50, 50);
		dropB.addActionListener(addListener("dropped", c));
		pickB = new JButton("");
		pickB.setSize(50, 50);
		pickB.addActionListener(addListener("picked_up", c));
		brokeB = new JButton("");
		brokeB.setSize(50, 50);
		brokeB.addActionListener(addListener("broken", c));
		craftB = new JButton("");
		craftB.setSize(50, 50);
		craftB.addActionListener(addListener("crafted", c));
		killB = new JButton("");
		killB.setSize(50, 50);
		killB.addActionListener(addListener("killed", c));
		deathB = new JButton("");
		deathB.setSize(50, 50);
		deathB.addActionListener(addListener("killed_by", c));
		miscB = new JButton("");
		miscB.setSize(50, 50);
		miscB.addActionListener(addListener("custom", c));

		one.add(scoreLabel);
		two.add(statsLabel);
		three.add(gameTime);
		four.add(disTrav);
		five.add(minedB);
		five.add(blocksMined);
		six.add(usedB);
		six.add(itemUsed);
		sevn.add(dropB);
		sevn.add(itemDrop);
		twel.add(pickB);
		twel.add(itemPicked);
		eght.add(brokeB);
		eght.add(itemBroken);
		nine.add(craftB);
		nine.add(itemCraft);
		ten.add(killB);
		ten.add(kills);
		elev.add(deathB);
		elev.add(deaths);
		thirt.add(miscB);
		thirt.add(misc);

		c.add(one);
		c.add(two);
		c.add(three);
		c.add(four);
		c.add(five);
		c.add(six);
		c.add(sevn);
		c.add(twel);
		c.add(eght);
		c.add(nine);
		c.add(ten);
		c.add(elev);
		c.add(thirt);
		return c;

	}
	
	public void setLabelToolTips() {
		blocksMined.setToolTipText("" + player.getMost("mined").replace("_", " "));
		itemPicked.setToolTipText("" + player.getMost("picked_up").replace("_", " "));
		itemDrop.setToolTipText("" + player.getMost("dropped").replace("_", " "));
		itemUsed.setToolTipText("" + player.getMost("used").replace("_", " "));
		itemCraft.setToolTipText("" + player.getMost("crafted").replace("_", " "));
		itemBroken.setToolTipText("" + player.getMost("broken").replace("_", " "));
		kills.setToolTipText("" + player.getMost("killed").replace("_", " "));
		deaths.setToolTipText("" + player.getMost("killed_by").replace("_", " "));
		misc.setToolTipText("Distance: n/1000 meters --- Time: n/1200 minutes");
	}

	/**
	 * Generates the southern panel
	 * 
	 * @return Panel
	 */

	private JPanel bottomPanel() {
		JPanel b = new JPanel();
		b.setLayout(new GridLayout(0, 1));
		JPanel one = new JPanel();
		refresh = new JButton("Refresh");
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				scoreChecks++;
				int scoreloss = scoreChecks * 5;
				player.runJSONreader();
				timeLabel.setText(new Date().toString());
				scoreLabel.setText("Current Score: " + (player.getScore() - scoreloss));
				gameTime.setText("Time Played: " + df.format(player.getPlayTime()) + " minutes");
				disTrav.setText("Distance Traveled: " + (df.format(player.getDistance())) + " meters");
				blocksMined.setText("Blocks Mined: " + player.getAmountMined());
				itemBroken.setText("Items Broken: " + player.getBrokeItems());
				itemCraft.setText("Items Crafted: " + player.getItemsCrafted());
				itemUsed.setText("Items Used: " + player.getItemsUsed());
				itemDrop.setText("Items Dropped: " + player.getItemsDropped());
				itemPicked.setText("Items Picked Up: " + player.getItemsPickedUp());
				kills.setText("Kills: " + player.getMobKills());
				deaths.setText("Deaths: " + player.getDeaths());
				setLabelToolTips();

			}
		});
		refresh.setSize(100, 100);
		one.add(refresh);
		b.add(one);
		b.setVisible(true);
		return b;
	}

	/**
	 * Generates the eastern panel
	 * 
	 * @return Panel
	 */
	private JPanel eastPanel() {
		JPanel e = new JPanel();
		JPanel one = new JPanel();
		term = new JButton("Terminate Test");
		term.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.newJson(playerAlias);
				generateLog(player);
				setLabelToolTips();
				System.exit(0);

			}
		});
		term.setSize(100, 100);
		one.add(term);
		e.setLayout(new BorderLayout());
		e.add(one, BorderLayout.SOUTH);
		return e;
	}

	/**
	 * Generates the western panel
	 * 
	 * @return Panel
	 */
	private JPanel westPanel() {
		JPanel w = new JPanel();
		JPanel one = new JPanel();
		log = new JButton("Generate Log");
		log.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.newJson(playerAlias);
				generateLog(player);

			}
		});
		log.setSize(100, 100);
		one.add(log);
		w.setLayout(new BorderLayout());
		w.add(one, BorderLayout.SOUTH);
		return w;
	}

	public ActionListener addListener(String s, JPanel p) {
		ActionListener a = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(p, player.getListString(player.getList(s)));
			}

		};
		return a;
	}

	public String generateLog(Player p) {
		SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy_hh-mm-ss");
		String date = df.format(new Date());
		String s = "CSC-450 PLAYER BEHAVIOR TRACKER MINECRAFT MOD v1.1\n" + new Date().toString() + "\n\n" + playerAlias
				+ " Log File\nMojang Account: '" + playerName + "' // UUID: " + player.getUuidStr() + "\nScore: "
				+ (sc - (scoreChecks * 5)) + " // Score Refreshes: " + scoreChecks
				+ "\n\n___SUMMARIZED STATISTICS___\n\nTime Played: " + player.getPlayTime()
				+ " minutes\nDistance Traveled: " + player.getDistance() + " meters\nBlocks Mined: "
				+ player.getAmountMined() + "\nItems Broken: " + player.getBrokeItems() + "\nItems Crafted: "
				+ player.getItemsCrafted() + "\nItems Used: " + player.getItemsUsed() + "\nItems Dropped: "
				+ player.getItemsDropped() + "\nItems Picked Up: " + player.getItemsPickedUp() + "\nKills: "
				+ player.getMobKills() + "\nDeaths: " + player.getDeaths() + "\n\n_______________\n\n"
				+ "Block Most Mined: " + player.getMost("mined") + "\nItem Broken Most: " + player.getMost("broken")
				+ "\nItem Crafted Most: " + player.getMost("crafted") + "\nItem Used Most: " + player.getMost("used")
				+ "\nItem Dropped Most: " + player.getMost("dropped") + "\nItem Picked Up Most: "
				+ player.getMost("picked_up") + "\nKilled Most: " + player.getMost("killed") + "\nKilled By Most: "
				+ player.getMost("killed_by");

		String summary = s;
		JOptionPane.showMessageDialog(this, summary, "Log Summary", JOptionPane.INFORMATION_MESSAGE);

		String raw = "\n\n___RAW STATISICS___ [Distance=n/1000 meters, Time=n/1200 minutes]\n\n";
		for (String e : player.getStatsMap().keySet()) {
			String[] m = player.getStatsMap().get(e);
			raw = raw + e + "\n";
			if (m != null) {
				for (String w : m) {
					raw = raw + ("   " + w + "\n").replace(".0", "");
				}
				raw = raw + "\n";
			}
		}
		s = s + raw + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
		File f = new File(player.getFolder().toString() + "//testers//" + playerAlias + "_" + date + ".txt");
		try {
			f.createNewFile();
			FileWriter fw = new FileWriter(f);
			fw.write(s);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return s;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public JFrame getGui() {
		return gui;
	}

	public void setGui(JFrame gui) {
		this.gui = gui;
	}

	public JButton getLog() {
		return log;
	}

	public void setLog(JButton log) {
		this.log = log;
	}

	public JButton getTerm() {
		return term;
	}

	public void setTerm(JButton term) {
		this.term = term;
	}

	public JButton getRefresh() {
		return refresh;
	}

	public void setRefresh(JButton refresh) {
		this.refresh = refresh;
	}

	public JButton getMinedB() {
		return minedB;
	}

	public void setMinedB(JButton minedB) {
		this.minedB = minedB;
	}

	public JButton getUsedB() {
		return usedB;
	}

	public void setUsedB(JButton usedB) {
		this.usedB = usedB;
	}

	public JButton getDropB() {
		return dropB;
	}

	public void setDropB(JButton dropB) {
		this.dropB = dropB;
	}

	public JButton getPickB() {
		return pickB;
	}

	public void setPickB(JButton pickB) {
		this.pickB = pickB;
	}

	public JButton getBrokeB() {
		return brokeB;
	}

	public void setBrokeB(JButton brokeB) {
		this.brokeB = brokeB;
	}

	public JButton getCraftB() {
		return craftB;
	}

	public void setCraftB(JButton craftB) {
		this.craftB = craftB;
	}

	public JButton getKillB() {
		return killB;
	}

	public void setKillB(JButton killB) {
		this.killB = killB;
	}

	public JButton getDeathB() {
		return deathB;
	}

	public void setDeathB(JButton deathB) {
		this.deathB = deathB;
	}

	public JButton getMiscB() {
		return miscB;
	}

	public void setMiscB(JButton miscB) {
		this.miscB = miscB;
	}

	public JPanel getNorth() {
		return north;
	}

	public void setNorth(JPanel north) {
		this.north = north;
	}

	public JPanel getWest() {
		return west;
	}

	public void setWest(JPanel west) {
		this.west = west;
	}

	public JPanel getEast() {
		return east;
	}

	public void setEast(JPanel east) {
		this.east = east;
	}

	public JPanel getSouth() {
		return south;
	}

	public void setSouth(JPanel south) {
		this.south = south;
	}

	public JPanel getCenter() {
		return center;
	}

	public void setCenter(JPanel center) {
		this.center = center;
	}

	public JLabel getVersion() {
		return version;
	}

	public void setVersion(JLabel version) {
		this.version = version;
	}

	public JLabel getNameLabel() {
		return nameLabel;
	}

	public void setNameLabel(JLabel nameLabel) {
		this.nameLabel = nameLabel;
	}

	public JLabel getTimeLabel() {
		return timeLabel;
	}

	public void setTimeLabel(JLabel timeLabel) {
		this.timeLabel = timeLabel;
	}

	public JLabel getActLabel() {
		return actLabel;
	}

	public void setActLabel(JLabel actLabel) {
		this.actLabel = actLabel;
	}

	public JLabel getScoreLabel() {
		return scoreLabel;
	}

	public void setScoreLabel(JLabel scoreLabel) {
		this.scoreLabel = scoreLabel;
	}

	public JLabel getStatsLabel() {
		return statsLabel;
	}

	public void setStatsLabel(JLabel statsLabel) {
		this.statsLabel = statsLabel;
	}

	public JLabel getActivity() {
		return activity;
	}

	public void setActivity(JLabel activity) {
		this.activity = activity;
	}

	public JLabel getGameTime() {
		return gameTime;
	}

	public void setGameTime(JLabel gameTime) {
		this.gameTime = gameTime;
	}

	public JLabel getDisTrav() {
		return disTrav;
	}

	public void setDisTrav(JLabel disTrav) {
		this.disTrav = disTrav;
	}

	public JLabel getBlocksMined() {
		return blocksMined;
	}

	public void setBlocksMined(JLabel blocksMined) {
		this.blocksMined = blocksMined;
	}

	public JLabel getKills() {
		return kills;
	}

	public void setKills(JLabel kills) {
		this.kills = kills;
	}

	public JLabel getDeaths() {
		return deaths;
	}

	public void setDeaths(JLabel deaths) {
		this.deaths = deaths;
	}

	public JLabel getItemCraft() {
		return itemCraft;
	}

	public void setItemCraft(JLabel itemCraft) {
		this.itemCraft = itemCraft;
	}

	public JLabel getItemUsed() {
		return itemUsed;
	}

	public void setItemUsed(JLabel itemUsed) {
		this.itemUsed = itemUsed;
	}

	public JLabel getItemDrop() {
		return itemDrop;
	}

	public void setItemDrop(JLabel itemDrop) {
		this.itemDrop = itemDrop;
	}

	public JLabel getItemBroken() {
		return itemBroken;
	}

	public void setItemBroken(JLabel itemBroken) {
		this.itemBroken = itemBroken;
	}

	public JLabel getItemPicked() {
		return itemPicked;
	}

	public void setItemPicked(JLabel itemPicked) {
		this.itemPicked = itemPicked;
	}

	public JLabel getMisc() {
		return misc;
	}

	public void setMisc(JLabel misc) {
		this.misc = misc;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerAlias() {
		return playerAlias;
	}

	public void setPlayerAlias(String playerAlias) {
		this.playerAlias = playerAlias;
	}

	public int getSc() {
		return sc;
	}

	public void setSc(int sc) {
		this.sc = sc;
	}

	public int getScoreChecks() {
		return scoreChecks;
	}

	public void setScoreChecks(int scoreChecks) {
		this.scoreChecks = scoreChecks;
	}

	public int getActs() {
		return acts;
	}

	public void setActs(int acts) {
		this.acts = acts;
	}

	public int getAct() {
		return act;
	}

	public void setAct(int act) {
		this.act = act;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public DecimalFormat getDf() {
		return df;
	}

	public void setDf(DecimalFormat df) {
		this.df = df;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static void main(String[] args) {
		Scoreboard test = new Scoreboard(new Player(new UUID(2, 2), "TestPlayer", ""));

	}

}