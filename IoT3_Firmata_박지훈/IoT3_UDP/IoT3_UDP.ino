/*
   IoT3_WiFiUDP1
   UDP/IP 로 메시지 수신 및 응답
*/

#include "ESP8266WiFi.h"
#include <WiFiUdp.h>

char spSSID[] = "IoTLab";
char spSSPW[] = "1122334455";
char szDispMsg[128];

// 서버 IP 객체 생성

int nSvrPort = 8888; // UDP Port 8888

char szUDPBuffer[128]; // UDP 수신 버퍼
char szReplyBuffer[128]; // UDP 응답 버퍼

WiFiUDP wifiUDP; // WiFiUdp 클래스의 인스턴스

void setup() {

  Serial.begin(9600);
  Serial.println("UART Started!");

  WiFi.mode(WIFI_STA); // ESP-01 모듈을 스테이션 모드로 설정
  WiFi.begin(spSSID, spSSPW);

  while (WiFi.status() != WL_CONNECTED) { // 접속시까지 대기

    Serial.print(".");
    delay(500);
  }

  Serial.println("AP Connected");
  Serial.println(WiFi.status()); // 3이면 AP 접속 성공
  IPAddress myIP = WiFi.localIP(); // 현재의 IP를 시리얼 모니터에 출력
  memset(szDispMsg, 0x00, sizeof(szDispMsg));
  sprintf(szDispMsg, "IP Address : %d.%d.%d.%d", myIP[0], myIP[1], myIP[2], myIP[3]);
  Serial.println(szDispMsg);

  // UDP Server Start
  wifiUDP.begin(nSvrPort); // UDP 서버를 포트 8888로 시작
}

void loop() {

  // UDP 포트 8888로 수신된 메시지를
  // 시리얼 모니터로 출력하고
  // 송신측으로 수신 응답 메시지를 전송

  // 1. UDP 8888로 수신메시지 여부 체크
  int nPacketSize = wifiUDP.parsePacket(); // 대상 UDP 포트에 수신된 메시지의 길이 리턴
  if (nPacketSize <= 0) {
    // 수신된 데이터 없음
    delay(100);
    return;
  }

  // 수신 메시지 읽기
  int nReadLen = wifiUDP.read(szUDPBuffer, sizeof(szUDPBuffer));
  // UDP_TX_PACKET_MAX_SIZE = 8192 바이트
  szUDPBuffer[nReadLen] = 0; // 읽은 버퍼의 끝 위치에 0으로끝나는 문자열로 지정

  Serial.print(szUDPBuffer);

  // 송신측으로 받은 메시지에 대한 응답 송신
  wifiUDP.beginPacket(wifiUDP.remoteIP(), wifiUDP.remotePort());
  sprintf(szReplyBuffer, "%d byte Receive Comleted", nReadLen);
  wifiUDP.write(szReplyBuffer);
  wifiUDP.endPacket();
}
