#include <iostream>
#include "utils.cpp"

void printHelp()
{
    std::cout << "usage:\t buildus [ <pathToConfigFile> | clean ]" << std::endl;
}

int main(int argc, char * argv[])
{
    std::string option;
    if (argv[1] == nullptr)
    {
        printHelp();
        return -1;
    }

    option = std::string(argv[1]);

    // If the option is clean
    if (option == "clean")
    {
        std::cout << "C'est un clean" << std::endl;
    }
    // if it's a config file
    else if (Utils::GetExtension(option) == ".buildus")
    {
        // Check if it exists
        if (!boost::filesystem::exists(option))
        {
            std::cout << "The configuration file " << option << " does not exist." << std::endl;
            printHelp();
            return -1;
        }

        
        
    }
    else
    {
        std::cout << "Please provide the path to a .buildus configuration file." << std::endl;
        printHelp();
    }

    return 0;
}