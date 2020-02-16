// AB - sommaire
//  1xCNS
//  1xNRC

#include "commit.h"

bool commit(std::vector<std::string> arguments)
{
    // If a git repo is created
    if (!gitUtils::isValidGitFolder())
    {
        std::cout << "The current directory is not a valid Gitus repository" << std::endl;
        return false;
    }

    // Check if optional parameter --help was given
    bool helpFlag = false;
    for (auto iterator = arguments.begin(); iterator != arguments.end();)
    {
        // if the optional parameter is found, make the help flag to true
        // and remove the optional parameter from the list
        if (*iterator == "--help" || *iterator =="-h")
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
        showCommitHelp();
        return true;
    }

    // If there is not 3 arguments given, we cannot proceed -> so print help
    if (arguments.size() != 3)
    {
        std::cout << "Invalid number of arguments" << std::endl;
        showCommitHelp();
        return false;
    }
    
    // if everything is okay, make commit with the given arguments
    return makeCommit(arguments[0], arguments[1], arguments[2]);
}

bool makeCommit(std::string message, std::string author, std::string email)
{
    // Generate UUID for the commit
    boost::uuids::random_generator gen; // ?
    boost::uuids::uuid id = gen();

    // AB - ce n'est pas la facon de faire
    //  NRC
    std::ostringstream commitContent;
    commitContent << id << std::endl;

    // AB - constante ".git/index" - CNS
    std::ifstream readFile(".git/index");
    std::string line;
    std::getline(readFile, line);

    // If there is a parent commit, add its SHA1
    if (line != "0")
        commitContent << "parent " << line << std::endl;

    gitUtils::ObjectsTree tree(boost::filesystem::current_path());
    int numberFiles = 0;
    while (std::getline(readFile, line))
    {
        if (line != "")
        {
            tree.addObject(boost::filesystem::current_path() / line.substr(0, line.find('\t')));
            numberFiles++;
        }
    }

    // If there is no staged file in the index, stop the commit process
    if (numberFiles == 0)
    {
        std::cout << "There is no staged files to commit" << std::endl;
        return false;
    }

    commitContent << "tree " << tree.writeTree() << std::endl;

    commitContent << '\''<< author << "\' ";
    commitContent << '\''<< email << "\' ";
    commitContent << time(0) << std::endl;

    commitContent << '\''<< message << "\'" << std::endl;

    // If an error occured, return false
    if (!gitUtils::createObjectFile(commitContent.str(), "commit"))
        return false;

    // AB - constante ".git/index"
    std::ofstream newIndex(".git/index", std::ofstream::trunc);
    newIndex << gitUtils::getSha1FromContent(commitContent.str(), "commit") << std::endl;

    std::cout << "Commit " << id << " created" << std::endl;
    return true;
}

void showCommitHelp()
{
    std::cout << "usage: gitus commit <msg> <author> <email>" << std::endl;
}

