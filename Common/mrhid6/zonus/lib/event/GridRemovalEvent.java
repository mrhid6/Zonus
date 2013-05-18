package mrhid6.zonus.lib.event;

import net.minecraftforge.event.Event;

public class GridRemovalEvent extends Event {

	public int gridId;

	public GridRemovalEvent( int i ) {
		gridId = i;
	}

}
