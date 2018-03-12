package com.ef.intergration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class File {

	public static BufferedReader getFileBufferReader(String filePath) throws FileNotFoundException {
		// create file buffer reader
		return new BufferedReader(new FileReader(filePath));
	}
}
