#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
mkdir -p out
javac -Xlint:unchecked -d out $(find src -name "*.java")
java -cp out app.Main