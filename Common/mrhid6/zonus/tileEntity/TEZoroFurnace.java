package mrhid6.zonus.tileEntity;

import java.util.HashMap;
import mrhid6.zonus.Utils;
import mrhid6.zonus.interfaces.IConverterObj;
import mrhid6.zonus.interfaces.ITriniumObj;
import mrhid6.zonus.interfaces.IXorGridObj;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;

public class TEZoroFurnace extends TEMachineBase implements IXorGridObj {

	public static int guiPacketId;
	protected static final HashMap recipes = new HashMap();

	public static boolean setGuiPacketId( int id ) {
		if (id == 0) {
			return false;
		}
		guiPacketId = id;
		return true;
	}

	public int tempEng = 0;

	public TEZoroFurnace() {
		inventory = new ItemStack[2];

		invName = "xor.furnace";
	}

	public void breakBlock() {

		if (getGrid() != null) {
			getGrid().removeMachine(this);
		}
	}

	@Override
	public boolean canConnectThrough() {
		return true;
	}

	@Override
	public boolean canInteractWith( TileEntity te ) {
		if (te instanceof IConverterObj) {
			return false;
		}
		if (te instanceof ITriniumObj) {
			return false;
		}

		return true;
	}

	public boolean foundController() {

		if (getGrid() != null) {
			return getGrid().hasMachine(this);
		}

		return false;
	}

	@Override
	public boolean func_102007_a( int i, ItemStack itemstack, int j ) {
		return false;
	}

	@Override
	public boolean func_102008_b( int i, ItemStack itemstack, int j ) {
		return false;
	}

	public ItemStack getResultFor( ItemStack itemstack ) {
		ItemStack item = (ItemStack) recipes.get(itemstack.itemID);
		return (item == null) ? null : item.copy();
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public int[] getSizeInventorySide( int var1 ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {

	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isStackValidForSlot( int i, ItemStack itemstack ) {
		return false;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (Utils.isClientWorld()) {
			return;
		}

		if (TickSinceUpdate % 2 == 0) {
			if (!foundController()) {
				if (getGrid() != null) {
					getGrid().removeMachine(this);
				}
				gridindex = -1;
				sendUpdatePacket(Side.CLIENT);
			}
		}

		TickSinceUpdate++;
	}
}
