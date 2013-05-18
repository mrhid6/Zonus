package mrhid6.zonus.block.fancy;

import mrhid6.zonus.Config;
import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.BlockTexturedBase;
import mrhid6.zonus.lib.BlockCoord;
import mrhid6.zonus.lib.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStearilliumGlass extends BlockTexturedBase {

	private BlockCoord coord = new BlockCoord();
	private int[] textureIndexMap = new int[256];

	public BlockStearilliumGlass( int id, String name ) {
		super(id, name, name, true);

		this.setResistance(4.0F);
		this.setHardness(5.0F);
		this.setLightOpacity(0);

		icons = new Icon[60];

		loadTextureMap();
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture( IBlockAccess iBlockAccess, int x, int y, int z, int side ) {
		return icons[getBlockTextureForConnect(iBlockAccess, x, y, z, side)];
	}

	public int getBlockTextureForConnect( IBlockAccess iBlockAccess, int x, int y, int z, int side ) {
		int[][] sideSideMap = { { 2, 5, 3, 4 }, { 2, 5, 3, 4 }, { 1, 4, 0, 5 }, { 1, 5, 0, 4 }, { 1, 3, 0, 2 }, { 1, 2, 0, 3 } };

	    int map = 0;
	    for (int b = 0; b < 4; b++)
	    {
	      int side0 = sideSideMap[side][((b + 3) % 4)];
	      int side1 = sideSideMap[side][b];
	      if (!canConnectOnSide(iBlockAccess, this.coord.set(x, y, z), sideSideMap[side][b], side))
	        map |= (7 << b * 2) % 256 | 7 >>> 8 - b * 2;
	      else if ((!canConnectOnSide(iBlockAccess, this.coord.set(x, y, z).offset(side0), side1, side)) || (!canConnectOnSide(iBlockAccess, this.coord.set(x, y, z).offset(side1), side0, side))) {
	        map |= 1 << b * 2;
	      }
	    }
	    return getTextureFromMap(iBlockAccess,map,side, x, y, z);
	}

	private int getBlockID(IBlockAccess iBlockAccess, BlockCoord coord)
	{
		return iBlockAccess.getBlockId(coord.x, coord.y, coord.z);
	}

	public boolean canConnectOnSide(IBlockAccess iBlockAccess, BlockCoord coord, int side, int face)
	{
		int block = getBlockID(iBlockAccess, coord.offset(side));
		int blockabove = getBlockID(iBlockAccess, coord.offset(face));
		
		return ((block == this.blockID) && (blockabove != this.blockID));
	}

	public int getTextureFromMap(IBlockAccess iBlockAccess, int map, int side,int x,int y,int z)
	{
		boolean[] connected = new boolean[6];
		for(int i=0;i<6;i++){
			
			int x1 = x + Config.SIDE_COORD_MOD[i][0];
			int y1 = y + Config.SIDE_COORD_MOD[i][1];
			int z1 = z + Config.SIDE_COORD_MOD[i][2];
			
			int id = iBlockAccess.getBlockId(x1, y1, z1);
			
			if(side == i && id==this.blockID){
				return 12;
			}
		}
		
		return this.textureIndexMap[map];
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onBlockAdded( World par1World, int par2, int par3, int par4 ) {
		super.onBlockAdded(par1World, par2, par3, par4);
		Utils.createReactor(null, null, par1World, par2, par3, par4);
	}

	@Override
	public void onBlockPlacedBy( World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack ) {
		super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving, par6ItemStack);

		Utils.createReactor(par6ItemStack, (EntityPlayer) par5EntityLiving, par1World, par2, par3, par4);
	}
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
	{
		return true;
	}
	@Override
	public void registerIcons( IconRegister iconRegister ) {
		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Zonus.Modname + "sGlass_" + getIconFileId(i));
		}
		super.registerIcons(iconRegister);
	}
	  private void loadTextureMap()
	  {
	    this.textureIndexMap[0] = 17;
	    this.textureIndexMap[1] = 24;
	    this.textureIndexMap[4] = 23;
	    this.textureIndexMap[5] = 27;
	    this.textureIndexMap[7] = 1;
	    this.textureIndexMap[16] = 7;
	    this.textureIndexMap[17] = 43;
	    this.textureIndexMap[20] = 53;
	    this.textureIndexMap[21] = 25;
	    this.textureIndexMap[23] = 39;
	    this.textureIndexMap[28] = 18;
	    this.textureIndexMap[29] = 58;
	    this.textureIndexMap[31] = 2;
	    this.textureIndexMap[64] = 8;
	    this.textureIndexMap[65] = 54;
	    this.textureIndexMap[68] = 59;
	    this.textureIndexMap[69] = 26;
	    this.textureIndexMap[71] = 40;
	    this.textureIndexMap[80] = 11;
	    this.textureIndexMap[81] = 10;
	    this.textureIndexMap[84] = 9;
	    this.textureIndexMap[85] = 21;
	    this.textureIndexMap[87] = 5;
	    this.textureIndexMap[92] = 42;
	    this.textureIndexMap[93] = 22;
	    this.textureIndexMap[95] = 6;
	    this.textureIndexMap[112] = 33;
	    this.textureIndexMap[113] = 56;
	    this.textureIndexMap[116] = 55;
	    this.textureIndexMap[117] = 37;
	    this.textureIndexMap[119] = 49;
	    this.textureIndexMap[124] = 34;
	    this.textureIndexMap[125] = 38;
	    this.textureIndexMap[127] = 50;
	    this.textureIndexMap['Á'] = 16;
	    this.textureIndexMap['Å'] = 57;
	    this.textureIndexMap['Ç'] = 0;
	    this.textureIndexMap['Ñ'] = 41;
	    this.textureIndexMap['Õ'] = 20;
	    this.textureIndexMap['×'] = 4;
	    this.textureIndexMap['Ý'] = 19;
	    this.textureIndexMap['ß'] = 3;
	    this.textureIndexMap['ñ'] = 32;
	    this.textureIndexMap['õ'] = 36;
	    this.textureIndexMap['÷'] = 48;
	    this.textureIndexMap['ý'] = 35;
	    this.textureIndexMap['ÿ'] = 51;
	  }

	public int getIconFileId(int id){
		if(id>=12 && id<16){
			return 12;
		}

		if(id>=28 && id<32){
			return 12;
		}
		if(id>=44 && id<48){
			return 12;
		}
		if(id == 52){
			return 12;
		}

		return id;
	}
}
