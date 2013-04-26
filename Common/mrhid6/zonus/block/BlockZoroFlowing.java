package mrhid6.zonus.block;

import java.util.Random;
import mrhid6.zonus.Zonus;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowing;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.liquids.ILiquid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZoroFlowing extends BlockFlowing implements ILiquid {

	int[] flowCost = new int[4];
	boolean[] isOptimalFlowDirection = new boolean[4];
	int numAdjacentSources = 0;

	protected BlockZoroFlowing( int par1, String name ) {
		super(par1, Material.water);

		setUnlocalizedName(name);
		setHardness(100F);
		setLightOpacity(3);
	}

	private boolean blockBlocksFlow( World par1World, int par2, int par3, int par4 ) {
		int l = par1World.getBlockId(par2, par3, par4);

		if (l != Block.doorWood.blockID && l != Block.doorIron.blockID && l != Block.signPost.blockID && l != Block.ladder.blockID && l != Block.reed.blockID) {
			if (l == 0) {
				return false;
			} else {
				Material material = Block.blocksList[l].blockMaterial;
				return material == Material.portal ? true : material.blocksMovement();
			}
		} else {
			return true;
		}
	}

	private int calculateFlowCost( World world, int i, int j, int k, int l, int i1 ) {
		int j1 = 1000;
		for (int k1 = 0; k1 < 4; k1++) {
			if (k1 == 0 && i1 == 1 || k1 == 1 && i1 == 0 || k1 == 2 && i1 == 3 || k1 == 3 && i1 == 2) {
				continue;
			}
			int l1 = i;
			int i2 = j;
			int j2 = k;
			if (k1 == 0) {
				l1--;
			}
			if (k1 == 1) {
				l1++;
			}
			if (k1 == 2) {
				j2--;
			}
			if (k1 == 3) {
				j2++;
			}
			if (blockBlocksFlow(world, l1, i2, j2) || world.getBlockMaterial(l1, i2, j2) == blockMaterial && world.getBlockMetadata(l1, i2, j2) == 0) {
				continue;
			}
			if (!blockBlocksFlow(world, l1, i2 - 1, j2)) {
				return l;
			}
			if (l >= 4) {
				continue;
			}
			int k2 = calculateFlowCost(world, l1, i2, j2, l + 1, k1);
			if (k2 < j1) {
				j1 = k2;
			}
		}

		return j1;
	}

	private void flowIntoBlock( World world, int i, int j, int k, int l ) {
		if (liquidCanDisplaceBlock(world, i, j, k)) {
			int blockId = world.getBlockId(i, j, k);
			if (blockId > 0) {
				Block.blocksList[blockId].dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			}
			world.setBlock(i, j, k, blockID, l, 3);
		}
	}

	private boolean[] getOptimalFlowDirections( World world, int i, int j, int k ) {
		for (int l = 0; l < 4; l++) {
			flowCost[l] = 1000;
			int j1 = i;
			int i2 = j;
			int j2 = k;
			if (l == 0) {
				j1--;
			}
			if (l == 1) {
				j1++;
			}
			if (l == 2) {
				j2--;
			}
			if (l == 3) {
				j2++;
			}
			if (blockBlocksFlow(world, j1, i2, j2) || world.getBlockMaterial(j1, i2, j2) == blockMaterial && world.getBlockMetadata(j1, i2, j2) == 0) {
				continue;
			}
			if (!blockBlocksFlow(world, j1, i2 - 1, j2)) {
				flowCost[l] = 0;
			} else {
				flowCost[l] = calculateFlowCost(world, j1, i2, j2, 1, l);
			}
		}

		int i1 = flowCost[0];
		for (int k1 = 1; k1 < 4; k1++) {
			if (flowCost[k1] < i1) {
				i1 = flowCost[k1];
			}
		}

		for (int l1 = 0; l1 < 4; l1++) {
			isOptimalFlowDirection[l1] = flowCost[l1] == i1;
		}

		return isOptimalFlowDirection;
	}

	@Override
	protected int getSmallestFlowDecay( World par1World, int par2, int par3, int par4, int par5 ) {
		int i1 = this.getFlowDecay(par1World, par2, par3, par4);

		if (i1 < 0) {
			return par5;
		} else {
			if (i1 == 0) {
				++numAdjacentSources;
			}

			if (i1 >= 8) {
				i1 = 0;
			}

			return par5 >= 0 && i1 >= par5 ? par5 : i1;
		}
	}

	@Override
	public boolean isBlockReplaceable( World world, int i, int j, int k ) {
		return true;
	}

	@Override
	public boolean isMetaSensitive() {
		return false;
	}

	private boolean liquidCanDisplaceBlock( World world, int i, int j, int k ) {
		Material material = world.getBlockMaterial(i, j, k);
		if (material == blockMaterial) {
			return false;
		} else {
			return !blockBlocksFlow(world, i, j, k);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons( IconRegister iconRegister ) {
		theIcon = new Icon[] { iconRegister.registerIcon(Zonus.Modname + "zoroStill"), iconRegister.registerIcon(Zonus.Modname + "zoroFlowing") };
	}

	@Override
	public int stillLiquidId() {
		return ModBlocks.zoroStill.blockID;
	}

	@Override
	public int stillLiquidMeta() {
		return 0;
	}

	private void updateFlow( World par1World, int par2, int par3, int par4 ) {
		int l = par1World.getBlockMetadata(par2, par3, par4);
		par1World.setBlock(par2, par3, par4, blockID + 1, l, 2);
	}

	@Override
	public void updateTick( World world, int x, int y, int z, Random random ) {
		int oldDecay = this.getFlowDecay(world, x, y, z);
		byte increment = 1;
		int flowDecay;

		if (oldDecay > 0) {
			numAdjacentSources = 0;
			int minFlowDecay = this.getSmallestFlowDecay(world, x - 1, y, z, -100);
			minFlowDecay = this.getSmallestFlowDecay(world, x + 1, y, z, minFlowDecay);
			minFlowDecay = this.getSmallestFlowDecay(world, x, y, z - 1, minFlowDecay);
			minFlowDecay = this.getSmallestFlowDecay(world, x, y, z + 1, minFlowDecay);
			flowDecay = minFlowDecay + increment;

			if (flowDecay >= 8 || minFlowDecay < 0) {
				flowDecay = -1;
			}

			int decayAbove = getFlowDecay(world, x, y + 1, z);
			if (decayAbove >= 0) {
				if (decayAbove >= 8) {
					flowDecay = decayAbove;
				} else {
					flowDecay = decayAbove + 8;
				}
			}

			if (flowDecay == oldDecay) {
				this.updateFlow(world, x, y, z);
			} else {
				oldDecay = flowDecay;

				if (flowDecay < 0) {
					world.setBlockToAir(x, y, z);
				} else {
					world.setBlockMetadataWithNotify(x, y, z, flowDecay, 2);
					world.scheduleBlockUpdate(x, y, z, blockID, this.tickRate(world));
					world.notifyBlocksOfNeighborChange(x, y, z, blockID);
				}
			}
		} else {
			this.updateFlow(world, x, y, z);
		}

		if (this.liquidCanDisplaceBlock(world, x, y - 1, z)) {
			if (oldDecay >= 8) {
				this.flowIntoBlock(world, x, y - 1, z, oldDecay);
			} else {
				this.flowIntoBlock(world, x, y - 1, z, oldDecay + 8);
			}
		} else if (oldDecay >= 0 && (oldDecay == 0 || this.blockBlocksFlow(world, x, y - 1, z))) {
			boolean[] flowDirection = this.getOptimalFlowDirections(world, x, y, z);
			flowDecay = oldDecay + increment;

			if (oldDecay >= 8) {
				flowDecay = 1;
			}

			if (flowDecay >= 8) {
				return;
			}

			if (flowDirection[0]) {
				this.flowIntoBlock(world, x - 1, y, z, flowDecay);
			}

			if (flowDirection[1]) {
				this.flowIntoBlock(world, x + 1, y, z, flowDecay);
			}

			if (flowDirection[2]) {
				this.flowIntoBlock(world, x, y, z - 1, flowDecay);
			}

			if (flowDirection[3]) {
				this.flowIntoBlock(world, x, y, z + 1, flowDecay);
			}
		}
	}
}