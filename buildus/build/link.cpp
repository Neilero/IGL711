#include "link.h"

using namespace std;

int linkFiles(const Config& configuration) 
{
    
    if(!(filesystem::exists(filesystem::current_path()/Utils::temporaryFolder)))
    {
        throw std::string("Intermediate folder doesn't exist.");
    }


    string linkCmd = createLinkCommand(configuration);
    int returnCode = system(linkCmd.c_str());
    return returnCode;
}

string createLinkCommand(const Config& configuration) //g++ f1.o f2.o f3.o -o app
{
    string libraryString;

    //Parcourir le yaml pour récupérer les fichiers 
    for(auto & libraryVar : configuration.getDepsLibraryVar())
    {
        char * pathVar = getenv(libraryVar.c_str());
        
        if (pathVar == nullptr){
            return "-1";
        }            

        libraryString.append(" -L" + string(pathVar));
    }
    for(const auto & libraryVar : configuration.getDepsLibraryLibs())
    {
        libraryString.append(" -l" + libraryVar);
    }
    //Créer commande "g++ "
    string linkCmd = "g++";

    std::string path = (filesystem::current_path()/Utils::temporaryFolder).string() + "/";
    //Pour chaque fichier
    for(const auto & file : configuration.getPackage()){
        //ajouter nomFichier + extension .o à la commande
        linkCmd.append(" \"" + path + file);
        linkCmd.append(".o\"");
    }

    //Ajouter " -o " à la commande
    linkCmd.append(" -o");

    //Ajouter nomApplication à la commande    
    linkCmd.append(" \"" + path + configuration.getProjet()+"\"");

    linkCmd.append(libraryString);

    return linkCmd;
}