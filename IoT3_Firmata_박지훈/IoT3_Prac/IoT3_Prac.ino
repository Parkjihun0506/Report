#include <SoftwareSerial.h>

String szMsg = "";
String saMsg = "";
String anl0;
String anl1;
String anl2;
String anl3;
String anl4;
String anl5;

int nSRX = 2;
int nSTX = 3;
int strRes1;
int strRes2;
String strRes3;

SoftwareSerial ssBT(nSRX, nSTX);

void setup() {

  Serial.begin(9600);
  ssBT.begin(9600);
}

void loop() {

  anl0 = (String)analogRead(A0);
  anl1 = (String)analogRead(A1);
  anl2 = (String)analogRead(A2);
  anl3 = (String)analogRead(A3);
  anl4 = (String)analogRead(A4);
  anl5 = (String)analogRead(A5);

  Serial.print("/A0: " + anl0 + "/");
  delay(100);
  Serial.print("/A1: " + anl1 + "/");
  delay(100);
  Serial.print("/A2: " + anl2 + "/");
  delay(100);
  Serial.print("/A3: " + anl3 + "/");
  delay(100);
  Serial.print("/A4: " + anl4 + "/");
  delay(100);
  Serial.print("/A5: " + anl5 + "/");
  delay(100);

  if (ssBT.available() > 0) {
    //SW 시리얼로부터 HW 시리얼로 전송
    saMsg = ssBT.readString();

    int strLen1 = saMsg.indexOf("b");

    if (strLen1 != -1) {
      strRes1 = saMsg.substring(1, strLen1).toInt();
      strRes2 = saMsg.substring(strLen1 + 1).toInt();

      digitalWrite(strRes1, strRes2);
    }

    int strLen2 = saMsg.indexOf("t");

    if (strLen2 != -1) {
      strRes1 = saMsg.substring(1, strLen2).toInt();
      strRes2 = saMsg.substring(strLen2 + 1).toInt();

      pinMode(strRes1, strRes2);
    }

    int strLen3 = saMsg.indexOf("c");
    if (strLen3 != -1) {
      strRes1 = saMsg.substring(1, strLen3).toInt();
      strRes2 = saMsg.substring(strLen3 + 1).toInt();

      analogWrite(strRes1, strRes2);
    }
  }

  if (Serial.available() > 0) {
    szMsg = Serial.readString();

    int strLen1 = szMsg.indexOf("b");

    if (strLen1 != -1) {
      strRes1 = szMsg.substring(1, strLen1).toInt();
      strRes2 = szMsg.substring(strLen1 + 1).toInt();     
      
      digitalWrite(strRes1, strRes2);
      String sndMsg = (String)"PinNum: " + strRes1 + ", PinStat: " + strRes2 + ".";
      Serial.println(sndMsg);
    }

    int strLen2 = szMsg.indexOf("t");

    if (strLen2 != -1) {
      strRes1 = szMsg.substring(1, strLen2).toInt();
      strRes2 = szMsg.substring(strLen2 + 1).toInt();      

      pinMode(strRes1, strRes2);
      String sndMsg = (String)"PinNum: " + strRes1 + ", PinMode: " + strRes2 + ".";
      Serial.println(sndMsg);
    }

    int strLen3 = szMsg.indexOf("c");
    if (strLen3 != -1) {
      strRes1 = szMsg.substring(1, strLen3).toInt();
      strRes2 = szMsg.substring(strLen3 + 1).toInt();

      analogWrite(strRes1, strRes2);
      String sndMsg = (String)"PinNum: " + strRes1 + ", PWM: " + strRes2 + ".";
      Serial.println(sndMsg);
    }
  }
}
