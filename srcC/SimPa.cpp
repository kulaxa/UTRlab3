#include <iostream>
#include <fstream>
#include <string>
#include <cstring>

using namespace std;
int main()
{

    string in;
    cin >> in;
    string *input;
    int i=0;
    for(auto c: in){
        string temp="";
         i=0;
        if(c=='\0') break;
        if(c == ','){
            i++;
            cout<<"preskacem ,"<<endl;
        }
        else{
            temp.append(to_string(c));
            input[i]=temp;
        }
    }

    for(int j=0; j <i; j++){
        cout << input[j];
    }
    return 0;
}