package mrhid6.zonus.lib;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.tileEntity.multiblock.TEStearilliumReactor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;

public class Utils {

	public static final String[] ColourName = { "White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime Green", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };
	public static Random rand = new Random();

	public static final int[][] SIDE_COORD_MOD = { { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 } };

	public static boolean createReactor( ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z ) {
		for (int xx = x - 3; xx <= x; xx++) {
			for (int yy = y - 3; yy <= y; yy++) {
				for (int zz = z - 3; zz <= z; zz++) {

					if (fitReactor(world, xx, yy, zz)) {
						if (Utils.isServerWorld()) {

							System.out.println("ok");
							replaceReactor(world, xx, yy, zz);

							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public static boolean fitReactor( World world, int x, int y, int z ) {
		int ss = ModBlocks.stearilliumReactorCore.blockID;
		int tb = ModBlocks.triniumBrick.blockID;
		int gl = ModBlocks.stearilliumGlass.blockID;

		int[][][] blueprint = { { { tb, tb, tb, tb }, { tb, tb, tb, tb }, { tb, tb, tb, tb }, { tb, tb, tb, tb } }, { { tb, gl, gl, tb }, { gl, ss, ss, gl }, { gl, ss, ss, gl }, { tb, gl, gl, tb } }, { { tb, gl, gl, tb }, { gl, ss, ss, gl }, { gl, ss, ss, gl }, { tb, gl, gl, tb } }, { { tb, tb, tb, tb }, { tb, tb, tb, tb }, { tb, tb, tb, tb }, { tb, tb, tb, tb } } };
		for (int yy = 0; yy < 4; yy++) {
			for (int xx = 0; xx < 4; xx++) {
				for (int zz = 0; zz < 4; zz++) {
					int block = world.getBlockId(x + xx, y - yy + 3, z + zz);
					if (block != blueprint[yy][xx][zz]) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static int[] getAdjacentCoordinatesForSide( int x, int y, int z, int side ) {
		return new int[] { x + SIDE_COORD_MOD[side][0], y + SIDE_COORD_MOD[side][1], z + SIDE_COORD_MOD[side][2] };
	}

	public static int getFuelFor( ItemStack item ) {

		if (item == null) {
			return 0;
		}

		int index = item.getItem().itemID;

		if (index == Item.bucketLava.itemID) {
			return 2000;
		}

		return TileEntityFurnace.getItemBurnTime(item);

	}

	public static List<ItemStack> getItemStackFromBlock( World world, int i, int j, int k ) {
		Block block = Block.blocksList[world.getBlockId(i, j, k)];
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		if (block == null) {
			return null;
		}

		int meta = world.getBlockMetadata(i, j, k);

		drops = block.getBlockDropped(world, i, j, k, meta, 0);

		TileEntity te = world.getBlockTileEntity(i, j, k);

		if (te != null && te instanceof IInventory) {

			IInventory inv = (IInventory) te;
			for (int c = 0; c < inv.getSizeInventory(); c++) {

				if (inv.getStackInSlot(c) != null) {
					drops.add(InventoryUtils.copyStack(inv.getStackInSlot(c), inv.getStackInSlot(c).stackSize));
					inv.setInventorySlotContents(c, null);
				}
			}
		}

		return drops;
	}

	public static int getRandomInt( int multiplier ) {
		return rand.nextInt(multiplier);
	}

	public static TileEntity getTileEntity( World world, int x, int y, int z, int meta, int blockId ) {
		for (int yy = -2; yy <= 2; yy++) {
			for (int xx = -2; xx <= 2; xx++) {
				for (int zz = -2; zz <= 2; zz++) {

					int id = world.getBlockId(x + xx, y + yy, z + zz);
					int md = world.getBlockMetadata(x + xx, y + yy, z + zz);

					if (id == blockId && md == meta) {
						return world.getBlockTileEntity(x + xx, y + yy, z + zz);
					}

				}
			}
		}

		return null;
	}

	public static boolean isClientWorld() {
		return FMLCommonHandler.instance().getEffectiveSide().isClient();
	}

	public static boolean isServerWorld() {
		return !isClientWorld();
	}

	public static boolean replaceReactor( World world, int x, int y, int z ) {
		for (int yy = 0; yy < 4; yy++) {
			int step = 1;
			for (int zz = 0; zz < 4; zz++) {
				for (int xx = 0; xx < 4; xx++) {
					int md = step;

					int blockid = world.getBlockId(x + xx, y + yy, z + zz);

					if (blockid == ModBlocks.stearilliumReactorCore.blockID) {
						md = 15;
					}

					if (blockid != 0) {
						// System.out.println(md);
						world.setBlock(x + xx, y + yy, z + zz, ModBlocks.stearilliumReactor.blockID, md, 2);
						world.addBlockEvent(x + xx, y + yy, z + zz, ModBlocks.stearilliumReactor.blockID, 1, 4);

						TileEntity tile = world.getBlockTileEntity(x + xx, y + yy, z + zz);

						if (tile instanceof TEStearilliumReactor) {
							((TEStearilliumReactor) tile).oldBlockId = blockid;
						}
						step++;
					}
				}
			}
		}

		world.markBlockRangeForRenderUpdate(x, y, z, x + 5, y + 7, z + 5);
		return true;
	}

}
