package mrhid6.zonus.tileEntity.machine;

import java.util.ArrayList;
import cpw.mods.fml.relauncher.Side;
import mrhid6.zonus.items.Materials;
import mrhid6.zonus.lib.Reference;
import mrhid6.zonus.lib.Utils;
import mrhid6.zonus.network.PacketTile;
import mrhid6.zonus.network.Payload;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSapling;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class TENoxiteLogger extends TETriniumMiner {

	public boolean placedSappings = false;
	public int plantingTries = 0;

	public boolean retryPlanting = false;

	public boolean shouldMine = true;
	public int tempEng = 0;
	public int timeTillLogger = 1000;
	public boolean triggerPlanting = false;

	public TENoxiteLogger() {

		inventory = new ItemStack[10];

	}
	
	@Override
	public Packet getDescriptionPacket() {
		Payload payload = new Payload(4, 1, 8, 2, 0);

		payload.boolPayload[0] = isActive;
		payload.boolPayload[1] = transmitpower;
		payload.boolPayload[2] = doneMineing;
		payload.boolPayload[3] = shouldMine;

		payload.intPayload[0] = gridindex;
		payload.intPayload[1] = colour;
		payload.intPayload[2] = depth;
		payload.intPayload[3] = depthLimit;
		payload.intPayload[4] = radius;
		payload.intPayload[5] = inputmode;
		payload.intPayload[6] = outputmode;
		payload.intPayload[7] = timeTillLogger;

		payload.floatPayload[0] = processCur;
		payload.floatPayload[1] = processEnd;

		payload.bytePayload[0] = (byte) getFacing();

		PacketTile packet = new PacketTile(descPacketId, xCoord, yCoord, zCoord, payload);
		return packet.getPacket();
	}

	@Override
	public void handleTilePacket( PacketTile packet ) {

		shouldMine = packet.payload.boolPayload[3];

		timeTillLogger = packet.payload.intPayload[7];

		if (Utils.isClientWorld()) {
			shouldMine = packet.payload.boolPayload[3];

			timeTillLogger = packet.payload.intPayload[7];
		}
		super.handleTilePacket(packet);
	}

	public boolean shouldRenderBlade(){
		if(shouldMine == true && getGrid()!=null){
			return true;
		}else{
			return false;
		}
	}

	public boolean canPlantSappling( int x, int z ) {

		boolean res = true;

		res = canProgress();
		int id = worldObj.getBlockId(x, yCoord, z);

		Block soil = Block.blocksList[worldObj.getBlockId(x, yCoord - 1, z)];

		if (soil == null || !soil.canSustainPlant(worldObj, x, yCoord, z, ForgeDirection.UP, (BlockSapling) Block.sapling)) {
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

	public boolean canProgress() {

		for (int i = 0; i < this.getSizeInventory(); i++) {
			if (inventory[i] == null) {
				continue;
			}
			if (isSapling(inventory[i].itemID)) {
				return true;
			}
		}

		if (getGrid() != null) {
			ArrayList<TEZoroChest> chests = getGrid().getChestsForSend(this, colour);

			for (int i = 0; i < chests.size(); i++) {
				TEZoroChest chest = chests.get(i);

				if (chest != null) {

					for (int i1 = 0; i1 < chest.getSizeInventory(); i1++) {
						if (chest.getStackInSlot(i1) == null) {
							continue;
						}
						if (isSapling(chest.getStackInSlot(i1).itemID)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public boolean checkStructure() {

		for (int x = -10; x <= 10; x++) {
			for (int z = -10; z <= 10; z++) {
				if (Math.sqrt((x * x) + (z * z)) <= 10) {

					int id = worldObj.getBlockId(x + xCoord, yCoord, z + zCoord);
					if (id != Materials.ZoroBrick.itemID) {
						return false;
					}
				}
			}
		}

		return true;

	}

	public int countSaplingsLeft() {

		int count = 0;
		for (int x = xCoord - 7; x < xCoord + 9; x++) {
			for (int z = zCoord - 7; z < zCoord + 9; z++) {

				int id = worldObj.getBlockId(x, yCoord, z);

				if (isSapling(id)) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public float getPower() {
		return super.getPower() + (Reference.POWER_GENERATION_RATE * 12);
	}

	public ItemStack getSaplingFromChests( int x, int z ) {
		ArrayList<TEZoroChest> chests = getGrid().getChestsForSend(this, colour);

		for (int i = 0; i < chests.size(); i++) {
			TEZoroChest chest = chests.get(i);

			for (int i1 = 0; i1 < chest.getSizeInventory(); i1++) {
				if (chest.getStackInSlot(i1) != null) {
					if (isSapling(chest.getStackInSlot(i1).itemID)) {

						if (!canPlantSappling(x, z)) {
							break;
						}

						ItemStack res = chest.getStackInSlot(i1);

						chest.getStackInSlot(i1).stackSize -= 1;
						if (chest.getStackInSlot(i1).stackSize <= 0) {
							chest.setInventorySlotContents(i1, null);
						}
						chest.onInventoryChanged();

						return res;
					}
				}
			}

		}

		return null;
	}

	@Override
	public int getSizeInventory() {
		return 10;
	}

	@Override
	public void init() {
		depth = yCoord + 80;
		radius = 21;
		depthLimit = yCoord - 1;
	}

	public boolean isSapling( int id ) {
		ArrayList<ItemStack> saplings = OreDictionary.getOres("treeSapling");

		for (ItemStack stack : saplings) {

			if (stack.itemID == id) {
				return true;
			}
		}

		return (Block.blocksList.length>id && (Block.blocksList[id] instanceof BlockSapling || Block.blocksList[id] instanceof BlockFlower));
	}

	public void placeSaplings() {
		boolean xSpace = false;
		boolean zSpace = false;

		int plantCount = 0;

		for (int x = xCoord - 7; x < xCoord + 9; x++) {
			if (xSpace) {
				xSpace = false;
				continue;
			}
			for (int z = zCoord - 7; z < zCoord + 9; z++) {

				if (zSpace) {
					zSpace = false;
					continue;
				}
				if (canPlantSappling(x, z)) {
					for (int i = 0; i < this.getSizeInventory(); i++) {
						if (inventory[i] == null) {
							continue;
						}
						if (isSapling(inventory[i].itemID)) {

							int id = inventory[i].itemID;
							int md = inventory[i].getItemDamage();

							worldObj.setBlock(x, yCoord, z, id, md, 2);
							worldObj.markBlockForUpdate(x, yCoord, z);

							inventory[i].stackSize -= 1;
							if (inventory[i].stackSize <= 0) {
								this.setInventorySlotContents(i, null);
							}
							this.onInventoryChanged();
							plantCount++;
							break;
						}
					}

					if (getGrid() != null) {

						ItemStack sapling = getSaplingFromChests(x, z);

						if (sapling != null) {

							int id = sapling.itemID;
							int md = sapling.getItemDamage();

							worldObj.setBlock(x, yCoord, z, id, md, 2);
							worldObj.markBlockForUpdate(x, yCoord, z);

							plantCount++;
						}
					}
				}

				zSpace = true;
			}
			xSpace = true;
		}

		if (plantCount == 0) {
			retryPlanting = true;

			plantingTries++;

			System.out.println("retry Planting " + plantingTries);
		} else {
			plantingTries = 0;

			if (retryPlanting) {
				retryPlanting = false;
			}

		}
	}

	public void quickGrowAll() {
		for (int x = xCoord - 7; x < xCoord + 9; x++) {
			for (int z = zCoord - 7; z < zCoord + 9; z++) {

				if (isSapling(worldObj.getBlockId(x, yCoord, z))) {
					worldObj.spawnParticle("happyVillager", x, yCoord, z, 0, 0, 0);
					((BlockFlower) Block.blocksList[worldObj.getBlockId(x, yCoord, z)]).updateTick(worldObj, x, yCoord, z, worldObj.rand);
				}
			}
		}
	}

	public void randomGrow() {
		for (int x = xCoord - 7; x < xCoord + 9; x++) {
			for (int z = zCoord - 7; z < zCoord + 9; z++) {

				if (worldObj.rand.nextInt(100000) < 99000) {
					continue;
				}

				if (isSapling(worldObj.getBlockId(x, yCoord, z))) {
					worldObj.spawnParticle("happyVillager", x, yCoord, z, 0, 0, 0);
					((BlockSapling) Block.blocksList[worldObj.getBlockId(x, yCoord, z)]).growTree(worldObj, x, yCoord, z, worldObj.rand);
					return;
				}
			}
		}
	}

	public void resetLogger() {
		init();
		doneMineing = false;
		System.out.println("i was reset!");
		this.setUpdate(true);
	}

	@Override
	public boolean shouldMineBlock( int blockID ) {

		ArrayList<ItemStack> wood = OreDictionary.getOres("logWood");

		for (ItemStack stack : wood) {
			if (stack.itemID == blockID) {
				return true;
			}
		}

		ArrayList<ItemStack> leaves = OreDictionary.getOres("treeLeaves");

		for (ItemStack stack : leaves) {
			if (stack.itemID == blockID) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (Utils.isClientWorld()) {
			return;
		}

		if (TickSinceUpdate % 8 == 0) {

			if (shouldMine || retryPlanting && plantingTries <= 5) {

				if (doneMineing) {
					shouldMine = false;
					placeSaplings();
				}
			} else {

				quickGrowAll();

				if ((countSaplingsLeft() <= 5 && timeTillLogger <= 0) || timeTillLogger <= 0) {
					resetLogger();
					shouldMine = true;
					triggerPlanting = false;
					timeTillLogger = 2000;
					plantingTries = 0;
				} else {
					//shouldMine = false;
					timeTillLogger -= 8;
				}

				// System.out.println(timeTillLogger);
			}
			sendUpdatePacket(Side.CLIENT);
		}

		TickSinceUpdate++;
	}
}
