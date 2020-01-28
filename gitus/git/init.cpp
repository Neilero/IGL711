#include "init.h"

namespace fs = boost::filesystem;

// fct init
    /* La commande init devra initialiser un dépôt git dans le dossier courant c.-à-d. le dossier à partir
    duquel gitus est invoqué. Notez qu'il n'est demandé que de mettre en place les structures
    participantes activement à la gestion des sources. Toutes autres structures peuvent donc être
    absentes suite à l'exécution de cette commande.
    Afin d'avoir une certaine simplicité dans ce travail, il n'y a seulement que le répertoire .git, le
    répertoire .git/objects et le fichier .git/index qui seront utilisés. */

bool init(const char * arg)
{
    if (arg != nullptr) // test argument presence
    {   
        auto argument = std::string(arg);

        if (argument == "-h" || argument == "--help")
            showInitHelp();
        else    // unknown argument
        {   
            showInitHelp(argument);
            return false;
        }

        return true; //stop initialisation
    }

    const auto rootFolder = fs::current_path() / ".git";
    const auto dataFolder = rootFolder / "objects";
    const auto indexFile = rootFolder / "index";
    const auto headFile = rootFolder / "HEAD";

    fs::create_directories(dataFolder); // create all directories, if they already exist, then do nothing

    if (!fs::exists(indexFile)) {
        std::ofstream file(indexFile.string());
        file << "0" << std::endl;
    }

    if (!fs::exists(headFile)) {
        std::ofstream file(headFile.string());
    }

    std::cout << "Initialized Git repository in " << fs::current_path() << std::endl;
    return true;
}

void showInitHelp(const std::string& unknownArg) {
    if (!unknownArg.empty())
        std::cout << "Unknown argument: " << unknownArg << std::endl;

    std::cout << "usage: gitus init" << std::endl;
}
