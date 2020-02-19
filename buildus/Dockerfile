FROM gcc

RUN wget https://github.com/Kitware/CMake/releases/download/v3.17.0-rc1/cmake-3.17.0-rc1-Linux-x86_64.sh \
    && sh cmake-3.17.0-rc1-Linux-x86_64.sh --skip-license --prefix=/usr/local/ --exclude-subdir \
    && rm cmake-3.17.0-rc1-Linux-x86_64.sh

RUN wget https://github.com/jbeder/yaml-cpp/archive/yaml-cpp-0.6.3.tar.gz \
    && tar xzf yaml-cpp-0.6.3.tar.gz \
    && rm yaml-cpp-0.6.3.tar.gz \
    && cd yaml-cpp-yaml-cpp-0.6.3 \
    && cmake . \
    && make \
    && make install \
    && cd .. \
    && rm -rf yaml-cpp-yaml-cpp-0.6.3