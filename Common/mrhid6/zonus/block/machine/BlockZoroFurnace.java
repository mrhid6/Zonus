package mrhid6.zonus.block.machine;

import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.BlockMachine;
import mrhid6.zonus.tileEntity.machine.TEZoroFurnace;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZoroFurnace extends BlockMachine {

	public Icon[] icons;

	public BlockZoroFurnace( int id, String textureName, String name, boolean craftable ) {
		super(id, textureName, name, craftable);

		this.setResistance(2.0F);
		this.setHardness(2.0F);
		icons = new Icon[5];
	}

	@Override
	public void breakBlock( World world, int x, int y, int z, int par5, int par6 ) {

		TEZoroFurnace tile = (TEZoroFurnace) world.getBlockTileEntity(x, y, z);

		if (tile != null) {
			tile.breakBlock();
		}

		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public TileEntity createNewTileEntity( World var1 ) {
		return new TEZoroFurnace();
	}

	@Override
	public Icon getBlockTexture( IBlockAccess par1IBlockAccess, int x, int y, int z, int blockSide ) {
		
		TileEntity te = par1IBlockAccess.getBlockTileEntity(x, y, z);
		
		boolean connected = false;
		if(te instanceof TEZoroFurnace){
			if( ((TEZoroFurnace)te).getGrid()!=null){
				connected = true;
			}
		}
		
		if (blockSide == 1) {
			return icons[0];
		} else if (blockSide == 0) {
			return icons[0];
		} else {
			int var6 = par1IBlockAccess.getBlockMetadata(x, y, z);
			return blockSide != var6 ? ((!connected)?icons[1]:icons[2]) : (!connected)?icons[3]:icons[4];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTextureFromSideAndMetadata( int side, int meta ) {
		if (side == 1) {
			return icons[0];
		} else if (side == 0) {
			return icons[0];
		} else if (side == 3) {
			return icons[3];
		} else {
			return icons[1];
		}
	}

	@Override
	public void registerIcons( IconRegister iconRegister ) {
		icons[0] = iconRegister.registerIcon(Zonus.Modname + textureName + "_top");
		icons[1] = iconRegister.registerIcon(Zonus.Modname + textureName + "_side_off");
		icons[2] = iconRegister.registerIcon(Zonus.Modname + textureName + "_side_on");
		icons[3] = iconRegister.registerIcon(Zonus.Modname + textureName + "_front_off");
		icons[4] = iconRegister.registerIcon(Zonus.Modname + textureName + "_front_on");
	}
}
