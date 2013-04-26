package mrhid6.zonus.gui;

import mrhid6.zonus.tileEntity.machine.TEStearilliumCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerStearilliumCrafter extends ContainerXorbo {

	protected TEStearilliumCrafter tileEntity;

	public ContainerStearilliumCrafter( EntityPlayer inventory, TEStearilliumCrafter te ) {
		tileEntity = te;

		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				this.addSlotToContainer(new DummySlot(tileEntity, r * 3 + c, 8 + c * 18, 20 + r * 18, 1));
			}
		}

		this.addSlotToContainer(new DummySlotOutput(tileEntity, 9, 80, 38){

			@Override
			public void slotClick( ItemStack stack, int button, boolean shift ) {
				if (button == 0) {
					tileEntity.instataCraft();
				} else {
					for (int i = 0; i < 9; i++) {
						tileEntity.setInventorySlotContents(i, null);
					}
				}
			}
		});
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				this.addSlotToContainer(new Slot(tileEntity, r * 3 + c + 10, 116 + c * 18, 20 + r * 18));
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory.inventory, j + i * 9 + 9, 8 + j * 18, 95 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory.inventory, i, 8 + i * 18, 153));
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

	public boolean doMergeStackAreas( int slotIndex, ItemStack stack ) {
		if (slotIndex < 19) {
			return mergeItemStack(stack, 19, 55, true);
		}
		return mergeItemStack(stack, 10, 19, false);
	}

	@Override
	public ItemStack transferStackInSlot( EntityPlayer par1EntityPlayer, int slotIndex ) {
		ItemStack transferredStack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack stack = slot.getStack();
			transferredStack = stack.copy();

			if (!doMergeStackAreas(slotIndex, stack)) {
				return null;
			}
			if (stack.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
		}
		return transferredStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar( int i, int j ) {
		super.updateProgressBar(i, j);
		tileEntity.receiveGuiNetworkData(i, j);
	}

}