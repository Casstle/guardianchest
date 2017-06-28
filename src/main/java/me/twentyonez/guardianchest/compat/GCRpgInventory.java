package me.twentyonez.guardianchest.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import me.twentyonez.guardianchest.common.EnumInventoryType;
import me.twentyonez.guardianchest.common.ItemStackTypeSlot;
import me.twentyonez.guardianchest.util.ConfigHelper;




import java.lang.reflect.Method;
import java.util.List;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */

public class GCRpgInventory {

    protected static boolean isInstalled = false;

    private GCRpgInventory() {
    }
    
    public static void addItems(List<ItemStackTypeSlot> itemStackTypeSlots, EntityPlayer player, Integer saveItems, Integer levelSoulBoundInventory) {
        if (isInstalled()) {
            try {
                Class<?> clazz = Class.forName("rpgInventory.gui.rpginv.PlayerRpgInventory");
                Method m = clazz.getDeclaredMethod("get", EntityPlayer.class);
                Object result = m.invoke(null, player);

                IInventory inventory = (IInventory) result;
                if (inventory != null) {
                    for (int indexSlot = 0; indexSlot < inventory.getSizeInventory(); indexSlot++) {
                        final ItemStack itemStackInSlot = inventory.getStackInSlot(indexSlot);
                        if (itemStackInSlot != null) {
                            final ItemStackTypeSlot itemStackTypeSlot = new ItemStackTypeSlot(itemStackInSlot, EnumInventoryType.RPG_INVENTORY, indexSlot);
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
            } catch (Exception e) {
            	//Error trying to get RPG Inventory... erm... inventory.
            }
        }
    }

    public static boolean isInstalled() {
        return isInstalled;
    }
}