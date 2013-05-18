package mrhid6.zonus.block.minable;

import java.util.ArrayList;
import java.util.List;
import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.BlockTexturedBase;
import mrhid6.zonus.items.Materials;
import mrhid6.zonus.lib.InventoryUtils;
import mrhid6.zonus.lib.Utils;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ZonusOres extends BlockTexturedBase {

	public ZonusOres( int id, String name ) {
		super(id, name, name, true);

		this.setResistance(2.0F);
		this.setHardness(2.0F);
		icons = new Icon[4];
	}

	@Override
	public int damageDropped( int metadata ) {
		return metadata;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped( World world, int x, int y, int z, int metadata, int fortune ) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

		if (metadata == Materials.NoxiteOre.getItemDamage()) {
			ItemStack a = InventoryUtils.copyStack(Materials.NoxiteCystal, 1);

			a.stackSize = (1 + Utils.getRandomInt(3) % (2 + fortune));

			drops.add(a);

			int dustCount = Utils.getRandomInt(3) % (3 + fortune);

			if (dustCount > 0) {

				a = InventoryUtils.copyStack(Materials.NoxiteDust, 1);
				a.stackSize = dustCount;
				drops.add(a);
			}
		} else if (metadata == Materials.StearilliumOreBlock.getItemDamage()) {
			ItemStack a = InventoryUtils.copyStack(Materials.StearilliumOre, 1);

			a.stackSize = (1 + Utils.getRandomInt(3) % (2 + fortune));

			drops.add(a);
		} else {
			ItemStack a = null;
			if (metadata == 0) {
				a = InventoryUtils.copyStack(Materials.ZoroOre, 1);
			}
			if (metadata == 1) {
				a = InventoryUtils.copyStack(Materials.TriniumOre, 1);
			}

			drops.add(a);
		}
		return drops;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture( IBlockAccess par1iBlockAccess, int x, int y, int z, int side ) {
		return getIcon(side, par1iBlockAccess.getBlockMetadata(x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon( int par1, int par2 ) {
		if (par2 < icons.length) {
			return icons[par2];
		}
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks( int par1, CreativeTabs tab, List subItems ) {
		subItems.add(new ItemStack(this, 1, 0));
		subItems.add(new ItemStack(this, 1, 1));
		subItems.add(new ItemStack(this, 1, 2));
		subItems.add(new ItemStack(this, 1, 3));
	}

	@Override
	public void registerIcons( IconRegister iconRegister ) {

		icons[0] = iconRegister.registerIcon(Zonus.Modname + "zoroore");
		icons[1] = iconRegister.registerIcon(Zonus.Modname + "triniumore");
		icons[2] = iconRegister.registerIcon(Zonus.Modname + "noxiteore");
		icons[3] = iconRegister.registerIcon(Zonus.Modname + "stearilliumore");

	}
}
