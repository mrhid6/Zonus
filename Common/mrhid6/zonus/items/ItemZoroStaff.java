package mrhid6.zonus.items;

import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.lib.Utils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemZoroStaff extends ItemTexturedBase {

	public static boolean replace( World world, int x, int y, int z ) {
		for (int yy = 0; yy < 3; yy++) {
			int step = 1;
			for (int zz = 0; zz < 3; zz++) {
				for (int xx = 0; xx < 3; xx++) {
					int md = step;
					if ((world.getBlockId(x + xx, y + yy, z + zz) == Block.ice.blockID)) {
						md = 0;
					}
					if (world.getBlockId(x + xx, y + yy, z + zz) == Block.fenceIron.blockID) {
						md = 10;
					}

					if (md == 5 && yy == 2) {
						md = 11;
					}

					if (!world.isAirBlock(x + xx, y + yy, z + zz)) {
						world.setBlock(x + xx, y + yy, z + zz, ModBlocks.triniumChiller.blockID, md, 2);
						world.addBlockEvent(x + xx, y + yy, z + zz, ModBlocks.triniumChiller.blockID, 1, 4);
					}

					step++;
				}
			}
		}

		world.markBlockRangeForRenderUpdate(x, y, z, x + 2, y + 2, z + 2);
		return true;
	}

	public static boolean replaceReactor( World world, int x, int y, int z ) {
		for (int yy = 0; yy < 7; yy++) {
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
						step++;
					}
				}
			}
		}

		world.markBlockRangeForRenderUpdate(x, y, z, x + 5, y + 7, z + 5);
		return true;
	}

	public ItemZoroStaff( int id, String name ) {
		super(id, 1, name);

		setMaxDamage(2000);
	}

	public boolean createChiller( ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z ) {
		for (int xx = x - 2; xx <= x; xx++) {
			for (int yy = y - 2; yy <= y; yy++) {
				for (int zz = z - 2; zz <= z; zz++) {

					if (fit(world, xx, yy, zz)) {
						if (Utils.isServerWorld()) {
							if (itemstack.getItemDamageForDisplay() + 5 <= itemstack.getMaxDamage() && !player.capabilities.isCreativeMode) {
								itemstack.setItemDamage(itemstack.getItemDamageForDisplay() + 5);
								replace(world, xx, yy, zz);
								return true;
							} else if (player.capabilities.isCreativeMode) {
								replace(world, xx, yy, zz);
								return true;
							}
						}
						return false;
					}
				}
			}
		}
		return false;
	}

	public boolean fit( World world, int x, int y, int z ) {
		int bo = ModBlocks.stearilliumStone.blockID;
		int bn = ModBlocks.triniumBrick.blockID;
		int bf = Block.fenceIron.blockID;
		int bl = Block.ice.blockID;

		int[][][] blueprint = { { { bn, bo, bn }, { bo, bo, bo }, { bn, bo, bn } }, { { bo, bo, bo }, { bo, bl, bo }, { bo, bo, bo } }, { { bn, bo, bn }, { bo, bo, bo }, { bn, bo, bn } } };

		boolean fencefound = false;
		for (int yy = 0; yy < 3; yy++) {
			for (int xx = 0; xx < 3; xx++) {
				for (int zz = 0; zz < 3; zz++) {
					int block = world.getBlockId(x + xx, y - yy + 2, z + zz);
					if (block != blueprint[yy][xx][zz]) {
						if ((yy == 1) && (!fencefound) && (block == bf) && (xx != zz) && ((xx == 1) || (zz == 1))) {
							fencefound = true;
						} else {
							return false;
						}
					}
				}
			}
		}
		return fencefound;
	}

	@Override
	public boolean onItemUseFirst( ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ ) {
		int bi = world.getBlockId(x, y, z);
		int md = world.getBlockMetadata(x, y, z);

		System.out.println(md);
		boolean result = false;
		ForgeDirection.getOrientation(side);

		if ((bi == ModBlocks.stearilliumStone.blockID) || (bi == ModBlocks.triniumBrick.blockID) || (bi == Block.fenceIron.blockID)) {
			// player.addChatMessage("" +world.isRemote);
			result = createChiller(stack, player, world, x, y, z);
		}

		if ((bi == ModBlocks.triniumBrick.blockID || bi == ModBlocks.stearilliumGlass.blockID) && result == false) {
			result = Utils.createReactor(stack, player, world, x, y, z);
		}

		return result;
	}

}
