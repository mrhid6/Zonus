package mrhid6.zonus.tileEntity;

import mrhid6.zonus.Config;
import mrhid6.zonus.GridManager;
import mrhid6.zonus.GridPower;
import mrhid6.zonus.Utils;
import mrhid6.zonus.interfaces.IGridInterface;
import net.minecraft.tileentity.TileEntity;

public abstract class TEPoweredBase extends TileRoot implements IGridInterface {

	public int gridindex = -1;
	public boolean isLoaded = false;
	public GridPower myGrid;
	public int TickSinceUpdate = 0;

	public abstract boolean canInteractWith( TileEntity te );

	public void findGridOnlyCable() {
		for (int i = 0; i < 6; i++) {

			if (myGrid != null) {
				return;
			}

			int x1 = xCoord + Config.SIDE_COORD_MOD[i][0];
			int y1 = yCoord + Config.SIDE_COORD_MOD[i][1];
			int z1 = zCoord + Config.SIDE_COORD_MOD[i][2];

			TileEntity te = worldObj.getBlockTileEntity(x1, y1, z1);

			if (te instanceof TECableBase) {
				TECableBase cable = (TECableBase) te;

				if (cable.getGrid() != null && cable.canInteractWith(this, i, false)) {
					gridindex = cable.getGrid().gridIndex;
					System.out.println("found Grid");
					break;
				}
			}
		}
	}

	@Override
	public GridPower getGrid() {
		GridPower grid = GridManager.getGrid(gridindex);

		return grid;

	}

	public abstract void init();

	public boolean isLoaded() {
		return isLoaded;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (Utils.isClientWorld()) {
			return;
		}

		if (!isLoaded) {
			init();
			isLoaded = true;
		}
	}
}
