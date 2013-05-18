package mrhid6.zonus.tileEntity.machine;

import java.util.HashMap;
import mrhid6.zonus.interfaces.IConverterObj;
import mrhid6.zonus.interfaces.ISidedBlock;
import mrhid6.zonus.interfaces.ITriniumObj;
import mrhid6.zonus.interfaces.IXorGridObj;
import mrhid6.zonus.lib.Utils;
import mrhid6.zonus.tileEntity.TEMachineBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;

public class TEStearilliumEnergyCube extends TEMachineBase implements IXorGridObj, ISidedBlock {

	protected static final HashMap recipes = new HashMap();
	public int tempEng = 0;

	private boolean update = false;

	public TEStearilliumEnergyCube() {
		inventory = new ItemStack[0];
	}

	public void breakBlock() {

		if (getGrid() != null) {
			getGrid().removeEnergyCube(this);
		}
	}

	@Override
	public boolean canConnectOnSide( int side ) {
		return (side == getFacing());

	}

	@Override
	public boolean canConnectThrough() {
		return false;
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
			return (getGrid().hasEnergyCube(this) && getGrid().canDiscoverObj(this));
		}

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
	public boolean isUpdate() {
		return update;
	}

	@Override
	public void setGridIndex( int id ) {
		gridindex = id;

	}

	@Override
	public void setUpdate( boolean update ) {
		this.update = update;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (Utils.isClientWorld()) {
			return;
		}

		if (!foundController()) {
			if (getGrid() != null) {
				getGrid().removeEnergyCube(this);
			}
			if (gridindex != -1) {
				gridindex = -1;
				setUpdate(true);
			}
		}

		if (isUpdate()) {
			sendUpdatePacket(Side.CLIENT);
			this.setUpdate(false);
		}

		TickSinceUpdate++;
	}

	@Override
	public int[] getAccessibleSlotsFromSide( int var1 ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem( int i, ItemStack itemstack, int j ) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem( int i, ItemStack itemstack, int j ) {
		// TODO Auto-generated method stub
		return false;
	}
}
