import tkinter
import random

def arithQuestion():
    global correctAns
    global tempIncorrect
    global numProblemsAttempted
    
    rand = random.randint(0, 3)
    
    if rand == 0:
        sign = '+'
        firstInt = random.randint(0, 999)
        secondInt = random.randint(0, 1000-firstInt)
        correctAns = firstInt + secondInt
    if rand == 1:
        sign = '-'
        firstInt = random.randint(0, 999)
        secondInt = random.randint(0, firstInt)
        correctAns = firstInt - secondInt
    if rand == 2:
        sign = '/'
        firstInt = random.randint(0, 999)
        possSecondInts = []
        for num in range(1, firstInt):
            if firstInt % num == 0:
                possSecondInts.append(num)
        secondIntIndex = random.randint(0, len(possSecondInts) - 1)
        secondInt = possSecondInts[secondIntIndex]
        correctAns = firstInt / secondInt
    if rand == 3:
        sign = '*'
        firstInt = random.randint(0, 99)
        secondInt = random.randint(0, 99)
        correctAns = firstInt * secondInt
        
    if [firstInt, sign, secondInt] not in problemLog:
        problemLabel.configure(text="{} {} {} =".format(firstInt, sign, secondInt))
        tempIncorrect = 0
        problemLog.append([firstInt, sign, secondInt])
        incorrectLabel.configure(text="{} incorrect answers for this problem".format(tempIncorrect))
        numProblemsAttempted += 1
    else:
        arithQuestion()

def checkAns():
    global correctAns
    global tempIncorrect
    global numProblemsAttempted
    global numSolved
    global numAttempts
    
    ans = ansEntry.get()
    numAttempts += 1
    if int(ans) == int(correctAns):
        correctIncorrectCounts[0] += 1
        arithQuestion()
        numSolved += 1
    else:
        correctIncorrectCounts[1] += 1
        tempIncorrect += 1
    correctLabel.configure(text="{} Problems Correct, {} Problems Incorrect".format(correctIncorrectCounts[0], correctIncorrectCounts[1]))
    incorrectLabel.configure(text="{} incorrect answers for this problem".format(tempIncorrect))
    
mainWindow = tkinter.Tk()
mainWindow.title("Play Math Game")

def quitGame():
    avgNumIncorrect = (numAttempts - numSolved) / numSolved
    print("The number of problems attempted this run were {}.".format(numProblemsAttempted))
    print("The number of problems solved this run were {}.".format(numSolved))
    print("The average number of incorrect answer attempts per problem was {}.".format(avgNumIncorrect))
    mainWindow.destroy()

global numProblemsAttempted
global numSolved
global numAttempts

correctIncorrectCounts = [0, 0]
problemLog = []
numSolved = 0
numProblemsAttempted = 0
numAttempts = 0

container = tkinter.Frame(mainWindow)
container.pack()
              
problemLabel = tkinter.Label(container, text="{} {} {} =")
problemLabel.pack(side=tkinter.LEFT)
ansEntry = tkinter.Entry(container)
ansEntry.pack()
button = tkinter.Button(mainWindow, text="Check...", command=checkAns)
button.pack()
correctLabel = tkinter.Label(mainWindow, text="0 Problems Correct, 0 Problems Incorrect")
correctLabel.pack()
incorrectLabel = tkinter.Label(mainWindow, text="0 incorrect answers for this problem")
incorrectLabel.pack()
newProblemButton = tkinter.Button(mainWindow, text="New Problem", command=arithQuestion)
newProblemButton.pack()
quitButton = tkinter.Button(mainWindow, text="Quit", command=quitGame)
quitButton.pack()

arithQuestion()
mainWindow.mainloop()