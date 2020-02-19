#include <iostream>
#include <filesystem>

#include "build/compile.h"
#include "build/link.h"
#include "build/clean.h"

void printHelp()
{
    std::cout << "usage:\t buildus [ <path-to-config-file> | clean ]" << std::endl;
}

int main(int argc, char * argv[])
{
    std::string option;
    if (argv[1] == nullptr)
    {
        std::cout << "Please provide the path to a BuildUS configuration file." << std::endl;
        printHelp();
        return -1;
    }

    option = std::string(argv[1]);

    // If the option is clean
    if (option == "clean")
    {
        clean();
    }
    // If it's a config file
    else
    {
        // Check if it exists
        if (!std::filesystem::exists(option))
        {
            std::cout << "The configuration file " << option << " does not exist." << std::endl;
            printHelp();
            return -1;
        }

        Config config(option);
        compileFiles(config);
        linkFiles(config);
    }

    return 0;
}