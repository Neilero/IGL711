#include "add.h"

using namespace std;

void showAddHelp()
{
    std::cout << "usage: gitus add <pathspec>" << std::endl;
}

void add()
{
    cout << "Vous avez lancé un add" << endl;
    showAddHelp();
}