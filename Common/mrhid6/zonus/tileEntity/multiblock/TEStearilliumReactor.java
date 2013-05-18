package mrhid6.zonus.tileEntity.multiblock;

import mrhid6.zonus.Config;
import mrhid6.zonus.Zonus;
import mrhid6.zonus.interfaces.IConverterObj;
import mrhid6.zonus.interfaces.ITriniumObj;
import mrhid6.zonus.interfaces.IXorGridObj;
import mrhid6.zonus.items.ModItems;
import mrhid6.zonus.lib.Reference;
import mrhid6.zonus.lib.Utils;
import mrhid6.zonus.network.PacketTile;
import mrhid6.zonus.network.Payload;
import mrhid6.zonus.tileEntity.TECableBase;
import mrhid6.zonus.tileEntity.TEMachineBase;
import mrhid6.zonus.tileEntity.TETriniumCable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import cpw.mods.fml.relauncher.Side;

public class TEStearilliumReactor extends TEMachineBase implements IXorGridObj, ITriniumObj, ITankContainer {

	public static int MAX_LIQUID = LiquidContainerRegistry.BUCKET_VOLUME * 10;
	public boolean breakingBlock = false;

	private int cablesConnected = 0;

	private boolean causeExplosion = false;
	private LiquidTank coolantTank;
	private TEStearilliumReactor coreBlock;

	private boolean isCore = false;

	private boolean loaded = false;
	public int oldBlockId = 0;

	public boolean state = false;
	public int tempLiquidAmt = -1;
	public int tempLiquidId = -1;

	private LiquidTank tempTank;
	private boolean update = false;
	private float heat = 0.0F;

	public TEStearilliumReactor() {
		inventory = new ItemStack[getSizeInventory()];

		invName = "stear.reactor";
		coolantTank = new LiquidTank(MAX_LIQUID);
		tempTank = new LiquidTank(MAX_LIQUID);
	}

	public void blockBreak() {

		if (breakingBlock == true) {
			return;
		}

		if (getGrid() != null) {
			getGrid().removeReactor(getCoreBlock());
		}
		System.out.println("breaking Block!");
		checkForExplode();
		breakingBlock = true;
	}

	@Override
	public boolean canConnectThrough() {
		return false;
	}

