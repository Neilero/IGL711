#include "clean.h"
//#include <boost/filesystem.hpp>

namespace fs = boost::filesystem;

void clean(){
    if(fs::exists(fs::current_path()/"temp")){
        fs::remove_all(fs::current_path()/"temp");
    }
}