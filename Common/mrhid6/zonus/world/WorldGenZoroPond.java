package mrhid6.zonus.world;

import java.util.Random;
import mrhid6.zonus.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class WorldGenZoroPond {

	public void generate( World world, Random rand, int x, int z ) {

		int y = world.getHeight() - 1;

		while ((world.getBlockId(x, y, z) == 0) && (y > 0)) {
			y--;
		}
		y--;
		if (world.getBlockId(x, y, z) != Block.grass.blockID && world.getBlockId(x, y, z) != Block.dirt.blockID) {
			return;
		}

		if (rand.nextInt(20) < 5) {
			return;
		}

		world.setBlock(x, y, z + 4, getGrassID(rand));
		world.setBlock(x, y, z + 5, getGrassID(rand));
		world.setBlock(x + 1, y, z + 3, getGrassID(rand));
		world.setBlock(x + 1, y, z + 4, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 1, y, z + 5, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 1, y, z + 6, getGrassID(rand));
		world.setBlock(x + 2, y, z + 2, getGrassID(rand));
		world.setBlock(x + 2, y, z + 3, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 2, y, z + 4, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 2, y, z + 5, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 2, y, z + 6, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 2, y, z + 7, getGrassID(rand));
		world.setBlock(x + 3, y, z + 1, getGrassID(rand));
		world.setBlock(x + 3, y, z + 2, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 3, y, z + 3, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 3, y, z + 4, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 3, y, z + 5, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 3, y, z + 6, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 3, y, z + 7, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 3, y, z + 8, getGrassID(rand));
		world.setBlock(x + 4, y, z + 1, getGrassID(rand));
		world.setBlock(x + 4, y, z + 2, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 4, y, z + 3, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 4, y, z + 4, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 4, y, z + 5, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 4, y, z + 6, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 4, y, z + 7, getGrassID(rand));
		world.setBlock(x + 5, y, z + 1, getGrassID(rand));
		world.setBlock(x + 5, y, z + 2, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 5, y, z + 3, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 5, y, z + 4, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 5, y, z + 5, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 5, y, z + 6, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 5, y, z + 7, getGrassID(rand));
		world.setBlock(x + 6, y, z + 1, getGrassID(rand));
		world.setBlock(x + 6, y, z + 2, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 6, y, z + 3, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 6, y, z + 4, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 6, y, z + 5, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 6, y, z + 6, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 6, y, z + 7, getGrassID(rand));
		world.setBlock(x + 7, y, z + 2, getGrassID(rand));
		world.setBlock(x + 7, y, z + 3, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 7, y, z + 4, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 7, y, z + 5, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 7, y, z + 6, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 7, y, z + 7, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 7, y, z + 8, getGrassID(rand));
		world.setBlock(x + 8, y, z + 3, getGrassID(rand));
		world.setBlock(x + 8, y, z + 4, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 8, y, z + 5, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 8, y, z + 6, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 8, y, z + 7, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 8, y, z + 8, getGrassID(rand));
		world.setBlock(x + 9, y, z + 4, getGrassID(rand));
		world.setBlock(x + 9, y, z + 5, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 9, y, z + 6, ModBlocks.zoroStill.blockID);
		world.setBlock(x + 9, y, z + 7, getGrassID(rand));
		world.setBlock(x + 10, y, z + 5, getGrassID(rand));
		world.setBlock(x + 10, y, z + 6, getGrassID(rand));
	}

	public int getGrassID( Random rand ) {

		int r = rand.nextInt(20);

		if (r >= 0 && r < 10) {
			return ModBlocks.zoroGrass.blockID;
		} else if (r >= 10 && r <= 15) {
			return Block.grass.blockID;
		} else {
			return 0;
		}

	}
}
