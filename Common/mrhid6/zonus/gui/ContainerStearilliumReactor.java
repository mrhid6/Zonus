package mrhid6.zonus.gui;

import mrhid6.zonus.tileEntity.multiblock.TEStearilliumReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerStearilliumReactor extends ContainerXorbo {

	protected TEStearilliumReactor tileEntity;

	public ContainerStearilliumReactor( EntityPlayer inventory, TEStearilliumReactor tileEntity2 ) {
		tileEntity = tileEntity2;

		addSlots();

		int leftCol = 8;
		for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
			for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
				addSlotToContainer(new Slot(inventory.inventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, 202 - (4 - playerInvRow) * 18 - 10));
			}

		}

		for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
			addSlotToContainer(new Slot(inventory.inventory, hotbarSlot, leftCol + hotbarSlot * 18, 202 - 24));
		}
	}

	public void addSlots() {
		// lvl1
		addSlotToContainer(new Slot(tileEntity, 0, 62, 17));
		addSlotToContainer(new Slot(tileEntity, 1, 80, 17));
		addSlotToContainer(new Slot(tileEntity, 2, 98, 17));

		// lvl2
		addSlotToContainer(new Slot(tileEntity, 3, 53, 35));
		addSlotToContainer(new Slot(tileEntity, 4, 71, 35));
		addSlotToContainer(new Slot(tileEntity, 5, 89, 35));
		addSlotToContainer(new Slot(tileEntity, 6, 107, 35));

		// lvl3
		addSlotToContainer(new Slot(tileEntity, 7, 44, 53));
		addSlotToContainer(new Slot(tileEntity, 8, 62, 53));
		addSlotToContainer(new Slot(tileEntity, 9, 80, 53));
		addSlotToContainer(new Slot(tileEntity, 10, 98, 53));
		addSlotToContainer(new Slot(tileEntity, 11, 116, 53));

		// lvl4
		addSlotToContainer(new Slot(tileEntity, 12, 53, 71));
		addSlotToContainer(new Slot(tileEntity, 13, 71, 71));
		addSlotToContainer(new Slot(tileEntity, 14, 89, 71));
		addSlotToContainer(new Slot(tileEntity, 15, 107, 71));

		// lvl5
		addSlotToContainer(new Slot(tileEntity, 16, 62, 89));
		addSlotToContainer(new Slot(tileEntity, 17, 80, 89));
		addSlotToContainer(new Slot(tileEntity, 18, 98, 89));
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
	public void onCraftGuiClosed( EntityPlayer entityplayer ) {
		super.onCraftGuiClosed(entityplayer);
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
	@SideOnly(Side.CLIENT)
	public void updateProgressBar( int i, int j ) {
		super.updateProgressBar(i, j);
		tileEntity.receiveGuiNetworkData(i, j);
	}

}
