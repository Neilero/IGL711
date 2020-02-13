cmake_minimum_required(VERSION 3.12)
project(BuildUS
    VERSION 1.0
    LANGUAGES C CXX
    DESCRIPTION "Compilateur UdeS")

set(Boost_USE_STATIC_LIBS on)
set(Boost_USE_MULTITHREADED ON)

if (UNIX)
find_package(ZLIB REQUIRED) 
find_package(Boost 1.71.0 REQUIRED COMPONENTS iostreams filesystem)
else ()
find_package(Boost 1.71.0 REQUIRED COMPONENTS iostreams filesystem zlib)
endif ()
		
set(CXX_STANDARD 17)

add_executable(buildus buildus.cpp)

if (UNIX)
target_link_libraries(buildus 
    PUBLIC 
		${Boost_LIBRARIES}
		${ZLIB_LIBRARIES}
)
else ()
target_link_libraries(buildus 
    PUBLIC 
		${Boost_LIBRARIES}
)
endif ()	
target_include_directories(buildus 
    PUBLIC 
        $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}>
		${Boost_INCLUDE_DIRS}
)

