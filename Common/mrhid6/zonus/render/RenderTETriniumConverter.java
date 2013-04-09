package mrhid6.zonus.render;

import mrhid6.zonus.GridManager;
import mrhid6.zonus.GridPower;
import mrhid6.zonus.models.ModelTConverter;
import mrhid6.zonus.tileEntity.TETriniumConverter;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class RenderTETriniumConverter extends TileEntitySpecialRenderer {

	public static int renderId;
	private ModelTConverter model;

	public RenderTETriniumConverter() {
		model = new ModelTConverter();
	}

	public void renderAll() {
		renderMain();
		renderNorth();
		renderWest();
		renderSouth();
		renderEast();
	}

	public void renderEast() {
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(0.0001F, -0.017F, 0.0F);
			GL11.glScalef(1.0F, 1.017F, 1.0F);
			model.renderEast();
			GL11.glScalef(1.0F, 1.0F, 1.0F);
		}
		GL11.glPopMatrix();
	}

	public void renderMain() {
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		model.render();
	}

	public void renderNorth() {
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(0.0F, -0.015F, 0.0001F);
			GL11.glScalef(1.0F, 1.015F, 1.0F);
			model.renderNorth();
			GL11.glScalef(1.0F, 1.0F, 1.0F);
		}
		GL11.glPopMatrix();
	}

	public void renderSouth() {
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(0.0F, -0.016F, -0.0001F);
			GL11.glScalef(1.0F, 1.016F, 1.0F);
			model.renderSouth();
			GL11.glScalef(1.0F, 1.0F, 1.0F);
		}
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt( TileEntity tileentity, double x, double y, double z, float f ) {

		if (!(tileentity instanceof TETriniumConverter) || tileentity == null) {
			return;
		}

		if (tileentity.worldObj == null) {
			bindTextureByName("/mods/zonus/textures/models/TriniumConverterOff.png");
			GL11.glPushMatrix();
			{
				GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5f, (float) z + 0.5F);
				GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				renderAll();
			}
			GL11.glPopMatrix();
			return;
		}

		TETriniumConverter te = (TETriniumConverter) tileentity;
		te.updateConnections();

		GridPower grid = GridManager.getGrid(te.gridindex);

		if (grid != null && grid.gridIndex != -1) {
			bindTextureByName("/mods/zonus/textures/models/TriniumConverterOn.png");
		} else {
			bindTextureByName("/mods/zonus/textures/models/TriniumConverterOff.png");
		}
		GL11.glPushMatrix();
		{
			GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5f, (float) z + 0.5F);
			GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
			renderMain();
			if (te.connections[2] == true) {
				renderNorth();
				GL11.glScalef(1.0F, 1.0F, 1.0F);
			}
			if (te.connections[3] == true) {
				renderSouth();
				GL11.glScalef(1.0F, 1.0F, 1.0F);
			}
			if (te.connections[4] == true) {
				renderWest();
				GL11.glScalef(1.0F, 1.0F, 1.0F);
			}
			if (te.connections[5] == true) {
				renderEast();
				GL11.glScalef(1.0F, 1.0F, 1.0F);
			}
			GL11.glScalef(1.0F, 1.0F, 1.0F);
		}
		GL11.glPopMatrix();
	}

	public void renderWest() {
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(-0.0001F, -0.018F, 0.0F);
			GL11.glScalef(1.0F, 1.018F, 1.0F);
			model.renderWest();
			GL11.glScalef(1.0F, 1.0F, 1.0F);
		}
		GL11.glPopMatrix();
	}
}
