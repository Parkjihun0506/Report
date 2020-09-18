/*
	R20190828-1.c
	C��� ����
*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <conio.h>

int MenuMgr();

#pragma pack(push, 1)
typedef struct _EpMg	// ����ü ����
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
		// �޴� ���� ȣ��
		int nMenu = MenuMgr();
		memset(a, 0x00, sizeof(a));
		switch(nMenu)
		{
			case '0': 
				printf("�����Ͻðڽ��ϱ�?...");
				int nYN=getch();
				if(nYN=='y' || nYN=='Y')
					return;
				continue;
				
			case '1':				
				printf("[DBG] 1) ��ȸ �޴��� �����ϼ̽��ϴ�.\n");
				printf("����� �Է��Ͻÿ�.\n");
				
				//memset(a, 0x00, sizeof(a));
				scanf("%s", a);
				
				for(int i=0;i<9;i++)
				{	
					int x = strcmp(p[i].szENmb, a);
										
					if(x==0)	// ����� ��ġ 
					{
						printf("%s %s %s %d %d %d\n", p[i].szENmb, p[i].szEName, p[i].szHP, p[i].nGp1, p[i].nGp2, p[i].nGp3);
						break;
					}
					else if(i==8)	// �ƿ� ��ġ�ϴ� ����� ����
					{
						printf("��ȿ���� ���� ����Դϴ�. ");
						break;
					}						
				}						
				break;
				
			case '2':
				printf("[DBG] 2) �Է� �޴��� �����ϼ̽��ϴ�.\n");							
								
				for(int i=0;i<9;i++)
				{											
					if(p[i].szENmb[0]=='\0')	// ���â�� ����� ���� �Է� ����
					{
						printf("����� �������� �ٲٽðڽ��ϱ�? ");
						scanf("%s", p[i].szENmb);
						
						printf("�̸��� �������� �ٲٽðڽ��ϱ�? ");
						scanf("%s", p[i].szEName);
						
						printf("����ó�� �������� �ٲٽðڽ��ϱ�? ");
						scanf("%s", p[i].szHP);
						
						printf("����1�� �������� �ٲٽðڽ��ϱ�? ");
						scanf("%d", &p[i].nGp1);
						
						printf("����2�� �������� �ٲٽðڽ��ϱ�? ");
						scanf("%d", &p[i].nGp2);
						
						printf("����3�� �������� �ٲٽðڽ��ϱ�? ");
						scanf("%d", &p[i].nGp3);
						
						printf("�Ϸ� �Ǿ����ϴ�. ");
						
						break;
					}
					else 
					{
						printf("�� �ڸ��� �����ϴ�. ");
						
						break;
					}						
				}				
				break;
				
			case '3':
				printf("[DBG] 3) ���� �޴��� �����ϼ̽��ϴ�.\n");				
				printf("����� �Է��Ͻÿ�.\n");
								
				scanf("%s", a);
				
				for(int i=0;i<9;i++)
				{	
					int x = strcmp(p[i].szENmb, a);
										
					if(x==0)		
					{
						printf("�̸��� �������� �ٲٽðڽ��ϱ�? ");
						scanf("%s", p[i].szEName);
						
						printf("����ó�� �������� �ٲٽðڽ��ϱ�? ");
						scanf("%s", p[i].szHP);
						
						printf("����1�� �������� �ٲٽðڽ��ϱ�? ");
						scanf("%d", &p[i].nGp1);
						
						printf("����2�� �������� �ٲٽðڽ��ϱ�? ");
						scanf("%d", &p[i].nGp2);
						
						printf("����3�� �������� �ٲٽðڽ��ϱ�? ");
						scanf("%d", &p[i].nGp3);
						
						printf("������ �Ϸ� �Ǿ����ϴ�. ");
										
						break;
					}
					else if(i==8)
					{
						printf("��ȿ���� ���� ����Դϴ�. ");
						
						break;
					}						
				}					
				break;
				
			case '4':
				printf("[DBG] 4) ���� �޴��� �����ϼ̽��ϴ�.\n");
				
				printf("������ ����� �Է��Ͻÿ�.\n");
													
				scanf("%s", a);
				
				for(int i=0;i<9;i++)
				{	
					int x = strcmp(p[i].szENmb, a);
										
					if(x==0)		
					{
						memset(&p[i], 0x00, sizeof(p[i]));
						
						printf("��� ������ ���� �Ǿ����ϴ�. ");
						
						break;
					}
					else if(i==8)
					{
						printf("��ȿ���� ���� ����Դϴ�. ");
						
						break;
					}						
				}	
				break;
				
			case '5':
				printf("[DBG] 5) �������� �޴��� �����ϼ̽��ϴ�.\n");
				
				printf("���������� ��ȸ�� ����� �Է��Ͻÿ�.\n");
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
						printf("[%s]����� ������ ������ �����ϴ�. [%d] [%d] [%d]. ��������� [%d]�Դϴ�.\n"
							, p[i].szEName, w, y, z, nGpAvr);
						
						break;
					}
					else if(i==8)
					{
						printf("��ȿ���� ���� ����Դϴ�. \n");
						
						break;
					}					
				}		
				
				printf("��� ����� �������� �Դϴ�. ");
				
				for(int i=0;i<9;i++)
				{	
					
					int w=p[i].nGp1;
					int y=p[i].nGp2;
					int z=p[i].nGp3;
										
					printf("����� [%s] ����1[%d] ����2[%d] ����3[%d]. ", p[i].szEName, w, y, z);									
				}
				
				
				break;
				
			default:
				printf("[DBG] �� �� ���� �޴��� �����ϼ̽��ϴ�.\n");
				
				break;
		}
		getch();
	}
	fclose(fp);
	
	return;
}


 int MenuMgr()
{
	//ȭ�� �����
	system("cls");
	
	// �޴� ����
	printf("������ ���Ͻô� �޴��� �����ϼ���.\n");
	printf("  \n");
	printf(" 1)��ȸ\n");
	printf(" 2)�Է�\n");
	printf(" 3)����\n");
	printf(" 4)����\n");
	printf(" 5)��������\n");
	printf(" 0)����\n");
	
	int nMenu=getch();
	printf("[DBG] ���� �޴� : [%d]\n", nMenu);
	
	return nMenu;	
}
