package mrhid6.zonus.tileEntity;

import java.util.List;
import mrhid6.zonus.Config;
import mrhid6.zonus.InventoryUtils;
import mrhid6.zonus.Utils;
import mrhid6.zonus.interfaces.IConverterObj;
import mrhid6.zonus.interfaces.ITriniumObj;
import mrhid6.zonus.interfaces.IXorGridObj;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;

public class TETriniumMiner extends TEMachineBase implements IXorGridObj, ITriniumObj {

	private int depth;
	private boolean doneMineing = false;
	public int tempEng = 0;

	public TETriniumMiner() {
		inventory = new ItemStack[2];

		invName = "xor.furnace";
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
			return getGrid().hasMachine(this)  && getGrid().canDiscoverObj(this);
		}
		return false;
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
	public int[] getSizeInventorySide( int var1 ) {
		// TODO Auto-generated method stub
		return null;
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
		int[][] coord_mod = { { -2, -2 }, { -1, -2 }, { 0, -2 }, { 1, -2 }, { 2, -2 }, { -2, -1 }, { -1, -1 }, { 0, -1 }, { 1, -1 }, { 2, -1 }, { -2, 0 }, { -1, 0 }, { 0, 0 }, { 1, 0 }, { 2, 0 }, { -2, 1 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 2, 1 }, { -2, 2 }, { -1, 2 }, { 0, 2 }, { 1, 2 }, { 2, 2 }, };
		int[] ar1 = Config.spiralArray(5);

		for (int i = 0; i < ar1.length; i++) {

			int coordid = -1 + ar1[i];
			int[] coords = coord_mod[coordid];
			// System.out.println(ar1[i]);
			// System.out.println("("+coords[0]+","+coords[1]+")");
			int x = coords[0] + xCoord;
			int z = coords[1] + zCoord;

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

		int[][] coord_mod = { { -2, -2 }, { -1, -2 }, { 0, -2 }, { 1, -2 }, { 2, -2 }, { -2, -1 }, { -1, -1 }, { 0, -1 }, { 1, -1 }, { 2, -1 }, { -2, 0 }, { -1, 0 }, { 0, 0 }, { 1, 0 }, { 2, 0 }, { -2, 1 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 2, 1 }, { -2, 2 }, { -1, 2 }, { 0, 2 }, { 1, 2 }, { 2, 2 }, };
		int[] ar1 = Config.spiralArray(5);

		for (int i = 0; i < ar1.length; i++) {

			int coordid = -1 + ar1[i];
			int[] coords = coord_mod[coordid];
			// System.out.println(ar1[i]);
			// System.out.println("("+coords[0]+","+coords[1]+")");
			int x = coords[0] + xCoord;
			int z = coords[1] + zCoord;

			if (depth == 0) {
				doneMineing = true;
				return;
			}
			if (shouldMineBlock(worldObj.getBlockId(x, depth, z))) {
				if (getGrid() != null && getGrid().getEnergyStored() >= 30) {
					getGrid().subtractPower(30);
					mineBlock(x, depth, z, worldObj.getBlockId(x, depth, z));
				}

				return;
			}

		}
	}

	private void mineStack( ItemStack stack ) {
		
		ItemStack added = addToNetworkedInventory(stack);
		stack.stackSize -= added.stackSize;
		
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
		doneMineing = data.getBoolean("donemining");

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

		if ((TickSinceUpdate % 1) == 0) {

			
		}
		if (getGrid() != null && getGrid().getEnergyStored() >= 30) {
			mineLevel();
		}

		if (!foundController()) {
			if (getGrid() != null) {
				getGrid().removeMachine(this);
			}
			gridindex = -1;
			sendUpdatePacket(Side.CLIENT);
		}

		TickSinceUpdate++;
	}

	@Override
	public void writeToNBT( NBTTagCompound data ) {
		super.writeToNBT(data);

		data.setInteger("depth", depth);

		data.setBoolean("donemining", doneMineing);
	}
	
	private ItemStack addToNetworkedInventory(ItemStack stack) {
		ItemStack added = InventoryUtils.copyStack(stack, stack.stackSize);
		
		if(getGrid()!=null){
			
			TEZoroChest chest = getGrid().getFirstChest();
			if(chest!=null){
				int injected = 0;
	
				int slot = -1;
				while ((slot = getPartialSlot(stack, slot + 1,chest.getSizeInventory(),chest)) >= 0 && injected < stack.stackSize) {
					injected += addToSlot(slot, stack, injected, true, chest);
				}
	
				slot = 0;
				while ((slot = getEmptySlot(0,chest.getSizeInventory(),chest)) >= 0 && injected < stack.stackSize) {
					injected += addToSlot(slot, stack, injected, true, chest);
				}
				chest.onInventoryChanged();
	
				added.stackSize=injected;
			}
		}

		return added;
	}
	
	protected int getEmptySlot(int startSlot, int endSlot, TEZoroChest chest) {
		for (int i = startSlot; i < endSlot; i++)
			if (chest.getStackInSlot(i) == null)
				return i;

		return -1;
	}
	
	protected int getPartialSlot(ItemStack stack, int startSlot, int endSlot, TEZoroChest chest) {

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
	
	protected int addToSlot(int slot, ItemStack stack, int injected, boolean doAdd, TEZoroChest chest) {
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

		if (!stackInSlot.isItemEqual(stack) || !ItemStack.areItemStackTagsEqual(stackInSlot, stack))
			return 0;

		int wanted = max - stackInSlot.stackSize;
		if (wanted <= 0)
			return 0;

		if (wanted > available)
			wanted = available;

		if (doAdd) {
			stackInSlot.stackSize += wanted;
			chest.setInventorySlotContents(slot, stackInSlot);
		}
		return wanted;
	}

}
