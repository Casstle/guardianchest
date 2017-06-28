package me.twentyonez.guardianchest.util;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import me.twentyonez.guardianchest.GuardianChest;
import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */

public class ConfigHelper {

	public ConfigHelper() {
		
	}
	
	public static Double applyDamageOnEquip;
	public static boolean damageOnEquipPercentage;
	
	public static boolean requireGuardianIdol;
	public static boolean defaultsToTier2;
	public static Integer levelCostGuardianTier1;
	public static Integer levelCostBoundMapTier1;
	public static Integer maxRadiusToSearchForAFreeSpot;
	public static Integer timeBeforeUnsecure;
	public static boolean informCoords;
	public static boolean returnChestToInventory;
	
	public static boolean anyEnchantSoulBinds;
	public static boolean makeAllItemsDrop;
	public static Integer maxSoulBoundItems;
	
	public static void setupConfig(Configuration cfg) {
		try {
			// Damage configuration
			Property applyDamageOnEquipProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.Damage"), "applyDamageOnEquip", 10.0D);
			applyDamageOnEquipProp.comment = LanguageRegistry.instance().getStringLocalization("config.applyDamage.onEquip");
			applyDamageOnEquip = applyDamageOnEquipProp.getDouble(10.0D);
			
			Property damageOnEquipPercentageProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.Damage"), "damageOnEquipPercentage", true);
			damageOnEquipPercentageProp.comment = LanguageRegistry.instance().getStringLocalization("config.Damage.isPercentual");
			damageOnEquipPercentage = damageOnEquipPercentageProp.getBoolean(true);
			
			// General configuration
			Property requireGuardianIdolProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.General"), "requireGuardianIdol", true);
			requireGuardianIdolProp.comment = LanguageRegistry.instance().getStringLocalization("config.GuardianIdol.Requirement").replace("%1", LanguageRegistry.instance().getStringLocalization("item.guardianTier1.name"));
			requireGuardianIdol = requireGuardianIdolProp.getBoolean(true);
			
			Property defaultsToTier2Prop = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.General"), "defaultsToTier2", false);
			defaultsToTier2Prop.comment = LanguageRegistry.instance().getStringLocalization("config.GuardianIdol.Defaults").replace("%2", LanguageRegistry.instance().getStringLocalization("item.guardianTier2.name"));
			defaultsToTier2 = defaultsToTier2Prop.getBoolean(false);
			
			Property levelCostGuardianTier1Prop = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.General"), "levelCostGuardianTier1", 1);
			levelCostGuardianTier1Prop.comment = LanguageRegistry.instance().getStringLocalization("config.GuardianTier1.LevelRequirement").replace("%1", LanguageRegistry.instance().getStringLocalization("item.guardianTier0.name"));
			levelCostGuardianTier1 = levelCostGuardianTier1Prop.getInt(1);
			
			Property levelCostBoundMapTier1Prop = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.General"), "levelCostBoundMapTier1", 10);
			levelCostBoundMapTier1Prop.comment = LanguageRegistry.instance().getStringLocalization("config.BoundMapTier1.LevelRequirement").replace("%1", LanguageRegistry.instance().getStringLocalization("item.boundMapTier0.name"));
			levelCostBoundMapTier1 = levelCostBoundMapTier1Prop.getInt(10);
			
			Property maxRadiusToSearchForAFreeSpotProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.General"), "maxRadiusToSearchForAFreeSpot", 5);
			maxRadiusToSearchForAFreeSpotProp.comment = LanguageRegistry.instance().getStringLocalization("config.maxDistanceToSearchForAFreeSpot.Radius").replace("%1", LanguageRegistry.instance().getStringLocalization("tile.guardianChest.name"));
			maxRadiusToSearchForAFreeSpot = maxRadiusToSearchForAFreeSpotProp.getInt(5);
			
			Property timeBeforeUnsecureProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.General"), "timeBeforeUnsecure", 300);
			timeBeforeUnsecureProp.comment = LanguageRegistry.instance().getStringLocalization("config.timeBeforeUnsecure.Seconds").replace("%1", LanguageRegistry.instance().getStringLocalization("tile.guardianChest.name"));
			timeBeforeUnsecure = timeBeforeUnsecureProp.getInt(300);
			
			Property informCoordsProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.General"), "informCoords", true);
			informCoordsProp.comment = LanguageRegistry.instance().getStringLocalization("config.Inform.ChestCoordinates").replace("%1", LanguageRegistry.instance().getStringLocalization("tile.guardianChest.name"));
			informCoords = informCoordsProp.getBoolean(true);
			
			Property returnChestToInventoryProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.General"), "returnChestToInventory", true);
			returnChestToInventoryProp.comment = LanguageRegistry.instance().getStringLocalization("config.Inform.returnChestToInventory").replace("%1", LanguageRegistry.instance().getStringLocalization("item.guardianTier0.name\t")).replace("%2", LanguageRegistry.instance().getStringLocalization("item.boundMapTier0.name"));
			returnChestToInventory = returnChestToInventoryProp.getBoolean(true);
			
			// Soulbinding configuration
			Property anyEnchantSoulBindsProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.Soulbinding"), "anyEnchantSoulBinds", false);
			anyEnchantSoulBindsProp.comment = LanguageRegistry.instance().getStringLocalization("config.Enchantments.AreAllSoulBound");
			anyEnchantSoulBinds = anyEnchantSoulBindsProp.getBoolean(false);
			
			Property makeAllItemsDropProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.Soulbinding"), "makeAllItemsDrop", true);
			makeAllItemsDropProp.comment = LanguageRegistry.instance().getStringLocalization("config.AllItems.MakeDrop");
			makeAllItemsDrop = makeAllItemsDropProp.getBoolean(true);
			
			Property maxSoulBoundItemsProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.Soulbinding"), "maxSoulBoundItems", 5);
			maxSoulBoundItemsProp.comment = LanguageRegistry.instance().getStringLocalization("config.maxSoulBoundItems").replace("%1", LanguageRegistry.instance().getStringLocalization("tile.guardianChest.name"));
			maxSoulBoundItems = maxSoulBoundItemsProp.getInt(5);

		} catch(Exception exception) {
			GuardianChest.logger.log(Level.ERROR, LanguageRegistry.instance().getStringLocalization("config.Error.Message"));
			exception.printStackTrace();
		} finally {
			if(cfg.hasChanged()) {
				cfg.save();
			}
		}
	}

}
