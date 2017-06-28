package me.twentyonez.guardianchest.common;

import java.util.HashMap;

/**
 * GuardianChest mod
 *
 * @author LemADEC
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */
public enum EnumInventoryType {
	VANILLA_MAIN       ("vanillaMain"),
	VANILLA_ARMOR      ("vanillaArmor"),
	BAUBLES            ("baubles"),
	RPG_INVENTORY      ("rpgInventory"),
	GALACTICRAFT       ("galacticraft"), 
	BATTLEGEAR         ("battlegear"), 
	CAMPINGMOD         ("campingMod"),
	TC_ACCESSORY       ("tcAccessory"), 
	TC_KNAPSACK        ("tcKnapsack"),
	TRAVELLERS_GEAR    ("travellersGear"),
	MISC               ("misc"),    // not used?
	;
	
	private final String unlocalizedName;
	
	// cached values
	public static final int length;
	private static final HashMap<Integer, EnumInventoryType> ID_MAP = new HashMap<>();
	
	static {
		length = EnumInventoryType.values().length;
		for (EnumInventoryType componentType : values()) {
			ID_MAP.put(componentType.ordinal(), componentType);
		}
	}
	
	EnumInventoryType(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
	}
	
	public static EnumInventoryType get(final int damage) {
		return ID_MAP.get(damage);
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}
}
