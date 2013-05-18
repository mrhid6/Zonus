package mrhid6.zonus.block.fancy;

import java.util.List;
import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.BlockTexturedBase;
import mrhid6.zonus.items.Materials;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFancy extends BlockTexturedBase {

	public BlockFancy( int id, String name ) {
		super(id, name, name, true);

		this.setResistance(4.0F);
		this.setHardness(5.0F);
		icons = new Icon[4];
	}

	@Override
	public int damageDropped( int metadata ) {
		return metadata;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture( IBlockAccess par1iBlockAccess, int x, int y, int z, int side ) {
		return getIcon(side, par1iBlockAccess.getBlockMetadata(x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon( int par1, int par2 ) {
		return icons[par2];
	}
	
	@Override
	public boolean canSustainPlant( World world, int x, int y, int z, ForgeDirection direction, IPlantable plant ) {
		if(world.getBlockMetadata(x, y, z) == Materials.ZoroBrick.getItemDamage()){
			return true;
		}
		
		return super.canSustainPlant(world, x, y, z, direction, plant);
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

		icons[0] = iconRegister.registerIcon(Zonus.Modname + "zorobrick");
		icons[1] = iconRegister.registerIcon(Zonus.Modname + "stearilliumstone");
		icons[2] = iconRegister.registerIcon(Zonus.Modname + "noxiteblock");
		icons[3] = iconRegister.registerIcon(Zonus.Modname + "noxiteengineercore");

	}
}
