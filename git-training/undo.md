# Revert and Reset

Revert
  - Adds a new commit, reversing a previous commit

Reset
  - Technically, it moves the HEAD and/or changes the 
  - The HEAD is what YOU are pointed at.
  - Different forms do different things
  - `git reset` - undo add to staging area
  - `git reset --soft` - Undo a commit, keeping the files that were committed in the Staging area
  - `git reset --mixed` - Undo a commit, keeping the files that were committed, but not keeping them in the staging area
  - `git reset --hard` - Undo a commit, completely throwing away all changes.

## Scenario

1. You are working on a code change, and immediately after commit, you realize you made a typo in one of the files or in the commit message.
  - `git reset --soft`
  - `git reset --mixed`
You can also add HEAD~1 and the command is identical.
  - `git reset --soft HEAD~1`
  - `git reset --mixed HEAD~1`
  - the 1 means 1 commit.

2. You have no idea what you were thinking last night and you want to rage quit all the changes you made.
  - `git reset --hard`
  - `git reset --hard HEAD~1`

3. You made 5 commits and you want to squash all the commits into a new commit
  - `git reset --soft HEAD~5`
  - `git reset --mixed HEAD~5`

## Next
  - [Cherry Picking](./cherry.md)