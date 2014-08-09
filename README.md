# ExplorationReward
This plugin runs a set of console commands when players discover new chunks.

## Config
The config will be generated after the first run with the plugin in the plugins folder.
<br>Configuration instructions can be found within the config file.
<br>The config can be used to configure what worlds the plugin operates in, and what commands are run when a player finds a new chunk.

The config contains commands to run when a player discovers a new chunk.
The commands will be run as console.
'%p' will be replaced with the player's name, '%w' will be replaced with the player's current world's name.
All commands support chatcolors.
Any number of commands and worlds may be added.
<br>Here is the default config:
<pre>worlds-to-operate-in:
  - 'world'
  - 'world_nether'
  - 'world_the_end'
commands:
  - 'give %p diamond 1'
  - 'say &r&6%p &rdiscovered a new chunk in &c ''%w''&r!'
  - 'tell %p &rNice job! You discovered a &6new chunk&r&c!&r Take your reward.'</pre>

## Commands
This plugin does not contain any commands.

## Permissions
This plugin does not use any permissions.