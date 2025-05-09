#!/bin/sh
# Copy this file to .git/hooks/pre-commit and make it executable

echo "Running detekt check..."
OUTPUT="/tmp/detekt-$(date +%s)"
./gradlew detekt > $OUTPUT
EXIT_CODE=$?
if [ $EXIT_CODE -ne 0 ]; then
  cat $OUTPUT
  rm $OUTPUT
  echo "Detekt check failed. Please fix the above issues before committing."
  exit 1
fi
rm $OUTPUT
exit 0