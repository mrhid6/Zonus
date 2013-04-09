package mrhid6.zonus.render;

import mrhid6.zonus.models.ModelZoroChest;
import mrhid6.zonus.tileEntity.TEZoroChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class RenderTEZoroChest extends TileEntitySpecialRenderer {

	private ModelZoroChest model;

	public RenderTEZoroChest() {
		model = new ModelZoroChest();
	}

	@Override
	public void renderTileEntityAt( TileEntity te, double x, double y, double z, float f ) {
		if (te == null || !(te instanceof TEZoroChest)) {
			return;
		}

		TEZoroChest tile = (TEZoroChest) te;

		int facing = tile.getFacing();
		bindTextureByName("/mods/zonus/textures/models/ZoroChestOff.png");
		GL11.glPushMatrix();
		{
			GL11.glEnable(32826 /* GL_RESCALE_NORMAL_EXT */);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
			GL11.glScalef(1.0F, -1F, -1F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			int k = 0;
			if (facing == 2) {
				k = 180;
			}
			if (facing == 3) {
				k = 0;
			}
			if (facing == 4) {
				k = 90;
			}
			if (facing == 5) {
				k = -90;
			}
			GL11.glRotatef(k, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			float lidangle = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * f;
			lidangle = 1.0F - lidangle;
			lidangle = 1.0F - lidangle * lidangle * lidangle;
			model.chestLid.rotateAngleX = -((lidangle * 3.141593F) / 2.0F);
			// Render the chest itself
			model.renderAll();
			GL11.glDisable(32826 /* GL_RESCALE_NORMAL_EXT */);
		}
		GL11.glPopMatrix();
	}

}
