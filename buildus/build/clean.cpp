#include "clean.h"

namespace fs = std::filesystem;

void clean(){
    if(fs::exists(fs::current_path()/"temp")){
        fs::remove_all(fs::current_path()/"temp");
    }
}