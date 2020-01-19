#include <iostream>

#include <boost/filesystem.hpp>
#include <string>
#include "git/init.h"
#include "git/add.h"
#include "git/commit.h"

using namespace std;
namespace fs = boost::filesystem;

void help()
{
    cout << "usage: gitus <command> [<args>]" << endl << endl;
    
    cout << "These are common gitus commands used in various situations:" << endl;
    cout << "init" << "\t" << "Create an empty Git repository or reinitialize an existing one" << endl;
    cout << "add" << "\t" << "Add file contents to the index" << endl;
    cout << "commit" << "\t" << "Record changes to the repository" << endl;
}

int main(int argc, char *argv[])
{
    string command;
    if (argv[1] != nullptr)
        command = string(argv[1]);
    else
        command = string("");

    if (command == "init")
        init();
    else if (command == "add")
        add(argv);
    else if (command == "commit")
        commit();
    else
        help();

    return 0;
}
