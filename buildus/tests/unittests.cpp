#define CATCH_CONFIG_MAIN

#include <string>
#include <filesystem>

#include "catch.hpp"
#include "../build/Config.h"

namespace fs = std::filesystem;

TEST_CASE("Configuration - YAML parsing")
{
    fs::path configFilePath = fs::current_path() / "config.buildus";
    std::ofstream configFile(configFilePath);

    SECTION("Simple config") {
        auto configContent = "projet: app1\n"
                             "compile:\n"
                             " - f1 : fichier1.cpp\n"
                             " - f2 : fichier2.cpp\n"
                             "package: f1 f2";

        // define expected results
        auto expectedProjet = "app1";
        Config::CompileFile f1{"f1", "fichier1.cpp"};
        Config::CompileFile f2{"f2", "fichier2.cpp"};
        std::vector<Config::CompileFile> expectedCompile{f1, f2};
        std::vector<std::string> expectedPackage{"f1", "f2"};

        configFile << configContent << std::endl;
        Config config(configFilePath.string());

        REQUIRE(config.getProjet() == "app1");
        REQUIRE(config.getCompile() == expectedCompile);
        REQUIRE(config.getPackage() == expectedPackage);
    }

    SECTION("Complex config") {
        auto configContent = "projet: app2\n"
                             "deps_include:\n"
                             " var: BOOST_INCLUDEDIR\n"
                             "deps_library:\n"
                             " var: BOOST_LIBRARYDIR\n"
                             " libs:\n"
                             " - lib1\n"
                             " - lib2\n"
                             "compile:\n"
                             " - f1 : fichier1.cpp\n"
                             " - f2 : fichier2.cpp\n"
                             "package: f1 f2";

        // define expected results
        auto expectedProjet = "app2";
        std::vector<std::string> expectedDepsIncludeVar{"BOOST_INCLUDEDIR"};
        std::vector<std::string> expectedDepsLibraryVar{"BOOST_LIBRARYDIR"};
        std::vector<std::string> expectedDepsLibraryLibs{"lib1", "lib2"};
        Config::CompileFile f1{"f1", "fichier1.cpp"};
        Config::CompileFile f2{"f2", "fichier2.cpp"};
        std::vector<Config::CompileFile> expectedCompile{f1, f2};
        std::vector<std::string> expectedPackage{"f1", "f2"};

        configFile << configContent;
        configFile.flush();
        Config config(configFilePath.string());

        REQUIRE(config.getProjet() == "app2");
        REQUIRE(config.getDepsIncludeVar() == expectedDepsIncludeVar);
        REQUIRE(config.getDepsLibraryVar() == expectedDepsLibraryVar);
        REQUIRE(config.getDepsLibraryLibs() == expectedDepsLibraryLibs);
        REQUIRE(config.getCompile() == expectedCompile);
        REQUIRE(config.getPackage() == expectedPackage);
    }

    configFile.close();
    std::remove(configFilePath.c_str());
}
