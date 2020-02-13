#include <iostream>
#include "utils.cpp"
#include "build.cpp"

void printHelp()
{
    std::cout << "usage:\t buildus [ <pathToConfigFile> | clean ]" << std::endl;
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
        if (!boost::filesystem::exists(option))
        {
            std::cout << "The configuration file " << option << " does not exist." << std::endl;
            printHelp();
            return -1;
        }

        Config configuration;
        build(configuration);
    }

    return 0;
}