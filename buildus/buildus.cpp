#include <iostream>
#include <filesystem>

#include "build/utils.h"
#include "build/build.h"

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
        std::cout << "C'est un clean" << std::endl;
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

        Config configuration(option);
        build(configuration);
    }

    return 0;
}