	public boolean canCycle() {

		if (getGrid() == null) {
			return false;
		}

		int cells = getCellCount();

		if (cells > 0) {
			return true;
		} else {
			return false;
		}
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

	public void checkForExplode() {

		if ((causeExplosion && isCore()) || (causeExplosion && getCoreBlock() == null)) {

			Zonus.spawnExplosion(worldObj, xCoord, yCoord + 4, zCoord, 50, true);
			// worldObj.newExplosion(null, xCoord, yCoord + 4, zCoord, 10.0F,
			// false, true);
		}
	}

	public void countCables() {
		cablesConnected = 0;
		for (int i = 0; i < 6; i++) {

			int x1 = xCoord + Config.SIDE_COORD_MOD[i][0];
			int y1 = yCoord + Config.SIDE_COORD_MOD[i][1];
			int z1 = zCoord + Config.SIDE_COORD_MOD[i][2];

			TileEntity te = worldObj.getBlockTileEntity(x1, y1, z1);

			if (te != null && te instanceof TECableBase && ((TECableBase) te).getGrid() != null) {
				cablesConnected++;
			}
		}
	}

	public void countCablesAroundReactor() {
		cablesConnected = 0;
		for (int yy = 0; yy > -4; yy--) {
			for (int xx = 0; xx > -4; xx--) {
				for (int zz = 0; zz > -4; zz--) {
					TileEntity te = worldObj.getBlockTileEntity(xCoord + xx, yCoord + yy, zCoord + zz);

					if (te != null && te instanceof TEStearilliumReactor) {
						((TEStearilliumReactor) te).countCables();
						cablesConnected += ((TEStearilliumReactor) te).cablesConnected;
					}
				}
			}
		}
	}

	@Override
	public LiquidStack drain( ForgeDirection from, int maxDrain, boolean doDrain ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LiquidStack drain( int tankIndex, int maxDrain, boolean doDrain ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int fill( ForgeDirection from, LiquidStack resource, boolean doFill ) {

		if (resource != null /* && resource.itemID == ModBlocks.zoroStill.blockID */ && getCoreBlock()!=null && getCoreBlock().coolantTank!=null) {
			setUpdate(true);
			return getCoreBlock().coolantTank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public int fill( int tankIndex, LiquidStack resource, boolean doFill ) {
		return 0;
	}

	public boolean foundController() {

		if (getGrid() != null) {
			return (getGrid().hasReactor(getCoreBlock()) && getGrid().canDiscoverReactorObj(getCoreBlock()));
		}

		return false;
	}


	public int getCellCount() {
		int cellCount = 0;
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack item = inventory[i];

			if (item == null) {
				continue;
			}

			if (item.itemID == ModItems.zoroWrench.itemID) {
				cellCount++;
			}
		}

		return cellCount;
	}

	public LiquidStack getCoolant() {
		if (getCoreBlock() != null && coolantTank != null) {
			return getCoreBlock().coolantTank.getLiquid();
		}
		return null;
	}

	public TEStearilliumReactor getCoreBlock() {
		if (isCore()) {
			return this;
		}
		return coreBlock;
	}

	public TEStearilliumReactor getCoreTileEntity() {

		TEStearilliumReactor res = null;

		for (int xx = xCoord - 4; xx < xCoord + 4; xx++) {
			for (int zz = zCoord - 4; zz < zCoord + 4; zz++) {
				for (int yy = yCoord - 4; yy < yCoord + 4; yy++) {

					TileEntity res2 = worldObj.getBlockTileEntity(xx, yy, zz);

					if (res2 != null && res2 instanceof TEStearilliumReactor) {
						res = (TEStearilliumReactor) res2;
					}
				}
			}
		}

		return res;
	}

	@Override
	public Packet getDescriptionPacket() {
		Payload payload = new Payload(2, 1, 5, 3, 0);

		payload.boolPayload[0] = isActive;
		payload.boolPayload[1] = transmitpower;

		payload.intPayload[0] = gridindex;
		payload.intPayload[1] = oldBlockId;
		payload.intPayload[2] = cablesConnected;
		if (getCoolant() != null) {
			payload.intPayload[3] = getCoolant().itemID;
			payload.intPayload[4] = getCoolant().amount;
		}

		payload.floatPayload[0] = processCur;
		payload.floatPayload[1] = processEnd;
		payload.floatPayload[2] = heat;

		payload.bytePayload[0] = (byte) facing;

		PacketTile packet = new PacketTile(descPacketId, xCoord, yCoord, zCoord, payload);
		return packet.getPacket();
	}

	@Override
	public int getGridIndex() {
		return getCoreBlock().gridindex;
	}

	public int getScaledCoolant( int i ) {
		return getCoolant() != null ? (int) (((float) getCoolant().amount / (float) (MAX_LIQUID)) * i) : 0;
	}

	@Override
	public int getSizeInventory() {
		return 26;
	}

	@Override
	public ILiquidTank getTank( ForgeDirection direction, LiquidStack type ) {
		return coolantTank;
	}

	@Override
	public ILiquidTank[] getTanks( ForgeDirection direction ) {
		return new LiquidTank[] { coolantTank };
	}

	@Override
	public void handleTilePacket( PacketTile packet ) {

		oldBlockId = packet.payload.intPayload[1];
		cablesConnected = packet.payload.intPayload[2];
		heat = packet.payload.floatPayload[2];

		if (getCoreBlock() != null && getCoreBlock().coolantTank != null) {
			getCoreBlock().coolantTank.setLiquid(new LiquidStack(packet.payload.intPayload[3], packet.payload.intPayload[4]));
		}

		if (Utils.isClientWorld()) {
			oldBlockId = packet.payload.intPayload[1];
			cablesConnected = packet.payload.intPayload[2];
			heat = packet.payload.floatPayload[2];
		}

		super.handleTilePacket(packet);
	}

	@Override
	public void init() {

		TEStearilliumReactor core = getCoreTileEntity();

		if (core == this) {
			setIsCore(true);
		} else {
			setCoreBlock(core);
		}
	}

	public boolean isCauseExplosion() {
		return causeExplosion;
	}

	public boolean isCore() {
		return isCore;
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
	public boolean isUpdate() {
		return update;
	}

	public void processCycle() {
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack item = inventory[i];

			if (item == null) {
				continue;
			}

			if (item.itemID == ModItems.zoroWrench.itemID) {

				item.setItemDamage(item.getItemDamage() + 1);
				// System.out.println(item.getItemDamage());
				if (item.getItemDamageForDisplay() >= item.getMaxDamage()) {
					setInventorySlotContents(i, null);
				}
			}
		}
		if(heat==0){
			heat = (getCellCount()+cablesConnected)*2.289F;
		}
		controlHeat();
		
		heat+=(heat/2.5F*(getCellCount()/8+1.3129856F))/3;
		if(heat>=10000){
			setCauseExplosion(true);
		}
		System.out.println("heat:"+heat);
		
		if(getCoolant()!=null){
			System.out.println("liquid:"+getCoolant().amount);
		}
	}
	
	public void controlHeat(){
		
		if(getCoolant()!=null){
			
			if(getCoolant().amount>=heat){
				getCoolant().amount-=heat;
				heat = 0;
				setUpdate(true);
			}else{
				heat-= getCoolant().amount;
				getCoolant().amount=0;
				setUpdate(true);
			}
			
			if(heat<0){
				heat=0;
			}
		}else{
			//System.out.println("collant null!");
		}
		
		//System.out.println("heat:"+heat);
	}

	@Override
	public void readFromNBT( NBTTagCompound data ) {
		super.readFromNBT(data);

		oldBlockId = data.getInteger("oldBlockId");
		isCore = data.getBoolean("iscore");

		if (isCore()) {
			System.out.println("loaded coolant");
			coolantTank.readFromNBT(data.getCompoundTag("coolantTank"));
		}
	}

	public void setCauseExplosion( boolean causeExplosion ) {
		this.causeExplosion = causeExplosion;
	}

	public void setCoreBlock( TEStearilliumReactor coreBlock ) {
		this.coreBlock = coreBlock;
	}

	@Override
	public void setGridIndex( int id ) {
		gridindex = id;
	}

	public void setIsCore( boolean isCore ) {
		this.isCore = isCore;
	}

	@Override
	public void setUpdate( boolean update ) {
		this.update = update;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (Utils.isClientWorld()) {

			if (!loaded) {
				init();
			}
			return;
		}

		checkForExplode();
		if (TickSinceUpdate % 2 == 0) {

			if (isCore()) {
				if (!foundController()) {
					if (getGrid() != null) {
						getGrid().removeReactor(this);
					}
					if (gridindex != -1) {
						gridindex = -1;
						setUpdate(true);
					}
				}

				countCablesAroundReactor();

				if (canCycle()) {
					getGrid().addEnergy(Reference.POWER_GENERATION_RATE * (cablesConnected + getCellCount()), this);

					if (TickSinceUpdate % 20 == 0) {
						processCycle();

					}
				}
			}

			if (isUpdate()) {
				sendUpdatePacket(Side.CLIENT);
				this.setUpdate(false);
			}
		}

		TickSinceUpdate++;
	}

	@Override
	public void writeToNBT( NBTTagCompound data ) {
		super.writeToNBT(data);

		data.setInteger("oldBlockId", oldBlockId);
		data.setBoolean("iscore", isCore);

		if (isCore() && getCoolant() != null) {
			System.out.println("saving liquid!");
			data.setTag("coolantTank", getCoolant().writeToNBT(new NBTTagCompound()));
		} else {

			if (!isCore()) {

			} else if (getCoolant() == null) {
				System.out.println("coolant null!");
			}
		}
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
