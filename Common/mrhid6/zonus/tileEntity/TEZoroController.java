package mrhid6.zonus.tileEntity;

import mrhid6.zonus.Config;
import mrhid6.zonus.GridManager;
import mrhid6.zonus.GridPower;
import mrhid6.zonus.Utils;
import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.fx.FXBeam;
import mrhid6.zonus.fx.FXSparkle;
import mrhid6.zonus.interfaces.ITriniumObj;
import mrhid6.zonus.interfaces.IXorGridObj;
import mrhid6.zonus.network.PacketUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;

public class TEZoroController extends TEMachineBase implements IXorGridObj {

	public static boolean setDescPacketId( int id ) {
		if (id == 0) {
			return false;
		}
		descPacketId = id;
		return true;
	}

	public boolean breakingblock = false;

	public float energyToStore = 0;
	
	public int particaltick = 0;

	public TEZoroController() {

		inventory = new ItemStack[0];
	}

	@Override
	public void invalidate() {
		super.invalidate();

		System.out.println("invalidate");
	}

	@Override
	public void validate() {
		super.validate();
		System.out.println("validate");
	}

	public void breakBlock() {
		if (getGrid() != null) {
			getGrid().removeController(worldObj, xCoord, yCoord, zCoord);
		}
	}

	public void breakController() {

		if (breakingblock == true) {
			return;
		}

		breakingblock = true;

		worldObj.setBlock(xCoord, yCoord, zCoord, 0);
		EntityItem entityitem = new EntityItem(worldObj, xCoord, yCoord+1, zCoord, new ItemStack(ModBlocks.zoroController, 1));

		entityitem.lifespan = 5200;
		entityitem.delayBeforeCanPickup = 10;

		entityitem.motionX *= 0.0000001;
		entityitem.motionY *= 0;
		entityitem.motionZ *= 0.0000001;
		worldObj.spawnEntityInWorld(entityitem);
	}

	public int cableCount() {
		int count = 0;
		for (int i = 0; i < 6; i++) {

			int x1 = xCoord + Config.SIDE_COORD_MOD[i][0];
			int y1 = yCoord + Config.SIDE_COORD_MOD[i][1];
			int z1 = zCoord + Config.SIDE_COORD_MOD[i][2];

			TileEntity te = worldObj.getBlockTileEntity(x1, y1, z1);

			if (te instanceof TECableBase) {
				count++;
			}
		}
		return count;
	}

	public boolean canBeAdjacent() {
		for (int i = 0; i < 6; i++) {

			int x1 = xCoord + Config.SIDE_COORD_MOD[i][0];
			int y1 = yCoord + Config.SIDE_COORD_MOD[i][1];
			int z1 = zCoord + Config.SIDE_COORD_MOD[i][2];

			TileEntity te = worldObj.getBlockTileEntity(x1, y1, z1);

			if (te instanceof TECableBase) {
				TECableBase cable = (TECableBase) te;

				if (cable.canInteractWith(this, i, false)) {
					return false;
				}
			}
			if (te instanceof TEPoweredBase) {
				return false;
			}
			if (te instanceof TETriniumConverter) {
				return false;
			}

		}

		return true;
	}

	@Override
	public boolean canConnectThrough() {
		return false;
	}

	@Override
	public boolean canInteractWith( TileEntity te ) {

		if (te instanceof ITriniumObj) {
			return false;
		}
		if (te instanceof TECableBase) {
			return true;
		}
		if (te instanceof IXorGridObj) {
			return true;
		}

		return false;
	}

	@Override
	public boolean func_102007_a( int i, ItemStack itemstack, int j ) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * public void sendGuiNetworkData(Container container, ICrafting iCrafting){
	 * if(getGrid()!=null && tempEng!=((int)getGrid().getEnergyStored())){
	 * iCrafting.sendProgressBarUpdate(container, 0,
	 * (int)getGrid().getEnergyStored()); tempEng =
	 * (int)getGrid().getEnergyStored(); }
	 * iCrafting.sendProgressBarUpdate(container, 1, this.gridindex); }
	 */

