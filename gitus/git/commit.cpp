#include "commit.h"

void commit(std::vector<std::string> arguments)
{
    // If a git repo is created
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
            showCommitHelp();
        else
        {
            // If there is not 3 arguments given, we cannot proceed -> so print help
            if (arguments.size() != 3)
            {
                std::cout << "Invalid number of arguments" << std::endl;
                showCommitHelp();
            }
            // if everything is okay, make commit with the given arguments
            else
            {
                makeCommit(arguments[0], arguments[1], arguments[2]);
            }
        }

    }
}

void showCommitHelp()
{
    std::cout << "usage: gitus commit <msg> <author> <email>" << std::endl;
}

bool makeCommit(std::string message, std::string author, std::string email)
{
    std::ostringstream commitContent;
    boost::uuids::random_generator gen;
    boost::uuids::uuid id = gen();

    commitContent << id << id << std::endl;
    commitContent << message << std::endl;
    commitContent << author << std::endl;
    commitContent << email << std::endl;

    gitUtils::createObjectFile(commitContent.str());
}

