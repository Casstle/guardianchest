package me.twentyonez.guardianchest.compat;

import java.util.List;

import me.twentyonez.guardianchest.common.EnumInventoryType;
import me.twentyonez.guardianchest.common.ItemStackTypeSlot;
import me.twentyonez.guardianchest.util.ConfigHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import travellersgear.api.TGSaveData;
import travellersgear.api.TravellersGearAPI;

public class GCTravellersGear {
	
	protected static boolean isInstalled = false;
	
	public static void addItems(List<ItemStackTypeSlot> itemStackTypeSlots, EntityPlayer player, Integer saveItems, Integer levelSoulBoundInventory) {
		if (isInstalled()) {
			ItemStack[] inventory = TravellersGearAPI.getExtendedInventory(player);
			for (int indexSlot = 0; indexSlot < inventory.length; indexSlot++) {
				if (inventory[indexSlot] != null) {
					final ItemStack itemStackInSlot = inventory[indexSlot];
					final ItemStackTypeSlot itemStackTypeSlot = new ItemStackTypeSlot(itemStackInSlot, EnumInventoryType.TRAVELLERS_GEAR, indexSlot);
					itemStackTypeSlots.add(itemStackTypeSlot);
					if ((saveItems != 0) || GCsoulBinding.keepItem(itemStackTypeSlot, player, levelSoulBoundInventory)) {
						TGSaveData.setPlayerData(player, null);
						TGSaveData.setDirty();
					} else if (ConfigHelper.makeAllItemsDrop) {
						player.inventory.addItemStackToInventory(itemStackInSlot);
					}
				}
			}
			TGSaveData.setPlayerData(player, null);
			TGSaveData.setDirty();
		}
	}
	
	public static boolean isInstalled() {
		return isInstalled;
	}
}