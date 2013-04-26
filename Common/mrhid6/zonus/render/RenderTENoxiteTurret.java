package mrhid6.zonus.render;

import mrhid6.zonus.models.ModelNoxiteTurret;
import mrhid6.zonus.tileEntity.TENoxiteTurret;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderTENoxiteTurret extends TileEntitySpecialRenderer {

	private ModelNoxiteTurret modelTutBox = new ModelNoxiteTurret();

	@Override
	public void renderTileEntityAt( TileEntity tileEntity, double x, double y, double z, float tick ) {
		modelTutBox.render((TENoxiteTurret) tileEntity, x, y, z);
	}
}