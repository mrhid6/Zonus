package mrhid6.zonus.render;

import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.models.ModelMiner;
import mrhid6.zonus.tileEntity.machine.TETriniumMiner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.client.FMLClientHandler;

public class RenderTETriniumMiner extends TileEntitySpecialRenderer {

	private Minecraft mc;
	private ModelMiner model;
	private RenderBlocks renderBlocks;

	public RenderTETriniumMiner() {
		mc = FMLClientHandler.instance().getClient();
		renderBlocks = new RenderBlocks();
		model = new ModelMiner();
	}

	@Override
	public void renderTileEntityAt( TileEntity te, double x, double y, double z, float f ) {

		if (te == null || !(te instanceof TETriniumMiner)) {
			return;
		}

		int count = 0;
		TETriniumMiner tpb = (TETriniumMiner) te;
		try {
			renderBlocks.renderBlockByRenderType(ModBlocks.triniumMiner, (int) x, (int) y, (int) z);
		} catch (NullPointerException ex) {
			count++;
			if (count > 2) {
				throw ex;
			}
			renderBlocks = new RenderBlocks(tpb.worldObj);
		}

		GL11.glPushMatrix();
		{
			// bindTextureByName("/mrhid6/xorbo/textures/ModelGrinder.png");
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			// model.renderArm(x, y, z);
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
		}

		GL11.glPushMatrix();
		{
			// bindTextureByName("/mrhid6/xorbo/textures/ModelGrinder.png");
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			// model.renderBlades(x, y-1, z);
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
		}

	}

}
