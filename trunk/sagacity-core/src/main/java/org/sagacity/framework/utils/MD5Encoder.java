/**
 * 
 */
package org.sagacity.framework.utils;

import java.io.UnsupportedEncodingException;

/**
 * 
 *@project sagacity-core 
 *@description:$<p>Md5加密</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:MD5Encoder.java,Revision:v1.0,Date:2008-12-14 下午10:03:57 $
 */
public class MD5Encoder {
	private final int BITS_TO_A_BYTE = 8;
	private final int BYTES_TO_A_WORD = 4;
	private final int BITS_TO_A_WORD = 32;

	private long[] m_lOnBits = new long[31];
	private long[] m_l2Power = new long[31];
	private long a, b, c, d;

	private long LShift(long lValue, short iShiftBits) {
		long LShift;
		if (iShiftBits == 0) {
			LShift = lValue;
			return LShift;
		} else if (iShiftBits == 31) {
			if ((lValue & 1) != 0) {
				LShift = 0x80000000;
			} else {
				LShift = 0;
			}
			return LShift;
		} else if (iShiftBits < 0 || iShiftBits > 31) {
			System.out.println("Out Of Ranger!!");
		}

		if ((lValue & m_l2Power[31 - iShiftBits]) != 0) {
			LShift = ((lValue & m_lOnBits[31 - (iShiftBits + 1)]) * m_l2Power[iShiftBits]) | 0x80000000;
		} else {
			LShift = ((lValue & m_lOnBits[31 - iShiftBits]) * m_l2Power[iShiftBits]);
		}
		return LShift;
	}

	private long RShift(long lValue, short iShiftBits) {
		long RShift;
		if (iShiftBits == 0) {
			RShift = lValue;
			return RShift;
		} else if (iShiftBits == 31) {
			if ((lValue & 0x80000000) == 0) {
				RShift = 1;
			} else {
				RShift = 0;
			}
			return RShift;
		} else if (iShiftBits < 0 || iShiftBits > 31) {
			System.out.println("Out Of Ranger!!");
		}
		RShift = (long) ((lValue & 0x7FFFFFFE) / m_l2Power[iShiftBits]);

		if ((lValue & 0x80000000) != 0) {
			RShift = (RShift | (long) (0x40000000 / m_l2Power[iShiftBits - 1]));
			return RShift;
		}
		return RShift;
	}

	private long RotateLeft(long lValue, short iShiftBits) {
		long RotateLeft;
		RotateLeft = LShift(lValue, iShiftBits)
				| RShift(lValue, (short) (32 - iShiftBits));
		return RotateLeft;
	}

	private long AddUnsigned(long lX, long lY) {
		long lX4;
		long lY4;
		long lX8;
		long lY8;
		long lResult;

		lX8 = lX & 0x80000000;
		lY8 = lY & 0x80000000;
		lX4 = lX & 0x40000000;
		lY4 = lY & 0x40000000;

		lResult = (lX & 0x3FFFFFFF) + (lY & 0x3FFFFFFF);

		if ((lX4 & lY4) != 0) {
			lResult = lResult ^ 0x80000000 ^ lX8 ^ lY8;
		} else if ((lX4 | lY4) != 0) {
			if ((lResult & 0x40000000) != 0) {
				lResult = lResult ^ 0xC0000000 ^ lX8 ^ lY8;
			} else {
				lResult = lResult ^ 0x40000000 ^ lX8 ^ lY8;
			}
		} else {
			lResult = lResult ^ lX8 ^ lY8;
		}
		return lResult;
	}

	private long md5_F(long x, long y, long z) {
		long result;
		return result = (x & y) | ((~x) & z);
	}

	private long md5_G(long x, long y, long z) {
		long result;
		return result = (x & z) | (y & (~z));
	}

	private long md5_H(long x, long y, long z) {
		long result;
		return result = (x ^ y ^ z);
	}

	private long md5_I(long x, long y, long z) {
		long result;
		return result = (y ^ (x | (~z)));
	}

