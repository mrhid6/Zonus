package mrhid6.zonus.tileEntity.machine;

import java.util.ArrayList;
import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.interfaces.IConverterObj;
import mrhid6.zonus.interfaces.ISidedBlock;
import mrhid6.zonus.interfaces.ITriniumObj;
import mrhid6.zonus.items.Materials;
import mrhid6.zonus.lib.CrystalForgeRecipe;
import mrhid6.zonus.lib.InventoryUtils;
import mrhid6.zonus.lib.Reference;
import mrhid6.zonus.lib.Utils;
import mrhid6.zonus.network.PacketTile;
import mrhid6.zonus.network.PacketUtils;
import mrhid6.zonus.network.Payload;
import mrhid6.zonus.tileEntity.TEMachineBase;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;

public class TECrystalForge extends TEMachineBase implements ISidedBlock {

	protected static final ArrayList<CrystalForgeRecipe> recipes = new ArrayList<CrystalForgeRecipe>();
	float rot = 0;
	public float sizeIncreased = -2.5F;

	public TECrystalForge() {

		inventory = new ItemStack[getSizeInventory()];
		processInv = new ItemStack[3];
		invName = "crystal.forge";

		recipes.add(new CrystalForgeRecipe(Materials.NoxiteCystal, new ItemStack(ModBlocks.stearilliumGlass,1), new ItemStack(ModBlocks.stearilliumGlass,1), Materials.TriniumSludge, 1,380.0F));
	}

	@Override
	public boolean canConnectOnSide( int side ) {
		if (side == 1) {
			return false;
		}

		return true;
	}

	@Override
	public boolean canConnectThrough() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem( int i, ItemStack itemstack, int j ) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canInsertItem( int i, ItemStack itemstack, int j ) {
		// TODO Auto-generated method stub
		return false;
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

	public boolean decreaseSize() {
		float before = sizeIncreased;
		sizeIncreased -= 0.01F;
		if (sizeIncreased < -2.5F) {
			sizeIncreased = -2.5F;
		}

		return (before != sizeIncreased);
	}

	public boolean foundController() {

		if (getGrid() != null) {
			return getGrid().hasMachine(this) && getGrid().canDiscoverObj(this);
		}

		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide( int var1 ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Packet getDescriptionPacket() {
		Payload payload = new Payload(2, 1, 1, 3, 0);

		payload.boolPayload[0] = isActive;
		payload.boolPayload[1] = transmitpower;

		payload.intPayload[0] = gridindex;

		payload.floatPayload[0] = processCur;
		payload.floatPayload[1] = processEnd;
		payload.floatPayload[2] = sizeIncreased;

		payload.bytePayload[0] = (byte) facing;

		PacketTile packet = new PacketTile(descPacketId, xCoord, yCoord, zCoord, payload);
		return packet.getPacket();
	}

	@Override
	public int getSizeInventory() {
		return 4;
	}

	@Override
	public void handleTilePacket( PacketTile packet ) {
		isActive = packet.payload.boolPayload[0];
		transmitpower = packet.payload.boolPayload[0];

		processCur = packet.payload.floatPayload[0];
		processEnd = packet.payload.floatPayload[1];
		sizeIncreased = packet.payload.floatPayload[2];

		gridindex = packet.payload.intPayload[0];
		facing = packet.payload.bytePayload[0];

		if (Utils.isClientWorld()) {
			processCur = packet.payload.floatPayload[0];
			processEnd = packet.payload.floatPayload[1];
			sizeIncreased = packet.payload.floatPayload[2];
			gridindex = packet.payload.intPayload[0];
			facing = packet.payload.bytePayload[0];
			// System.out.println(gridindex);
		}

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		worldObj.updateAllLightTypes(xCoord, yCoord, zCoord);

		if (Utils.isServerWorld()) {
			PacketUtils.sendToPlayers(getDescriptionPacket(), worldObj, xCoord, yCoord, zCoord, 192);
		}
	}

	public boolean increaseSize() {
		float before = sizeIncreased;
		sizeIncreased += 0.005F;
		if (sizeIncreased >= -0.5F) {
			sizeIncreased = -0.5F;
		}

		return (before != sizeIncreased);
	}

	@Override
	public void init() {

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

	@Override
	public void setGridIndex( int id ) {
		gridindex = id;
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
		
		sizeIncreased =-2.5F+((getScaledProgress(100)/100.0F)*2);
		setUpdate(true);

		if (isUpdate()) {
			sendUpdatePacket(Side.CLIENT);
			this.setUpdate(false);
		}
	}
	
	protected void processFinish() {
		ItemStack output = getRecipeResult(true);
		if(output!=null){ 
			if (inventory[3] == null) {
				inventory[3] = InventoryUtils.copyStack(output, output.stackSize);
			} else {
				inventory[3].stackSize += output.stackSize;
			}
		}

	}
	
	protected void processStart() {

		processEnd = getEndTime(getRecipeResult(false));
		
		processInv[0] = InventoryUtils.copyStack(inventory[0], 1);
		processInv[1] = InventoryUtils.copyStack(inventory[1], 1);
		processInv[2] = InventoryUtils.copyStack(inventory[2], 1);
		
		inventory[0].stackSize -= 1;
		inventory[1].stackSize -= 1;
		inventory[2].stackSize -= 1;
		
		if (inventory[0].stackSize <= 0) {
			inventory[0] = null;
		}
		if (inventory[1].stackSize <= 0) {
			inventory[1] = null;
		}
		if (inventory[2].stackSize <= 0) {
			inventory[2] = null;
		}
	}
	
	public boolean canStart() {

		if (getGrid() == null) {
			return false;
		}

		if (getGrid().getEnergyStored() < (Reference.POWER_GENERATION_RATE * Reference.FURNACE_USEAGE_MULITPLIER)) {
			return false;
		}

		ItemStack output = getRecipeResult(false);

		if (output == null) {
			return false;
		}

		if (inventory[3] == null) {
			return true;
		}

		if (!inventory[3].isItemEqual(output)) {
			return false;
		}

		int result = Integer.valueOf(inventory[3].stackSize) + Integer.valueOf(output.stackSize);
		return (result <= output.getMaxStackSize());
	}
	
	public boolean canFinish() {
		if (processCur < processEnd) {
			return false;
		}

		ItemStack output = getRecipeResult(true);

		if (output == null) {
			processCur = 0.0F;
			isActive = false;
			return true;
		}

		if (inventory[3] == null) {
			return true;
		}
		if (!inventory[3].isItemEqual(output)) {
			return false;
		}
		int result = Integer.valueOf(inventory[3].stackSize) + Integer.valueOf(output.stackSize);
		return (result <= getInventoryStackLimit()) && (result <= output.getMaxStackSize());
	}
	
	public ItemStack getRecipeResult(boolean processing){
		
		ItemStack result = null;
		for (int i = 0; i < recipes.size(); i++) {
			CrystalForgeRecipe recipe = recipes.get(i);
			if(processing){
				if (recipe.checkRecipe(processInv[0], processInv[1], processInv[2])) {
					result = recipe.getOutput();
				}
			}else{
				if (recipe.checkRecipe(inventory[0], inventory[1], inventory[2])) {
					result = recipe.getOutput();
				}
			}
		}

		return result;
	}
	
	public float getEndTime(ItemStack output){
		for (int i = 0; i < recipes.size(); i++) {
			CrystalForgeRecipe recipe = recipes.get(i);
			
			if(output.isItemEqual(recipe.getOutput())){
				return recipe.getTimeNeeded();
			}
		}
		
		return 0.0F;
	}

}
