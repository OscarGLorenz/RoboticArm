#include "Arduino.h"
#include "SoftwareSerial.h"
#include "PololuMaestro.h"

#define MAX_VEL 20
#define MAX_ACC 5

SoftwareSerial servoSerial(10, 11);
MicroMaestro maestro(servoSerial);

const int configValues[][2] = { { 4000, 8000 }, { 4000, 5500 }, { 5000, 7000 },
		{ 6000, 8000 } };

const int positions[] = { 6000, 0, 5000, 7000 };

void serialEvent() {
		char buffer[10];
		Serial.readBytesUntil(' ', buffer, 10);
		switch (buffer[0]) {
		case 'G':
			if (toInt(buffer[1]) == 1) {
				if (toInt(buffer[2]) == 0)
					maestro.setTarget(1, configValues[1][0]);
				else if (toInt(buffer[2]) == 1)
					maestro.setTarget(1, configValues[1][1]);
			} else {
				maestro.setTarget(toInt(buffer[1]),
						map(atoi(buffer + 2), 0, 100,
								configValues[toInt(buffer[1])][0],
								configValues[toInt(buffer[1])][1]));
			}
			break;
		case 'H':
			maestro.setTarget(0, positions[0]);
			maestro.setTarget(1, configValues[1][1]);
			maestro.setTarget(2, positions[2]);
			maestro.setTarget(3, positions[3]);
			break;
		}
}

void setup() {
	Serial.setTimeout(0);
	servoSerial.begin(9600);
	Serial.begin(9600);

	for (int i = 0; i < 4; i++) {
		if (i == 1)
			continue;
		maestro.setSpeed(i, MAX_VEL);
		maestro.setAcceleration(i, MAX_ACC);
	}

	maestro.setTarget(0, positions[0]);
	maestro.setTarget(1, configValues[1][1]);
	maestro.setTarget(2, positions[2]);
	maestro.setTarget(3, positions[3]);
}

void loop() {
}

int toInt(char c) {
	return c - '0';
}

