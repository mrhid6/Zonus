package mrhid6.zonus.gui;

import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiZoroChest extends GuiMain {

	public ContainerZoroChest container;

	public GuiZoroChest( ContainerZoroChest containerZoroChest ) {
		super(containerZoroChest);
		container = containerZoroChest;
		xSize = 238;
		ySize = 256;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer( float par1, int par2, int par3 ) {
		// draw your Gui here, only thing you need to change is the path
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		mc.renderEngine.bindTexture("/mods/zonus/textures/gui/zorochest.png");
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		/*
		 * GridPower grid =
		 * GridManager.getGrid(this.container.tileEntity.gridindex);
		 * 
		 * if(grid!=null){ int l = grid.getScaledEnergyStored(48); if (l > 0) {
		 * drawTexturedModalRect(x + 64, y + 63, 0, 177, l, 6); } }
		 */
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int param1, int param2 ) {
		fontRenderer.drawString("Stearillium Crafter", 45, 6, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		super.drawGuiContainerForegroundLayer(param1, param2);
	}

	@Override
	protected void drawTooltips() {
		/*
		 * GridPower grid =
		 * GridManager.getGrid(this.container.tileEntity.gridindex);
		 * 
		 * if(grid==null) return;
		 * 
		 * if ((this.mousex >= 64) && (this.mousex < 111) && (this.mousey >= 63)
		 * && (this.mousey < 70)){ drawToolTip("" + (int)grid.getEnergyStored()+
		 * " / " + (int)grid.getMaxEnergy() + " MJ"); }
		 */

	}
}