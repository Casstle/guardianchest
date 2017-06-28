package me.twentyonez.guardianchest.compat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.twentyonez.guardianchest.GuardianChest;
import me.twentyonez.guardianchest.common.EnumInventoryType;
import me.twentyonez.guardianchest.common.ItemStackTypeSlot;
import me.twentyonez.guardianchest.util.ConfigHelper;
import micdoodle8.mods.galacticraft.api.inventory.AccessInventoryGC;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
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

public class GCsoulBinding {

    public static List<String> soulBindingEnchantmentsList;
    private static Map<UUID, Integer> playerToSoulboundCount = new HashMap<>(50);

    private GCsoulBinding() {
    }

    public static void startCounting(final EntityPlayer entityPlayer) {
    	playerToSoulboundCount.put(entityPlayer.getPersistentID(), ConfigHelper.maxSoulBoundItems);
    }
	
	public static boolean canKeepNextSoulBoundItem(final EntityPlayer entityPlayer) {
		final int count = playerToSoulboundCount.get(entityPlayer.getPersistentID());
		if (count <= 0) {
			return false;
		}
		playerToSoulboundCount.put(entityPlayer.getPersistentID(), count - 1);
		return true;
	}
	
	public static void getSoulBoundItemsBack(final List<ItemStackTypeSlot> itemStackTypeSlots, final EntityPlayer player, final Integer levelSoulBoundInventory) {
		if (GuardianChest.DEBUG) {
			GuardianChest.logger.info(String.format("getSoulBoundItemsBack itemStackTypeSlots %s levelSoulBoundInventory %d",
			                                        itemStackTypeSlots, levelSoulBoundInventory));
		}
		boolean isFirstException = true;
    	final Iterator<ItemStackTypeSlot> iteratorItemStackTypeSlots = itemStackTypeSlots.iterator();
        while (iteratorItemStackTypeSlots.hasNext()) {
	        ItemStackTypeSlot itemStackTypeSlot = iteratorItemStackTypeSlots.next();
            if (itemStackTypeSlot.itemStack != null && keepItem(itemStackTypeSlot, player, levelSoulBoundInventory))  {
            	//player.inventory.addItemStackToInventory(stack.copy());
        		if (itemStackTypeSlot.type == EnumInventoryType.VANILLA_MAIN) {
        			if (player.inventory.getStackInSlot(itemStackTypeSlot.indexSlot) != null) {
                        while (!player.inventory.addItemStackToInventory(itemStackTypeSlot.itemStack)) {
                            player.dropOneItem(true);
                        }
        			} else {
        				player.inventory.setInventorySlotContents(itemStackTypeSlot.indexSlot, itemStackTypeSlot.itemStack);
        			}
                    iteratorItemStackTypeSlots.remove();
        			
            	} else if (itemStackTypeSlot.type == EnumInventoryType.VANILLA_ARMOR) {
                    player.inventory.setInventorySlotContents(itemStackTypeSlot.indexSlot + 36, itemStackTypeSlot.itemStack);
                    iteratorItemStackTypeSlots.remove();
                    
            	} else if (itemStackTypeSlot.type == EnumInventoryType.BAUBLES && GCBaubles.isInstalled) {
                    IInventory inventory = BaublesApi.getBaubles(player);
            		inventory.setInventorySlotContents(itemStackTypeSlot.indexSlot, itemStackTypeSlot.itemStack);
                    iteratorItemStackTypeSlots.remove();
                    
            	} else if (itemStackTypeSlot.type == EnumInventoryType.RPG_INVENTORY && GCRpgInventory.isInstalled) {
                    try {
                        Class<?> clazz = Class.forName("rpgInventory.gui.rpginv.PlayerRpgInventory");
                        Method m = clazz.getDeclaredMethod("get", EntityPlayer.class);
                        Object result = m.invoke(null, player);

                        IInventory inventory = (IInventory) result;
	            		inventory.setInventorySlotContents(itemStackTypeSlot.indexSlot, itemStackTypeSlot.itemStack);
	                    iteratorItemStackTypeSlots.remove();
                    } catch (Exception exception) {
                    	// Error trying to give back RPG Inventory... erm... inventory.
	                    if (isFirstException) {
		                    exception.printStackTrace();
		                    isFirstException = false;
	                    }
                    }
                    
            	} else if (itemStackTypeSlot.type == EnumInventoryType.GALACTICRAFT && GCGalacticraft.isInstalled) {
                    IInventory inventory = AccessInventoryGC.getGCInventoryForPlayer((EntityPlayerMP) player);
            		inventory.setInventorySlotContents(itemStackTypeSlot.indexSlot, itemStackTypeSlot.itemStack);
                    iteratorItemStackTypeSlots.remove();
                    
            	} else if (itemStackTypeSlot.type == EnumInventoryType.BATTLEGEAR && GCBattlegear.isInstalled)  {
                    player.inventory.setInventorySlotContents(itemStackTypeSlot.indexSlot, itemStackTypeSlot.itemStack);
                    iteratorItemStackTypeSlots.remove();
                    
            	} else if (itemStackTypeSlot.type == EnumInventoryType.CAMPINGMOD && GCCampingMod.isInstalled) {
			        while (!player.inventory.addItemStackToInventory(itemStackTypeSlot.itemStack)) {
				        player.dropOneItem(true);
			        }
			        iteratorItemStackTypeSlots.remove();
					
		        } else if ((itemStackTypeSlot.type == EnumInventoryType.TRAVELLERS_GEAR) && (GCTravellersGear.isInstalled)) {
			        while (!player.inventory.addItemStackToInventory(itemStackTypeSlot.itemStack)) {
				        player.dropOneItem(true);
			        }
			        iteratorItemStackTypeSlots.remove();
			        
			    } else if (itemStackTypeSlot.type == EnumInventoryType.TC_ACCESSORY && GCTinkersConstruct.isInstalled)  {
                    while (!player.inventory.addItemStackToInventory(itemStackTypeSlot.itemStack)) {
                    	player.dropOneItem(true);
                    }
                    iteratorItemStackTypeSlots.remove();
                    
            	} else if (itemStackTypeSlot.type == EnumInventoryType.TC_KNAPSACK && GCTinkersConstruct.isInstalled)  {
                    while (!player.inventory.addItemStackToInventory(itemStackTypeSlot.itemStack)) {
                        player.dropOneItem(true);
                    }
                    iteratorItemStackTypeSlots.remove();
                    
            	} else {
        			if (player.inventory.getStackInSlot(itemStackTypeSlot.indexSlot) != null) {
                        while (!player.inventory.addItemStackToInventory(itemStackTypeSlot.itemStack)) {
                            player.dropOneItem(true);
                        }
        			} else {
        				player.inventory.setInventorySlotContents(itemStackTypeSlot.indexSlot, itemStackTypeSlot.itemStack);
        			}
                    iteratorItemStackTypeSlots.remove();
            	}
            }
        }
    }

