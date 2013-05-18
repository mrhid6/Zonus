package mrhid6.zonus.lib;

import net.minecraft.item.ItemStack;

public class ZonusFurnaceRecipe {

	private int amount;
	private ItemStack input;
	private ItemStack output;

	public ZonusFurnaceRecipe( ItemStack input, ItemStack output, int amount ) {
		this.input = input.copy();
		this.output = output.copy();
		this.amount = amount;
	}

	public ItemStack getInput() {
		return input.copy();
	}

	public ItemStack getOutput() {
		return InventoryUtils.copyStack(output, amount);
	}

}
