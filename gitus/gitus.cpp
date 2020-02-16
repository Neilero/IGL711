/* AB - note
    les tests sont supers mais difficile à lire
    4 tests échouent
    a quoi sert le 0 dans le index...il est toujours là - NRC - négotiable

    4xCNS   -4
    1xDNG   -5
    2xNRC   -10
    branche -10

    qualité des test 20/25

    Total : 66/100
*/


/* AB - pour ce fichier
    3xCNS
*/
#include <iostream>
#include <boost/filesystem.hpp>
#include <string>

#include "git/init.h"
#include "git/add.h"
#include "git/commit.h"
#include "git/ObjectsTree.h"

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

        //TODO: remove debug
    if (command == "test") { // AB - ?
        gitUtils::ObjectsTree tree(boost::filesystem::current_path());

        tree.addObject(boost::filesystem::current_path() / "git" / "MakeFile");
        tree.addObject(boost::filesystem::current_path() / "git" / "cmake_install.cmake");

        tree.writeTree();

        return 0;
    }

    // If the command is Init
    if (command == "init") // AB - constante? CNS
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
    else // c'est préférable d'avoir des accolades tout le temps
        help();

    return 0;
}