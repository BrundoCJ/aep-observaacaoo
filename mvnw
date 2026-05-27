#!/bin/sh
MAVEN_VERSION="3.9.6"
M2_DIR="$HOME/.m2/wrapper/dists/apache-maven-$MAVEN_VERSION"
M2_HOME="$M2_DIR/apache-maven-$MAVEN_VERSION"

if [ ! -f "$M2_HOME/bin/mvn" ]; then
    echo "[mvnw] Apache Maven $MAVEN_VERSION não encontrado. Fazendo download..."
    mkdir -p "$M2_DIR"
    curl -fsSL "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/$MAVEN_VERSION/apache-maven-$MAVEN_VERSION-bin.tar.gz" \
        -o "$M2_DIR/maven.tar.gz"
    tar -xzf "$M2_DIR/maven.tar.gz" -C "$M2_DIR"
    rm "$M2_DIR/maven.tar.gz"
    chmod +x "$M2_HOME/bin/mvn"
    echo "[mvnw] Download concluído."
fi

exec "$M2_HOME/bin/mvn" "$@"
