package mrhid6.zonus.interfaces;

import mrhid6.zonus.network.PacketTile;

public abstract interface IPacketXorHandler {

	public abstract void handleTilePacket( PacketTile paramPacketTile );
}
