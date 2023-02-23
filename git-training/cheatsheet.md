# Cheat Sheet

`git status`
  - Self-explanatory

`git clone https://github.com/dev10-program/associate-support.git`
  - clone (download) a git repository from remote to your local

`git checkout -b feature/new-branch-name`
  - create a new branch

`git checkout feature/some-other-branch`
  - check out a different existing branch

`git fetch`
  - update local copy of remote, but do not merge with local repository

`git merge`
  - merge two (or more) branches

`git pull`
  - Fetch + Merge in one command

`git branch` (or) `git branch -l`
  - Show local branches

`git branch -lr`
  - Show remote branches

`git commit -m "Commit message goes here"`
  - Perform a commit

`git push`
  - Push my current branch to the default upstream tracking branch

`git push -u origin`
  - Push my current branch and set the upstream tracking branch at the same time

`git diff`
  - Show file differences that are not yet added to staging area

`git reset`
  - Force the current branch to point directly to a different commit

`git rebase`
  - Move this entire branch and all of its commits on top of another commit

`git restore`
  - Can be used to move files from the staging area back out of the staging area 
  - Can be used to restore any changes from files

`git revert`
  - Undo a previous commit by adding a new commit that undoes the previous commit

`git remote show origin`
  - Show lots of information about the remote git repository called "origin"

`git stash`
  - Take all current changes (in files that are known to git) and "stash them" away so that I can do something and quickly come back and use them again. Can be used multiple times. The stash is a "stack" (FIFO). The stash is a "stack" (FIFO).

`git stash pop`
  - Retrieve the most recently stashed changes. Can be used multiple times. The stash is a "stack" (FIFO).
