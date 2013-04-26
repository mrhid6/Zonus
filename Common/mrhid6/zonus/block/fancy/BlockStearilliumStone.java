package mrhid6.zonus.block.fancy;

import mrhid6.zonus.block.BlockTexturedBase;

public class BlockStearilliumStone extends BlockTexturedBase {

	public BlockStearilliumStone( int id, String name ) {
		super(id, name, name, true);

		this.setResistance(4.0F);
		this.setHardness(5.0F);
	}

}
