#include "jDetectedMark.h"

using namespace jni;
using namespace std;

const string jDetectedMark::CLASS_NAME = "com/qrcode/QrCodes/DetectedMark";

jDetectedMark::jDetectedMark(JNIEnv * env) {
	JNIWrapper(env, CLASS_NAME);
}

jDetectedMark::jDetectedMark(JNIEnv *env, jobject jObject) {
	JNIWrapper(env, jObject);
}

jDetectedMark::jDetectedMark(JNIEnv *env, DetectedMark detectedMark) {
	JNIWrapper(env, CLASS_NAME);
	setPoints(detectedMark.points);
	setMatch(detectedMark.match);
	setFlags(detectedMark.flags);
}

vector<Point> jDetectedMark::getPoints() {
	vector<Point> points;

	int arrLength = getArrayLength(this, "points");
	for (int i = 0; i < arrLength; i++) {
		points.push_back(jPoint(getObjectArrayElement(this, "points", i)));
	}

	return points;
}

void jDetectedMark::setPoints(vector<Point> points) {
	int arrLength = points.size();
	jobjectArray pointsArray = newObjectArray(jPoint.getJClass(), arrLength, jPoint());

	setObjectField(this, "points", pointsArray);

	for (int i = 0; i < arrLength; i++) {
		jPoint point = jPoint(points[i]);
		setObjectArrayElement(this, "points", i, point);
	}
}

double jDetectedMark::getMatch() {
	return getDoubleField(this, "match");
}

void jDetectedMark::setMatch(double match) {
	setDoubleField(this, "match", match);
}

int jDetectedMark::getFlags() {
	return getIntField(this, "flags");
}

void jDetectedMark::setFlags(int flags) {
	setIntField(this, "flags", flags);
}

operator DetectedMark() {
	DetectedMark detectedMark;
	detectedMark.flags = getFlags();
	detectedMark.points = getPoints();
	detectedMark.match = getMatch();

	return detectedMark;
}
