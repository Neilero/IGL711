#include "gitUtils.h"

namespace fs = boost::filesystem;

namespace gitUtils 
{
    std::string hashFile(const fs::path &path) {
        std::ifstream ifs(path.string());
        std::string fileContent((std::istreambuf_iterator<char>(ifs)), (std::istreambuf_iterator<char>()));

        return gitUtils::hashFile(fileContent);
    }

    std::string hashFile(std::string stringToHash) {
        boost::compute::detail::sha1 s(stringToHash);
        return s;
    }

    bool isValidGitFolder() {
        fs::path potentialGitDirectory = fs::current_path() / ".git";

        return fs::exists(potentialGitDirectory);
    }

    bool createObjectFile(std::string fileContent)
    {
        if (!isValidGitFolder())
            return false;
            
        const char* pathObjects = ".git/objects/";
        
        std::string hashFile = gitUtils::hashFile(std::string(fileContent));
        std::string folderStringHash = hashFile.substr(0, 2);

        boost::filesystem::path dir(fs::current_path() / (pathObjects+folderStringHash));

        boost::filesystem::create_directory(dir);

        // If the file doesn't already exist, create it
        if (!fs::exists(dir / hashFile.substr(2)))
        {
            std::ofstream outfile ((dir / hashFile.substr(2)).string());
            outfile << fileContent << std::endl;
            outfile.close();
        }

        return true;
    }

    /**
     * Function which add a file to the index folder
     */
    bool addFileToIndex(fs::path pathToFile)
    {
        if (!isValidGitFolder())
            return false;

        std::string stringPathToFile = pathToFile.string();
        std::string hash = hashFile(pathToFile);

        bool alreadyAddedFlag = false;

        std::ifstream inFile(".git/index");
        std::vector<std::string> lines;
        std::string line;
        while (getline(inFile, line))
        {
            if (line.rfind(stringPathToFile, 0) == 0)
            {
                lines.push_back(stringPathToFile+'\t'+hash);
                alreadyAddedFlag = true;
            }
            else 
                lines.push_back(line);
        }

        if (!alreadyAddedFlag)
            lines.push_back(stringPathToFile+'\t'+hash);

        std::ofstream outFile(".git/index");
        for(int i =0; i<lines.size() ;i++)
        {
            outFile << lines[i] << std::endl;
        }

        return true;
    }
}