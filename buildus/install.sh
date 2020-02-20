#!/bin/bash

echo Create temp folder yamlCpp_install
mkdir yamlCpp_install
(
  cd yamlCpp_install || exit

  echo Download yaml-cpp v0.6.3
  wget https://github.com/jbeder/yaml-cpp/archive/yaml-cpp-0.6.3.tar.gz

  echo Extract library
  tar xzf yaml-cpp-0.6.3.tar.gz
  cd yaml-cpp-yaml-cpp-0.6.3 || exit

  echo Run CMake
  cmake .

  echo Run make
  make

  echo Run sudo make install
  sudo make install || make install # if root user, sudo won't exist
)

echo Remove temp folder yamlCpp_install
rm -rf yamlCpp_install

echo yaml-cpp has been successfully installed!