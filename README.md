# This is the Lead Tech Shift Report  

### Affectionately known as LeTeRS  

This 'Lead Tech Shift Report' program was designed and implemented as a way of communicating important information between shifts. I was not able to implement a proper database backend due to network and system permissions. So, I implemented a simple "datastore" that essentially stores everything in a long string that is delimited by "--;". I plan to continue exploring options for this data storage methodology (like a database that resides in a network folder but can run locally), and any suggestions would be appreciated!  
  
## The Main Screen   
A simple window to allow the user to select what they intend to do with a file menu for easy access to configuration files.  
![Image of Main Window](img/main.png)

## The Date Selection Window  
This is the first window seen if the user selects 'Review Previous Shift Reports'.  
![Image of Date Selection Window](img/date_selection.PNG)

## The Shift Report Entry Window  
This window is the heart of the program. Technicians are able to effectively communicate any important shift information through this window. Once the oncoming lead has reviewed and accepted the passdown, pressing 'Save and Close' will create an HTML email in Outlook that can be sent to anyone that needs to see it.  
![Image of Shift Report Entry](img/passdown_entry.PNG)
