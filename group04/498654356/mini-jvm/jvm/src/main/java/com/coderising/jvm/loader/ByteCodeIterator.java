package com.coderising.jvm.loader;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ByteCodeIterator {
	private byte[] codes;
	private int index;

	public ByteCodeIterator(byte[] codes) {
		super();
		this.codes = codes;
	}

	
	public void skip(int index) {
		this.index = index;
	}


	public int next2ByteToInt() {
		return converToInt(codes[index++],codes[index++]);
	}

	public int next1ByteToInt() {
		return converToInt(codes[index++]);
	}

	/**
	 * big-endian
	 */
	private int converToInt(byte ... bs) {
		int result = 0;
		for(int size = bs.length, index = 0; size > 0; size--, index++) {
			if(size == 1) {
				result += bs[index] & 0xFF;	//符号位
			} else {
				result += bs[index] << ((size -1) * 8);
			}
		}
		return result;
	}


	public String nextLengthByteToString(int length) {
		byte[] bytes = Arrays.copyOfRange(codes, index, index + length);
		String str = null;
		try {
			str = new String(bytes, "UTF-8");
			index += length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
}