	private long md5_FF(long a, long b, long c, long d, long x, short s, long ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(md5_F(b, c, d), x), ac));
		a = RotateLeft(a, s);
		a = AddUnsigned(a, b);
		return a;
	}

	private long md5_GG(long a, long b, long c, long d, long x, short s, long ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(md5_G(b, c, d), x), ac));
		a = RotateLeft(a, s);
		return a = AddUnsigned(a, b);
	}

	private long md5_HH(long a, long b, long c, long d, long x, short s, long ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(md5_H(b, c, d), x), ac));
		a = RotateLeft(a, s);
		return a = AddUnsigned(a, b);
	}

	private long md5_II(long a, long b, long c, long d, long x, short s, long ac) {
		a = AddUnsigned(a, AddUnsigned(AddUnsigned(md5_I(b, c, d), x), ac));
		a = RotateLeft(a, s);
		return a = AddUnsigned(a, b);
	}

	private long[] ConvertToWordArray(String sMessage) {
		int lMessageLength;
		int lNumberOfWords;
		short lBytePosition;
		int lByteCount;
		int lWordCount;
		long result[];

		final int MODULUS_BITS = 512;
		final int CONGRUENT_BITS = 448;

		lMessageLength = sMessage.length();

		lNumberOfWords = (((lMessageLength + (int) ((MODULUS_BITS - CONGRUENT_BITS) / BITS_TO_A_BYTE)) / (int) (MODULUS_BITS / BITS_TO_A_BYTE)) + 1)
				* (int) (MODULUS_BITS / BITS_TO_A_WORD);
		long[] lWordArray = new long[lNumberOfWords];
		lBytePosition = 0;
		lByteCount = 0;

		while (lByteCount < lMessageLength) {
			lWordCount = (int) (lByteCount / BYTES_TO_A_WORD);
			lBytePosition = (short) ((lByteCount % BYTES_TO_A_WORD) * BITS_TO_A_BYTE);
			try {
				lWordArray[lWordCount] = lWordArray[lWordCount]
						| LShift(Character
								.toString(sMessage.charAt(lByteCount))
								.getBytes("US-ASCII")[0], lBytePosition);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			lByteCount = lByteCount + 1;
		}

		lWordCount = (int) (lByteCount / BYTES_TO_A_WORD);
		lBytePosition = (short) ((lByteCount % BYTES_TO_A_WORD) * BITS_TO_A_BYTE);

		lWordArray[lWordCount] = lWordArray[lWordCount]
				| LShift(0x80, lBytePosition);

		lWordArray[lNumberOfWords - 2] = LShift(lMessageLength, (short) 3);
		lWordArray[lNumberOfWords - 1] = RShift(lMessageLength, (short) 29);
		result = lWordArray;
		return result;
	}

	private String WordToHex(long lValue) {
		long lByte;
		int lCount;
		String result = new String();

		for (lCount = 0; lCount <= 3; lCount++) {
			lByte = (long) (RShift(lValue, (short) (lCount * BITS_TO_A_BYTE)) & m_lOnBits[BITS_TO_A_BYTE - 1]);
			String pro = ("0" + Long.toHexString(lByte));
			result = result + pro.substring(pro.length() - 2, pro.length());
		}
		return result;
	}

	public String md5(String sMessage) {
		String result = null;

		m_lOnBits[0] = 1;
		m_lOnBits[1] = 3;
		m_lOnBits[2] = 7;
		m_lOnBits[3] = 15;
		m_lOnBits[4] = 31;
		m_lOnBits[5] = 63;
		m_lOnBits[6] = 127;
		m_lOnBits[7] = 255;
		m_lOnBits[8] = 511;
		m_lOnBits[9] = 1023;
		m_lOnBits[10] = 2047;
		m_lOnBits[11] = 4095;
		m_lOnBits[12] = 8191;
		m_lOnBits[13] = 16383;
		m_lOnBits[14] = 32767;
		m_lOnBits[15] = 65535;
		m_lOnBits[16] = 131071;
		m_lOnBits[17] = 262143;
		m_lOnBits[18] = 524287;
		m_lOnBits[19] = 1048575;
		m_lOnBits[20] = 2097151;
		m_lOnBits[21] = 4194303;
		m_lOnBits[22] = 8388607;
		m_lOnBits[23] = 16777215;
		m_lOnBits[24] = 33554431;
		m_lOnBits[25] = 67108863;
		m_lOnBits[26] = 134217727;
		m_lOnBits[27] = 268435455;
		m_lOnBits[28] = 536870911;
		m_lOnBits[29] = 1073741823;
		m_lOnBits[30] = 2147483647;

		m_l2Power[0] = 1;
		m_l2Power[1] = 2;
		m_l2Power[2] = 4;
		m_l2Power[3] = 8;
		m_l2Power[4] = 16;
		m_l2Power[5] = 32;
		m_l2Power[6] = 64;
		m_l2Power[7] = 128;
		m_l2Power[8] = 256;
		m_l2Power[9] = 512;
		m_l2Power[10] = 1024;
		m_l2Power[11] = 2048;
		m_l2Power[12] = 4096;
		m_l2Power[13] = 8192;
		m_l2Power[14] = 16384;
		m_l2Power[15] = 32768;
		m_l2Power[16] = 65536;
		m_l2Power[17] = 131072;
		m_l2Power[18] = 262144;
		m_l2Power[19] = 524288;
		m_l2Power[20] = 1048576;
		m_l2Power[21] = 2097152;
		m_l2Power[22] = 4194304;
		m_l2Power[23] = 8388608;
		m_l2Power[24] = 16777216;
		m_l2Power[25] = 33554432;
		m_l2Power[26] = 67108864;
		m_l2Power[27] = 134217728;
		m_l2Power[28] = 268435456;
		m_l2Power[29] = 536870912;
		m_l2Power[30] = 1073741824;

		long[] x = null;
		long AA;
		long BB;
		long CC;
		long DD;

		final int S11 = 7;
		final int S12 = 12;
		final int S13 = 17;
		final int S14 = 22;
		final int S21 = 5;
		final int S22 = 9;
		final int S23 = 14;
		final int S24 = 20;
		final int S31 = 4;
		final int S32 = 11;
		final int S33 = 16;
		final int S34 = 23;
		final int S41 = 6;
		final int S42 = 10;
		final int S43 = 15;
		final int S44 = 21;

		x = ConvertToWordArray(sMessage);

		a = 0x67452301;
		b = 0xEFCDAB89;
		c = 0x98BADCFE;
		d = 0x10325476;

		for (int k = 0; k < x.length; k += 16) {

			AA = a;
			BB = b;
			CC = c;
			DD = d;

			a = md5_FF(a, b, c, d, x[k + 0], (short) S11, 0xD76AA478);
			d = md5_FF(d, a, b, c, x[k + 1], (short) S12, 0xE8C7B756);
			c = md5_FF(c, d, a, b, x[k + 2], (short) S13, 0x242070DB);
			b = md5_FF(b, c, d, a, x[k + 3], (short) S14, 0xC1BDCEEE);
			a = md5_FF(a, b, c, d, x[k + 4], (short) S11, 0xF57C0FAF);
			d = md5_FF(d, a, b, c, x[k + 5], (short) S12, 0x4787C62A);
			c = md5_FF(c, d, a, b, x[k + 6], (short) S13, 0xA8304613);
			b = md5_FF(b, c, d, a, x[k + 7], (short) S14, 0xFD469501);
			a = md5_FF(a, b, c, d, x[k + 8], (short) S11, 0x698098D8);
			d = md5_FF(d, a, b, c, x[k + 9], (short) S12, 0x8B44F7AF);
			c = md5_FF(c, d, a, b, x[k + 10], (short) S13, 0xFFFF5BB1);
			b = md5_FF(b, c, d, a, x[k + 11], (short) S14, 0x895CD7BE);
			a = md5_FF(a, b, c, d, x[k + 12], (short) S11, 0x6B901122);
			d = md5_FF(d, a, b, c, x[k + 13], (short) S12, 0xFD987193);
			c = md5_FF(c, d, a, b, x[k + 14], (short) S13, 0xA679438E);
			b = md5_FF(b, c, d, a, x[k + 15], (short) S14, 0x49B40821);

			a = md5_GG(a, b, c, d, x[k + 1], (short) S21, 0xF61E2562);
			d = md5_GG(d, a, b, c, x[k + 6], (short) S22, 0xC040B340);
			c = md5_GG(c, d, a, b, x[k + 11], (short) S23, 0x265E5A51);
			b = md5_GG(b, c, d, a, x[k + 0], (short) S24, 0xE9B6C7AA);
			a = md5_GG(a, b, c, d, x[k + 5], (short) S21, 0xD62F105D);
			d = md5_GG(d, a, b, c, x[k + 10], (short) S22, 0x2441453);
			c = md5_GG(c, d, a, b, x[k + 15], (short) S23, 0xD8A1E681);
			b = md5_GG(b, c, d, a, x[k + 4], (short) S24, 0xE7D3FBC8);
			a = md5_GG(a, b, c, d, x[k + 9], (short) S21, 0x21E1CDE6);
			d = md5_GG(d, a, b, c, x[k + 14], (short) S22, 0xC33707D6);
			c = md5_GG(c, d, a, b, x[k + 3], (short) S23, 0xF4D50D87);
			b = md5_GG(b, c, d, a, x[k + 8], (short) S24, 0x455A14ED);
			a = md5_GG(a, b, c, d, x[k + 13], (short) S21, 0xA9E3E905);
			d = md5_GG(d, a, b, c, x[k + 2], (short) S22, 0xFCEFA3F8);
			c = md5_GG(c, d, a, b, x[k + 7], (short) S23, 0x676F02D9);
			b = md5_GG(b, c, d, a, x[k + 12], (short) S24, 0x8D2A4C8A);

			a = md5_HH(a, b, c, d, x[k + 5], (short) S31, 0xFFFA3942);
			d = md5_HH(d, a, b, c, x[k + 8], (short) S32, 0x8771F681);
			c = md5_HH(c, d, a, b, x[k + 11], (short) S33, 0x6D9D6122);
			b = md5_HH(b, c, d, a, x[k + 14], (short) S34, 0xFDE5380C);
			a = md5_HH(a, b, c, d, x[k + 1], (short) S31, 0xA4BEEA44);
			d = md5_HH(d, a, b, c, x[k + 4], (short) S32, 0x4BDECFA9);
			c = md5_HH(c, d, a, b, x[k + 7], (short) S33, 0xF6BB4B60);
			b = md5_HH(b, c, d, a, x[k + 10], (short) S34, 0xBEBFBC70);
			a = md5_HH(a, b, c, d, x[k + 13], (short) S31, 0x289B7EC6);
			d = md5_HH(d, a, b, c, x[k + 0], (short) S32, 0xEAA127FA);
			c = md5_HH(c, d, a, b, x[k + 3], (short) S33, 0xD4EF3085);
			b = md5_HH(b, c, d, a, x[k + 6], (short) S34, 0x4881D05);
			a = md5_HH(a, b, c, d, x[k + 9], (short) S31, 0xD9D4D039);
			d = md5_HH(d, a, b, c, x[k + 12], (short) S32, 0xE6DB99E5);
			c = md5_HH(c, d, a, b, x[k + 15], (short) S33, 0x1FA27CF8);
			b = md5_HH(b, c, d, a, x[k + 2], (short) S34, 0xC4AC5665);

			a = md5_II(a, b, c, d, x[k + 0], (short) S41, 0xF4292244);
			d = md5_II(d, a, b, c, x[k + 7], (short) S42, 0x432AFF97);
			c = md5_II(c, d, a, b, x[k + 14], (short) S43, 0xAB9423A7);
			b = md5_II(b, c, d, a, x[k + 5], (short) S44, 0xFC93A039);
			a = md5_II(a, b, c, d, x[k + 12], (short) S41, 0x655B59C3);
			d = md5_II(d, a, b, c, x[k + 3], (short) S42, 0x8F0CCC92);
			c = md5_II(c, d, a, b, x[k + 10], (short) S43, 0xFFEFF47D);
			b = md5_II(b, c, d, a, x[k + 1], (short) S44, 0x85845DD1);
			a = md5_II(a, b, c, d, x[k + 8], (short) S41, 0x6FA87E4F);
			d = md5_II(d, a, b, c, x[k + 15], (short) S42, 0xFE2CE6E0);
			c = md5_II(c, d, a, b, x[k + 6], (short) S43, 0xA3014314);
			b = md5_II(b, c, d, a, x[k + 13], (short) S44, 0x4E0811A1);
			a = md5_II(a, b, c, d, x[k + 4], (short) S41, 0xF7537E82);
			d = md5_II(d, a, b, c, x[k + 11], (short) S42, 0xBD3AF235);
			c = md5_II(c, d, a, b, x[k + 2], (short) S43, 0x2AD7D2BB);
			b = md5_II(b, c, d, a, x[k + 9], (short) S44, 0xEB86D391);

			a = AddUnsigned(a, AA);
			b = AddUnsigned(b, BB);
			c = AddUnsigned(c, CC);
			d = AddUnsigned(d, DD);
		}
		// result = ( WordToHex(a)+WordToHex(b) + WordToHex(c)+
		// WordToHex(d)).toLowerCase();
		result = (WordToHex(b) + WordToHex(c)).toLowerCase();
		return result;
	}
	
	public static void main(String[] args)
	{
		MD5Encoder encode=new MD5Encoder();
		String password="eb629cc338d02760";
		System.err.println(encode.md5("4005                123456"));
		
	}
}
