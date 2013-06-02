package mrhid6.zonus.lib.event;

import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.items.Materials;
import mrhid6.zonus.world.WorldGenHazelspring;
import mrhid6.zonus.world.WorldGenWinterbirch;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class BonemealHandler {

	@ForgeSubscribe
	public void onUseBonemeal( BonemealEvent event ) {
		
		
		if (event.ID == Materials.winterbirchSapling.itemID || event.ID == Materials.hazlespringSapling.itemID) {
			
			if (!event.world.isRemote) {
				int meta = event.world.getBlockMetadata(event.X, event.Y, event.Z);
				
				boolean result = false;
				if(meta == 0){
					result = (new WorldGenWinterbirch()).growTree(event.world, event.X, event.Y, event.Z, event.world.rand);
					
				}else if(meta == 1){
					result = (new WorldGenHazelspring()).growTree(event.world, event.X, event.Y, event.Z, event.world.rand);
				}
				event.setResult(Result.ALLOW);
			}
		}
	}
}