    public static boolean hasSoulBound(final ItemStack itemStack) {
        Map enchantments = EnchantmentHelper.getEnchantments(itemStack);
        for (Object id : enchantments.keySet()) {
            final Enchantment enchantment = Enchantment.enchantmentsList[((Integer) id).shortValue()];
            if (enchantment != null) {
            	if ( ConfigHelper.anyEnchantSoulBinds
	              || soulBindingEnchantmentsList().contains(enchantment.getClass().getName()) ) {
            		return true;
            	}
            }
        }
        return false;
    }
    
    public static boolean keepItem(final ItemStackTypeSlot itemStackTypeSlot, final EntityPlayer player, final Integer levelSoulBoundInventory) {
    	if (hasSoulBound(itemStackTypeSlot.itemStack)) {
    		if (canKeepNextSoulBoundItem(player)) {
			    if (GuardianChest.DEBUG) {
				    GuardianChest.logger.info(String.format("Keeping soulbounded %s", itemStackTypeSlot.itemStack));
			    }
    			return true;
		    }
    	}
    	// Checks for charms of keeping lvl 3
    	if (levelSoulBoundInventory == 3) {
    		return true;
    	}
    	// Checks for charms of keeping lvl 2 AND if (item was in hotbar OR (is not main inventory AND is not battlegear AND is not miscellaneous) ).
    	if ( (levelSoulBoundInventory == 2)
	      && ( ( (itemStackTypeSlot.type == EnumInventoryType.VANILLA_MAIN)
	          && (itemStackTypeSlot.indexSlot < InventoryPlayer.getHotbarSize()) ) 
	        || ( (itemStackTypeSlot.type != EnumInventoryType.VANILLA_MAIN) 
	          && (itemStackTypeSlot.type != EnumInventoryType.BATTLEGEAR)
	          && (itemStackTypeSlot.type != EnumInventoryType.MISC) ) ) ) {
    		return true;
    	}
    	// Checks for charms of keeping lvl 1 AND if (item was the currently held OR (is not main inventory AND is not battlegear AND is not miscellaneous) ) .
    	if ( (levelSoulBoundInventory == 1)
	      && ( ( (itemStackTypeSlot.type == EnumInventoryType.VANILLA_MAIN)
	          && (itemStackTypeSlot.indexSlot == player.inventory.currentItem) )
	        || ( (itemStackTypeSlot.type != EnumInventoryType.VANILLA_MAIN)
	          && (itemStackTypeSlot.type != EnumInventoryType.BATTLEGEAR)
	          && (itemStackTypeSlot.type != EnumInventoryType.MISC) ) ) )  {
    		return true;
    	}
    	return false;
    }
    
    public static List soulBindingEnchantmentsList() {
    	if (soulBindingEnchantmentsList == null) {
    		soulBindingEnchantmentsList = new ArrayList<>();
		    if (GCAM2.isInstalled()) {
			    soulBindingEnchantmentsList.add("am2.enchantments.EnchantmentSoulbound");
		    }
		    if (GCEnderIO.isInstalled()) {
			    soulBindingEnchantmentsList.add("enchantment.enderio.soulBound");
		    }
	    }
    	return soulBindingEnchantmentsList;
    }
    
}