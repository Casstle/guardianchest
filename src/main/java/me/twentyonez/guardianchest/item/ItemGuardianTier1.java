package me.twentyonez.guardianchest.item;

import me.twentyonez.guardianchest.GuardianChest;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.LanguageRegistry;
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
public class ItemGuardianTier1 extends Item {

	public ItemGuardianTier1() {
		setUnlocalizedName("guardianTier1");
		setTextureName("guardianchest:guardianTier1");
		setCreativeTab(GuardianChest.GCtab);
		setMaxStackSize(1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(LanguageRegistry.instance().getStringLocalization("desc.guardianTier1.Line1").replace("%1", LanguageRegistry.instance().getStringLocalization("item.boundMapTier0.name")));
		list.add(LanguageRegistry.instance().getStringLocalization("desc.guardianTier1.Line2").replace("%1", LanguageRegistry.instance().getStringLocalization("item.boundMapTier0.name")));
		list.add(LanguageRegistry.instance().getStringLocalization("desc.guardianTier1.Line3").replace("%1", LanguageRegistry.instance().getStringLocalization("item.boundMapTier0.name")));
		list.add(LanguageRegistry.instance().getStringLocalization("desc.guardianTier1.Line4").replace("%1", LanguageRegistry.instance().getStringLocalization("item.boundMapTier0.name")));
	}

	/**
	 *
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
    	if(world.isRemote) {
    		player.addChatComponentMessage(new ChatComponentText(LanguageRegistry.instance().getStringLocalization("desc.guardianTier1.Warning").replace("%1", LanguageRegistry.instance().getStringLocalization("item.guardianTier1.name"))));
    	}
    	return itemStack;
    }
    /**
     * Callback for item usage. If the item does something special on right
     * clicking, he will have one of those. Return True if something happen and
     * false if it don't. This is for ITEMS, not BLOCKS
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
        return false;
    }
     */
}
