#include "add.h"

namespace fs = boost::filesystem;

void add(char * argv[])
{
    //TODO Test if a git repo is created
    if (gitUtils::isValidGitFolder())
    {
        // Check if an argument has been given, otherwise initialize as "-h" to print help
        std::string argument;
        if (argv[2] != nullptr)
            argument = std::string(argv[2]);
        else
            argument = std::string("-h");

        // If an argument is given
        if (argument.rfind("-", 0) == 0)
        {
            std::string argumentOption = argument.substr(1);

            if ((argumentOption.compare("-help") == 0) || (argumentOption.compare("h") == 0))
                showAddHelp();
            else
                std::cout << "Invalid argument " << argumentOption << std::endl;
        }
        // If the file exists, we call the function which make the changes in .git/index and .git/objects
        else if (fs::exists(argument))
        {
            fs::path pathToFile(argument);
            addFileToGit(pathToFile);
        }
        // If the file doesn't exist, print tips and help
        else
        {
            std::cout << "File doesn't exist" << std::endl;
            std::cout << "Make sure you wrote the correct path to the file" << std::endl;
            showAddHelp();
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
            std::cout << "The file " << pathToFile << " has been added to the Gitus repository" << std::endl;
    }
}