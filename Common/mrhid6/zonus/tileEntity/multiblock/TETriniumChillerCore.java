package mrhid6.zonus.tileEntity.multiblock;

import mrhid6.zonus.Utils;
import mrhid6.zonus.network.PacketTile;
import mrhid6.zonus.network.PacketUtils;
import mrhid6.zonus.network.Payload;
import mrhid6.zonus.tileEntity.TEMachineBase;
import net.minecraft.block.Block;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

public class TETriniumChillerCore extends TEMachineBase implements ITankContainer {

	public static boolean setDescPacketId( int id ) {
		if (id == 0) {
			return false;
		}
		descPacketId = id;
		return true;
	}

	public LiquidTank myTank = new LiquidTank(Block.waterStill.blockID, 0, 4000);

	public TETriniumChillerCore() {
		inventory = new ItemStack[1];
		processInv = new ItemStack[0];

		invName = "trin.chiller";
	}

	@Override
	public boolean canConnectThrough() {
		return false;
	}

	/*
	 * @Override protected boolean canFinish() { if(this.processCur <
	 * this.processEnd){ return false; }
	 * 
	 * if(this.inventory[0]==null){ return true; }
	 * 
	 * ItemStack output = (new ItemStack(Block.ice)).copy();
	 * 
	 * if(!this.inventory[0].isItemEqual(output)){ return false; }
	 * 
	 * int result = Integer.valueOf(this.inventory[0].stackSize) +
	 * Integer.valueOf(output.stackSize); return (result <=
	 * getInventoryStackLimit()) && (result <= output.getMaxStackSize()); }
	 */

	/*
	 * @Override protected boolean canStart() {
	 * 
	 * if(this.myProvider.getEnergyStored() < this.PowerNeeded()){ return false;
	 * }
	 * 
	 * if(this.myTank.getLiquid().amount < 1000){ return false; } return true; }
	 */

	@Override
	public boolean canInteractWith( TileEntity te ) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LiquidStack drain( ForgeDirection from, int maxDrain, boolean doDrain ) {
		return null;
	}

	@Override
	public LiquidStack drain( int tankIndex, int maxDrain, boolean doDrain ) {
		return null;
	}

	@Override
	public int fill( ForgeDirection from, LiquidStack resource, boolean doFill ) {
		return myTank.fill(resource, doFill);
	}

	@Override
	public int fill( int tankIndex, LiquidStack resource, boolean doFill ) {
		return 0;
	}

	@Override
	public boolean func_102007_a( int i, ItemStack itemstack, int j ) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean func_102008_b( int i, ItemStack itemstack, int j ) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Packet getDescriptionPacket() {
		Payload payload = new Payload(0, 0, 1, 0, 0);

		payload.intPayload[0] = myTank.getLiquid().amount;

		PacketTile packet = new PacketTile(descPacketId, xCoord, yCoord, zCoord, payload);
		return packet.getPacket();
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public int[] getSizeInventorySide( int var1 ) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @Override protected void processFinish() {
	 * 
	 * ItemStack output = (new ItemStack(Block.ice)).copy();
	 * 
	 * if (this.inventory[0] == null) this.inventory[0] = output; else {
	 * this.inventory[0].stackSize += output.stackSize; }
	 * 
	 * }
	 */

	/*
	 * @Override protected void processStart() { this.processEnd =
	 * this.PowerNeeded();
	 * 
	 * myTank.getLiquid().amount -= 1000;
	 * 
	 * }
	 */

	@Override
	public ILiquidTank getTank( ForgeDirection direction, LiquidStack type ) {
		if (direction == ForgeDirection.UNKNOWN) {
			System.out.println("nulltank1!");
			return null;
		}
		return myTank;
	}

	@Override
	public ILiquidTank[] getTanks( ForgeDirection direction ) {
		if (direction == ForgeDirection.UNKNOWN) {
			System.out.println("nulltank2!");
			return new ILiquidTank[0];
		}
		return new ILiquidTank[] { myTank };
	}

	@Override
	public void handleTilePacket( PacketTile packet ) {

		myTank.getLiquid().amount = packet.payload.intPayload[0];

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		worldObj.updateAllLightTypes(xCoord, yCoord, zCoord);

		if (Utils.isServerWorld()) {
			PacketUtils.sendToPlayers(getDescriptionPacket(), worldObj, xCoord, yCoord, zCoord, 192);
		}

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

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

	public float PowerNeeded() {
		return 160F;
	}

	@Override
	public void readFromNBT( NBTTagCompound data ) {
		super.readFromNBT(data);

		myTank.getLiquid().amount = data.getInteger("qty.water");
	}

	@Override
	public void receiveGuiNetworkData( int i, int j ) {

	}

	@Override
	public void sendGuiNetworkData( Container container, ICrafting iCrafting ) {
		super.sendGuiNetworkData(container, iCrafting);
	}

	@Override
	public void writeToNBT( NBTTagCompound data ) {
		super.writeToNBT(data);
		data.setInteger("qty.water", myTank.getLiquid().amount);

	}

}
