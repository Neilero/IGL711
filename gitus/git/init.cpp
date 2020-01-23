#include "init.h"

namespace fs = boost::filesystem;

// fct init
    /* La commande init devra initialiser un dépôt git dans le dossier courant c.-à-d. le dossier à partir
    duquel gitus est invoqué. Notez qu'il n'est demandé que de mettre en place les structures
    participantes activement à la gestion des sources. Toutes autres structures peuvent donc être
    absentes suite à l'exécution de cette commande.
    Afin d'avoir une certaine simplicité dans ce travail, il n'y a seulement que le répertoire .git, le
    répertoire .git/objects et le fichier .git/index qui seront utilisés. */

void init(char * argv[])
{
//    TODO : create getArguments helper and test "-h" / "--help" arguments

    const auto rootFolder = fs::current_path() / ".git";
    const auto dataFolder = rootFolder / "objects";
    const auto indexFile = rootFolder / "index";
    const auto headFile = rootFolder / "HEAD";

    fs::create_directories(dataFolder); // create all directories, if they already exist, then do nothing

    if (!fs::exists(indexFile)) {
        std::ofstream file(indexFile.string());
    }

    if (!fs::exists(headFile)) {
        std::ofstream file(headFile.string());
    }

    std::cout << "Initialized Git repository in " << fs::current_path() << std::endl;
}