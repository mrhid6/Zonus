package mrhid6.zonus.block;

import java.util.Random;
import mrhid6.zonus.Config;
import mrhid6.zonus.Zonus;
import mrhid6.zonus.fx.FXBeam;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.liquids.ILiquid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZoroStill extends BlockStationary implements ILiquid {

	protected BlockZoroStill( int par1, String name ) {
		super(par1, Material.water);
		this.setTickRandomly(false);
		this.setUnlocalizedName(name);
		setLightOpacity(3);
	}

	@Override
	public boolean getBlocksMovement( IBlockAccess par1IBlockAccess, int par2, int par3, int par4 ) {
		return blockMaterial != Material.lava;
	}

	@Override
	public boolean isBlockReplaceable( World world, int i, int j, int k ) {
		return true;
	}

	@Override
	public boolean isMetaSensitive() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick( World par1World, int par2, int par3, int par4, Random par5Random ) {
		super.randomDisplayTick(par1World, par2, par3, par4, par5Random);

		if (par5Random.nextInt(1) == 0) {
			FXBeam bubble = new FXBeam(par1World, par2, par3 + 1F, par4,0.1D);
			Minecraft.getMinecraft().effectRenderer.addEffect(bubble);
			Minecraft.getMinecraft().effectRenderer.renderParticles(bubble, 1);
			// par1World.spawnParticle("smoke", (double)((float)par2 +
			// par5Random.nextFloat()), (double)((float)par3 + 1.1F),
			// (double)((float)par4 + par5Random.nextFloat()), 0.0D, 0.0D,
			// 0.0D);
		}

		if (par5Random.nextInt(25) == 0) {
			for (int i = 0; i < 6; i++) {

				int x1 = par2 + Config.SIDE_COORD_MOD[i][0];
				int y1 = par3 + Config.SIDE_COORD_MOD[i][1];
				int z1 = par4 + Config.SIDE_COORD_MOD[i][2];
				
				int blockid = par1World.getBlockId(x1, y1, z1);
				
				if(blockid==Block.waterStill.blockID){
					par1World.setBlock(x1, y1, z1, this.blockID);
					return;
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons( IconRegister iconRegister ) {
		theIcon = new Icon[] { iconRegister.registerIcon(Zonus.Modname + "zoroStill"), iconRegister.registerIcon(Zonus.Modname + "zoroFlowing") };
	}

	@Override
	public int stillLiquidId() {
		return blockID;
	}

	@Override
	public int stillLiquidMeta() {
		return 0;
	}

}
