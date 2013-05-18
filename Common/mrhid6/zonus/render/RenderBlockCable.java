package mrhid6.zonus.render;

import mrhid6.zonus.Config;
import mrhid6.zonus.GridManager;
import mrhid6.zonus.GridPower;
import mrhid6.zonus.items.Materials;
import mrhid6.zonus.tileEntity.TECableBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderBlockCable implements ISimpleBlockRenderingHandler {

	public static int renderId;

	public RenderBlockCable() {
		renderId = RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public int getRenderId() {
		return renderId;
	}

	public void renderCableEnd( Block block, RenderBlocks renderer, float offset, float Thickness, int x, int y, int z, ForgeDirection orientation, Tessellator var5 ) {
		renderer.renderAllFaces = true;
		float offX = orientation.offsetX / 2.0F;
		float offY = orientation.offsetY / 2.0F;
		float offZ = orientation.offsetZ / 2.0F;

		float centerX = 0.5F;
		float centerY = 0.5F;
		float centerZ = 0.5F;

		centerX += orientation.offsetX * -offset;
		centerY += orientation.offsetY * -offset;
		centerZ += orientation.offsetZ * -offset;

		float thickX = Math.abs(orientation.offsetX) > 0.1D ? 0.076F : Thickness / 2;
		float thickY = Math.abs(orientation.offsetY) > 0.1D ? 0.076F : Thickness / 2;
		float thickZ = Math.abs(orientation.offsetZ) > 0.1D ? 0.076F : Thickness / 2;

		renderer.setRenderBounds(centerX + offX - thickX, centerY + offY - thickY, centerZ + offZ - thickZ, centerX + offX + thickX, centerY + offY + thickY, centerZ + offZ + thickZ);

		Block tex = Block.blocksList[Materials.CableTip.itemID];

		GL11.glPushMatrix();
		{
			renderer.renderStandardBlock(tex, x, y, z);
		}
		GL11.glPopMatrix();
		renderer.renderAllFaces = false;
	}

	@Override
	public void renderInventoryBlock( Block block, int metadata, int modelID, RenderBlocks renderer ) {

	}

	@Override
	public boolean renderWorldBlock( IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderblocks ) {

		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (!(te instanceof TECableBase)) {
			return true;
		}

		TECableBase cable = (TECableBase) te;

		float th = (float) cable.getCableThickness();
		float sp = (1.0F - th) / 2.0F;

		Tessellator tessellator = Tessellator.instance;
		int connectivity = 0;
		int renderSide = 0;

		Icon texture = null;

		for (int i = 0; i < 6; i++) {

			int x1 = x + Config.SIDE_COORD_MOD[i][0];
			int y1 = y + Config.SIDE_COORD_MOD[i][1];
			int z1 = z + Config.SIDE_COORD_MOD[i][2];

			TileEntity te1 = world.getBlockTileEntity(x1, y1, z1);

			if (cable.canInteractWith(te1, i, true)) {
				if (!(te1 instanceof TECableBase)) {
					renderCableEnd(block, renderblocks, 0.076F, th + 0.125F, x, y, z, ForgeDirection.getOrientation(i), tessellator);
				} else if (cable.coupling) {
					renderCableEnd(block, renderblocks, 0.076F, th + 0.125F, x, y, z, ForgeDirection.getOrientation(i), tessellator);
				}
			}

		}

		GridPower grid = GridManager.getGrid(cable.gridindex);

		if (grid != null && grid.gridIndex != -1) {
			texture = block.getBlockTexture(world, x, y, z, 1);
		} else {
			texture = block.getBlockTexture(world, x, y, z, 0);
		}

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));

		double xD = x;
		double yD = y;
		double zD = z;

		int mask = 1;
		int[] invertedsides = new int[]{5,4,1,0,3,2};
		for (int i = 0; i < 6; i++) {

			TileEntity neighbor = null;

			int[] coords = { x, y, z };

			coords[(i / 2)] += (i % 2 * 2 - 1);

			if ((cable.worldObj != null) && (cable.worldObj.blockExists(coords[0], coords[1], coords[2]))) {
				neighbor = cable.worldObj.getBlockTileEntity(coords[0], coords[1], coords[2]);
			}

			if ((neighbor != null)) {

				if (cable.canInteractRender(neighbor, i, invertedsides[i])) {
					connectivity |= mask;
					renderSide |= mask;
				}
			}

			mask *= 2;
		}

		// renderTipPart(block, renderer, 0.076F, th+0.1F, x, y, z,
		// ForgeDirection.NORTH,tessellator);
		// renderTipPart(block, renderer, 0.152F, 0.25F, x, y, z,
		// ForgeDirection.UP);
		// renderTipPart(block, renderer, 0.228F, 0.2F, x, y, z,
		// ForgeDirection.UP);
		// renderTipPart(block, renderer, 0.304F, 0.15F, x, y, z,
		// ForgeDirection.UP);

		if (connectivity == 0) {
			block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
			renderblocks.setRenderBoundsFromBlock(block);

			tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
			renderblocks.renderFaceYNeg(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
			renderblocks.renderFaceYPos(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
			renderblocks.renderFaceZNeg(block, xD, yD, zD, texture);
			renderblocks.renderFaceZPos(block, xD, y, zD, texture);
			tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
			renderblocks.renderFaceXNeg(block, xD, yD, zD, texture);
			renderblocks.renderFaceXPos(block, xD, yD, zD, texture);
		}
		else if (connectivity == 3) {
			block.setBlockBounds(0.0F, sp, sp, 1.0F, sp + th, sp + th);
			renderblocks.setRenderBoundsFromBlock(block);

			tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
			renderblocks.renderFaceYNeg(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
			renderblocks.renderFaceYPos(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
			renderblocks.renderFaceZNeg(block, xD, yD, zD, texture);
			renderblocks.renderFaceZPos(block, xD, y, zD, texture);

			if ((renderSide & 0x1) != 0) {
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderblocks.renderFaceXNeg(block, xD, yD, zD, texture);
			}

			if ((renderSide & 0x2) != 0) {
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderblocks.renderFaceXPos(block, xD, yD, zD, texture);
			}
		} else if (connectivity == 12) {
			block.setBlockBounds(sp, 0.0F, sp, sp + th, 1.0F, sp + th);
			renderblocks.setRenderBoundsFromBlock(block);

			tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
			renderblocks.renderFaceZNeg(block, xD, yD, zD, texture);
			renderblocks.renderFaceZPos(block, xD, y, zD, texture);
			tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
			renderblocks.renderFaceXNeg(block, xD, yD, zD, texture);
			renderblocks.renderFaceXPos(block, xD, yD, zD, texture);

			if ((renderSide & 0x4) != 0) {
				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderblocks.renderFaceYNeg(block, xD, yD, zD, texture);
			}

			if ((renderSide & 0x8) != 0) {
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderblocks.renderFaceYPos(block, xD, yD, zD, texture);
			}
		} else if (connectivity == 48) {
			block.setBlockBounds(sp, sp, 0.0F, sp + th, sp + th, 1.0F);
			renderblocks.setRenderBoundsFromBlock(block);

			tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
			renderblocks.renderFaceYNeg(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
			renderblocks.renderFaceYPos(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
			renderblocks.renderFaceXNeg(block, xD, yD, zD, texture);
			renderblocks.renderFaceXPos(block, xD, yD, zD, texture);

			if ((renderSide & 0x10) != 0) {
				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderblocks.renderFaceZNeg(block, xD, y, zD, texture);
			}

			if ((renderSide & 0x20) != 0) {
				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderblocks.renderFaceZPos(block, xD, yD, zD, texture);
			}
		}
		else {
			if ((connectivity & 0x1) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderblocks.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderblocks.renderFaceXNeg(block, xD, yD, zD, texture);
			} else {
				block.setBlockBounds(0.0F, sp, sp, sp, sp + th, sp + th);
				renderblocks.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderblocks.renderFaceYNeg(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderblocks.renderFaceYPos(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderblocks.renderFaceZNeg(block, xD, yD, zD, texture);
				renderblocks.renderFaceZPos(block, xD, y, zD, texture);

				if ((renderSide & 0x1) != 0) {
					tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
					renderblocks.renderFaceXNeg(block, xD, yD, zD, texture);
				}

			}

			if ((connectivity & 0x2) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderblocks.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderblocks.renderFaceXPos(block, xD, yD, zD, texture);
			} else {
				block.setBlockBounds(sp + th, sp, sp, 1.0F, sp + th, sp + th);
				renderblocks.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderblocks.renderFaceYNeg(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderblocks.renderFaceYPos(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderblocks.renderFaceZNeg(block, xD, yD, zD, texture);
				renderblocks.renderFaceZPos(block, xD, y, zD, texture);

				if ((renderSide & 0x2) != 0) {
					tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
					renderblocks.renderFaceXPos(block, xD, yD, zD, texture);
				}

			}

			if ((connectivity & 0x4) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderblocks.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderblocks.renderFaceYNeg(block, xD, yD, zD, texture);
			} else {
				block.setBlockBounds(sp, 0.0F, sp, sp + th, sp, sp + th);
				renderblocks.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderblocks.renderFaceZNeg(block, xD, yD, zD, texture);
				renderblocks.renderFaceZPos(block, xD, y, zD, texture);
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderblocks.renderFaceXNeg(block, xD, yD, zD, texture);
				renderblocks.renderFaceXPos(block, xD, yD, zD, texture);

				if ((renderSide & 0x4) != 0) {
					tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
					renderblocks.renderFaceYNeg(block, xD, yD, zD, texture);
				}

			}

			if ((connectivity & 0x8) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderblocks.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderblocks.renderFaceYPos(block, xD, yD, zD, texture);
			} else {
				block.setBlockBounds(sp, sp + th, sp, sp + th, 1.0F, sp + th);
				renderblocks.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderblocks.renderFaceZNeg(block, xD, yD, zD, texture);
				renderblocks.renderFaceZPos(block, xD, y, zD, texture);
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderblocks.renderFaceXNeg(block, xD, yD, zD, texture);
				renderblocks.renderFaceXPos(block, xD, yD, zD, texture);

				if ((renderSide & 0x8) != 0) {
					tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
					renderblocks.renderFaceYPos(block, xD, yD, zD, texture);
				}

			}

			if ((connectivity & 0x10) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderblocks.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderblocks.renderFaceZNeg(block, xD, y, zD, texture);
			} else {
				block.setBlockBounds(sp, sp, 0.0F, sp + th, sp + th, sp);
				renderblocks.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderblocks.renderFaceYNeg(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderblocks.renderFaceYPos(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderblocks.renderFaceXNeg(block, xD, yD, zD, texture);
				renderblocks.renderFaceXPos(block, xD, yD, zD, texture);

				if ((renderSide & 0x10) != 0) {
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					renderblocks.renderFaceZNeg(block, xD, y, zD, texture);
				}

			}

			if ((connectivity & 0x20) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderblocks.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderblocks.renderFaceZPos(block, xD, yD, zD, texture);
			} else {
				block.setBlockBounds(sp, sp, sp + th, sp + th, sp + th, 1.0F);
				renderblocks.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderblocks.renderFaceYNeg(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderblocks.renderFaceYPos(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderblocks.renderFaceXNeg(block, xD, yD, zD, texture);
				renderblocks.renderFaceXPos(block, xD, yD, zD, texture);

				if ((renderSide & 0x20) != 0) {
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					renderblocks.renderFaceZPos(block, xD, yD, zD, texture);
				}
			}
		}

		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderblocks.setRenderBoundsFromBlock(block);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

}
