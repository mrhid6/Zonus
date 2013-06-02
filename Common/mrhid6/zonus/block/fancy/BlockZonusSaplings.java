package mrhid6.zonus.block.fancy;

import java.util.List;
import java.util.Random;
import mrhid6.zonus.Config;
import mrhid6.zonus.Zonus;
import mrhid6.zonus.world.WorldGenHazelspring;
import mrhid6.zonus.world.WorldGenWinterbirch;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZonusSaplings extends BlockSapling {
	
	Icon[] icons = new Icon[2];
	
	public static int getGrowHeight( World world, int x, int y, int z ) {

		return 8;
	}

	public BlockZonusSaplings( int par1, String name ) {
		super(par1);

		this.setCreativeTab(Config.creativeTabXor);
		this.setUnlocalizedName(name);
		setStepSound(soundGrassFootstep);
	}

	@Override
	public int damageDropped( int par1 ) {

		return par1 & 3;
	}

	@Override
	public void updateTick( World par1World, int par2, int par3, int par4, Random par5Random ) {
		this.growTree(par1World, par2, par3, par4, par5Random);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon( int par1, int par2 ) {
		par2 &= 3;
		return icons[par2];
	}

	@Override
	public void getSubBlocks( int par1, CreativeTabs par2CreativeTabs, List par3List ) {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
	}

	@Override
	public void growTree( World world, int x, int y, int z, Random random ) {
		int meta = world.getBlockMetadata(x,y,z);
		if(meta == 0){
			(new WorldGenWinterbirch()).growTree(world, x,y,z, random);
			
		}else if(meta == 1){
			(new WorldGenHazelspring()).growTree(world,x,y,z, random);
		}

	}

	@Override
	public void registerIcons( IconRegister iconRegister ) {
		icons[0] = iconRegister.registerIcon(Zonus.Modname + "sapling_winter");
		icons[1] = iconRegister.registerIcon(Zonus.Modname + "sapling_hazle");
	}

}
