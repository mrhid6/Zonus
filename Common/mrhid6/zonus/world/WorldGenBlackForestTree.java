package mrhid6.zonus.world;

import java.util.Random;
import net.minecraft.world.World;


public class WorldGenBlackForestTree {

	public WorldGenBlackForestTree() {
		// TODO Auto-generated constructor stub
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

	private void growTree( World world, int x, int y, int z, Random random ) {
		
		
	}

}
