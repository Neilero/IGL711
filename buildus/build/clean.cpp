#include "clean.h"

namespace fs = std::filesystem;

void clean(){
    if(fs::exists(fs::current_path()/"temporaire")){
        fs::remove_all(fs::current_path()/"temporaire");
    }
}