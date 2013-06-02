package mrhid6.zonus.render;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import mrhid6.zonus.models.ModelNoxiteLogger;
import mrhid6.zonus.tileEntity.machine.TENoxiteLogger;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.client.FMLClientHandler;


public class RenderTENoxiteLogger extends TileEntitySpecialRenderer {
	
	private Minecraft mc;
	ModelNoxiteLogger model = new ModelNoxiteLogger();
	private RenderItem renderItems;
	private RenderBlocks renderBlocks;
	
	public RenderTENoxiteLogger() {
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
	public void renderTileEntityAt( TileEntity tileentity, double x, double y, double z, float f ) {
		if(tileentity == null || !(tileentity instanceof TENoxiteLogger))
			return;

		GL11.glPushMatrix();
		{
			GL11.glTranslatef((float) x, (float) y, (float) z);
			renderBase((float)x, (float)y+1.5F, (float)z,(TENoxiteLogger)tileentity,f);
			if(tileentity.worldObj!=null)
				renderTree(new ItemStack(Block.sapling),tileentity.worldObj,f);
		}
		GL11.glPopMatrix();

	}
	
	public void renderBase(float x, float y, float z, TENoxiteLogger tile,float partialTick){
		
		EntityPlayer p = mc.thePlayer;
		GL11.glPushMatrix();
		{
			bindTextureByName("/mods/zonus/textures/models/NoxiteLogger.png");
			GL11.glPushMatrix();
			{
				GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.5F, -1.5F, -0.5F);
				GL11.glScalef(1.0F, 1.0F, 1.0F);
				model.render();
				
				//System.out.println(tile.shouldRenderBlade());
				if(tile.shouldRenderBlade()){
					glTranslatef(0F, 0.75F, 0F);
					//float bob = MathHelper.sin(p.ticksExisted / (10.0F)) * 0.4F;
					float rot = (partialTick + p.ticksExisted)*(100*1.0F) % 360;
					GL11.glRotatef(rot, 1F, 0.0F, 0F);
					model.render2(rot);
				}
				
				GL11.glScalef(1.0F, 1.0F, 1.0F);
			}
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}
	
	public void renderTree(ItemStack stack, World w, float partialTick){
		EntityPlayer p = mc.thePlayer;
		EntityItem ei = new EntityItem(w);
		ei.hoverStart = 0f;
		ei.setEntityItemStack(stack);
		
		if(stack.itemID < Block.blocksList.length && Block.blocksList[stack.itemID] != null
				  && Block.blocksList[stack.itemID].blockID != 0)
		{
			glPushMatrix();
			glTranslatef(0.5F, 1.0F, 0.50F);
			
			GL11.glScalef(1.5F, 1.5F, 1.5F);
			
			float rot = (partialTick + p.ticksExisted)*(10*1.0F) % 360;
			GL11.glRotatef(rot, 0F, 1.0F, 0F);
			renderItems.doRenderItem(ei, 0, 0, 0, 0, 0);
			glPopMatrix();
		}
	}
	
	

}
