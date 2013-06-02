package mrhid6.zonus.lib;

import net.minecraft.item.ItemStack;


public class CrystalForgeRecipe {
	
	private int amount;
	private ItemStack input1;
	private ItemStack input2;
	private ItemStack input3;
	private ItemStack output;
	private float time;
	
	public CrystalForgeRecipe() {
		
	}
	
	public void setInput1(ItemStack i){
		this.input1 = i.copy();
	}
	public void setInput2(ItemStack i){
		this.input2 = i.copy();
	}
	public void setInput3(ItemStack i){
		this.input3 = i.copy();
	}
	
	public void setOutput(ItemStack i){
		this.output = i.copy();
	}
	
	public void setOutputQuantity(int a){
		this.amount = a;
	}
	
	public void setTimeRequired(int t){
		this.time = t;
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
