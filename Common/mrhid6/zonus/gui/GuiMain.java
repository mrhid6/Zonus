package mrhid6.zonus.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GuiMain extends GuiContainer {

	protected int mousex;
	protected int mousey;

	public GuiMain( Container par1Container ) {
		super(par1Container);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer( float var1, int var2, int var3 ) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void drawGuiContainerForegroundLayer( int par1, int par2 ) {

		GL11.glDisable(2896);
		GL11.glDisable(2929);

		drawTooltips();

		GL11.glEnable(2896);
		GL11.glEnable(2929);
	}

	public void drawIcon( String texture, int iconIndex, int x, int y, boolean hover ) {
		if(hover)
			iconIndex++;
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		mc.renderEngine.bindTexture(texture);
		int textureRow = iconIndex >> 4;
		int textureColumn = iconIndex - 16 * textureRow;

		drawTexturedModalRect(x, y, 16 * textureColumn, 16 * textureRow, 16, 16);
	}
	
	public void drawColouredIcon(String texture, int x, int y, boolean hover, int color ){
		drawIcon(texture,0,x,y,hover);
		drawIcon(texture,240+color,x,y,false);
	}

	public void drawToolTip( String text ) {
		drawCreativeTabHoveringText(text, mousex, mousey);
	}
	
	public boolean isHovering(int minX,int maxX,int minY,int maxY){
		return (this.mousex>minX && this.mousex<maxX && this.mousey>minY && this.mousey<maxY);
	}

	protected abstract void drawTooltips();

	@Override
	public void handleMouseInput() {

		int x = Mouse.getEventX() * width / mc.displayWidth;
		int y = height - Mouse.getEventY() * height / mc.displayHeight - 1;

		mousex = (x - (width - xSize) / 2);
		mousey = (y - (height - ySize) / 2);

		super.handleMouseInput();
	}

}
