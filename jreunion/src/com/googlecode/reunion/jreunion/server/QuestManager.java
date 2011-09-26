package com.googlecode.reunion.jreunion.server;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.googlecode.reunion.jreunion.game.Player;
import com.googlecode.reunion.jreunion.game.Quest;

public class QuestManager {

	java.util.Map<Integer,Quest> quests = new HashMap<Integer,Quest>();
	
	public QuestManager(){
		quests = DatabaseUtils.getStaticInstance().loadAllQuests();
	}
	public Quest getQuest(int id){
		
		return quests.get(id);
	}
	
	public Quest getRandomQuest(Player player){
		if (player == null) return null;
		
		//a quests list that will only contain quests of the player level
		List<Quest> questsList = new Vector<Quest>();
		int randQuestId = -1;
		
		for(Quest quest: quests.values()){
			if(quest.getMinLevel() <= player.getLevel() &&
					quest.getMaxLevel() >= player.getLevel())
				questsList.add(quest);
		} 
		
		if(questsList.isEmpty()){
			Logger.getLogger(QuestManager.class).debug("No quests found for the player level!");
			return null;
		}
		
		//gets a random position from the available quests list.
		while( randQuestId > questsList.size()-1 || randQuestId == -1){
			randQuestId = (int)(Math.random()*100);
		}
		
		return questsList.get(randQuestId);
	}
}