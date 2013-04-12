package mrhid6.zonus.render;

import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.tileEntity.machine.TEStearilliumCrafter;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;

public class RenderTEStearilliumCrafter extends TileEntitySpecialRenderer {

	private Minecraft mc;
	private RenderBlocks renderBlocks;
	private RenderItem renderItems;

	public RenderTEStearilliumCrafter() {
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

	public void renderItemInSlot( ItemStack stack, int slotid, World w, double x, double y, double z ) {

		EntityItem ei = new EntityItem(w);
		ei.hoverStart = 0f;
		ei.setEntityItemStack(stack);
		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		glPushMatrix();
		{
			glEnable(32826 /* rescale */);
			glTranslatef((float) x, (float) y, (float) z);

			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 150F, 150F);
			glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			if (!ei.getEntityItem().equals(stack)) {
				return;
			}
			if (stack.itemID < Block.blocksList.length && Block.blocksList[stack.itemID] != null && Block.blocksList[stack.itemID].blockID != 0) {
				glPushMatrix();
				{
					float[] coords = setItemPosition(slotid);
					glTranslatef(coords[0], 1F, coords[1]);
					glScalef(0.5F, 0.5F, 0.5F);
					renderItems.doRenderItem(ei, 0, 0, 0, 0, 0);
				}
				glPopMatrix();
			} else {
				glPushMatrix();
				{
					float[] coords = setItemPosition(slotid);
					glTranslatef(coords[0], 1F, coords[1]);
					glScalef(0.5F, 0.5F, 0.5F);
					renderItems.doRenderItem(ei, 0, 0, 0, 0, 0);
				}
				glPopMatrix();
			}
			glDisable(32826 /* scale */);
		}
		glPopMatrix();
		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt( TileEntity te, double x, double y, double z, float f ) {

		if (te == null || !(te instanceof TEStearilliumCrafter)) {
			return;
		}

		int count = 0;
		TEStearilliumCrafter tpb = (TEStearilliumCrafter) te;
		try {
			renderBlocks.renderBlockByRenderType(ModBlocks.stearilliumCrafter, (int) x, (int) y, (int) z);
		} catch (NullPointerException ex) {
			count++;
			if (count > 2) {
				throw ex;
			}
			renderBlocks = new RenderBlocks(tpb.worldObj);
		}

		if (tpb.worldObj.getBlockId(tpb.xCoord, tpb.yCoord + 1, tpb.zCoord) == 0 && tpb.worldObj.getClosestPlayer(tpb.xCoord, tpb.yCoord, tpb.zCoord, 15) != null) {
			for (int i = 0; i < 9; i++) {

				if (tpb.inventory[i] != null) {
					ItemStack stack = tpb.inventory[i].copy();
					stack.stackSize = 1;
					renderItemInSlot(stack, i, tpb.worldObj, x, y, z);
				}
			}

		}

	}

	public float[] setItemPosition( int id ) {
		float[] res = new float[2];
		switch (id) {
		case 0:
			res[0] = 0.31F;
			res[1] = 0.31F;
			break;
		case 1:
			res[0] = 0.5F;
			res[1] = 0.31F;
			break;
		case 2:
			res[0] = 0.69F;
			res[1] = 0.31F;
			break;

		case 3:
			res[0] = 0.31F;
			res[1] = 0.5F;
			break;
		case 4:
			res[0] = 0.5F;
			res[1] = 0.5F;
			break;
		case 5:
			res[0] = 0.69F;
			res[1] = 0.5F;
			break;

		case 6:
			res[0] = 0.31F;
			res[1] = 0.69F;
			break;
		case 7:
			res[0] = 0.5F;
			res[1] = 0.69F;
			break;
		case 8:
			res[0] = 0.69F;
			res[1] = 0.69F;
			break;
		}

		return res;
	}
}
