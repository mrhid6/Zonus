package mrhid6.zonus.tileEntity;

import mrhid6.zonus.interfaces.IConverterObj;
import mrhid6.zonus.interfaces.ITriniumObj;
import net.minecraft.tileentity.TileEntity;

public class TETriniumCable extends TECableBase implements ITriniumObj {

	public TETriniumCable() {
		type = 1;
	}

	@Override
	public boolean canInteractRender( TileEntity te, int side ) {
		if (te instanceof TETriniumConverter) {

			if (side == 2 || side == 3) {
				return false;
			}

			return true;
		}

		return canInteractWith(te, side, false);
	}

	@Override
	public boolean canInteractWith( TileEntity te, int side, boolean boundingbox ) {

		if (te instanceof TETriniumConverter) {
			TETriniumConverter te1 = (TETriniumConverter) te;
			return te1.canConnectOnSide(side);
		}

		if (te instanceof ITriniumObj) {
			return true;
		}
		if (te instanceof IConverterObj) {
			return true;
		}

		return false;
	}
}
