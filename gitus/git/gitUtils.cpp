#include "gitUtils.h"

namespace fs = boost::filesystem;

int hashFile(fs::path path)
{
    std::ifstream ifs(path.string());
    std::string fileContent((std::istreambuf_iterator<char>(ifs)),(std::istreambuf_iterator<char>()));

    boost::hash<std::string> string_hash;

    return string_hash(fileContent);
}