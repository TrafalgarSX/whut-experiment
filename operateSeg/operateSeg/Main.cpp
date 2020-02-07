#include<iostream>
#include"Partition.h"
#include"variable.h"
using namespace std;
int main() {

	//初始化  进程表
	initPartition();

	int nSelection;
	int num;
	char rubbish[1000] = { 0 };
	nSelection = -1;
	printf("******************菜 单*********************\n");
	do {
		printf("1、显示分区状态\n");
		printf("2、释放分区\n");
		printf("3、分配分区\n");
		printf("4、添加进程\n");
		printf("5、增加访问次数\n");
		printf("0、退出\n");
		printf("请选择菜单项编号：");
		nSelection = -1;
		num = scanf_s("%d", &nSelection);
		while (num == 0) {
			scanf_s("%s", rubbish);
			printf("\n\n请选择正确的菜单编号:\n\n");
			printf("1、显示分区状态\n");
			printf("2、释放分区\n");
			printf("3、分配分区\n");
			printf("4、添加进程\n");
			printf("5、增加访问次数\n");
			printf("0、退出\n");
			printf("请选择菜单项编号：");
			num = scanf_s("%d", &nSelection);
		}
		switch (nSelection) {
		case 1: {
			printPartition();
			break;
		}
		case 2: {
			freePartition();
			break;
		}
		case 3: {
			allocation();
			break;
		}
		case 4: {
			insert();
			break;
		}
		case 5: {
			visitraise();
			break;
		}
		case 0: {
			printf("感谢您使用本系统！");
			exit(0);
		}
		default: {
			printf("输入的菜单编号错误！\n");
		}
		}
	} while (nSelection != 0);

}