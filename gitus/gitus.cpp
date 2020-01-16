#include <iostream>

#include <dummy.h>
#include <string>

using namespace std;

void init()
{
    cout << "Vous avez lancé un init" << endl;
}

void add()
{
    cout << "Vous avez lancé un add" << endl;
}

void commit()
{
    cout << "Vous avez lancé un commit" << endl;
}

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
    string commande;
    if (argv[1] != NULL)
        string commande(argv[1]);
    else
        string commande("");

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
