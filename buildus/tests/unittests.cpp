#define CATCH_CONFIG_MAIN

#include <string>
#include <iostream>
#include <fstream>
#include <filesystem>
#include <iomanip>

using namespace std::chrono_literals;

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

    SECTION("Exception tests") {

        SECTION("Mandatory elements") {
            SECTION("Missing projet") {
                auto configContent = "compile:\n"
                                     " - f1 : fichier1.cpp\n"
                                     " - f2 : fichier2.cpp\n"
                                     "package: f1 f2";

                configFile << configContent << std::endl;
                REQUIRE_THROWS_AS(Config(configFilePath.string()), Config::ConfigParsingException);
            }
            SECTION("Missing compile") {
                auto configContent = "projet: app1\n"
                                     "package: f1 f2";

                configFile << configContent << std::endl;
                REQUIRE_THROWS_AS(Config(configFilePath.string()), Config::ConfigParsingException);
            }
            SECTION("Missing package") {
                auto configContent = "projet: app1\n"
                                     "compile:\n"
                                     " - f1 : fichier1.cpp\n"
                                     " - f2 : fichier2.cpp\n";

                configFile << configContent << std::endl;
                REQUIRE_THROWS_AS(Config(configFilePath.string()), Config::ConfigParsingException);
            }
        }

        SECTION("Wrong format - Unsupported operations") {
            SECTION("Multiple projets") {
                auto configContent = "projet:\n"
                                     " - app1\n"
                                     " - app2\n"
                                     "compile:\n"
                                     " - f1 : fichier1.cpp\n"
                                     " - f2 : fichier2.cpp\n"
                                     "package: f1 f2";

                configFile << configContent << std::endl;
                REQUIRE_THROWS_AS(Config(configFilePath.string()), Config::ConfigParsingException);
            }
            SECTION("Compile is not a map") {
                auto configContent = "projet: app1\n"
                                     "compile:\n"
                                     " - fichier1.cpp\n"
                                     " - fichier2.cpp\n"
                                     "package: f1 f2";

                configFile << configContent << std::endl;
                REQUIRE_THROWS_AS(Config(configFilePath.string()), Config::ConfigParsingException);
            }
            SECTION("package is not a scalar") {
                auto configContent = "projet: app1\n"
                                     "compile:\n"
                                     " - f1 : fichier1.cpp\n"
                                     " - f2 : fichier2.cpp\n"
                                     "package:\n"
                                     " - f1\n"
                                     " - f2\n";

                configFile << configContent << std::endl;
                REQUIRE_THROWS_AS(Config(configFilePath.string()), Config::ConfigParsingException);
            }
            SECTION("deps_include is not a map") {
                auto configContent = "projet: app2\n"
                                     "deps_include:\n"
                                     " - BOOST_INCLUDEDIR\n"
                                     "deps_library:\n"
                                     " var: BOOST_LIBRARYDIR\n"
                                     " libs:\n"
                                     " - lib1\n"
                                     "compile:\n"
                                     " - f1 : fichier1.cpp\n"
                                     "package: f1";

                configFile << configContent << std::endl;
                REQUIRE_THROWS_AS(Config(configFilePath.string()), Config::ConfigParsingException);
            }
            SECTION("deps_library is not a map") {
                auto configContent = "projet: app2\n"
                                     "deps_include:\n"
                                     " var: BOOST_INCLUDEDIR\n"
                                     "deps_library:\n"
                                     " - BOOST_LIBRARYDIR\n"
                                     " - lib1\n"
                                     "compile:\n"
                                     " - f1 : fichier1.cpp\n"
                                     "package: f1";

                configFile << configContent << std::endl;
                REQUIRE_THROWS_AS(Config(configFilePath.string()), Config::ConfigParsingException);
            }
            SECTION("deps_include's lib is not a list") {
                auto configContent = "projet: app2\n"
                                     "deps_include:\n"
                                     " var: BOOST_INCLUDEDIR\n"
                                     "deps_library:\n"
                                     " var: BOOST_LIBRARYDIR\n"
                                     " libs: lib1 lib2\n"
                                     "compile:\n"
                                     " - f1 : fichier1.cpp\n"
                                     "package: f1";

                configFile << configContent << std::endl;
                REQUIRE_THROWS_AS(Config(configFilePath.string()), Config::ConfigParsingException);
            }
        }
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

    SECTION("Intermediate files are more recent")
    {
        // To have enough time between modification dates
        usleep(1000);

        ofs = std::ofstream ("intermediate/f1.o");
        ofs << "" << std::endl;
        ofs.close();

        ofs = std::ofstream ("intermediate/f2.o");
        ofs << "" << std::endl;
        ofs.close();

        ofs = std::ofstream ("intermediate/f3.o");
        ofs << "" << std::endl;
        ofs.close();

        REQUIRE_FALSE(Utils::DoesCPPNeedRebuild("fichier1.cpp", "f1"));
        REQUIRE_FALSE(Utils::DoesCPPNeedRebuild("fichier2.cpp", "f2"));
        REQUIRE_FALSE(Utils::DoesCPPNeedRebuild("fichier3.cpp", "f3"));
    }

    SECTION("Intermediate files are older")
    {
        ofs = std::ofstream ("intermediate/f1.o");
        ofs << "" << std::endl;
        ofs.close();

        ofs = std::ofstream ("intermediate/f2.o");
        ofs << "" << std::endl;
        ofs.close();

        ofs = std::ofstream ("intermediate/f3.o");
        ofs << "" << std::endl;
        ofs.close();

        // To have enough time between modification dates
        usleep(1000);

        ofs = std::ofstream ("fichier1.cpp");
        ofs << "" << std::endl;
        ofs.close();

        ofs = std::ofstream ("fichier3.cpp");
        ofs << "" << std::endl;
        ofs.close();

        REQUIRE(Utils::DoesCPPNeedRebuild("fichier1.cpp", "f1"));
        REQUIRE_FALSE(Utils::DoesCPPNeedRebuild("fichier2.cpp", "f2"));
        REQUIRE(Utils::DoesCPPNeedRebuild("fichier3.cpp", "f3"));
    }

    SECTION("No intermediate files")
    {
        REQUIRE(Utils::DoesCPPNeedRebuild("fichier1.cpp", "f1"));
        REQUIRE(Utils::DoesCPPNeedRebuild("fichier2.cpp", "f2"));
        REQUIRE(Utils::DoesCPPNeedRebuild("fichier3.cpp", "f3"));
    }

    if(fs::exists(fs::current_path()/"intermediate"))
    {
        fs::remove_all(fs::current_path()/"intermediate");
    }

    fs::remove_all(fs::current_path()/"fichier1.cpp");
    fs::remove_all(fs::current_path()/"fichier2.cpp");
    fs::remove_all(fs::current_path()/"fichier3.cpp");

}

