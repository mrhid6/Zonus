package mrhid6.zonus.tileEntity.machine;

import java.util.ArrayList;
import java.util.HashMap;
import mrhid6.zonus.Config;
import mrhid6.zonus.interfaces.IConverterObj;
import mrhid6.zonus.interfaces.ILogisticsMachine;
import mrhid6.zonus.interfaces.ITriniumObj;
import mrhid6.zonus.interfaces.IXorGridObj;
import mrhid6.zonus.lib.InventoryUtils;
import mrhid6.zonus.lib.Utils;
import mrhid6.zonus.network.PacketTile;
import mrhid6.zonus.network.Payload;
import mrhid6.zonus.tileEntity.TEMachineBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;

public class TETriniumPlanter extends TEMachineBase implements IXorGridObj, ILogisticsMachine, ITriniumObj {

	protected static final HashMap<Integer, ItemStack> recipes = new HashMap<Integer, ItemStack>();

	private int colour = 0;

	private int mode = 0;
	public boolean placedSappings = false;
	public int tempEng = 0;

	public int timeTillLogger = 8000;

	public TETriniumPlanter() {
		inventory = new ItemStack[2];
		processInv = new ItemStack[1];

		invName = "trin.planter";
	}

	private ItemStack addToNetworkedInventory( ItemStack stack ) {
		ItemStack added = InventoryUtils.copyStack(stack, 0);

		if (getGrid() != null) {

			TEZoroChest chest = getGrid().getFirstChestForReciveForItem(colour, stack);
			if (chest != null) {
				int injected = 0;

				int slot = -1;
				while ((slot = InventoryUtils.getPartialSlot(stack, slot + 1, chest.getSizeInventory(), chest)) >= 0 && injected < stack.stackSize) {
					injected += addToSlot(slot, stack, injected, true, chest);
				}

				slot = 0;
				while ((slot = InventoryUtils.getEmptySlot(0, chest.getSizeInventory(), chest)) >= 0 && injected < stack.stackSize) {
					injected += addToSlot(slot, stack, injected, true, chest);
				}
				chest.onInventoryChanged();

				added.stackSize = injected;
			}
		}

		return added;
	}

	protected int addToSlot( int slot, ItemStack stack, int injected, boolean doAdd, TEZoroChest chest ) {
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
			mode = 1;
		}

