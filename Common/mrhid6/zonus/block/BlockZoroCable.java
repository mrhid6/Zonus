package mrhid6.zonus.block;

import java.util.Random;
import mrhid6.zonus.items.ModItems;
import mrhid6.zonus.tileEntity.TECableBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockZoroCable extends BlockCableBase {

	public BlockZoroCable( int id, String name, boolean craftable ) {
		super(id, name, craftable);
	}

	@Override
	public void breakBlock( World world, int x, int y, int z, int par5, int par6 ) {

		TECableBase tile = (TECableBase) world.getBlockTileEntity(x, y, z);

		if (tile != null) {
			tile.breakBlock();
		}

		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public TileEntity createNewTileEntity( World world ) {
		return new TECableBase();
	}

	@Override
	public int idDropped( int par1, Random par2Random, int par3 ) {
		return ModItems.zoroCable.itemID;
	}

}
