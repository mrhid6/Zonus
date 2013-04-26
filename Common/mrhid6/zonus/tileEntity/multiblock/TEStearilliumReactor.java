package mrhid6.zonus.tileEntity.multiblock;

import mrhid6.zonus.Config;
import mrhid6.zonus.Zonus;
import mrhid6.zonus.interfaces.IConverterObj;
import mrhid6.zonus.interfaces.ITriniumObj;
import mrhid6.zonus.interfaces.IXorGridObj;
import mrhid6.zonus.items.ModItems;
import mrhid6.zonus.lib.Reference;
import mrhid6.zonus.lib.Utils;
import mrhid6.zonus.tileEntity.TECableBase;
import mrhid6.zonus.tileEntity.TEMachineBase;
import mrhid6.zonus.tileEntity.TETriniumCable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;

public class TEStearilliumReactor extends TEMachineBase implements IXorGridObj, ITriniumObj {

	public boolean breakingBlock = false;
	private int cablesConnected = 0;

	private boolean causeExplosion = false;

	private TEStearilliumReactor coreBlock;
	private boolean isCore = false;
	private boolean loaded = false;

	public boolean state = false;

	private boolean update = false;

	public TEStearilliumReactor() {
		inventory = new ItemStack[19];

		invName = "stear.reactor";
	}

	public void blockBreak() {

		if (breakingBlock == true) {
			return;
		}

		if (getGrid() != null) {
			getGrid().removeReactor(getCoreBlock());
		}
		System.out.println("breaking Block!");
		checkForExplode();
		breakingBlock = true;
	}

	@Override
	public boolean canConnectThrough() {
		return false;
	}

	public boolean canCycle() {

		if (getGrid() == null) {
			return false;
		}

		int cells = getCellCount();

		if (cells > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean canInteractWith( TileEntity te ) {
		if (te instanceof IConverterObj) {
			return false;
		}
		if (te instanceof TETriniumCable) {
			return true;
		}

		return false;
	}

	public void checkForExplode() {

		if ((causeExplosion && isCore()) || (causeExplosion && getCoreBlock() == null)) {

			Zonus.spawnExplosion(worldObj, xCoord, yCoord + 4, zCoord, 50, true);
			// worldObj.newExplosion(null, xCoord, yCoord + 4, zCoord, 10.0F,
			// false, true);
		}
	}

	public void countCables() {
		cablesConnected = 0;
		for (int i = 0; i < 6; i++) {

			int x1 = xCoord + Config.SIDE_COORD_MOD[i][0];
			int y1 = yCoord + Config.SIDE_COORD_MOD[i][1];
			int z1 = zCoord + Config.SIDE_COORD_MOD[i][2];

			TileEntity te = worldObj.getBlockTileEntity(x1, y1, z1);

			if (te != null && te instanceof TECableBase && ((TECableBase) te).getGrid() != null) {
				cablesConnected++;
			}
		}
	}

	public void countCablesAroundReactor() {
		cablesConnected = 0;
		for (int yy = 0; yy > -4; yy--) {
			for (int xx = 0; xx > -4; xx--) {
				for (int zz = 0; zz > -4; zz--) {
					TileEntity te = worldObj.getBlockTileEntity(xCoord + xx, yCoord + yy, zCoord + zz);

					if (te != null && te instanceof TEStearilliumReactor) {
						((TEStearilliumReactor) te).countCables();
						cablesConnected += ((TEStearilliumReactor) te).cablesConnected;
					}
				}
			}
		}
	}

	public boolean foundController() {

		if (getGrid() != null) {
			return (getGrid().hasReactor(getCoreBlock()) && getGrid().canDiscoverReactorObj(getCoreBlock()));
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

	public int getCellCount() {
		int cellCount = 0;
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack item = inventory[i];

			if (item == null) {
				continue;
			}

			if (item.itemID == ModItems.zoroStaff.itemID) {
				cellCount++;
			}
		}

		return cellCount;
	}

	public TEStearilliumReactor getCoreBlock() {
		if (isCore()) {
			return this;
		}
		return coreBlock;
	}

	public TEStearilliumReactor getCoreTileEntity() {

		TEStearilliumReactor res = null;

		for (int xx = xCoord - 4; xx < xCoord + 4; xx++) {
			for (int zz = zCoord - 4; zz < zCoord + 4; zz++) {
				for (int yy = yCoord - 4; yy < yCoord + 4; yy++) {

					TileEntity res2 = worldObj.getBlockTileEntity(xx, yy, zz);

					if (res2 != null && res2 instanceof TEStearilliumReactor) {
						res = (TEStearilliumReactor) res2;
					}
				}
			}
		}

		return res;
	}

	@Override
	public int getSizeInventory() {
		return 19;
	}

	@Override
	public int[] getSizeInventorySide( int var1 ) {
		return null;
	}

	@Override
	public void init() {

		TEStearilliumReactor core = getCoreTileEntity();

		if (core == this) {
			setIsCore(true);
		} else {
			setCoreBlock(core);
		}
	}

	public boolean isCauseExplosion() {
		return causeExplosion;
	}

	public boolean isCore() {
		return isCore;
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

	public void processCycle() {
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack item = inventory[i];

			if (item == null) {
				continue;
			}

			if (item.itemID == ModItems.zoroStaff.itemID) {

				item.setItemDamage(item.getItemDamage() + 1);
				System.out.println(item.getItemDamage());
				if (item.getItemDamageForDisplay() >= item.getMaxDamage()) {
					setInventorySlotContents(i, null);
				}
			}
		}

	}

	@Override
	public void readFromNBT( NBTTagCompound data ) {
		super.readFromNBT(data);
	}

	public void setCauseExplosion( boolean causeExplosion ) {
		this.causeExplosion = causeExplosion;
	}

	public void setCoreBlock( TEStearilliumReactor coreBlock ) {
		this.coreBlock = coreBlock;
	}

	public void setIsCore( boolean isCore ) {
		this.isCore = isCore;
	}

	@Override
	public void setUpdate( boolean update ) {
		this.update = update;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (Utils.isClientWorld()) {

			if (!loaded) {
				init();
			}
			return;
		}

		checkForExplode();
		if (TickSinceUpdate % 2 == 0) {

			if (isCore()) {
				if (!foundController()) {
					if (getGrid() != null) {
						getGrid().removeReactor(this);
					}
					gridindex = -1;
					this.setUpdate(true);
				}

				countCablesAroundReactor();

				if (canCycle()) {
					getGrid().addEnergy(Reference.POWER_GENERATION_RATE * (cablesConnected + getCellCount()));

					if (TickSinceUpdate % 20 == 0) {
						processCycle();

					}
				}
			}

			if (isUpdate()) {
				sendUpdatePacket(Side.CLIENT);
				this.setUpdate(false);
			}
		}

		TickSinceUpdate++;
	}

	@Override
	public void writeToNBT( NBTTagCompound data ) {
		super.writeToNBT(data);
	}

}
