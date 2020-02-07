#include<iostream>
#include<stdio.h>
#include<algorithm>
#include<direct.h>
#include<stack>
#include<string>
#include<iomanip>
#include"parser2.h"
extern unordered_map<char, int> col2;
//extern string ACTION[20][10];  SLR法的表
extern const char* PFILEPATH;
extern char PIROR[23][23];
//extern int GOTO[20][4];   SLR法的表
extern const char* IFILEPATH;
extern const char* OFILEPATH;

int lexer::line = 0;
using namespace std;

int main() {


	//词法分析步骤
	char* buffer;
	//获取当前文件路径
	if ((buffer = _getcwd(NULL, 0)) == NULL) {
		perror("getcwd error");
	}
	string ifilename = "\\lexer.txt";
	string ofilename = "\\lexout.txt";
	string temp = string(buffer) + ifilename;
	IFILEPATH = temp.c_str();
	string temp1 = string(buffer) + ofilename;
	OFILEPATH = temp1.c_str();
	lexer lex;
	lex.openFile();
	int flag;
	while (true) {
		flag = lex.scans();
		if (flag != 0 && flag != ENDFILE && flag != 5000) {
			continue;
		}
		else if (flag == ERROR) {
			cout << "单词输入错误！" << endl;
		}
		else if (flag == ENDFILE) {
			cout << "词法分析结束！" << endl;
			break;
		}
		else if (flag == 5000) {
			getchar();
			exit(0);
		}
	}
	//lex.out();
	lex.inFile.close();
	lex.targetFile.close();


	//语法分析准备
	stack<attribute> state;//状态栈  左边的那个栈  规约过程的  用vector表示
	stack<attribute> symbol;//符号规约栈    右边那个栈  存一些结构体   
	attribute tempfin;//要压入符号栈的当前终结符号
	tempfin.symbol = '#';
	symbol.push(tempfin);

	initpro();//初始化  产生式  结构体数组
	//这个应该是为了输出  移进规约过程而写的  暂时不用
	string sy;
	sy.push_back('#');
	//初始化符号栈
	for (int i = lex.sentence.size() - 1; i >= 0; --i) {//这里是把输入的终结符串 变成 符号栈  将一些ID和数字变成i和n
		if (lex.sentence[i].num == ID) {
			tempfin.symbol = 'i';  //id   表示这个终结符号是个ID
			tempfin.sval = lex.sentence[i].sename;
			symbol.push(tempfin);
			//sy.push_back('i');
		}
		else if (lex.sentence[i].num == NUM) {
			tempfin.symbol = 'n';  //n   表示这个终结符号是个  数字
			tempfin.nval = lex.sentence[i].sename;
			symbol.push(tempfin);
		}
		//对于>=  <=两个的要有判断  特殊情况   <=用 小L表示 >=用g表示  less equal   great equal
		else if (lex.sentence[i].num == LE) {
			tempfin.symbol = 'l';
			symbol.push(tempfin);
		}
		else if (lex.sentence[i].num == GE) {
			tempfin.symbol = 'g';
			symbol.push(tempfin);
		}
		else if (lex.sentence[i].num == OPERATOR) {//操作符   +  -  *  /  只有一个的
			tempfin.symbol=(lex.sentence[i].sename.at(0));//字符串“+”变成  字符‘+’  
			symbol.push(tempfin);
			//sy.push_back(lex.sentence[i].sename.at(0));
		}
		else if (lex.sentence[i].num == BAUNDARY) {//本次文法中  用到的仅有  （  ）  左右 括号
			tempfin.symbol = (lex.sentence[i].sename.at(0));
			symbol.push(tempfin);
			//sy.push_back(lex.sentence[i].sename.at(0));
		}
		else if (lex.sentence[i].num == WHILE) {
			tempfin.symbol = 'w';
			symbol.push(tempfin);
		}
	}
	//reverse(sy.begin(), sy.end());//输出移进规约过程所用

	//语法分析部分
	//语法分析结果 输出到以下两个文件中
	string parser = "\\parser.txt";
	string parserout = "\\parserout.txt";
	string temp3 = string(buffer) + parser;
	string temp4 = string(buffer) + parserout;
	PFILEPATH = temp3.c_str();
	POFILEPATH = temp4.c_str();

	pfile.open(PFILEPATH);
	pofile.open(POFILEPATH);
	if (!pfile.is_open() || !pofile.is_open()) {
		cout << "Could not find the file\n" << endl;
		cout << "Program terminating\n" << endl;
		exit(EXIT_FAILURE);
	}
	else {
		cout << "Open file successfully" << endl;
	}
	/*  
	//这里开始简单优先文法的主要部分
	*/
	tempfin.symbol = '#';//简单优先文法  左边状态栈的  栈底总是#
	state.push(tempfin);//令开始状态为栈顶的状态

	attribute sta;//栈顶状态   用结构体中的 sta.symbol表示
	attribute sym;//为  符号栈的第一个符号  同样用结构体中的 sym.symbol表示
	sym = symbol.top();//栈顶的 第一个符号获得
	char colsym,colsta;
	//一个是符号栈栈顶的文法符号，一个是状态栈的文法符号
	//上面两个字符用来比较优先级  找到优先级矩阵
	string link; //用来连接弹出的文法符号 变成符号串
	vector<attribute> attributes;//用来保存状态栈弹出的那些  结构体 因为规约时的动作要用
	int proi;//产生式的数组下标
	flag = 0;
	while (true) {
		colsta = state.top().symbol;
		colsym = symbol.top().symbol;
		int a=col2[colsym];
		int b=col2[colsta];
		//优先级矩阵 用col2作为文法符号到行号列号的映射
		//行是 状态栈的文法符号映射  列是符号栈的文法符号的映射
		if (PIROR[col2[colsta]][col2[colsym]]=='<'||PIROR[col2[colsta]][col2[colsym]]=='=') {
			//当 状态栈的栈顶文法符号的优先级小于等于符号栈顶文法符号的优先级时
			//弹出符号栈栈顶   入状态栈
			sym = symbol.top();
			symbol.pop();//弹出符号栈
			if (state.top().symbol == 'w'||state.top().symbol=='C') { flag = 1; }
			state.push(sym);//入状态栈
			if (flag&&sym.symbol == '(') {//如果栈顶是( (紧跟while的右括号） 就执行一个动作
				flag = 0;
				midAct(1);
			}
			else if (flag&&sym.symbol == ')') {
				flag = 0;
				midAct(2);
			}

		}
		else if (PIROR[col2[colsta]][col2[colsym]] == '>') {
		//如果大于  就从状态栈中 找到一个小于的之后的全部弹出 再根据弹出的符号串判断是不是文法之一的右部
			sta = state.top();
			state.pop();
			attributes.push_back(sta);
			link += sta.symbol;//连接状态栈弹出的文法符号
			while (PIROR[col2[state.top().symbol]][col2[sta.symbol]] != '<') {
				//如果  ak-1<ak则停止 否则继续弹出
				sta = state.top();
				state.pop();
				link += sta.symbol;
				attributes.push_back(sta);
			}
			//因为link 连接的弹出的文法符号串是反的（和产生式相比）  所以需要逆转
			reverse(link.begin(), link.end());
			reverse(attributes.begin(), attributes.end());

			proi=isright(link);//判断是不是该文法产生式的右部
			if(proi==FALSE){
				sta.symbol = 'P';
			}
			else{
				sta.symbol = pro[proi].left; 
			}
			if (sta.symbol != 'P') {//如果是该文法的右部，该右部符号进状态栈
				//这里是规约 翻译的话 规约要有一些动作 根据不同的产生式有不同的动作  调用函数
				sta=action(proi,attributes);
				//返回求过属性的文法符号
				//不同产生式的动作 
				//根据proi找到不同的产生式  attributes是弹出的右部的结构体数组来求综合属性

				//清空Link  和attributes
				link.clear();
				attributes.clear();

				if (state.top().symbol == '#'&&sta.symbol=='S') {//如果栈中只剩下#和 S（开始符号）就规约结束
					cout << "语法分析成功！" << endl;
					outquaternary();
					state.push(sta);
					break;//结束循环
				}
				//然后将求过属性值（综合属性或继承属性  本次文法中规约中的综合属性）的进栈
				state.push(sta);//进栈后继续循环分析即可
			
			}
			else {
				cout << "语法分析失败！出错！规约过程中的文法符号串不是该文法的产生式右部！" << endl;
				break;
			}
		}
		else if (PIROR[col2[colsta]][col2[colsym]] == 'N') {
			cout << "语法分析失败！出错！没有此优先级比较" << endl;
			break;
		}
	}

	getchar();
}