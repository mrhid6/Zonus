package mrhid6.zonus.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Payload {

	public boolean[] boolPayload = new boolean[0];
	public byte[] bytePayload = new byte[0];
	public float[] floatPayload = new float[0];
	public int[] intPayload = new int[0];
	public String[] stringPayload = new String[0];

	public Payload() {
	}

	public Payload( int boolSize, int byteSize, int intSize, int floatSize, int stringSize ) {
		boolPayload = new boolean[boolSize];
		bytePayload = new byte[byteSize];
		intPayload = new int[intSize];
		floatPayload = new float[floatSize];
		stringPayload = new String[stringSize];
	}

	public void readPayloadData( DataInputStream data ) throws IOException {
		boolPayload = new boolean[data.readByte()];
		bytePayload = new byte[data.readByte()];
		intPayload = new int[data.readByte()];
		floatPayload = new float[data.readByte()];
		stringPayload = new String[data.readByte()];

		for (int i = 0; i < boolPayload.length; i++) {
			boolPayload[i] = data.readBoolean();
		}
		for (int i = 0; i < bytePayload.length; i++) {
			bytePayload[i] = data.readByte();
		}
		for (int i = 0; i < intPayload.length; i++) {
			intPayload[i] = data.readInt();
		}
		for (int i = 0; i < floatPayload.length; i++) {
			floatPayload[i] = data.readFloat();
		}
		for (int i = 0; i < stringPayload.length; i++) {
			stringPayload[i] = data.readUTF();
		}

	}

	@Override
	public String toString() {
		return "Payload [boolPayload=" + Arrays.toString(boolPayload) + ", bytePayload=" + Arrays.toString(bytePayload) + ", intPayload=" + Arrays.toString(intPayload) + ", floatPayload=" + Arrays.toString(floatPayload) + ", stringPayload=" + Arrays.toString(stringPayload) + "]";
	}

	public void writePayloadData( DataOutputStream data ) throws IOException {
		data.writeByte(boolPayload.length);
		data.writeByte(bytePayload.length);
		data.writeByte(intPayload.length);
		data.writeByte(floatPayload.length);
		data.writeByte(stringPayload.length);

		for (boolean boolData : boolPayload) {
			data.writeBoolean(boolData);
		}
		for (byte byteData : bytePayload) {
			data.writeByte(byteData);
		}
		for (int intData : intPayload) {
			data.writeInt(intData);
		}
		for (float floatData : floatPayload) {
			data.writeFloat(floatData);
		}
		for (String stringData : stringPayload) {
			data.writeUTF(stringData);
		}
	}
}
