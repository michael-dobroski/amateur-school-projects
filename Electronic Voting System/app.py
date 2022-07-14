from ast import arg
from asyncore import poll
from email.policy import default
from enum import unique
from multiprocessing.sharedctypes import Value
from pickle import FALSE
from unittest import result
from unittest.util import strclass
from flask import Flask, render_template, session, url_for, request, redirect, flash
from flask_wtf import FlaskForm
from pymysql import NULL, IntegrityError
from sqlalchemy import and_, true
from wtforms import StringField
from wtforms.validators import DataRequired
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.sql import func
import pymysql
from datetime import datetime, date
import random
import math
import smtplib, ssl
import hashlib
import json

STATES = ["AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", 
          "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", 
          "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", 
          "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", 
          "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"]

app = Flask(__name__)

app.config['SECRET_KEY'] = '9c41f7884fdcc89b3faa8f1bff86f345' # need it for db queries
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:iowa@127.0.0.1:3306/project' # change!
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = FALSE

db = SQLAlchemy(app)

class users(db.Model):
    voterID = db.Column(db.Integer, primary_key=True, unique=True)
    password = db.Column(db.String(64))
    user_type = db.Column(db.String(45))
    first_name = db.Column(db.String(45))
    last_name = db.Column(db.String(45))
    age = db.Column(db.Integer())
    address = db.Column(db.String(45))
    address2 = db.Column(db.String(45))
    city = db.Column(db.String(45))
    state = db.Column(db.String(45))
    zipcode = db.Column(db.String(10))
    driver_license_num = db.Column(db.String(45))
    email = db.Column(db.String(45))
    pwd = db.Column(db.String(45))
    verified = db.Column(db.Integer)
    access_code = db.Column(db.Integer)
    valid_code = db.Column(db.String(45))
    incorrect_attempts = db.Column(db.Integer)
    last_login = db.Column(db.DateTime)

    def __init__(self, voterID, password, user_type, first_name, last_name, age, address, address2, city, state, zipcode, driver_license_num, email, pwd, verified, access_code, valid_code, incorrect_attempts, last_login):
        self.voterID = voterID
        self.password = password
        self.user_type = user_type
        self.first_name = first_name
        self.last_name = last_name
        self.age = age
        self.address = address
        self.address2 = address2
        self.city = city
        self.state = state
        self.zipcode = zipcode
        self.driver_license_num = driver_license_num
        self.email = email
        self.pwd = pwd
        self.verified = verified
        self.access_code = access_code
        self.valid_code = valid_code
        self.incorrect_attempts = incorrect_attempts
        self.last_login = last_login

class login_log(db.Model):
    inc = db.Column(db.Integer, primary_key=True)
    login_ID = db.Column(db.Integer)
    login_success = db.Column(db.Integer)
    login_date = db.Column(db.DateTime)

    def __init__(self, login_ID, login_success, login_date):
        self.login_ID = login_ID
        self.login_success = login_success
        self.login_date = login_date

class precincts(db.Model):
    precinct_id = db.Column(db.Integer, primary_key=True, unique=True)
    location = db.Column(db.String(45))
    pooling_manager_id = db.Column(db.String(100))
    state_election_office_email = db.Column(db.String(45))
    zipcode = db.Column(db.Integer)
    zip_4_start = db.Column(db.String(45))
    zip_4_end = db.Column(db.String(45))

    def __init__(self, location, pooling_manager_id, state_election_office_email, zipcode, zip_4_start, zip_4_end):
        self.location = location
        self.pooling_manager_id = pooling_manager_id
        self.state_election_office_email = state_election_office_email
        self.zipcode = zipcode
        self.zip_4_start = zip_4_start
        self.zip_4_end = zip_4_end

class races(db.Model):
    race_id = db.Column(db.Integer, primary_key=True, unique=True)
    title = db.Column(db.String(45))
    term = db.Column(db.String(45))
    cand1 = db.Column(db.String(45))
    cand2 = db.Column(db.String(45))
    cand3 = db.Column(db.String(45))
    cand4 = db.Column(db.String(45))
    cand5 = db.Column(db.String(45))
    election_id = db.Column(db.Integer)
    total1 = db.Column(db.Integer)
    total2 = db.Column(db.Integer)
    total3 = db.Column(db.Integer)
    total4 = db.Column(db.Integer)
    total5 = db.Column(db.Integer)

    def __init__(self, race_id, title, term, cand1, cand2, cand3, cand4, cand5, election_id, total1, total2, total3, total4, total5):
        self.race_id = race_id
        self.title = title
        self.term = term
        self.cand1 = cand1
        self.cand2 = cand2
        self.cand3 = cand3
        self.cand4 = cand4
        self.cand5 = cand5
        self.election_id = election_id
        self.total1 = total1
        self.total2 = total2
        self.total3 = total3
        self.total4 = total4
        self.total5 = total5
        
class elections(db.Model):
    election_id = db.Column(db.Integer, primary_key=True, unique=True)
    title = db.Column(db.String(45))
    polling_date = db.Column(db.String(45))
    start_time = db.Column(db.String(45))
    end_time = db.Column(db.String(45))
    complete = db.Column(db.Integer)
    in_progress = db.Column(db.Integer)

    def __init__(self, election_id, title, polling_date, start_time, end_time, complete, in_progress):
        self.election_id = election_id
        self.title = title
        self.polling_date = polling_date
        self.start_time = start_time
        self.end_time = end_time
        self.complete = complete
        self.in_progress = in_progress

class election_precincts(db.Model):
    election_id = db.Column(db.Integer)
    precinct_id = db.Column(db.Integer)
    ballot_id = db.Column(db.Integer, primary_key=True, unique=True)
    ballot_active = db.Column(db.Integer)

    def __init__(self, election_id, precinct_id, ballot_active):
        self.election_id = election_id
        self.precinct_id = precinct_id
        self.ballot_active = ballot_active

