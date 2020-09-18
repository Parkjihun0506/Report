'''
-등록용 지문인식기-
1. 지문정보를 템플릿에 저장X
2. 지문정보를 파일로 저장
3. FTP를 활용하여 파일 업로드X
'''

import os
import sys
import pickle
import tempfile
import time
import serial
from ftplib import FTP

from pyfingerprint.pyfingerprint import PyFingerprint

yourUserName = 'kb304-01'
yourPassword = 'iot3*'
upload_path = "./FingerInfo"

#window
if sys.platform.startswith('win'):   
    ports = ['COM%s' % (i + 1) for i in range(256)]   
#linux
elif sys.platform.startswith('linux') or sys.platform.startswith('cygwin'):   
    # this excludes your current terminal "/dev/tty"   
    ports = glob.glob('/dev/tty[A-Za-z]*')   
#who a you?
elif sys.platform.startswith('darwin'):   
    ports = glob.glob('/dev/tty.*')   
else:   
    raise EnvironmentError('Unsupported platform')   
   

portName = [] 
  
for port in ports:   
    try:   
        s = serial.Serial(port)   
        s.close()   
        portName.append(port)   
    except (OSError, serial.SerialException):   
        pass  
    portNumber = ' '.join(portName[2:3])
        

try:            
    f = PyFingerprint('./{}'.format(portNumber), 57600, 0xFFFFFFFF, 0x00000000)
    
    if ( f.verifyPassword() == False ):
        raise ValueError('The given fingerprint sensor password is wrong!')

except Exception as e:
    print('The fingerprint sensor could not be initialized!')
    print('Exception message: ' + str(e))
    exit(1)
    

try:
    print('손가락을 기다리는 중 입니다.')

    while(f.readImage() == False):
        pass
        
    #지문의 특징점을 추출하여 문자열로 변환
    f.convertImage(0x01)    
    
    #저장할 템플릿 서치
    result = f.searchTemplate()
    positionNumber = result[0]  
    
    
    if ( positionNumber >= 0 ):
        print('이미 존재하는 템플릿 위치 입니다. #' + str(positionNumber))
        #return()
        exit(0)
    
    
    print('손가락을 제거 하십시오.')
    time.sleep(1)       

    print('같은 손가락을 인식하십시오.')
    
    while ( f.readImage() == False ):
        pass        
        
    f.convertImage(0x02)    
    
    if ( f.compareCharacteristics() == 0 ):        
        raise Exception('매치 실패!')
                    

    
    positionNumber = f.storeTemplate()
    
    print('등록성공! :)')
    print('새로운 템플릿 위치는 #' + str(positionNumber))
    
    #특성변환
    characterics = str(f.downloadCharacteristics(0x02)).encode('utf-8')
    
    #지문정보 저장할 위치에폴더 생성
    path = './FingerInfo'
    if not (os.path.isdir(path)):
        os.mkdir(os.path.join(path))
    
    
    #저장할 제목 입력받고 텍스트파일로 저장
    filename=input("FileName? :")
    filename+='.txt'
    
    #지문이 저장된 템플릿 위치 확인
    position = positionNumber 
    
    #저장된 위치의 템플릿 불러오기        
    f.loadTemplate(position, 0x01)  
    
    #저장된 지문의 특성 다운로드
    characters = f.downloadCharacteristics(0x01)   
    
    #파일을 열어 다운로드한 지문의 특성을 쓰기
    with open('FingerInfo/{}'.format(filename), 'w') as r:
        r.write(str(characters))
        print ('저장된위치: ',os.getcwd(),'->FingerInfo<- Folder')
        
    #템플릿에 등록된 지문 삭제    
    if ( f.deleteTemplate(positionNumber) == False ):
        print('wwwwww')
        #return()
        exit(0)
    else:
        print('Delete Template!')
        
    
    '''
    #저장한 지문정보 전송
    print ('FTP Upload Start!')
    ftp.set_debuglevel(1) # FTP의 디버그 모드
    ftp.connect("192.168.34.38",21)
    ftp.login(yourUserName,yourPassword)
    ftp.cwd("./")
    os.chdir(r"./FingerInfo")
    myfile = open(filename,'rb')
    ftp.storbinary('STOR ' +filename, myfile)
    myfile.close()
    ftp.close
    print ('FTP Upload Complete!')
    
    sys.exit()
    '''
    
except Exception as e:
    print('Operation failed!')
    print('Exception messge: ' +str(e))
    exit(1)
   