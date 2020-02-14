add_executable(buildtests
        catch.hpp
        )

target_link_libraries(buildtests
        PRIVATE
        build
        )

target_include_directories(buildtests
        PRIVATE
        build
        )


