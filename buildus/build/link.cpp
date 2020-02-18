#include "link.h"

using namespace std;

int linkFiles(const Config& configuration) //g++ f1.o f2.o f3.o -o app
{
    string libraryString;
    //Parcourir le yaml pour récupérer les fichiers 
    for(auto & libraryVar : configuration.getDepsLibraryVar())
    {
        char * pathVar = std::getenv(libraryVar.c_str());
        
        if (pathVar == nullptr)
            return -1;

        libraryString.append(" -L "+std::string(pathVar));
    }
    for(const auto & libraryVar : configuration.getDepsLibraryLibs())
    {
        libraryString.append(" -l " + libraryVar);
    }
    //Créer commande "g++ "
    string linkCmd = "g++ ";
    //Pour chaque fichier
    for(const auto & file : configuration.getPackage()){
        //ajouter nomFichier + extension .o à la commande
        linkCmd.append(file);
        linkCmd.append(".o ");
    }
    //Ajouter " -o " à la commande
    linkCmd.append(" -o ");

    //Ajouter nomApplication à la commande    
    linkCmd.append(configuration.getProjet());

    linkCmd.append(libraryString);

    cout<<"linkCmd: "<<linkCmd<<endl;

    // Si pb => Exception ou return erreur
    return system(linkCmd.c_str());
}