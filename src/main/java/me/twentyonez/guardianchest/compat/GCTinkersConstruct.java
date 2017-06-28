package me.twentyonez.guardianchest.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.List;

import me.twentyonez.guardianchest.common.EnumInventoryType;
import me.twentyonez.guardianchest.common.ItemStackTypeSlot;
import tconstruct.api.IPlayerExtendedInventoryWrapper;
import tconstruct.api.TConstructAPI;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */

public class GCTinkersConstruct {

    protected static boolean isInstalled = false;
    
    private static final int ACCESSORIES_SLOTS_COUNT = 4;

    private GCTinkersConstruct() {
    }

    public static void addItems(List<ItemStackTypeSlot> itemStackTypeSlots, EntityPlayer player, Integer saveItems, Integer levelSoulBoundInventory) {
        if (isInstalled()) {
            final IPlayerExtendedInventoryWrapper inventoryWrapper = TConstructAPI.getInventoryWrapper(player);
            if (inventoryWrapper != null) {
                final IInventory inventoryKnapsack = inventoryWrapper.getKnapsackInventory(player);
                if (inventoryKnapsack != null) {
                    for (int indexSlot = 0; indexSlot < inventoryKnapsack.getSizeInventory(); indexSlot++) {
                        final ItemStack itemStackInSlot = inventoryKnapsack.getStackInSlot(indexSlot);
                        if (itemStackInSlot != null) {
                            final ItemStackTypeSlot itemStackTypeSlot = new ItemStackTypeSlot(itemStackInSlot, EnumInventoryType.TC_KNAPSACK, indexSlot);
                            itemStackTypeSlots.add(itemStackTypeSlot);
                            if ((saveItems != 0) || GCsoulBinding.keepItem(itemStackTypeSlot, player, levelSoulBoundInventory)) {
                            	inventoryKnapsack.setInventorySlotContents(indexSlot, null);
                            }
                        }
                    }
                }

                final IInventory inventoryAccessory = inventoryWrapper.getAccessoryInventory(player);
                if (inventoryAccessory != null) {
                    //Heart Canisters should not go in the grave as they are not supposed to be dropped on death, so only first 4 slots required
                    for (int indexSlot = 0; indexSlot < ACCESSORIES_SLOTS_COUNT; indexSlot++) {
                        final ItemStack itemStackInSlot = inventoryAccessory.getStackInSlot(indexSlot);
                        if (itemStackInSlot != null) {
                            final ItemStackTypeSlot itemStackTypeSlot = new ItemStackTypeSlot(itemStackInSlot, EnumInventoryType.TC_ACCESSORY, indexSlot);
                            itemStackTypeSlots.add(itemStackTypeSlot);
                            if ((saveItems != 0) || GCsoulBinding.keepItem(itemStackTypeSlot, player, levelSoulBoundInventory)) {
                            	inventoryAccessory.setInventorySlotContents(indexSlot, null);
                            }
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