//#include<iostream>
//#include<stdio.h>
//#include<algorithm>
//#include<unordered_map>
//#include<direct.h>
//#include"variable.h"
//#include<stack>
//#include"lexer.h"
//#include<string>
//#include<iomanip>
//extern unordered_map<char, int> col;
//extern string ACTION[20][10];
//extern const char* PFILEPATH;
//extern int GOTO[20][4];
//extern const char* IFILEPATH;
//extern const char* OFILEPATH;
//extern production pro[productions];
//int lexer::line = 0;
//using namespace std;
//void initpro() {
//	pro[0].left = 'S'; pro[0].right = "i=E";
//	pro[1].left = 'E'; pro[1].right = "T";
//	pro[2].left = 'E'; pro[2].right = "E+T";
//	pro[3].left = 'E'; pro[3].right = "E-T";
//	pro[4].left = 'T'; pro[4].right = "F";
//	pro[5].left = 'T'; pro[5].right = "T*F";
//	pro[6].left = 'T'; pro[6].right = "T/F";
//	pro[7].left = 'F'; pro[7].right = "i";
//	pro[8].left = 'F'; pro[8].right = "n";
//	pro[9].left = 'F'; pro[9].right = "(E)";
//}
//int main() {
//
//
//	//�ʷ���������
//	char* buffer;
//	//��ȡ��ǰ�ļ�·��
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
//		if (flag != 0 && flag != ENDFILE&&flag!=5000) {
//			continue;
//		}
//		else if (flag == ERROR) {
//			cout << "�����������" << endl;
//		}
//		else if (flag == ENDFILE) {
//			cout << "�ʷ�����������" << endl;
//			break;
//		}
//		else if (flag == 5000) {
//			getchar();
//			exit(0);
//		}
//	}
//	//lex.out();
//	lex.inFile.close();
//	lex.targetFile.close();
//
//	//�﷨����׼��
//	stack<int> state;
//	stack<char> symbol;
//	symbol.push('$');
//	initpro();//��ʼ��  ����ʽ  �ṹ������
//	string sy;
//	sy.push_back('$');
//	//��ʼ������ջ
//	for (int i = lex.sentence.size() - 1; i >= 0; --i) {
//		if(lex.sentence[i].num==ID){
//			symbol.push('i');
//			sy.push_back('i');
//		}
//		else if (lex.sentence[i].num ==NUM ) {
//			symbol.push('n');
//			sy.push_back('n');
//		}
//		else if (lex.sentence[i].num == OPERATOR) {
//			symbol.push(lex.sentence[i].sename.at(0));
//			sy.push_back(lex.sentence[i].sename.at(0));
//		}
//		else if (lex.sentence[i].num ==BAUNDARY ) {
//			symbol.push(lex.sentence[i].sename.at(0));
//			sy.push_back(lex.sentence[i].sename.at(0));
//		}
//	}
//	reverse(sy.begin(),sy.end());
//	//�﷨��������
//	string parser="\\parser.txt";
//	string parserout = "\\parserout.txt";
//	string temp3 = string(buffer) + parser;
//	string temp4 = string(buffer) + parserout;
//	PFILEPATH = temp3.c_str();
//	POFILEPATH = temp4.c_str();
//	ofstream pfile;
//	ofstream pofile;
//	pfile.open(PFILEPATH);
//	pofile.open(POFILEPATH);
//	if (!pfile.is_open()||!pofile.is_open() ) {
//		cout << "Could not find the file\n" << endl;
//		cout << "Program terminating\n" << endl;
//		exit(EXIT_FAILURE);
//	}
//	else {
//		cout << "Open file successfully" << endl;
//	}
//	state.push(0);//�ʼ״̬Ϊջ����״̬
//	int sta;//ջ��״̬
//	char sym;//Ϊ  ����ջ�ĵ�һ������
//	sym = symbol.top();
//	string next;
//	int nexts;
//	pofile << setiosflags(ios::left);
//	string status="0";
//	pofile << "״̬ջ" <<setw(10)<< "\t\t"<<"����ջ" <<setw(20)<< endl;
//	pofile << status <<setw(10) <<"\t\t" << sy<<setw(20) << endl;
//	while (true) {
//		sta = state.top();
//		next=ACTION[sta][col[sym]];
//		if (next.at(0) == 's') {
//			if (next.length() == 2) {
//				nexts = next[1] - '0';
//			}
//			else if (next.length() == 3) {
//				nexts = (next[1] - '0') * 10 + next[2] - '0';
//			}
//
//			symbol.pop();//��������ջջ��
//			sym = symbol.top();//��symΪ��һ���������
//			sta = nexts;//״̬
//			state.push(sta);//ѹջ
//
//			/*status.push_back('0');
//			status += to_string(sta);
//			sy.erase(0, 1);
//			pofile <<status<<setw(10) << "\t\t"<< sy<<setw(20)<<endl;*/
//		}
//		else if (next.at(0) == 'r') {
//			if (next.length() == 2) {
//				nexts = next[1] - '0';
//			}
//			else if (next.length() == 3) {
//				nexts = (next[1] - '0') * 10 + next[2] - '0';
//			}
//			int i=pro[nexts-1].right.length();
//			for (int j = 0; j < i; j++) {
//
//				state.pop();//����������Լ��״̬
//				status.pop_back();
//				
//			}
//			pfile << pro[nexts-1].left << "-->"<<pro[nexts-1].right  << endl;
//			sta = state.top();//ջ��״̬
//			state.push(GOTO[sta][col[pro[nexts - 1].left]]);//���  Ȼ����뼴��ѹջ��״̬
//
//			/*status.push_back('0');
//			status += to_string(GOTO[sta][col[pro[nexts - 1].left]]);
//			pofile << status<<setw(10) << "\t\t" << sy <<setw(20)<< endl;*/
//		}
//		else if (next == "acc") {
//			cout << "�﷨�����ɹ���" << endl;
//			break;
//		}
//		else if (next == "error") {
//			cout << "�﷨����ʧ�ܣ�" << endl;
//			break;
//		}
//	}
//	getchar();
//}