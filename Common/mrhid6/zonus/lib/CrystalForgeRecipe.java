package mrhid6.zonus.lib;

import net.minecraft.item.ItemStack;


public class CrystalForgeRecipe {
	
	private int amount;
	private ItemStack input1;
	private ItemStack input2;
	private ItemStack input3;
	private ItemStack output;
	private float time;
	
	public CrystalForgeRecipe(ItemStack top, ItemStack left, ItemStack right, ItemStack output, int amount, float time) {
		this.input1 = top.copy();
		this.input2 = left.copy();
		this.input3 = right.copy();
		this.output = output.copy();
		this.amount = amount;
		this.time = time;
	}
	
	public boolean checkRecipe(ItemStack top, ItemStack left, ItemStack right){
		
		if(top==null || left==null || right==null){
			return false;
		}
		
		boolean ch1 = input1.isItemEqual(top);
		boolean ch2 = input2.isItemEqual(left);
		boolean ch3 = input3.isItemEqual(right);
		
		return (ch1==true && ch2==true && ch3==true);
	}
	
	public ItemStack getOutput(){
		return output.copy();
	}

	public float getTimeNeeded() {
		return time;
	}

}
