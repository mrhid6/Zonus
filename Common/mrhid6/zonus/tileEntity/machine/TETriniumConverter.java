package mrhid6.zonus.tileEntity.machine;

import mrhid6.zonus.Config;
import mrhid6.zonus.GridManager;
import mrhid6.zonus.GridPower;
import mrhid6.zonus.interfaces.IConverterObj;
import mrhid6.zonus.interfaces.ISidedBlock;
import mrhid6.zonus.lib.Utils;
import mrhid6.zonus.tileEntity.TECableBase;
import mrhid6.zonus.tileEntity.TEMachineBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;

public class TETriniumConverter extends TEMachineBase implements IConverterObj, ISidedBlock {

	public boolean[] connections = new boolean[6];

	public TETriniumConverter() {
		inventory = new ItemStack[0];
	}

	public void breakBlock() {
		if (getGrid() != null) {
			getGrid().removeConverter(this);
		}
	}

	@Override
	public boolean canConnectOnSide( int side ) {

		if (side == 1 || side == 0) {
			return false;
		}

		return true;
	}

	@Override
	public boolean canConnectThrough() {
		return false;
	}

	@Override
	public boolean canInteractWith( TileEntity te ) {
		if (te instanceof TECableBase) {
			return true;
		}
		return false;
	}

	public boolean foundController() {

		if (getGrid() != null) {
			return getGrid().hasConverter(this) && getGrid().canDiscoverObj(this);
		}

		return false;
	}

	public void gridCheck() {

		// System.out.println("updateCheck!");
		for (int i = 0; i < 6; i++) {

			int x1 = xCoord + Config.SIDE_COORD_MOD[i][0];
			int y1 = yCoord + Config.SIDE_COORD_MOD[i][1];
			int z1 = zCoord + Config.SIDE_COORD_MOD[i][2];

			GridPower gridCheck = GridManager.getGridAt(x1, y1, z1, worldObj, i);

			if (getGrid() != null && gridCheck != null) {

				if (gridCheck.gridIndex < getGrid().gridIndex) {
					getGrid().removeMachine(this);
					gridindex = gridCheck.gridIndex;

					System.out.println("teconverter grid Was Changed to" + gridCheck.gridIndex);
				}
			}
		}
	}

	@Override
	public void init() {

	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStackValidForSlot( int i, ItemStack itemstack ) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUseableByPlayer( EntityPlayer var1 ) {
		return false;
	}

	@Override
	public void setGridIndex( int id ) {
		gridindex = id;

	}

	public void updateConnections() {

		for (int i = 0; i < 6; i++) {

			int[] coords = Config.getAdjacentCoordinatesForSide(xCoord, yCoord, zCoord, i);
			TileEntity te = worldObj.getBlockTileEntity(coords[0], coords[1], coords[2]);

			connections[i] = (te instanceof TECableBase);
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (Utils.isClientWorld()) {
			return;
		}

		if (!foundController()) {
			if (getGrid() != null) {
				getGrid().removeConverter(this);
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

		updateConnections();
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
