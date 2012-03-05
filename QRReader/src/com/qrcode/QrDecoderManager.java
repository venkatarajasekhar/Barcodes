///////////////////////////////////////////////////////////////////////////////
// Project:    QR Reader for Android
// Package:    com.qrcode
// File:       QrDecoderManager.java
// Date:       March 2012
// Author:     Radim Loskot
// E-mail:     xlosko01(at)stud.fit.vutbr.cz
//
// Brief:      Contains the class which provides interface for decoding the 
//             QR codes.
///////////////////////////////////////////////////////////////////////////////

package com.qrcode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.qrcode.decoders.QrDecoder;
import com.qrcode.qrcodes.QrCode;

/**
 * The Class QrDecoderManager provides the interface for decoding 
 * the {@link com.qrcode.qrcodes.QrCode QR codes} from the byte array without knowing
 * the type of the QR code before decoding. The decoding is based on iterative 
 * trying of the specific decoders. When one decoders fails, the next decoder tries 
 * the luck.
 * <p>
 * This classes uses only statically loaded decoders which in the package
 * {@link com.qrcode.decoders}. For using custom decoders can be used the
 * static method {@link #decodeQrCode(ArrayList, byte[])}.
 * 
 * @version 1.0
 */
final public class QrDecoderManager {
    
    /** The constants that contains the classes of the decoders. */
    final private static List<String> DECODER_CLASSES = Arrays.asList(
            "com.qrcode.decoders.BaseQrDecoder"
    );
    
    /** The delimiter of the URI scheme. */
    final public static char URI_SCHEME_DELIMITER = ':';
    
    /** The character with the first printable char of the ASCII. */
    final public static char FIRST_PRINTABLE_CHAR = ' ';
    
    /** The instance of this manager. */
    private static QrDecoderManager qrDecoderManager;
    
    /** The list with loaded decoders. */
    private ArrayList<QrDecoder> decoders = new ArrayList<QrDecoder>();
    
    /**
     * Instantiates a new QR decoder manager.
     *
     * @throws ClassNotFoundException Signals that the class cannot be found.
     * @throws InstantiationException The instantiation exception.
     * @throws IllegalAccessException The illegal access exception.
     */
    private QrDecoderManager () throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // Loads and instantiate the decoders
        for (String decoderClassName : DECODER_CLASSES) {
            decoders.add((QrDecoder) Class.forName(decoderClassName).newInstance());
        }
    }
    
    /**
     * Decodes the QR code.
     *
     * @param The byte array that should contain the serialized QR code object.
     * @return The specific QR code object on success or null when no proprietary
     * decoder has been found.
     */
    public QrCode decodeQrCode(byte[] data) {
        return decodeQrCode(decoders, data);
    }
    
    /**
     * Gets the list of used decoders.
     *
     * @return The list of used decoders
     */
    public ArrayList<QrDecoder> getDecoders() {
        return decoders;
    }
    
    /**
     * Gets the instance of the decoder manager.
     *
     * @return The instance of the decoder manager.
     */
    public static QrDecoderManager getDecoderManager() {
        try {
            return qrDecoderManager = (qrDecoderManager == null)? new QrDecoderManager() : qrDecoderManager;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        
        return qrDecoderManager = null;
    }
    
    /**
     * Gets the URI scheme from the byte array.
     *
     * @param data Byte array that can contain the scheme.
     * @return The URI scheme.
     */
    public static String getUriScheme(byte data[]) {
        if (data != null) {
            int i = 0;
            for (byte ch : data) {
                i++;
                
                // The array contains non printable char, no URI scheme there
                if (ch < FIRST_PRINTABLE_CHAR)
                    return null;
                
                // Trying luck with scheme delimiter
                if (ch == URI_SCHEME_DELIMITER) 
                    return new String(data).substring(0, i - 1);
                    
            }
        }
        return null;
    }
    
    /**
     * Decodes URI string.
     *
     * @param encodedUri The encoded URI.
     * @return The decoded URI string.
     */
    public static String decodeUri(String encodedUri) {
        if (encodedUri != null) {
            try {
                return URLDecoder.decode(encodedUri.replace("+", "%2B"), "UTF-8").replace("%2B", "+");
            } catch (UnsupportedEncodingException e) {}
        }
        return null;
    }

    
    /**
     * Decodes the QR code with specified decoders.
     *
     * @param decoders The list with the decoders to be used for decoding of the QR code.
     * @param The byte array that should contain the serialized QR code object.
     * @return The specific QR code object on success or null when no proprietary
     * decoder has been found.
     */
    public static QrCode decodeQrCode(ArrayList<QrDecoder> decoders, byte[] data) {
        ArrayList<QrDecoder> passedDecoders = new ArrayList<QrDecoder>();
        String scheme;
        QrCode qrCode;
        
        if (data == null || (scheme = getUriScheme(data)) == null && decoders.isEmpty()) {
            return null;
        }
        
        // Filters the decoders by supported schemes
        for (QrDecoder decoder : decoders) {
            if (decoder.isSupported(scheme)) passedDecoders.add(decoder);
        }
                
        // Iterating through possible decoders and trying to decode
        for (QrDecoder decoder : passedDecoders) {
            //try {
                qrCode = decoder.decode(data);
            /*} catch (LexicalError e) {
                qrCode = null;
            } catch (SyntaxError e) {
                qrCode = null;
            } catch (SemanticError e) {
                qrCode = null;
            }*/
            
            return qrCode;
        }
        
        return null;
    }
}

//End of the file
