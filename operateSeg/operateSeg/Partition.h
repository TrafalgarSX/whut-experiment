#pragma once
#include<iostream>
#include<string>
#include<vector>
#include<unordered_map>
#define USEFUL 1
#define NOUSE 0
using namespace std;
//����˵����
struct Partition {
public:
	int address;//ÿ���������ʼ��ַ
	int length;//ÿ������ĳ���
	int state;//����˵����״̬
	string processName;//ռ��һ������Ľ�����
	int segnum;//��ؽ��̶κ�
	int itemCode;//ÿ����������
	Partition *next;
};
//���ñ�
struct Available {
public:
	int itemCode;//��������  ����Ӧ�ķ���˵��������ͬ
	int address;//��������ʼ��ַ
	int length;//����������

};
//�����
struct Request {
	string processName;//���������
	int segnum;//�ĸ����̵��ĸ���
	int length;//�����������εĳ���
};
struct Segtable {//�νṹ
	int segNum;//�κ�
	int startAdd=0;//����ʼ��ַ
	int segLength;//�γ�
	int visitCount=0;//���ʴ���
	int state=0;//״̬λ  1���ڴ棬0�����
};
//���̽ṹ
struct Process {
	string processName;//������
	unordered_map<int,Segtable> segTable;//�α�
};
bool cmpAsc(const Available& a1, const Available& a2);
bool cmpDec(const Available& a1, const Available& a2);
//���������
void printPartition();
//��ʼ��
void initPartition();
//��ӽ���  �������
void insert();
//�ϲ�������
void Mergeidle();
//���¿��ñ�
void updateAvail();
//�ͷŽ�����ռ�ڴ�
void freePartition();
//����
void theFirst(string processName,int segnum);
//���
void theBest(string processName,int segnum);
//�
void worst(string processName,int segnum);
//Ϊ���̷����ڴ�
void allocation();
void createhead(string processName, Request allocatReq);
bool worstmain(string processName, Request allocatReq);
bool firstmain(string processName, Request allocatReq);
bool bestmain(string processName, Request allocatReq);
void compaction();//�ڴ��������
void eliminate(int size);
void visitraise();