package mrhid6.zonus.tileEntity;

import mrhid6.zonus.GridManager;
import mrhid6.zonus.GridPower;
import mrhid6.zonus.Utils;
import mrhid6.zonus.interfaces.IConverterObj;
import mrhid6.zonus.interfaces.IGridInterface;
import mrhid6.zonus.interfaces.IPacketXorHandler;
import mrhid6.zonus.interfaces.ISidedBlock;
import mrhid6.zonus.interfaces.ITriniumObj;
import mrhid6.zonus.interfaces.IXorGridObj;
import mrhid6.zonus.network.PacketTile;
import mrhid6.zonus.network.PacketUtils;
import mrhid6.zonus.network.Payload;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;

public class TECableBase extends TileEntity implements IGridInterface, IPacketXorHandler {

	protected static int descPacketId;
	public int gridindex = -1;

	public double maxPower = 5.0D;
	public double tempPower = 0.0D;
	public int ticks = 0;
	public int type = 0;

	public boolean init = false;
	
	private boolean update = false;

	public TECableBase() {

	}

	public void breakBlock() {

		if (getGrid() != null) {
			getGrid().removeCable(this);
		}
	}

	public boolean canInteractRender( TileEntity te, int side ) {
		if (te instanceof TETriniumConverter) {

			if (side == 2 || side == 3) {
				return false;
			}

			return true;
		}

		if (te instanceof ISidedBlock) {
			TEMachineBase te1 = (TEMachineBase) te;

			if (side == 2 && te1.getFacing() == 1) {
				return true;
			} else if (side == 3 && te1.getFacing() == 4) {
				return true;
			} else if (side == 4 && te1.getFacing() == 3) {
				return true;
			} else if (side == 5 && te1.getFacing() == 2) {
				return true;
			} else if (side == 0 && te1.getFacing() == 5) {
				return true;
			} else if (side == 1 && te1.getFacing() == 4) {
				return true;
			}

			return false;
		}
		return canInteractWith(te, side, false);
	}

	public boolean canInteractWith( TileEntity te, int side, boolean boundingbox ) {

		if (te instanceof ISidedBlock) {
			ISidedBlock te1 = (ISidedBlock) te;
			side = (boundingbox) ? side ^ 1 : side;
			return te1.canConnectOnSide(side);
		}

		if (te instanceof ITriniumObj) {
			return false;
		}
		if (te instanceof TECableBase) {
			return true;
		}
		if (te instanceof IXorGridObj) {
			return true;
		}
		if (te instanceof IConverterObj) {
			return true;
		}

		return false;
	}

	public boolean foundController() {

		if (getGrid() != null) {
			return (getGrid().hasCable(this) && getGrid().canDiscoverCable(this));
		}
		return false;
	}

	public double getCableThickness() {
		switch (type) {
		case 0:
			return 4.0D / 16.0D;
		case 1:
			return 6.0D / 16.0D;
		}

		return 1.0D / 16.0D;
	}

	@Override
	public Packet getDescriptionPacket() {
		Payload payload = new Payload(0, 0, 1, 0, 0);

		// System.out.println(gridindex);

		payload.intPayload[0] = gridindex;

		PacketTile packet = new PacketTile(descPacketId, xCoord, yCoord, zCoord, payload);
		return packet.getPacket();
	}

	@Override
	public GridPower getGrid() {
		GridPower grid = GridManager.getGrid(gridindex);

		return grid;

	}

	@Override
	public void readFromNBT( NBTTagCompound data ) {
		super.readFromNBT(data);

		gridindex = data.getInteger("grid.index");

	}

	@Override
	public void writeToNBT( NBTTagCompound data ) {
		super.writeToNBT(data);

		data.setInteger("grid.index", gridindex);

	}

	@Override
	public void handleTilePacket( PacketTile packet ) {

		gridindex = packet.payload.intPayload[0];

		if (Utils.isClientWorld()) {
			gridindex = packet.payload.intPayload[0];
			// System.out.println("hadlepacket"+gridindex);
		}

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		worldObj.updateAllLightTypes(xCoord, yCoord, zCoord);

		if (Utils.isServerWorld()) {
			PacketUtils.sendToPlayers(getDescriptionPacket(), worldObj, xCoord, yCoord, zCoord, 192);
		}

	}

	public void onNeighborBlockChange() {

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void sendUpdatePacket( Side side ) {
		if ((Utils.isServerWorld()) && (side == Side.CLIENT)) {
			PacketUtils.sendToPlayers(getDescriptionPacket(), worldObj, xCoord, yCoord, zCoord, 192);

			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			worldObj.updateAllLightTypes(xCoord, yCoord, zCoord);
		} else if ((Utils.isClientWorld()) && (side == Side.SERVER)) {
			PacketUtils.sendToServer(getDescriptionPacket());
		}
	}
	
	

	@Override
	public String toString() {
		return "TECableBase [gridindex=" + gridindex + ", xCoord=" + xCoord + ", yCoord=" + yCoord + ", zCoord=" + zCoord + "]";
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (Utils.isClientWorld()) {
			return;
		}
		if (ticks % 2 == 0) {
			if (!foundController()) {
				if (getGrid() != null) {
					getGrid().removeCable(this);
				}
				gridindex = -1;
				setUpdate(true);
			}
			
			if(isUpdate()){
				sendUpdatePacket(Side.CLIENT);
				this.setUpdate(false);
			}

		}
		ticks++;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate( boolean update ) {
		this.update = update;
	}
}
