package mrhid6.zonus.block.machine;

import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.BlockMachine;
import mrhid6.zonus.tileEntity.TETriniumMiner;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTriniumMiner extends BlockMachine {

	public BlockTriniumMiner( int id, String textureName, String name, boolean craftable ) {
		super(id, textureName, name, craftable);

		this.setResistance(6.0F);
		this.setHardness(6.0F);
		icons = new Icon[4];
	}

	@Override
	public TileEntity createNewTileEntity( World var1 ) {
		return new TETriniumMiner();
	}

	@Override
	public Icon getBlockTexture( IBlockAccess par1IBlockAccess, int x, int y, int z, int blockSide ) {
		if (blockSide == 1) {
			return icons[0];
		} else if (blockSide == 0) {
			return icons[3];
		} else {
			int var6 = par1IBlockAccess.getBlockMetadata(x, y, z);
			return blockSide != var6 ? icons[1] : icons[2];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTextureFromSideAndMetadata( int side, int meta ) {

		if (side == 1) {
			return icons[0];
		} else if (side == 0) {
			return icons[3];
		} else if (side == 3) {
			return icons[2];
		} else {
			return icons[1];
		}
	}

	@Override
	public void registerIcons( IconRegister iconRegister ) {
		icons[0] = iconRegister.registerIcon(Zonus.Modname + textureName + "_top");
		icons[1] = iconRegister.registerIcon(Zonus.Modname + textureName + "_side");
		icons[2] = iconRegister.registerIcon(Zonus.Modname + textureName + "_front");
		icons[3] = iconRegister.registerIcon(Zonus.Modname + textureName + "_bottom");
	}

}
