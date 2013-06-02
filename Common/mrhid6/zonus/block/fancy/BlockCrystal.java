package mrhid6.zonus.block.fancy;

import mrhid6.zonus.block.BlockTexturedBase;
import mrhid6.zonus.render.BRCrystal;
import mrhid6.zonus.tileEntity.TECrystal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class BlockCrystal extends BlockTexturedBase {

	public BlockCrystal( int id,String name ) {
		super(id, "machine", name, true);
		
		this.setResistance(0.5F);
		this.setHardness(0.5F);
	}
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return BRCrystal.renderID;
	}
	
	@Override
	public void setBlockBoundsBasedOnState( IBlockAccess world, int x, int y, int z ) {
		
		setBlockBounds(0.25F, 0, 0.25F, 0.75F, 0.75F, 0.75F);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity( World world ) {
		return new TECrystal();
	}
	
	
}
