package mrhid6.zonus.tileEntity;

import java.util.Random;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class TEBlock extends TEPoweredBase implements IInventory {

	public ItemStack[] inventory;
	public String invName;
	public boolean isActive;
	public boolean loaded = false;

	@Override
	public void closeChest() {
	}
	
	public void dropContent(int newSize)
	{

		Random random = new Random();
		for (int l = newSize; l < getSizeInventory(); l++)
		{
			ItemStack itemstack = getStackInSlot(l);
			if (itemstack == null)
			{
				continue;
			}
			float f = random.nextFloat() * 0.8F + 0.1F;
			float f1 = random.nextFloat() * 0.8F + 0.1F;
			float f2 = random.nextFloat() * 0.8F + 0.1F;
			while (itemstack.stackSize > 0)
			{
				int i1 = random.nextInt(21) + 10;
				if (i1 > itemstack.stackSize)
				{
					i1 = itemstack.stackSize;
				}
				itemstack.stackSize -= i1;
				EntityItem entityitem = new EntityItem(worldObj, (float) xCoord + f, (float) yCoord + (newSize > 0 ? 1 : 0) + f1, (float) zCoord + f2,
						new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
				float f3 = 0.05F;
				entityitem.motionX = (float) random.nextGaussian() * f3;
				entityitem.motionY = (float) random.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float) random.nextGaussian() * f3;
				if (itemstack.hasTagCompound())
				{
					entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
				}
				worldObj.spawnEntityInWorld(entityitem);
			}
		}
	}

	@Override
	public ItemStack decrStackSize( int i, int amt ) {
		if (inventory[i] != null) {
			if (inventory[i].stackSize <= amt) {
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				return itemstack;
			}

			ItemStack itemstack1 = inventory[i].splitStack(amt);

			if (inventory[i].stackSize == 0) {
				inventory[i] = null;
			}

			return itemstack1;
		}

		return null;
	}

	public boolean getActive() {
		return isActive;
	}

	@Override
	public String getInvName() {
		return invName;
	}

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public ItemStack getStackInSlot( int var1 ) {
		return (var1 >= inventory.length) ? null : inventory[var1];
	}

	@Override
	public ItemStack getStackInSlotOnClosing( int slot ) {
		 if (this.inventory[slot] != null)
	        {
	            ItemStack itemstack = this.inventory[slot];
	            this.inventory[slot] = null;
	            return itemstack;
	        }
	        else
	        {
	            return null;
	        }
	}

	@Override
	public boolean isUseableByPlayer( EntityPlayer var1 ) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		return var1.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void readFromNBT( NBTTagCompound data ) {
		super.readFromNBT(data);

		NBTTagList tagList = data.getTagList("inventory");

		inventory = new ItemStack[getSizeInventory()];

		for (int i = 0; i < tagList.tagCount(); i++) {

			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			int slot = tag.getInteger("slot");

			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}

	}

	public void setActive( boolean bol ) {
		isActive = bol;
	}

	@Override
	public void setInventorySlotContents( int i, ItemStack itemstack ) {
		inventory[i] = itemstack;

		if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit())) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public void writeToNBT( NBTTagCompound data ) {
		super.writeToNBT(data);

		NBTTagList itemList = new NBTTagList();

		for (int i = 0; i < inventory.length; i++) {
			ItemStack stack = inventory[i];

			if (stack != null) {

				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("slot", i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}

		data.setTag("inventory", itemList);
	}

}
