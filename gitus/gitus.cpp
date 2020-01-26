#include <iostream>
#include <boost/filesystem.hpp>
#include <string>

#include "git/init.h"
#include "git/add.h"
#include "git/commit.h"

void help()
{
    std::cout << "usage: gitus <command> [<args>]" << std::endl << std::endl;
    
    std::cout << "These are common gitus commands used in various situations:" << std::endl;
    std::cout << "init" << "\t" << "Create an empty Git repository or reinitialize an existing one" << std::endl;
    std::cout << "add" << "\t" << "Add file contents to the index" << std::endl;
    std::cout << "commit" << "\t" << "Record changes to the repository" << std::endl;
}

int main(int argc, char * argv[])
{
    // If there is no command given, initialize command to a null string
    std::string command;
    if (argv[1] != nullptr)
        command = std::string(argv[1]);
    else
        command = std::string("");

    // If the command is Init
    if (command == "init")
        init(argv[2]);

    // If the command is Add, parse arguments in a vector
    else if (command == "add")
    {
        std::vector<std::string> args;
        for (int i = 2; i<argc; i++)
            args.push_back(argv[i]);
            
        add(args);
    }

    // If the command is Commit, parse arguments in a vector
    else if (command == "commit")
    {
        std::vector<std::string> args;
        for (int i = 2; i<argc; i++)
            args.push_back(argv[i]);

        commit(args);
    }

    // If command not recognized, print help
    else
        help();

    return 0;
}