#include "add.h"

namespace fs = boost::filesystem;

void add(std::vector<std::string> arguments)
{
    // FI a git repo is created
    if (gitUtils::isValidGitFolder())
    {
        // Check if optional parameter --help was given
        bool help = false;
        for (auto it = arguments.begin(); it != arguments.end();)
        {
            // if the optional parameter is found, make the help flag to true
            // and remove the optional parameter from the list
            if (*it == "--help" || *it =="-h")
            {
                arguments.erase(it);
                help = true;
            }
            else
                it++;
        }

        // If user asked for help, just print the help
        if (help)
            showAddHelp();
        else
        {
            // If there is no argument given, we cannot proceed -> so print help
            if (arguments.size() < 1)
            {
                std::cout << "Invalid number of arguments" << std::endl;
                showAddHelp();
            }
            // if everything is okay, add each given file to the repo
            else
            {
                for (auto file : arguments)
                {
                    if (fs::exists(file))
                    {
                        fs::path pathToFile(file);
                        addFileToGit(pathToFile);
                    }
                    // If the file doesn't exist, print tips and help
                    else
                    {
                        std::cout << "File " << file << " doesn't exist" << std::endl;
                    }
                }
            }
        }
    }
}

void showAddHelp()
{
    std::cout << "usage: gitus add <pathspec>" << std::endl;
}

bool addFileToGit(fs::path pathToFile)
{
    if (gitUtils::isValidGitFolder())
    {
        std::ifstream ifs(pathToFile.string());
        std::string fileContent((std::istreambuf_iterator<char>(ifs)), (std::istreambuf_iterator<char>()));

        if (gitUtils::createObjectFile(fileContent))
        {
            if (gitUtils::addFileToIndex(pathToFile))
            {
                std::cout << "The file " << pathToFile << " has been added to the Gitus repository" << std::endl;
                return true;
            }
        }
    }
    
    return false;
}