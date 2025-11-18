#!/usr/bin/env bash
set -e

# Output zip name (default: project.zip)
OUT=${1:-project.zip}

# Zip project excluding build cache
zip -r "$OUT" . -x "target/*" ".git/*" ".idea/*" "*.iml"

echo "Package created: $OUT"
