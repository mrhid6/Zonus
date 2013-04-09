package mrhid6.zonus.gui;

import mrhid6.zonus.tileEntity.TEZoroChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerZoroChest extends ContainerXorbo {

	protected TEZoroChest tileEntity;

	public ContainerZoroChest( EntityPlayer inventory, TEZoroChest tileEntity2 ) {
		tileEntity = tileEntity2;
		tileEntity.openChest();

		for (int chestRow = 0; chestRow < tileEntity.getRowCount(); chestRow++) {
			for (int chestCol = 0; chestCol < tileEntity.getRowLength(); chestCol++) {
				addSlotToContainer(new Slot(tileEntity, chestCol + chestRow * tileEntity.getRowLength(), 12 + chestCol * 18, 8 + chestRow * 18));
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

}
