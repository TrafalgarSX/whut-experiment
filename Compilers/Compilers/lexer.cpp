//#include<stdio.h>
//#include<stdlib.h>
//#include<direct.h>
//#include<string>
//#include"variable.h"
//#include"lexer.h"
//using namespace std;
//extern const char* IFILEPATH;
//extern const char* OFILEPATH;
//int lexer::line = 0;
////Word Word::andd("&&", AND), orr("||", OR), eq("==", EQ), ne("!=", NE), le("<=", LE), ge(">=", GE), minus("minus", MINUS), True("true", TRUE), False("false", FALSE), temp("t", TEMP);
////Type Type::Int("int", BASIC, 4), Float("float", BASIC, 8), Char("char", BASIC, 1), Bool("bool", BASIC, 1);
//int main() {
//	char* buffer;
//	//获取当前文件路径
//	if ((buffer = _getcwd(NULL, 0)) == NULL) {
//		perror("getcwd error");
//	}
//	string ifilename = "\\lexer.txt";
//	string ofilename = "\\lexout.txt";
//	string temp = string(buffer) + ifilename;
//	IFILEPATH = temp.c_str();
//	string temp1 = string(buffer) + ofilename;
//	OFILEPATH = temp1.c_str();
//	lexer lex;
//	lex.openFile();
//	int flag;
//	while (true) {
//		flag = lex.scans();
//		if (flag != 0&&flag!=ENDFILE) {
//				continue;
//		}
//		else if(flag==ERROR) {
//			cout << "单词输入错误！" << endl;
//		}
//		else if (flag == ENDFILE) {
//			cout << "词法分析结束！" << endl;
//			break;
//		}
//	}
//	//lex.out();
//	lex.inFile.close();
//	lex.targetFile.close();
//
//}