TEST_CASE("Compile intermediate cpp files")
{
    SECTION("Generate include string")
    {
        std::vector<std::string> vars;
        vars.push_back("HOME");
        REQUIRE(createIncludeOptionsFromVars(vars) == " -I "+std::string(getenv("HOME")));

        vars.push_back("BOOST_ROOT");
        REQUIRE(createIncludeOptionsFromVars(vars) == " -I "+std::string(getenv("HOME"))+" -I "+std::string(getenv("BOOST_ROOT")));

        vars.push_back("CANEXISTEPAS");
        REQUIRE_THROWS(createIncludeOptionsFromVars(vars));
    }

    SECTION("Generate g++ command")
    {
        REQUIRE(createCompileCommand("fichier1.cpp", "f1", " -I /home/user/something") == "g++ -c fichier1.cpp -o intermediate/f1.o -I /home/user/something");

        REQUIRE(createCompileCommand("quelquepart/fichier1.cpp", "f1", " -I /home/user/something") == "g++ -c quelquepart/fichier1.cpp -o intermediate/f1.o -I /home/user/something");
    }

    SECTION("Main function with configuration file")
    {
        fs::path configFilePath = fs::current_path() / "config.buildus";
        std::ofstream configFile(configFilePath);

        SECTION("Config file without includes")
        {
            std::string configContent = "projet: app1\n"
                "compile:\n"
                " - f1 : fichier1.cpp\n"
                " - f2 : fichier2.cpp\n"
                " - f3 : fichier3.cpp\n"
                "package: f1 f2 f3";

            configFile << configContent;
            configFile.flush();
            Config config(configFilePath.string());

            SECTION("CPP Files don't exist")
            {
                REQUIRE(compileFiles(config) != 0);
            }
            SECTION("CPP Files exist")
            {
                SECTION("One file or more aren't valid")
                {
                    std::ofstream ofs("fichier1.cpp");
                    ofs << "#incln 0;}" << std::endl;
                    ofs.close();

                    ofs = std::ofstream ("fichier2.cpp");
                    ofs << "#inn 0;}" << std::endl;
                    ofs.close();

                    ofs = std::ofstream ("fichier3.cpp");
                    ofs << "#inrn 0;}" << std::endl;
                    ofs.close();

                    REQUIRE(compileFiles(config) != 0);

                    ofs = std::ofstream ("fichier2.cpp");
                    ofs << "#include <iostream>\nint main() {std::cout << \"Hello, World!\";return 0;}" << std::endl;
                    ofs.close();

                    ofs = std::ofstream ("fichier3.cpp");
                    ofs << "#include <iostream>\nint main() {std::cout << \"Hello, World!\";return 0;}" << std::endl;
                    ofs.close();

                    REQUIRE(compileFiles(config) != 0);
                }

                SECTION("Files are valid")
                {
                    std::ofstream ofs("fichier1.cpp");
                    ofs << "#include <iostream>\nint main() {std::cout << \"Hello, World!\";return 0;}" << std::endl;
                    ofs.close();

                    ofs = std::ofstream ("fichier2.cpp");
                    ofs << "#include <iostream>\nint main() {std::cout << \"Hello, World!\";return 0;}" << std::endl;
                    ofs.close();

                    ofs = std::ofstream ("fichier3.cpp");
                    ofs << "#include <iostream>\nint main() {std::cout << \"Hello, World!\";return 0;}" << std::endl;
                    ofs.close();

                    REQUIRE(compileFiles(config) == 0);
                    REQUIRE(fs::exists("intermediate/f1.o"));
                    REQUIRE(fs::exists("intermediate/f2.o"));
                    REQUIRE(fs::exists("intermediate/f3.o"));
                }
            }
        }
    }

    if(fs::exists(fs::current_path()/"intermediate"))
    {
        fs::remove_all(fs::current_path()/"intermediate");
    }

    fs::remove_all(fs::current_path()/"fichier1.cpp");
    fs::remove_all(fs::current_path()/"fichier2.cpp");
    fs::remove_all(fs::current_path()/"fichier3.cpp");
    
}

