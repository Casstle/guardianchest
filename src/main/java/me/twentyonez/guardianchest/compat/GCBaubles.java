package me.twentyonez.guardianchest.compat;

import java.util.List;

import me.twentyonez.guardianchest.common.EnumInventoryType;
import me.twentyonez.guardianchest.common.ItemStackTypeSlot;
import me.twentyonez.guardianchest.util.ConfigHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import baubles.api.BaublesApi;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */
public class GCBaubles {

    protected static boolean isInstalled = false;

    private GCBaubles() {

    }
    
    public static void addItems(List<ItemStackTypeSlot> itemStackTypeSlots, EntityPlayer player, Integer saveItems, Integer levelSoulBoundInventory) {
        if (isInstalled()) {
            IInventory inventory = BaublesApi.getBaubles(player);
            if (inventory != null) {
                for (int indexSlot = 0; indexSlot < inventory.getSizeInventory(); indexSlot++) {
                    final ItemStack itemStackInSlot = inventory.getStackInSlot(indexSlot);
                    if (itemStackInSlot != null) {
                        final ItemStackTypeSlot itemStackTypeSlot = new ItemStackTypeSlot(itemStackInSlot, EnumInventoryType.BAUBLES, indexSlot);
                        itemStackTypeSlots.add(itemStackTypeSlot);
                        if ((saveItems != 0) || GCsoulBinding.keepItem(itemStackTypeSlot, player, levelSoulBoundInventory)) {
                        	inventory.setInventorySlotContents(indexSlot, null);
                        } else if (ConfigHelper.makeAllItemsDrop) {
                        	player.inventory.addItemStackToInventory(itemStackInSlot);
                        	inventory.setInventorySlotContents(indexSlot, null);
                        }
                    }
                }
            }
        }
    }

    public static boolean isInstalled() {
        return isInstalled;
    }
}