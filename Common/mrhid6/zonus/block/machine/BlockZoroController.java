package mrhid6.zonus.block.machine;

import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.BlockTexturedBase;
import mrhid6.zonus.tileEntity.machine.TEZoroController;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZoroController extends BlockTexturedBase {

	public Icon[] icons;

	public BlockZoroController( int id, String textureName, String name, boolean craftable ) {
		super(id, textureName, name, craftable);

		this.setResistance(2.0F);
		this.setHardness(2.0F);
		icons = new Icon[4];
	}

	@Override
	public void breakBlock( World world, int x, int y, int z, int par5, int par6 ) {

		TEZoroController tile = (TEZoroController) world.getBlockTileEntity(x, y, z);

		if (tile != null) {
			tile.breakBlock();
		}

		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public TileEntity createNewTileEntity( World var1 ) {
		return new TEZoroController();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture( IBlockAccess par1iBlockAccess, int x, int y, int z, int blockSide ) {

		return getBlockTextureFromSideAndMetadata(blockSide, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTextureFromSideAndMetadata( int side, int meta ) {

		if (side == 1) {
			return icons[0];
		}
		if (side == 0) {
			return icons[3];
		}
		if (side == 2 || side == 4) {
			return icons[2];
		}

		return icons[1];
	}

	@Override
	public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are ) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity == null || player.isSneaking()) {
			return false;
		}

		// code to open gui explained later
		player.openGui(Zonus.instance, 0, world, x, y, z);
		return true;
	}

	@Override
	public void onNeighborBlockChange( World world, int x, int y, int z, int blockID ) {
		super.onNeighborBlockChange(world, x, y, z, blockID);

		TEZoroController tile = (TEZoroController) world.getBlockTileEntity(x, y, z);
		tile.onNeighborBlockChange();
	}

	@Override
	public void registerIcons( IconRegister iconRegister ) {
		icons[0] = iconRegister.registerIcon(Zonus.Modname + textureName + "_top");
		icons[1] = iconRegister.registerIcon(Zonus.Modname + textureName + "_side1");
		icons[2] = iconRegister.registerIcon(Zonus.Modname + textureName + "_side2");
		icons[3] = iconRegister.registerIcon(Zonus.Modname + "machine");
	}

}
