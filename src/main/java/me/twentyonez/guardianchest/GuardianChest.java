package me.twentyonez.guardianchest;

import me.twentyonez.guardianchest.block.GCBlocks;
import me.twentyonez.guardianchest.common.GCEventHandler;
import me.twentyonez.guardianchest.common.ServerProxy;
import me.twentyonez.guardianchest.compat.InitCompatCheck;
import me.twentyonez.guardianchest.item.ItemBoundMapTier0;
import me.twentyonez.guardianchest.item.ItemBoundMapTier1;
import me.twentyonez.guardianchest.item.ItemGuardianTier0;
import me.twentyonez.guardianchest.item.ItemGuardianTier1;
import me.twentyonez.guardianchest.item.ItemGuardianTier2;
import me.twentyonez.guardianchest.util.ConfigHelper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */
@Mod(modid = "GuardianChest", name = "Guardian Chest", version = MinecraftForge.MC_VERSION)
public class GuardianChest {

	@SidedProxy(clientSide = "me.twentyonez.guardianchest.common.ClientProxy", serverSide = "me.twentyonez.guardianchest.common.ServerProxy") 
	public static ServerProxy proxy;
	
	public static ItemGuardianTier0 guardianTier0 = new ItemGuardianTier0();
	public static ItemGuardianTier1 guardianTier1 = new ItemGuardianTier1();
	public static ItemGuardianTier2 guardianTier2 = new ItemGuardianTier2();
	public static ItemBoundMapTier0 boundMapTier0 = new ItemBoundMapTier0();
	public static ItemBoundMapTier1 boundMapTier1 = new ItemBoundMapTier1();
	
	public static GuardianChest modInstance;
	
	public static Logger logger;
	public static final boolean DEBUG = false;
	
	
	public static CreativeTabs GCtab = new CreativeTabs("CreativeTabName")
	{
		@SideOnly(Side.CLIENT)
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(GCBlocks.GCChest);
		}
	};
	
	
	public GuardianChest() {
		
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		ConfigHelper.setupConfig(new Configuration(event.getSuggestedConfigurationFile()));
		if (ConfigHelper.requireGuardianIdol) {
			GameRegistry.registerItem(guardianTier0, "guardianTier0");
			GameRegistry.registerItem(guardianTier1, "guardianTier1");
			GameRegistry.registerItem(guardianTier2, "guardianTier2");
			GameRegistry.registerItem(boundMapTier0, "boundMapTier0");
			GameRegistry.registerItem(boundMapTier1, "boundMapTier1");
			guardianTier0.setCreativeTab(GCtab);
			guardianTier1.setCreativeTab(GCtab);
			guardianTier2.setCreativeTab(GCtab);
			boundMapTier0.setCreativeTab(GCtab);
			boundMapTier1.setCreativeTab(GCtab);
		}
		GCBlocks.mainRegistry();
		proxy.registerTileEntities();
		proxy.registerRenderThings();
		
	}
	
	@EventHandler
	public void loadMod(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(GCEventHandler.instance);
		FMLCommonHandler.instance().bus().register(GCEventHandler.instance);
		
		if (ConfigHelper.requireGuardianIdol) {
			GameRegistry.addRecipe(new ItemStack(guardianTier0), "srs", "tct", "srs", 's', Blocks.stone, 'c', Blocks.chest, 'r', Items.redstone, 't', Blocks.redstone_torch);
			GameRegistry.addShapelessRecipe(new ItemStack(guardianTier2), guardianTier1, boundMapTier1);
			GameRegistry.addShapelessRecipe(new ItemStack(boundMapTier0), guardianTier1, Items.map);
			GameRegistry.addShapelessRecipe(new ItemStack(boundMapTier1), guardianTier2);
		}
	}
	
	@EventHandler
	public void finishLoading(FMLPostInitializationEvent event) {
		InitCompatCheck.getInstance().checkMods();
	}

}