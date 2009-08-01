package com.googlecode.reunion.jreunion.game;

import java.util.Iterator;

import com.googlecode.reunion.jreunion.server.*;

/**
 * @author Aidamina
 * @license http://reunion.googlecode.com/svn/trunk/license.txt
 */
public class G_Merchant extends G_Npc {
	
	public G_Merchant(int type) {
		super(type);
	}
	
	/******		Open Merchant Shop		******/
	public void openShop(G_Player player, int uniqueid){
		
		S_Client client = S_Server.getInstance().getNetworkModule().getClient(player);
		
		if(client == null)
			return;
		
		G_Npc npc = S_Server.getInstance().getWorldModule().getNpcManager().getNpc(uniqueid);
		
		String packetData = "shop_rate "+npc.getBuyRate()+ " "+npc.getSellRate()+"\n";
		S_Server.getInstance().getNetworkModule().SendPacket(client.networkId, packetData);
		
		Iterator<G_Item> itemListIter = npc.itemsListIterator();
		
		while(itemListIter.hasNext()){
			G_Item item = itemListIter.next();
			
			packetData = "shop_item "+item.getType()+"\n"; 
			S_Server.getInstance().getNetworkModule().SendPacket(client.networkId, packetData);
		}
	}
	
	/******		Sell items to merchant shop		******/
	public void sellItem(G_Player player, int npcUniqueid){
		S_Client client = S_Server.getInstance().getNetworkModule().getClient(player);
		
		if(client == null)
			return;
		
		G_Npc npc = S_Server.getInstance().getWorldModule().getNpcManager().getNpc(npcUniqueid);
		G_Item item = player.getInventory().getItemSelected().getItem();
						
		player.updateStatus(10,((item.getPrice()*npc.getSellRate())/100),0);
		player.getInventory().setItemSelected(null);
		S_DatabaseUtils.getInstance().deleteItem(item);
	}
	
	/******		Buy items from merchant shop		******/
	public void buyItem(G_Player player, int npcUniqueid, int itemType, int tab, int qnt){
		
		S_Client client = S_Server.getInstance().getNetworkModule().getClient(player);
		
		if(client == null)
			return;
		
		//if(player.getInventory().freeSlots(tab) == false)
		//	return;
		
		G_Npc npc = S_Server.getInstance().getWorldModule().getNpcManager().getNpc(npcUniqueid);
		G_Item item = new G_Item(itemType);
		int count = 0;
		
		if((player.getLime()-item.getPrice()) < 0){
			String packetData = "msg Not enough lime.\n";
			S_Server.getInstance().getNetworkModule().SendPacket(client.networkId, packetData);
			return;
		}
		
		for(int i=0; i<qnt; i++){
			if(player.getInventory().freeSlots(tab,item) == false)
				break;
			item = S_ItemFactory.createItem(itemType);
			player.pickItem(item.getEntityId());
			count++;
		}
		player.updateStatus(10,((item.getPrice()*npc.getBuyRate())/100)*(-1)*count,0);
		
	}
	
}