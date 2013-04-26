package mrhid6.zonus.gui;

import mrhid6.zonus.GridManager;
import mrhid6.zonus.GridPower;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.client.FMLClientHandler;

public class GuiZoroFurnace extends GuiMain {

	public ContainerZoroFurnace container;

	public GuiZoroFurnace( ContainerZoroFurnace container ) {
		super(container);
		this.container = container;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer( float par1, int par2, int par3 ) {
		// draw your Gui here, only thing you need to change is the path
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		mc.renderEngine.bindTexture("/mods/zonus/textures/gui/furnace.png");
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		GridPower grid = GridManager.getGrid(container.tileEntity.gridindex);

		if (grid != null) {
			int l = grid.getScaledEnergyStored(102);
			if (l > 0) {
				drawTexturedModalRect(x + 42, y + 61, 0, 166, l, 10);
			}

			l = container.tileEntity.getScaledProgress(24);
			if (l > 0) {
				drawTexturedModalRect(x + 79, y + 33, 176, 16, l, 16);
			}

			if (container.tileEntity.isActive) {
				drawTexturedModalRect(x + 56, y + 42, 176, 0, 16, 16);
			}
		}

		boolean hover = isHovering(10, 26, 16, 32);
		boolean hover2 = isHovering(10, 26, 34, 50);

		drawIcon("/mods/zonus/textures/gui/icons.png", 10 + (container.tileEntity.getMode() * 2), x + 10, y + 16, hover);
		drawColouredIcon("/mods/zonus/textures/gui/icons.png", x + 10, y + 34, hover2, (container.tileEntity.getColour() - 1));
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int param1, int param2 ) {
		fontRenderer.drawString("Zoro Furnace", 50, 6, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		super.drawGuiContainerForegroundLayer(param1, param2);
	}

	@Override
	protected void drawTooltips() {
		// System.out.println(container.tileEntity.gridindex);
		GridPower grid = GridManager.getGrid(container.tileEntity.gridindex);

		if (grid == null) {
			return;
		}

		if (isHovering(42, 144, 61, 71)) {
			drawToolTip(String.format(GridManager.GUISTRING, (int) grid.getEnergyStored(), (int) grid.getMaxEnergy()));
		}

		if (isHovering(10, 26, 16, 32)) {
			drawToolTip(container.tileEntity.getModeText());

		} else if (isHovering(10, 26, 34, 50)) {
			drawToolTip(container.tileEntity.getColourText());
		}

	}

	public boolean handleMouseClicked( int x, int y, int mouseButton ) {

		if (isHovering(10, 26, 16, 32)) {

			FMLClientHandler.instance().getClient().sndManager.playSoundFX("random.click", 1.0F, 0.6F);

			if (mouseButton == 0) {
				container.tileEntity.alterModeUp();
			}
			if (mouseButton == 1) {
				container.tileEntity.alterModeDown();
			}
		}
		if (isHovering(10, 26, 34, 50)) {

			FMLClientHandler.instance().getClient().sndManager.playSoundFX("random.click", 1.0F, 0.6F);
			if (mouseButton == 0) {
				container.tileEntity.alterColour();
			}
			if (mouseButton == 1) {
				container.tileEntity.alterColourBack();
			}

		}

		return true;
	}

	@Override
	protected void mouseClicked( int x, int y, int mouseButton ) {
		super.mouseClicked(x, y, mouseButton);
		handleMouseClicked(mousex, mousey, mouseButton);
	}
}