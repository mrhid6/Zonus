package mrhid6.zonus.block.machine;

import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.BlockTexturedBase;
import mrhid6.zonus.render.BRCrystalForge;
import mrhid6.zonus.tileEntity.machine.TECrystalForge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


public class BlockCrystalForge extends BlockTexturedBase {

	public BlockCrystalForge( int id,String name ) {
		super(id, "machine", name, true);
		
		this.setResistance(0.5F);
		this.setHardness(0.5F);
	}
	@Override
	public boolean renderAsNormalBlock() {
		return false;
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
	public int getRenderType() {
		return BRCrystalForge.renderID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity( World world ) {
		return new TECrystalForge();
	}
	
	
}
