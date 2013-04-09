package mrhid6.zonus.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemZoroCable extends ItemTexturedBase {

	public ItemZoroCable( int id, int maxStackSize, String name ) {
		super(id, maxStackSize, name);
	}

	@Override
	public boolean onItemUseFirst( ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ ) {
		int blockId = world.getBlockId(x, y, z);

		if (blockId > 0) {
			switch (side) {
			case 0:
				y--;
				break;
			case 1:
				y++;
				break;
			case 2:
				z--;
				break;
			case 3:
				z++;
				break;
			case 4:
				x--;
				break;
			case 5:
				x++;
			}

		}
		/*
		 * BlockZoroCable block =
		 * (BlockZoroCable)Block.blocksList[ModBlocks.zoroCable.blockID];
		 * 
		 * if (((blockId == 0) ||
		 * (world.canPlaceEntityOnSide(ModBlocks.zoroCable.blockID, x, y, z,
		 * false, side, entityplayer))) &&
		 * (world.b(block.getCollisionBoundingBoxFromPool(world, x, y, z,
		 * itemstack.j()))) && (world.d(x, y, z, block.cm, itemstack.j()))) {
		 * block. block.g(world, x, y, z, side); block.a(world, x, y, z,
		 * entityplayer); itemstack.stackSize -= 1;
		 * 
		 * return true; }
		 */
		return false;
	}

}
