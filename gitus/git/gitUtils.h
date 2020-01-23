#include <iostream>

#include <string>
#include <boost/filesystem.hpp>
#include <boost/functional/hash.hpp>
#include <boost/uuid/detail/sha1.hpp>
#include <boost/uuid/uuid.hpp>
#include <sstream>

namespace fs = boost::filesystem;

namespace gitUtils 
{
    std::string hashFile(std::string fileContent);
    std::string hashFile(const fs::path& path);

    bool isValidGitFolder();

    bool createObjectFile(std::string fileContent);
}