package mrhid6.zonus.models;

import mrhid6.zonus.tileEntity.TENoxiteTurret;
import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelNoxiteTurret extends ModelBase {

	private IModelCustom modelTutBox;

	public ModelNoxiteTurret() {
		modelTutBox = AdvancedModelLoader.loadModel("/mods/zonus/models/NoxiteTurret.obj");
	}

	public void render() {
		modelTutBox.renderAll();
	}

	public void render( TENoxiteTurret box, double x, double y, double z ) {
		// Push a blank matrix onto the stack
		GL11.glPushMatrix();
		{
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glTranslatef((float) x + 0.5f, (float) y + 0f, (float) z + 0.5f);
			GL11.glScalef(0.05f, 0.05f, 0.05f);

			GL11.glRotatef(box.getFacing() * 90F, 0f, 1f, 0f);
			FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/zonus/textures/models/NoxiteTurret.png");
			this.render();

			GL11.glEnable(GL11.GL_LIGHTING);

		}
		GL11.glPopMatrix();
	}
}
