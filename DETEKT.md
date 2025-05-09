# Detekt Static Analysis for GameDB

This project uses [Detekt](https://detekt.dev/) for static code analysis, with special attention to Jetpack Compose best practices.

## Running Detekt

You can run Detekt using the following Gradle commands:

```bash
# Run Detekt on the entire project
./gradlew detekt

# Run Detekt on all sources (including build files)
./gradlew detektAll

# Generate a baseline file to exclude existing issues
./gradlew detektBaseline
```

## Configuration

The Detekt configuration is located in the following files:

- `config/detekt/detekt.yml`: Main configuration file
- `config/detekt/baseline.xml`: Baseline file for excluding existing issues

## Compose-Specific Rules

This project includes the following Compose-specific rules:

1. **Modifier Reuse**: Avoid creating new Modifier instances in loops or frequently recomposed composables
2. **State Management**: Ensure proper use of remember and rememberSaveable
3. **Composable Function Naming**: Follow the UI component naming conventions
4. **Mutable Parameters**: Avoid using mutable parameters in composables
5. **Multiple Content Emitters**: Don't have multiple emitters in a single composable
6. **Public Preview Functions**: Ensure @Preview composables are public for better tooling support
7. **Modifier Order**: Maintain a consistent order for modifiers (clickable before padding, etc.)
8. **Default Modifier Parameter**: Include default Modifier parameter in composable functions

## Pre-commit Hook

To install the pre-commit hook that runs Detekt before each commit:

1. Copy the pre-commit hook script to the Git hooks directory:
   ```bash
   cp pre-commit-hook.sh .git/hooks/pre-commit
   ```

2. Make the script executable:
   ```bash
   chmod +x .git/hooks/pre-commit
   ```

## CI/CD Integration

Detekt is integrated into the CI/CD pipeline using GitHub Actions. The workflow is defined in `.github/workflows/detekt.yml`.

## Customizing Rules

To customize the Detekt rules:

1. Edit the `config/detekt/detekt.yml` file
2. Run Detekt to see the effects of your changes
3. Generate a new baseline file if needed

## Troubleshooting

If you encounter issues with Detekt:

1. Make sure you have the latest version of the Detekt plugin
2. Check the Detekt documentation at https://detekt.dev/
3. Regenerate the baseline file if you've made significant changes to the codebase