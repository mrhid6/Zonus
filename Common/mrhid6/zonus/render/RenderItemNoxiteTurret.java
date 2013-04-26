package mrhid6.zonus.render;

import mrhid6.zonus.models.ModelNoxiteTurret;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.client.FMLClientHandler;

public class RenderItemNoxiteTurret implements IItemRenderer {

	private ModelNoxiteTurret modelTutBox;

	public RenderItemNoxiteTurret() {
		modelTutBox = new ModelNoxiteTurret();
	}

	@Override
	public boolean handleRenderType( ItemStack item, ItemRenderType type ) {
		return true;
	}

	@Override
	public void renderItem( ItemRenderType type, ItemStack item, Object... data ) {
		switch (type) {
		case ENTITY: {
			renderTutBox(0f, 0f, 0f, 0.03f, 0);
			return;
		}

		case EQUIPPED: {
			renderTutBox(0f, 1f, 1f, 0.03f, -90F);
			return;
		}

		case INVENTORY: {
			renderTutBox(0f, -0.4f, 0f, 0.03f, 180F);
			return;
		}

		default:
			return;
		}
	}

	private void renderTutBox( float x, float y, float z, float scale, float rotate ) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glTranslatef(x, y, z);
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(rotate, 0f, 1f, 0f);

		FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/zonus/textures/models/NoxiteTurret.png");

		modelTutBox.render();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	@Override
	public boolean shouldUseRenderHelper( ItemRenderType type, ItemStack item, ItemRendererHelper helper ) {
		return true;
	}
}
