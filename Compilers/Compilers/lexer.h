#pragma once
#include<iostream>
#include<fstream>
#include<string>
#include"data.h"
#include<ctype.h>
#include<unordered_map>
#include<vector>
extern char Baundary[6];
extern const char * IFILEPATH;
extern const char * OFILEPATH;
using namespace std;
class lexer
{
public:
	static int line;
	char peek = ' ';
	ifstream inFile;
	ofstream targetFile;
	unordered_map<string,int> words;
	vector<senten> sentence;
	senten sen;
	int count = 0;
	lexer() {
		reserve("if", IF);
		reserve("else", ELSE);
		reserve("while", WHILE);
		reserve("do", DO);
		reserve("break", BREAK);
		reserve("const", KEYWORD); reserve("var", KEYWORD);
		reserve("procedure", KEYWORD); reserve("begin", KEYWORD);
		reserve("write", KEYWORD); reserve("read", KEYWORD);
		reserve("call", KEYWORD); reserve("then", KEYWORD);
		reserve("odd", KEYWORD);reserve("true",TRUE);
		reserve("false",FALSE);
		reserve("int",KEYWORD); reserve("end",KEYWORD);
		reserve("char",KEYWORD); reserve("bool",KEYWORD);
	};
	~lexer();
	void openFile();
	void reserve(string,int);
	int readch();
	bool readch(char c);
	int Baundarys(char c);
	int scans();
	void out();
	void store(string ,int);
private:

};


