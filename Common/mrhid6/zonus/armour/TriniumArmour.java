package mrhid6.zonus.armour;

import mrhid6.zonus.Config;
import mrhid6.zonus.Zonus;
import mrhid6.zonus.items.ModItems;
import mrhid6.zonus.lib.Utils;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IArmorTextureProvider;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TriniumArmour extends ItemArmor implements IArmorTextureProvider {

	public TriniumArmour( int id, EnumArmorMaterial enumMaterial, int par3, int par4 ) {
		super(id, enumMaterial, par3, par4);
		setCreativeTab(Config.creativeTabXor);

	}

	@Override
	public String getArmorTextureFile( ItemStack stack ) {

		if (stack.itemID == ModItems.triniumPlate.itemID || stack.itemID == ModItems.triniumHelm.itemID || stack.itemID == ModItems.triniumBoots.itemID) {
			return "/mods/zonus/textures/armor/trinium_1.png";
		}

		return "/mods/zonus/textures/armor/trinium_2.png";

	}

	@ForgeSubscribe
	public void onEntityLivingFallEvent( LivingFallEvent event ) {
		System.out.println("fall event was called!");
		if (Utils.isServerWorld() && event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			ItemStack armor = player.inventory.armorInventory[0];

			if ((armor != null) && (armor.itemID == itemID)) {
				event.setCanceled(true);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons( IconRegister iconRegister ) {

		switch (armorType) {
		case 0:
			itemIcon = iconRegister.registerIcon(Zonus.Modname + "triniumhelm");
			break;
		case 1:
			itemIcon = iconRegister.registerIcon(Zonus.Modname + "triniumplate");
			break;
		case 2:
			itemIcon = iconRegister.registerIcon(Zonus.Modname + "triniumlegs");
			break;
		case 3:
			itemIcon = iconRegister.registerIcon(Zonus.Modname + "triniumboots");
			break;
		}
	}

}