	@Override
	public boolean func_102008_b( int i, ItemStack itemstack, int j ) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[] getSizeInventorySide( int var1 ) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean gridCheck() {
		for (int i = 0; i < 6; i++) {

			int x1 = xCoord + Config.SIDE_COORD_MOD[i][0];
			int y1 = yCoord + Config.SIDE_COORD_MOD[i][1];
			int z1 = zCoord + Config.SIDE_COORD_MOD[i][2];

			GridPower gridCheck = GridManager.getGridAt(x1, y1, z1, worldObj, i);

			if (myGrid != null && gridCheck != null) {

				if (gridCheck.gridIndex < myGrid.gridIndex && !(gridCheck.gridIndex == -1)) {
					if (!canBeAdjacent()) {
						myGrid.removeController(worldObj, xCoord, yCoord, zCoord);
						breakController();
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void init() {

		findGridOnlyCable();

		if (gridindex < 0 || GridManager.getGrid(gridindex) == null) {

			System.out.println("grid is null creating new one!");
			myGrid = new GridPower();

			myGrid.setController(this);

			// System.out.println(energyToStore);
			myGrid.setEnergyStored(energyToStore);
			
			sendUpdatePacket(Side.CLIENT);
			GridManager.sendUpdatePacket(Side.CLIENT, worldObj, xCoord, yCoord, zCoord, myGrid.gridIndex);

		} else if (getGrid() != null && !getGrid().isController(worldObj, xCoord, yCoord, zCoord)) {

			// System.out.println((getGrid()!=null)+","+(!getGrid().isController(worldObj,
			// xCoord, yCoord, zCoord)));

			if (!canBeAdjacent()) {
				breakController();
			}
		}else{
			getGrid().setController(this);
		}
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

	public void onNeighborBlockChange() {

		// updateGrid();
	}

	@Override
	public void readFromNBT( NBTTagCompound data ) {
		super.readFromNBT(data);

		gridindex = data.getInteger("grid.index");

		System.out.println(gridindex);
		if (getGrid() != null) {
			getGrid().setEnergyStored(data.getFloat("grid.power"));
		} else {
			energyToStore = data.getFloat("grid.power");
		}

	}

	@Override
	public void receiveGuiNetworkData( int i, int j ) {

	}

	@Override
	public void sendGuiNetworkData( Container container, ICrafting iCrafting ) {
		if (((iCrafting instanceof EntityPlayer)) && (Utils.isServerWorld())) {
			PacketUtils.sendToPlayer((EntityPlayer) iCrafting, getDescriptionPacket());
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (Utils.isClientWorld()) {
			if ((particaltick % 3) == 0) {
				double x = xCoord +0.5F+(Math.random()*0.3)-0.15;
				double z = zCoord +0.5F+(Math.random()*0.3)-0.15;

				FXSparkle beam = new FXSparkle(worldObj, x, yCoord+0.9F, z);
				Minecraft.getMinecraft().effectRenderer.addEffect(beam);
				Minecraft.getMinecraft().effectRenderer.renderParticles(beam, 1);
			}
			particaltick++;
		}
		
		boolean update = false;
		boolean updateGridCheck = false;

		if (Utils.isClientWorld() || breakingblock) {
			return;
		}
		
		if(getGrid()!=null){
			getGrid().update();
			update=true;
		}
		if ((TickSinceUpdate % 3) == 0) {

			if (getGrid() != null) {
				//getGrid().addEnergy(500F);
				update = true;
			}
		}

		if (update || updateGridCheck) {
			GridManager.sendUpdatePacket(Side.CLIENT, worldObj, xCoord, yCoord, zCoord, gridindex);
			sendUpdatePacket(Side.CLIENT);
		}

		TickSinceUpdate++;
	}

	@Override
	public void writeToNBT( NBTTagCompound data ) {
		super.writeToNBT(data);

		data.setInteger("grid.index", gridindex);

		if (getGrid() != null) {
			getGrid().writeToNBT(data);
		}
	}

}
