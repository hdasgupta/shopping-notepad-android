#!/usr/bin/env sh
# Lightweight Gradle launcher. Downloads the exact Gradle distribution from
# gradle-wrapper.properties, then runs it; this avoids requiring a system Gradle.
set -eu
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
PROPERTIES="$APP_HOME/gradle/wrapper/gradle-wrapper.properties"
DIST_URL=$(sed -n 's/^distributionUrl=//p' "$PROPERTIES" | sed 's/\\:/:/g' | tr -d '\r')
if [ -z "$DIST_URL" ]; then
  echo "distributionUrl is missing from $PROPERTIES" >&2
  exit 1
fi
DIST_NAME=$(basename "$DIST_URL" .zip)
GRADLE_USER_HOME=${GRADLE_USER_HOME:-"$HOME/.gradle"}
INSTALL_DIR="$GRADLE_USER_HOME/manual-wrapper/${DIST_NAME%-bin}"
if [ ! -x "$INSTALL_DIR/bin/gradle" ]; then
  mkdir -p "$GRADLE_USER_HOME/manual-wrapper" "$GRADLE_USER_HOME/manual-wrapper/.tmp"
  TMP_ZIP="$GRADLE_USER_HOME/manual-wrapper/.tmp/$DIST_NAME.zip"
  echo "Downloading $DIST_NAME..."
  if command -v curl >/dev/null 2>&1; then
    curl --fail --location --retry 3 --retry-delay 2 "$DIST_URL" --output "$TMP_ZIP"
  elif command -v wget >/dev/null 2>&1; then
    wget -O "$TMP_ZIP" "$DIST_URL"
  else
    echo "curl or wget is required to download Gradle." >&2
    exit 1
  fi
  command -v unzip >/dev/null 2>&1 || { echo "unzip is required to unpack Gradle." >&2; exit 1; }
  unzip -q -o "$TMP_ZIP" -d "$GRADLE_USER_HOME/manual-wrapper"
  rm -f "$TMP_ZIP"
fi
exec "$INSTALL_DIR/bin/gradle" -p "$APP_HOME" "$@"
