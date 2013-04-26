package mrhid6.zonus.block.machine;

import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.BlockMachine;
import mrhid6.zonus.tileEntity.machine.TETriniumPlanter;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTriniumPlanter extends BlockMachine {

	public BlockTriniumPlanter( int id, String name ) {
		super(id, name, name, true);

		this.setResistance(2.0F);
		this.setHardness(2.0F);
		icons = new Icon[2];
	}

	@Override
	public TileEntity createNewTileEntity( World var1 ) {
		return new TETriniumPlanter();
	}

	@Override
	public Icon getBlockTexture( IBlockAccess par1IBlockAccess, int x, int y, int z, int blockSide ) {
		if (blockSide == 1 || blockSide == 0) {
			return icons[0];
		} else {
			return icons[1];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon( int side, int meta ) {

		if (side == 1 || side == 0) {
			return icons[0];
		} else {
			return icons[1];
		}
	}

	@Override
	public void registerIcons( IconRegister iconRegister ) {
		icons[0] = iconRegister.registerIcon(Zonus.Modname + textureName + "_top");
		icons[1] = iconRegister.registerIcon(Zonus.Modname + textureName + "_side");
	}

}
