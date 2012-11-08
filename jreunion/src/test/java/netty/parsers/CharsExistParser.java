package netty.parsers;

import java.util.regex.*;

import netty.Packet;
import netty.packets.*;
import netty.packets.UseSkillPacket.TargetType;
import netty.parsers.PacketParser.Client;

import org.reunionemu.jreunion.game.Equipment.Slot;
import org.reunionemu.jreunion.game.Player.Race;
import org.reunionemu.jreunion.game.Player.Sex;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
@Client
public class CharsExistParser implements PacketParser {

	static final Pattern regex = Pattern.compile("^chars_exist (\\d+) (\\d+) ([^ ]+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d+) (-?\\d+) (-?\\d+) (-?\\d+) (-?\\d+) (-?\\d+) (-?\\d+) (\\d+)$"); 
	
	@Override
	public Pattern getPattern() {
		return regex;
	}
	
	/*
	 * 
	 * 
	 *charlist += "chars_exist " + slot + " "
				+ (client.getVersion() >= 2000 ? rs.getString("id") + " " : "") // nga client have this extra value in the packet
				+ rs.getString("name") + " "
				+ rs.getString("race") + " "
				+ rs.getString("sex") + " "
				+ rs.getString("hair") + " "
				+ rs.getString("level") + " "
				+ 1 + " " //hp
				+ 1 + " " //hp max
				+ 1 + " " //mana
				+ 1 + " " //mana max
				+ 1 + " " //stamina
				+ 1 + " " //stamina max
				+ 1 + " " //electricity
				+ 1 + " " //electricity max
				+ rs.getString("strength") + " " 
				+ rs.getString("wisdom") + " " 
				+ rs.getString("dexterity") + " "
				+ rs.getString("constitution") + " " 
				+ rs.getString("leadership") + " "
				+ "0" + " " // unknown value
				+ eq.getTypeId(Slot.HELMET) + " " 
				+ eq.getTypeId(Slot.CHEST) + " " 
				+ eq.getTypeId(Slot.PANTS) + " " 
				+ eq.getTypeId(Slot.SHOULDER)	+ " "
				+ eq.getTypeId(Slot.BOOTS) + " " 
				+ eq.getTypeId(Slot.OFFHAND) 
				+ " 0\n"; //unknown value
	 */

	@Override
	public Packet parse(Matcher match, String input) {
		CharsExistPacket packet = new CharsExistPacket();
		int n = 0;
		packet.setSlot(Integer.parseInt(match.group(++n)));
		
		packet.setId(Integer.parseInt(match.group(++n)));
		
		packet.setName(match.group(++n));
		
		packet.setRace(Race.byValue(Integer.parseInt(match.group(++n))));
		
		packet.setSex(Sex.byValue(Integer.parseInt(match.group(++n))));
		
		packet.setHair(Integer.parseInt(match.group(++n)));
		
		packet.setLevel(Integer.parseInt(match.group(++n)));
		
		packet.setHp(Integer.parseInt(match.group(++n)));
		
		packet.setMaxHp(Integer.parseInt(match.group(++n)));
		
		packet.setMana(Integer.parseInt(match.group(++n)));
		
		packet.setMaxMana(Integer.parseInt(match.group(++n)));
		
		packet.setStamina(Integer.parseInt(match.group(++n)));
		
		packet.setMaxStamina(Integer.parseInt(match.group(++n)));
		
		packet.setElectricity(Integer.parseInt(match.group(++n)));
		
		packet.setMaxElectricity(Integer.parseInt(match.group(++n)));
		
		packet.setStrength(Integer.parseInt(match.group(++n)));

		packet.setIntellect(Integer.parseInt(match.group(++n)));
		
		packet.setDexterity(Integer.parseInt(match.group(++n)));

		packet.setConstitution(Integer.parseInt(match.group(++n)));
		
		packet.setLeadership(Integer.parseInt(match.group(++n)));
		
		packet.setUnknown1(Integer.parseInt(match.group(++n)));
		
		packet.setHelmetTypeId(Integer.parseInt(match.group(++n)));
		
		packet.setChestTypeId(Integer.parseInt(match.group(++n)));
		
		packet.setPantsTypeId(Integer.parseInt(match.group(++n)));
		
		packet.setShoulderTypeId(Integer.parseInt(match.group(++n)));
		
		packet.setBootsTypeId(Integer.parseInt(match.group(++n)));
		
		packet.setOffhandTypeId(Integer.parseInt(match.group(++n)));
		
		packet.setUnknown2(Integer.parseInt(match.group(++n)));

		
		
		return packet;
	}
	

}