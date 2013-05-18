package mrhid6.zonus.tileEntity.machine;

import java.util.List;
import mrhid6.zonus.interfaces.IConverterObj;
import mrhid6.zonus.interfaces.ILogisticsMachine;
import mrhid6.zonus.interfaces.ITriniumObj;
import mrhid6.zonus.interfaces.IXorGridObj;
import mrhid6.zonus.lib.InventoryUtils;
import mrhid6.zonus.lib.Reference;
import mrhid6.zonus.lib.SpiralMatrix;
import mrhid6.zonus.lib.Utils;
import mrhid6.zonus.network.PacketTile;
import mrhid6.zonus.network.Payload;
import mrhid6.zonus.tileEntity.TEMachineBase;
import mrhid6.zonus.tileEntity.TETriniumCable;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;

public class TETriniumMiner extends TEMachineBase implements IXorGridObj, ITriniumObj, ILogisticsMachine {

	protected int colour = 0;
	public int depth;
	public int depthLimit = 0;
	public boolean doneMineing = false;
	protected int inputmode = 0;
	protected int outputmode = 0;
	public int radius = 15;
	public int tempEng = 0;

	public TETriniumMiner() {
		inventory = new ItemStack[2];

		invName = "xor.furnace";
	}

	protected int addToChestSlot( int slot, ItemStack stack, int injected, boolean doAdd, TEZoroChest chest ) {
		int available = stack.stackSize - injected;
		int max = Math.min(stack.getMaxStackSize(), chest.getInventoryStackLimit());

		ItemStack stackInSlot = chest.getStackInSlot(slot);
		if (stackInSlot == null) {
			int wanted = Math.min(available, max);
			if (doAdd) {
				stackInSlot = stack.copy();
				stackInSlot.stackSize = wanted;
				chest.setInventorySlotContents(slot, stackInSlot);
			}
			return wanted;
		}

		if (!stackInSlot.isItemEqual(stack) || !ItemStack.areItemStackTagsEqual(stackInSlot, stack)) {
			return 0;
		}

		int wanted = max - stackInSlot.stackSize;
		if (wanted <= 0) {
			return 0;
		}

		if (wanted > available) {
			wanted = available;
		}

		if (doAdd) {
			stackInSlot.stackSize += wanted;
			chest.setInventorySlotContents(slot, stackInSlot);
		}
		return wanted;
	}

	private ItemStack addToInventory( ItemStack stack ) {
		ItemStack added = InventoryUtils.copyStack(stack, 0);

		int injected = 0;

		int slot = -1;
		while ((slot = InventoryUtils.getPartialSlot(stack, slot + 1, getSizeInventory(), this)) >= 0 && injected < stack.stackSize) {
			injected += addToSlot(slot, stack, injected, true);
		}

		slot = 0;
		while ((slot = InventoryUtils.getEmptySlot(0, getSizeInventory(), this)) >= 0 && injected < stack.stackSize) {
			injected += addToSlot(slot, stack, injected, true);
		}
		onInventoryChanged();

		added.stackSize = injected;

		return added;
	}

	private ItemStack addToNetworkedInventory( ItemStack stack ) {
		ItemStack added = InventoryUtils.copyStack(stack, 0);

		if (getGrid() != null) {

			TEZoroChest chest = getGrid().getFirstChestForReciveForItem(this,colour, stack);
			if (chest != null) {
				int injected = 0;

				int slot = -1;
				while ((slot = InventoryUtils.getPartialChestSlot(stack, slot + 1, chest.getSizeInventory(), chest)) >= 0 && injected < stack.stackSize) {
					injected += addToChestSlot(slot, stack, injected, true, chest);
				}

				slot = 0;
				while ((slot = InventoryUtils.getEmptyChestSlot(0, chest.getSizeInventory(), chest)) >= 0 && injected < stack.stackSize) {
					injected += addToChestSlot(slot, stack, injected, true, chest);
				}
				chest.onInventoryChanged();

				added.stackSize = injected;
			}
		}

		return added;
	}

