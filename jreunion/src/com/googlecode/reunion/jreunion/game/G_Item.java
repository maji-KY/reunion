package com.googlecode.reunion.jreunion.game;

import com.googlecode.reunion.jreunion.server.S_ParsedItem;
import com.googlecode.reunion.jreunion.server.S_Reference;

/**
 * @author Aidamina
 * @license http://reunion.googlecode.com/svn/trunk/license.txt
 */
public class G_Item extends G_Entity {
	private int uniqueId;

	private int price;

	private int sizeX; // number of cols

	private int sizeY; // number of rows

	private int gemNumber;

	private int extraStats;

	private int type;

	private int posX;

	private int posY;

	private int posZ;

	private int rotation;
	
	private int description;

	public G_Item(int type) {
		super();
		this.type = type;
		loadFromReference(type);
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPrice() {
		return this.price;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeX() {
		return this.sizeX;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public int getSizeY() {
		return this.sizeY;
	}

	public void setGemNumber(int gemNumber) {
		this.gemNumber = gemNumber;
	}

	public int getGemNumber() {
		return this.gemNumber;
	}

	public void setExtraStats(int extraStats) {
		this.extraStats = extraStats;
	}

	public int getExtraStats() {
		return this.extraStats;
	}

	public int getType() {
		return this.type;
	}

	public int getUniqueId() {
		return this.uniqueId;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosX() {
		return this.posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getPosY() {
		return this.posY;
	}

	public void setPosZ(int posZ) {
		this.posZ = posZ;
	}

	public int getPosZ() {
		return this.posZ;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public int getRotation() {
		return this.rotation;
	}
	public void setDescription(int description) {
		this.description = description;
	}
	public int getDescription() {
		return this.description;
	}
	public void loadFromReference(int id)
	{	
	  S_ParsedItem item = S_Reference.getInstance().getItemReference().getItemById(id);
		
	  if (item==null)
	  {
		// cant find Item in the reference continue to load defaults:
		setSizeX(1);
		setSizeY(1);
		setPrice(1);
		setDescription(-1);
	  }
	  else {
		
		if(item.checkMembers(new String[]{"SizeX"}))
		{
			// use member from file
			setSizeX(Integer.parseInt(item.getMemberValue("SizeX")));
		}
		else
		{
			// use default
			setSizeX(1);
		}
		if(item.checkMembers(new String[]{"SizeY"}))
		{
			// use member from file
			setSizeY(Integer.parseInt(item.getMemberValue("SizeY")));
		}
		else
		{
			// use default
			setSizeY(1);
		}
		if(item.checkMembers(new String[]{"Price"}))
		{
			// use member from file
			setPrice(Integer.parseInt(item.getMemberValue("Price")));
		}
		else
		{
			// use default
			setPrice(1);
		}
		if(item.checkMembers(new String[]{"Description"}))
		{
			// use member from file
			setDescription(Integer.parseInt(item.getMemberValue("Description")));
		}
		else
		{
			// use default
			setDescription(-1);
		}
	  }
		
	}

}