class race_precincts(db.Model):
    inc = db.Column(db.Integer, primary_key=True, unique=True)
    race_id = db.Column(db.Integer)
    precinct_id = db.Column(db.Integer)

    def __init__(self, race_id, precinct_id):
        self.race_id = race_id
        self.precinct_id = precinct_id

class polling_managers(db.Model):
    voterID = db.Column(db.Integer, primary_key=True, unique=True)
    precinct_id = db.Column(db.Integer)

    def __init__(self, voterID, precinct_id):
        self.voterID = voterID
        self.precinct_id = precinct_id

class candidates(db.Model):
    name = db.Column(db.String(45), primary_key=True, unique=True)
    bio = db.Column(db.String(500))

    def __init__(self, name):
        self.name = name

class ballot_info(db.Model):
    ballot_id = db.Column(db.Integer)
    voterID = db.Column(db.Integer)
    ballot_info = db.Column(db.String(450))
    inc = db.Column(db.Integer, primary_key=True, unique=True)

    def __init__(self, ballot_id, voterID, ballot_info):
        self.ballot_id = ballot_id
        self.voterID = voterID
        self.ballot_info = ballot_info

db.create_all()
curVoterID = ''
#added a comment here
def createAccountDict():
    #place query here using unique userID above for account info
    currentUser = users.query.get(curVoterID)

    #insert into the corresponding values below

    utype=currentUser.user_type
    fname=currentUser.first_name        
    lname=currentUser.last_name         
    age=currentUser.age               
    addr=currentUser.address           
    apt=currentUser.address2          
    city=currentUser.city              
    state=currentUser.state             
    zip=currentUser.zipcode           
    dlno=currentUser.driver_license_num
    email=currentUser.email
    precID = int(getAssocPrecinct(currentUser.zipcode))
    prec = precincts.query.filter_by(precinct_id=precID).first()
    precinctName = prec.location
    elections = getAssocElections(prec)
    currElect = []
    pastElect = []
    for e in elections:
        if e.complete == 1 or hasVoted(e.election_id,currentUser.voterID,precID) == 'True':
            pastElect.append(e)
        else:
            currElect.append(e)
    return locals()

def hasVoted(electID,voterID,precID):
    #get ballot
    ballotID = election_precincts.query.filter(and_(election_precincts.election_id.like(int(electID)),election_precincts.precinct_id.like(precID))).first()
    #get all voters to use that ballot
    voters = ballot_info.query.filter_by(ballot_id=ballotID.ballot_id).all()
    for v in voters:
        if voterID == int(v.voterID):
            return 'True'
    return 'False'

def getAssocPrecinct(zip):
    #split up user zipcode
    zipArray = str(zip).split("-")
    #Query prceinct db for matching starting zip
    matchingPrecs = precincts.query.filter_by(zipcode=zipArray[0]).all()
    if len(matchingPrecs) > 0:#if matching
        for prec in matchingPrecs:#loop through matching
            start = prec.zip_4_start#see if 2nd portion falls in range of start-end
            end = prec.zip_4_end
            if int(zipArray[1]) >= start and int(zipArray[1]) <= end:#if it does, send string
                return str(prec.precinct_id)
        return "No Matching Precinct+4 For Given Zipcode"#if it doesnt, send string
    else:#if not matching send string saying that
        return "No Matching Precinct For Given Zipcode"

def getAssocElections(prec):
    #Get all election IDs associate with that precinct
    electionIDs = election_precincts.query.filter_by(precinct_id=prec.precinct_id).all()
    elects = []
    for ids in electionIDs:
        newElection = elections.query.filter_by(election_id=ids.election_id).first()
        elects.append(newElection)
    return elects
    
# checks to see if a password meets the requirements
def isPasswordStrong(password):
    digitFlag = False

    # checks to see there is a number in the password
    for ele in password:
        if ele.isdigit():
            digitFlag = True

    # makes sure password is valid
    if digitFlag and (len(password) >= 8):
        print("password works")
        return True
    else:
        return False

# checks to see if current user is admin, returns True for admin user type and False otherwise
def checkAdmin():
    if curVoterID == '':
        return False
    admins = users.query.filter_by(user_type='admin')
    for admin in admins:
        if int(curVoterID) == int(admin.voterID):
            return True
    return False

# checks for overlapping precincts
def checkPrecinct(zip, start, finish):
    sharedZips = precincts.query.filter_by(zipcode=zip).all()#query precinct db for any precincts with the same zip
    if len(sharedZips) > 0:#if there is one with same zip, check if the entered start and finish zips overlap
        #do something
        for pcs in sharedZips: #loop for all precincts with same zip
            currentStart = pcs.zip_4_start
            currentEnd = pcs.zip_4_end
            if (start >= currentStart and start <= currentEnd): #if start is in another range
                return False
            elif (finish >= currentStart and finish <= currentEnd): #if end is in another range
                return False
            else:
                return True
    else: #if the zip is not shared
        return True
    

@app.route('/index.html')
def index():
    print(curVoterID)
    return render_template('index.html', voterID=curVoterID)