	protected int addToSlot( int slot, ItemStack stack, int injected, boolean doAdd ) {
		int available = stack.stackSize - injected;
		int max = Math.min(stack.getMaxStackSize(), getInventoryStackLimit());

		ItemStack stackInSlot = getStackInSlot(slot);
		if (stackInSlot == null) {
			int wanted = Math.min(available, max);
			if (doAdd) {
				stackInSlot = stack.copy();
				stackInSlot.stackSize = wanted;
				setInventorySlotContents(slot, stackInSlot);
			}
			return wanted;
		}

		if (!stackInSlot.isItemEqual(stack) || !ItemStack.areItemStackTagsEqual(stackInSlot, stack)) {
			return 0;
		}

		int wanted = max - stackInSlot.stackSize;
		if (wanted <= 0) {
			return 0;
		}

		if (wanted > available) {
			wanted = available;
		}

		if (doAdd) {
			stackInSlot.stackSize += wanted;
			setInventorySlotContents(slot, stackInSlot);
		}
		return wanted;
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
		outputmode--;
		if (outputmode < 0) {
			outputmode = 1;
		}

		sendUpdatePacket(Side.SERVER);
	}

	@Override
	public void alterModeUp() {
		outputmode++;
		outputmode %= 2;

		sendUpdatePacket(Side.SERVER);
	}

	public void breakBlock() {
		dropContent(0);
		if (getGrid() != null) {
			getGrid().removeMachine(this);
		}
	}

