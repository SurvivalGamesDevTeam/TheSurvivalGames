Multilingual Tutorial
=====
Our plugin supports Multilanguage, but for this to function properly, we need both out developers and translators to follow certain guidelines.

***For our developers:***  (Translators, you are further down the page)

From now on, all strings added that are printed to the console or displayed to MUST be localized in the language files.

There is a semi-automatic method of doing this.  Here’s how:

So say you wanted to print “Hello World!” to the console, you would normally do this:

```Bukkit.getLogger.log(Level.INFO, “Hello World!”);```

With I18N, you would edit the enUS language file with the text editor of your choice, and under the correct section you would add the line:

```HELLO_WORLD =  Hello World!```

Do the same for every other language file we currently have, except leave the field after the equals sign blank, and comment out the line completely with a “#”.  This signals to our translators that this line needs to be translated, when the line is commented out, the plugin will automatically fall back to the English version, to avoid null pointers.  So the idID file (Indonesian) version would look like this:

```#HELLO_WORLD = ```

Until a translator got around to it, then it would look like:

```HELLO_WORLD = Halo Dunia!```

Once you have updated all the language files, the final log line would look like this:

```Bukkit.getLogger.log(Level.INFO, I18N.getLocaleString("HELLO_WORLD")```

Please not that all colors, spaces (before and after line), and new line chars (\n) should be done in code, not in the lang file

That’s it!  Thanks for helping make out plugin go international!


***Translators Tutorial:***

The language files are located in the src/main/resources folder of out GitHub repository.  Find your language file, we will use Indonesian here as an example.  Download both your language file (idID) and the English file (enUS) and open both of them in your favorite text editor.  If you see no lines that begin with a “#” then everything has already been translated, but if you see a line that begins with a hashtag, find the corresponding label in the enUS file, and translate the text from English to your language after the equals sign, and also sans the hashtag.

Here is an example:

English file (enUS):

```HELLO_WORLD =  Hello World!```
 
Indonesian File (idID):

```#HELLO_WORLD = ```

You would change to this:

Indonesian File (idID):

```HELLO_WORLD = Halo Dunia!```

You would then save the file and preferably submit a Pull Request on GitHub, but files can also be emailed to: survivalgamesdevteam@gmail.com

TOOLS SUCH AS GOOGLE TRANSLATE ARE UNACCEPTABLE!!!

You should check your language file every once in a while to see if there are any new lines to be translated.

That’s it!  Thanks for helping make out plugin go international!


