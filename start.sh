#!/bin/bash
screen="discord"
file="target/OlympaManager.jar"
ram_max="1536M"
screen -XS $screen quit
screen -dmS $screen java -Xmx$ram_max -jar $file