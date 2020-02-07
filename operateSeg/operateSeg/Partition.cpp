#include<iostream>
#include<algorithm>
#include<string>
#include"Partition.h"
#include<vector>
#include<fstream>

using namespace std;
extern vector<Available> available;//���з��������ñ�
extern vector<Request> request;//�����
extern Partition * head, *tail, *temp;
extern int reqNum;
extern int total;
extern unordered_map<string,Process> processTable;//���̱�  ��ǰ�ж��ٽ��� �п������ڴ����
//��ʼ������˵����
void initPartition() {
	ifstream inFile;//���ļ����г�ʼ��  
	//��ʼ������ �ڴ��ܴ�С�����̸�����ÿ�����̶������δ�С
	inFile.open("./init.txt", ios::in);
	if (!inFile.is_open()) {
		cout << "Could not find the file\n" << endl;
		cout << "Program terminating\n" << endl;
		exit(EXIT_FAILURE);
	}
	else {
		cout << "Open file successfully" << endl;
	}
	//���н��̵ĳ�ʼ��  �м������̺�  ���̵Ķ���  ��ÿһ�εĴ�С  �κ�
	printf("***************��ʼ��*****************\n");
    int  processnum,segnum,seglength;
	inFile >> total;//�ڴ��ܴ�С

    //�������ڴ��С��Կ��з�������г�ʼ��
	//����˵����û�м�  �ڷ����ڴ溯�������ټ�
	Available avail;//���ñ�ṹ��
	avail.address = 0;
	avail.itemCode = 0;//�������ź���Ӧ����˵��������ͬ
	avail.length = total;//��ʼΪ�ڴ��ܳ�
	available.push_back(avail);//������з�����

	inFile >> processnum;//���̸���
	//����ÿ�����̶�Ҫ�õ����Ķ������δ�С
	for (int i = 0; i < processnum; i++) {
		Process process;
		inFile >> process.processName;//��ȡ���̵�����
		inFile >> segnum;//��ȡÿ�����̵Ķ���
		for (int j = 0; j < segnum; j++) {
			Segtable segtable;//�����еĶ�
			Request newReq;//�����
			segtable.segNum = j;//�κŸ�ֵ
			inFile >> seglength;//��õ�ǰ�εĳ���
			segtable.segLength = seglength;//����ǰ�ζγ����и�ֵ
			//process.segTable.push_back(segtable);
			//����ǰ�μ���ý��̵Ķα�
			process.segTable[j] = segtable;
			//��������ӵĽ��̵Ķζ������������
			newReq.length = seglength;//�γ�
			newReq.processName = process.processName;
			newReq.segnum = j;//�κ�
			request.push_back(newReq);//�������ṹ�����������
		}
		//processTable.push_back(process);
		processTable[process.processName] = process;

	}
	
}
//�������˵����
void printPartition() {
	printf("\n����\t��ַ\t����\t״̬\tռ�ý��̺�\t�κ�\n");
	Partition *iteration = head;
	while (iteration != nullptr) {
		string status = "";
		if (iteration->state == USEFUL) {
			status = "ռ��";
		}
		else {
			status = "����";
		}
		printf("%d\t%d\t%d\t%s\t%s\t\t%d\n", iteration->itemCode, iteration->address, iteration->length, status.c_str(), iteration->processName.c_str(),iteration->segnum);
		iteration = iteration->next;
	}
}
//�ϲ�������
void Mergeidle() {//û������Ӧ��
	temp = head;
	Partition *del;
	int itemCode = 0;
	while (temp) {
		if (temp->state == NOUSE && temp->next != nullptr&&temp->next->state == NOUSE) {
			if (temp->next->next != nullptr&&temp->next->next->state == NOUSE) {
				temp->length += temp->next->length + temp->next->next->length;
				temp->processName = " ";
				del = temp->next;
				temp->next = del->next;
				delete del;
				del = temp->next;
				temp->next = del->next;
				delete del;
			}
			else {
				temp->length += temp->next->length;
				temp->processName = " ";
				del = temp->next;
				temp->next = del->next;
				delete del;
			}
		}
		temp->itemCode = itemCode;
		itemCode++;
		temp = temp->next;
	}
}
//���¿��ñ�   vector����洢
void updateAvail() {
	temp = head;
	int availNum = 0;
	Available avail;
	available.clear();
	if (temp==nullptr) {
		avail.address = 0;
		avail.itemCode = 0;//�������ź���Ӧ����˵��������ͬ
		avail.length = total;//��ʼΪ�ڴ��ܳ�
		available.push_back(avail);//������з�����
	}
	else {
		while (temp) {
			if (temp->state == NOUSE) {

				avail.address = temp->address;
				avail.itemCode = temp->itemCode;
				avail.length = temp->length;
				available.push_back(avail);//
			}
			temp = temp->next;
		}
	}
}
//�ͷŷ���
void freePartition() {//OK
	string processName;
	//processName.resize(10);
	//scanf_s("%s", &processName[0]);
	printf("��������Ҫ�ͷŵĽ��̣�");
	cin >> processName; cout << endl;
	temp = head;
	while (temp) {//���������̣����жΣ����ó���
		if (temp->processName == processName) {
			//�ͷź�Ľ��̶��޸Ķα� 
			processTable[temp->processName].segTable[temp->segnum].startAdd = -1;
			processTable[temp->processName].segTable[temp->segnum].state = 0;//���
			processTable[temp->processName].segTable[temp->segnum].visitCount = 0;
			//���������
			Request newReq;
			newReq.length = temp->length;
			newReq.processName = temp->processName;
			newReq.segnum = temp->segnum;
			request.push_back(newReq);
			temp->state = NOUSE;//��Ҫ�������̵����ֵ����������Ϊ  ����
			temp->processName = " ";//����������Ϊ ��
			temp->segnum = -1;//�κ�����λ-1
			
		}
		temp = temp->next;
	}
	Mergeidle();//�ϲ�������
	//���¿��ñ�
	updateAvail();
}
//������Ӧ�㷨
void theFirst(string processName,int segnum) {
	Request allocatReq;//Ҫ������������
	int del = 0;//��סҪ����Ľ�����������е�λ��  ����Ҫ��
	for (int i = 0; i < request.size(); i++) {
		if (request[i].processName == processName && request[i].segnum == segnum) {
			allocatReq = request[i];
			del = i;
			break;
		}
	}
	bool flag = false;
	flag = firstmain(processName, allocatReq);
	if (flag == false) {
		compaction();
		flag = firstmain(processName, allocatReq);
		if (flag == false) {
			eliminate(allocatReq.length);
			flag = firstmain(processName, allocatReq);
		}
	}
	if (flag) {//������ɹ�  �����ҲҪ��    ������Ľ��̴��������ɾ�� ǰ���ס�˽�����������е�λ��
		//����ɹ��� �޸Ķα�
			//�ͷź�Ľ��̶��޸Ķα� 
		temp = head;
		while (temp) {
			if (temp->processName == processName && temp->segnum == segnum) {
				processTable[processName].segTable[segnum].startAdd = temp->address;//�����ʼ��ַ
				processTable[processName].segTable[segnum].state = 1;//�ڴ�
				processTable[processName].segTable[segnum].visitCount++;//���ʴ�����һ
				break;
			}
			temp = temp->next;
		}
		request.erase(request.begin() + del);
		printf("�ɹ����䣡\n");
	}
	else {//����ʧ�ܾͲ���Ҫ����
		printf("����ʧ�ܣ�\n");

	}
	return;
}
//�����Ӧ�㷨
void theBest(string processName,int segnum) {
	//�ȶԿ��ñ��������
	sort(available.begin(), available.end(), cmpAsc);
	//����������ҵ���Ӧ��  ���������Ϣ
	Request allocatReq;//Ҫ������������
	int del;//��סҪ����Ľ�����������е�λ��  ����Ҫ��
	for (int i = 0; i < request.size(); i++) {
		if (request[i].processName == processName && request[i].segnum == segnum) {
			allocatReq = request[i];
			del = i;
			break;
		}
	}
	bool flag = false;
	flag = bestmain(processName, allocatReq);
	//�����ǰ���б��еĿ�������С��С������ĶεĴ�С
	if (flag == false) {//���������
		compaction();//�ڴ��������  �Ƚ��н���
		flag = bestmain(processName, allocatReq); //����
		if (flag == false) {//�����������  ������̭
			eliminate(allocatReq.length);//��̭��С��  allocatReq.length �� Ҫ����γ��ȵ����ɸ���  ��̭�������ΪNOUSE
			flag = bestmain(processName, allocatReq);//��̭���������
		}
	}
	if (flag) {//������ɹ�  �����ҲҪ��    ������Ľ��̴��������ɾ�� ǰ���ס�˽�����������е�λ��
		temp = head;
		while (temp) {
			if (temp->processName == processName && temp->segnum == segnum) {
				processTable[processName].segTable[segnum].startAdd = temp->address;//�����ʼ��ַ
				processTable[processName].segTable[segnum].state = 1;//�ڴ�
				processTable[processName].segTable[segnum].visitCount++;//���ʴ�����һ
				break;
			}
			temp = temp->next;
		}
		request.erase(request.begin() + del);
		printf("�ɹ�����\n");
	}
	else {//����ʧ�ܾͲ���Ҫ����
		printf("����ʧ��\n");

	}
	return;
}
void createhead(string processName, Request allocatReq) {
	head = new Partition;
	tail = new Partition;
	head->address = 0;
	head->length = allocatReq.length;
	head->state = USEFUL;//��ռ��
	head->itemCode = 0;//����˵����������
	head->processName = processName;//��������̵�����
	head->segnum = allocatReq.segnum;

	head->next = tail;
	tail->address = allocatReq.length;//����˵����βָ��ĳ�ʼ��ַ
	tail->itemCode = 1;
	tail->length = available[0].length - tail->address;
	tail->segnum = -1;
	tail->state = NOUSE;

	tail->next = nullptr;
}
//������㷨
void worst(string processName, int segnum) {
	//�ȶԿ��ñ��������
	sort(available.begin(), available.end(), cmpDec);
	//����������ҵ���Ӧ��  ���������Ϣ
	Request allocatReq;//Ҫ�����������̵Ķ�
	int del;//��סҪ����Ľ�����������е�λ��  �����ɾ������Ҫ��
	for (int i = 0; i < request.size(); i++) {
		if (request[i].processName == processName && request[i].segnum == segnum) {
			allocatReq = request[i];//�������Ҫ����Ķε���Ϣ  ��ʵ���ã���
			del = i;//Ҫ����Ķ���������е�λ�ã�����
			break;
		}
	}
	bool flag;
	//ͨ�����ҿ��б������ö�
	flag = worstmain(processName, allocatReq);

	//�����ǰ���б��еĿ�������С��С������ĶεĴ�С
	if (flag == false) {//���������
		compaction();//�ڴ��������  �Ƚ��н���
		flag = worstmain(processName, allocatReq); //����
		if (flag == false) {//�����������  ������̭
			eliminate(allocatReq.length);//��̭��С��  allocatReq.length �� Ҫ����γ��ȵ����ɸ���  ��̭�������ΪNOUSE
			flag = worstmain(processName, allocatReq);//��̭���������
		}
	}
	if (flag) {//������ɹ�  �����ҲҪ��    ������Ľ��̴��������ɾ�� ǰ���ס�˽�����������е�λ��
		//����ɹ��� �޸Ķα�
			//�ͷź�Ľ��̶��޸Ķα� 
		temp = head;
		while (temp) {
			if(temp->processName == processName && temp->segnum == segnum){
		processTable[processName].segTable[segnum].startAdd =temp->address;//�����ʼ��ַ
		processTable[processName].segTable[segnum].state = 1;//�ڴ�
		processTable[processName].segTable[segnum].visitCount++;//���ʴ�����һ
		break;
           }
		temp=temp->next;
		}
		request.erase(request.begin() + del);
		printf("�ɹ����䣡\n");
	}
	else {//����ʧ�ܾͲ���Ҫ����
		printf("����ʧ�ܣ�\n");

	}
	return;
}
//ѡ���������Ľ��̵Ķν��з���
void allocation() {
	//�ȸ��¿��ñ�
	updateAvail();
	printf("���������\t����κ�\t������̳���\n");
	for (int i = 0; i < request.size(); i++) {
		printf("%s\t\t%d\t\t%d\n", request[i].processName.c_str(),request[i].segnum, request[i].length);
	}
	printf("��ѡ�������������");
	string processName;
	cin >> processName;//Ҫ����Ľ�������

	printf("��ѡ������κţ�");
	int segnum;
	cin >> segnum;//��������Ķκ�
	int nSelection;
	int num;

	char rubbish[1000] = { 0 };
	nSelection = -1;

			printf("\n1.������Ӧ�㷨\n");
			printf("2.�����Ӧ�㷨\n");
			printf("3.���Ӧ�㷨\n");
			printf("0.�˳�\n");
			printf("��ѡ��˵����ţ�");
			num = scanf_s("%d", &nSelection);
		
		switch (nSelection) {
		case 1: {
			theFirst(processName,segnum);
			break;
		}
		case 2: {
			theBest(processName,segnum);
			break;
		}
		case 3: {
			worst(processName, segnum);
			break;
		}
		case 0: {
			return;
		}
		default: {
			printf("����Ĳ˵���Ŵ���\n");

		}
		}
}
char c = 'a';
//�����������ӽ��̣�ÿ���ζ�Ҫ�ӣ�
void insert() {
	printf("***************��ӽ���*****************\n");
	//���̵����֡������������εĳ�
	string name;
	int segnum=0, segLength=0;

	printf("���������1��%d����Ҫ���ڴ�ռ䣺", segnum);//ÿ������Ҫ���ڴ�ռ�

	Process process;//Ҫ��ӵĽ���
	printf("���̵����֣�");
	cin >> process.processName;//��ȡ���̵�����
	printf("\n���̵Ķ�����");
	scanf_s("%d", &segnum);//��ȡÿ�����̵Ķ���
	for (int j = 0; j < segnum; j++) {
		Request newReq;//���������  �������װ���Ƕ�
		newReq.processName = process.processName;//������� �� �εĽ�����
		Segtable segtable;
		segtable.segNum = j;//�κŸ�ֵ
		printf("\n�����뵱ǰ�εĳ��ȣ�");
		scanf_s("%d", &segLength);//��õ�ǰ�εĳ���
		segtable.segLength = segLength;//����ǰ�ζγ����и�ֵ
		//process.segTable.push_back(segtable);
		process.segTable[j] = segtable;//����ǰ�μ���ý��̵Ķα�
	    //����ǰ��ӵĶμ����������
		newReq.length = segLength;//������еĶγ�
		newReq.segnum = j;//������еĶκ�
		request.push_back(newReq);//�������ṹ�����������
	}
	//processTable.push_back(process);
	processTable[process.processName] = process;
}
//�ڴ��������
void compaction() {
	temp = head;
	int count = 0,address,length;
	//address�ŵ���ǰһ���ڴ����˵�������ʼ��ַ
	//length�ŵ���ǰһ�������ĳ���
	int itemCode = 0;
	Partition *link=new Partition;
	while (temp) {
		Partition *ttemp;
		if (temp->state == NOUSE) {
			ttemp = temp;
			temp = temp->next;
			delete ttemp;//�����е��ͷŵ�  ������˵
		}
		else if (temp->state == USEFUL) {
			if (count == 0) {//����ǵ�һ�����˵�
				address = 0;
				temp->address = address;
				temp->itemCode = itemCode;//����˵������ ��   ���Ҫ���
				itemCode++;
				length = temp->length;
				count++;
				head = temp;//�������Ϊͷ���
				link = temp;
			}
			else {//������ǵ�һ��ռ�õ�
			address += length;
			temp->itemCode = itemCode;
			itemCode++;
			temp->address = address;//��ǰ��������ʼ��ַ����һ����ʼ��ַ���������ĳ���
			length = temp->length;//��һ����

			link->next = temp;//���޸ĺ����������   
			link = temp;
			}
			tail = temp;
			temp = temp->next;
		}
	}
	Partition *newNode=new Partition;//���������  ��ʣ��Ŀ��пռ���һ����Ŀ�����������
	newNode->address = address + length;
	newNode->itemCode = itemCode;
	newNode->itemCode = tail->itemCode + 1;
	newNode->length = total - newNode->address;
	newNode->next = nullptr;
	newNode->processName = " ";
	newNode->segnum = -1;
	newNode->state = NOUSE;
	tail->next = newNode;
	tail = newNode;
	//���¿��ñ�
	updateAvail();
}
bool cmpAsc(const Available& a1, const Available& a2) {//����
	return a1.length < a2.length;
}
bool cmpDec(const Available& a1, const Available& a2) {//����
	return a1.length > a2.length;
}
bool worstmain(string processName,Request allocatReq) {
	//�ȽϿ��ñ���Ҫ��������ڴ�Ĵ�С
	bool flag = false;
	int num = 0;
	for (int i = 0; i < available.size(); i++) {
		//������Էŵ��£���Ҫ���·���˵����Ϳ��ñ�
		if (allocatReq.length < available[i].length) {
			//����˵����Ҫ�ָ����н��
			temp = head;
			Partition *newNode = new Partition;
			if (temp == nullptr) {
				//ֱ�Ӵ���һ��ͷ�����������
				createhead(processName, allocatReq);
			}
			else {//������˵������Ϊ��
				while (temp) {
					temp->itemCode = num;
					if (temp->itemCode == available[i].itemCode) {
						temp->length = allocatReq.length;//�¼ӵĽ��������ڴ��С
						temp->processName = processName;//�½��̵�����
						temp->segnum = allocatReq.segnum;//���̵Ķκ�
						temp->state = USEFUL;//ռ��

						newNode->address = temp->address + temp->length;
						newNode->itemCode = temp->itemCode + 1;
						//num = newNode->itemCode;
						newNode->length = available[i].length - temp->length;//������Ӻ�  ��һ����ʣ�ڴ��С   ���ñ������-�½��̵Ĵ�С
						newNode->processName = " ";
						newNode->segnum = -1;//
						newNode->state = NOUSE;
						//���������
						newNode->next = temp->next;
						temp->next = newNode;
					}
					num++;
					temp = temp->next;
				}
			}
			//���¿��ñ���
			updateAvail();
			flag = true;
			break;
		}
		else if (allocatReq.length == available[i].length) {//�γ����õ��ڿ���������
			//����˵����Ҫ����Ӧ�����ݾ�����
			temp = head;
			//�������˵����Ϊ�� ͷ���Ϊ��
			if (temp == nullptr) {
				//ͷ���Ϊ��  ��������� Ҳ���ü��µĽ��
				createhead(processName, allocatReq);
			}
			else {
				while (temp) {
					if (temp->itemCode == available[i].itemCode) {

						temp->length = allocatReq.length;//�¼ӵĽ��̵Ķ������ڴ��С
						temp->processName = processName;//�½��̵�����
						temp->segnum = allocatReq.segnum;//���̵Ķκ�
						temp->state = USEFUL;//ռ��
						break;//�ҵ�����������ѭ��  ����Ҫ������

					}
					temp = temp->next;
				}
			}
			//���¿��ñ���
			updateAvail();//����˵�����Ѹ�   ѭ��һ�����½������ñ�
			flag = true;
			break;
		}
		else {//������������  ���̶��ڴ���ڿ��ñ�ǰ����ڴ�
			//���ڿ��ñ�Ӵ�С���� �����ǰ���������  ��û��Ҫ�������� 
			//����ֱ���˳�
			flag = false;
			break;
		}

	}
	return flag;
}
//��̭����
void eliminate(int size) {
	temp = head;
	int min;
	vector<string> processName;//��Ҫ����̭�εĽ����� ����
	vector<int> segnum;//��Ҫ����̭�εĶκ�  ����
	int elimSize=0;//��̭�ε��ܳ���
	for (int i = 0; i < available.size(); i++) {
		elimSize += available[i].length;
	}
	Partition *mintemp=new Partition;
	while (elimSize < size) {//�����̭�ε��ܳ���С��Ҫ����Ķεĳ���  
		//ѭ����̭
		temp = head;
		min = INT_MAX;
		while (temp) {//����һ�����ʴ�����С����̭
			auto segtable = processTable[temp->processName].segTable;//��øý��̵Ķα�
			if (segtable[temp->segnum].visitCount < min&&temp->state==USEFUL) {//��÷��ʴ�����С�ε���Ϣ   �����С���кü���  ���ֱȽ�����̭��һ��
				min = segtable[temp->segnum].visitCount;
				mintemp = temp;
			}
			temp = temp->next;
		}
		mintemp->state = NOUSE;
		processName.push_back(mintemp->processName);//��ÿ���ҳ�������С�ε������Ϣ���� ��̬����
		segnum.push_back(mintemp->segnum);
		elimSize += processTable[mintemp->processName].segTable[mintemp->segnum].segLength;
	}
	//������̭
	temp = head;
	int i = 0;
	while (temp) {
		if (temp->processName == processName[i]&&temp->segnum==segnum[i]) {
			//��̭Ҫ���¶α�
			processTable[temp->processName].segTable[temp->segnum].visitCount = 0;//Ҫ��̭�Ķ� ���ʴ�����0
			processTable[temp->processName].segTable[temp->segnum].startAdd = -1;
			processTable[temp->processName].segTable[temp->segnum].state = 0;//�����
			//��̭��Ķν����������
			Request newReq;
			newReq.processName = temp->processName;
			newReq.segnum = temp->segnum;
			newReq.length = temp->length;
			request.push_back(newReq);

			printf("\n��̭�ˣ�������Ϊ��%s�κ�Ϊ��%d�γ�Ϊ��%d\n\n", temp->processName.c_str(), temp->segnum,temp->length);
			temp->state = NOUSE;//���о������̭����
			temp->processName = " ";
			temp->segnum = -1;

			i++;
			if (i == processName.size()) {
				break;//����Ѿ���Ҫ��̭�Ķ���̭�˾���ǰ��ȥ
			}
		}
		temp = temp->next;
	}
	//��̭�����п��������н���
	compaction();//������������͸����˿��ñ�
}
void visitraise() {
	printf("\n����\t��ַ\t����\t״̬\tռ�ý��̺�\t�κ�\n");
	Partition *iteration = head;
	while (iteration != nullptr) {
		string status = "";
		if (iteration->state == USEFUL) {//ֻ�������ڴ��еĶεķ��ʴ���
			status = "ռ��";
		printf("%d\t%d\t%d\t%s\t%s\t\t%d\n", iteration->itemCode, iteration->address, iteration->length, status.c_str(), iteration->processName.c_str(),iteration->segnum);
		}
	
		iteration = iteration->next;
	}
	printf("��ѡ��Ҫ���ӷ��ʴ����Ľ��̵Ķ�\n");
	int segnum;
	string processName;
	printf("������ѡ��Ľ��̣�"); cin >> processName;
	printf("\n������ѡ��ĶΣ�"); cin >> segnum;
	Segtable *seg;
	seg=&processTable[processName].segTable[segnum];
	printf("�κ�\t��ʼ��ַ\t�γ�\t���ʴ���\n");
	printf("%d\t%d\t\t%d\t\t%d\n", seg->segNum, seg->startAdd, seg->segLength, seg->visitCount);
	//���ʴ�����1
	++(seg->visitCount);
	printf("%d\t%d\t\t%d\t\t%d\n", seg->segNum, seg->startAdd, seg->segLength, seg->visitCount);

}
bool bestmain(string processName, Request allocatReq) {
	//�ȽϿ��ñ���Ҫ��������ڴ�Ĵ�С
	bool flag = false;
	int num = 0;
	for (int i = 0; i < available.size(); i++) {
		//������Էŵ��£���Ҫ���·���˵����Ϳ��ñ�
		if (allocatReq.length < available[i].length) {
			//����˵����Ҫ�ָ����н��
			temp = head;
			Partition *newNode = new Partition;
			if (temp == nullptr) {
				createhead(processName, allocatReq);
			}
			else {
				while (temp) {
					temp->itemCode = num;
					if (temp->itemCode == available[i].itemCode) {

						temp->length = allocatReq.length;//�¼ӵĽ��������ڴ��С
						temp->processName = processName;//�½��̵�����
						temp->segnum = allocatReq.segnum;
						temp->state = USEFUL;//ռ��

						newNode->address = temp->address + temp->length;
						newNode->itemCode = temp->itemCode + 1;
						//num = newNode->itemCode;
						newNode->length = available[i].length - temp->length;//������Ӻ�  ��һ����ʣ�ڴ��С   ���ñ������-�½��̵Ĵ�С
						newNode->processName = " ";
						newNode->segnum = -1;
						newNode->state = NOUSE;
						//���������
						newNode->next = temp->next;
						temp->next = newNode;

					}
					temp = temp->next;
					num++;

				}
			}
			//���¿��ñ���
			updateAvail();
			flag = true;
			break;
		}
		else if (allocatReq.length == available[i].length) {
			//����˵����Ҫ����Ӧ�����ݾ�����
			temp = head;
			if (temp == nullptr) {
				//ͷ���Ϊ��  ��������� Ҳ���ü��µĽ��
				createhead(processName, allocatReq);
			}
			else {
				while (temp) {
					if (temp->itemCode == available[i].itemCode) {
						temp->segnum = allocatReq.segnum;
						temp->length = allocatReq.length;//�¼ӵĽ��������ڴ��С
						temp->processName = processName;//�½��̵�����
						temp->state = USEFUL;//ռ��
						break;//�ҵ�����������ѭ��  ����Ҫ������

					}
					temp = temp->next;
				}
			}
			//���¿��ñ���
			updateAvail();//����˵�����Ѹ�   ѭ��һ�����½������ñ�
			flag = true;
			break;
		}

	}
	return flag;
}
bool firstmain(string processName,Request allocatReq) {
	bool flag = false;
	int num = 0;
	for (int i = 0; i < available.size(); i++) {
		//������Էŵ��£���Ҫ���·���˵����Ϳ��ñ�
		if (allocatReq.length < available[i].length) {
			//����˵����Ҫ�ָ����н��
			temp = head;
			Partition *newNode = new Partition;
			if (temp == nullptr) {
				//ֱ�Ӵ���һ��ͷ�����������
				createhead(processName, allocatReq);
			}
			else {
				while (temp) {
					temp->itemCode = num;

					if (temp->itemCode == available[i].itemCode) {

						temp->length = allocatReq.length;//�¼ӵĽ��������ڴ��С
						temp->segnum = allocatReq.segnum;
						temp->processName = processName;//�½��̵�����
						temp->state = USEFUL;//ռ��

						newNode->address = temp->address + temp->length;
						newNode->itemCode = temp->itemCode + 1;
						//num = newNode->itemCode;
						newNode->length = available[i].length - temp->length;//������Ӻ�  ��һ����ʣ�ڴ��С   ���ñ������-�½��̵Ĵ�С
						newNode->processName = " ";
						newNode->segnum = -1;
						newNode->state = NOUSE;
						//���������
						newNode->next = temp->next;
						temp->next = newNode;
						

					}
					num++;
					temp = temp->next;
				}
			}
			//���¿��ñ���
			updateAvail();
			flag = true;
			break;
		}
		else if (allocatReq.length == available[i].length) {
			//����˵����Ҫ����Ӧ�����ݾ�����
			temp = head;
			//�������˵����Ϊ�� ͷ���Ϊ��
			if (temp == nullptr) {
				//ͷ���Ϊ��  ��������� Ҳ���ü��µĽ��
				createhead(processName, allocatReq);
			}
			else {
				while (temp) {
					if (temp->itemCode == available[i].itemCode) {

						temp->length = allocatReq.length;//�¼ӵĽ��������ڴ��С
						temp->processName = processName;//�½��̵�����
						temp->segnum = allocatReq.segnum;
						temp->state = USEFUL;//ռ��
						break;//�ҵ�����������ѭ��  ����Ҫ������

					}
					temp = temp->next;
				}
			}
			//���¿��ñ���
			updateAvail();//����˵�����Ѹ�   ѭ��һ�����½������ñ�
			flag = true;
			break;
		}

	}
	return flag;
}
////printf("***************��ʼ��*****************\n");
//int total, processNum, length;
//printf("�������ڴ��ܴ�С��");
//scanf_s("%d", &total);
//printf("\n");
//printf("��������̸�����");
//scanf_s("%d", &processNum);
//printf("\n");
//printf("���������1��%d����Ҫ���ڴ�ռ䣺", processNum);
//Partition *newNode;
//for (int i = 0; i < processNum; i++) {
//	scanf_s("%d", &length);
//	//�����һ���ڴ�С���ܵ��ڴ�ͼ���   ���ʱ��û�п��ñ�
//	if (i == 0 && length < total) {
//		head = new Partition;
//		tail = new Partition;
//		head->address = 0;//���ǵ�һ�����̽����ڴ���ʼ��ַ��Ϊ0
//		head->length = length;//ռ���ڴ泤��
//		head->state = USEFUL;//�ѱ�ռ��
//		head->itemCode = i;//��������
//		//����Ľ�������ô�㣿��  ���֣�
//		head->processName = to_string(i + 1);
//		head->next = tail;
//		temp = head;
//		tail->address = length;//
//		tail->itemCode = i + 1;
//		tail->length = total - tail->address;
//		tail->state = NOUSE;
//		//����Ľ�����
//
//		tail->next = nullptr;
//	}
//	else if (length < tail->length) {//���ڶ����Ժ�Ľ��̴�СС�ڽ��������ڴ�ʱ���
//		newNode = new Partition;
//		newNode->address = tail->address;
//		newNode->itemCode = tail->itemCode;
//		newNode->length = length;
//		newNode->state = USEFUL;
//		//����Ľ�����
//		newNode->processName = to_string(tail->itemCode + 1);
//		newNode->next = tail;
//		temp->next = newNode;
//		temp = newNode;
//		//����ֵ������ʣ�࣬���ٱ��һ������
//		//û�о����Ϊ��
//		if (total - (temp->address + length) > 0) {
//
//			tail->address = temp->address + length;
//			tail->itemCode = temp->itemCode + 1;
//			tail->length = total - tail->address;
//			tail->state = NOUSE;
//			//����Ľ�����
//			tail->processName = " ";
//			tail->next = nullptr;
//		}
//		else {
//			tail = nullptr;
//
//		}
//	}
//	else {//���ʣ���ڴ�С�ڽ�����Ҫ�ڴ� �Ͱѽ��̷��������  �Ժ��ٴ���
//		request[reqNum].length = length;
//		request[reqNum].processName = to_string(i + 1);
//		reqNum++;
//	}
//}