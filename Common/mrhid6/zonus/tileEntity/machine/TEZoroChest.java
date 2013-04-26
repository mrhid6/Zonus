package mrhid6.zonus.tileEntity.machine;

import java.util.Iterator;
import java.util.List;
import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.gui.ContainerZoroChest;
import mrhid6.zonus.interfaces.IConverterObj;
import mrhid6.zonus.interfaces.ILogisticsMachine;
import mrhid6.zonus.interfaces.IPacketXorHandler;
import mrhid6.zonus.interfaces.ITriniumObj;
import mrhid6.zonus.interfaces.IXorGridObj;
import mrhid6.zonus.lib.Utils;
import mrhid6.zonus.network.PacketTile;
import mrhid6.zonus.network.PacketUtils;
import mrhid6.zonus.network.Payload;
import mrhid6.zonus.tileEntity.TEBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import cpw.mods.fml.relauncher.Side;

public class TEZoroChest extends TEBlock implements ISidedInventory, IPacketXorHandler, IXorGridObj, ILogisticsMachine {

	protected static int descPacketId;
	private int colour = 0;
	private byte facing = 3;
	public float lidAngle;

	private int mode = 0;
	public int numUsingPlayers;

	public float prevLidAngle;
	private int ticksSinceSync;
	private boolean update = false;

	public TEZoroChest() {
		inventory = new ItemStack[getSizeInventory()];
	}

	public void alterColour() {
		colour++;
		colour %= 17;

		sendUpdatePacket(Side.SERVER);
	}

	public void alterColourBack() {
		colour--;

		if (colour < 0) {
			colour = 16;
		}

		sendUpdatePacket(Side.SERVER);
	}

	@Override
	public void alterModeDown() {
		mode--;
		if (mode < 0) {
			mode = 3;
		}

		sendUpdatePacket(Side.SERVER);
	}

	@Override
	public void alterModeUp() {
		mode++;
		mode %= 4;

		sendUpdatePacket(Side.SERVER);
	}

	public void breakBlock() {
		dropContent(0);
		if (getGrid() != null) {
			getGrid().removeChest(this);
		}
	}

	@Override
	public boolean canInteractWith( TileEntity te ) {
		if (te instanceof IConverterObj) {
			return false;
		}
		if (te instanceof ITriniumObj) {
			return false;
		}

		return true;
	}

