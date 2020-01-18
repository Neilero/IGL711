#include <iostream>

#include <string>
#include <boost/filesystem.hpp>
#include <boost/functional/hash.hpp>

namespace fs = boost::filesystem;


int hashFile(fs::path path);