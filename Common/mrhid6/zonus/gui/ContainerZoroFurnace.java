package mrhid6.zonus.gui;

import mrhid6.zonus.tileEntity.TEZoroFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerZoroFurnace extends ContainerXorbo {

	public float energy = -1;
	protected TEZoroFurnace tileEntity;

	public ContainerZoroFurnace( EntityPlayer inventory, TEZoroFurnace te ) {
		tileEntity = te;
		addSlotToContainer(new Slot(tileEntity, 0, 56, 24));
		addSlotToContainer(new SlotFurnace(inventory, tileEntity, 1, 116, 33));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory.inventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith( EntityPlayer player ) {
		return tileEntity.isUseableByPlayer(player);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++) {
			ICrafting icrafting = (ICrafting) crafters.get(i);

			tileEntity.sendGuiNetworkData(this, icrafting);
		}
	}

	@Override
	public ItemStack transferStackInSlot( EntityPlayer player, int i ) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);

		int invTile = tileEntity.inventory.length;
		int invPlayer = invTile + 27;
		int invFull = invTile + 36;

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack stackInSlot = slot.getStack();
			itemstack = stackInSlot.copy();

			if (i == 1) {
				if (!mergeItemStack(stackInSlot, invTile, invFull, true)) {
					return null;
				}
			} else if (i != 0) {
				if (tileEntity.getResultFor(stackInSlot) != null) {
					if (!mergeItemStack(stackInSlot, 0, 1, false)) {
						return null;
					}
				} else if ((i >= invTile) && (i < invPlayer)) {
					if (!mergeItemStack(stackInSlot, invPlayer, invFull, false)) {
						return null;
					}
				} else if ((i >= invPlayer) && (i < invFull) && (!mergeItemStack(stackInSlot, invTile, invPlayer, false))) {
					return null;
				}
			} else if (!mergeItemStack(stackInSlot, invTile, invFull, false)) {
				return null;
			}

			if (stackInSlot.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (stackInSlot.stackSize == itemstack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, stackInSlot);
		}
		return itemstack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar( int i, int j ) {
		super.updateProgressBar(i, j);
		tileEntity.receiveGuiNetworkData(i, j);
	}

}