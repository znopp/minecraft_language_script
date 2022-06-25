# Mr Language Script
 #### everything I make has "mr" because yes

---

## Features:

- [x] Doesn't destroy the json
- [x] Enter Minecraft version to upgrade to (for your convenience)
- [x] Enter the name of the new and old language file
- [x] Tells you how many lines of text were added
- [x] Tells you if something from the old file no longer exists
- [x] Tells you where the new additions begin

---

## Instructions

### Prerequisites:
- Java 17
- Your current language file (.json)
- Your new language file (E.g. newly generated 1.19 en_us.json)
- A keyboard

### Steps, in order:

- Download the source, extract it and open the folder. 
  - You should now be able to see folders, such as ``src``


- In the ``run`` folder, add your old and new .json language files.


- Open a terminal by, on Windows, typing ``cmd`` in the explorer path (bar on the top)


- Finally, type ``java -jar m`` and press ``tab`` to autocomplete the name of the jar.
  - If this doesn't work, it may be because your ``JAVA_HOME`` is set to a different version of Java.
    - In order to mitigate this, replace ``java`` with your whole installation path.
    Example:``C:\Program Files\Eclipse Adoptium\jdk-17.0.3.7-hotspot\bin\java.exe``
  - Hit ``enter`` to begin the upgrading process, and follow instructions when prompted.