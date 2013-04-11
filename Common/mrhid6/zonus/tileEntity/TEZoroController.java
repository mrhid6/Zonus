package mrhid6.zonus.tileEntity;

import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import mrhid6.zonus.Config;
import mrhid6.zonus.GridManager;
import mrhid6.zonus.GridPower;
import mrhid6.zonus.Utils;
import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.fx.FXSparkle;
import mrhid6.zonus.interfaces.ITriniumObj;
import mrhid6.zonus.interfaces.IXorGridObj;
import mrhid6.zonus.lib.Reference;
import mrhid6.zonus.network.PacketTile;
import mrhid6.zonus.network.PacketUtils;
import mrhid6.zonus.network.Payload;
import mrhid6.zonus.proxy.PowerMJProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;

public class TEZoroController extends TEMachineBase implements IXorGridObj, IPowerReceptor {

	PowerMJProxy pp;
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
		
		if(Reference.useBuildCraft){
			pp = new PowerMJProxy();
			pp.configure(0, 1, 320, 8, 640);
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		//System.out.println("invalidate");
	}

	@Override
	public void validate() {
		super.validate();
		//System.out.println("validate");
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

			myGrid = new GridPower();
			myGrid.setController(this);
			gridindex = myGrid.gridIndex;
			getGrid().setEnergyStored(energyToStore);

			setUpdate(true);

		} else if (getGrid() != null && !getGrid().isController(worldObj, xCoord, yCoord, zCoord)) {

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
	public Packet getDescriptionPacket() {
		Payload payload = new Payload(2, 1, 1, 3, 0);

		payload.boolPayload[0] = isActive;
		payload.boolPayload[1] = transmitpower;

		payload.intPayload[0] = gridindex;

		payload.floatPayload[0] = processCur;
		payload.floatPayload[1] = processEnd;
		if(Reference.useBuildCraft)
			payload.floatPayload[2] = pp.getEnergyStored();

		payload.bytePayload[0] = (byte) getFacing();

		PacketTile packet = new PacketTile(descPacketId, xCoord, yCoord, zCoord, payload);
		return packet.getPacket();
	}
	
	

	@Override
	public void handleTilePacket( PacketTile packet ) {
		if(Reference.useBuildCraft){
			pp.setEnergyStored(packet.payload.floatPayload[2]);
			
			if(Utils.isClientWorld()){
				pp.setEnergyStored(packet.payload.floatPayload[2]);
			}
		}
		super.handleTilePacket(packet);
	}

	@Override
	public void readFromNBT( NBTTagCompound data ) {
		super.readFromNBT(data);

		if (getGrid() != null) {
			energyToStore = data.getFloat("grid.power");
			getGrid().setEnergyStored(data.getFloat("grid.power"));

		} else {
			energyToStore = data.getFloat("grid.power");
		}

		gridindex = data.getInteger("grid.index");

		System.out.println("read nbt - "+energyToStore);

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

		if(!isLoaded)
			return;

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

		if (Utils.isClientWorld() || breakingblock) {
			return;
		}

		if (isUpdate()) {
			GridManager.sendUpdatePacket(Side.CLIENT, worldObj, xCoord, yCoord, zCoord, gridindex);
			sendUpdatePacket(Side.CLIENT);
		}

		if(getGrid()!=null){
			getGrid().update();
			setUpdate(true);
		}
		if ((TickSinceUpdate % 3) == 0) {

			if (getGrid() != null) {

				if(Reference.useBuildCraft){
					float want = (getGrid().getMaxEnergy() - getGrid().getEnergyStored()/3);
					float used = this.pp.useEnergy(1.0F, Math.min(want, this.pp.getMaxEnergyStored()), true);
					getGrid().addEnergy(used * 3.0F);
					setUpdate(true);
				}
			}
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

	@Override
	public void setPowerProvider( IPowerProvider provider ) {
	}

	@Override
	public IPowerProvider getPowerProvider() {
		return this.pp;
	}

	@Override
	public void doWork() {
	}

	@Override
	public int powerRequest( ForgeDirection from ) {
		if(getGrid()!=null && Reference.useBuildCraft && getGrid().getMaxEnergy()>getGrid().getEnergyStored()){
			return (int) (getGrid().getMaxEnergy() - getGrid().getEnergyStored()/3);
		}
		return 0;
	}

}
