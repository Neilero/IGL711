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
#include "../build/link.h"

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

        configFile << configContent << std::endl;
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
    std::ofstream ofs;
    ofs = std::ofstream ("fichier1.cpp");
    ofs << "" << std::endl;
    ofs.close();

    ofs = std::ofstream ("fichier2.cpp");
    ofs << "" << std::endl;
    ofs.close();

    ofs = std::ofstream ("fichier3.cpp");
    ofs << "" << std::endl;
    ofs.close();

    std::filesystem::create_directory("intermediate");

    std::vector<Config::CompileFile> compilesFiles;

    compilesFiles.push_back(Config::CompileFile{"f1", "fichier1.cpp"});
    compilesFiles.push_back(Config::CompileFile{"f2", "fichier2.cpp"});
    compilesFiles.push_back(Config::CompileFile{"f3", "fichier3.cpp"});

    SECTION("Intermediate files are more recent")
    {
        REQUIRE(system("g++ -c fichier1.cpp -o intermediate/f1.o") == 0);
        REQUIRE(system("g++ -c fichier2.cpp -o intermediate/f2.o") == 0);
        REQUIRE(system("g++ -c fichier3.cpp -o intermediate/f3.o") == 0);

        for(const auto & file : compilesFiles)
        {
            REQUIRE_FALSE(Utils::DoesCPPNeedRebuild(file.path, file.name));
        }
    }

    SECTION("Intermediate files are older")
    {
        REQUIRE(system("g++ -c fichier1.cpp -o intermediate/f1.o") == 0);
        REQUIRE(system("g++ -c fichier2.cpp -o intermediate/f2.o") == 0);
        REQUIRE(system("g++ -c fichier3.cpp -o intermediate/f3.o") == 0);

        ofs = std::ofstream ("fichier1.cpp");
        ofs << "" << std::endl;
        ofs.close();

        ofs = std::ofstream ("fichier2.cpp");
        ofs << "" << std::endl;
        ofs.close();

        ofs = std::ofstream ("fichier3.cpp");
        ofs << "" << std::endl;
        ofs.close();

        for(const auto & file : compilesFiles)
        {
            REQUIRE(Utils::DoesCPPNeedRebuild(file.path, file.name));
        }
    }
}

TEST_CASE("Compile intermediate cpp files")
{
    SECTION("Generate include string")
    {
        std::vector<std::string> vars;
        vars.push_back("HOME");
        REQUIRE(createIncludeOptionsFromVars(vars) == " -I "+std::string(getenv("HOME")));

        vars.push_back("CANEXISTEPAS");
        REQUIRE_THROWS(createIncludeOptionsFromVars(vars));
    }

    SECTION("Generate g++ command")
    {
        
    }

    SECTION("Main function")
    {
        fs::path configFilePath = fs::current_path() / "config.buildus";
        std::ofstream configFile(configFilePath);
        auto configContent = "projet: app1\n"
                                "compile:\n"
                                " - f1 : fichier1.cpp\n"
                                " - f2 : fichier2.cpp\n"
                                " - f3 : fichier3.cpp\n"
                                "package: f1 f2 f3";

        configFile << configContent;
        configFile.flush();
        Config config(configFilePath.string());
    }
}

TEST_CASE("Clean command")
{
    fs::path tempPath = fs::current_path() / Utils::temporaryFolder;

    SECTION("No temporary folder") {
        //nothing should happened
        REQUIRE_FALSE(fs::exists(tempPath));
        REQUIRE_NOTHROW(clean());
    }

    SECTION("With temporary folder") {
        REQUIRE_NOTHROW(fs::create_directory(tempPath));
        REQUIRE(fs::exists(tempPath));

        SECTION("No file in temporary folder") {
            //nothing should happened
            long fileCount = std::distance(fs::directory_iterator(tempPath), fs::directory_iterator());
            REQUIRE(fileCount == 0);
            REQUIRE_NOTHROW(clean());
        }

        SECTION("With files in temporary folder") {
            // create empty files
            std::ofstream(tempPath / "f1.txt");
            std::ofstream(tempPath / "f2.txt");

            long fileCount = std::distance(fs::directory_iterator(tempPath), fs::directory_iterator());
            REQUIRE(fileCount != 0);

            REQUIRE_NOTHROW(clean());
            REQUIRE_FALSE(fs::exists(tempPath));
        }
    }

    fs::remove(tempPath);
}

TEST_CASE("Linking files")
{
    clean();

    /*SECTION("Simple config") 
    {
        auto configContent = "projet: app\n"
                             "compile:\n"
                             " - f1 : fichier1.cpp\n"
                             " - f2 : fichier2.cpp\n"
                             "package: f1 f2";

        fs::path configFilePath = fs::current_path() / "config.buildus";
        std::ofstream configFile(configFilePath);

        configFile << configContent << std::endl;
        Config config(configFilePath.string());

        

        /*SECTION("Command created")
        {
            std::string expectedCommand = "g++ f1.o f2.o -o app";
            REQUIRE(createLinkCommand(config) == expectedCommand);
        }*/

        /*SECTION("Executable created")
        {
            std::cout<<linkFiles(config);
            compileFiles(config);
            linkFiles(config);
            fs::path folderPath = fs::current_path();
            std::cout<<"folder: "<<folderPath;
            REQUIRE(fs::exists(folderPath/config.getProjet()));
            
            //REQUIRE(fs::exists(folderPath));
        }*/

        /*SECTION("Returned code")
        {
            //REQUIRE(linkFiles(config) == 0);
        }*/

    //}

    /*SECTION("Complex config") 
    {
        auto configContent = "projet: app\n"
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
    
        fs::path configFilePath = fs::current_path() / "config.buildus";
        std::ofstream configFile(configFilePath);

        configFile << configContent << std::endl;
        Config config(configFilePath.string());

        build(config);
        linkFiles(config);

        /*SECTION("Command created")
        {
            std::string expectedCommand = "g++ f1.o f2.o -o app -L/boostinstall/boost_1_71_0/stage/lib -llib1 -llib2";
            REQUIRE(createLinkCommand(config) == expectedCommand);
        }*/

        /*SECTION("Executable created")
        {
            std::cout<<linkFiles(config);
            compileFiles(config);
            linkFiles(config);
            fs::path folderPath = fs::current_path();
            std::cout<<"folder: "<<folderPath;
            REQUIRE(fs::exists(folderPath/config.getProjet()));
            
            //REQUIRE(fs::exists(folderPath));
        }*/

    //}

    //Cas d'erreur

    clean();

}