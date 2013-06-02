package mrhid6.zonus.block.machine;

import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.BlockMachine;
import mrhid6.zonus.render.BRNoxiteLogger;
import mrhid6.zonus.tileEntity.machine.TENoxiteLogger;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNoxiteLogger extends BlockMachine {

	public BlockNoxiteLogger( int id, String name ) {
		super(id, name, name, true);

		this.setResistance(2.0F);
		this.setHardness(2.0F);
		icons = new Icon[2];

		setLightValue(1.0F);
	}

	@Override
	public void breakBlock( World world, int x, int y, int z, int par5, int par6 ) {

		TENoxiteLogger tile = (TENoxiteLogger) world.getBlockTileEntity(x, y, z);

		if (tile != null) {
			tile.breakBlock();
		}

		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public TileEntity createNewTileEntity( World var1 ) {
		return new TENoxiteLogger();
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
	
	@Override
	public int getRenderType() {
		return BRNoxiteLogger.renderID;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}