TEST_CASE("Clean command")
{
    fs::path tempPath = fs::current_path() / Utils::temporaryFolder;

    SECTION("No temporary folder") {
        //nothing should happen
        REQUIRE_FALSE(fs::exists(tempPath));
        REQUIRE_NOTHROW(clean());
    }

    SECTION("With temporary folder") {
        REQUIRE_NOTHROW(fs::create_directory(tempPath));
        REQUIRE(fs::exists(tempPath));

        SECTION("No file in temporary folder") {
            //nothing should happen
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

    fs::create_directory(Utils::temporaryFolder);
    std::ofstream helloWorldCPP(fs::current_path()/"helloWorld.cpp");
    const std::string appName = "app";
    std::string intermediatePath = (fs::current_path()/Utils::temporaryFolder).string() + "/";
    REQUIRE(fs::exists("helloWorld.cpp"));
    REQUIRE(fs::exists(Utils::temporaryFolder));

    SECTION("Simple config") 
    {
        auto helloWorld =    "#include <iostream>\n"
                             "using namespace std;\n"
                             "int main(){\n"
                             "cout << \"Hello, world, from Visual C++!\" << endl;}";
        helloWorldCPP << helloWorld << std::endl;
        system(("g++ -c helloWorld.cpp -o " + Utils::temporaryFolder + "/helloWorld.o").c_str());

        auto configContent = "projet: " + appName + "\n"
                             "compile:\n"
                             " - helloWorld : helloWorld.cpp\n"
                             "package: hw";

        fs::path configFilePath = fs::current_path() / "config.buildus";
        std::ofstream configFile(configFilePath);

        configFile << configContent << std::endl;
        Config config(configFilePath.string());

        SECTION("Command created")
        {
            std::string expectedCommand = "g++ " + intermediatePath + "helloWorld.o -o " + intermediatePath + appName;
            REQUIRE(createLinkCommand(config) == expectedCommand);
        }

        SECTION("Executable created")
        {
            REQUIRE(linkFiles(config) == 0);
            REQUIRE(fs::exists(intermediatePath + appName));            
        }

    }

    SECTION("Complex config") 
    {
        auto helloWorld =    "#include <iostream>\n"
                             "#include <yaml-cpp/yaml.h>\n"
                             "using namespace std;\n"
                             "int main(){\n"
                             "YAML::Node node = YAML::Load(\"[1, 2, 3]\");"
                             "cout << \"Hello, world, from Visual C++!\" << endl;}";
        helloWorldCPP << helloWorld << std::endl;
        REQUIRE(system(("g++ -c helloWorld.cpp -o " + Utils::temporaryFolder + "/helloWorld.o").c_str()) == 0);
        
        auto configContent = "projet: " + appName + "\n"
                             "deps_library:\n"
                             " libs:\n"
                             " - yaml-cpp\n"
                             "compile:\n"
                             " - helloWorld : helloWorld.cpp\n"
                             "package: hw";
    
        fs::path configFilePath = fs::current_path() / "config.buildus";
        std::ofstream configFile(configFilePath);

        configFile << configContent << std::endl;
        Config config(configFilePath.string());

        linkFiles(config);

        SECTION("Command created")
        {
            std::string expectedCommand = "g++ " + intermediatePath + "helloWorld.o -o " + intermediatePath + appName + " -lyaml-cpp";
            REQUIRE(createLinkCommand(config) == expectedCommand);
        }

        SECTION("Executable created")
        {
            REQUIRE(linkFiles(config) == 0);
            REQUIRE(fs::exists(intermediatePath + appName)); 
        }

    }

    SECTION("Missing file")
    {
        auto configContent = "projet: " + appName + "\n"
                             "deps_library:\n"
                             " libs:\n"
                             " - yaml-cpp\n"
                             "compile:\n"
                             " - helloWorld : helloWorld.cpp\n"
                             "package: hw";
    
        fs::path configFilePath = fs::current_path() / "config.buildus";
        std::ofstream configFile(configFilePath);

        configFile << configContent << std::endl;
        Config config(configFilePath.string());

        linkFiles(config);

        SECTION("Command created")
        {
            std::string expectedCommand = "g++ " + intermediatePath + "helloWorld.o -o " + intermediatePath + appName + " -lyaml-cpp";
            REQUIRE(createLinkCommand(config) == expectedCommand);
        }

        SECTION("Executable not created")
        {
            REQUIRE(linkFiles(config) != 0);
            std::cout<<intermediatePath + appName<<std::endl;
            REQUIRE_FALSE(fs::exists(intermediatePath + appName)); 
        }
    }

    clean();
    helloWorldCPP.close();
    fs::remove(fs::current_path()/"helloWorld.cpp");

}