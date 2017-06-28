package me.twentyonez.guardianchest.compat;

import me.twentyonez.guardianchest.common.EnumInventoryType;
import me.twentyonez.guardianchest.common.ItemStackTypeSlot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.List;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GCCampingMod {

    protected static boolean isInstalled = false;

    private GCCampingMod() {
    }

    public static void addItems(List<ItemStackTypeSlot> itemStackTypeSlots, EntityPlayer player, Integer saveItems, Integer levelSoulBoundInventory) {
        if (isInstalled()) {
            final NBTTagCompound tagCompoundCampInv = player.getEntityData().getCompoundTag("campInv");
            final NBTTagList tagListItems = tagCompoundCampInv.getTagList("Items", 10);
            for (int indexSlot = 0; indexSlot < tagListItems.tagCount(); ++indexSlot) {
                NBTTagCompound tagCompoundSlot = tagListItems.getCompoundTagAt(indexSlot);
                // @TODO: do we need to use the following as an indexSlot value?
                // tagCompoundSlot.getInteger("Slot");
                final ItemStackTypeSlot itemStackTypeSlot = new ItemStackTypeSlot(ItemStack.loadItemStackFromNBT(tagCompoundSlot).copy(), EnumInventoryType.CAMPINGMOD, indexSlot);
                itemStackTypeSlots.add(itemStackTypeSlot);
            }
            player.getEntityData().setTag("campInv", new NBTTagCompound());
        }
    }


    public static boolean isInstalled() {
        return isInstalled;
    }
}