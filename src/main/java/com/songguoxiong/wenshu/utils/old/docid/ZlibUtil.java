package com.songguoxiong.wenshu.utils.old.docid;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class ZlibUtil {
    public static String unzip(String base64data) throws DataFormatException {
        String string = new String(Base64.getDecoder().decode(base64data));

        byte[] byteArray = new byte[string.length()];
        for (int i = 0; i < string.length(); i++) {
            byteArray[i] = (byte) string.charAt(i);
        }

        return new String(decompress(byteArray));
    }

    private static byte[] decompress(byte[] input) throws DataFormatException {
        Inflater inflater = new Inflater(true);
        inflater.setInput(input);

        List<Byte> byteList = new ArrayList<>();
        while (!inflater.needsInput()) {
            byte[] byteArray = new byte[input.length];

            int numberOfBytesDecompressedThisTime = inflater.inflate(byteArray);

            for (int i = 0; i < numberOfBytesDecompressedThisTime; i++) {
                byteList.add(byteArray[i]);
            }
        }

        inflater.end();

        byte[] output = new byte[byteList.size()];
        for (int i = 0; i < output.length; i++) {
            output[i] = byteList.get(i);
        }

        return output;
    }
}
