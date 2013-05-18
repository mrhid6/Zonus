package mrhid6.zonus.proxy;

import java.util.EnumSet;
import mrhid6.zonus.VersionControll;
import mrhid6.zonus.entities.EntityTitan;
import mrhid6.zonus.lib.BlockIds;
import mrhid6.zonus.models.ModelTitan;
import mrhid6.zonus.network.PacketHandler;
import mrhid6.zonus.render.RenderItemNoxiteTurret;
import mrhid6.zonus.render.RenderTECrystalForge;
import mrhid6.zonus.render.RenderTENoxiteTurret;
import mrhid6.zonus.render.RenderTEStearilliumCrafter;
import mrhid6.zonus.render.RenderTETriniumConverter;
import mrhid6.zonus.render.RenderTETriniumMiner;
import mrhid6.zonus.render.RenderTEZoroChest;
import mrhid6.zonus.render.RenderTEZoroController;
import mrhid6.zonus.render.RenderTitan;
import mrhid6.zonus.tileEntity.TEMachineBase;
import mrhid6.zonus.tileEntity.TENoxiteTurret;
import mrhid6.zonus.tileEntity.machine.TECrystalForge;
import mrhid6.zonus.tileEntity.machine.TEStearilliumCrafter;
import mrhid6.zonus.tileEntity.machine.TETriniumConverter;
import mrhid6.zonus.tileEntity.machine.TETriniumMiner;
import mrhid6.zonus.tileEntity.machine.TEZoroChest;
import mrhid6.zonus.tileEntity.machine.TEZoroController;
import mrhid6.zonus.tileEntity.machine.TEZoroFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class clientProxy extends commonProxy implements ITickHandler {

	public static final Minecraft mc = Minecraft.getMinecraft();
	public boolean allHaveCape = false;

	String[] capeUsers = { "mrhid6", "tommo1590", "danzo1997", "Wolfyart", "GreatCannonba11", "Blackout656" };
	private int playerCounter;
	private boolean shownMessage = false;

	public clientProxy() {
		TickRegistry.registerTickHandler(this, Side.CLIENT);
	}

	@Override
	public String getLabel() {
		return "Zonus";
	}

	public boolean playerCanHaveCape( String username ) {

		for (String u : capeUsers) {

			if (u.equalsIgnoreCase(username)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void registerPacketInformation() {
		// TETriniumCore.setDescPacketId(PacketHandler.getAvailablePacketId());
		// TileZoroGen.setDescPacketId(PacketHandler.getAvailablePacketId());
		TEMachineBase.setDescPacketId(PacketHandler.getAvailablePacketId());
		TEZoroFurnace.setGuiPacketId(PacketHandler.getAvailablePacketId());
		TEZoroController.setDescPacketId(PacketHandler.getAvailablePacketId());
		// TETriniumLaserTurret.setDescPacketId(PacketHandler.getAvailablePacketId());
		// TETriniumChillerCore.setDescPacketId(PacketHandler.getAvailablePacketId());
	}

	@Override
	public void registerRenderers() {

		RenderingRegistry.registerEntityRenderingHandler(EntityTitan.class, new RenderTitan(new ModelTitan(), 0.3F));

		ClientRegistry.bindTileEntitySpecialRenderer(TEZoroController.class, new RenderTEZoroController());
		ClientRegistry.bindTileEntitySpecialRenderer(TENoxiteTurret.class, new RenderTENoxiteTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TEStearilliumCrafter.class, new RenderTEStearilliumCrafter());
		ClientRegistry.bindTileEntitySpecialRenderer(TETriniumMiner.class, new RenderTETriniumMiner());
		ClientRegistry.bindTileEntitySpecialRenderer(TETriniumConverter.class, new RenderTETriniumConverter());
		ClientRegistry.bindTileEntitySpecialRenderer(TEZoroChest.class, new RenderTEZoroChest());
		ClientRegistry.bindTileEntitySpecialRenderer(TECrystalForge.class, new RenderTECrystalForge());

		MinecraftForgeClient.registerItemRenderer(BlockIds.getID("noxiteTurret"), new RenderItemNoxiteTurret());

	}

	@Override
	public void tickEnd( EnumSet<TickType> type, Object... tickData ) {
		if (!shownMessage) {
			for (TickType tickType : type) {
				if (tickType == TickType.CLIENT) {
					if (FMLClientHandler.instance().getClient().currentScreen == null) {
						if (VersionControll.getResult() != 0 || VersionControll.getResult() != 3) {

							shownMessage = true;

							if (VersionControll.getResult() == 2) {
								FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage("The current version of Zonus is outdated. A newer version is available at " + VersionControll.updateLocation);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public void tickStart( EnumSet<TickType> type, Object... tickData ) {

		if ((mc.theWorld != null) && (mc.theWorld.playerEntities.size() > 0)) {
			playerCounter += 1;

			if (playerCounter >= mc.theWorld.playerEntities.size()) {
				playerCounter = 0;
			}

			EntityPlayer lplayer = (EntityPlayer) mc.theWorld.playerEntities.get(playerCounter);
			if (allHaveCape || playerCanHaveCape(lplayer.username)) {
				String oldCape = lplayer.cloakUrl;

				lplayer.cloakUrl = "http://profiles.projectminecraft.org/xorbo/modfiles/zoroCape.png";
				if (oldCape != lplayer.cloakUrl) {
					mc.renderEngine.obtainImageData(lplayer.cloakUrl, null);
				}
			}
		}
	}
}
