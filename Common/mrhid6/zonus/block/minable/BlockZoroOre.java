package mrhid6.zonus.block.minable;

import mrhid6.zonus.block.BlockTexturedBase;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZoroOre extends BlockTexturedBase {

	public BlockZoroOre( int id, String name ) {
		super(id, name, name, true);
		setHardness(5.0F);
		this.setStepSound(soundStoneFootstep);
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

}
