#define CATCH_CONFIG_MAIN

#include <string>
#include <iostream>
#include <fstream>
#include <filesystem>

#include "catch.hpp"
#include "../build/Config.h"
#include "../build/build.h"
#include "../build/utils.h"
#include "../build/clean.h"

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

TEST_CASE("Minimal compilation")
{
    Config configuration;

    CompileFile file1;
    file1.name = "f1";
    file1.path = "file1.cpp";

    CompileFile file2;
    file2.name = "f2";
    file2.path = "file2.cpp";

    CompileFile file3;
    file3.name = "f3";
    file3.path = "file3.cpp";

    std::vector<CompileFile> files;
    files.push_back(file1);
    files.push_back(file2);
    files.push_back(file3);

    std::ofstream ofs("file1.cpp");
    ofs << "" << std::endl;
    ofs.close();

    ofs = std::ofstream("file2.cpp");
    ofs << "" << std::endl;
    ofs.close();

    ofs = std::ofstream("file3.cpp");
    ofs << "" << std::endl;
    ofs.close();

    std::filesystem::create_directory("intermediate");

    SECTION("Intermediate files are more recent")
    {
        REQUIRE(system("g++ -c file1.cpp -o intermediate/f1.o") == 0);
        REQUIRE(system("g++ -c file2.cpp -o intermediate/f2.o") == 0);
        REQUIRE(system("g++ -c file3.cpp -o intermediate/f3.o") == 0);

        for(const auto & file : configuration.compile)
        {
            REQUIRE_FALSE(Utils::DoesCPPNeedRebuild(file.path, file.name));
        }
    }

    SECTION("Intermediate files are older")
    {
        REQUIRE(system("g++ -c file1.cpp -o intermediate/f1.o") == 0);
        REQUIRE(system("g++ -c file2.cpp -o intermediate/f2.o") == 0);
        REQUIRE(system("g++ -c file3.cpp -o intermediate/f3.o") == 0);

        ofs = std::ofstream ("file1.cpp");
        ofs << "" << std::endl;
        ofs.close();

        ofs = std::ofstream ("file2.cpp");
        ofs << "" << std::endl;
        ofs.close();

        ofs = std::ofstream ("file3.cpp");
        ofs << "" << std::endl;
        ofs.close();

        for(const auto & file : configuration.compile)
        {
            REQUIRE_FALSE(Utils::DoesCPPNeedRebuild(file.path, file.name));
        }
    }
}

TEST_CASE("Compile intermediate cpp files")
{
    Config configuration;

    CompileFile file1;
    file1.name = "f1";
    file1.path = "file1.cpp";

    std::ofstream ofs("file1.cpp");
    ofs << "" << std::endl;
    ofs.close();

    CompileFile file2;
    file2.name = "f2";
    file2.path = "file2.cpp";

    ofs = std::ofstream("file2.cpp");
    ofs << "" << std::endl;
    ofs.close();

    CompileFile file3;
    file3.name = "f3";
    file3.path = "file3.cpp";

    ofs = std::ofstream("file3.cpp");
    ofs << "" << std::endl;
    ofs.close();

    std::vector<CompileFile> files;
    files.push_back(file1);
    files.push_back(file2);
    files.push_back(file3);

    configuration.projet = "projet1";
    configuration.compile = files;

    SECTION("Files without includes")
    {
        REQUIRE(build(configuration) == 0);
    }

    SECTION("Files with environment variables")
    {
        std::vector<std::string> include_vars;

        include_vars.push_back("HOME");

        configuration.deps_include_var = include_vars;

        REQUIRE(build(configuration) == 0);
    }
}

TEST_CASE("Clean command")
{
    clean();

    int count = 0;

    for (fs::directory_iterator endDirIt, it(fs::current_path()/Utils::temporaryFolder); it != endDirIt; ++it) {
        count++;
    }

    REQUIRE(count == 0);

}