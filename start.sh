#!/bin/bash
screen="discord"
file="OlympaManager.jar"
ram_max="1536M"

screen -dmS $screen java -Xmx$ram_max -jar $file