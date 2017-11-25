package me.twentyonez.guardianchest.tile_entity;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import me.twentyonez.guardianchest.GuardianChest;
import me.twentyonez.guardianchest.block.GCChest;
import me.twentyonez.guardianchest.util.ConfigHelper;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */

public class TileEntityGCChest extends TileEntityChest {
	
    private static final String OWNER_NONE = "[none]";
	private String owner = OWNER_NONE;
	private long creationDate = 0;
	public boolean isSecure = true;
	private ItemStack[] chestContents = new ItemStack[255];
    /** Determines if the check for adjacent chests has taken place. */
    public boolean adjacentChestChecked;
    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityGCChest adjacentChestZNeg;
    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityGCChest adjacentChestXPos;
    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityGCChest adjacentChestXNeg;
    /** Contains the chest tile located adjacent to this one (if any) */
    public TileEntityGCChest adjacentChestZPos;
    /** The current angle of the lid (between 0 and 1) */
    public float lidAngle;
    /** The angle of the lid last tick */
    public float prevLidAngle;
    /** The number of players currently using this chest */
    public int numPlayersUsing;
    /** Server sync counter (once per 20 ticks) */
    // private int ticksSinceSync;
    private int cachedChestType;
    private String customName;
    
    private boolean isDirty = false;
    
    public boolean checkSecurity() {
    	if (creationDate != 0) {
	    	long secureTimeLeft = ConfigHelper.timeBeforeUnsecure - ((this.worldObj.getTotalWorldTime() - creationDate)/20);
	    	if  (secureTimeLeft < 0) {
	    		isSecure = false;
	    		return false;
	    	} else {
	    		return true;
	    	}
    	} else {
    		return true;
    	}
    }
    
    public void processActivate(EntityPlayer player, World world, int x, int y, int z) {
    	if (owner.equals(OWNER_NONE)) {
            GuardianChest.logger.info(String.format("Ignoring activation for chest with no owner in DIM%d at (%d %d %d)",
                                      world.provider.dimensionId, x, y, z));
            return;
        }
        final long secureTimeLeft = ConfigHelper.timeBeforeUnsecure - ((this.worldObj.getTotalWorldTime() - creationDate)/20);
    	if(!world.isRemote) {
	        if ( (!player.isSneaking())
              && (!player.capabilities.isCreativeMode)
	          && (owner.equals(player.getCommandSenderName()) || owner.equals("any") || (secureTimeLeft < 0)) ) {
	        	world.setBlockToAir(x, y, z);
	        } else {
	        	if (secureTimeLeft >= 60) {
		        	if (secureTimeLeft/60 == 1) {
		        		player.addChatComponentMessage(new ChatComponentText(LanguageRegistry.instance().getStringLocalization("desc.guardianChestRightClick.Minute").replace("%1", LanguageRegistry.instance().getStringLocalization("tile.guardianChest.name")).replace("%2", owner)));
		        	} else {
		        		player.addChatComponentMessage(new ChatComponentText(LanguageRegistry.instance().getStringLocalization("desc.guardianChestRightClick.Minutes").replace("%1", LanguageRegistry.instance().getStringLocalization("tile.guardianChest.name")).replace("%2", owner).replace("%3", String.valueOf(secureTimeLeft/60))));
		        	}
	        	} else {
	        		player.addChatComponentMessage(new ChatComponentText(LanguageRegistry.instance().getStringLocalization("desc.guardianChestRightClick.Seconds").replace("%1", LanguageRegistry.instance().getStringLocalization("tile.guardianChest.name")).replace("%2", owner).replace("%3", String.valueOf(secureTimeLeft))));
	        	}
	    	}
		}
    }
    
    public void registerOwner(EntityPlayer player, World world, int x, int y, int z) {
    	owner = player.getCommandSenderName();
    	creationDate = this.worldObj.getTotalWorldTime();
        markDirty();
    }
    
    @Override
    public void markDirty() {
        if ( hasWorldObj()
          && isSafeThread() ) {
            super.markDirty();
            isDirty = false;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        } else {
            isDirty = true;
        }
    }
    
