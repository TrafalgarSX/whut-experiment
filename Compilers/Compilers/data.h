#pragma once
#include<string>
#include<vector>
using namespace std;
#define AND 256
#define BASIC 300
#define	KEYWORD 257//�ؼ���  ������
#define	BREAK 258 
#define	DO 259
#define ELSE 260
#define	EQ 261 //==
#define	FALSE 262
#define	GE 263 //>=
#define	ID 264//��ʶ��
#define	IF 265
#define	INDEX 266
#define LE 267//<=
#define	MINUS 268//-
#define NE 268//!=
#define NUM 269//����
#define OR 270//||
#define	REAL 271//������
#define	TEMP 272
#define	TRUE 273
#define WHILE 274
#define BAUNDARY 275//���
#define ERROR 0//����
#define OPERATOR 276 //�����
#define CHBAUNNUM 6
#define READ -1
#define PLUS 301//+
#define MUL 302//*
#define DIV 303//  /��
#define ASSIGN 304//=  ��ֵ
#define  ENDFILE 1000

#define productions 20
struct production {
public:
	char left;
	string right;
};

struct senten {
	string sename;
	int num;
};
struct attribute {
	char symbol;
	//Ӧ�ù���
	vector<int> btruelist;
	vector<int> bfalselist;
	vector<int> snextlist;
	string addr;//��Ԫʽ�Ľ��  ���ұߵĽ��
	string sval;//�ս�� ID   ��ֵ  
	string nval;//��� �ս�� ���� �п��ܹ�Լ��ʱ�򸳸����� ���ֵ�ֵ
};
struct Quaternary {
	string operate;
	string operand1;
	string operand2;
	string result;
};