@app.route('/login_page.html', methods=['GET', 'POST'])
def login_page():
    if request.method == 'POST':
        global curVoterID 
        curVoterID = int(request.form['userID'])
        getPassword = hashlib.sha256(str(request.form['password']).encode('utf-8')).hexdigest() #secure password
        newLogin = login_log(curVoterID, 0, datetime.now())
        #check if voterID exists in DB
        try:
            voter = users.query.get_or_404(curVoterID)
            #get password from specific voterID
            if voter.verified == 0: #if account is locked
                db.session.add(newLogin)
                db.session.commit()
                flash("The account with the associated Voter ID is locked and needs verification. Check back later.", "error")
            else:
                if voter.password == getPassword: #if dbPassword == givenPassword
                    voter.incorrect_attempts = 0 #update login attempts
                    voter.access_code = str(random.random())[-7:-1] # random six digit code
                    db.session.commit()
                    server = smtplib.SMTP_SSL("smtp.gmail.com", 465)
                    server.login("voterprojectclass@gmail.com", "Group1Project123!")
                    server.sendmail("voterprojectclass@gmail.com", voter.email, 
                                ("Hello, here is your one-time validation code\n \nValidation Code: {}\n \nBest,\nVoter Registration").format(voter.access_code))
                    server.quit()
                    return redirect(url_for('login_access_code'))
                else:#else if password incorrct
                    #increment incorrect counter and show error
                    voter.incorrect_attempts = voter.incorrect_attempts + 1
                    db.session.add(newLogin)
                    if voter.incorrect_attempts < 3:
                        flash("Incorrect Password. "+str(3-voter.incorrect_attempts)+" login attempts remaining.", "error")
                    else:
                        voter.verified = FALSE
                        flash("You have been locked out of your account for too many failed attempts. Please check the email associated with your account for your next steps", "error")
                    db.session.commit()
            return render_template('login_page.html', pageTitle='Login')
        except:
            db.session.add(newLogin)
            db.session.commit()
            flash("Entered Voter ID did not match any verified accounts. Please try again.", "error")
            return render_template('login_page.html', pageTitle='Login')
    else:
        return render_template('login_page.html', pageTitle='Login')

@app.route('/login_access_code.html', methods=['GET', 'POST'])
def login_access_code():
    if request.method == 'POST':
        userCode = int(request.form['accessCode'])
        voter = users.query.get_or_404(curVoterID)
        if voter.access_code == userCode:
            newLogin = login_log(curVoterID, 1, datetime.now())
            voter.last_login = datetime.now()
            voter.access_code = None
            db.session.add(newLogin)
            db.session.commit()
            return redirect(url_for('account_home'))
        else:
            flash("The entered code did not match the one sent to your email. Please try again", "error")
            return render_template('login_access_code.html', pageTitle='Login Page')
    else:
        return render_template('login_access_code.html', pageTitle='Login Page')

@app.route('/logout.html', methods=['GET', 'POST'])
def logout():
    global curVoterID
    if curVoterID== '':
        return '''SESSION TIMED OUT'''
    if request.method == 'POST':
        if request.form['lButtons'] == 'Cancel':
            return render_template('/index.html', pageTitle='Public Home Page', voterID=curVoterID)
        elif request.form['lButtons'] == 'Logout':
            curVoterID = ''
            return redirect('/index.html')
    else:
        return render_template('/logout.html', voterID=curVoterID)

@app.route('/create_precinct.html', methods=['GET', 'POST'])
def create_precinct():
    if not checkAdmin():
        return '''ACCESS DENIED'''
    if request.method == 'POST':
        #get info from form
        state = str(request.form['location'])
        email = str(request.form['state_election_office_email'])
        #check for overlapping precincts
        zipcode = int(request.form['zipcode'])
        zip4start = int(request.form['zip_4_start'])
        zip4end = int(request.form['zip_4_end'])
        response = checkPrecinct(zipcode,zip4start,zip4end)
        if response:
            #create new precinct object with form
            newPrecinct = precincts(state,"None",email,zipcode,zip4start,zip4end)
            #import new precinct to dB
            db.session.add(newPrecinct)
            db.session.commit()
            #Flash message and stay on page
            flash("Successfully Added New Precinct", "error")
            precs = precincts.query.all()
            return render_template('create_precinct.html', precincts=precs, voterID=curVoterID)
        else:
            flash("Precinct Has Overlapping Zip Extensions With Existing Precinct, Check Existing Precincts Below and Try Again", "error")
            precs = precincts.query.all()
            return render_template('create_precinct.html', precincts=precs, voterID=curVoterID)
    else:
        precs = precincts.query.all()
        return render_template('create_precinct.html', precincts=precs, voterID=curVoterID)

@app.route('/view_precinct/<precID>/<ID>', methods=['GET','POST'])
def view_precinct(precID,ID):
    if request.method == 'POST':
        electionID = request.form['id']
        return redirect(url_for('view_elections',electionID=electionID,ID=ID))
    else:
        try:
            user = users.query.filter_by(voterID=ID).first()
            if user.user_type == 'pm':
                prec = precincts.query.filter_by(precinct_id=precID).first()
                elects = getAssocElections(prec)
                return render_template('view_precinct.html', pageTitle='View Precinct',prec=prec,voterID=ID, elects=elects)
            else:
                return '''ACCESS DENIED'''
        except AttributeError:
            return '''SESSION TIMED OUT'''

@app.route('/view_election/<electionID>/<ID>', methods=['GET','POST'])
def view_elections(electionID,ID):
    user = users.query.filter_by(voterID=ID).first()
    precID = int(getAssocPrecinct(user.zipcode))
    elect = elections.query.filter_by(election_id=electionID).first()
    assocBallotInfo = election_precincts.query.filter(and_(election_precincts.election_id.like(int(electionID)),election_precincts.precinct_id.like(precID))).first()
    prec = 0
    if user.user_type == 'pm':
        assoc = polling_managers.query.filter_by(voterID=user.voterID).first()
        prec = assoc.precinct_id
    if request.method == 'POST':
        print(request.form)
        if request.form['submitBTN'] == 'Return To Precinct Info':
            prec = request.form['id']
            return redirect(url_for('view_precinct',precID=prec,ID=ID))
        elif request.form['submitBTN'] == 'Activate Ballot':
            assocBallotInfo.ballot_active = 1
            db.session.commit()
        elif request.form['submitBTN'] == 'Deactivate Ballot':
            assocBallotInfo.ballot_active = 0
            db.session.commit()
        elif request.form['submitBTN'] == 'Declare Election':
            return redirect(url_for('declare_election',electionID=electionID,ID=ID,precID=prec))
    raceIDs = race_precincts.query.filter_by(precinct_id=precID).all()
    allRaces = []
    for r in raceIDs:
        race = races.query.filter_by(race_id=r.race_id).first()
        if race.election_id == int(electionID):
            allRaces.append(race)
    return render_template('view_election.html', pageTitle='View Election', voterID=ID, election=elect, ballot=assocBallotInfo, races=allRaces, electionID=electionID, user=user, prec=prec)

