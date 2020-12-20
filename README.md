# This is the Lead Tech Shift Report  

### Affectionately known as LeTeRS  

This 'Lead Tech Shift Report' program was designed and implemented as a way of communicating important information between shifts. I was not able to implement a proper database backend due to network and system permissions. So, I implemented a simple "datastore" that essentially stores everything in a long string that is delimited by "--;". I plan to continue exploring options for this data storage methodology, and any suggestions would be appreciated!  
  
  
**(Update...)** I hated the hacky "datastore" I implemented to store data! So, now that the semester is over and I have a break until January, I'm researching and will be (hopefully soon) implementing a HyperSQL DB to store the passdown data. This should be a much more efficient, secure, and reliable method of persistent data storage and retrieval. I should also be able to adjust other aspects of the backend to allow the non-software savvy coworkers/management that I built it for the ability to easily adjust the checklist and staff lists. The staff list is easy enough right now, but I want to add the ability to adjust it for a single day in the event an employee from another shift switches shifts or works overtime to support a shift that is lacking staff (which is a big issue right now because we lost a lot of people and we're short staffed for day and swing shift).  
  
## The Main Screen   
A simple window to allow the user to select what they intend to do with a file menu for easy access to configuration files.  
![Image of Main Window](img/main.png)

## The Date Selection Window  
This is the first window seen if the user selects 'Review Previous Shift Reports'.  
![Image of Date Selection Window](img/date_selection.PNG)

## The Shift Report Entry Window  
This window is the heart of the program. Technicians are able to effectively communicate any important shift information through this window. Once the oncoming lead has reviewed and accepted the passdown, pressing 'Save and Close' will create an HTML email in Outlook that can be sent to anyone that needs to see it.  
![Image of Shift Report Entry](img/passdown_entry.PNG)



### I will be graduating in May of 2021 and will be willing and able to accept a software developer position immediately after.
