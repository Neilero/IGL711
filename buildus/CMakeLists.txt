cmake_minimum_required(VERSION 3.12)
project(BuildUS
    VERSION 1.0
    LANGUAGES C CXX
    DESCRIPTION "Compilateur UdeS")

#set(Boost_USE_STATIC_LIBS on)
#set(Boost_USE_MULTITHREADED ON)

#if (UNIX)
#find_package(ZLIB REQUIRED)
#find_package(Boost 1.71.0 REQUIRED COMPONENTS iostreams filesystem)
#else ()
#find_package(Boost 1.71.0 REQUIRED COMPONENTS iostreams filesystem zlib)
#endif ()

find_library(yaml-cpp REQUIRED)

set(CMAKE_CXX_STANDARD 17)

add_subdirectory(build)
add_subdirectory(tests)

add_executable(buildus buildus.cpp)

target_link_libraries(buildus
	PRIVATE
		build
		yaml-cpp
		"stdc++fs"
	)