@app.route('/declare_election/<electionID>/<ID>/<precID>', methods=['GET','POST'])
def declare_election(electionID,ID,precID):
    elect = elections.query.filter_by(election_id=electionID).first()
    #assocBallotInfo = election_precincts.query.filter(and_(election_precincts.election_id.like(int(electionID)),election_precincts.precinct_id.like(precID))).first()
    raceIDs = race_precincts.query.filter_by(precinct_id=precID).all()
    allRaces = []
    for r in raceIDs:
        race = races.query.filter_by(race_id=r.race_id).first()
        if race.election_id == int(electionID):
            allRaces.append(race)
    if request.method == 'POST':
        if request.form['submitBTN'] == 'Cancel':
            return redirect(url_for('view_elections',electionID=electionID,ID=ID))
        if request.form['submitBTN'] == 'Declare Election':
            elect.complete = 1
            db.session.commit()
    return render_template('declare_election.html', pageTitle='Declare Election', voterID=ID, election=elect, races=allRaces, electionID=electionID, prec=precID)

@app.route('/view_results/<electionID>/<ID>/<precID>', methods=['GET','POST'])
def view_results(electionID,ID,precID):
    elect = elections.query.filter_by(election_id=electionID).first()
    #assocBallotInfo = election_precincts.query.filter(and_(election_precincts.election_id.like(int(electionID)),election_precincts.precinct_id.like(precID))).first()
    raceIDs = race_precincts.query.filter_by(precinct_id=precID).all()
    allRaces = []
    winners = []
    for r in raceIDs:
        race = races.query.filter_by(race_id=r.race_id).first()
        if race.election_id == int(electionID):
            allRaces.append(race)
            votes = [race.total1,race.total2,race.total3,race.total4,race.total5]
            cands = [race.cand1,race.cand2,race.cand3,race.cand4,race.cand5]
            idx = votes.index(max(votes))
            winners.append(cands[idx])

    return render_template('view_results.html', pageTitle='View Results', voterID=ID, election=elect, races=allRaces, electionID=electionID, prec=precID, winningCand=winners)

@app.route('/candidate_info/<electionID>/<ID>/<candName>', methods=['GET','POST'])
def candidate_info(electionID,ID,candName):
    if candName != None:
        cand=candidates.query.filter_by(name=candName).first()
    return render_template('candidate_info.html', pageTitle='View Candidate Info', voterID=ID, electionID=electionID, cand=cand)

@app.route('/account_create.html', methods=['GET', 'POST'])
def account_create():
    if request.method == 'POST':
        userInfo = {}
        userInfo['email'] = str(request.form['email'])
        found_user = users.query.filter_by(email=userInfo['email']).first()
        userInfo['password'] = str(request.form['pwd'])
        confirmPwd = str(request.form['pwd2'])
        userInfo['state'] = str(request.form['state'])
        if (found_user or (confirmPwd != userInfo['password']) or userInfo['state'] not in STATES):
            if found_user:
                flash("*An account with that email already exists!", 'error')
            if confirmPwd != userInfo['password']:
                flash("*Password does not match confirmation field.", 'error')
            if userInfo['state'] not in STATES:
                flash("*Invalid state abbrevation!", 'error')
            return render_template('account_create.html', pageTitle='Account Creation Page')
        else:
            userInfo['firstName'] = str(request.form['fname'])
            userInfo['lastName'] = str(request.form['lname'])
            userInfo['age'] = int(request.form['age'])
            userInfo['address'] = str(request.form['addr'])
            userInfo['apartment'] = str(request.form['apt'])
            userInfo['city'] = str(request.form['city'])
            userInfo['zip'] = str(request.form['zip'])
            #userInfo['zip'] = str(userInfo['zip'])[:5]+'-'+str(userInfo['zip'])[6:]
            userInfo['driversLicenseNum'] = str(request.form['dlno'])
            ids = []
            foo = users.query.all()
            for user in foo:
                # print(user.voterID, user.first_name, user.last_name, user.age, user.address, user.address2, user.city, user.state, user.zipcode, user.driver_license_num, user.email, user.pwd, user.verified)
                ids.append(user.voterID)
            randID = random.randint(1000, 9999)
            while randID in ids:
                randID = random.randint(1000, 9999)
            verifCode = str(random.random())[-7:-1] # random six digit code
            pwd = hashlib.sha256(userInfo['password'].encode('utf-8')).hexdigest()
            usr = users(randID, 
                        pwd,
                        "user",
                        userInfo['firstName'],
                        userInfo['lastName'],
                        userInfo['age'],
                        userInfo['address'],
                        userInfo['apartment'],
                        userInfo['city'],
                        userInfo['state'],
                        userInfo['zip'],
                        userInfo['driversLicenseNum'],
                        userInfo['email'],
                        userInfo['password'],
                        0,
                        None,
                        verifCode,
                        0,
                        datetime.now())
            db.session.add(usr)
            db.session.commit()

            server = smtplib.SMTP_SSL("smtp.gmail.com", 465)
            server.login("voterprojectclass@gmail.com", "Group1Project123!")
            server.sendmail("voterprojectclass@gmail.com", userInfo['email'], 
                                ("Hello,\n \nValidation Code: {}\n \nBest,\nVoter Registration").format(verifCode))
            server.quit()

            return redirect(url_for('verify_email', email=userInfo['email'], voterID=randID))
    else:
        return render_template('account_create.html', pageTitle='Account Creation Page')

