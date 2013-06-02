package mrhid6.zonus.lib;

import net.minecraft.tileentity.TileEntity;


public class GridTileEntity {
	int x;
	int z;
	int y;
	
	public GridTileEntity( int x, int z, int y ) {
		this.x = x;
		this.z = z;
		this.y = y;
	}

	public GridTileEntity( TileEntity te ) {
		this.x = te.xCoord;
		this.y = te.yCoord;
		this.z = te.zCoord;
		
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public int getY() {
		return y;
	}
	
	public boolean isEqual(GridTileEntity gte){
		
		return (gte.getX() == getX() && gte.getY() == getY() && gte.getZ() == getZ());
	}

	@Override
	public String toString() {
		return "GridTileEntity [x=" + x + ", z=" + z + ", y=" + y + "]";
	}
	
}
