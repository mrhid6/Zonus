package mrhid6.zonus.block.fancy;

import java.util.List;
import java.util.Random;
import mrhid6.zonus.Config;
import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.ModBlocks;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWinterBirchSapling extends BlockSapling {

	public static int getGrowHeight( World world, int x, int y, int z ) {

		return 8;
	}

	public BlockWinterBirchSapling( int par1, String name ) {
		super(par1);

		this.setCreativeTab(Config.creativeTabXor);
		this.setUnlocalizedName(name);
	}

	@Override
	public int damageDropped( int par1 ) {

		return par1 & 3;
	}

	@Override
	public void func_96477_c( World par1World, int par2, int par3, int par4, Random par5Random ) {
		this.growTree(par1World, par2, par3, par4, par5Random);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon( int par1, int par2 ) {
		return blockIcon;
	}

	@Override
	public void getSubBlocks( int par1, CreativeTabs par2CreativeTabs, List par3List ) {
		par3List.add(new ItemStack(par1, 1, 0));
	}

	@Override
	public void growTree( World world, int x, int y, int z, Random random ) {
		if ((world == null) || (ModBlocks.winterbirchLog == null)) {
			System.out.println("[ERROR] Had a null that shouldn't have been. Winter Birch did not spawn! w=" + world + " r=" + ModBlocks.winterbirchLog);
			return;
		}

		if (random.nextInt(20) < 5) {
			return;
		}

		int height = getGrowHeight(world, x, y, z);

		height -= random.nextInt(4) + 1;

		if (height < 2) {
			return;
		}

		if (!((height % 2) == 0)) {
			return;
		}

		for (int i = 0; i < height; i++) {
			world.setBlock(x, y + i, z, ModBlocks.winterbirchLog.blockID);
		}

		boolean outdent = false;
		int outdentCount = 0;
		int radius = 0;

		int treeTop = y + height + 1;

		// System.out.println(height);

		for (int y1 = y + 2; (y1 <= treeTop); y1++) {

			if (outdentCount >= 1 && (y1 < treeTop && y1 < (treeTop - 1))) {
				radius = 2;
				outdent = !outdent;
				outdentCount = 0;
			} else {
				radius = 1;
				outdentCount++;
			}
			if (y1 < treeTop) {
				for (int x1 = -radius; x1 <= radius; x1++) {
					for (int z1 = -radius; z1 <= radius; z1++) {

						if (Math.sqrt((x1 * x1) + (z1 * z1)) <= radius) {

							if (world.getBlockId(x1 + x, y1, z1 + z) == 0) {
								world.setBlock(x1 + x, y1, z1 + z, ModBlocks.winterbirchLeaves.blockID);
								world.addBlockEvent(x1 + x, y1, z1 + z, ModBlocks.winterbirchLeaves.blockID, 1, 4);
							}
						}
					}
				}
			} else {
				world.setBlock(x, y1, z, ModBlocks.winterbirchLeaves.blockID);
			}

		}

	}

	@Override
	public void registerIcons( IconRegister iconRegister ) {
		blockIcon = iconRegister.registerIcon(Zonus.Modname + "sapling_winter");
	}

}
