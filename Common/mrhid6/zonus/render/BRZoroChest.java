package mrhid6.zonus.render;

import mrhid6.zonus.tileEntity.machine.TEZoroChest;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BRZoroChest implements ISimpleBlockRenderingHandler {

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderId() {
		return renderID;
	}

	@Override
	public void renderInventoryBlock( Block block, int metadata, int modelID, RenderBlocks renderer ) {

		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		TileEntityRenderer.instance.renderTileEntityAt(new TEZoroChest(), 0.0D, 0.0D, 0.0D, 0.0F);
		GL11.glEnable(32826);

	}

	@Override
	public boolean renderWorldBlock( IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer ) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}
}
