#include <iostream>

#include <dummy.h>
#include <string>

using namespace std;

void init()
{
    std::cout << "Vous avez lancé un init" << std::endl;
}

void add()
{
    std::cout << "Vous avez lancé un add" << std::endl;
}

void commit()
{
    std::cout << "Vous avez lancé un commit" << std::endl;
}

void help()
{
    std::cout << "usage: gitus <command> [<args>]" << std::endl << std::endl;
    
    std::cout << "These are common gitus commands used in various situations:" << std::endl;
    std::cout << "init" << "\t" << "Create an empty Git repository or reinitialize an existing one" << std::endl;
    std::cout << "add" << "\t" << "Add file contents to the index" << std::endl;
    std::cout << "commit" << "\t" << "Record changes to the repository" << std::endl;
}

int main(int argc, char *argv[])
{
    string commande(argv[1]);

	if (commande.compare("init") == 0)
        init();
    else if (commande.compare("add") == 0)
        add();
    else if (commande.compare("commit") == 0)
        commit();
    else
        help();

    return 0;
}
