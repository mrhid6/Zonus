package mrhid6.zonus.gui;

import mrhid6.zonus.GridManager;
import mrhid6.zonus.GridPower;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

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
			
			l = this.container.tileEntity.getScaledProgress(24);
			if (l > 0) {
				drawTexturedModalRect(x + 79, y + 33, 176, 16, l, 16);
			}
			
			if (this.container.tileEntity.isActive)
			{
				drawTexturedModalRect(x + 56, y + 42, 176, 0, 16, 16);
			}
		}
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

		if ((mousex >= 42) && (mousex < 144) && (mousey >= 61) && (mousey < 71)) {
			drawToolTip(String.format(GridManager.GUISTRING, (int) grid.getEnergyStored(), (int) grid.getMaxEnergy()));
		}

	}
}