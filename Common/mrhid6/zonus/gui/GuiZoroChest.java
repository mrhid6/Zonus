package mrhid6.zonus.gui;

import org.lwjgl.opengl.GL11;
import cpw.mods.fml.client.FMLClientHandler;

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
		
		boolean hover = isHovering(192,208,6,22);
		boolean hover2 = isHovering(210,226,6,22);
		
		drawIcon("/mods/zonus/textures/gui/icons.png",2+(container.tileEntity.getMode()*2),x+192,y+6,hover);
		drawColouredIcon("/mods/zonus/textures/gui/icons.png",x+210,y+6,hover2,(container.tileEntity.getColour()-1));
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int param1, int param2 ) {
		fontRenderer.drawString("Zoro Chest", 12, 12, 4210752);
		//fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		super.drawGuiContainerForegroundLayer(param1, param2);
	}

	@Override
	protected void drawTooltips() {
		
		if(isHovering(192,208,6,22)){
			drawToolTip(container.tileEntity.getModeText());
			
		}else if(isHovering(210,226,6,22)){
			drawToolTip(container.tileEntity.getColourText());
		}

	}
	
	@Override
	protected void mouseClicked(int x, int y, int mouseButton) {
		super.mouseClicked(x, y, mouseButton);
		handleMouseClicked(this.mousex,this.mousey,mouseButton);
	}

	public boolean handleMouseClicked(int x, int y, int mouseButton){
		
		if(isHovering(192,208,6,22)){
			
			FMLClientHandler.instance().getClient().sndManager.playSoundFX("random.click", 1.0F, 0.6F);
			
			if(mouseButton==0)
				container.tileEntity.alterMode();
			if(mouseButton==1)
				container.tileEntity.alterModeBack();
		}
		if(isHovering(210,226,6,22)){
			
			FMLClientHandler.instance().getClient().sndManager.playSoundFX("random.click", 1.0F, 0.6F);
			if(mouseButton==0)
				container.tileEntity.alterColour();
			if(mouseButton==1)
				container.tileEntity.alterColourBack();
				
		}

		return true;
	}
	
	
}