@app.route('/verify_email.html', methods=['GET', 'POST'])
def verify_email():
    if request.method == 'POST':
        userCode = request.form['vcode']
        targetID = request.form['currID']
        targetEmail = request.form['currEmail']
        user = users.query.get_or_404(targetID)
        if userCode == user.valid_code:
            setattr(user, 'valid_code', None)
            db.session.commit()
            server = smtplib.SMTP_SSL("smtp.gmail.com", 465)
            server.login("voterprojectclass@gmail.com", "Group1Project123!")
            server.sendmail("voterprojectclass@gmail.com", targetEmail, 
                                ("Hello,\n \nEmail verification was a success!\n \nVoterID: {}\n \nPlease wait for your account to be verified by one of our admins.\nAn email will be sent again when you're account has been verified,\nthen you will be able to login.\n\nBest,\nVoter Registration").format(targetID))
            server.quit()
            flash("Email verification sent. Please check email for next steps.")
            return render_template('verify_email.html', email=targetEmail, voterID=targetID)
        else:
            flash("*Incorrect code! Please try again.", 'error')
            return render_template('verify_email.html', email=targetEmail, voterID=targetID)
    else:
        target_email = request.args.get('email', None)
        targetID = request.args.get('voterID', None)
        return render_template('verify_email.html', email=target_email, voterID=targetID)

@app.route('/create_election.html', methods=['GET', 'POST'])
def create_election():
    if not checkAdmin():
        return '''ACCESS DENIED'''
    if request.method == 'POST':
        electionTitle = str(request.form['title'])
        electionPollingDate = str(request.form['polling-date'])
        electionPrecIDs = request.form.getlist('chosen-prec')
        start = str(request.form['start-time'])
        end = str(request.form['end-time'])
        if  len(electionPrecIDs) == 0 or (start[:2] == end[:2] and int(start[-2:]) >= int(end[-2:])) or int(start[:2]) > int(end[:2]):
            if len(electionPrecIDs) == 0:
                flash("*You must select at least one precinct for this election.")
            if (start[:2] == end[:2] and int(start[-2:]) >= int(end[-2:])) or int(start[:2]) > int(end[:2]):
                flash("*Start time must be before end time.")
            precs = precincts.query.all()
            return render_template('create_election.html', date=str(date.today()), precincts=precs, voterID=curVoterID)
        foo = elections.query.all()
        ids = []
        for elec in foo:
            ids.append(elec.election_id)
        if len(ids) == 0:
            thisID = 0
        else:
            thisID = ids[-1] + 1
        for id in electionPrecIDs:
            newPrecElec = election_precincts(thisID,id,0)
            db.session.add(newPrecElec)
            db.session.commit()
        elec = elections(thisID,
                         electionTitle,
                         electionPollingDate,
                         start,
                         end,
                         0,
                         0)
        db.session.add(elec)
        db.session.commit()
        return redirect(url_for('add_races', elecID=thisID))
    else:
        precs = precincts.query.all()
        return render_template('create_election.html', date=str(date.today()), precincts=precs, voterID=curVoterID)

@app.route('/add_races.html', methods=['GET', 'POST'])
def add_races():
    if not checkAdmin():
        return '''ACCESS DENIED'''
    if request.method == 'POST':
        if request.form['submit_button'] == 'Add Race':
            foo = races.query.all()
            ids = []
            for race in foo:
                ids.append(race.race_id)
            if len(ids) == 0:
                thisID = 0
            else:
                thisID = ids[-1] + 1
            rtitle = str(request.form['title'])
            rterm = str(request.form['term'])
            rcand1 = str(request.form['cand1'])
            rcand2 = str(request.form['cand2'])
            rcand3 = str(request.form['cand3'])
            rcand4 = str(request.form['cand4'])
            rcand5 = str(request.form['cand5'])
            racePrecIDs = request.form.getlist('chosen-prec')
            if rtitle == '' or rterm == '' or rcand1 == '' or rcand2 == '' or len(racePrecIDs) == 0:
                if rtitle == '':
                    flash("*Missing race title field.")
                if rterm == '':
                    flash("*Missing race term field.")
                if rcand1 == '':
                    flash("*Missing first candidate field.")
                if rcand2 == '':
                    flash("*Missing second candidate field.")
                if len(racePrecIDs) == 0:
                    flash("*You must select at least one precinct for this race.")
                elecID = int(request.form['elecID'])
                elec = elections.query.get_or_404(elecID)
                curRaces = races.query.filter_by(election_id=elecID).all()
                rPrecs = []
                for r in curRaces:
                    tmp = ""
                    precs = race_precincts.query.filter_by(race_id=r.race_id).all()
                    for precID in precs:
                        tmp += str(precID.precinct_id)
                        tmp += ", "
                    tmp = tmp[:-2]
                    rPrecs.append(tmp)
                precs = election_precincts.query.filter_by(election_id=elecID).all()
                availPrecincts = []
                for p in precs:
                    precID = p.precinct_id
                    availPrecincts.append(precincts.query.filter_by(precinct_id=precID).first())
                return render_template('add_races.html', elec=elec, races=curRaces, precincts=availPrecincts, racePrecs=rPrecs, voterID=curVoterID)
            if len(rcand3) == 0:
                rcand3 = None
            if len(rcand4) == 0:
                rcand4 = None
            if len(rcand5) == 0:
                rcand5 = None
            cands = [rcand1, rcand2, rcand3, rcand4, rcand5]
            for id in racePrecIDs:
                newPrecRace = race_precincts(thisID,id)
                db.session.add(newPrecRace)
                db.session.commit()
            elecID = int(request.form['elecID'])
            race = races(thisID,
                         rtitle,
                         rterm,
                         rcand1,
                         rcand2,
                         rcand3,
                         rcand4,
                         rcand5,
                         elecID,
                         0,
                         0,
                         0,
                         0,
                         0)
            db.session.add(race)
            db.session.commit()
            for c in cands: #add candidates to db
                if c != None:
                    newCand = candidates(c)
                    db.session.add(newCand)
                    db.session.commit()
            elec = elections.query.get_or_404(elecID)
            curRaces = races.query.filter_by(election_id=elecID).all()
            rPrecs = []
            for r in curRaces:
                tmp = ""
                precs = race_precincts.query.filter_by(race_id=r.race_id).all()
                for precID in precs:
                    tmp += str(precID.precinct_id)
                    tmp += ", "
                tmp = tmp[:-2]
                rPrecs.append(tmp)
            precs = election_precincts.query.filter_by(election_id=elecID).all()
            availPrecincts = []
            for p in precs:
                precID = p.precinct_id
                availPrecincts.append(precincts.query.filter_by(precinct_id=precID).first())
            return render_template('add_races.html', elec=elec, races=curRaces, precincts=availPrecincts, racePrecs=rPrecs, voterID=curVoterID)
        elif request.form['submit_button'] == 'Finished':
            db.session.commit()
            return render_template('/account_home.html',**createAccountDict(), voterID=curVoterID)
    else:
        elecID = request.args.get('elecID', None)
        precs = election_precincts.query.filter_by(election_id=elecID).all()
        availPrecincts = []
        for p in precs:
            precID = p.precinct_id
            availPrecincts.append(precincts.query.filter_by(precinct_id=precID).first())
        elec = elections.query.get_or_404(elecID)
        currRaces = []
        return render_template('add_races.html', elec=elec, races=currRaces, precincts=availPrecincts, racePrecs=[], voterID=curVoterID)

