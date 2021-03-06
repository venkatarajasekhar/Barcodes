///////////////////////////////////////////////////////////////////////////////
// Project:    Docomo Decoder Plugin
// Package:    com.test
// File:       Main.java
// Date:       May 2012
// Author:     Radim Loskot
// E-mail:     xlosko01(at)stud.fit.vutbr.cz
//
// Brief:      Tests com.docomo.DocomoDecoder.
///////////////////////////////////////////////////////////////////////////////

package com.test;

import com.docomo.DocomoDecoder;
import com.qrcode.QrCodes.DataSegment;
import com.qrcode.QrCodes.DataSegments;

public class Main {
public static void main(String[] args) {
    DocomoDecoder decoder = new DocomoDecoder();
    DataSegments dataSegments = new DataSegments();
    dataSegments.segments = new DataSegment[1];
    dataSegments.segments[0] = new DataSegment();
    
    dataSegments.segments[0].data = new String("MATMSG:TO:test@seznam.cz;SUB:Test;BODY:This is a test. Please reply if received.;;").getBytes();
    assert(decoder.decode(dataSegments) != null);

    dataSegments.segments[0].data = new String("MATMSG:TO: docomo.triangletriangletriangle.taro@docomo.ne.jp;SUB:Test;BODY:This is a test. Please reply if received.;; ").getBytes();
    assert(decoder.decode(dataSegments) != null);
}
}
