package mrhid6.zonus.lib.event;

import mrhid6.zonus.block.ModBlocks;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class BonemealHandler {

	@ForgeSubscribe
	public void onUseBonemeal( BonemealEvent event ) {
		if (event.ID == ModBlocks.winterbirchSapling.blockID) {
			if (!event.world.isRemote) {
				ModBlocks.winterbirchSapling.growTree(event.world, event.X, event.Y, event.Z, event.world.rand);
				event.setResult(Result.ALLOW);
			}
		}
	}
}