lexer::~lexer()
{
}
inline void lexer::openFile()
{
	inFile.open(IFILEPATH,ios::in);
	targetFile.open(OFILEPATH,ios::out);
	if (!inFile.is_open()||!targetFile.is_open()) {
		cout << "Could not find the file\n" << endl;
		cout << "Program terminating\n" << endl;
		exit(EXIT_FAILURE);
	}
	else {
		cout << "Open file successfully" << endl;
	}
}
//�����ȡ�ַ�
int lexer::readch() {
	char end;
	peek = inFile.peek();
	//inFile >> end;
	end = inFile.get();
	//����һ�ַ���
	//if (!inFile.eof()) {
		//eof()����true�������Ƕ����ļ������� ��0xff) 
		//�ļ���������ļ�������  �����ַ�����һλ�ΰ��ǣ�
		//�ڴ˱������У��������һ���ַ����ļ�λ�õ�ָ��ͻᶨ�����һ���ַ�
		//���Ի�һֱ�ظ����һ���ַ�
	//	return 1;
	//}
	return 1;
	/*if (end != EOF) {
		return 1;
	}
	else {
		printf("�ļ��Ѿ����꣡");
		return 0;
	}*/
}
inline bool lexer::readch(char c)
{
	readch();//���һ���ַ�  ���ſ��ܻ�Ҫ����һ���ַ�
	if (peek != c) {
		return false;
	}
	//peek = ' ';
	return true;
}
inline int lexer::Baundarys(char c )
{
	for (int i = 0; i < CHBAUNNUM; i++) {
		if (c == Baundary[i]) {
			string temp(1,c);
			//reserve(temp, BAUNDARY);
			sen.sename = temp;
			sen.num = BAUNDARY;
			sentence.push_back(sen);
			store(temp, BAUNDARY);
			return BAUNDARY;
		}
	}
}
inline int lexer::scans()
{
	//����հ��ַ�
	int i=readch();//iû����
	int flag=0;
	while (peek == ' ' || peek == '\t' || peek == '\n') {
		if (peek == '\n')line = line + 1;
		flag = 1;
		readch();
	}
	//if (flag) {
	//	flag = 0;
	//inFile.seekg(-1, ios::cur);//���������հ��ַ� �ͻ���һλ
	//}
	if (peek==EOF) {
		return ENDFILE;//����ļ��Ѿ�����ͷ���  �ʷ���������
	}

	//�����
	switch (peek) {
	case '&':
		if (readch('&')) { 
			sen.sename = "&&"; sen.num = OPERATOR; sentence.push_back(sen); count++;
			store("&&", OPERATOR); return OPERATOR; }//��������һ���ַ����ж��Ƿ���&& �����  ���������ض���һ���ַ���ʼ����
	else { 
			sen.sename = "&"; sen.num = OPERATOR; sentence.push_back(sen); count++;
			store("&", OPERATOR); inFile.seekg(-1, ios::cur); return OPERATOR; }//������� ��Ҫ����һ���ַ�  ���¶���  ���·���
	case '|':
		if (readch('|')) { 
			sen.sename = "||"; sen.num = OPERATOR; sentence.push_back(sen); count++;
			store("||", OPERATOR); return OPERATOR; }
		else{ 
			sen.sename = "|"; sen.num = OPERATOR; sentence.push_back(sen); count++;
			store("|", OPERATOR); inFile.seekg(-1, ios::cur);  return OPERATOR; }
	case '=':
		if (readch('=')) { 
			sen.sename = "=="; sen.num = OPERATOR; sentence.push_back(sen); count++;
			 store("==", OPERATOR); return OPERATOR; }
		else{ 
			sen.sename = "="; sen.num = OPERATOR; sentence.push_back(sen); count++;
			store("=", OPERATOR); inFile.seekg(-1, ios::cur);   return OPERATOR; }
	case '!':
		if (readch('=')) {
			sen.sename = "!="; sen.num = OPERATOR; sentence.push_back(sen); count++;
			store("!=", OPERATOR); return OPERATOR; }
		else{ 
			sen.sename = "!"; sen.num = OPERATOR; sentence.push_back(sen); count++;
			store("!", OPERATOR); inFile.seekg(-1, ios::cur); return OPERATOR; }
	case '<':
		if (readch('=')) { 
			sen.sename = "<="; sen.num = LE; sentence.push_back(sen); count++;
			store("<=", LE);   return OPERATOR; }//�﷨�������õ�<=  ��LE��ʾ������  ���Ǵʷ�����������OPERATOR������  һ��
		else{ 
			sen.sename = "<"; sen.num = OPERATOR; sentence.push_back(sen); count++;
			store("<", OPERATOR); inFile.seekg(-1, ios::cur); return OPERATOR; }
	case '>':
		if (readch('=')) { 
			sen.sename = ">="; sen.num = GE; sentence.push_back(sen); count++;
			store(">=", GE);  return OPERATOR; }//�����Ԥ��������  peek����Ԥ��  ������Ķ�һ��  
		//�﷨�������õ�>=  ��GE��ʾ������  ���Ǵʷ�����������OPERATOR������  һ��
		else
		{	sen.sename = ">"; sen.num = OPERATOR; sentence.push_back(sen); count++;
			store(">", OPERATOR);  inFile.seekg(-1, ios::cur); return OPERATOR; }
	case '+':
		sen.sename = "+"; sen.num = OPERATOR; sentence.push_back(sen); count++;
		store("+",OPERATOR); return OPERATOR;
	case '-':
		sen.sename = "-"; sen.num = OPERATOR; sentence.push_back(sen); count++;
		store("-", OPERATOR);  return OPERATOR;
	case '*':
		sen.sename = "*"; sen.num = OPERATOR; sentence.push_back(sen); count++;
		store("*", OPERATOR);  return OPERATOR;
	case '/':
		sen.sename = "/"; sen.num = OPERATOR; sentence.push_back(sen); count++;
		store("/", OPERATOR);  return OPERATOR;
	case '#':
		sen.sename = "#"; sen.num = OPERATOR; sentence.push_back(sen); count++;
		store("#", OPERATOR);   return OPERATOR;
	}
	//���
	if (peek=='('||peek==')'||peek==','||peek==';'||peek=='{'||peek=='}') {
		count++;
		return Baundarys(peek);//���ֻ��һ��  ���治��Ҫreadch
		//�ж���ͷ���  ѭ�����ñ�������������һ���ַ�
	}
	//�ж��ǲ�������
	if (isdigit(peek)) {
		int v = 0;
		do {
			v = 10 * v + peek - '0';
			readch();
		} while (isdigit(peek));

		if (peek != '.') {
			inFile.seekg(-1, ios::cur);
			//reserve(to_string(v), NUM);
			sen.sename = to_string(v);
			sen.num = NUM;
			sentence.push_back(sen);
			store(to_string(v), NUM);
			count++;
			return NUM;
		}
		float x = v;
		float d = 10;
		for (;;) {
			readch();
			if (!isdigit(peek))break;
			x = x + (peek - '0') / d;
			d = d * 10;
		}
		inFile.seekg(-1, ios::cur);
		//reserve(to_string(x), REAL);
		sen.sename = to_string(x);
		sen.num = REAL;
		sentence.push_back(sen);
		store(to_string(x), REAL);
		count++;
		return REAL;//��������
	}
	//�����ʶ��������
	if (isalpha(peek)) {
		string id;
		do {
			id.push_back(peek);
			readch();
		} while (isalpha(peek)||isdigit(peek));
		//����һλ  ���¿�ʼ����һλ�ַ���ʼ����
		inFile.seekg(-1, ios::cur);
		if (words.find(id) != words.end()) {
			//����ڴ洢�����ֵ�map������Ϊ������
			sen.sename = id;//�����ֵ�ȫ��  ����while  if    switch��Щ
			sen.num = words[id];//���ر����ֵ�  ��  ����   WHILE    IF  ��Щ��  ��������
			sentence.push_back(sen);
			store(id, words[id]);
			count++;
			return ID;//�����map�д����� �п����Ǳ����� Ҳ�п����Ǳ�ʶ��
		}
		else {
			sen.sename = id;
			sen.num = ID;
			sentence.push_back(sen);
			count++;
			store(id, ID);//���ʷ��������������ļ���
			return ID;
		}
	}
	cout << "��" << count + 1 << "�����ʴʷ���������" << endl;
	return 5000;
	
}
inline void lexer::out()
{
	for (const auto &i : words) {
		targetFile <<"(" <<i.first <<","<< i.second <<")"<< endl;
	}
}
inline void lexer::store(string first, int second)
{
	targetFile << "(  " << first << "," << second << "  )" << endl;
}
void lexer::reserve(string lexeme,int tag) {
	words[lexeme]=tag;
}
////ע��
//if (peek == '/') {
//	readch();
//	if (peek == '/') {
//		for (;; readch()) {
//			if (peek == '\n') {
//				break;
//			}
//		}
//	}
//	else if (peek == '*') {
//		char prevPeek = ' ';
//		for (;; prevPeek = peek, readch()) {
//			if (prevPeek == '*'&&peek == '/') {
//				break;
//			}
//		}
//	}
//	//���ﻹ�и�else  ��ǰ���������ǵ�ʱ��  ���׳��﷨�����쳣  ���Ǻ���û���쳣�����Ϣ �ҾͲ�д���Ժ�����˵
////���û�п��ǳ�������
//}

