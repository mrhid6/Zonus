package mrhid6.zonus.tileEntity.machine;

import java.util.ArrayList;
import mrhid6.zonus.interfaces.IConverterObj;
import mrhid6.zonus.interfaces.ILogisticsMachine;
import mrhid6.zonus.interfaces.ITriniumObj;
import mrhid6.zonus.interfaces.IXorGridObj;
import mrhid6.zonus.items.Materials;
import mrhid6.zonus.lib.InventoryUtils;
import mrhid6.zonus.lib.Reference;
import mrhid6.zonus.lib.Utils;
import mrhid6.zonus.lib.ZonusFurnaceRecipe;
import mrhid6.zonus.network.PacketTile;
import mrhid6.zonus.network.Payload;
import mrhid6.zonus.tileEntity.TEMachineBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;

public class TEZoroFurnace extends TEMachineBase implements IXorGridObj, ILogisticsMachine {

	public static int guiPacketId;
	protected static final ArrayList<ZonusFurnaceRecipe> recipes = new ArrayList<ZonusFurnaceRecipe>();

	public static boolean setGuiPacketId( int id ) {
		if (id == 0) {
			return false;
		}
		guiPacketId = id;
		return true;
	}

	private int colour = 0;

	private int mode = 0;
	public int tempEng = 0;

	public TEZoroFurnace() {
		inventory = new ItemStack[2];
		processInv = new ItemStack[1];

		invName = "xor.furnace";

		recipes.add(new ZonusFurnaceRecipe(Materials.ZoroOre, Materials.ZoroIngot, 2));
		recipes.add(new ZonusFurnaceRecipe(Materials.TriniumOre, Materials.TriniumSludge, 2));
		recipes.add(new ZonusFurnaceRecipe(Materials.TriniumSludge, Materials.TriniumIngot, 1));
	}

	private ItemStack addToNetworkedInventory( ItemStack stack ) {
		ItemStack added = InventoryUtils.copyStack(stack, 0);

		if (getGrid() != null) {

			TEZoroChest chest = getGrid().getFirstChestForReciveForItem(this, colour, stack);
			if (chest != null) {
				int injected = 0;

				int slot = -1;
				while ((slot = getPartialSlot(stack, slot + 1, chest.getSizeInventory(), chest)) >= 0 && injected < stack.stackSize) {
					injected += addToSlot(slot, stack, injected, true, chest);
				}

				slot = 0;
				while ((slot = getEmptySlot(0, chest.getSizeInventory(), chest)) >= 0 && injected < stack.stackSize) {
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

	public boolean canFinish() {
		if (processCur < processEnd) {
			return false;
		}

		ItemStack output = getResultFor(processInv[0]);

		if (output == null) {
			processCur = 0.0F;
			isActive = false;
			return true;
		}

		if (inventory[1] == null) {
			return true;
		}
		if (!inventory[1].isItemEqual(output)) {
			return false;
		}
		int result = Integer.valueOf(inventory[1].stackSize) + Integer.valueOf(output.stackSize);
		return (result <= getInventoryStackLimit()) && (result <= output.getMaxStackSize());
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

	public boolean canStart() {

		if (getGrid() == null) {
			return false;
		}

		if (getGrid().getEnergyStored() < (Reference.POWER_GENERATION_RATE * Reference.FURNACE_USEAGE_MULITPLIER)) {
			return false;
		}

		ItemStack output = getResultFor(inventory[0]);

		if (output == null) {
			return false;
		}

		if (inventory[1] == null) {
			return true;
		}

		if (!inventory[1].isItemEqual(output)) {
			return false;
		}

		int result = Integer.valueOf(inventory[1].stackSize) + Integer.valueOf(output.stackSize);
		return (result <= output.getMaxStackSize());
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

	protected int getEmptySlot( int startSlot, int endSlot, TEZoroChest chest ) {
		for (int i = startSlot; i < endSlot; i++) {
			if (chest.getStackInSlot(i) == null) {
				return i;
			}
		}

		return -1;
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

	protected int getPartialSlot( ItemStack stack, int startSlot, int endSlot, TEZoroChest chest ) {

		for (int i = startSlot; i < endSlot; i++) {
			if (chest.getStackInSlot(i) == null) {
				continue;
			}

			if (!chest.getStackInSlot(i).isItemEqual(stack) || !ItemStack.areItemStackTagsEqual(chest.getStackInSlot(i), stack)) {
				continue;
			}

			if (chest.getStackInSlot(i).stackSize >= chest.getStackInSlot(i).getMaxStackSize()) {
				continue;
			}

			return i;
		}

		return -1;
	}

	public ItemStack getResultFor( ItemStack itemstack ) {
		if (itemstack == null) {
			return null;
		}

		ItemStack result = null;
		for (int i = 0; i < recipes.size(); i++) {
			ZonusFurnaceRecipe recipe = recipes.get(i);
			if (itemstack.isItemEqual(recipe.getInput())) {
				result = recipe.getOutput();
			}
		}

		return result;
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

	@Override
	public boolean isStackValidForSlot( int i, ItemStack itemstack ) {
		return false;
	}

	protected void processFinish() {
		ItemStack output = getResultFor(processInv[0]);

		if (inventory[1] == null) {
			inventory[1] = InventoryUtils.copyStack(output, output.stackSize);
		} else {
			inventory[1].stackSize += output.stackSize;
		}

	}

	protected void processStart() {
		processInv[0] = InventoryUtils.copyStack(inventory[0], 1);

		processEnd = 50;

		inventory[0].stackSize -= 1;
		if (inventory[0].stackSize <= 0) {
			inventory[0] = null;
		}
	}

	@Override
	public void readFromNBT( NBTTagCompound data ) {
		super.readFromNBT(data);
		mode = data.getInteger("mode");
		colour = data.getInteger("colour");
	}

	@Override
	public void setGridIndex( int id ) {
		gridindex = id;

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

				if (gridindex != -1) {
					gridindex = -1;
					setUpdate(true);
				}
			}
		}

		boolean curActive = isActive;

		if (isActive) {
			if (processCur < processEnd) {

				if (getGrid() != null) {

					if (getGrid().getEnergyStored() >= (Reference.POWER_GENERATION_RATE * Reference.FURNACE_USEAGE_MULITPLIER)) {
						processCur++;
						getGrid().subtractPower(Reference.POWER_GENERATION_RATE * Reference.FURNACE_USEAGE_MULITPLIER, this);
					}
				}
			}
			if (canFinish()) {
				processFinish();
				processCur = 0.0F;
				processCur = 0.0F;

				if ((canStart())) {
					processStart();
					processCur += 1;
				} else {
					processCur = 0.0F;
					isActive = false;
					wasActive = true;
				}
			}
		} else {
			if (canStart()) {
				processStart();
				processCur += 1;
				isActive = true;
			}
		}

		ejectItemsToNetwork();

		if ((curActive != isActive) && (isActive == true)) {
			this.setUpdate(true);
		} else if ((wasActive)) {
			wasActive = false;
			this.setUpdate(true);
		}

		if (isUpdate()) {
			sendUpdatePacket(Side.CLIENT);
			this.setUpdate(false);
			onInventoryChanged();
		}

		TickSinceUpdate++;
	}

	@Override
	public void writeToNBT( NBTTagCompound data ) {
		super.writeToNBT(data);
		data.setInteger("mode", mode);
		data.setInteger("colour", colour);
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
