package mrhid6.zonus.tileEntity;

import mrhid6.zonus.interfaces.IXorGridObj;

public class TENoxiteTurret extends TileRoot implements IXorGridObj {

	private byte facing;

	public byte getFacing() {
		return facing;
	}

	public void setFacing( byte facing ) {
		this.facing = facing;
	}

}
