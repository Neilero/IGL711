#include <iostream>
#include <sstream>
#include <iomanip>

#include <string>
#include <boost/filesystem.hpp>
#include <boost/functional/hash.hpp>
#include <boost/compute/detail/sha1.hpp>
#include <boost/uuid/uuid.hpp>
#include <sstream>

namespace fs = boost::filesystem;

namespace gitUtils 
{
    /**
     * Function which hash a string and return a SHA1 string
     */
    std::string hashFile(std::string stringToHash);

    /**
     * Function which hash the content file and return a SHA1 string
     */
    std::string hashFile(const fs::path& path);

    /**
     * Return true if the current working directory contains a ".git" folder
     * @return true if the current working directory contains a ".git" folder, false otherwise
     */
    bool isValidGitFolder();

    /**
     * Function which create a file object given its string content
     */
    bool createObjectFile(std::string fileContent);

    /**
     * Function which add a file to the index file
     */
    bool addFileToIndex(fs::path chemin);
}