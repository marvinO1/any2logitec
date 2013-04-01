The "Jekings to Gamepad" project for NCA Code Camp 2013.

Approach
--------
 - We have a text file consumer which will read text files from the directory defined with the 
   system environment LOGITEC_DISPLAY_FOLDER_ROOT. The consumer is written in C++ to be able to
   talk to the Logitec game pad.
   
   - the first 4 lines of such a text file will be send to the logitec game pad.
   - after sending the lines, the file will be removed by the consumer
   - name pattern of the files: *.nn.message
        - where nn is the time in seconds the message should be displayed at least
        - currently the consumer ignores this argument
        - also the files will be consumed in any order. It is not a FIFO behaviour implemented yet!
   - in case there is no new file, the last displayed text will remain on the game pad
   - an empty file will reset the text on the game pad
   
     
 - We have a text file producer written in Java (the code of this project) which connects to a 
   jenkins server and pulls the state of all jobs.
   
   - failing jobs will be added to the text file
   - text file will be written with jenkins.05.message to LOGITEC_DISPLAY_FOLDER_ROOT
   - we wait for 60 seconds before we do the next check.
   
   
 - Consumer as well as producer could be registered as Windows Services (thanks to this great operating
   system). 