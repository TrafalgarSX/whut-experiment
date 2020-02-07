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
//逐个读取字符
int lexer::readch() {
	char end;
	peek = inFile.peek();
	//inFile >> end;
	end = inFile.get();
	//用另一种方法
	//if (!inFile.eof()) {
		//eof()返回true的条件是读到文件结束符 （0xff) 
		//文件的最后不是文件结束符  最后的字符的下一位次啊是，
		//在此编译器中，读到最后一个字符后，文件位置的指针就会定在最后一个字符
		//所以会一直重复最后一个字符
	//	return 1;
	//}
	return 1;
	/*if (end != EOF) {
		return 1;
	}
	else {
		printf("文件已经读完！");
		return 0;
	}*/
}
inline bool lexer::readch(char c)
{
	readch();//多读一个字符  接着可能会要回退一个字符
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
	//处理空白字符
	int i=readch();//i没用了
	int flag=0;
	while (peek == ' ' || peek == '\t' || peek == '\n') {
		if (peek == '\n')line = line + 1;
		flag = 1;
		readch();
	}
	//if (flag) {
	//	flag = 0;
	//inFile.seekg(-1, ios::cur);//如果处理过空白字符 就会跳一位
	//}
	if (peek==EOF) {
		return ENDFILE;//如果文件已经读完就返回  词法分析结束
	}

	//运算符
	switch (peek) {
	case '&':
		if (readch('&')) { 
			sen.sename = "&&"; sen.num = OPERATOR; sentence.push_back(sen); count++;
			store("&&", OPERATOR); return OPERATOR; }//这里多读了一个字符来判断是否是&& 如果是  则正常返回读下一个字符开始分析
	else { 
			sen.sename = "&"; sen.num = OPERATOR; sentence.push_back(sen); count++;
			store("&", OPERATOR); inFile.seekg(-1, ios::cur); return OPERATOR; }//如果不是 就要回退一个字符  重新读入  重新分析
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
			store("<=", LE);   return OPERATOR; }//语法分析会用到<=  用LE表示更方便  但是词法分析返回是OPERATOR就行了  一样
		else{ 
			sen.sename = "<"; sen.num = OPERATOR; sentence.push_back(sen); count++;
			store("<", OPERATOR); inFile.seekg(-1, ios::cur); return OPERATOR; }
	case '>':
		if (readch('=')) { 
			sen.sename = ">="; sen.num = GE; sentence.push_back(sen); count++;
			store(">=", GE);  return OPERATOR; }//这里的预读可以用  peek（）预读  不用真的读一个  
		//语法分析会用到>=  用GE表示更方便  但是词法分析返回是OPERATOR就行了  一样
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
	//界符
	if (peek=='('||peek==')'||peek==','||peek==';'||peek=='{'||peek=='}') {
		count++;
		return Baundarys(peek);//界符只读一个  里面不需要readch
		//判断完就返回  循环调用本函数继续读下一个字符
	}
	//判断是不是数字
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
		return REAL;//处理浮点数
	}
	//处理标识符或保留字
	if (isalpha(peek)) {
		string id;
		do {
			id.push_back(peek);
			readch();
		} while (isalpha(peek)||isdigit(peek));
		//回退一位  重新开始读入一位字符开始分析
		inFile.seekg(-1, ios::cur);
		if (words.find(id) != words.end()) {
			//如果在存储保留字的map存在则为保留字
			sen.sename = id;//保留字的全称  比如while  if    switch这些
			sen.num = words[id];//返回保留字的  宏  比如   WHILE    IF  这些宏  都是整数
			sentence.push_back(sen);
			store(id, words[id]);
			count++;
			return ID;//如果在map中存在则 有可能是保留字 也有可能是标识符
		}
		else {
			sen.sename = id;
			sen.num = ID;
			sentence.push_back(sen);
			count++;
			store(id, ID);//将词法分析结果输出到文件中
			return ID;
		}
	}
	cout << "第" << count + 1 << "个单词词法分析错误" << endl;
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
////注释
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
//	//这里还有个else  当前两个都不是的时候  就抛出语法错误异常  但是后面没有异常相关信息 我就不写了以后懂了再说
////这个没有考虑除法符号
//}

//	//运算符
//switch (peek) {
//case '&':
//	if (readch('&')) { reserve("&&", OPERATOR); store("&&", OPERATOR); return OPERATOR; }//这里多读了一个字符来判断是否是&& 如果是  则正常返回读下一个字符开始分析
//	else { reserve("&", OPERATOR); store("&", OPERATOR); inFile.seekg(-1, ios::cur); return OPERATOR; }//如果不是 就要回退一个字符  重新读入  重新分析
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
//	if (readch('=')) { reserve(">=", OPERATOR);   store(">=", OPERATOR);  return OPERATOR; }//这里的预读可以用  peek（）预读  不用真的读一个  
//	else { reserve(">", OPERATOR); store(">", OPERATOR);  inFile.seekg(-1, ios::cur); return OPERATOR; }
//case '+':reserve("+", OPERATOR); store("+", OPERATOR); return OPERATOR;
//case '-':reserve("-", OPERATOR);  store("-", OPERATOR);  return OPERATOR;
//case '*':reserve("*", OPERATOR); store("*", OPERATOR);  return OPERATOR;
//case '/':reserve("/", OPERATOR); store("/", OPERATOR);  return OPERATOR;
//case '#':reserve("#", OPERATOR);  store("#", OPERATOR);   return OPERATOR;
//}