package mrhid6.zonus.block.fancy;

import java.util.Random;
import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.BlockTexturedBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZoroGrass extends BlockTexturedBase {

	public BlockZoroGrass( int id, String texture, String name ) {
		super(id, texture, name, true);
		this.setStepSound(soundGrassFootstep);

		icons = new Icon[2];
	}

	@Override
	public int colorMultiplier( IBlockAccess par1IBlockAccess, int par2, int par3, int par4 ) {
		int l = 0;
		int i1 = 0;
		int j1 = 0;

		for (int k1 = -1; k1 <= 1; ++k1) {
			for (int l1 = -1; l1 <= 1; ++l1) {
				int i2 = par1IBlockAccess.getBiomeGenForCoords(par2 + l1, par4 + k1).getBiomeGrassColor();
				l += (i2 & 16711680) >> 16;
				i1 += (i2 & 65280) >> 8;
				j1 += i2 & 255;
			}
		}

		int val = (l / 9 & 255) << 16 | (i1 / 9 & 255) << 8 | j1 / 9 & 255;

		return val;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return 0x505c44;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture( IBlockAccess par1iBlockAccess, int x, int y, int z, int blockSide ) {
		if (blockSide == 1) {
			return icons[0];
		}
		if (blockSide == 0) {
			return Block.dirt.getBlockTexture(par1iBlockAccess, x, y, z, blockSide);
		}
		return icons[1];
	}

	@Override
	public Icon getIcon( int side, int meta ) {
		if (side == 1) {
			return icons[0];
		}
		if (side == 0) {
			return Block.dirt.getIcon(side, meta);
		}

		return icons[1];
	}

	@Override
	public int getRenderColor( int par1 ) {
		return this.getBlockColor();
	}

	@Override
	public int idDropped( int par1, Random par2Random, int par3 ) {
		return Block.dirt.blockID;
	}

	@Override
	public void randomDisplayTick( World par1World, int par2, int par3, int par4, Random par5Random ) {
		super.randomDisplayTick(par1World, par2, par3, par4, par5Random);

		if (par5Random.nextInt(50) == 0) {
			par1World.spawnParticle("smoke", par2 + par5Random.nextFloat(), par3 + 1.1F, par4 + par5Random.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void registerIcons( IconRegister iconRegister ) {

		new Random();

		icons[0] = iconRegister.registerIcon(Zonus.Modname + textureName + "_top");
		icons[1] = iconRegister.registerIcon(Zonus.Modname + textureName + "_side");
	}
}
