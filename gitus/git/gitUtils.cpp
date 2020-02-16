// AB - somnmaire
//  1xCNS
//  1xQLT   

#include "gitUtils.h"

namespace fs = boost::filesystem;

namespace gitUtils 
{
    std::string hashFile(std::string stringToHash) {
        boost::compute::detail::sha1 s(stringToHash);
        return s;
    }

    bool isValidGitFolder() {
        fs::path potentialGitDirectory = fs::current_path() / ".git";

        return fs::exists(potentialGitDirectory);
    }

    bool createObjectFile(std::string content, std::string prefix)
    {
        // If a git repo is created
        if (!isValidGitFolder())
        {
            std::cout << "The current directory is not a valid Gitus repository" << std::endl;
            return false;
        }
            
        const char* pathObjects = ".git/objects/";

        // generate the header and the SHA1
        std::string objectContent(prefix+" "+std::to_string((int) content.length())+'\0'+content);
        std::string hashFile = gitUtils::hashFile(objectContent);

        std::string folderStringHash = hashFile.substr(0, 2);
        boost::filesystem::path dir(fs::current_path() / (pathObjects+folderStringHash));
        boost::filesystem::create_directory(dir);

        // If the file doesn't already exist, create it
        if (!fs::exists(dir / hashFile.substr(2))) // AB la variable folderStringHash existe - QLT
        {
            std::ofstream outfile ((dir / hashFile.substr(2)).string());

            // If it's a blob, then we have to compress the content
            if (prefix == "blob")
            {
                boost::iostreams::filtering_streambuf<boost::iostreams::input> in;
                in.push(boost::iostreams::zlib_compressor());
                in.push(boost::make_iterator_range(objectContent));
                std::string compressed;
                boost::iostreams::copy(in, boost::iostreams::back_inserter(compressed));

                objectContent = compressed;
            }

            outfile << objectContent;
        }

        return true;
    }

    std::string getSha1FromFile(const fs::path &path)
    {
        std::ifstream ifs(path.string());
        std::string content((std::istreambuf_iterator<char>(ifs)), (std::istreambuf_iterator<char>()));

        // good!
        return gitUtils::hashFile(std::string("blob "+std::to_string((int) content.length())+'\0'+content));
    }

    std::string getSha1FromContent(std::string content, std::string prefix)
    {
        return gitUtils::hashFile(std::string(prefix+" "+std::to_string((int) content.length())+'\0'+content));
    }

    bool addFileToIndex(fs::path pathToFile)
    {
       // If a git repo is created
        if (!isValidGitFolder())
        {
            std::cout << "The current directory is not a valid Gitus repository" << std::endl;
            return false;
        }

        std::string stringPathToFile = pathToFile.string();
        std::string hash = getSha1FromFile(pathToFile);

        bool alreadyAddedFlag = false;

        std::ifstream inFile(".git/index"); // AB - constante? CNS
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