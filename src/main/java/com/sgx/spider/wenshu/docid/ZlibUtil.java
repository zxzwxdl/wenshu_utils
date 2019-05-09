package com.sgx.spider.wenshu.docid;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class ZlibUtil {
    public static String unzip(String base64data) {
        byte[] decodedData = Base64.getDecoder().decode(base64data);
        String strData = new String(decodedData);

        byte[] byteArray = new byte[strData.length()];
        for (int i = 0; i < strData.length(); i++) {
            char c = strData.charAt(i);
            byteArray[i] = (byte) c;
        }

        byte[] decompressedData = decompress(byteArray);
        return new String(decompressedData);
    }

    private static byte[] decompress(byte[] byteArray) {
        byte[] returnValues = null;

        Inflater inflater = new Inflater(true);
        inflater.setInput(byteArray);

        List<Byte> bytesDecompressedSoFar = new ArrayList<>();
        try {
            while (!inflater.needsInput()) {
                byte[] bytesDecompressedBuffer = new byte[byteArray.length];

                int numberOfBytesDecompressedThisTime = inflater.inflate(bytesDecompressedBuffer);

                for (int b = 0; b < numberOfBytesDecompressedThisTime; b++) {
                    bytesDecompressedSoFar.add(bytesDecompressedBuffer[b]);
                }
            }

            returnValues = new byte[bytesDecompressedSoFar.size()];
            for (int b = 0; b < returnValues.length; b++) {
                returnValues[b] = bytesDecompressedSoFar.get(b);
            }
        } catch (DataFormatException dfe) {
            dfe.printStackTrace();
        }

        inflater.end();

        return returnValues;
    }
}
