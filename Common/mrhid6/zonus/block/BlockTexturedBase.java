package mrhid6.zonus.block;

import mrhid6.zonus.Config;
import mrhid6.zonus.Zonus;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public abstract class BlockTexturedBase extends BlockContainer {

	public Icon[] icons;
	private String name;
	protected String textureName;

	public BlockTexturedBase( int id, String texture, String name, boolean craftable ) {
		super(id, Material.ground);

		textureName = texture.toLowerCase();
		this.name = name;
		if (craftable) {
			this.setCreativeTab(Config.creativeTabXor);
		} else {
			this.setCreativeTab(null);
		}
		this.setHardness(89.3F);
		this.setResistance(89.5F);
		this.setStepSound(soundMetalFootstep);
		this.setUnlocalizedName(name);
	}

	@Override
	public TileEntity createNewTileEntity( World world ) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return name;
	}

	@Override
	public void registerIcons( IconRegister iconRegister ) {
		blockIcon = iconRegister.registerIcon(Zonus.Modname + textureName);
	}

}
