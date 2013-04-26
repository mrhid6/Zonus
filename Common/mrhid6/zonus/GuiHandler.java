package mrhid6.zonus;

import mrhid6.zonus.gui.ContainerStearilliumCrafter;
import mrhid6.zonus.gui.ContainerStearilliumReactor;
import mrhid6.zonus.gui.ContainerZoroChest;
import mrhid6.zonus.gui.ContainerZoroFurnace;
import mrhid6.zonus.gui.GuiStearilliumCrafter;
import mrhid6.zonus.gui.GuiStearilliumReactor;
import mrhid6.zonus.gui.GuiZoroChest;
import mrhid6.zonus.gui.GuiZoroFurnace;
import mrhid6.zonus.tileEntity.machine.TEStearilliumCrafter;
import mrhid6.zonus.tileEntity.machine.TEZoroChest;
import mrhid6.zonus.tileEntity.machine.TEZoroFurnace;
import mrhid6.zonus.tileEntity.multiblock.TEStearilliumReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	// returns an instance of the Gui you made earlier
	@Override
	public Object getClientGuiElement( int id, EntityPlayer player, World world, int x, int y, int z ) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof TEZoroFurnace) {
			return new GuiZoroFurnace(new ContainerZoroFurnace(player, (TEZoroFurnace) tileEntity));
		}
		if (tileEntity instanceof TEStearilliumCrafter) {
			return new GuiStearilliumCrafter(new ContainerStearilliumCrafter(player, (TEStearilliumCrafter) tileEntity));
		}
		if (tileEntity instanceof TEZoroChest) {
			return new GuiZoroChest(new ContainerZoroChest(player, (TEZoroChest) tileEntity));
		}
		/*
		 * if(tileEntity instanceof TETriniumChillerBase || tileEntity
		 * instanceof TETriniumChillerCore){ TETriniumChillerCore core = null;
		 * if(tileEntity instanceof TETriniumChillerBase){ core =
		 * ((TETriniumChillerBase) tileEntity).getCore(); }else{ core =
		 * (TETriniumChillerCore)tileEntity; }
		 * 
		 * if(core!=null) System.out.println(core.myTank.getLiquid().amount); }
		 */

		if (tileEntity instanceof TEStearilliumReactor) {

			System.out.println("gui" + (((TEStearilliumReactor) tileEntity).getCoreBlock() != null));

			return new GuiStearilliumReactor(new ContainerStearilliumReactor(player, ((TEStearilliumReactor) tileEntity).getCoreBlock()));
		}

		return null;
	}

	// returns an instance of the Container you made earlier
	@Override
	public Object getServerGuiElement( int id, EntityPlayer player, World world, int x, int y, int z ) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof TEZoroFurnace) {
			return new ContainerZoroFurnace(player, (TEZoroFurnace) tileEntity);
		}
		if (tileEntity instanceof TEStearilliumCrafter) {
			return new ContainerStearilliumCrafter(player, (TEStearilliumCrafter) tileEntity);
		}
		if (tileEntity instanceof TEZoroChest) {
			return new ContainerZoroChest(player, (TEZoroChest) tileEntity);
		}
		/*
		 * if(tileEntity instanceof TETriniumChillerBase || tileEntity
		 * instanceof TETriniumChillerCore){ TETriniumChillerCore core = null;
		 * if(tileEntity instanceof TETriniumChillerBase){ core =
		 * ((TETriniumChillerBase) tileEntity).getCore(); }else{ core =
		 * (TETriniumChillerCore)tileEntity; }
		 * 
		 * if(core!=null) return new ContainerTChiller(player, core); }
		 */

		if (tileEntity instanceof TEStearilliumReactor) {
			System.out.println("con" + (((TEStearilliumReactor) tileEntity).getCoreBlock() != null));
			return new ContainerStearilliumReactor(player, ((TEStearilliumReactor) tileEntity).getCoreBlock());
		}
		return null;
	}
}
