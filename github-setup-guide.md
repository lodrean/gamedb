# GitHub Setup Guide for GameDB Project

This guide will help you connect your local GameDB project to GitHub.

## Prerequisites
- Git is already initialized in your project ✓
- Git user configuration is set up ✓
- .gitignore file is properly configured ✓
- GitHub workflows are already set up ✓

## Steps to Connect to GitHub

### 1. Create a New Repository on GitHub
1. Go to [GitHub](https://github.com/) and sign in to your account
2. Click the "+" button in the top-right corner and select "New repository"
3. Enter "gamedb" as the repository name
4. Add a description (optional)
5. Keep the repository as Public or set it to Private based on your preference
6. **Important**: Do NOT initialize the repository with a README, .gitignore, or license as your project already has these files
7. Click "Create repository"

### 2. Connect Your Local Repository to GitHub

After creating the repository, GitHub will show you commands to connect your existing repository. Use the HTTPS or SSH method based on your preference:

> **Important Note**: Your current branch is named "master", but GitHub's default branch name is "main". The commands below include a step to rename your branch from "master" to "main" to match GitHub's convention.

#### Option 1: HTTPS Method (Recommended for beginners)

```powershell
# Add the GitHub repository as a remote named "origin"
git remote add origin https://github.com/YOUR_USERNAME/gamedb.git

# Rename your main branch to "main" if it's not already named that
git branch -M main

# Push your local repository to GitHub
git push -u origin main
```

Replace `YOUR_USERNAME` with your actual GitHub username.

#### Option 2: SSH Method (Requires SSH key setup)

If you prefer using SSH (which doesn't require entering your password each time):

1. [Generate an SSH key](https://docs.github.com/en/authentication/connecting-to-github-with-ssh/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent) if you don't have one
2. [Add the SSH key to your GitHub account](https://docs.github.com/en/authentication/connecting-to-github-with-ssh/adding-a-new-ssh-key-to-your-github-account)
3. Connect your repository:

```powershell
# Add the GitHub repository as a remote named "origin"
git remote add origin git@github.com:YOUR_USERNAME/gamedb.git

# Rename your main branch to "main" if it's not already named that
git branch -M main

# Push your local repository to GitHub
git push -u origin main
```

Replace `YOUR_USERNAME` with your actual GitHub username.

### 3. Verify the Connection

After pushing your code, verify that the remote is correctly set up:

```powershell
git remote -v
```

This should show:
```
origin  https://github.com/YOUR_USERNAME/gamedb.git (fetch)
origin  https://github.com/YOUR_USERNAME/gamedb.git (push)
```

Or if using SSH:
```
origin  git@github.com:YOUR_USERNAME/gamedb.git (fetch)
origin  git@github.com:YOUR_USERNAME/gamedb.git (push)
```

### 4. View Your Repository on GitHub

Go to `https://github.com/YOUR_USERNAME/gamedb` to see your code on GitHub.

## Working with GitHub in IntelliJ IDEA

IntelliJ IDEA provides excellent integration with Git and GitHub:

1. Open the "Git" tool window (Alt+9)
2. You can commit, push, pull, and manage branches directly from the IDE
3. To push changes: Right-click on the project → Git → Push

## Additional Resources

- [GitHub Documentation](https://docs.github.com/en)
- [Git Handbook](https://guides.github.com/introduction/git-handbook/)
- [GitHub Flow](https://guides.github.com/introduction/flow/)
