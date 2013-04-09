package mrhid6.zonus.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerXorbo extends Container {

	@Override
	public abstract boolean canInteractWith( EntityPlayer player );

	@Override
	public ItemStack slotClick( int par1, int par2, int par3, EntityPlayer player ) {

		if ((par1 >= 0) && (par1 < inventorySlots.size())) {
			Slot slot = getSlot(par1);
			if ((slot instanceof DummySlot)) {
				((DummySlot) slot).slotClick(player.inventory.getItemStack(), par2, par3 == 1);
				return null;
			}
		}

		return super.slotClick(par1, par2, par3, player);
	}

	public boolean transferToSlots( ItemStack stack, int startIndex, int endIndex, boolean lookBackwards ) {
		return mergeItemStack(stack, startIndex, endIndex, lookBackwards);
	}
}
