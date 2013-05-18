package mrhid6.zonus.world;

import java.util.Random;
import mrhid6.zonus.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class WorldGenHazelspring {

	public static int getGrowHeight( World world, int x, int y, int z ) {
		if (((world.getBlockId(x, y - 1, z) != Block.grass.blockID) && (world.getBlockId(x, y - 1, z) != Block.dirt.blockID)) || ((world.getBlockId(x, y, z) != 0))) {
			return 0;
		}

		int height = 1;

		for (; (world.getBlockId(x, y + 1, z) == 0) && (height < 26); y++) {
			height++;
		}

		return height;
	}

	public void generate( World world, Random random, int x, int z, int count ) {

		for (; count > 0; count--) {
			int y = world.getHeight() - 1;

			while ((world.getBlockId(x, y - 1, z) == 0) && (y > 0)) {
				y--;
			}

			growTree(world, x, y, z, random);

			x += random.nextInt(15) - 7;
			z += random.nextInt(15) - 7;

		}
	}

	public boolean growTree( World world, int x, int y, int z, Random random ) {
		if ((world == null) || (ModBlocks.hazelspringLog == null)) {
			System.out.println("[ERROR] Had a null that shouldn't have been. Winter Birch did not spawn! w=" + world + " r=" + ModBlocks.winterbirchLog);
			return false;
		}

		if (random.nextInt(20) > 2) {
			// return false;
		}

		int height = getGrowHeight(world, x, y, z);

		height -= random.nextInt(8) + 1;

		if (height < 2) {
			return false;
		}

		if (!((height % 2) == 0)) {
			return false;
		}

		for (int i = 0; i < height; i++) {
			world.setBlock(x, y + i, z, ModBlocks.hazelspringLog.blockID, 0, 0);
		}

		int width = 3;
		int treeTop = y + height + 1;

		world.setBlock(x - 1, y + 5, z, ModBlocks.hazelspringLeaves.blockID, 0, 0);
		world.setBlock(x + 1, y + 5, z, ModBlocks.hazelspringLeaves.blockID, 0, 0);
		world.setBlock(x, y + 5, z + 1, ModBlocks.hazelspringLeaves.blockID, 0, 0);
		world.setBlock(x, y + 5, z - 1, ModBlocks.hazelspringLeaves.blockID, 0, 0);

		for (int y1 = y + 6; (y1 <= treeTop); y1++) {

			int distanceTopTop = treeTop - y1;

			if (distanceTopTop <= 1) {
				width = 1;
			} else if (distanceTopTop == 2) {
				width = 2;
			}

			if (y1 < treeTop) {
				for (int x1 = x - width; x1 <= x + width; x1++) {
					for (int z1 = z - width; z1 <= z + width; z1++) {

						if (world.getBlockId(x1, y1, z1) == 0) {
							world.setBlock(x1, y1, z1, ModBlocks.hazelspringLeaves.blockID, 0, 0);
						}
					}
				}
				makeRounded(width, x, y1, z, world);
				// System.out.println(width);
				if (width == 1) {
					width = 3;
				} else {
					width--;
				}
			} else {
				world.setBlock(x, y1, z, ModBlocks.hazelspringLeaves.blockID, 0, 0);
				world.setBlock(x - 1, y1, z, ModBlocks.hazelspringLeaves.blockID, 0, 0);
				world.setBlock(x + 1, y1, z, ModBlocks.hazelspringLeaves.blockID, 0, 0);
				world.setBlock(x, y1, z + 1, ModBlocks.hazelspringLeaves.blockID, 0, 0);
				world.setBlock(x, y1, z - 1, ModBlocks.hazelspringLeaves.blockID, 0, 0);
			}

		}

		return true;

	}

	public void makeRounded( int radius, int x, int y, int z, World world ) {
		int x1 = x - radius;
		int x2 = x + radius;
		int z1 = z - radius;
		int z2 = z + radius;

		world.setBlock(x1, y, z1, 0);
		world.setBlock(x2, y, z1, 0);
		world.setBlock(x1, y, z2, 0);
		world.setBlock(x2, y, z2, 0);
	}
}
