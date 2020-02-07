#include<iostream>
#include<algorithm>
#include<string>
#include"Partition.h"
#include<vector>
#include<fstream>

using namespace std;
extern vector<Available> available;//空闲分区表，可用表
extern vector<Request> request;//请求表
extern Partition * head, *tail, *temp;
extern int reqNum;
extern int total;
extern unordered_map<string,Process> processTable;//进程表  当前有多少进程 有可能在内存或不在
//初始化分区说明表
void initPartition() {
	ifstream inFile;//打开文件进行初始化  
	//初始化内容 内存总大小，进程个数，每个进程段数、段大小
	inFile.open("./init.txt", ios::in);
	if (!inFile.is_open()) {
		cout << "Could not find the file\n" << endl;
		cout << "Program terminating\n" << endl;
		exit(EXIT_FAILURE);
	}
	else {
		cout << "Open file successfully" << endl;
	}
	//进行进程的初始化  有几个进程和  进程的段数  和每一段的大小  段号
	printf("***************初始化*****************\n");
    int  processnum,segnum,seglength;
	inFile >> total;//内存总大小

    //读入总内存大小后对空闲分区表进行初始化
	//分区说明表还没有加  在分配内存函数里面再加
	Available avail;//可用表结构体
	avail.address = 0;
	avail.itemCode = 0;//空闲区号和相应分区说明表项相同
	avail.length = total;//初始为内存总长
	available.push_back(avail);//加入空闲分区表

	inFile >> processnum;//进程个数
	//对于每个进程都要得到它的段数、段大小
	for (int i = 0; i < processnum; i++) {
		Process process;
		inFile >> process.processName;//获取进程的名字
		inFile >> segnum;//读取每个进程的段数
		for (int j = 0; j < segnum; j++) {
			Segtable segtable;//进程中的段
			Request newReq;//请求表
			segtable.segNum = j;//段号赋值
			inFile >> seglength;//获得当前段的长度
			segtable.segLength = seglength;//给当前段段长进行赋值
			//process.segTable.push_back(segtable);
			//将当前段加入该进程的段表
			process.segTable[j] = segtable;
			//将现在添加的进程的段都加入请求表中
			newReq.length = seglength;//段长
			newReq.processName = process.processName;
			newReq.segnum = j;//段号
			request.push_back(newReq);//将请求表结构加入请求表中
		}
		//processTable.push_back(process);
		processTable[process.processName] = process;

	}
	
}
//输出分区说明表
void printPartition() {
	printf("\n区号\t首址\t长度\t状态\t占用进程号\t段号\n");
	Partition *iteration = head;
	while (iteration != nullptr) {
		string status = "";
		if (iteration->state == USEFUL) {
			status = "占用";
		}
		else {
			status = "空闲";
		}
		printf("%d\t%d\t%d\t%s\t%s\t\t%d\n", iteration->itemCode, iteration->address, iteration->length, status.c_str(), iteration->processName.c_str(),iteration->segnum);
		iteration = iteration->next;
	}
}
//合并空闲区
void Mergeidle() {//没问题了应该
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
//更新可用表   vector数组存储
void updateAvail() {
	temp = head;
	int availNum = 0;
	Available avail;
	available.clear();
	if (temp==nullptr) {
		avail.address = 0;
		avail.itemCode = 0;//空闲区号和相应分区说明表项相同
		avail.length = total;//初始为内存总长
		available.push_back(avail);//加入空闲分区表
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
//释放分区
void freePartition() {//OK
	string processName;
	//processName.resize(10);
	//scanf_s("%s", &processName[0]);
	printf("请输入想要释放的进程：");
	cin >> processName; cout << endl;
	temp = head;
	while (temp) {//不连续进程（所有段）都得撤销
		if (temp->processName == processName) {
			//释放后的进程段修改段表 
			processTable[temp->processName].segTable[temp->segnum].startAdd = -1;
			processTable[temp->processName].segTable[temp->segnum].state = 0;//外存
			processTable[temp->processName].segTable[temp->segnum].visitCount = 0;
			//加入请求表
			Request newReq;
			newReq.length = temp->length;
			newReq.processName = temp->processName;
			newReq.segnum = temp->segnum;
			request.push_back(newReq);
			temp->state = NOUSE;//将要撤销进程的名字的物理块设置为  空闲
			temp->processName = " ";//进程名设置为 空
			temp->segnum = -1;//段号设置位-1
			
		}
		temp = temp->next;
	}
	Mergeidle();//合并空闲区
	//更新可用表
	updateAvail();
}
//最先适应算法
void theFirst(string processName,int segnum) {
	Request allocatReq;//要分配的请求进程
	int del = 0;//记住要分配的进程在请求表中的位置  后面要用
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
	if (flag) {//若分配成功  请求表也要改    将分配的进程从请求表中删除 前面记住了进程在请求表中的位置
		//分配成功后 修改段表
			//释放后的进程段修改段表 
		temp = head;
		while (temp) {
			if (temp->processName == processName && temp->segnum == segnum) {
				processTable[processName].segTable[segnum].startAdd = temp->address;//物理初始地址
				processTable[processName].segTable[segnum].state = 1;//内存
				processTable[processName].segTable[segnum].visitCount++;//访问次数加一
				break;
			}
			temp = temp->next;
		}
		request.erase(request.begin() + del);
		printf("成功分配！\n");
	}
	else {//分配失败就不需要改了
		printf("分配失败！\n");

	}
	return;
}
//最佳适应算法
void theBest(string processName,int segnum) {
	//先对可用表进行排序
	sort(available.begin(), available.end(), cmpAsc);
	//从请求表中找到相应的  进程相关信息
	Request allocatReq;//要分配的请求进程
	int del;//记住要分配的进程在请求表中的位置  后面要用
	for (int i = 0; i < request.size(); i++) {
		if (request[i].processName == processName && request[i].segnum == segnum) {
			allocatReq = request[i];
			del = i;
			break;
		}
	}
	bool flag = false;
	flag = bestmain(processName, allocatReq);
	//如果当前空闲表中的空闲区大小都小于请求的段的大小
	if (flag == false) {//如果不够大
		compaction();//内存紧缩技术  先进行紧缩
		flag = bestmain(processName, allocatReq); //分配
		if (flag == false) {//如果还不够大  进行淘汰
			eliminate(allocatReq.length);//淘汰不小于  allocatReq.length 即 要分配段长度的若干个段  淘汰后的设置为NOUSE
			flag = bestmain(processName, allocatReq);//淘汰后继续分配
		}
	}
	if (flag) {//若分配成功  请求表也要改    将分配的进程从请求表中删除 前面记住了进程在请求表中的位置
		temp = head;
		while (temp) {
			if (temp->processName == processName && temp->segnum == segnum) {
				processTable[processName].segTable[segnum].startAdd = temp->address;//物理初始地址
				processTable[processName].segTable[segnum].state = 1;//内存
				processTable[processName].segTable[segnum].visitCount++;//访问次数加一
				break;
			}
			temp = temp->next;
		}
		request.erase(request.begin() + del);
		printf("成功分配\n");
	}
	else {//分配失败就不需要改了
		printf("分配失败\n");

	}
	return;
}
void createhead(string processName, Request allocatReq) {
	head = new Partition;
	tail = new Partition;
	head->address = 0;
	head->length = allocatReq.length;
	head->state = USEFUL;//被占用
	head->itemCode = 0;//分区说明表项区号
	head->processName = processName;//被分配进程的名字
	head->segnum = allocatReq.segnum;

	head->next = tail;
	tail->address = allocatReq.length;//分区说明表尾指针的初始地址
	tail->itemCode = 1;
	tail->length = available[0].length - tail->address;
	tail->segnum = -1;
	tail->state = NOUSE;

	tail->next = nullptr;
}
//最坏分配算法
void worst(string processName, int segnum) {
	//先对可用表进行排序
	sort(available.begin(), available.end(), cmpDec);
	//从请求表中找到相应的  进程相关信息
	Request allocatReq;//要分配的请求进程的段
	int del;//记住要分配的进程在请求表中的位置  分配后删除请求要用
	for (int i = 0; i < request.size(); i++) {
		if (request[i].processName == processName && request[i].segnum == segnum) {
			allocatReq = request[i];//保存这个要分配的段的信息  其实不用？？
			del = i;//要分配的段在请求表中的位置？？？
			break;
		}
	}
	bool flag;
	//通过查找空闲表来放置段
	flag = worstmain(processName, allocatReq);

	//如果当前空闲表中的空闲区大小都小于请求的段的大小
	if (flag == false) {//如果不够大
		compaction();//内存紧缩技术  先进行紧缩
		flag = worstmain(processName, allocatReq); //分配
		if (flag == false) {//如果还不够大  进行淘汰
			eliminate(allocatReq.length);//淘汰不小于  allocatReq.length 即 要分配段长度的若干个段  淘汰后的设置为NOUSE
			flag = worstmain(processName, allocatReq);//淘汰后继续分配
		}
	}
	if (flag) {//若分配成功  请求表也要改    将分配的进程从请求表中删除 前面记住了进程在请求表中的位置
		//分配成功后 修改段表
			//释放后的进程段修改段表 
		temp = head;
		while (temp) {
			if(temp->processName == processName && temp->segnum == segnum){
		processTable[processName].segTable[segnum].startAdd =temp->address;//物理初始地址
		processTable[processName].segTable[segnum].state = 1;//内存
		processTable[processName].segTable[segnum].visitCount++;//访问次数加一
		break;
           }
		temp=temp->next;
		}
		request.erase(request.begin() + del);
		printf("成功分配！\n");
	}
	else {//分配失败就不需要改了
		printf("分配失败！\n");

	}
	return;
}
//选择请求表里的进程的段进行分配
void allocation() {
	//先更新可用表
	updateAvail();
	printf("请求进程名\t请求段号\t请求进程长度\n");
	for (int i = 0; i < request.size(); i++) {
		printf("%s\t\t%d\t\t%d\n", request[i].processName.c_str(),request[i].segnum, request[i].length);
	}
	printf("请选择请求进程名：");
	string processName;
	cin >> processName;//要分配的进程名称

	printf("请选择请求段号：");
	int segnum;
	cin >> segnum;//输入请求的段号
	int nSelection;
	int num;

	char rubbish[1000] = { 0 };
	nSelection = -1;

			printf("\n1.最先适应算法\n");
			printf("2.最佳适应算法\n");
			printf("3.最坏适应算法\n");
			printf("0.退出\n");
			printf("请选择菜单项编号：");
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
			printf("输入的菜单编号错误！\n");

		}
		}
}
char c = 'a';
//向请求表中添加进程（每个段都要加）
void insert() {
	printf("***************添加进程*****************\n");
	//进程的名字、段数、各个段的长
	string name;
	int segnum=0, segLength=0;

	printf("请输入进程1到%d所需要的内存空间：", segnum);//每个段需要的内存空间

	Process process;//要添加的进程
	printf("进程的名字：");
	cin >> process.processName;//获取进程的名字
	printf("\n进程的段数：");
	scanf_s("%d", &segnum);//读取每个进程的段数
	for (int j = 0; j < segnum; j++) {
		Request newReq;//请求表还有用  请求表中装的是段
		newReq.processName = process.processName;//请求表中 的 段的进程名
		Segtable segtable;
		segtable.segNum = j;//段号赋值
		printf("\n请输入当前段的长度：");
		scanf_s("%d", &segLength);//获得当前段的长度
		segtable.segLength = segLength;//给当前段段长进行赋值
		//process.segTable.push_back(segtable);
		process.segTable[j] = segtable;//将当前段加入该进程的段表
	    //将当前添加的段加入请求表中
		newReq.length = segLength;//请求表中的段长
		newReq.segnum = j;//请求表中的段号
		request.push_back(newReq);//将请求表结构加入请求表中
	}
	//processTable.push_back(process);
	processTable[process.processName] = process;
}
//内存紧缩技术
void compaction() {
	temp = head;
	int count = 0,address,length;
	//address放的是前一个内存分区说明表的起始地址
	//length放的是前一个分区的长度
	int itemCode = 0;
	Partition *link=new Partition;
	while (temp) {
		Partition *ttemp;
		if (temp->state == NOUSE) {
			ttemp = temp;
			temp = temp->next;
			delete ttemp;//将空闲的释放掉  后面再说
		}
		else if (temp->state == USEFUL) {
			if (count == 0) {//如果是第一个用了的
				address = 0;
				temp->address = address;
				temp->itemCode = itemCode;//分区说明表项 号   这个要变的
				itemCode++;
				length = temp->length;
				count++;
				head = temp;//将这个变为头结点
				link = temp;
			}
			else {//如果不是第一个占用的
			address += length;
			temp->itemCode = itemCode;
			itemCode++;
			temp->address = address;//当前分区的起始地址是上一个起始地址加它分区的长度
			length = temp->length;//下一个用

			link->next = temp;//把修改后的链接起来   
			link = temp;
			}
			tail = temp;
			temp = temp->next;
		}
	}
	Partition *newNode=new Partition;//上面紧缩后  将剩余的空闲空间搞成一个大的空闲区链接上
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
	//更新可用表
	updateAvail();
}
bool cmpAsc(const Available& a1, const Available& a2) {//升序
	return a1.length < a2.length;
}
bool cmpDec(const Available& a1, const Available& a2) {//降序
	return a1.length > a2.length;
}
bool worstmain(string processName,Request allocatReq) {
	//比较可用表与要分配进程内存的大小
	bool flag = false;
	int num = 0;
	for (int i = 0; i < available.size(); i++) {
		//如果可以放得下，就要更新分区说明表和可用表
		if (allocatReq.length < available[i].length) {
			//分区说明表要分割已有结点
			temp = head;
			Partition *newNode = new Partition;
			if (temp == nullptr) {
				//直接创建一个头结点放入就行了
				createhead(processName, allocatReq);
			}
			else {//若分区说明链表不为空
				while (temp) {
					temp->itemCode = num;
					if (temp->itemCode == available[i].itemCode) {
						temp->length = allocatReq.length;//新加的进程所需内存大小
						temp->processName = processName;//新进程的名称
						temp->segnum = allocatReq.segnum;//进程的段号
						temp->state = USEFUL;//占用

						newNode->address = temp->address + temp->length;
						newNode->itemCode = temp->itemCode + 1;
						//num = newNode->itemCode;
						newNode->length = available[i].length - temp->length;//进程添加后  这一块所剩内存大小   可用表相关项-新进程的大小
						newNode->processName = " ";
						newNode->segnum = -1;//
						newNode->state = NOUSE;
						//添加链表结点
						newNode->next = temp->next;
						temp->next = newNode;
					}
					num++;
					temp = temp->next;
				}
			}
			//更新可用表即可
			updateAvail();
			flag = true;
			break;
		}
		else if (allocatReq.length == available[i].length) {//段长正好等于空闲区长度
			//分区说明表要改相应的内容就行了
			temp = head;
			//如果分区说明表为空 头结点为空
			if (temp == nullptr) {
				//头结点为空  分配进行了 也不用加新的结点
				createhead(processName, allocatReq);
			}
			else {
				while (temp) {
					if (temp->itemCode == available[i].itemCode) {

						temp->length = allocatReq.length;//新加的进程的段所需内存大小
						temp->processName = processName;//新进程的名称
						temp->segnum = allocatReq.segnum;//进程的段号
						temp->state = USEFUL;//占用
						break;//找到即跳出链表循环  不需要再找了

					}
					temp = temp->next;
				}
			}
			//更新可用表即可
			updateAvail();//分区说明表已改   循环一遍重新建立可用表
			flag = true;
			break;
		}
		else {//如果到这里就是  进程段内存大于可用表当前项的内存
			//由于可用表从大到小排列 如果当前项都不能满足  就没必要往后走了 
			//所以直接退出
			flag = false;
			break;
		}

	}
	return flag;
}
//淘汰函数
void eliminate(int size) {
	temp = head;
	int min;
	vector<string> processName;//将要被淘汰段的进程名 数组
	vector<int> segnum;//将要被淘汰段的段号  数组
	int elimSize=0;//淘汰段的总长度
	for (int i = 0; i < available.size(); i++) {
		elimSize += available[i].length;
	}
	Partition *mintemp=new Partition;
	while (elimSize < size) {//如果淘汰段的总长度小于要调入的段的长度  
		//循环淘汰
		temp = head;
		min = INT_MAX;
		while (temp) {//挑出一个访问次数最小的淘汰
			auto segtable = processTable[temp->processName].segTable;//获得该进程的段表
			if (segtable[temp->segnum].visitCount < min&&temp->state==USEFUL) {//获得访问次数最小段的信息   如果最小的有好几个  这种比较下淘汰第一个
				min = segtable[temp->segnum].visitCount;
				mintemp = temp;
			}
			temp = temp->next;
		}
		mintemp->state = NOUSE;
		processName.push_back(mintemp->processName);//将每次找出来的最小段的相关信息存入 动态数组
		segnum.push_back(mintemp->segnum);
		elimSize += processTable[mintemp->processName].segTable[mintemp->segnum].segLength;
	}
	//进行淘汰
	temp = head;
	int i = 0;
	while (temp) {
		if (temp->processName == processName[i]&&temp->segnum==segnum[i]) {
			//淘汰要更新段表
			processTable[temp->processName].segTable[temp->segnum].visitCount = 0;//要淘汰的段 访问次数清0
			processTable[temp->processName].segTable[temp->segnum].startAdd = -1;
			processTable[temp->processName].segTable[temp->segnum].state = 0;//在外存
			//淘汰后的段进入请求表中
			Request newReq;
			newReq.processName = temp->processName;
			newReq.segnum = temp->segnum;
			newReq.length = temp->length;
			request.push_back(newReq);

			printf("\n淘汰了：进程名为：%s段号为：%d段长为：%d\n\n", temp->processName.c_str(), temp->segnum,temp->length);
			temp->state = NOUSE;//进行具体的淘汰操作
			temp->processName = " ";
			temp->segnum = -1;

			i++;
			if (i == processName.size()) {
				break;//如果已经把要淘汰的都淘汰了就提前出去
			}
		}
		temp = temp->next;
	}
	//淘汰完了有空闲区进行紧缩
	compaction();//紧缩完了里面就更新了可用表
}
void visitraise() {
	printf("\n区号\t首址\t长度\t状态\t占用进程号\t段号\n");
	Partition *iteration = head;
	while (iteration != nullptr) {
		string status = "";
		if (iteration->state == USEFUL) {//只增加在内存中的段的访问次数
			status = "占用";
		printf("%d\t%d\t%d\t%s\t%s\t\t%d\n", iteration->itemCode, iteration->address, iteration->length, status.c_str(), iteration->processName.c_str(),iteration->segnum);
		}
	
		iteration = iteration->next;
	}
	printf("请选择要增加访问次数的进程的段\n");
	int segnum;
	string processName;
	printf("请输入选择的进程："); cin >> processName;
	printf("\n请输入选择的段："); cin >> segnum;
	Segtable *seg;
	seg=&processTable[processName].segTable[segnum];
	printf("段号\t开始地址\t段长\t访问次数\n");
	printf("%d\t%d\t\t%d\t\t%d\n", seg->segNum, seg->startAdd, seg->segLength, seg->visitCount);
	//访问次数加1
	++(seg->visitCount);
	printf("%d\t%d\t\t%d\t\t%d\n", seg->segNum, seg->startAdd, seg->segLength, seg->visitCount);

}
bool bestmain(string processName, Request allocatReq) {
	//比较可用表与要分配进程内存的大小
	bool flag = false;
	int num = 0;
	for (int i = 0; i < available.size(); i++) {
		//如果可以放得下，就要更新分区说明表和可用表
		if (allocatReq.length < available[i].length) {
			//分区说明表要分割已有结点
			temp = head;
			Partition *newNode = new Partition;
			if (temp == nullptr) {
				createhead(processName, allocatReq);
			}
			else {
				while (temp) {
					temp->itemCode = num;
					if (temp->itemCode == available[i].itemCode) {

						temp->length = allocatReq.length;//新加的进程所需内存大小
						temp->processName = processName;//新进程的名称
						temp->segnum = allocatReq.segnum;
						temp->state = USEFUL;//占用

						newNode->address = temp->address + temp->length;
						newNode->itemCode = temp->itemCode + 1;
						//num = newNode->itemCode;
						newNode->length = available[i].length - temp->length;//进程添加后  这一块所剩内存大小   可用表相关项-新进程的大小
						newNode->processName = " ";
						newNode->segnum = -1;
						newNode->state = NOUSE;
						//添加链表结点
						newNode->next = temp->next;
						temp->next = newNode;

					}
					temp = temp->next;
					num++;

				}
			}
			//更新可用表即可
			updateAvail();
			flag = true;
			break;
		}
		else if (allocatReq.length == available[i].length) {
			//分区说明表要改相应的内容就行了
			temp = head;
			if (temp == nullptr) {
				//头结点为空  分配进行了 也不用加新的结点
				createhead(processName, allocatReq);
			}
			else {
				while (temp) {
					if (temp->itemCode == available[i].itemCode) {
						temp->segnum = allocatReq.segnum;
						temp->length = allocatReq.length;//新加的进程所需内存大小
						temp->processName = processName;//新进程的名称
						temp->state = USEFUL;//占用
						break;//找到即跳出链表循环  不需要再找了

					}
					temp = temp->next;
				}
			}
			//更新可用表即可
			updateAvail();//分区说明表已改   循环一遍重新建立可用表
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
		//如果可以放得下，就要更新分区说明表和可用表
		if (allocatReq.length < available[i].length) {
			//分区说明表要分割已有结点
			temp = head;
			Partition *newNode = new Partition;
			if (temp == nullptr) {
				//直接创建一个头结点放入就行了
				createhead(processName, allocatReq);
			}
			else {
				while (temp) {
					temp->itemCode = num;

					if (temp->itemCode == available[i].itemCode) {

						temp->length = allocatReq.length;//新加的进程所需内存大小
						temp->segnum = allocatReq.segnum;
						temp->processName = processName;//新进程的名称
						temp->state = USEFUL;//占用

						newNode->address = temp->address + temp->length;
						newNode->itemCode = temp->itemCode + 1;
						//num = newNode->itemCode;
						newNode->length = available[i].length - temp->length;//进程添加后  这一块所剩内存大小   可用表相关项-新进程的大小
						newNode->processName = " ";
						newNode->segnum = -1;
						newNode->state = NOUSE;
						//添加链表结点
						newNode->next = temp->next;
						temp->next = newNode;
						

					}
					num++;
					temp = temp->next;
				}
			}
			//更新可用表即可
			updateAvail();
			flag = true;
			break;
		}
		else if (allocatReq.length == available[i].length) {
			//分区说明表要改相应的内容就行了
			temp = head;
			//如果分区说明表为空 头结点为空
			if (temp == nullptr) {
				//头结点为空  分配进行了 也不用加新的结点
				createhead(processName, allocatReq);
			}
			else {
				while (temp) {
					if (temp->itemCode == available[i].itemCode) {

						temp->length = allocatReq.length;//新加的进程所需内存大小
						temp->processName = processName;//新进程的名称
						temp->segnum = allocatReq.segnum;
						temp->state = USEFUL;//占用
						break;//找到即跳出链表循环  不需要再找了

					}
					temp = temp->next;
				}
			}
			//更新可用表即可
			updateAvail();//分区说明表已改   循环一遍重新建立可用表
			flag = true;
			break;
		}

	}
	return flag;
}
////printf("***************初始化*****************\n");
//int total, processNum, length;
//printf("请输入内存总大小：");
//scanf_s("%d", &total);
//printf("\n");
//printf("请输入进程个数：");
//scanf_s("%d", &processNum);
//printf("\n");
//printf("请输入进程1到%d所需要的内存空间：", processNum);
//Partition *newNode;
//for (int i = 0; i < processNum; i++) {
//	scanf_s("%d", &length);
//	//如果第一个内存小于总的内存就加入   这个时候还没有可用表
//	if (i == 0 && length < total) {
//		head = new Partition;
//		tail = new Partition;
//		head->address = 0;//这是第一个进程进入内存起始地址设为0
//		head->length = length;//占用内存长度
//		head->state = USEFUL;//已被占用
//		head->itemCode = i;//表项区号
//		//表项的进程名怎么搞？？  数字？
//		head->processName = to_string(i + 1);
//		head->next = tail;
//		temp = head;
//		tail->address = length;//
//		tail->itemCode = i + 1;
//		tail->length = total - tail->address;
//		tail->state = NOUSE;
//		//表项的进程名
//
//		tail->next = nullptr;
//	}
//	else if (length < tail->length) {//当第二个以后的进程大小小于接下来的内存时添加
//		newNode = new Partition;
//		newNode->address = tail->address;
//		newNode->itemCode = tail->itemCode;
//		newNode->length = length;
//		newNode->state = USEFUL;
//		//表项的进程名
//		newNode->processName = to_string(tail->itemCode + 1);
//		newNode->next = tail;
//		temp->next = newNode;
//		temp = newNode;
//		//如果分到最后还有剩余，就再变成一个分区
//		//没有就最后为空
//		if (total - (temp->address + length) > 0) {
//
//			tail->address = temp->address + length;
//			tail->itemCode = temp->itemCode + 1;
//			tail->length = total - tail->address;
//			tail->state = NOUSE;
//			//表项的进程名
//			tail->processName = " ";
//			tail->next = nullptr;
//		}
//		else {
//			tail = nullptr;
//
//		}
//	}
//	else {//如果剩余内存小于进程需要内存 就把进程放入请求表  以后再处理
//		request[reqNum].length = length;
//		request[reqNum].processName = to_string(i + 1);
//		reqNum++;
//	}
//}