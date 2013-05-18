package mrhid6.zonus.render;

import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.items.ModItems;
import mrhid6.zonus.tileEntity.machine.TEZoroController;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.client.FMLClientHandler;

public class RenderTEZoroController extends TileEntitySpecialRenderer {

	private Minecraft mc;
	private RenderBlocks renderBlocks;
	private RenderItem renderItems;

	public RenderTEZoroController() {
		mc = FMLClientHandler.instance().getClient();
		renderBlocks = new RenderBlocks();
		renderItems = new RenderItem(){

			@Override
			public boolean shouldBob() {
				return false;
			}

			@Override
			public boolean shouldSpreadItems() {
				return false;
			}
		};
		renderItems.setRenderManager(RenderManager.instance);
	}

	@Override
	public void renderTileEntityAt( TileEntity te, double x, double y, double z, float f ) {

		if (te == null || !(te instanceof TEZoroController)) {
			return;
		}

		if (te.worldObj == null) {
			bindTextureByName("/mods/zonus/textures/models/TriniumConverterOff.png");
			GL11.glPushMatrix();
			{
				GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5f, (float) z + 0.5F);
				GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				renderBlocks.renderBlockByRenderType(ModBlocks.zoroController, (int) x, (int) y, (int) z);
			}
			GL11.glPopMatrix();
			return;
		}

		int count = 0;
		TEZoroController tpb = (TEZoroController) te;

		try {
			renderBlocks.renderBlockByRenderType(ModBlocks.zoroController, (int) x, (int) y, (int) z);
		} catch (NullPointerException ex) {
			count++;
			if (count > 2) {
				throw ex;
			}
			renderBlocks = new RenderBlocks(tpb.worldObj);
		}

		if (tpb.worldObj.getBlockId(tpb.xCoord, tpb.yCoord + 1, tpb.zCoord) == 0 && tpb.worldObj.getClosestPlayer(tpb.xCoord, tpb.yCoord, tpb.zCoord, 15) != null && !mc.isGamePaused) {

			EntityPlayer p = mc.thePlayer;

			ItemStack stack = new ItemStack(ModItems.zoroWrench, 1);

			EntityItem ei = new EntityItem(tpb.worldObj);
			ei.hoverStart = 0f;
			ei.setEntityItemStack(stack);
			glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			glPushMatrix();
			{
				glEnable(32826 /* rescale */);
				glTranslatef((float) x, (float) y, (float) z);

				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 170F, 170F);
				glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

				if (!ei.getEntityItem().equals(stack)) {
					return;
				}
				if (stack.itemID < Block.blocksList.length && Block.blocksList[stack.itemID] != null && Block.blocksList[stack.itemID].blockID != 0) {

				} else {
					glPushMatrix();
					{
						float bob = MathHelper.sin(p.ticksExisted / (10.0F)) * 0.02F;

						RenderEngine renderEngine = RenderManager.instance.renderEngine;
						Tessellator tessellator = Tessellator.instance;

						String texture = ((stack.getItemSpriteNumber() == 0) ? "/terrain.png" : "/gui/items.png");
						renderEngine.bindTexture(texture);
						Icon icon = stack.getIconIndex();

						glTranslatef(0.5F, 1.1F + bob, 0.5F);
						glRotatef(90F, 1F, 0.0F, 0F);
						GL11.glScalef(0.6F, 0.6F, 0.6F);

						float fa = icon.getMinU();
						float f1 = icon.getMaxU();
						float f2 = icon.getMinV();
						float f3 = icon.getMaxV();
						// GL11.glEnable(GL12.GL_RESCALE_NORMAL);
						// GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
						GL11.glTranslatef(-0.5F, -0.5F, 1 / 32.0F);
						ItemRenderer.renderItemIn2D(tessellator, f1, f2, fa, f3, icon.getSheetWidth(), icon.getSheetHeight(), 0.0625F);

						// renderItems.doRenderItem(ei, 0, 0, 0, 0, 0);*/
					}
					glPopMatrix();
				}
				glDisable(32826 /* scale */);
			}
			glPopMatrix();
			glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		}

	}
}