	@Override
	public void closeChest() {
		if (worldObj == null) {
			return;
		}
		numUsingPlayers--;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.zoroChest.blockID, 1, numUsingPlayers);
	}

	@Override
	public ItemStack decrStackSize( int par1, int par2 ) {
		if (inventory[par1] != null) {
			ItemStack itemstack;

			if (inventory[par1].stackSize <= par2) {
				itemstack = inventory[par1];
				inventory[par1] = null;
				this.onInventoryChanged();
				return itemstack;
			} else {
				itemstack = inventory[par1].splitStack(par2);

				if (inventory[par1].stackSize == 0) {
					inventory[par1] = null;
				}

				this.onInventoryChanged();
				return itemstack;
			}
		} else {
			return null;
		}
	}

	public boolean foundController() {

		if (getGrid() != null) {
			return getGrid().hasChest(this) && getGrid().canDiscoverObj(this);
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

	public int getColour() {
		return colour;
	}

	public String getColourText() {
		if (colour == 0) {
			return "Colour Not Set";
		}

		return Utils.ColourName[colour - 1];
	}

	@Override
	public Packet getDescriptionPacket() {
		Payload payload = new Payload(0, 1, 4, 2, 0);

		// System.out.println(gridindex);

		payload.bytePayload[0] = facing;

		payload.floatPayload[0] = lidAngle;
		payload.floatPayload[1] = prevLidAngle;

		payload.intPayload[0] = numUsingPlayers;
		payload.intPayload[1] = gridindex;
		payload.intPayload[2] = mode;
		payload.intPayload[3] = colour;

		PacketTile packet = new PacketTile(descPacketId, xCoord, yCoord, zCoord, payload);
		return packet.getPacket();
	}

	public byte getFacing() {
		return facing;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public String getInvName() {
		return "xor.chest";
	}

	@Override
	public int getMode() {
		return mode;
	}

	public String getModeText() {

		switch (getMode()) {
		case 0:
			return "Send And Recive Items";
		case 1:
			return "Send Items Only";
		case 2:
			return "Recive Items Only";
		case 3:
			return "Disabled";
		}

		return "";
	}

	public int getRowCount() {
		return 8;
	}

	public int getRowLength() {
		return 12;
	}

	@Override
	public int getSizeInventory() {
		return getRowCount() * getRowLength();
	}

	@Override
	public int[] getSizeInventorySide( int var1 ) {
		return null;
	}

	@Override
	public ItemStack getStackInSlot( int i ) {
		return inventory[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing( int i ) {
		if (inventory[i] != null) {
			ItemStack itemstack = inventory[i];
			inventory[i] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void handleTilePacket( PacketTile packet ) {

		facing = packet.payload.bytePayload[0];

		lidAngle = packet.payload.floatPayload[0];
		prevLidAngle = packet.payload.floatPayload[1];

		numUsingPlayers = packet.payload.intPayload[0];
		gridindex = packet.payload.intPayload[1];
		mode = packet.payload.intPayload[2];
		colour = packet.payload.intPayload[3];

		if (Utils.isClientWorld()) {
			facing = packet.payload.bytePayload[0];

			lidAngle = packet.payload.floatPayload[0];
			prevLidAngle = packet.payload.floatPayload[1];

			numUsingPlayers = packet.payload.intPayload[0];
			gridindex = packet.payload.intPayload[1];
			mode = packet.payload.intPayload[2];
			colour = packet.payload.intPayload[3];

			// System.out.println("hadlepacket"+gridindex);
		}

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		worldObj.updateAllLightTypes(xCoord, yCoord, zCoord);

		if (Utils.isServerWorld()) {
			PacketUtils.sendToPlayers(getDescriptionPacket(), worldObj, xCoord, yCoord, zCoord, 192);
		}

	}

	@Override
	public void init() {
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isStackValidForSlot( int i, ItemStack itemstack ) {

		return true;
	}

	public boolean isUpdate() {
		return update;
	}

	@Override
	public boolean isUseableByPlayer( EntityPlayer entityplayer ) {
		if (worldObj == null) {
			return true;
		}
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		return entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64D;

	}

	@Override
	public void openChest() {
		if (worldObj == null) {
			return;
		}
		numUsingPlayers++;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.zoroChest.blockID, 1, numUsingPlayers);
	}

	@Override
	public void readFromNBT( NBTTagCompound data ) {
		super.readFromNBT(data);

		facing = data.getByte("facing");
		mode = data.getInteger("mode");
		colour = data.getInteger("colour");
	}

	@Override
	public boolean receiveClientEvent( int i, int j ) {
		if (i == 1) {
			numUsingPlayers = j;
		} else if (i == 2) {
			facing = (byte) j;
		} else if (i == 3) {
			facing = (byte) (j & 0x7);
			numUsingPlayers = (j & 0xF8) >> 3;
		}
		return true;
	}

	public void receiveGuiNetworkData( int i, int j ) {
	}

	public void sendGuiNetworkData( Container container, ICrafting iCrafting ) {
		if (((iCrafting instanceof EntityPlayer)) && (Utils.isServerWorld())) {
			PacketUtils.sendToPlayer((EntityPlayer) iCrafting, getDescriptionPacket());
		}
	}

	public void setFacing( byte facing ) {
		this.facing = facing;
	}

	@Override
	public void setInventorySlotContents( int par1, ItemStack par2ItemStack ) {
		inventory[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public void setMode( int mode ) {
	}

	public void setUpdate( boolean update ) {
		this.update = update;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		// Resynchronize clients with the server state
		if (worldObj != null && !worldObj.isRemote && numUsingPlayers != 0 && (ticksSinceSync + xCoord + yCoord + zCoord) % 200 == 0) {
			numUsingPlayers = 0;
			float var1 = 5.0F;
			List var2 = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB(xCoord - var1, yCoord - var1, zCoord - var1, xCoord + 1 + var1, yCoord + 1 + var1, zCoord + 1 + var1));
			Iterator var3 = var2.iterator();

			while (var3.hasNext()) {
				EntityPlayer var4 = (EntityPlayer) var3.next();

				if (var4.openContainer instanceof ContainerZoroChest) {
					++numUsingPlayers;
				}
			}
		}

		if (worldObj != null && !worldObj.isRemote && ticksSinceSync < 0) {
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.zoroChest.blockID, 3, ((numUsingPlayers << 3) & 0xF8) | (facing & 0x7));
		}

		ticksSinceSync++;
		prevLidAngle = lidAngle;
		float f = 0.1F;
		if (numUsingPlayers > 0 && lidAngle == 0.0F) {
			double d = xCoord + 0.5D;
			double d1 = zCoord + 0.5D;
			worldObj.playSoundEffect(d, yCoord + 0.5D, d1, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
		if (numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F) {
			float f1 = lidAngle;
			if (numUsingPlayers > 0) {
				lidAngle += f;
			} else {
				lidAngle -= f;
			}
			if (lidAngle > 1.0F) {
				lidAngle = 1.0F;
			}
			float f2 = 0.5F;
			if (lidAngle < f2 && f1 >= f2) {
				double d2 = xCoord + 0.5D;
				double d3 = zCoord + 0.5D;
				worldObj.playSoundEffect(d2, yCoord + 0.5D, d3, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
			if (lidAngle < 0.0F) {
				lidAngle = 0.0F;
			}
		}

		if (Utils.isClientWorld()) {
			return;
		}

		if ((TickSinceUpdate % 5) == 0) {
			if (!foundController()) {
				if (getGrid() != null) {
					getGrid().removeChest(this);
				}
				gridindex = -1;
				setUpdate(true);
			}
		}

		if (isUpdate()) {
			sendUpdatePacket(Side.CLIENT);
			this.setUpdate(false);
		}

		TickSinceUpdate++;
	}

	@Override
	public void writeToNBT( NBTTagCompound data ) {
		super.writeToNBT(data);
		data.setByte("facing", facing);
		data.setInteger("mode", mode);
		data.setInteger("colour", colour);
	}
}
