#include<iostream>
#include"Partition.h"
#include"variable.h"
using namespace std;
int main() {

	//��ʼ��  ���̱�
	initPartition();

	int nSelection;
	int num;
	char rubbish[1000] = { 0 };
	nSelection = -1;
	printf("******************�� ��*********************\n");
	do {
		printf("1����ʾ����״̬\n");
		printf("2���ͷŷ���\n");
		printf("3���������\n");
		printf("4����ӽ���\n");
		printf("5�����ӷ��ʴ���\n");
		printf("0���˳�\n");
		printf("��ѡ��˵����ţ�");
		nSelection = -1;
		num = scanf_s("%d", &nSelection);
		while (num == 0) {
			scanf_s("%s", rubbish);
			printf("\n\n��ѡ����ȷ�Ĳ˵����:\n\n");
			printf("1����ʾ����״̬\n");
			printf("2���ͷŷ���\n");
			printf("3���������\n");
			printf("4����ӽ���\n");
			printf("5�����ӷ��ʴ���\n");
			printf("0���˳�\n");
			printf("��ѡ��˵����ţ�");
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
			printf("��л��ʹ�ñ�ϵͳ��");
			exit(0);
		}
		default: {
			printf("����Ĳ˵���Ŵ���\n");
		}
		}
	} while (nSelection != 0);

}