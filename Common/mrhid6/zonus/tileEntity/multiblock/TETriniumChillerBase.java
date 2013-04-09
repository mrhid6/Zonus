package mrhid6.zonus.tileEntity.multiblock;

import mrhid6.zonus.Utils;
import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.interfaces.ITriniumObj;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TETriniumChillerBase extends TileEntity implements ITriniumObj {

	private TETriniumChillerCore core = null;

	public TETriniumChillerCore getCore() {
		if (core == null) {
			core = (TETriniumChillerCore) Utils.getTileEntity(worldObj, xCoord, yCoord, zCoord, 5, ModBlocks.triniumChiller.blockID);
		}

		return core;
	}

	@Override
	public void readFromNBT( NBTTagCompound par1nbtTagCompound ) {
		super.readFromNBT(par1nbtTagCompound);
	}

	@Override
	public void writeToNBT( NBTTagCompound data ) {
		super.writeToNBT(data);

	}

}
