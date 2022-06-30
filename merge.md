# Merge

Merge incorporates changes from another branch into the current branch. Merge sometimes introduces a "merge commit". 

![Merge](./assets/merge.png)

## Scenario

You are working on ABC-111. The instructor is working on ABC-112.

ABC-111: Add "Introduction" to "book.md" before chapter 1
  - assigned to you

ABC-112: Add "Forward" to "book.md" before chapter 1
  - assigned to instructor

## Steps

  0. Checkout main and pull latest, make sure clean.
  1. Create/Checkout New Feature Branch (feature/ABC-111)
  2. Make code changes
  3. Commit
  4. Pretend you introduced a typo in your first commit. Make some more code changes and do another commit. Don't ammend your commit, create a new one so you have 2 commits in your feature branch. The reason we're doing this twice is because we want to experience the pain of merge conflicts twice. No pain no gain. 
  5. Commit again.
  6. Confirm with the instructor that they have already merged the ABC-112 pull request. Ensure you are 100% clean, then checkout main and pull latest, then re-checkout your feature branch.
  7. Perform a git merge. Notice we are on feature/ABC-111 and we want to merge main into our branch. Another way of saying this is we want to replay all commits that are in main that we don't have.
  8. At this point, Git should be yelling at you that you have merge conflicts. 
  9. Fix merge conflicts using a [dedicated mergetool](https://www.perforce.com/products/helix-core-apps/merge-diff-tool-p4merge) or IntelliJ/VSCode
  10. `git add` to mark resolution of merge conflicts
  11. `git merge --continue` to finish merging (`git merge --abort` if you make a mistake and want to start over)
  12. Repeat 9, 10, 11 as necessary.
  13. Push / Create Pull Request

## Next
  - [Rebase](./rebase.md)
