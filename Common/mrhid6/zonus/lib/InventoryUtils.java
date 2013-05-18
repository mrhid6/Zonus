package mrhid6.zonus.lib;

import java.util.ArrayList;
import java.util.List;
import mrhid6.zonus.tileEntity.machine.TEZoroChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

public class InventoryUtils {

	public static boolean canStack( ItemStack stack1, ItemStack stack2 ) {
		return stack1 == null || stack2 == null || (stack1.itemID == stack2.itemID && (!stack2.getHasSubtypes() || stack2.getItemDamage() == stack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack2, stack1));
	}

	public static boolean canStoreInChest( TEZoroChest chest, ItemStack stack ) {

		int slot = -1;
		while ((slot = InventoryUtils.getPartialSlot(stack, slot + 1, chest.getSizeInventory(), chest)) >= 0) {
			return true;
		}

		slot = 0;
		while ((slot = InventoryUtils.getEmptySlot(0, chest.getSizeInventory(), chest)) >= 0) {
			return true;
		}

		return false;

	}

	public static ItemStack copyStack( ItemStack stack ) {

		if (stack == null) {
			return null;
		}
		int quanity = stack.stackSize;
		stack = stack.copy();
		stack.stackSize = quanity;

		return stack;
	}

	public static ItemStack copyStack( ItemStack stack, int quanity ) {

		if (stack == null) {
			return null;
		}

		stack = stack.copy();
		stack.stackSize = quanity;

		return stack;
	}

	public static ItemStack decrStackSize( IInventory inv, int slot, int size ) {
		ItemStack item = inv.getStackInSlot(slot);

		if (item != null) {
			if (item.stackSize <= size) {
				ItemStack itemstack = item;
				inv.setInventorySlotContents(slot, null);
				inv.onInventoryChanged();
				return itemstack;
			}
			ItemStack itemstack1 = item.splitStack(size);
			if (item.stackSize == 0) {
				inv.setInventorySlotContents(slot, null);
			}
			inv.onInventoryChanged();
			return itemstack1;
		} else {
			return null;
		}
	}

	public static IRecipe findMatchingRecipe( InventoryCrafting par1InventoryCrafting, World par2World ) {
		int var3 = 0;
		ItemStack var4 = null;
		ItemStack var5 = null;
		int var6;

		for (var6 = 0; var6 < par1InventoryCrafting.getSizeInventory(); ++var6) {
			ItemStack var7 = par1InventoryCrafting.getStackInSlot(var6);

			if (var7 != null) {
				if (var3 == 0) {
					var4 = var7;
				}

				if (var3 == 1) {
					var5 = var7;
				}

				++var3;
			}
		}

		if (var3 == 2 && var4.itemID == var5.itemID && var4.stackSize == 1 && var5.stackSize == 1 && Item.itemsList[var4.itemID].isRepairable()) {
			Item var11 = Item.itemsList[var4.itemID];
			int var13 = var11.getMaxDamage() - var4.getItemDamageForDisplay();
			int var8 = var11.getMaxDamage() - var5.getItemDamageForDisplay();
			int var9 = var13 + var8 + var11.getMaxDamage() * 5 / 100;
			int var10 = var11.getMaxDamage() - var9;

			if (var10 < 0) {
				var10 = 0;
			}

			ArrayList ingredients = new ArrayList<ItemStack>(2);
			ingredients.add(var4);
			ingredients.add(var5);
			return new ShapelessRecipes(new ItemStack(var4.itemID, 1, var10), ingredients);
		} else {
			List recipes = CraftingManager.getInstance().getRecipeList();
			for (var6 = 0; var6 < recipes.size(); ++var6) {
				IRecipe var12 = (IRecipe) recipes.get(var6);

				if (var12.matches(par1InventoryCrafting, par2World)) {
					return var12;
				}
			}

			return null;
		}
	}

	public static int getEmptyChestSlot( int startSlot, int endSlot, TEZoroChest chest ) {
		for (int i = startSlot; i < endSlot; i++) {
			if (chest.getStackInSlot(i) == null) {
				return i;
			}
		}

		return -1;
	}

	public static int getEmptySlot( int startSlot, int endSlot, IInventory inv ) {
		for (int i = startSlot; i < endSlot; i++) {
			if (inv.getStackInSlot(i) == null) {
				return i;
			}
		}

		return -1;
	}

	public static int getPartialChestSlot( ItemStack stack, int startSlot, int endSlot, TEZoroChest chest ) {

		for (int i = startSlot; i < endSlot; i++) {
			if (chest.getStackInSlot(i) == null) {
				continue;
			}

			if (!chest.getStackInSlot(i).isItemEqual(stack) || !ItemStack.areItemStackTagsEqual(chest.getStackInSlot(i), stack)) {
				continue;
			}

			if (chest.getStackInSlot(i).stackSize >= chest.getStackInSlot(i).getMaxStackSize()) {
				continue;
			}

			return i;
		}

		return -1;
	}

	public static int getPartialSlot( ItemStack stack, int startSlot, int endSlot, IInventory inv ) {

		for (int i = startSlot; i < endSlot; i++) {
			if (inv.getStackInSlot(i) == null) {
				continue;
			}

			if (!inv.getStackInSlot(i).isItemEqual(stack) || !ItemStack.areItemStackTagsEqual(inv.getStackInSlot(i), stack)) {
				continue;
			}

			if (inv.getStackInSlot(i).stackSize >= inv.getStackInSlot(i).getMaxStackSize()) {
				continue;
			}

			return i;
		}

		return -1;
	}

	public static int incrStackSize( ItemStack base, int addition ) {
		int totalSize = base.stackSize + addition;

		if (totalSize <= base.getMaxStackSize()) {
			return addition;
		}
		if (base.stackSize < base.getMaxStackSize()) {
			return base.getMaxStackSize() - base.stackSize;
		}

		return 0;
	}

}
