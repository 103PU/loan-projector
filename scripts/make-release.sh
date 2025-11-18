#!/usr/bin/env bash
set -e

# Release version arg
VERSION=${1:-"0.0.1"}
TAG="v${VERSION}"

git add .
git commit -m "chore: release $TAG" || true    # Commit release changes
git tag -f "$TAG"                               # Create tag

./scripts/package-project.sh "release-${VERSION}.zip"  # Build package

echo "Release complete: release-${VERSION}.zip"
