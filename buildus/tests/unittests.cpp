#define CATCH_CONFIG_MAIN

#include <iostream>
#include <filesystem>
#include <fstream>

#include "catch.hpp"
#include "../build/build.h"
#include "../build/utils.h"
#include "../build/clean.h"

namespace fs = std::filesystem;

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

    std::vector<std::string> includes;

    SECTION("Intermediate files are more recent")
    {
        REQUIRE(system("g++ -c file1.cpp -o temp/f1.o") == 0);
        REQUIRE(system("g++ -c file2.cpp -o temp/f2.o") == 0);
        REQUIRE(system("g++ -c file3.cpp -o temp/f3.o") == 0);

        for(const auto & file : configuration.compile)
        {
            REQUIRE_FALSE(Utils::DoesCPPNeedRebuild(file.path, includes, file.name));
        }
    }

    SECTION("Intermediate files are older")
    {
        REQUIRE(system("g++ -c file1.cpp -o temp/f1.o") == 0);
        REQUIRE(system("g++ -c file2.cpp -o temp/f2.o") == 0);
        REQUIRE(system("g++ -c file3.cpp -o temp/f3.o") == 0);

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
            REQUIRE_FALSE(Utils::DoesCPPNeedRebuild(file.path, includes, file.name));
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

    SECTION("Files with headers")
    {
        std::vector<std::string> include_headers;

        include_headers.push_back("headers/header.h");
        include_headers.push_back("headers/header1.h");

        configuration.deps_include_head = include_headers;

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
	for (fs::directory_iterator endDirIt, it(fs::current_path()/"intermediate"); it != endDirIt; ++it) {
		count++;
	}

    REQUIRE(count == 0);

}