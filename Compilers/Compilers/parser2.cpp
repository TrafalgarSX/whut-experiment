#include<iostream>
#include<stdio.h>
#include<algorithm>
#include<direct.h>
#include<stack>
#include<string>
#include<iomanip>
#include"parser2.h"
extern unordered_map<char, int> col2;
//extern string ACTION[20][10];  SLR���ı�
extern const char* PFILEPATH;
extern char PIROR[23][23];
//extern int GOTO[20][4];   SLR���ı�
extern const char* IFILEPATH;
extern const char* OFILEPATH;

int lexer::line = 0;
using namespace std;

int main() {


	//�ʷ���������
	char* buffer;
	//��ȡ��ǰ�ļ�·��
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
			cout << "�����������" << endl;
		}
		else if (flag == ENDFILE) {
			cout << "�ʷ�����������" << endl;
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


	//�﷨����׼��
	stack<attribute> state;//״̬ջ  ��ߵ��Ǹ�ջ  ��Լ���̵�  ��vector��ʾ
	stack<attribute> symbol;//���Ź�Լջ    �ұ��Ǹ�ջ  ��һЩ�ṹ��   
	attribute tempfin;//Ҫѹ�����ջ�ĵ�ǰ�ս����
	tempfin.symbol = '#';
	symbol.push(tempfin);

	initpro();//��ʼ��  ����ʽ  �ṹ������
	//���Ӧ����Ϊ�����  �ƽ���Լ���̶�д��  ��ʱ����
	string sy;
	sy.push_back('#');
	//��ʼ������ջ
	for (int i = lex.sentence.size() - 1; i >= 0; --i) {//�����ǰ�������ս���� ��� ����ջ  ��һЩID�����ֱ��i��n
		if (lex.sentence[i].num == ID) {
			tempfin.symbol = 'i';  //id   ��ʾ����ս�����Ǹ�ID
			tempfin.sval = lex.sentence[i].sename;
			symbol.push(tempfin);
			//sy.push_back('i');
		}
		else if (lex.sentence[i].num == NUM) {
			tempfin.symbol = 'n';  //n   ��ʾ����ս�����Ǹ�  ����
			tempfin.nval = lex.sentence[i].sename;
			symbol.push(tempfin);
		}
		//����>=  <=������Ҫ���ж�  �������   <=�� СL��ʾ >=��g��ʾ  less equal   great equal
		else if (lex.sentence[i].num == LE) {
			tempfin.symbol = 'l';
			symbol.push(tempfin);
		}
		else if (lex.sentence[i].num == GE) {
			tempfin.symbol = 'g';
			symbol.push(tempfin);
		}
		else if (lex.sentence[i].num == OPERATOR) {//������   +  -  *  /  ֻ��һ����
			tempfin.symbol=(lex.sentence[i].sename.at(0));//�ַ�����+�����  �ַ���+��  
			symbol.push(tempfin);
			//sy.push_back(lex.sentence[i].sename.at(0));
		}
		else if (lex.sentence[i].num == BAUNDARY) {//�����ķ���  �õ��Ľ���  ��  ��  ���� ����
			tempfin.symbol = (lex.sentence[i].sename.at(0));
			symbol.push(tempfin);
			//sy.push_back(lex.sentence[i].sename.at(0));
		}
		else if (lex.sentence[i].num == WHILE) {
			tempfin.symbol = 'w';
			symbol.push(tempfin);
		}
	}
	//reverse(sy.begin(), sy.end());//����ƽ���Լ��������

	//�﷨��������
	//�﷨������� ��������������ļ���
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
	//���￪ʼ�������ķ�����Ҫ����
	*/
	tempfin.symbol = '#';//�������ķ�  ���״̬ջ��  ջ������#
	state.push(tempfin);//�ʼ״̬Ϊջ����״̬

	attribute sta;//ջ��״̬   �ýṹ���е� sta.symbol��ʾ
	attribute sym;//Ϊ  ����ջ�ĵ�һ������  ͬ���ýṹ���е� sym.symbol��ʾ
	sym = symbol.top();//ջ���� ��һ�����Ż��
	char colsym,colsta;
	//һ���Ƿ���ջջ�����ķ����ţ�һ����״̬ջ���ķ�����
	//���������ַ������Ƚ����ȼ�  �ҵ����ȼ�����
	string link; //�������ӵ������ķ����� ��ɷ��Ŵ�
	vector<attribute> attributes;//��������״̬ջ��������Щ  �ṹ�� ��Ϊ��Լʱ�Ķ���Ҫ��
	int proi;//����ʽ�������±�
	flag = 0;
	while (true) {
		colsta = state.top().symbol;
		colsym = symbol.top().symbol;
		int a=col2[colsym];
		int b=col2[colsta];
		//���ȼ����� ��col2��Ϊ�ķ����ŵ��к��кŵ�ӳ��
		//���� ״̬ջ���ķ�����ӳ��  ���Ƿ���ջ���ķ����ŵ�ӳ��
		if (PIROR[col2[colsta]][col2[colsym]]=='<'||PIROR[col2[colsta]][col2[colsym]]=='=') {
			//�� ״̬ջ��ջ���ķ����ŵ����ȼ�С�ڵ��ڷ���ջ���ķ����ŵ����ȼ�ʱ
			//��������ջջ��   ��״̬ջ
			sym = symbol.top();
			symbol.pop();//��������ջ
			if (state.top().symbol == 'w'||state.top().symbol=='C') { flag = 1; }
			state.push(sym);//��״̬ջ
			if (flag&&sym.symbol == '(') {//���ջ����( (����while�������ţ� ��ִ��һ������
				flag = 0;
				midAct(1);
			}
			else if (flag&&sym.symbol == ')') {
				flag = 0;
				midAct(2);
			}

		}
		else if (PIROR[col2[colsta]][col2[colsym]] == '>') {
		//�������  �ʹ�״̬ջ�� �ҵ�һ��С�ڵ�֮���ȫ������ �ٸ��ݵ����ķ��Ŵ��ж��ǲ����ķ�֮һ���Ҳ�
			sta = state.top();
			state.pop();
			attributes.push_back(sta);
			link += sta.symbol;//����״̬ջ�������ķ�����
			while (PIROR[col2[state.top().symbol]][col2[sta.symbol]] != '<') {
				//���  ak-1<ak��ֹͣ �����������
				sta = state.top();
				state.pop();
				link += sta.symbol;
				attributes.push_back(sta);
			}
			//��Ϊlink ���ӵĵ������ķ����Ŵ��Ƿ��ģ��Ͳ���ʽ��ȣ�  ������Ҫ��ת
			reverse(link.begin(), link.end());
			reverse(attributes.begin(), attributes.end());

			proi=isright(link);//�ж��ǲ��Ǹ��ķ�����ʽ���Ҳ�
			if(proi==FALSE){
				sta.symbol = 'P';
			}
			else{
				sta.symbol = pro[proi].left; 
			}
			if (sta.symbol != 'P') {//����Ǹ��ķ����Ҳ������Ҳ����Ž�״̬ջ
				//�����ǹ�Լ ����Ļ� ��ԼҪ��һЩ���� ���ݲ�ͬ�Ĳ���ʽ�в�ͬ�Ķ���  ���ú���
				sta=action(proi,attributes);
				//����������Ե��ķ�����
				//��ͬ����ʽ�Ķ��� 
				//����proi�ҵ���ͬ�Ĳ���ʽ  attributes�ǵ������Ҳ��Ľṹ�����������ۺ�����

				//���Link  ��attributes
				link.clear();
				attributes.clear();

				if (state.top().symbol == '#'&&sta.symbol=='S') {//���ջ��ֻʣ��#�� S����ʼ���ţ��͹�Լ����
					cout << "�﷨�����ɹ���" << endl;
					outquaternary();
					state.push(sta);
					break;//����ѭ��
				}
				//Ȼ���������ֵ���ۺ����Ի�̳�����  �����ķ��й�Լ�е��ۺ����ԣ��Ľ�ջ
				state.push(sta);//��ջ�����ѭ����������
			
			}
			else {
				cout << "�﷨����ʧ�ܣ�������Լ�����е��ķ����Ŵ����Ǹ��ķ��Ĳ���ʽ�Ҳ���" << endl;
				break;
			}
		}
		else if (PIROR[col2[colsta]][col2[colsym]] == 'N') {
			cout << "�﷨����ʧ�ܣ�����û�д����ȼ��Ƚ�" << endl;
			break;
		}
	}

	getchar();
}