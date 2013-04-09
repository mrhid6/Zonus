package mrhid6.zonus.render;

import mrhid6.zonus.GridManager;
import mrhid6.zonus.GridPower;
import mrhid6.zonus.tileEntity.TECableBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
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

	@Override
	public void renderInventoryBlock( Block block, int metadata, int modelID, RenderBlocks renderer ) {

	}

	@Override
	public boolean renderWorldBlock( IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer ) {

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

		for (int i = 0; i < 6; i++) {

			TileEntity neighbor = null;

			int[] coords = { x, y, z };

			coords[(i / 2)] += (i % 2 * 2 - 1);

			if ((cable.worldObj != null) && (cable.worldObj.blockExists(coords[0], coords[1], coords[2]))) {
				neighbor = cable.worldObj.getBlockTileEntity(coords[0], coords[1], coords[2]);
			}

			if ((neighbor != null)) {

				if (cable.canInteractRender(neighbor, i)) {
					connectivity |= mask;
					renderSide |= mask;
				}
			}

			mask *= 2;
		}

		if (connectivity == 0) {
			block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
			renderer.setRenderBoundsFromBlock(block);

			tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
			renderer.renderBottomFace(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
			renderer.renderTopFace(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
			renderer.renderEastFace(block, xD, yD, zD, texture);
			renderer.renderWestFace(block, xD, y, zD, texture);
			tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
			renderer.renderNorthFace(block, xD, yD, zD, texture);
			renderer.renderSouthFace(block, xD, yD, zD, texture);
		} else if (connectivity == 3) {
			block.setBlockBounds(0.0F, sp, sp, 1.0F, sp + th, sp + th);
			renderer.setRenderBoundsFromBlock(block);

			tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
			renderer.renderBottomFace(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
			renderer.renderTopFace(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
			renderer.renderEastFace(block, xD, yD, zD, texture);
			renderer.renderWestFace(block, xD, y, zD, texture);

			if ((renderSide & 0x1) != 0) {
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderNorthFace(block, xD, yD, zD, texture);
			}

			if ((renderSide & 0x2) != 0) {
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderSouthFace(block, xD, yD, zD, texture);
			}
		} else if (connectivity == 12) {
			block.setBlockBounds(sp, 0.0F, sp, sp + th, 1.0F, sp + th);
			renderer.setRenderBoundsFromBlock(block);

			tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
			renderer.renderEastFace(block, xD, yD, zD, texture);
			renderer.renderWestFace(block, xD, y, zD, texture);
			tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
			renderer.renderNorthFace(block, xD, yD, zD, texture);
			renderer.renderSouthFace(block, xD, yD, zD, texture);

			if ((renderSide & 0x4) != 0) {
				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderer.renderBottomFace(block, xD, yD, zD, texture);
			}

			if ((renderSide & 0x8) != 0) {
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderer.renderTopFace(block, xD, yD, zD, texture);
			}
		} else if (connectivity == 48) {
			block.setBlockBounds(sp, sp, 0.0F, sp + th, sp + th, 1.0F);
			renderer.setRenderBoundsFromBlock(block);

			tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
			renderer.renderBottomFace(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
			renderer.renderTopFace(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
			renderer.renderNorthFace(block, xD, yD, zD, texture);
			renderer.renderSouthFace(block, xD, yD, zD, texture);

			if ((renderSide & 0x10) != 0) {
				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderEastFace(block, xD, y, zD, texture);
			}

			if ((renderSide & 0x20) != 0) {
				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderWestFace(block, xD, yD, zD, texture);
			}
		} else {
			if ((connectivity & 0x1) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderNorthFace(block, xD, yD, zD, texture);
			} else {
				block.setBlockBounds(0.0F, sp, sp, sp, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderer.renderBottomFace(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderer.renderTopFace(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderEastFace(block, xD, yD, zD, texture);
				renderer.renderWestFace(block, xD, y, zD, texture);

				if ((renderSide & 0x1) != 0) {
					tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
					renderer.renderNorthFace(block, xD, yD, zD, texture);
				}

			}

			if ((connectivity & 0x2) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderSouthFace(block, xD, yD, zD, texture);
			} else {
				block.setBlockBounds(sp + th, sp, sp, 1.0F, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderer.renderBottomFace(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderer.renderTopFace(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderEastFace(block, xD, yD, zD, texture);
				renderer.renderWestFace(block, xD, y, zD, texture);

				if ((renderSide & 0x2) != 0) {
					tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
					renderer.renderSouthFace(block, xD, yD, zD, texture);
				}

			}

			if ((connectivity & 0x4) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderer.renderBottomFace(block, xD, yD, zD, texture);
			} else {
				block.setBlockBounds(sp, 0.0F, sp, sp + th, sp, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderEastFace(block, xD, yD, zD, texture);
				renderer.renderWestFace(block, xD, y, zD, texture);
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderNorthFace(block, xD, yD, zD, texture);
				renderer.renderSouthFace(block, xD, yD, zD, texture);

				if ((renderSide & 0x4) != 0) {
					tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
					renderer.renderBottomFace(block, xD, yD, zD, texture);
				}

			}

			if ((connectivity & 0x8) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderer.renderTopFace(block, xD, yD, zD, texture);
			} else {
				block.setBlockBounds(sp, sp + th, sp, sp + th, 1.0F, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderEastFace(block, xD, yD, zD, texture);
				renderer.renderWestFace(block, xD, y, zD, texture);
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderNorthFace(block, xD, yD, zD, texture);
				renderer.renderSouthFace(block, xD, yD, zD, texture);

				if ((renderSide & 0x8) != 0) {
					tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
					renderer.renderTopFace(block, xD, yD, zD, texture);
				}

			}

			if ((connectivity & 0x10) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderEastFace(block, xD, y, zD, texture);
			} else {
				block.setBlockBounds(sp, sp, 0.0F, sp + th, sp + th, sp);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderer.renderBottomFace(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderer.renderTopFace(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderNorthFace(block, xD, yD, zD, texture);
				renderer.renderSouthFace(block, xD, yD, zD, texture);

				if ((renderSide & 0x10) != 0) {
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					renderer.renderEastFace(block, xD, y, zD, texture);
				}

			}

			if ((connectivity & 0x20) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderWestFace(block, xD, yD, zD, texture);
			} else {
				block.setBlockBounds(sp, sp, sp + th, sp + th, sp + th, 1.0F);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderer.renderBottomFace(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderer.renderTopFace(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderNorthFace(block, xD, yD, zD, texture);
				renderer.renderSouthFace(block, xD, yD, zD, texture);

				if ((renderSide & 0x20) != 0) {
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					renderer.renderWestFace(block, xD, yD, zD, texture);
				}
			}
		}

		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderer.setRenderBoundsFromBlock(block);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

}