//	//�����
//switch (peek) {
//case '&':
//	if (readch('&')) { reserve("&&", OPERATOR); store("&&", OPERATOR); return OPERATOR; }//��������һ���ַ����ж��Ƿ���&& �����  ���������ض���һ���ַ���ʼ����
//	else { reserve("&", OPERATOR); store("&", OPERATOR); inFile.seekg(-1, ios::cur); return OPERATOR; }//������� ��Ҫ����һ���ַ�  ���¶���  ���·���
//case '|':
//	if (readch('|')) { reserve("||", OPERATOR); store("||", OPERATOR); return OPERATOR; }
//	else { reserve("|", OPERATOR); store("|", OPERATOR); inFile.seekg(-1, ios::cur);  return OPERATOR; }
//case '=':
//	if (readch('=')) { reserve("==", OPERATOR); store("==", OPERATOR); return OPERATOR; }
//	else { reserve("=", OPERATOR); store("=", OPERATOR); inFile.seekg(-1, ios::cur);   return OPERATOR; }
//case '!':
//	if (readch('=')) { reserve("=", OPERATOR); store("=", OPERATOR); return OPERATOR; }
//	else { reserve("!", OPERATOR); store("!", OPERATOR); inFile.seekg(-1, ios::cur); return OPERATOR; }
//case '<':
//	if (readch('=')) { reserve("<=", OPERATOR); store("<=", OPERATOR);   return OPERATOR; }
//	else { reserve("<", OPERATOR);  store("<", OPERATOR); inFile.seekg(-1, ios::cur); return OPERATOR; }
//case '>':
//	if (readch('=')) { reserve(">=", OPERATOR);   store(">=", OPERATOR);  return OPERATOR; }//�����Ԥ��������  peek����Ԥ��  ������Ķ�һ��  
//	else { reserve(">", OPERATOR); store(">", OPERATOR);  inFile.seekg(-1, ios::cur); return OPERATOR; }
//case '+':reserve("+", OPERATOR); store("+", OPERATOR); return OPERATOR;
//case '-':reserve("-", OPERATOR);  store("-", OPERATOR);  return OPERATOR;
//case '*':reserve("*", OPERATOR); store("*", OPERATOR);  return OPERATOR;
//case '/':reserve("/", OPERATOR); store("/", OPERATOR);  return OPERATOR;
//case '#':reserve("#", OPERATOR);  store("#", OPERATOR);   return OPERATOR;
//}