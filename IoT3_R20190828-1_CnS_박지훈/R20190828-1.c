/*
	R20190828-1.c
	C언어 과제
*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <conio.h>

int MenuMgr();

#pragma pack(push, 1)
typedef struct _EpMg	// 구조체 선언
{ 	
	char szENmb[7];
	char szEName[10];
	char szHP[20];
	int nGp1;
	int nGp2;
	int nGp3;		
}EpMg;
#pragma pack(pop)

char a[10];

void main()
{	
	EpMg p[9];
	
	FILE *fp=fopen("EpMg.dat", "rb");	
	memset(p, 0x00, sizeof(p));
	
	fread(&p, sizeof(p), 1, fp);
	
	while(1)
	{
		// 메뉴 관리 호출
		int nMenu = MenuMgr();
		memset(a, 0x00, sizeof(a));
		switch(nMenu)
		{
			case '0': 
				printf("종료하시겠습니까?...");
				int nYN=getch();
				if(nYN=='y' || nYN=='Y')
					return;
				continue;
				
			case '1':				
				printf("[DBG] 1) 조회 메뉴를 선택하셨습니다.\n");
				printf("사번을 입력하시오.\n");
				
				//memset(a, 0x00, sizeof(a));
				scanf("%s", a);
				
				for(int i=0;i<9;i++)
				{	
					int x = strcmp(p[i].szENmb, a);
										
					if(x==0)	// 사번과 일치 
					{
						printf("%s %s %s %d %d %d\n", p[i].szENmb, p[i].szEName, p[i].szHP, p[i].nGp1, p[i].nGp2, p[i].nGp3);
						break;
					}
					else if(i==8)	// 아예 일치하는 사번이 없음
					{
						printf("유효하지 않은 사번입니다. ");
						break;
					}						
				}						
				break;
				
			case '2':
				printf("[DBG] 2) 입력 메뉴를 선택하셨습니다.\n");							
								
				for(int i=0;i<9;i++)
				{											
					if(p[i].szENmb[0]=='\0')	// 사번창이 비었을 때만 입력 가능
					{
						printf("사번을 무엇으로 바꾸시겠습니까? ");
						scanf("%s", p[i].szENmb);
						
						printf("이름을 무엇으로 바꾸시겠습니까? ");
						scanf("%s", p[i].szEName);
						
						printf("연락처를 무엇으로 바꾸시겠습니까? ");
						scanf("%s", p[i].szHP);
						
						printf("평점1을 무엇으로 바꾸시겠습니까? ");
						scanf("%d", &p[i].nGp1);
						
						printf("평정2를 무엇으로 바꾸시겠습니까? ");
						scanf("%d", &p[i].nGp2);
						
						printf("평점3을 무엇으로 바꾸시겠습니까? ");
						scanf("%d", &p[i].nGp3);
						
						printf("완료 되었습니다. ");
						
						break;
					}
					else 
					{
						printf("빈 자리가 없습니다. ");
						
						break;
					}						
				}				
				break;
				
			case '3':
				printf("[DBG] 3) 수정 메뉴를 선택하셨습니다.\n");				
				printf("사번을 입력하시오.\n");
								
				scanf("%s", a);
				
				for(int i=0;i<9;i++)
				{	
					int x = strcmp(p[i].szENmb, a);
										
					if(x==0)		
					{
						printf("이름을 무엇으로 바꾸시겠습니까? ");
						scanf("%s", p[i].szEName);
						
						printf("연락처를 무엇으로 바꾸시겠습니까? ");
						scanf("%s", p[i].szHP);
						
						printf("평점1을 무엇으로 바꾸시겠습니까? ");
						scanf("%d", &p[i].nGp1);
						
						printf("평정2를 무엇으로 바꾸시겠습니까? ");
						scanf("%d", &p[i].nGp2);
						
						printf("평점3을 무엇으로 바꾸시겠습니까? ");
						scanf("%d", &p[i].nGp3);
						
						printf("수정이 완료 되었습니다. ");
										
						break;
					}
					else if(i==8)
					{
						printf("유효하지 않은 사번입니다. ");
						
						break;
					}						
				}					
				break;
				
			case '4':
				printf("[DBG] 4) 삭제 메뉴를 선택하셨습니다.\n");
				
				printf("삭제할 사번을 입력하시오.\n");
													
				scanf("%s", a);
				
				for(int i=0;i<9;i++)
				{	
					int x = strcmp(p[i].szENmb, a);
										
					if(x==0)		
					{
						memset(&p[i], 0x00, sizeof(p[i]));
						
						printf("모든 정보가 삭제 되었습니다. ");
						
						break;
					}
					else if(i==8)
					{
						printf("유효하지 않은 사번입니다. ");
						
						break;
					}						
				}	
				break;
				
			case '5':
				printf("[DBG] 5) 평점보고서 메뉴를 선택하셨습니다.\n");
				
				printf("평점정보를 조회할 사번을 입력하시오.\n");
				scanf("%s", a);											
				
				for(int i=0;i<9;i++)
				{
					int x=strcmp(p[i].szENmb, a);	
					int w=p[i].nGp1;
					int y=p[i].nGp2;
					int z=p[i].nGp3;
					int nGpAvr=(w+y+z)/3;					
					
					if(x==0)		
					{												
						printf("[%s]사원의 평점은 다음과 같습니다. [%d] [%d] [%d]. 평점평균은 [%d]입니다.\n"
							, p[i].szEName, w, y, z, nGpAvr);
						
						break;
					}
					else if(i==8)
					{
						printf("유효하지 않은 사번입니다. \n");
						
						break;
					}					
				}		
				
				printf("모든 사원의 평점정보 입니다. ");
				
				for(int i=0;i<9;i++)
				{	
					
					int w=p[i].nGp1;
					int y=p[i].nGp2;
					int z=p[i].nGp3;
										
					printf("사원명 [%s] 평점1[%d] 평점2[%d] 평점3[%d]. ", p[i].szEName, w, y, z);									
				}
				
				
				break;
				
			default:
				printf("[DBG] 알 수 없는 메뉴를 선택하셨습니다.\n");
				
				break;
		}
		getch();
	}
	fclose(fp);
	
	return;
}


 int MenuMgr()
{
	//화면 지우기
	system("cls");
	
	// 메뉴 구성
	printf("다음중 원하시는 메뉴를 선택하세요.\n");
	printf("  \n");
	printf(" 1)조회\n");
	printf(" 2)입력\n");
	printf(" 3)수정\n");
	printf(" 4)삭제\n");
	printf(" 5)평점보고서\n");
	printf(" 0)종료\n");
	
	int nMenu=getch();
	printf("[DBG] 선택 메뉴 : [%d]\n", nMenu);
	
	return nMenu;	
}
