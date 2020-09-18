from tkinter import *
import pymysql
import tkinter.ttk
import threading
import time
from time import strftime 

# sql 연결
conn = pymysql.connect(host='localhost', port=23308
    , user='root', password='23308', db='iot3lab')    

cursor = conn.cursor(pymysql.cursors.DictCursor)           

# 찾기 버튼               
def submit():
    
    for i in treeview.get_children():
        treeview.delete(i)  
    
    # 전부 조회
    if len(entry1.get())<=0 and len(entry2.get())<=0 :
        sql1="SELECT * FROM empinfo"
        cursor.execute(sql1)
        treelist = cursor.fetchall()    
        #print(treelist)
        
        for i in range(len(treelist)):
            treeview.insert('', 'end', text=i+1, values=(treelist[i]['EMPID'], treelist[i]['EMPNAME'], treelist[i]['PHONE']
                    , '', treelist[i]['SCORE1'], treelist[i]['SCORE2'], treelist[i]['SCORE3']
                    , treelist[i]['SCORE1']+treelist[i]['SCORE2']+treelist[i]['SCORE3'], (round(((treelist[i]['SCORE1']+treelist[i]['SCORE2']+treelist[i]['SCORE3'])/3),1))))     
                    
    # id 조회               
    if len(entry1.get())>0 and len(entry2.get())<=0 :
        sql1="SELECT * FROM empinfo WHERE INSTR(EMPID, '{}')"
        cursor.execute(sql1.format(entry1.get().strip()))
        treelist = cursor.fetchall()    
        #print(treelist)
        
        for i in range(len(treelist)):
            treeview.insert('', 'end', text=i+1, values=(treelist[i]['EMPID'], treelist[i]['EMPNAME'], treelist[i]['PHONE']
                    , '', treelist[i]['SCORE1'], treelist[i]['SCORE2'], treelist[i]['SCORE3']
                    , treelist[i]['SCORE1']+treelist[i]['SCORE2']+treelist[i]['SCORE3'], (round(((treelist[i]['SCORE1']+treelist[i]['SCORE2']+treelist[i]['SCORE3'])/3),1))))     
                    
    # 이름 조회               
    if len(entry2.get())>0 and len(entry1.get())<=0 :
        sql1="SELECT * FROM empinfo WHERE INSTR(EMPNAME, '{}')"
        cursor.execute(sql1.format(entry2.get().strip()))
        treelist = cursor.fetchall()    
        #print(treelist)
        
        for i in range(len(treelist)):
            treeview.insert('', 'end', text=i+1, values=(treelist[i]['EMPID'], treelist[i]['EMPNAME'], treelist[i]['PHONE']
                    , '', treelist[i]['SCORE1'], treelist[i]['SCORE2'], treelist[i]['SCORE3']
                    , treelist[i]['SCORE1']+treelist[i]['SCORE2']+treelist[i]['SCORE3'], (round(((treelist[i]['SCORE1']+treelist[i]['SCORE2']+treelist[i]['SCORE3'])/3),1))))                      
  
    # id, 이름 같이 조회
    if len(entry1.get())>0 and len(entry2.get())>0 :
        sql1="SELECT * FROM empinfo WHERE INSTR(EMPID, '{}') and INSTR(EMPNAME, '{}')"
        cursor.execute(sql1.format((entry1.get().strip()),(entry2.get().strip())))
        treelist = cursor.fetchall()    
        #print(treelist)
        
        for i in range(len(treelist)):
            treeview.insert('', 'end', text=i+1, values=(treelist[i]['EMPID'], treelist[i]['EMPNAME'], treelist[i]['PHONE']
                    , '', treelist[i]['SCORE1'], treelist[i]['SCORE2'], treelist[i]['SCORE3']
                    , treelist[i]['SCORE1']+treelist[i]['SCORE2']+treelist[i]['SCORE3'], (round(((treelist[i]['SCORE1']+treelist[i]['SCORE2']+treelist[i]['SCORE3'])/3),1))))                      

# 취소 버튼
def cancle():
    for i in treeview.get_children():
        treeview.delete(i)  
        
    entry1.delete(0,END)
    entry2.delete(0,END)  

# 날짜, 시간
def time(): 
    string = strftime('%Y-%m-%d %H:%M:%S %p')
    label5.config(text = string) 
    label5.after(1000, time)

# 생성
root=Tk()
root.title("사원관리 프로그램")
root.geometry('700x400')
root.resizable(False, False)

# 프레임 생성
frame1=tkinter.Frame(root, relief="solid", bd=2)
frame1.pack(side="top", fill="both", expand=True)

frame2=tkinter.Frame(root, relief="solid", bd=2)
frame2.pack(side="bottom", expand=True)

image=tkinter.PhotoImage(file="Whale.gif")

label1=Label(frame1, text='사원관리')
label2=Label(frame1, text='사번 :')
label3=Label(frame1, text='이름 :')
label4=Label(frame1, image=image)
label5=Label(frame1)

label4.pack()
time()

entry1=Entry(frame1)
entry2=Entry(frame1)

button1=Button(frame1, text='찾기', command = submit)
button2=Button(frame1, text='취소', command = cancle)

label1.place(x=280, y=5)
label2.place(x=90, y=35)
label3.place(x=290, y=35)
label4.place(x=10, y=5)
label5.place(x=500, y=5)

entry1.place(x=125, y=35)
entry2.place(x=325, y=35)

button1.place(x=500,y=35, width=50, height=25)
button2.place(x=560,y=35, width=50, height=25)

#트리뷰 생성
treeview=tkinter.ttk.Treeview(frame2,column=['1','2','3','4','5','6','7','8','9']
    ,displaycolumns=['1','2','3','4','5','6','7','8','9']) 

treeview.pack(side=LEFT)

treeview.column('#0', width=50, anchor='e')
treeview.heading('#0', text='순서', anchor='center')
treeview.column('1', width=50, anchor='center')
treeview.heading('1', text='사번', anchor='center')
treeview.column('2', width=50, anchor='center')
treeview.heading('2', text='이름', anchor='center')
treeview.column('3', width=100, anchor='center')
treeview.heading('3', text='전화', anchor='center')
treeview.column('4', width=100, anchor='e')
treeview.heading('4', text='이메일', anchor='center')
treeview.column('5', width=40, anchor='e')
treeview.heading('5', text='점수1', anchor='center')
treeview.column('6', width=40, anchor='e')
treeview.heading('6', text='점수2', anchor='center')
treeview.column('7', width=40, anchor='e')
treeview.heading('7', text='점수3', anchor='center')
treeview.column('8', width=40, anchor='e')
treeview.heading('8', text='총점', anchor='center')
treeview.column('9', width=40, anchor='e')
treeview.heading('9', text='평균', anchor='center')

# 스크롤바 생성
scrollbar = Scrollbar(frame2)
scrollbar.pack(side=RIGHT, fill=Y)
scrollbar.config(command=treeview.yview)
treeview.configure(yscrollcommand=scrollbar.set)  

root.mainloop()