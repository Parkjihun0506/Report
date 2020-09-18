#include <SoftwareSerial.h>

String szMsg = "";
String saMsg = "";
String anl0;
String anl1;
String anl2;
String anl3;
String anl4;
String anl5;

int strRes1;
int strRes2;
String strRes3;

void setup() {

  Serial.begin(9600);  
}

void loop() {

  anl0 = (String)analogRead(A0);
  anl1 = (String)analogRead(A1);
  anl2 = (String)analogRead(A2);
  anl3 = (String)analogRead(A3);
  anl4 = (String)analogRead(A4);
  anl5 = (String)analogRead(A5);

  Serial.print("A" + anl0 + "a");
  delay(100);
  Serial.print("B" + anl1 + "b");
  delay(100);
  Serial.print("C" + anl2 + "c");
  delay(100);
  Serial.print("D" + anl3 + "d");
  delay(100);
  Serial.print("E" + anl4 + "e");
  delay(100);
  Serial.print("F" + anl5 + "f\n");
  delay(100);  

  if (Serial.available() > 0) {
    szMsg = Serial.readString();

    int strLen1 = szMsg.indexOf("b");

    if (strLen1 != -1) {
      strRes1 = szMsg.substring(1, strLen1).toInt();
      strRes2 = szMsg.substring(strLen1 + 1).toInt();     
      
      digitalWrite(strRes1, strRes2);
      String sndMsg = (String)"PinNum: " + strRes1 + ", PinStat: " + strRes2;
      Serial.println(sndMsg);
      delay(100);
    }

    int strLen2 = szMsg.indexOf("t");

    if (strLen2 != -1) {
      strRes1 = szMsg.substring(1, strLen2).toInt();
      strRes2 = szMsg.substring(strLen2 + 1).toInt();      

      pinMode(strRes1, strRes2);
      String sndMsg = (String)"PinNum: " + strRes1 + ", PinMode: " + strRes2;
      Serial.println(sndMsg);
      delay(100);
    }

    int strLen3 = szMsg.indexOf("c");
    if (strLen3 != -1) {
      strRes1 = szMsg.substring(1, strLen3).toInt();
      strRes2 = szMsg.substring(strLen3 + 1).toInt();

      analogWrite(strRes1, strRes2);
      String sndMsg = (String)"PinNum: " + strRes1 + ", PWM: " + strRes2;
      Serial.println(sndMsg);
      delay(100);
    }
  }
}
