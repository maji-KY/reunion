package com.googlecode.reunion.jreunion.game;

import java.util.*;

import com.googlecode.reunion.jreunion.server.*;
/**
 * @author Aidamina
 * @license http://reunion.googlecode.com/svn/trunk/license.txt
 */
public class G_Inventory {
	private List<G_InventoryItem> items;
	
	private boolean success = false;
	
	private G_InventoryItem selected=null;

	public G_Inventory() {
		items = new Vector<G_InventoryItem>();
	}

	public void addItem(int posX, int posY, G_Item item, int tab) {
		
		G_InventoryItem inventoryItem = new G_InventoryItem(item, posX, posY, tab);
		
		if (itemFit(tab,posX,posY,item.getSizeX(),item.getSizeY()) == true)
		{
			items.add(inventoryItem);
			success=true;
			//System.out.print("Item Inserted\n");
			//PrintInventoryMap(0);
			//PrintInventoryMap(1);
			//PrintInventoryMap(2);
		}
		
	}

	public void addItem(G_Item item) {
		success=false;
		
		for (int tab = 0; tab < 3; tab++)
			for (int x = 0; x < 8; x++)
				for (int y = 0; y < 6; y++)	{
					if(success==true)
						return;

					addItem(x, y, item, tab);
				}
	}

	public boolean posEmpty(int tab, int posX, int posY) {
		
		Iterator<G_InventoryItem> iter = getInventoryIterator();
		while(iter.hasNext()){
			G_InventoryItem item = (G_InventoryItem) iter.next();
			
			for(int x=item.getPosX(); x<item.getPosX()+item.getItem().getSizeX(); x++)
				for(int y=item.getPosY(); y<item.getPosY()+item.getItem().getSizeY(); y++)
					if(x==posX && y==posY && item.getTab()==tab)
						return false;
		}
		return true;
	}
	
	public boolean freeSlots(int tab, G_Item item){
		for (int x = 0; x < 8; x++)
			for (int y = 0; y < 6; y++)	{
				if(posEmpty(tab,x,y) == true)
					if(itemFit(tab,x,y,item.getSizeX(),item.getSizeY()))
						return true;
			}
		return false;
	}
	
	public boolean itemFit(int tab, int posX, int posY, int sizeX, int sizeY) {
		
		if (posX + sizeX > 8 || posY + sizeY > 6)
			return false;
		
		for(int x=posX; x<posX+sizeX; x++)
			for(int y=posY; y<posY+sizeY; y++)
				if(posEmpty(tab,x,y) == false)
					return false;
		
		return true;
	}
	
	public void removeItem(G_InventoryItem invItem) {
		if(invItem==null)
			return;
		
		while(items.contains(invItem))
			items.remove(invItem);
	}
			
	public G_InventoryItem getItem(int itemId) {
		
		Iterator<G_InventoryItem> iter = getInventoryIterator();
		while(iter.hasNext()){
			G_InventoryItem invItem = iter.next();
			
			if(invItem.getItem().getEntityId() == itemId)
				return invItem;
		}
		return null;
	}
	
	public G_InventoryItem getItem(G_Item item) {
		
		Iterator<G_InventoryItem> iter = getInventoryIterator();
		while(iter.hasNext()){
			G_InventoryItem invItem = iter.next();
			
			if(invItem.getItem() == item)
				return invItem;
		}
		return null;
	}
	
	public G_InventoryItem getItem(int tab, int posX, int posY) {
		
		Iterator<G_InventoryItem>  iter = getInventoryIterator();
		while(iter.hasNext()){
			G_InventoryItem invItem = iter.next();
			
			for(int x=invItem.getPosX(); x<invItem.getPosX()+invItem.getItem().getSizeX(); x++)
				for(int y=invItem.getPosY(); y<invItem.getPosY()+invItem.getItem().getSizeY(); y++)
					if(x==posX && y==posY && invItem.getTab()==tab)
						return invItem;
		}
		return null;
	}
	public int getSize() {
		return items.size();
	}
	public G_InventoryItem getItemSelected() {
		return selected;
	}
	public void setItemSelected(G_InventoryItem selected) {
		//PrintInventoryMap(0);
		this.selected = selected;
	}
	
