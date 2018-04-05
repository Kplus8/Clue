## CSCI306 Clue Project
We mostly worked together on one machine, so the commits were mainly made by Jim DeBlock (invalid-email-address is Graham Kitchenka, not sure why it says that), so it may not look even but we contributed an even amount.

NOTE: I added a branch called filesep-test which changes the static file separator ('\\') to a call to File which should give a system-dependent file separator at runtime. I did this because the backslash separator wasn't playing nice with my Linux machine, but I have no Windows machine to test on and didn't want to push potentially broken code to main.

When you get the chance, please try and test the branch on Windows. If it works feel free to merge into master (or I can if you let me know)

### Possible to-do (Brandon Verkamp)
- Refactored references to filenames to be more forgiving of users of different operating systems ;)
  - might be useful to hold file names (such that filesystem separator is filled at runtime) in some sort of global static reference? Might be bad design, I dunno.
- May consider a builder pattern for the board - Board.getInstance().setConfigFiles(\[files\]).loadConfig() instead of separate calls.