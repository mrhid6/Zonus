package mrhid6.zonus.lib;

public class SpiralMatrix {

	public static String[] makeCoords( int size ) {
		String res = "";

		for (int i = -(size / 2); i <= size / 2; i++) {
			for (int j = -(size / 2); j <= size / 2; j++) {
				if (res.equals("")) {
					res = i + "," + j;
				} else {
					res += "#" + i + "," + j;
				}
			}
		}

		String[] data = res.split("#");

		return data;

	}

	public static int[] spiralArray( int dimension ) {

		int numberOfItem = dimension * dimension;
		int[] spiralArr = new int[numberOfItem];
		byte toggle = 0;
		int ds = dimension;
		int cnt = dimension - 1;
		int cntStart = dimension - 1;
		for (int i = 1; i <= dimension; i++) {
			spiralArr[numberOfItem - i] = i;
		}

		for (int i = numberOfItem - dimension; i > 0; i--) {
			spiralArr[i - 1] = spiralArr[i] + ds;
			cnt = cnt - 1;
			if (cnt == 0) {
				ds = (int) (ds * turnFactor(toggle, dimension));
				cntStart = cntStart - toggle;
				cnt = cntStart;
				toggle = (byte) (toggle ^ 1);
			}
		}
		return spiralArr;
	}

	private static float turnFactor( int arg, float n ) {
		if (arg == 0) {
			return -1 / n;
		} else {
			return n;
		}
	}

}