	public Iterator<G_InventoryItem> getInventoryIterator() {
		return items.iterator();
	}
	
	/******		Manages the Items on the Inventory		******/
	public void moveItem(G_Player player, int tab, int posX, int posY)
	{
		S_Client client = S_Server.getInstance().getNetworkModule().getClient(player);
		
		if (client==null)
			return;
		
		G_InventoryItem oldInvItem=null;
		G_InventoryItem newInvItem=null;
		G_InventoryItem auxItem=null;
		int count=0;
						
		if(player.getInventory().posEmpty(tab,posX,posY) == false)
		{
			if(player.getInventory().getItemSelected() == null)
			{
				newInvItem = player.getInventory().getItem(tab,posX,posY);
				player.getInventory().removeItem(newInvItem);
				player.getInventory().setItemSelected(newInvItem);
			}
			else {
				oldInvItem = player.getInventory().getItemSelected();
				
				for(int x=0; x<oldInvItem.getItem().getSizeX();x++)
					for(int y=0; y<oldInvItem.getItem().getSizeY();y++)
					{
						newInvItem = player.getInventory().getItem(tab,posX+x,posY+y);
						
						if(auxItem != newInvItem){
							auxItem = newInvItem;
							count++;
						}
						
						if(count > 1)
							return;
					}
				
				if(newInvItem==null)
					return;

				oldInvItem.setPosX(posX);
				oldInvItem.setPosY(posY);
				oldInvItem.setTab(tab);
				
				player.getInventory().removeItem(newInvItem);
				player.getInventory().setItemSelected(newInvItem);
				player.getInventory().addItem(posX,posY,oldInvItem.getItem(),tab);
			}
		}
		else {
			newInvItem = player.getInventory().getItemSelected();
			
			if (newInvItem==null)
				return;
			
			//System.out.print("Item Selected: "+newInvItem.getItem().getType()+"\n");
			
			for(int x=0; x<newInvItem.getItem().getSizeX();x++)
				for(int y=0; y<newInvItem.getItem().getSizeY();y++)
				{
					oldInvItem = player.getInventory().getItem(tab,posX+x,posY+y);
					if(auxItem != oldInvItem && oldInvItem != null){
						auxItem = oldInvItem;
						count++;
					}
					if(count > 1)
						return;
				}

			if(auxItem == null)
				player.getInventory().setItemSelected(null);
			else{
				player.getInventory().removeItem(auxItem);
				player.getInventory().setItemSelected(auxItem);
			}
			
			//System.out.print("Item "+newInvItem.getItem().getType()+" is goind to be added\n");
			newInvItem.setPosX(posX);
			newInvItem.setPosY(posY);
			newInvItem.setTab(tab);
			player.getInventory().addItem(posX,posY,newInvItem.getItem(),tab);
		}
	}
	
	public void PrintInventoryMap(int tab) {	//Debug Only
		boolean[][] newInvMap = new boolean[8][6];
				
		for(int x=0; x<8; x++)
			for(int y=0; y<6; y++)
				newInvMap[x][y] = false;
		
		System.out.print("Tab "+tab+": \n");
		Iterator<G_InventoryItem> iter = getInventoryIterator();
		while(iter.hasNext()){
			G_InventoryItem item = iter.next();
			
			for(int x=item.getPosX(); x<item.getPosX()+item.getItem().getSizeX(); x++)
				for(int y=item.getPosY(); y<item.getPosY()+item.getItem().getSizeY(); y++)
					if(item.getTab() == tab)
						newInvMap[x][y] = true;
		}
		
		for(int y=0; y<6; y++)
		{
			for(int x=0; x<8; x++)
			{
				if(newInvMap[x][y] == false)
					System.out.print("0");
				if(newInvMap[x][y] == true)
					System.out.print("1");
			}
			System.out.print("\n");
		}
	}
}