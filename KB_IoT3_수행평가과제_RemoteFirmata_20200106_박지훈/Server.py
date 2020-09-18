'''
TcpServer.py
'''

import threading
import time
import serial
from socket import *

i = 0
str = ''
member = {'192.168.34.44':'1111', '192.168.34.38':'2222'
    , '192.168.34.45':'3333', '192.168.34.43':'1111', '192.168.34.42':'1111'}
memIP = list(member.keys())
memPW = list(member.values())

addrList = []
tcpIPList=[]
tcpConnList=[]

class TcpServer(threading.Thread):

    def __init__(self, conn, serial1):
        threading.Thread.__init__(self)  
        self.conn = conn
        self.serial1 = serial1
        
    def run(self): 
        global i
        global str        
        global tcpConnList
        global tcpIPList
        
        if i > 5 :            
            
            self.conn.send("Connected is Full\n".encode('euc-kr'))
            i -= 1
            print("Thread Cnt: %d" % (i))
            self.conn.shutdown(SHUT_RDWR)
            self.conn.close()            
            
        else :        
            rcvPW = self.conn.recv(1024).decode('euc-kr')                          
                
            if not rcvPW in memPW:  
            
                i -= 1
                print("Thread Cnt: %d" % (i))
                self.conn.send("Server Disconnected\n".encode('euc-kr'))
                self.conn.shutdown(SHUT_RDWR)
                self.conn.close()                                            
                
            else :
            
                self.conn.send("Server Connect\n".encode('euc-kr'))                                                         
                
                tcpIPList.append(addr[0])
                tcpConnList.append(self.conn)
                
                #print(tcpConnList)
                #print(tcpIPList)
                
                while True :
                    try :                               
                        self.conn.settimeout(0.1)                        
                        rcvdata = self.conn.recv(1024)
                        strRcvData = rcvdata.decode('euc-kr')  
                        
                        if len(strRcvData)>0:
                            print("Receive Massage:", strRcvData, addr)
                            self.serial1.write(strRcvData.encode('utf-8'))
                            
                        if len(strRcvData)==0 :
                            i -= 1
                            print("Disconnected by", addr)
                            print("Thread Cnt: %d" % (i))  
                            del tcpConnList[tcpConnList.index(self.conn)]             
                            del tcpIPList[tcpIPList.index(addr[0])]                             
                            return
                                
                    except timeout as e:                        
                        pass
                            
                    except :
                        pass                                
               
                self.conn.shutdown(SHUT_RDWR)            
                self.conn.close()             
                
class Serial(threading.Thread):
    
    def __init__(self, serial1):
        threading.Thread.__init__(self) 
        self.serial1 = serial1    
    
    def run(self): 
        global str          
        global i
        global tcpConnList
        
        while True :
            try :                
                if self.serial1.readable():                   
                    str = self.serial1.readline()                    
                    for x in tcpConnList :                                 
                        x.send(str)                
                        #print(str)
                        #print(x)
                    
            except timeout as a:
                pass
            except :
                pass

class UDPServer(threading.Thread):
    
    def __init__(self):
        threading.Thread.__init__(self)

    def run(self):    
        global tcpIPList
        
        svrU = socket(AF_INET, SOCK_DGRAM)
        svrU.bind(('192.168.34.99', 7777))        
        #print("여긴들어옴")
        
        while True :
            conn1, addr1 = svrU.recvfrom(200) 
            try :                      
                print("UDP Message:", conn1.decode())                
                
                #if addr1[0] in tcpIPList:
                for i in tcpIPList:
                    udpSndMsg= ("IP:[%s], MSG:[%s]" %(addr1[0],conn1.decode())).encode('utf-8')
                    print(udpSndMsg)
                    svrU.sendto(udpSndMsg, (i, 7777))  
                        
            except timeout as s:
                pass
            except :
                pass


if __name__ == '__main__' :  
    
    svrS = socket(AF_INET, SOCK_STREAM)
    svrS.bind(('192.168.34.99', 8888)) 
    svrS.setsockopt(SOL_SOCKET, SO_REUSEADDR,1)

    serial1 = serial.Serial(port=
        '/dev/ttyACM0'
        , baudrate=9600
        , timeout=0.1)   
        
    serial = Serial(serial1)
    serial.start()         
    udpSvr = UDPServer()
    udpSvr.start()      

    while True :
        svrS.listen()
        conn, addr = svrS.accept()   
        print("Connected by", addr)            
            
        if not addr[0] in memIP:  
            print("Unknown IP, Bye")
            conn.shutdown(SHUT_RDWR)
            conn.close()                      
                
        else :
            if i <= 5 :
                i += 1        
                svr1 = TcpServer(conn, serial1)
                svr1.start()  
                print("Thread Cnt: %d" % (i))
                
            else :
                print("Server is Full")
                i-=1
                print("Thread Cnt: %d" % (i))
                conn.shutdown(SHUT_RDWR)
                conn.close()    
