import tkinter
import ssl
from urllib.request import urlopen, urlretrieve
from urllib.parse import urlencode, quote_plus
import json

GOOGLEAPIKEY = "AIzaSyA3EmeV-xDcXDwEm3_ah45MbuZ8XuvNqcM"

class Globals:
   
   rootWindow = None
   mapLabel = None

   defaultLocation = "Mauna Kea, Hawaii"
   mapLocation = defaultLocation
   mapFileName = 'googlemap.gif'
   mapSize = 400
   zoomLevel = 9
   mapType = 'roadmap'
   
def geocodeAddress(addressString):
   urlbase = "https://maps.googleapis.com/maps/api/geocode/json?address="
   geoURL = urlbase + quote_plus(addressString)
   geoURL = geoURL + "&key=" + GOOGLEAPIKEY

   ctx = ssl.create_default_context()
   ctx.check_hostname = False
   ctx.verify_mode = ssl.CERT_NONE
   
   stringResultFromGoogle = urlopen(geoURL, context=ctx).read().decode('utf8')
   jsonResult = json.loads(stringResultFromGoogle)
   if (jsonResult['status'] != "OK"):
      print("Status returned from Google geocoder *not* OK: {}".format(jsonResult['status']))
      return (0.0, 0.0)
   loc = jsonResult['results'][0]['geometry']['location']
   return (float(loc['lat']),float(loc['lng']))

def getMapUrl(lat, lng):
   urlbase = "http://maps.google.com/maps/api/staticmap?"
   args = "center={},{}&zoom={}&size={}x{}&maptype={}&markers=color:red%7Clabel:S%7C{},{}&format=gif".format(lat,lng,Globals.zoomLevel,Globals.mapSize, Globals.mapSize, Globals.mapType, lat, lng)
   args = args + "&key=" + GOOGLEAPIKEY
   mapURL = urlbase+args
   return  mapURL

def retrieveMapFromGoogle():
   lat, lng = geocodeAddress(Globals.mapLocation)
   url = getMapUrl( lat, lng)
   urlretrieve(url, Globals.mapFileName)

def displayMap():
   retrieveMapFromGoogle()    
   mapImage = tkinter.PhotoImage(file=Globals.mapFileName)
   Globals.mapLabel.configure(image=mapImage)
   Globals.mapLabel.mapImage = mapImage
   
def readEntryAndDisplayMap():
   Globals.mapLocation = str(addressEntry.get())
   Globals.zoomLevel = 9
   displayMap()
   
def zoomIn():
    Globals.zoomLevel += 1
    displayMap()
    
def zoomOut():
    Globals.zoomLevel -= 1
    displayMap()
    
def radioButtonChosen():
    if choiceVar.get() == 1:
        Globals.mapType = "roadmap"
    if choiceVar.get() == 2:
        Globals.mapType = "satellite"
    if choiceVar.get() == 3:
        Globals.mapType = "terrain"
    if choiceVar.get() == 4:
        Globals.mapType = "hybrid"
    displayMap()
     
def initializeGUIetc():
   global addressEntry
   global choiceVar
    
   Globals.rootWindow = tkinter.Tk()
   Globals.rootWindow.title("HW9")
   mainFrame = tkinter.Frame(Globals.rootWindow) 
   mainFrame.pack()

   addressLabel = tkinter.Label(mainFrame, text="Enter the location: ")
   addressLabel.pack()
   addressEntry = tkinter.Entry(mainFrame)
   addressEntry.pack()
   readEntryAndDisplayMapButton = tkinter.Button(mainFrame, text="Show me the map!", command=readEntryAndDisplayMap)
   readEntryAndDisplayMapButton.pack()
   Globals.mapLabel = tkinter.Label(mainFrame, width=Globals.mapSize, bd=2, relief=tkinter.FLAT)
   Globals.mapLabel.pack()
   zoomInButton = tkinter.Button(mainFrame, text="+", command=zoomIn)
   zoomInButton.pack()
   zoomOutButton = tkinter.Button(mainFrame, text="-", command=zoomOut)
   zoomOutButton.pack()
   
   choiceVar = tkinter.IntVar()
   choiceVar.set(1)
   
   roadmapRadio = tkinter.Radiobutton(mainFrame, text="roadmap", variable=choiceVar, value=1, command=radioButtonChosen)
   roadmapRadio.pack()
   satelliteRadio = tkinter.Radiobutton(mainFrame, text="satellite", variable=choiceVar, value=2, command=radioButtonChosen)
   satelliteRadio.pack()
   terrainRadio = tkinter.Radiobutton(mainFrame, text="terrain", variable=choiceVar, value=3, command=radioButtonChosen)
   terrainRadio.pack()
   hybridRadio = tkinter.Radiobutton(mainFrame, text="hybrid", variable=choiceVar, value=4, command=radioButtonChosen)
   hybridRadio.pack()

def HW9():
    initializeGUIetc()
    displayMap()
    Globals.rootWindow.mainloop()

if __name__ == '__main__':
    HW9()














