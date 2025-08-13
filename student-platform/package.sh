#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
rm -rf out dist
mkdir -p out dist
javac -Xlint:unchecked -d out $(find src -name "*.java")
jar --create --file dist/student-platform.jar --main-class app.Main -C out .
echo "Built dist/student-platform.jar"