## CSCI306 Clue Project
We mostly worked together on one machine, so the commits were mainly made by Jim DeBlock (invalid-email-address is Graham Kitchenka, not sure why it says that), so it may not look even but we contributed an even amount.

### Possible to-do (Brandon Verkamp)
- Refactored references to filenames to be more forgiving of users of different operating systems ;)
  - might be useful to hold file names (such that filesystem separator is filled at runtime) in some sort of global static reference? Might be bad design, I dunno.
- May consider a builder pattern for the board - Board.getInstance().setConfigFiles(\[files\]).loadConfig() instead of separate calls.