package mrhid6.zonus.block.machine;

import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.BlockTexturedBase;
import mrhid6.zonus.render.BRTriniumConverter;
import mrhid6.zonus.tileEntity.machine.TETriniumConverter;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTriniumConverter extends BlockTexturedBase {

	public BlockTriniumConverter( int id, String texture, String name ) {
		super(id, texture, name, true);

		this.setBlockBounds(0, 0, 0, 1F, 0.9F, 1F);
		this.setResistance(2.0F);
		this.setHardness(2.0F);
	}

	@Override
	public void breakBlock( World world, int x, int y, int z, int par5, int par6 ) {

		TETriniumConverter tile = (TETriniumConverter) world.getBlockTileEntity(x, y, z);

		if (tile != null) {
			tile.breakBlock();
		}

		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public TileEntity createNewTileEntity( World world ) {
		return new TETriniumConverter();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTextureFromSideAndMetadata( int side, int meta ) {
		return blockIcon;
	}

	@Override
	public int getRenderType() {
		return BRTriniumConverter.renderID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void registerIcons( IconRegister iconRegister ) {
		blockIcon = iconRegister.registerIcon(Zonus.Modname + "machine");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}
