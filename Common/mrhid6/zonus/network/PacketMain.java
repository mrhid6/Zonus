package mrhid6.zonus.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public abstract class PacketMain {

	protected String channel = "Zonus";
	protected boolean isChunkDataPacket = false;
	protected int packetId;
	protected int packetType;

	public Packet getPacket() {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);
		try {
			data.writeByte(getPacketId());
			data.writeByte(getPacketType());
			writeData(data);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = channel;
		packet.data = bytes.toByteArray();
		packet.length = packet.data.length;
		packet.isChunkDataPacket = isChunkDataPacket;
		return packet;
	}

	public int getPacketId() {
		return packetId;
	}

	public int getPacketType() {
		return packetType;
	}

	public abstract void readData( DataInputStream paramDataInputStream ) throws IOException;

	public abstract void writeData( DataOutputStream paramDataOutputStream ) throws IOException;
}
