#include "commit.h"

bool commit(std::vector<std::string> arguments)
{
    // If a git repo is created
    if (!gitUtils::isValidGitFolder())
        return false;

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
    boost::uuids::random_generator gen;
    boost::uuids::uuid id = gen();

    std::ostringstream commitContent;
    commitContent << id << id << std::endl;
    commitContent << message << std::endl;
    commitContent << author << std::endl;
    commitContent << email << std::endl;

    // If an error occured, return false
    if (!gitUtils::createObjectFile(commitContent.str(), "commit"))
        return false;

    std::cout << "Commit " << id << " created" << std::endl;
    return true;
}

void showCommitHelp()
{
    std::cout << "usage: gitus commit <msg> <author> <email>" << std::endl;
}

