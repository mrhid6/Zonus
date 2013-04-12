package mrhid6.zonus;

import java.util.ArrayList;
import mrhid6.zonus.lib.Utils;
import mrhid6.zonus.network.PacketGrid;
import mrhid6.zonus.network.PacketUtils;
import mrhid6.zonus.tileEntity.TECableBase;
import mrhid6.zonus.tileEntity.TEPoweredBase;
import mrhid6.zonus.tileEntity.machine.TETriniumConverter;
import mrhid6.zonus.tileEntity.machine.TEZoroController;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;

public class GridManager {

	public static ArrayList<GridPower> grids = new ArrayList<GridPower>();

	public static final String GUISTRING = "%s / %s ZJ";

	public static int addGridToManager( GridPower grid ) {

		int index = grids.size();
		grids.add(grid);

		return index;
	}

	public static GridPower getGrid( int gridindex ) {

		if (gridindex == -1 || gridindex >= grids.size()) {
			return null;
		}

		return grids.get(gridindex);
	}

	public static GridPower getGridAt( int x, int y, int z, World w, int side ) {
		TileEntity te = w.getBlockTileEntity(x, y, z);

		if (te != null) {

			if (te instanceof TECableBase) {
				TECableBase cable = (TECableBase) te;

				if (cable.getGrid() != null) {
					return cable.getGrid();
				}
			} else if (te instanceof TEZoroController) {
				TEZoroController controller = (TEZoroController) te;

				if (controller.getGrid() != null) {
					return controller.getGrid();
				}
			} else if (te instanceof TETriniumConverter) {
				TETriniumConverter converter = (TETriniumConverter) te;

				if (converter.canConnectOnSide(side)) {
					if (converter.getGrid() != null) {
						return converter.getGrid();
					}
				}
			} else if (te instanceof TEPoweredBase) {
				TEPoweredBase converter = (TEPoweredBase) te;

				if (converter.getGrid() != null) {
					return converter.getGrid();
				}
			}
		}

		return null;
	}

	public static void handleGridPacket( int id, PacketGrid packet ) {

		GridPower grid = getGrid(id);
		if (grid == null) {
			System.out.println("grids was null so created!");
			grids.add(new GridPower());
		}

		grid = getGrid(id);

		grid.handleTilePacket(packet);

	}

	public static void sendUpdatePacket( Side side, World worldObj, int xCoord, int yCoord, int zCoord, int id ) {

		GridPower grid = getGrid(id);
		if (grid != null) {
			if ((Utils.isServerWorld()) && (side == Side.CLIENT)) {
				PacketUtils.sendToPlayers(grid.getDescriptionPacket(), worldObj, xCoord, yCoord, zCoord, 192);

			} else if ((Utils.isClientWorld()) && (side == Side.SERVER)) {
				PacketUtils.sendToServer(grid.getDescriptionPacket());
			}
		}
	}

}
