// ConsoleApplication1.cpp : Definiert den Einstiegspunkt für die Konsolenanwendung.

#pragma comment(lib, "LogitechLcd.lib")

#include "stdafx.h"
#include "LogitechLcd.h"
#include <iostream>
#include <fstream>

#define LOGITEC_DISPLAY_FOLDER_ROOT "LOGITEC_DISPLAY_FOLDER_ROOT"
#define LOGITEC_DISPLAY_FOLDER_ROOT_DEFAULT "C:/temp/logitec"

using namespace std;

char* getRootPath() {
	char* pPath;
	size_t len;
	errno_t err = _dupenv_s(&pPath, &len, LOGITEC_DISPLAY_FOLDER_ROOT );
	if (pPath == NULL) {
		pPath = *&LOGITEC_DISPLAY_FOLDER_ROOT_DEFAULT;
		printf ("Environemnt: %s not set, using default root directory: %s\n", LOGITEC_DISPLAY_FOLDER_ROOT, pPath);
	}
	return pPath;
}

void removeFile(_finddata_t c_file) {
	// printf ("Removing file: %s\n", c_file.name);
	remove(c_file.name);

}


void sendContentToLogitec(char* line0, char* line1, char* line2, char* line3) {
	// printf("Zeile[0]: %s \n", line0);
	// printf("Zeile[1]: %s \n", line1);
	// printf("Zeile[2]: %s \n", line2);
	// printf("Zeile[3]: %s \n", line3);

	wchar_t  ws[100];
	swprintf(ws, 100, L"%hs", line0);
	LogiLcdMonoSetText(0, ws);

    swprintf(ws, 100, L"%hs", line1);
	LogiLcdMonoSetText(1, ws);
		
	swprintf(ws, 100, L"%hs", line2);
	LogiLcdMonoSetText(2, ws);
	
	swprintf(ws, 100, L"%hs", line3);
	LogiLcdMonoSetText(3, ws);

	LogiLcdUpdate();
}


void displayFileContent(_finddata_t c_file) {
    int idx = 0;
	fstream f;
	char lArray[4][1024];

	// initialize the array so we have at least in each string a "".
	for (int i = 0; i < 4; i++) {
		 strcpy_s(lArray[i], "");
	}

	printf ("Going to read file: %s\n", c_file.name);
    f.open(c_file.name, ios::in);
    while (!f.eof() && idx <= 3 ) {
    	f.getline(lArray[idx], 1024);    	
    	idx++;
    }
    f.close();

	sendContentToLogitec(lArray[0], lArray[1], lArray[2], lArray[3]);
}

long handleFiles() {

	long hFile;
	struct _finddata_t c_file;

	_chdir("inbound");
	 // Would be nice to read the oldest file first, so we would have some kind of FIFO queue!
	if ((hFile = _findfirst("*.message", &c_file)) == -1L) {
		// printf ("No Files found!\n");
		
	} else {		
		printf ("Found file: %s\n", c_file.name);
		// wait a short time to be sure the file has been written
		Sleep(100);
		displayFileContent(c_file);
		removeFile(c_file);		
	}
	_findclose(hFile);
	_chdir("..");

	// we could read the dispaly time from the file name, by now we just display a message 
	// at least 5 seconds. This is ok for the first approach!
	return 5;
}

void writeOutbound(char* button) {
  ofstream myfile;
  myfile.open (button);
  myfile << "pressed";
  myfile.close();
}

void waitBeforeDisplayNextFile(int seconds) {
  Sleep(seconds *  1000);
}

void checkButtonsBeforeDisplayNextFile(int seconds) {

	boolean checkButtons = true;

	int i = 0;
	while (checkButtons) {
	  LogiLcdUpdate();
	  if (LogiLcdIsButtonPressed(LOGI_LCD_MONO_BUTTON_0)) {
		 printf("Button 0 pressed ...\n");
		 writeOutbound("outbound/b0.pressed");
	  }
	  if (LogiLcdIsButtonPressed(LOGI_LCD_MONO_BUTTON_1)) {
		printf("Button 1 pressed ...\n");
		writeOutbound("outbound/b1.pressed");
	  }
	  if (LogiLcdIsButtonPressed(LOGI_LCD_MONO_BUTTON_2)) {
		printf("Button 2 pressed ...\n");
		writeOutbound("outbound/b2.pressed");
	  }
	  if (LogiLcdIsButtonPressed(LOGI_LCD_MONO_BUTTON_3)) {
		printf("Button 3 pressed ...\n");
		writeOutbound("outbound/b3.pressed");
	  }

	  // if not reached the max time to wait, we stay in the loop.
	  if (i * 100 < seconds * 1000) {
		  Sleep(100);
		  i++;
	  } else {
		  // printf("leaving loop to chec buttons!");
		  checkButtons = false;
	  }
	}
}

int _tmain(int argc, _TCHAR* argv[]) {

	wchar_t * text = L"NCA-LogitecHub";
	LogiLcdInit(text, LOGI_LCD_TYPE_MONO);

	printf("Running ConsoleApplication1 ...\n");
	char* pPath = getRootPath();
	if (pPath != NULL) {
	  printf ("Current path to look up files is: %s\n", pPath);

	   _chdir(pPath);
	   while(true) {
		   long displayTime = handleFiles();
		   checkButtonsBeforeDisplayNextFile(displayTime);
	   }
	} else {
	  printf ("%s is not set, aborting programm!", LOGITEC_DISPLAY_FOLDER_ROOT);
	}
	return 0;
}