		sendUpdatePacket(Side.SERVER);
	}

	@Override
	public void alterModeUp() {
		mode++;
		mode %= 2;

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
		if (te instanceof ITriniumObj) {
			return false;
		}

		return true;
	}

	public boolean canPlantSappling( int x, int z ) {

		boolean res = true;

		int id = worldObj.getBlockId(x, yCoord, z);
		if (worldObj.getBlockId(x, yCoord - 1, z) != Block.grass.blockID && worldObj.getBlockId(x, yCoord - 1, z) != Block.dirt.blockID) {
			res = false;
		}
		if (id != 0 && id != Block.snow.blockID && id != Block.tallGrass.blockID) {
			res = false;
		}
		if (worldObj.getBlockId(x, yCoord + 1, z) != 0) {
			res = false;
		}

		return res;
	}

	public int countSaplingsLeft() {

		int count = 0;
		for (int x = xCoord - 7; x < xCoord + 7; x++) {
			for (int z = zCoord - 7; z < zCoord + 7; z++) {

				int id = worldObj.getBlockId(x, yCoord, z);

				if (isSapling(id)) {
					count++;
				}
			}
		}

		return count;

	}

	public void ejectItemsToNetwork() {

		if (mode == 0 || inventory[1] == null) {
			return;
		}

		ItemStack added = addToNetworkedInventory(inventory[1]);
		inventory[1].stackSize -= added.stackSize;
		if (inventory[1].stackSize <= 0) {
			inventory[1] = null;
		}
	}

	public boolean foundController() {

		if (getGrid() != null) {
			return getGrid().hasMachine(this) && getGrid().canDiscoverObj(this);
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
		Payload payload = new Payload(2, 1, 3, 2, 0);

		payload.boolPayload[0] = isActive;
		payload.boolPayload[1] = transmitpower;

		payload.intPayload[0] = gridindex;
		payload.intPayload[1] = mode;
		payload.intPayload[2] = colour;

		payload.floatPayload[0] = processCur;
		payload.floatPayload[1] = processEnd;

		payload.bytePayload[0] = (byte) getFacing();

		PacketTile packet = new PacketTile(descPacketId, xCoord, yCoord, zCoord, payload);
		return packet.getPacket();
	}

	public TENoxiteLogger getLoggerAdjacent() {
		for (int i = 0; i < 6; i++) {

			int x1 = xCoord + Config.SIDE_COORD_MOD[i][0];
			int y1 = yCoord + Config.SIDE_COORD_MOD[i][1];
			int z1 = zCoord + Config.SIDE_COORD_MOD[i][2];

			TileEntity te = worldObj.getBlockTileEntity(x1, y1, z1);

			if (te instanceof TENoxiteLogger) {

				return (TENoxiteLogger) te;
			}
		}

		return null;
	}

	@Override
	public int getMode() {
		return mode;
	}

	public String getModeText() {

		switch (getMode()) {
		case 0:
			return "Eject Items - Off";
		case 1:
			return "Eject Items - On";
		}

		return "";
	}

	public ItemStack getResultFor( ItemStack itemstack ) {
		if (itemstack == null) {
			return null;
		}
		ItemStack item = recipes.get(itemstack.itemID);
		return (item == null) ? null : InventoryUtils.copyStack(item, item.stackSize);
	}

	@Override
	public int getScaledProgress( int scale ) {
		if (processEnd == 0.0F) {
			return 0;
		}
		return (int) (processCur * scale / processEnd);
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public int[] getSizeInventorySide( int var1 ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleTilePacket( PacketTile packet ) {
		mode = packet.payload.intPayload[1];
		colour = packet.payload.intPayload[2];

		if (Utils.isClientWorld()) {
			mode = packet.payload.intPayload[1];
			colour = packet.payload.intPayload[2];
		}
		super.handleTilePacket(packet);
	}

	@Override
	public void init() {

	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	public boolean isSapling( int id ) {

		ArrayList<ItemStack> saplings = OreDictionary.getOres("treeSapling");

		for (ItemStack stack : saplings) {

			if (stack.itemID == id) {
				return true;
			}
		}

		return (id == Block.sapling.blockID);
	}

	@Override
	public boolean isStackValidForSlot( int i, ItemStack itemstack ) {
		return false;
	}

	public void placeSaplings() {
		boolean xSpace = false;
		boolean zSpace = false;

		int count = 0;

		for (int x = xCoord - 7; x < xCoord + 7; x++) {
			if (xSpace) {
				xSpace = false;
				continue;
			}
			for (int z = zCoord - 7; z < zCoord + 7; z++) {

				if (zSpace) {
					zSpace = false;
					continue;
				}
				if (canPlantSappling(x, z)) {
					worldObj.setBlock(x, yCoord, z, Block.sapling.blockID);
					worldObj.markBlockForUpdate(x, yCoord, z);
					return;
				}

				zSpace = true;
				count++;
			}
			xSpace = true;
		}

		if (count == 49) {
			// System.out.println("im done placing");
			placedSappings = true;
		}
	}

	public void quickGrowAll() {
		for (int x = xCoord - 7; x < xCoord + 7; x++) {
			for (int z = zCoord - 7; z < zCoord + 7; z++) {

				if (worldObj.getBlockId(x, yCoord, z) == Block.sapling.blockID) {

					((BlockSapling) Block.sapling).growTree(worldObj, x, yCoord, z, worldObj.rand);
				}
			}
		}
	}

	public void randomGrow() {
		for (int x = xCoord - 7; x < xCoord + 7; x++) {
			for (int z = zCoord - 7; z < zCoord + 7; z++) {

				if (worldObj.rand.nextInt(100000) < 99000) {
					continue;
				}

				if (worldObj.getBlockId(x, yCoord, z) == Block.sapling.blockID) {

					((BlockSapling) Block.sapling).growTree(worldObj, x, yCoord, z, worldObj.rand);
					return;
				}
			}
		}
	}

	@Override
	public void readFromNBT( NBTTagCompound data ) {
		super.readFromNBT(data);
		mode = data.getInteger("mode");
		colour = data.getInteger("colour");
	}

	@Override
	public void setMode( int mode ) {
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (Utils.isClientWorld()) {
			return;
		}

		if (TickSinceUpdate % 2 == 0) {
			if (!foundController()) {
				if (getGrid() != null) {
					getGrid().removeMachine(this);
				}
				gridindex = -1;
				setUpdate(true);
			}

		}

		if (TickSinceUpdate % 8 == 0) {
			if (!placedSappings) {
				placeSaplings();
			}

			quickGrowAll();

			if (countSaplingsLeft() <= 5) {
				TENoxiteLogger te = getLoggerAdjacent();

				boolean canPlace = true;
				if (te != null) {
					canPlace = te.doneMineing;
				}
				if (canPlace) {
					placedSappings = false;
				}
			}

			if (countSaplingsLeft() <= 5 && timeTillLogger <= 0) {
				TENoxiteLogger te = getLoggerAdjacent();

				if (te != null) {
					te.resetLogger();
					// System.out.println("te found");
				}

				timeTillLogger = 8000;
			} else {

				timeTillLogger -= 8;
			}

			// System.out.println(timeTillLogger);
		}

		// ejectItemsToNetwork();

		if (isUpdate()) {
			sendUpdatePacket(Side.CLIENT);
			this.setUpdate(false);
		}
		onInventoryChanged();

		TickSinceUpdate++;
	}

	@Override
	public void writeToNBT( NBTTagCompound data ) {
		super.writeToNBT(data);
		data.setInteger("mode", mode);
		data.setInteger("colour", colour);
	}
}
