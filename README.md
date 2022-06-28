# Git After-Hours Lesson

## Assumptions

This lesson assumes that you already have Git installed/configured and have very basic understanding of repositories, branches and commits. 

![xkcd: Git](./assets/xkcd.png)

<i>If that doesn't fix it, git.txt contains the phone number of a friend of mine who understands git. Just wait through a few minutes of 'It's really pretty simple, just think of branches as...' and eventually you'll learn the commands that will fix everything.</i>

## Goals

This lesson will walk you through various development scenarios
  - Visualizing Commit History
  - Merge Conflicts
  - Squashing Commits
  - Undoing / Reverting Changes
  - Cherry Picking 

## GitHub-Flow branch-based workflow

Before we get to development scenarios, let's take a moment to understand the concept of a branching model. There are [multiple branching models](https://www.atlassian.com/git/tutorials/comparing-workflows) in use today for Git. [GitFlow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow) is a branching model that can use different branches to track multiple releases simultaneously, and [GitHub-Flow](https://docs.github.com/en/get-started/quickstart/github-flow) is a newer branching model that emphasizes simplicity for frequent deployments. There is no "best" branching model, just different branching models with different pros and cons.

#### GitFlow

![GitFlow](./assets/gitflow.png)

#### GitHub-Flow

![GitHub-Flow](./assets/github-flow.png)

We are going to use the GitHub-Flow branching model in this lesson.