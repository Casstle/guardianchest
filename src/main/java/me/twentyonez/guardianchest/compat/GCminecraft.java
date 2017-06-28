package me.twentyonez.guardianchest.compat;

import me.twentyonez.guardianchest.GuardianChest;
import me.twentyonez.guardianchest.common.EnumInventoryType;
import me.twentyonez.guardianchest.common.ItemStackTypeSlot;
import me.twentyonez.guardianchest.util.ConfigHelper;

import java.util.List;

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

public class GCminecraft {

    private GCminecraft() {

    }
	
	public static void addItems(List<ItemStackTypeSlot> itemStackTypeSlots, EntityPlayer player, Integer saveItems, Integer levelSoulBoundInventory) {
	
	    // Get armor inventory
	    for(int indexSlot = 0; indexSlot < player.inventory.armorInventory.length; indexSlot++){
		    final ItemStack itemStackInSlot = player.inventory.armorItemInSlot(indexSlot);
		    if (itemStackInSlot != null) {
		    	final ItemStackTypeSlot itemStackTypeSlot = new ItemStackTypeSlot(applyItemDamage(itemStackInSlot), EnumInventoryType.VANILLA_ARMOR, indexSlot);
			    itemStackTypeSlots.add(itemStackTypeSlot);
			    if ((saveItems != 0) || GCsoulBinding.keepItem(itemStackTypeSlot, player, levelSoulBoundInventory)) {
				    player.inventory.armorInventory[indexSlot] = null;
			    }
		    }
	    }
	    
		// Get main inventory
		for(int indexSlot = 0; indexSlot < player.inventory.mainInventory.length; indexSlot++){
			final ItemStack itemStackInSlot = player.inventory.getStackInSlot(indexSlot);
			if (itemStackInSlot != null) {
				final ItemStackTypeSlot itemStackTypeSlot = new ItemStackTypeSlot(
					indexSlot == player.inventory.currentItem ? applyItemDamage(itemStackInSlot) : itemStackInSlot,
					EnumInventoryType.VANILLA_MAIN, indexSlot);
				itemStackTypeSlots.add(itemStackTypeSlot);
				if ((saveItems != 0) || GCsoulBinding.keepItem(itemStackTypeSlot, player, levelSoulBoundInventory)) {
                	player.inventory.mainInventory[indexSlot] = null;
                }
			}
		}
		
    }
	
	public static ItemStack applyItemDamage(final ItemStack itemStack) {
		if (ConfigHelper.applyDamageOnEquip == 0.0D) {
			return itemStack;
		}
		if ( (itemStack != null)
		  && itemStack.isItemStackDamageable()
		  && itemStack.getItem().isDamageable()
		  && !itemStack.isStackable()
		  && !itemStack.getItem().getHasSubtypes()
		  && itemStack.getItem().getMaxDamage() > 0 ) {
			final int newDamageValue;
			if (ConfigHelper.damageOnEquipPercentage) {
				newDamageValue = (int)(itemStack.getMaxDamage() * (ConfigHelper.applyDamageOnEquip / 100.0D) + itemStack.getItemDamage());
			} else {
				newDamageValue = (int)(itemStack.getItemDamage() + ConfigHelper.applyDamageOnEquip);
			}
			itemStack.setItemDamage(Math.min(newDamageValue, itemStack.getMaxDamage() - 1));
		}
		return itemStack;
	}
}