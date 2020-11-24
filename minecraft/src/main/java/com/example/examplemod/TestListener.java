package com.example.examplemod;

import java.util.UUID;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TestListener {
	public Thread gui;
	String playerName, uid;
	UUID id;
	Scoreboard you;
	Player plyr;
	int counter = 30;

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		System.setProperty("java.awt.headless", "false");
		LivingEntity player = event.getEntityLiving();
		playerName = player.getName().getString().toString();
		you = new Scoreboard(new Player(player.getUniqueID(), playerName, ""));
		player.sendMessage(ITextComponent.func_244388_a("BEHAVIOR TRACKER MOD v1.1 : Welcome, " + you.getPlayerAlias()),
				player.getUniqueID());
		id = player.getUniqueID();
		uid = id.toString();
		;

//		System.out.println("TESTING!!!!!");

	}

	public static void main(String[] args) {
	

	}

}
