#pragma once
#include<string>
#include<vector>
using namespace std;
#define AND 256
#define BASIC 300
#define	KEYWORD 257//关键字  保留字
#define	BREAK 258 
#define	DO 259
#define ELSE 260
#define	EQ 261 //==
#define	FALSE 262
#define	GE 263 //>=
#define	ID 264//标识符
#define	IF 265
#define	INDEX 266
#define LE 267//<=
#define	MINUS 268//-
#define NE 268//!=
#define NUM 269//数字
#define OR 270//||
#define	REAL 271//浮点数
#define	TEMP 272
#define	TRUE 273
#define WHILE 274
#define BAUNDARY 275//界符
#define ERROR 0//出错
#define OPERATOR 276 //运算符
#define CHBAUNNUM 6
#define READ -1
#define PLUS 301//+
#define MUL 302//*
#define DIV 303//  /除
#define ASSIGN 304//=  赋值
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
	//应该够了
	vector<int> btruelist;
	vector<int> bfalselist;
	vector<int> snextlist;
	string addr;//四元式的结果  最右边的结果
	string sval;//终结符 ID   的值  
	string nval;//求出 终结符 属性 有可能规约的时候赋给其他 数字的值
};
struct Quaternary {
	string operate;
	string operand1;
	string operand2;
	string result;
};