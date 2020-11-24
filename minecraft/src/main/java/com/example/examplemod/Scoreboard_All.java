package com.example.examplemod;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
/**
 * Scoreboard of all Players (multiplayer) Currently not utilized
 * @author Colby Kopec
 *
 */
public class Scoreboard_All extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Date date;
	private JFrame gui;
	private JButton back, forward;
	private JPanel north, west, east, south, center;
	private JLabel version, nameLabel, timeLabel, actLabel, scoreLabel, statsLabel, activity, gameTime, disTrav, scOvrt,
			kills, deaths, blocksCol, mostCol, itemsCraf, timesEatn;
	private String[] activities;
	private Player[] sampleData;
	private String playerName;
	private int sc, counter, acts, act;

	public Scoreboard_All() {
		URL iconURL = getClass().getResource("/images/sword.png");
		ImageIcon icon = new ImageIcon(iconURL);
		this.setIconImage(icon.getImage());
		playerName = "TestPlayer";
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2); // Centers
																												// GUI

	}

	private JPanel northPanel() {
		JPanel n = new JPanel();
		JPanel one = new JPanel();
		JPanel two = new JPanel();
		JPanel three = new JPanel();
		n.setLayout(new GridLayout(0, 1));
		nameLabel = new JLabel(playerName);
		version = new JLabel("Player Scoreboard User Interface v1.0");
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

	private JPanel centerPanel() {
		sc = 117;
		JPanel c = new JPanel();
		c.setLayout(new BorderLayout());
		JPanel one = new JPanel();
		JPanel two = new JPanel();
		two.setLayout(new BoxLayout(two, BoxLayout.PAGE_AXIS));
		two.setPreferredSize(new Dimension(600, 600));
		JScrollPane scroll = new JScrollPane(two, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(600, 600));

		scoreLabel = new JLabel("Player Scores");
		scoreLabel.setFont((new Font("Georgia", Font.BOLD, 19)));
		ArrayList<Player> data = new ArrayList<Player>();
		Collections.addAll(data, sampleData);
		Collections.sort(data, Collections.reverseOrder());
		int rank = 1;
		for (Player p : data) {
			JLabel l = new JLabel(" #" + rank + "      " + p.getName() + "      " + p.getScore() + "");
			JLabel la = new JLabel("               " + p.getLastAct());
			l.setFont((new Font("Georgia", Font.PLAIN, 18)));
			la.setFont((new Font("Georgia", Font.ITALIC, 12)));
			two.add(l);
			two.add(la);
			rank++;

		}

		one.add(scoreLabel);

		c.add(one, BorderLayout.NORTH);
		c.add(scroll, BorderLayout.CENTER);

		return c;

	}

	private JPanel bottomPanel() {
		JPanel b = new JPanel();
		b.setLayout(new GridLayout(0, 1));
		JPanel one = new JPanel();
		JPanel two = new JPanel();
		JPanel three = new JPanel();
		actLabel = new JLabel("Last Activity");
		actLabel.setFont((new Font("Georgia", Font.BOLD, 14)));
		activity = new JLabel("[15:35] Checked the Scoreboard [-1]");
		activity.setFont((new Font("Georgia", Font.PLAIN, 12)));
		one.add(actLabel);
		two.add(activity);
		b.add(one);
		b.add(two);
		b.setVisible(true);
		return b;
	}

	private JPanel eastPanel() {
		JPanel e = new JPanel();
		JPanel one = new JPanel();
		forward = new JButton(">");
		forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (act == acts - 1) {
					act = 0;
				} else {
					act++;
				}
				activity.setText(activities[act]);

			}
		});
		forward.setSize(100, 100);
		one.add(forward);
		e.setLayout(new BorderLayout());
		e.add(one, BorderLayout.SOUTH);
		return e;
	}

	private JPanel westPanel() {
		JPanel w = new JPanel();
		JPanel one = new JPanel();
		back = new JButton("<");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (act == 0) {
					act = acts - 1;
				} else {
					act--;
				}
				activity.setText(activities[act]);
			}
		});
		back.setSize(100, 100);
		one.add(back);
		w.setLayout(new BorderLayout());
		w.add(one, BorderLayout.SOUTH);
		return w;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		Scoreboard_All test = new Scoreboard_All();

	}

}