# @app.route('/vote_races.html', methods=['GET', 'POST'])
# def vote_races():
#     if request.method == 'POST':
#         pass
#     else:
#         voter = users.query.get_or_404(curVoterID)
#         zip_plus_4 = str(voter.zipcode)
#         zip = int(zip_plus_4[:5])
#         zip4 = int(zip_plus_4[5:])
#         ps = precincts.query.filter_by(zipcode=zip).all()
#         valid_ps = []
#         for p in ps:
#             zip_start = int(p.zip_4_start)
#             zip_end = int(p.zip_4_end)
#             if zip4 >= zip_start and zip4 <= zip_end:
#                 valid_ps.append(p)
#         return render_template('/vote_races.html')

@app.route('/account_home.html', methods=['GET', 'POST'])
def account_home():

    #All are passed to the render_template with **locals()
    #see function above
    try:
        user = users.query.filter_by(voterID=curVoterID).first()
        print(user.user_type)
        if user.user_type == 'pm':
            assocPrec = polling_managers.query.filter_by(voterID=curVoterID).first()
            if assocPrec:
                prec = precincts.query.filter_by(precinct_id=assocPrec.precinct_id).first()
            else:
                prec = "None"
        else:
            prec = "None"
        return render_template('account_home.html',**createAccountDict(),voterID=curVoterID,precinct=prec,pageTitle="Account Home Page")
    except AttributeError:
        return '''SESSION TIMED OUT'''

@app.route('/verify_accounts.html', methods=['GET', 'POST'])
def verify_accounts():
    accountstoverify = users.query.filter_by(verified='0').all()
    invalid = []
    if request.method == 'POST':
        if('submit' in request.form.keys()):
            print(request.form)
            for key in request.form:
                if(key != "submit"):
                    try:
                        if('a' in key):
                            key = key.removesuffix('a')
                            if(key+'b' in request.form ):
                                invalid.append(key)
                                
                            else:  
                                user = users.query.filter_by(voterID=key.removesuffix('b')).first()
                                user.verified = 1
                                try:
                                    server = smtplib.SMTP_SSL("smtp.gmail.com", 465)
                                    server.login("voterprojectclass@gmail.com", "Group1Project123!")
                                    server.sendmail("voterprojectclass@gmail.com", user.email, 
                                                ("Hello,\n \nYour account has been verified!\n\nYou can now login using your voterID sent in the previous email\nand your password at the time of account creation.\n\nBest,\nVoter Registration"))
                                    server.quit()
                                except (smtplib.SMTPRecipientsRefused):
                                    pass
                        elif('b' in key):
                            key = key.removesuffix('b')
                            if (key+'a' in request.form):
                                pass
                            else:
                                user = users.query.filter_by(voterID=key.removesuffix('b')).first()
                                try:
                                    server = smtplib.SMTP_SSL("smtp.gmail.com", 465)
                                    server.login("voterprojectclass@gmail.com", "Group1Project123!")
                                    server.sendmail("voterprojectclass@gmail.com", user.email, 
                                                ("Hello,\n \nYour account has been denied.\n\nPlease enter in correct information next time.\n\nBest,\nVoter Registration"))
                                    server.quit()
                                except(smtplib.SMTPRecipientsRefused):
                                    pass
                                users.query.filter_by(voterID=key).delete()
                    except AttributeError:
                        pass
                db.session.commit()
            if(invalid != []):
                flash("Can not verify and deny users with voterIds: "+ ",".join(invalid))
            return redirect(url_for('verify_accounts'))
    return render_template('verify_accounts.html', users=accountstoverify, pageTitle="Account Home Page", voterID=curVoterID)

