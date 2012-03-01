package com.android.qrreader.installation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class PackageResourceXML {
    final private static String XML_NOT_OPENED = "Resource XMl is not opened!";
    
    final private Context context;
    final private File packageFile;
    final private String resName;
    
    private XmlPullParser parser;
    private InputStream resInputStream;
    
    public PackageResourceXML(Context context, File packageFile, String resName) {
        this.context = context;
        this.packageFile = packageFile;
        this.resName = resName;       
    }
    
    public boolean open() {
        DexClassLoader classLoader = new DexClassLoader(
                packageFile.getAbsolutePath(), 
                context.getFilesDir().getAbsolutePath(),
                null, 
                getClass().getClassLoader()
            );

        try {
            resInputStream = classLoader.getResourceAsStream(resName);
            if (resInputStream != null) {
                parser = XmlPullParserFactory.newInstance().newPullParser();
                parser.setInput(resInputStream, null);
                return true;
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
                
        parser = null;
        return false;
    }
    
    public void close() {
        if (parser != null) {
            parser = null;
            try {
                resInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    int getEventType() throws XmlPullParserException {
        if (parser == null) {
            throw new XmlPullParserException(XML_NOT_OPENED);
        }
        
        return parser.getEventType();
    }
    
    String getName() throws XmlPullParserException {
        if (parser == null) {
            throw new XmlPullParserException(XML_NOT_OPENED);
        }
        
        return parser.getName();
    }
    
    String getText() throws XmlPullParserException {
        if (parser == null) {
            throw new XmlPullParserException(XML_NOT_OPENED);
        }
        
        return parser.getText();
    }
    
    int next() throws XmlPullParserException, IOException {
        if (parser == null) {
            throw new XmlPullParserException(XML_NOT_OPENED);
        }
        
        return parser.next();
    }
}