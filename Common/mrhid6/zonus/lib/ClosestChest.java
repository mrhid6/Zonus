package mrhid6.zonus.lib;

import mrhid6.zonus.tileEntity.machine.TEZoroChest;


public class ClosestChest {
	
	public int xDiff;
	public int yDiff;
	public int zDiff;
	
	public TEZoroChest chest;
	
	public ClosestChest(int x,int y,int z, TEZoroChest c) {
		xDiff = x;
		yDiff = y;
		zDiff = z;
		chest = c;
	}
	
	

}
