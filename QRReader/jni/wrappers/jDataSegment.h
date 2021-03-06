///////////////////////////////////////////////////////////////////////////////
// Project:    QR Reader for Android
// File:       jDataSegment.h
// Date:       March 2012
// Author:     Radim Loskot
// E-mail:     xlosko01(at)stud.fit.vutbr.cz
//
// Brief:      Defines the wrapper for accessing the members of the objects
//             of DataSegment class in the Java.
///////////////////////////////////////////////////////////////////////////////

/**
 * @file jDataSegment.h
 *
 * @brief Defines the wrapper for accessing the members of the objects
 *        of DataSegment class in the Java.
 * @author Radim Loskot xlosko01(at)stud.fit.vutbr.cz
 */

#ifndef JNI_JDATASEGMENT_H_
#define JNI_JDATASEGMENT_H_

#include <barlib/barcodes/DataSegments.h>
#include <barlib/common.h>

#include "JNIWrapper.h"

using namespace barcodes;

namespace jni {

/**
 * Wraps the Java DataSegment class.
 */
class jDataSegment: public JNIWrapper {
private:
	const static string CLASS_NAME;

public:
	jDataSegment(JNIEnv *env);
	jDataSegment(JNIEnv *env, jobject jObject);
	jDataSegment(JNIEnv *env, DataSegment &dataSegment);

	ByteArray getData();
	void setData(ByteArray &data);

	size_t getRemainderBits();
	void setRemainderBits(size_t remainderBits);

	int getFlags();
	void setFlags(int flags);

	int getMode();
	void setMode(int flags);

	static jclass getJClass(JNIEnv *env);
	operator DataSegment();
};

}

#endif
