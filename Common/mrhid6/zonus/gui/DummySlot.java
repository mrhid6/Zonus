package mrhid6.zonus.gui;

import mrhid6.zonus.InventoryUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class DummySlot extends Slot {

	public final int limit;

	public DummySlot( IInventory inv, int slot, int x, int y ) {
		this(inv, slot, x, y, 64);
	}

	public DummySlot( IInventory inv, int slot, int x, int y, int limit ) {
		super(inv, slot, x, y);
		this.limit = limit;
	}

	@Override
	public void putStack( ItemStack stack ) {

		if (stack != null && stack.stackSize > limit) {
			stack = InventoryUtils.copyStack(stack, limit);
		}
		super.putStack(stack);
	}

	public void slotClick( ItemStack stack, int button, boolean shift ) {
		if ((stack != null) && (!stack.equals(getStack()))) {
			int quantity = Math.min(stack.stackSize, limit);
			if (shift) {
				quantity = limit;
			}
			if (button == 1) {
				quantity = 1;
			}
			putStack(InventoryUtils.copyStack(stack, quantity));
		} else if (getStack() != null) {
			int inc = button == 1 ? -1 : 1;
			if (shift) {
				inc *= 16;
			}
			ItemStack tstack = getStack();
			int quantity = tstack.stackSize + InventoryUtils.incrStackSize(tstack, inc);
			if (quantity <= 0) {
				putStack(null);
			} else {
				putStack(InventoryUtils.copyStack(tstack, quantity));
			}
		}
	}

}
