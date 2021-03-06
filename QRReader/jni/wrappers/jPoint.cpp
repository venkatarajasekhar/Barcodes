///////////////////////////////////////////////////////////////////////////////
// Project:    QR Reader for Android
// File:       jPoint.cpp
// Date:       March 2012
// Author:     Radim Loskot
// E-mail:     xlosko01(at)stud.fit.vutbr.cz
//
// Brief:      Defines members of the the wrapper for accessing the members
//             of the objects of Point class in the Java.
///////////////////////////////////////////////////////////////////////////////

/**
 * @file jPoint.cpp
 *
 * @brief Defines members of the wrapper for accessing the members of the objects
 *        of Point class in the Java.
 * @author Radim Loskot xlosko01(at)stud.fit.vutbr.cz
 */

#include "jPoint.h"

using namespace jni;
using namespace std;

const string jPoint::CLASS_NAME = "com/qrcode/QrCodes$Point";

jPoint::jPoint(JNIEnv *env) : JNIWrapper(env, CLASS_NAME) {}

jPoint::jPoint(JNIEnv *env, jobject jObject) : JNIWrapper(env, jObject) {}

jPoint::jPoint(JNIEnv *env, Point point) : JNIWrapper(env, CLASS_NAME) {
	setX(point.x);
	setY(point.y);
}

void jPoint::setX(int x) {
	setIntField(this, "x", x);
}

void jPoint::setY(int y) {
	setIntField(this, "y", y);
}

int jPoint::getX() {
	return getIntField(this, "x");
}

int jPoint::getY() {
	return getIntField(this, "y");
}

jPoint::operator Point() {
	return Point(getX(), getY());
}

jclass jPoint::getJClass(JNIEnv *env) {
	return env->FindClass(CLASS_NAME.c_str());
}
