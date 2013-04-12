package mrhid6.zonus.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrhid6.zonus.tileEntity.machine.TEZoroChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerZoroChest extends ContainerXorbo {

	protected TEZoroChest tileEntity;

	public ContainerZoroChest( EntityPlayer inventory, TEZoroChest tileEntity2 ) {
		tileEntity = tileEntity2;
		tileEntity.openChest();

		for (int chestRow = 0; chestRow < tileEntity.getRowCount(); chestRow++) {
			for (int chestCol = 0; chestCol < tileEntity.getRowLength(); chestCol++) {
				addSlotToContainer(new Slot(tileEntity, chestCol + chestRow * tileEntity.getRowLength(), 12 + chestCol * 18, 26 + chestRow * 18));
			}

		}

		int leftCol = (238 - 162) / 2 + 1;
		for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
			for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
				addSlotToContainer(new Slot(inventory.inventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, 256 - (4 - playerInvRow) * 18 - 10));
			}

		}

		for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
			addSlotToContainer(new Slot(inventory.inventory, hotbarSlot, leftCol + hotbarSlot * 18, 256 - 24));
		}
	}

	@Override
	public boolean canInteractWith( EntityPlayer player ) {
		return tileEntity.isUseableByPlayer(player);
	}

	@Override
	public void onCraftGuiClosed( EntityPlayer entityplayer ) {
		super.onCraftGuiClosed(entityplayer);
		tileEntity.closeChest();
	}

	@Override
	public ItemStack transferStackInSlot( EntityPlayer p, int i ) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i < tileEntity.getSizeInventory()) {
				if (!mergeItemStack(itemstack1, tileEntity.getSizeInventory(), inventorySlots.size(), true)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 0, tileEntity.getSizeInventory(), false)) {
				return null;
			}
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemstack;
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
	@SideOnly(Side.CLIENT)
	public void updateProgressBar( int i, int j ) {
		super.updateProgressBar(i, j);
		tileEntity.receiveGuiNetworkData(i, j);
	}

}
