package me.twentyonez.guardianchest.common;

import me.twentyonez.guardianchest.GuardianChest;

import net.minecraft.item.ItemStack;

/**
 * GuardianChest mod
 *
 * @author LemADEC
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */
public class ItemStackTypeSlot {
	
	public ItemStack itemStack;
	public EnumInventoryType type;
	public int indexSlot;
	
	public ItemStackTypeSlot(final ItemStack itemStack, final EnumInventoryType type, final int indexSlot) {
		this.itemStack = itemStack.copy();
		this.type = type;
		this.indexSlot = indexSlot;
		if (GuardianChest.DEBUG) {
			GuardianChest.logger.info(String.format("Created %s[%d] = %s",
			                                        type, indexSlot, itemStack));
		}
	}
	
	@Override
	public String toString() {
		return String.format("\nItemStackTypeSlot %s[%d] = %s",
		                     type, indexSlot, itemStack);
	}
}