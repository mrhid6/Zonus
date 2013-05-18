package mrhid6.zonus.lib.event;

import net.minecraftforge.event.ForgeSubscribe;

public class GridEventHandler {

	public GridEventHandler() {

	}

	@ForgeSubscribe
	public void OnGridRemoval( GridRemovalEvent event ) {
		// GridManager.removeGrid(event.gridId);
	}

}