@app.route('/search_user_database.html', methods=['GET', 'POST'])
def search_user_database():

    zipcode = ''
    firstname = ''
    lastname = ''
    usertype = ''
    precinct = ''
    if request.method == 'POST':
        ids = []
        zipcode = request.form['zipcode'].strip()
        firstname = request.form['first name'].strip()
        lastname = request.form['last name'].strip()
        usertype = request.form['user type'].strip()
        precinct = request.form['precinctid'].strip()

        if zipcode == '' and firstname == '' and lastname =='' and usertype == '' and precinct == '':
            return render_template('search_user_database.html', precinct=precinct,usertype=usertype, zipcode=zipcode, firstname=firstname, lastname=lastname, pageTitle="Search User Database", voterID=curVoterID) 
        else:
            ids = users.query
            if usertype != '':
                ids = ids.filter_by(user_type=usertype)
            if zipcode != '':
                ids = ids.filter(users.zipcode.contains(zipcode))
            if firstname != '':
                ids = ids.filter_by(first_name=firstname)
            if lastname != '':
                ids = ids.filter_by(last_name=lastname) 

            if precinct != '':
                p = precincts.query.filter_by(precinct_id=precinct).first()
                z = p.zipcode
                left = p.zip_4_start
                right = p.zip_4_end
                ids = [id for id in ids.all() if  left < int(id.zipcode.split("-")[1]) < right and int(id.zipcode.split("-")[0]) == z]
                return render_template('search_user_database.html', precinct=precinct,usertype=usertype, zipcode=zipcode, firstname=firstname, lastname=lastname, users=ids, pageTitle="Search User Database", voterID=curVoterID)
            else:

                ids = ids.all()
                return render_template('search_user_database.html', precinct=precinct,usertype=usertype, zipcode=zipcode, firstname=firstname, lastname=lastname, users=ids, pageTitle="Search User Database", voterID=curVoterID)
    else:
         return render_template('search_user_database.html', precinct=precinct,usertype=usertype,zipcode=zipcode, firstname=firstname, lastname=lastname, pageTitle="Search User Database", voterID=curVoterID)

@app.route('/assign_pm.html', methods=['GET', 'POST'])
def assign_pm():
    pcs = precincts.query.all()
    allPMs = users.query.filter_by(user_type='pm').all() #get all pms in user table
    availPMs = []
    tmp = 0
    for pm in allPMs:
        usr = polling_managers.query.filter_by(voterID=pm.voterID).first()
        if usr: #if found the user
            tmp = 0
        else:
            availPMs.append(pm.voterID)
    if request.method == 'POST':
        if request.form['submitBTN'] == 'Update':
            arr = request.form.getlist('PMSelect')
            newPMs = []
            for a in arr:
                if len(a) == 5:
                    a = a[1:]    
                newPMs.append(a)
            #create new PM
            precID = int(request.form['submit'])
            pmString = ""
            #check for existing PMs that need to be deleted
            prevPMS = polling_managers.query.filter_by(precinct_id=precID).all()
            tmp = 0
            for prev in prevPMS:
                for p in newPMs:
                    if p != "None":
                        if int(p) == prev.voterID:
                            tmp = 0
                            break
                        else:
                            tmp = 1
                    else:
                        tmp = 1
                        break
                if tmp == 1:
                    polling_managers.query.filter_by(voterID=prev.voterID).delete()
            for pm in newPMs:
                if pm != "None":
                    np = polling_managers.query.filter_by(voterID=int(pm)).first()
                    t = 0
                    if np:
                        pmString = pmString+pm+","
                        t=0
                    else:
                        pmString = pmString+pm+","
                        p = polling_managers(int(pm),precID)
                        db.session.add(p)
                        db.session.commit()
                else:
                    pmString = "None"
                    break
            #add to DB for polling managers and precints
            prec = precincts.query.filter_by(precinct_id=precID).first()
            prec.pooling_manager_id = pmString
            db.session.commit()
            flash("Successfully Added New Polling Manager(s)!","error")
            return redirect(url_for('assign_pm'))
        if request.form['submitBTN'] == 'Cancel':
            return render_template('assign_pm.html', precincts=pcs, pageTitle="Assign Polling Managers",voterID=curVoterID,availablePMs=json.dumps(availPMs))
    return render_template('assign_pm.html', precincts=pcs, pageTitle="Assign Polling Managers",voterID=curVoterID,availablePMs=json.dumps(availPMs))

@app.route('/account_change.html', methods=['GET', 'POST'])
def account_change():
    currentUser = users.query.get(curVoterID)
    if request.method == 'POST':
        userInfo = {}
        userInfo['email'] = str(request.form['email'])
        found_users = users.query.filter_by(email=userInfo['email']).all()
        found_user = False
        if (found_users == None):
            found_user = False
        else:
            try:
                found_users.remove(currentUser)
                if len(found_users) >0:
                    found_user = True
            except ValueError:
                if len(found_users) > 0:
                    found_user = True
                else:
                    found_user = False
            
        userInfo['state'] = str(request.form['state'])
        if (found_user or userInfo['state'] not in STATES):
            if found_user:
                flash("An account with that email already exists!", "error")
            if userInfo['state'] not in STATES:
                flash("Invalid state abbrevation!", "error")
            return render_template('account_change.html', currentUser=currentUser,pageTitle='Account Change Page',voterID=curVoterID)
        else:
            userInfo['firstName'] = str(request.form['fname'])
            userInfo['lastName'] = str(request.form['lname'])
            userInfo['age'] = int(request.form['age'])
            userInfo['address'] = str(request.form['addr'])
            userInfo['apartment'] = str(request.form['apt'])
            userInfo['city'] = str(request.form['city'])
            userInfo['zip'] = str(request.form['zip'])
            userInfo['driversLicenseNum'] = str(request.form['dlno'])
            currentUser.first_name         = userInfo['firstName']  
            currentUser.last_name          = userInfo['lastName']
            currentUser.age                = userInfo['age'],    
            currentUser.address            = userInfo['address'],  
            currentUser.address2           = userInfo['apartment'], 
            currentUser.city               = userInfo['city'], 
            currentUser.state              = userInfo['state'], 
            currentUser.zipcode            = userInfo['zip'],    
            currentUser.driver_license_num = userInfo['driversLicenseNum'],
            currentUser.email              = userInfo['email']
            db.session.commit()
            return redirect(url_for('account_home'))
    else:
        return render_template('account_change.html', pageTitle='Account Change Page', currentUser=currentUser, voterID=curVoterID)

