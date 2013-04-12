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
	private boolean update = false;

	public int tempEng = 0;

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
		return (side==getFacing());

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

		if (!foundController()) {
			if (getGrid() != null) {
				getGrid().removeEnergyCube(this);
				this.setUpdate(true);
			}
			gridindex = -1;
		}
		
		if(isUpdate()){
			sendUpdatePacket(Side.CLIENT);
			this.setUpdate(false);
		}

		TickSinceUpdate++;
	}
	
	public boolean isUpdate() {
		return update;
	}

	public void setUpdate( boolean update ) {
		this.update = update;
	}
}
