package mrhid6.zonus.tileEntity;

import java.util.HashMap;
import mrhid6.zonus.InventoryUtils;
import mrhid6.zonus.Utils;
import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.interfaces.IConverterObj;
import mrhid6.zonus.interfaces.ITriniumObj;
import mrhid6.zonus.interfaces.IXorGridObj;
import mrhid6.zonus.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;

public class TEZoroFurnace extends TEMachineBase implements IXorGridObj {

	public static int guiPacketId;
	protected static final HashMap<Integer, ItemStack> recipes = new HashMap<Integer, ItemStack>();

	public static boolean setGuiPacketId( int id ) {
		if (id == 0) {
			return false;
		}
		guiPacketId = id;
		return true;
	}

	public int tempEng = 0;

	public TEZoroFurnace() {
		inventory = new ItemStack[2];
		processInv = new ItemStack[1];

		invName = "xor.furnace";
		
		recipes.put(ModBlocks.zoroOre.blockID, new ItemStack(ModItems.zoroIngot,2));
	}

	public void breakBlock() {

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

	public boolean canStart(){

		if(getGrid()==null)
			return false;

		if(getGrid().getEnergyStored()<80)
			return false;

		ItemStack output = getResultFor(this.inventory[0]);

		if (output == null) 
			return false;

		if (this.inventory[1] == null) {
			return true;
		}

		if (!this.inventory[1].isItemEqual(output)) {
			return false;
		}

		int result = Integer.valueOf(this.inventory[1].stackSize) + Integer.valueOf(output.stackSize);
		return (result <= output.getMaxStackSize());
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

	public ItemStack getResultFor( ItemStack itemstack ) {
		if(itemstack==null){
			return null;
		}
		ItemStack item = (ItemStack) recipes.get(itemstack.itemID);
		return (item == null) ? null : InventoryUtils.copyStack(item, item.stackSize);
	}
	
	public int getScaledProgress(int scale)
	{
		if (this.processEnd == 0.0F) {
			return 0;
		}
		return (int)(this.processCur * scale / this.processEnd);
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

		if (this.isActive) {
			if (this.processCur < this.processEnd) {
				this.processCur += 1;
				if(getGrid()!=null){
					getGrid().subtractPower(8.23F);
				}
			}
			if (canFinish()) {
				processFinish();
				this.processCur = 0.0F;
				this.processCur = 0.0F;

				if ((canStart())) {
					processStart();
					this.processCur += 1;
				} else {
					this.processCur = 0.0F;
					this.isActive = false;
					this.wasActive = true;
				}
			}
		} else{
			if (canStart()) {
				processStart();
				this.processCur += 1;
				this.isActive = true;
			}
		}
		
		if(isUpdate()){
			sendUpdatePacket(Side.CLIENT);
			this.setUpdate(false);
		}
		onInventoryChanged();

		TickSinceUpdate++;
	}

	protected void processStart()
	{
		this.processInv[0] = InventoryUtils.copyStack(this.inventory[0],1);
		
		this.processEnd = 50;

		this.inventory[0].stackSize -= 1;
		if (this.inventory[0].stackSize <= 0)
			this.inventory[0] = null;
	}

	protected void processFinish()
	{
		ItemStack output = getResultFor(this.processInv[0]);

		if (this.inventory[1] == null){
			this.inventory[1] = InventoryUtils.copyStack(output,output.stackSize);
		}else{
			this.inventory[1].stackSize += output.stackSize;
		}

	}
	
	public boolean canFinish()
	{
		if (this.processCur < this.processEnd) {
			return false;
		}

		ItemStack output = getResultFor(this.processInv[0]);
		
		if (output == null) {
			this.processCur = 0.0F;
			this.isActive = false;
			return true;
		}
		
		if (this.inventory[1] == null) {
			return true;
		}
		if (!this.inventory[1].isItemEqual(output)) {
			return false;
		}
		int result = Integer.valueOf(this.inventory[1].stackSize) + Integer.valueOf(output.stackSize);
		return (result <= getInventoryStackLimit()) && (result <= output.getMaxStackSize());
	}
	
	
}