@app.route('/vote_ballot.html/<voter_id>/<election_id>', methods=['GET', 'POST'])
def vote_ballot(voter_id, election_id):
    confirmation = 0
    user = users.query.filter_by(voterID=voter_id).first()
    precID = int(getAssocPrecinct(user.zipcode))
    raceIDs = race_precincts.query.filter_by(precinct_id=precID).all()
    elect = elections.query.filter_by(election_id=election_id).first()
    assocBallotInfo = election_precincts.query.filter(and_(election_precincts.election_id.like(int(election_id)),election_precincts.precinct_id.like(precID))).first()
    if request.method == 'POST':
        if request.form['submitBTN'] == 'Review Selections':
            ballotArray = []
            raced = []
            cands = []
            for key in request.form.items():
                if key[0] != 'submitBTN':
                    tmp = str(key[0]).split('.')
                    ballotArray.append(str(key[0]))
                    raceID = int(tmp[0])
                    raced.append(races.query.filter_by(race_id=raceID).first())
                    cands.append(tmp[1])
            ballotString = ",".join(ballotArray)
            confirmation = 1
            return render_template('vote_ballot.html', pageTitle='Vote Election', voterID=voter_id, election=elect, ballot=assocBallotInfo, electionID=election_id,conf=confirmation,races=raced,cands=cands,bal=ballotString)
        if request.form['submitBTN'] == 'Submit Ballot':
            tmpString = str(request.form['value'])
            tmpArray = tmpString.split(',')
            for a in tmpArray:
                arr = a.split('.')
                #send to ballotInfo db
                newBallotEntry = ballot_info(assocBallotInfo.ballot_id,voter_id,tmpString)
                db.session.add(newBallotEntry)
                db.session.commit()
                #update tallies in races db
                #get the race
                race = races.query.filter_by(race_id=int(arr[0])).first()
                if race.cand1 == str(arr[1]):
                    race.total1 = race.total1+1
                elif race.cand2 == str(arr[1]):
                    race.total2 = race.total2+1
                elif race.cand3 == str(arr[1]):
                    race.total3 = race.total3+1
                elif race.cand4 == str(arr[1]):
                    race.total4 = race.total4+1
                elif race.cand5 == str(arr[1]):
                    race.total5 = race.total5+1
                db.session.commit()
            #load new page
            confirmation = 2
            return render_template('vote_ballot.html', pageTitle='Vote Election', voterID=voter_id, conf=confirmation)
            #make sure voter can't vote again
    allRaces = []
    for r in raceIDs:
        race = races.query.filter_by(race_id=r.race_id).first()
        if race.election_id == int(election_id):
            allRaces.append(race)
    return render_template('vote_ballot.html', pageTitle='Vote Election', voterID=voter_id, election=elect, ballot=assocBallotInfo, races=allRaces, electionID=election_id,conf=confirmation)

@app.route('/password_recovery', methods=['GET', 'POST'])
def password_recovery():
    if request.method == 'POST':
        voter_id = int(request.form['voter_Id'])
        isVoter = users.query.filter_by(voterID=voter_id).first() # also query email column
        if isVoter:
            access_code = random.randint(10000,99999)

            isVoter.access_code = access_code
            db.session.commit()

            server = smtplib.SMTP_SSL("smtp.gmail.com", 465)
            server.login("voterprojectclass@gmail.com", "Group1Project123!")
            server.sendmail("voterprojectclass@gmail.com", isVoter.email, 
                                ("Hello,\n \nAccess Code: {}\n \nBest,\nVoter Registration").format(access_code))
            server.quit()
            # go to access code page
            return redirect(url_for('access_code_page'))
        else:
            flash("Incorrect Voter ID. Try Again!", 'error')
            return redirect(url_for('password_recovery'))
    return render_template('password_recovery.html', pageTitle='Password Recovery Page')

@app.route('/access_code', methods=['GET','POST'])
def access_code_page():
    if request.method == 'POST':
        user_email = request.form['email']
        code = int(request.form['code'])
        isCode = users.query.filter_by(email=user_email,access_code=code).first()
        if isCode:
            return redirect(url_for('password_reset', access_code=code, user_email=user_email))
        else:
            flash("Incorrect Access Code. Try Again!", category='error')
            return redirect(url_for('access_code_page'))
    else:
        return render_template('access_code.html', pageTitle='Access Code Page')

@app.route('/password_reset/<access_code>/<user_email>', methods=['GET','POST'])
def password_reset(access_code, user_email):
    if request.method == 'POST':
        passOne = request.form['pass']
        passTwo = request.form['passTwo']

        if (passOne == passTwo and isPasswordStrong(passTwo)):
            # query row which holds users password
            #hash the password
            new_pwd = hashlib.sha256(passTwo.encode('utf-8')).hexdigest()
            row = users.query.filter_by(email=user_email, access_code=access_code).first()
            row.password = new_pwd
            row.pwd = passTwo
            row.access_code = None
            db.session.commit()
            return(redirect(url_for('login_page')))
        else:
            if (passOne != passTwo):
                flash("Password's Do Not Match. Try Again!")
            elif(not isPasswordStrong(passTwo)):
                flash("Password needs to be 8 characters and must have numbers in them. Try Again!")
            return redirect(url_for('password_reset', access_code=access_code, user_email=user_email))
    return render_template('password_reset.html', access_code=access_code, user_email=user_email, pageTitle='Password Reset Page')

if __name__ == '__main__':
    app.run(debug=True)