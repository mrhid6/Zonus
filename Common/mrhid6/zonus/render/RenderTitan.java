package mrhid6.zonus.render;

import mrhid6.zonus.entities.EntityTitan;
import mrhid6.zonus.models.ModelTitan;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class RenderTitan extends Render {

	protected ModelTitan model;

	public RenderTitan( ModelTitan par1ModelBase, float par2 ) {
		shadowSize = 1.6F;
		model = par1ModelBase;
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender( Entity par1Entity, double par2, double par4, double par6, float par8, float par9 ) {
		this.renderTitan((EntityTitan) par1Entity, par2, par4, par6, par8, par9);
	}

	public void renderTitan( EntityTitan par1EntityBoat, double par2, double par4, double par6, float par8, float par9 ) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		GL11.glRotatef(180.0F - par8 - 90, 0.0F, 1.0F, 0.0F);

		this.loadTexture("/terrain.png");
		float f4 = 0.75F;
		GL11.glScalef(f4, f4, f4);
		GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
		this.loadTexture("/item/boat.png");
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		model.render(par1EntityBoat, 0.0F, 0.0F, 0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

}
