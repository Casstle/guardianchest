package me.twentyonez.guardianchest.compat;

import java.util.List;

import me.twentyonez.guardianchest.common.EnumInventoryType;
import me.twentyonez.guardianchest.common.ItemStackTypeSlot;
import me.twentyonez.guardianchest.util.ConfigHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */
public class GCBattlegear {

    protected static boolean isInstalled = false;

    private static final short FIRST_SLOT = 150;
    private static final short LAST_SLOT = 155;
    
    private GCBattlegear() {

    }
	
	public static void addItems(List<ItemStackTypeSlot> itemStackTypeSlots, EntityPlayer player, Integer saveItems, Integer levelSoulBoundInventory) {
        if (isInstalled()) {
			// Get main inventory
			for(int indexSlot = FIRST_SLOT; indexSlot <= LAST_SLOT; indexSlot++){
				final ItemStack itemStackInSlot = player.inventory.getStackInSlot(indexSlot);
				if (itemStackInSlot != null) {
					final ItemStackTypeSlot itemStackTypeSlot = new ItemStackTypeSlot(GCminecraft.applyItemDamage(itemStackInSlot), EnumInventoryType.BATTLEGEAR, indexSlot);
					itemStackTypeSlots.add(itemStackTypeSlot);
	                if ((saveItems != 0) || GCsoulBinding.keepItem(itemStackTypeSlot, player, levelSoulBoundInventory)) {
                    	player.inventory.setInventorySlotContents(indexSlot, null);
	                } else if (ConfigHelper.makeAllItemsDrop) {
                    	player.inventory.addItemStackToInventory(itemStackInSlot);
                    	player.inventory.setInventorySlotContents(indexSlot, null);
                    }
				}
			}
        }
    }

    public static boolean isInstalled() {
        return isInstalled;
    }
}

