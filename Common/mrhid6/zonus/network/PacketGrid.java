package mrhid6.zonus.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketGrid extends PacketMain {

	public Payload payload;
	public EntityPlayerMP thePlayer;
	public int xCoord;
	public int yCoord;
	public int zCoord;

	public PacketGrid( int id, EntityPlayerMP player ) {
		packetId = id;
		thePlayer = player;
		packetType = 1;
		payload = new Payload();
	}

	public PacketGrid( int id, Payload payload ) {
		packetId = id;
		packetType = 1;

		this.payload = payload;
	}

	@Override
	public void readData( DataInputStream data ) throws IOException {

		payload.readPayloadData(data);

	}

	@Override
	public void writeData( DataOutputStream data ) throws IOException {

		payload.writePayloadData(data);

	}
}
