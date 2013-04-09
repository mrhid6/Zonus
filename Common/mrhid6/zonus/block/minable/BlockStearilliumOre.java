package mrhid6.zonus.block.minable;

import java.util.Random;
import mrhid6.zonus.block.BlockTexturedBase;
import mrhid6.zonus.items.ModItems;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStearilliumOre extends BlockTexturedBase {

	public BlockStearilliumOre( int id, String name ) {
		super(id, name, name, true);
		setHardness(7.0F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture( IBlockAccess par1iBlockAccess, int x, int y, int z, int blockSide ) {
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTextureFromSideAndMetadata( int par1, int par2 ) {
		return blockIcon;
	}

	@Override
	public int idDropped( int par1, Random random, int zero ) {
		return ModItems.stearilliumOre.itemID;
	}

}
