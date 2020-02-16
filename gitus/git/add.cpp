// AB - sommaire
//  1xDNG

#include "add.h"

namespace fs = boost::filesystem;

bool add(std::vector<std::string> arguments)
{
    // If a git repo is created
    if (!gitUtils::isValidGitFolder())
    {
        std::cout << "The current directory is not a valid Gitus repository" << std::endl;
        return false;
    }

    // Check if optional parameter --help has been given
    // AB - sa aurait été plus simple de juste vérifier si le 0e param est le help?

    bool helpFlag = false;
    for (auto iterator = arguments.begin(); iterator != arguments.end();) 
    {
        // if the optional parameter is found, make the help flag to true
        // and remove the optional parameter from the list
        if (*iterator == "--help" || *iterator =="-h") // constantes? // 
        {
            arguments.erase(iterator);
            helpFlag = true;
        }
        else
            iterator++;
    }

    // If user asked for help, just print the help
    if (helpFlag)
    {
        showAddHelp();
        return true;
    }

    // If there is no argument given, we cannot proceed -> so print help
    if (arguments.size() < 1)
    {
        std::cout << "Invalid number of arguments" << std::endl;
        showAddHelp();
        return false;
    }
    // if everything is okay, add each given file to the repo if it exists
    for (auto file : arguments)
    {
        if (fs::exists(file)) // AB - danger, exception potentielle - DNG
        {
            fs::path pathToFile(file);
            if (addFileToGit(pathToFile))
                std::cout << "The file " << pathToFile << " has been added to the Gitus repository" << std::endl;
        }
        // If the file doesn't exist, print tips
        else
        {
            std::cout << "The file \"" << file << "\" doesn't exist" << std::endl;
        }
    }

    return true;
}

bool addFileToGit(fs::path pathToFile)
{
    std::ifstream ifs(pathToFile.string());
    std::string fileContent((std::istreambuf_iterator<char>(ifs)), (std::istreambuf_iterator<char>()));

    if (!gitUtils::createObjectFile(fileContent, "blob"))
        return false;

    if (!gitUtils::addFileToIndex(pathToFile))
        return false;

    return true;
}

void showAddHelp()
{
    std::cout << "usage: gitus add <pathspec>" << std::endl;
}