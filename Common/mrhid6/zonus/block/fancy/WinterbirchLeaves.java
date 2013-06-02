package mrhid6.zonus.block.fancy;

import java.util.List;
import java.util.Random;
import mrhid6.zonus.Config;
import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.items.Materials;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WinterbirchLeaves extends BlockLeaves {

	public WinterbirchLeaves( int id, String name ) {
		super(id);
		setLightOpacity(1);
		setTickRandomly(true);
		setHardness(0.2F);
		setStepSound(Block.soundGrassFootstep);
		this.setUnlocalizedName(name);
		setCreativeTab(Config.creativeTabXor);
		disableStats();
		graphicsLevel = true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier( IBlockAccess par1iBlockAccess, int par2, int par3, int par4 ) {
		return 0x71cae3;
	}
	@Override
	public int damageDropped( int par1 ) {
		return 0;
	}
	@Override
	public int quantityDropped(Random par1Random)
	{
		return par1Random.nextInt(9) == 0 ? 1 : 0;
	}

	@Override
	public void dropBlockAsItemWithChance( World par1World, int par2, int par3, int par4, int par5, float par6, int par7 ) {
		if (!par1World.isRemote) {
			int idx = par1World.rand.nextInt(5);
			if (idx == 0) {
				int var9 = idDropped(par5, par1World.rand, par7);
				dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(var9, 1, damageDropped(par5)));
			}else{
				System.out.println(idx);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor( int par1 ) {
		return 0x71cae3;
	}

	@Override
	public void getSubBlocks( int par1, CreativeTabs tabs, List par3List ) {
		par3List.add(new ItemStack(par1, 1, 0));
	}

	@Override
	public int idDropped( int par1, Random random, int zero ) {
		return Materials.winterbirchSapling.itemID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered( IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5 ) {
		return !par1iBlockAccess.isBlockOpaqueCube(par2, par3, par4) ? true : par1iBlockAccess.getBlockId(par2, par3, par4) == blockID ? graphicsLevel : false;
	}
}