	@Override
	public boolean canConnectThrough() {
		return true;
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

	public boolean foundController() {

		if (getGrid() != null) {
			return getGrid().hasMachine(this) && getGrid().canDiscoverObj(this);
		}
		return false;
	}

	@Override
	public Packet getDescriptionPacket() {
		Payload payload = new Payload(3, 1, 7, 2, 0);

		payload.boolPayload[0] = isActive;
		payload.boolPayload[1] = transmitpower;
		payload.boolPayload[2] = doneMineing;

		payload.intPayload[0] = gridindex;
		payload.intPayload[1] = colour;
		payload.intPayload[2] = depth;
		payload.intPayload[3] = depthLimit;
		payload.intPayload[4] = radius;
		payload.intPayload[5] = inputmode;
		payload.intPayload[6] = outputmode;

		payload.floatPayload[0] = processCur;
		payload.floatPayload[1] = processEnd;

		payload.bytePayload[0] = (byte) getFacing();

		PacketTile packet = new PacketTile(descPacketId, xCoord, yCoord, zCoord, payload);
		return packet.getPacket();
	}

	@Override
	public int getMode() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getPower() {
		return Reference.TMINER_USEAGE_MULITPLIER * Reference.POWER_GENERATION_RATE;
	}

	@Override
	public void handleTilePacket( PacketTile packet ) {

		doneMineing = packet.payload.boolPayload[2];
		colour = packet.payload.intPayload[1];
		depth = packet.payload.intPayload[2];
		depthLimit = packet.payload.intPayload[3];
		radius = packet.payload.intPayload[4];
		inputmode = packet.payload.intPayload[5];
		outputmode = packet.payload.intPayload[6];

		if (Utils.isClientWorld()) {
			doneMineing = packet.payload.boolPayload[2];
			colour = packet.payload.intPayload[1];
			depth = packet.payload.intPayload[2];
			depthLimit = packet.payload.intPayload[3];
			radius = packet.payload.intPayload[4];
			inputmode = packet.payload.intPayload[5];
			outputmode = packet.payload.intPayload[6];
		}
		super.handleTilePacket(packet);
	}

	@Override
	public void init() {
		depth = yCoord - 1;
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

	public void mineBlock( int i, int j, int k, int blockId ) {
		List<ItemStack> stacks = Utils.getItemStackFromBlock(worldObj, i, j, k);

		if (stacks != null) {
			for (ItemStack s : stacks) {
				if (s != null) {
					mineStack(s);
				}
			}
		}

		worldObj.playAuxSFXAtEntity(null, 2001, i, j, k, blockId + (worldObj.getBlockMetadata(i, j, k) << 12));
		worldObj.setBlock(i, j, k, 0);
	}

	public boolean minedLevel() {

		String[] coord_mod = SpiralMatrix.makeCoords(radius);
		int[] ar1 = SpiralMatrix.spiralArray(radius);

		for (int i = 0; i < ar1.length; i++) {

			int coordid = -1 + ar1[i];
			String[] coords = coord_mod[coordid].split(",");
			// System.out.println(ar1[i]);
			// System.out.println("("+coords[0]+","+coords[1]+")");
			int x = Integer.parseInt(coords[0]) + xCoord;
			int z = Integer.parseInt(coords[1]) + zCoord;

			if (shouldMineBlock(worldObj.getBlockId(x, depth, z))) {
				return false;
			}

		}

		return true;
	}

	public void mineLevel() {

		if (minedLevel()) {
			depth--;
		}

		String[] coord_mod = SpiralMatrix.makeCoords(radius);
		int[] ar1 = SpiralMatrix.spiralArray(radius);

		for (int i = 0; i < ar1.length; i++) {

			int coordid = -1 + ar1[i];
			String[] coords = coord_mod[coordid].split(",");
			// System.out.println(ar1[i]);
			// System.out.println("("+coords[0]+","+coords[1]+")");
			int x = Integer.parseInt(coords[0]) + xCoord;
			int z = Integer.parseInt(coords[1]) + zCoord;

			if (depth == depthLimit) {
				doneMineing = true;
				return;
			}

			int blockID = worldObj.getBlockId(x, depth, z);

			if (shouldMineBlock(blockID)) {

				if (getGrid() != null && getGrid().getEnergyStored() >= getPower()) {
					getGrid().subtractPower(getPower(), this);
					mineBlock(x, depth, z, worldObj.getBlockId(x, depth, z));
				}

				return;
			}

		}
	}

	private void mineStack( ItemStack stack ) {

		if (useLogistics()) {
			ItemStack added = addToNetworkedInventory(stack);
			stack.stackSize -= added.stackSize;
		} else {
			ItemStack added = addToInventory(stack);
			stack.stackSize -= added.stackSize;
		}

		if (stack.stackSize > 0) {
			float f = worldObj.rand.nextFloat() * 0.8F + 0.1F;
			float f1 = worldObj.rand.nextFloat() * 0.8F + 0.1F;
			float f2 = worldObj.rand.nextFloat() * 0.8F + 0.1F;

			EntityItem entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1 + 0.5F, zCoord + f2, stack);

			entityitem.lifespan = 5200;
			entityitem.delayBeforeCanPickup = 10;

			float f3 = 0.05F;
			entityitem.motionX = (float) worldObj.rand.nextGaussian() * f3;
			entityitem.motionY = (float) worldObj.rand.nextGaussian() * f3 + 0.5F;
			entityitem.motionZ = (float) worldObj.rand.nextGaussian() * f3;
			worldObj.spawnEntityInWorld(entityitem);
		}
	}

	@Override
	public void readFromNBT( NBTTagCompound data ) {
		super.readFromNBT(data);

		depth = data.getInteger("depth");
		depthLimit = data.getInteger("depthLimit");
		radius = data.getInteger("radius");
		colour = data.getInteger("colour");
		outputmode = data.getInteger("outputmode");
		inputmode = data.getInteger("inputmode");
		doneMineing = data.getBoolean("donemining");

	}

	@Override
	public void setGridIndex( int id ) {
		gridindex = id;

	}

	@Override
	public void setMode( int mode ) {
		// TODO Auto-generated method stub

	}

	public boolean shouldMineBlock( int blockID ) {

		if (blockID == 0) {
			return false;
		}
		if (blockID == Block.bedrock.blockID) {
			return false;
		}
		if (blockID == Block.waterStill.blockID) {
			return false;
		}
		if (blockID == Block.waterMoving.blockID) {
			return false;
		}
		if (blockID == Block.lavaStill.blockID) {
			return false;
		}
		if (blockID == Block.lavaMoving.blockID) {
			return false;
		}

		Block b = Block.blocksList[blockID];

		if (b.getBlockHardness(null, 0, 0, 0) == -1.0F) {
			return false;
		}

		return true;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (Utils.isClientWorld()) {
			return;
		}

		if (doneMineing == true) {
			// System.out.println("im done mining");
			return;
		}

		if (getGrid() != null && getGrid().getEnergyStored() >= 30) {
			mineLevel();
		}

		if (!foundController()) {
			if (getGrid() != null) {
				getGrid().removeMachine(this);
			}
			if (gridindex != -1) {
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

	public boolean useLogistics() {

		if (outputmode == 0) {
			return true;
		}

		return false;
	}

	@Override
	public void writeToNBT( NBTTagCompound data ) {
		super.writeToNBT(data);

		data.setInteger("depth", depth);
		data.setInteger("depthLimit", depthLimit);
		data.setInteger("radius", radius);
		data.setInteger("colour", colour);
		data.setInteger("outputmode", outputmode);
		data.setInteger("inputmode", inputmode);

		data.setBoolean("donemining", doneMineing);
	}

	@Override
	public int[] getAccessibleSlotsFromSide( int var1 ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem( int i, ItemStack itemstack, int j ) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem( int i, ItemStack itemstack, int j ) {
		// TODO Auto-generated method stub
		return false;
	}

}