    public static boolean isSafeThread() {
        final String name = Thread.currentThread().getName();
        return name.equals("Server thread") || name.equals("Client thread");
    }
    
    public TileEntityGCChest()
    {
        this.cachedChestType = -1;
    }

    @SideOnly(Side.CLIENT)
    public TileEntityGCChest(int p_i2350_1_)
    {
        this.cachedChestType = p_i2350_1_;
    }

    @Override
    public int getSizeInventory()
    {
        return 254;
    }
    
    @Override
    public ItemStack getStackInSlot(int p_70301_1_)
    {
        return this.chestContents[p_70301_1_];
    }
    
    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
    {
        if (this.chestContents[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.chestContents[p_70298_1_].stackSize <= p_70298_2_)
            {
                itemstack = this.chestContents[p_70298_1_];
                this.chestContents[p_70298_1_] = null;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.chestContents[p_70298_1_].splitStack(p_70298_2_);

                if (this.chestContents[p_70298_1_].stackSize == 0)
                {
                    this.chestContents[p_70298_1_] = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    {
        if (this.chestContents[p_70304_1_] != null)
        {
            ItemStack itemstack = this.chestContents[p_70304_1_];
            this.chestContents[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }
    
    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.chestContents[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
        {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }
    
    @Override
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.customName : LanguageRegistry.instance().getStringLocalization("tile.guardianChest.name");
    }
    
    @Override
    public boolean hasCustomInventoryName()
    {
        return this.customName != null && this.customName.length() > 0;
    }
    
    @Override
    public void func_145976_a(String p_145976_1_)
    {
        this.customName = p_145976_1_;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);
        NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
        this.chestContents = new ItemStack[this.getSizeInventory()];

        if (p_145839_1_.hasKey("CustomName", 8))
        {
            this.customName = p_145839_1_.getString("CustomName");
        }

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.chestContents.length)
            {
                this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        this.owner = p_145839_1_.getString("owner");
        this.creationDate = p_145839_1_.getLong("creationDate");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);
        NBTTagList nbttaglist = new NBTTagList();
        

        for (int i = 0; i < this.chestContents.length; ++i)
        {
            if (this.chestContents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.chestContents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        p_145841_1_.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            p_145841_1_.setString("CustomName", this.customName);
        }
        p_145841_1_.setString("owner", owner);
        p_145841_1_.setLong("creationDate", creationDate);
    }
    
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
    {
        return worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this
            && p_70300_1_.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }
    
    @Override
    public void updateContainingBlockInfo()
    {
        super.updateContainingBlockInfo();
        this.adjacentChestChecked = false;
    }
    
    
    @Override
    public void checkForAdjacentChests()
    {
        this.adjacentChestChecked = false;
        this.adjacentChestZNeg = null;
        this.adjacentChestXPos = null;
        this.adjacentChestXNeg = null;
        this.adjacentChestZPos = null;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        
        if (isDirty) {
            markDirty();
        }
    }
    
    @Override
    public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_)
    {
        if (p_145842_1_ == 1)
        {
            this.numPlayersUsing = p_145842_2_;
            return true;
        }
        else
        {
            return super.receiveClientEvent(p_145842_1_, p_145842_2_);
        }
    }
    
    @Override
    public void openInventory()
    {
        if (this.numPlayersUsing < 0)
        {
            this.numPlayersUsing = 0;
        }

        ++this.numPlayersUsing;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
    }
    
    @Override
    public void closeInventory()
    {
        if (this.getBlockType() instanceof GCChest)
        {
            --this.numPlayersUsing;
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
        }
    }
    
    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }
    
    @Override
    public void invalidate()
    {
        super.invalidate();
        this.updateContainingBlockInfo();
        this.checkForAdjacentChests();
    }
    
    @Override
    public int func_145980_j()
    {
        if (this.cachedChestType == -1)
        {
            if (this.worldObj == null || !(this.getBlockType() instanceof GCChest))
            {
                return 0;
            }

            this.cachedChestType = ((GCChest)this.getBlockType()).field_149956_a;
        }

        return this.cachedChestType;
    }
}