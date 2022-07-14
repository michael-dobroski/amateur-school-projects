# import all required frameworks
from asyncore import poll
from email.policy import default
from enum import unique
from multiprocessing.sharedctypes import Value
from pickle import FALSE
from unittest import result
from unittest.util import strclass
from flask import Flask, render_template, session, url_for, request, redirect, flash
from sqlalchemy import and_, true
import random
from datetime import datetime, date

from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.sql import func

import unittest
from selenium import webdriver
from selenium.webdriver import Chrome
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support import expected_conditions as ec
from selenium.webdriver.support.wait import WebDriverWait
 
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

# inherit TestCase Class and create a new test class
class PythonOrgSearch(unittest.TestCase):
 
    # initialization of webdriver
    def setUp(self):
        self.driver = webdriver.Chrome()
 
    # Test case method. It should always start with test_
    def test_index(self):
        # get driver
        driver = self.driver
        # get index.html using selenium
        driver.get('http://localhost:5000/index.html')
        assert "Index Not Found" not in driver.page_source
    
    def test_account_create(self):
        first_name = "Mike"
        last_name = "Dobroski"
        age = 69
        address = "113 E Prentiss St"
        address2 = "APT 3"
        city = "Iowa City"
        state = "IA"
        zip = "52240-1002"
        dlno = "859402943"
        email = "testemail"
        email += str(random.random())[-14:-1]
        email += "@testing.com"
        pwd = "Password1"
        driver = self.driver
        driver.get('http://localhost:5000/account_create.html')
        assert "Account Create Not Found" not in driver.page_source
        form = driver.find_element_by_id("CreateAccountForm")
        first_name_field = driver.find_element_by_id("fname")
        last_name_field = driver.find_element_by_id("lname")
        age_field = driver.find_element_by_id("age")
        address_field = driver.find_element_by_id("addr")
        address2_field = driver.find_element_by_id("apt")
        city_field = driver.find_element_by_id("city")
        state_field = driver.find_element_by_id("state")
        zip_field = driver.find_element_by_id("zip")
        dlno_field = driver.find_element_by_id("dlno")
        email_field = driver.find_element_by_id("email")
        pwd_field = driver.find_element_by_id("pwd")
        pwd2_field = driver.find_element_by_id("pwd2")
        first_name_field.send_keys(first_name)
        last_name_field.send_keys(last_name)
        age_field.send_keys(age)
        address_field.send_keys(address)
        address2_field.send_keys(address2)
        city_field.send_keys(city)
        state_field.send_keys(state)
        zip_field.send_keys(zip)
        dlno_field.send_keys(dlno)
        email_field.send_keys(email)
        pwd_field.send_keys(pwd)
        pwd2_field.send_keys(pwd)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/verify_email.html'), 'Timed out waiting for response')
        assert "Verify Email Page Found" not in driver.page_source
        vc = users.query.filter_by(email=email).first().valid_code
        form = driver.find_element_by_id("VerifyEmailForm")
        vc_field = driver.find_element_by_id("vcode")
        vc_field.send_keys(vc)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/verify_email.html'), 'Timed out waiting for response')
        flash_msg = driver.find_element_by_class_name("flash").text
        assert flash_msg == "Email verification sent. Please check email for next steps." # success

    #Login Usecase DONE
    def test_login(self):
        # get driver
        username = "9468"
        pwd = "Password1"
        driver = self.driver
        driver.get('http://localhost:5000/login_page.html') # get index.html using selenium
        assert "Login Not Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("userID")
        password = driver.find_element_by_name("password")
        name.send_keys(username)
        password.send_keys(pwd)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/login_access_code.html'),
                                     'Timed out waiting for response')
        assert "Login Access Page Found" not in driver.page_source
        ac = users.query.filter_by(voterID=9468).first().access_code
        driver = self.driver
        driver.get('http://localhost:5000/login_access_code.html') # get index.html using selenium
        assert "Login Access Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("accessCode")
        name.send_keys(ac)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source

    #TODO Put change of address here
    def test_change_address(self):
        username = "9468"
        pwd = "Password1"
        driver = self.driver
        driver.get('http://localhost:5000/login_page.html') # get index.html using selenium
        assert "Login Not Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("userID")
        password = driver.find_element_by_name("password")
        name.send_keys(username)
        password.send_keys(pwd)
        form.submit()
        db.session.commit()
        WebDriverWait(driver, 12).until(ec.url_matches('/login_access_code.html'),
                                     'Timed out waiting for response')
        assert "Login Access Page Found" not in driver.page_source
        driver.get('http://localhost:5000/login_access_code.html') # get index.html using selenium
        assert "Login Access Found" not in driver.page_source
        ac = users.query.filter_by(voterID=9468).first().access_code
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("accessCode")
        name.send_keys(ac)
        form.submit()
        db.session.commit()
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source
        first_name = "Connor"
        last_name = "Hoeger"
        age = 69
        address = "246 S Right St"
        address2 = "APT 7"
        city = "Iowa City"
        state = "IA"
        zip = "52248-1002"
        dlno = "859402943"
        email = "testemail"
        email += str(random.random())[-14:-1]
        email += "@testing.com"
        driver = self.driver
        driver.get('http://localhost:5000/account_change.html')
        assert "Account Change Not Found" not in driver.page_source
        form = driver.find_element_by_id("accountchange")
        first_name_field = driver.find_element_by_id("fname")
        last_name_field = driver.find_element_by_id("lname")
        age_field = driver.find_element_by_id("age")
        address_field = driver.find_element_by_id("addr")
        address2_field = driver.find_element_by_id("apt")
        city_field = driver.find_element_by_id("city")
        state_field = driver.find_element_by_id("state")
        zip_field = driver.find_element_by_id("zip")
        dlno_field = driver.find_element_by_id("dlno")
        email_field = driver.find_element_by_id("email")
        first_name_field.send_keys(first_name)
        last_name_field.send_keys(last_name)
        age_field.send_keys(age)
        address_field.send_keys(address)
        address2_field.send_keys(address2)
        city_field.send_keys(city)
        state_field.send_keys(state)
        zip_field.send_keys(zip)
        dlno_field.send_keys(dlno)
        email_field.send_keys(email)
        form.submit()
        


    #TODO Put approve user profile here
    def test_verify_accounts(self):
        username = "9468"
        pwd = "Password1"
        driver = self.driver
        driver.get('http://localhost:5000/login_page.html') # get index.html using selenium
        assert "Login Not Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("userID")
        password = driver.find_element_by_name("password")
        name.send_keys(username)
        password.send_keys(pwd)
        form.submit()
        db.session.commit()
        WebDriverWait(driver, 12).until(ec.url_matches('/login_access_code.html'),
                                     'Timed out waiting for response')
        assert "Login Access Page Found" not in driver.page_source
        driver.get('http://localhost:5000/login_access_code.html') # get index.html using selenium
        assert "Login Access Found" not in driver.page_source
        ac = users.query.filter_by(voterID=9468).first().access_code
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("accessCode")
        name.send_keys(ac)
        form.submit()
        db.session.commit()
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source
        driver = self.driver
        driver.get('http://localhost:5000/verify_account.html')
        WebDriverWait(driver, 12).until(ec.url_matches('/verify_account.html'), 'Timed out waiting for response')
        assert "Verify Accounts Not Found" not in driver.page_source

        driver.get("http://localhost:5000/account_home.html")
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source

    #TODO Put search user dB here
    def test_search_DB(self):
        username = "9468"
        pwd = "Password1"
        driver = self.driver
        driver.get('http://localhost:5000/login_page.html') # get index.html using selenium
        assert "Login Not Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("userID")
        password = driver.find_element_by_name("password")
        name.send_keys(username)
        password.send_keys(pwd)
        form.submit()
        db.session.commit()
        WebDriverWait(driver, 12).until(ec.url_matches('/login_access_code.html'),
                                     'Timed out waiting for response')
        assert "Login Access Page Found" not in driver.page_source
        driver.get('http://localhost:5000/login_access_code.html') # get index.html using selenium
        assert "Login Access Found" not in driver.page_source
        ac = users.query.filter_by(voterID=9468).first().access_code
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("accessCode")
        name.send_keys(ac)
        form.submit()
        db.session.commit()
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source
        first_name = "Connor"
        Last_name = "Hoeger"
        zip_code = "52245"
        usertype = "admin"
        precinctid = "4"

        driver = self.driver
        driver.get('http://localhost:5000/search_user_database.html')
        form = driver.find_element_by_id("search")
        searchzip = driver.find_element_by_name("zipcode")
        searchfst = driver.find_element_by_name("first name")
        searchlst = driver.find_element_by_name("last name")
        searchtyp = driver.find_element_by_name("user type")
        searchprc = driver.find_element_by_name("precinctid")
        searchzip.send_keys(zip_code)
        searchfst.send_keys(first_name)
        searchlst.send_keys(Last_name)
        searchtyp.send_keys(usertype)
        searchprc.send_keys(precinctid)
        form.submit()
        assert "Search User DB Not Found" not in driver.page_source
        driver.get('http://localhost:5000/account_home.html')

        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source

    #TODO Put Setup Election/Races Here
    def test_create_elections(self):

        # login first
        username = "9468"
        pwd = "Password1"
        driver = self.driver
        driver.maximize_window()
        driver.get('http://localhost:5000/login_page.html') # get index.html using selenium
        assert "Login Not Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("userID")
        password = driver.find_element_by_name("password")
        name.send_keys(username)
        password.send_keys(pwd)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/login_access_code.html'),
                                     'Timed out waiting for response')
        assert "Login Access Page Found" not in driver.page_source
        ac = users.query.filter_by(voterID=9468).first().access_code
        driver = self.driver
        driver.get('http://localhost:5000/login_access_code.html') # get index.html using selenium
        assert "Login Access Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("accessCode")
        name.send_keys(ac)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source
        
        # nav to create election
        driver.find_element_by_link_text("Create Election/Races").click()
        WebDriverWait(driver, 12).until(ec.url_matches('/create_election.html'),
                                     'Timed out waiting for response')
        assert "Create Election Not Found" not in driver.page_source

        # create election
        title = "~testelection#"
        title += str(random.random())[-14:-1]
        polling_date = str(date.today())[5:7]+str(date.today())[8:10]+str(date.today())[0:4]
        start_time = "800A"
        end_time = "500P"
        title_field = driver.find_element_by_name("title")
        polling_date_field = driver.find_element_by_name("polling-date")
        start_time_field = driver.find_element_by_name("start-time")
        end_time_field = driver.find_element_by_name("end-time")
        form = driver.find_element_by_id("CreateElectionForm")
        title_field.send_keys(title)
        polling_date_field.send_keys(polling_date)
        start_time_field.send_keys(start_time)
        end_time_field.send_keys(end_time)
        driver.find_element_by_name("chosen-prec").click()
        form.submit()

        # add races
        WebDriverWait(driver, 12).until(ec.url_matches('/add_races.html'),
                                     'Timed out waiting for response')
        assert "Add Races Page Not Found" not in driver.page_source
        title = "US Senate-1"
        term = "2022-2024"
        cand1 = "Raman#"
        cand1 += str(random.random())[-14:-1]
        cand2 = "Junnan#"
        cand2 += str(random.random())[-14:-1]
        title_field = driver.find_element_by_name("title")
        term_field = driver.find_element_by_name("term")
        cand1_field = driver.find_element_by_name("cand1")
        cand2_field = driver.find_element_by_name("cand2")
        title_field.send_keys(title)
        term_field.send_keys(term)
        cand1_field.send_keys(cand1)
        cand2_field.send_keys(cand2)
        driver.find_element_by_name("chosen-prec").click()
        driver.find_element_by_id("add-race").click()
        WebDriverWait(driver, 12).until(ec.url_matches('/add_races.html'),
                                     'Timed out waiting for response')
        assert "Add Races Page Not Found" not in driver.page_source
        driver.find_element_by_id("sub-elec").click()
        driver.get('http://localhost:5000/account_home.html') 
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source

    #TODO Put precinct info here
    def test_create_precinct(self):
        driver = self.driver
        driver.get('http://localhost:5000/create_precinct.html')
        assert "Create Precinct Not Found" not in driver.page_source
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source

    #TODO Create Ballot
    def test_create_ballot(self):
         # get driver
        username = "2000"
        pwd = "Password1"
        driver = self.driver
        driver.get('http://localhost:5000/login_page.html') # get index.html using selenium
        assert "Login Not Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("userID")
        password = driver.find_element_by_name("password")
        name.send_keys(username)
        password.send_keys(pwd)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/login_access_code.html'),
                                     'Timed out waiting for response')
        assert "Login Access Page Found" not in driver.page_source
        ac = users.query.filter_by(voterID=9468).first().access_code
        driver = self.driver
        driver.get('http://localhost:5000/login_access_code.html') # get index.html using selenium
        assert "Login Access Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("accessCode")
        name.send_keys(ac)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source

        


        driver = self.driver
        driver.get('http://localhost:5000/view_ballot.html')
        assert "View Ballot Not Found" not in driver.page_source
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source

    #TODO Assign Manager to polling station
    def test_assign_pm(self):
        username = "9468"
        pwd = "Password1"
        driver = self.driver
        driver.get('http://localhost:5000/login_page.html') # get index.html using selenium
        assert "Login Not Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("userID")
        password = driver.find_element_by_name("password")
        name.send_keys(username)
        password.send_keys(pwd)
        form.submit()
        db.session.commit()
        WebDriverWait(driver, 12).until(ec.url_matches('/login_access_code.html'),
                                     'Timed out waiting for response')
        assert "Login Access Page Found" not in driver.page_source
        driver.get('http://localhost:5000/login_access_code.html') # get index.html using selenium
        assert "Login Access Found" not in driver.page_source
        ac = users.query.filter_by(voterID=9468).first().access_code
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("accessCode")
        name.send_keys(ac)
        form.submit()
        db.session.commit()
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source
        driver = self.driver
        driver.get('http://localhost:5000/assign_pm.html')
        driver.get('http://localhost:5000/account_home.html')
        assert "Assign Polling Manager Not Found" not in driver.page_source
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source
    
    #TODO Change Password Done
    def test_change_password(self):
        # get driver
        username = 9803
        pwd = "Password2"
        email = 'emailnihaal@gmail.com'
        driver = self.driver
        driver.get('http://localhost:5000/password_recovery') # get index.html using selenium
        assert "Login Not Found" not in driver.page_source
        form = driver.find_element_by_id("password_recovery")
        name = driver.find_element_by_name("voter_Id")
        name.send_keys(username)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/access_code'),
                                     'Timed out waiting for response')
        assert "Login Access Page Found" not in driver.page_source

        ac = users.query.filter_by(voterID=9803).first().access_code
        driver = self.driver
        driver.get('http://localhost:5000/access_code') # get index.html using selenium
        assert "Login Access Found" not in driver.page_source
        form = driver.find_element_by_id("access_code")
        email_in = driver.find_element_by_name("email")
        access_code = driver.find_element_by_name("code")
        email_in.send_keys(email)
        access_code = send_keys(ac)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/password_reset.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source
        
        driver = self.driver
        driver.get('http://localhost:5000/password_reset.html') # get index.html using selenium
        assert "Login Access Found" not in driver.page_source
        form = driver.find_element_by_id("reset")
        passOne = driver.find_element_by_name("pass")
        passTwo = driver.find_element_by_name("passTwo")
        passOne.send_keys(pwd)
        passOne.send_keys(pwd)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source
    
    #Logout usecase DONE
    def test_logout(self):
        driver = self.driver
        btn = driver.find_element_by_id("logBTN")
        btn.click()
        WebDriverWait(driver, 12).until(ec.url_matches('/logout.html'),
                                     'Timed out waiting for response')
        assert "Logout Not Found" not in driver.page_source
        driver.get('http://localhost:5000/logout.html')
        assert "Logout Not Found" not in driver.page_source
        lbtn = driver.find_element_by_id("logout")
        lbtn.click()
        WebDriverWait(driver, 12).until(ec.url_matches('/index.html'),
                                     'Timed out waiting for response')
        assert "Index Not Found" not in driver.page_source                 
        # get driver
        username = "1789"
        pwd = "Password1"
        driver = self.driver
        driver.get('http://localhost:5000/login_page.html') # get index.html using selenium
        assert "Login Not Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("userID")
        password = driver.find_element_by_name("password")
        name.send_keys(username)
        password.send_keys(pwd)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/login_access_code.html'),
                                     'Timed out waiting for response')
        assert "Login Access Page Found" not in driver.page_source
        ac = users.query.filter_by(voterID=9468).first().access_code
        driver = self.driver
        driver.get('http://localhost:5000/login_access_code.html') # get index.html using selenium
        assert "Login Access Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("accessCode")
        name.send_keys(ac)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Found" not in driver.page_source

    #TODO Activate or Deactivate Ballot
    def test_activate_ballot(self):
        # get driver
        username = "9468"
        pwd = "Password1"
        driver = self.driver
        driver.get('http://localhost:5000/login_page.html') # get index.html using selenium
        assert "Login Not Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("userID")
        password = driver.find_element_by_name("password")
        name.send_keys(username)
        password.send_keys(pwd)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/login_access_code.html'),
                                     'Timed out waiting for response')
        assert "Login Access Page Found" not in driver.page_source
        ac = users.query.filter_by(voterID=9468).first().access_code
        driver = self.driver
        driver.get('http://localhost:5000/login_access_code.html') # get index.html using selenium
        assert "Login Access Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("accessCode")
        name.send_keys(ac)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source

        driver = self.driver
        driver.get('http://localhost:5000/view_election.html')
        assert "View Election for activate Not Found" not in driver.page_source
        activateBTN=driver.find_element_by_id("btn")
        activateBTN.click()
        WebDriverWait(driver, 12).until(ec.url_matches('/view_election.html'),
                                     'Timed out waiting for response')
        assert "View Election Not Found" not in driver.page_source

    #TODO Declare Election
    def test_declare_election(self):
        driver = self.driver
        driver.get('http://localhost:5000/view_election.html')
        assert "View Election for activate Not Found" not in driver.page_source
        BTN=driver.find_element_by_id("dec")
        BTN.click()
        WebDriverWait(driver, 12).until(ec.url_matches('/declare_election.html'),
                                     'Timed out waiting for response')
        assert "Declare Election Not Found" not in driver.page_source
        driver.get('http://localhost:5000/declare_election.html')
        assert "Declare Election Not Found" not in driver.page_source
        BTN=driver.find_element_by_id("dec")
        BTN.click()
        WebDriverWait(driver, 12).until(ec.url_matches('/declare_election.html'),
                                     'Timed out waiting for response')
        assert "Declare Election Not Found" not in driver.page_source

    #Logout usecase DONE
    def test_logout_again(self):
        driver = self.driver
        btn = driver.find_element_by_id("logBTN")
        btn.click()
        WebDriverWait(driver, 12).until(ec.url_matches('/logout.html'),
                                     'Timed out waiting for response')
        assert "Logout Not Found" not in driver.page_source
        driver.get('http://localhost:5000/logout.html')
        assert "Logout Not Found" not in driver.page_source
        lbtn = driver.find_element_by_id("logout")
        lbtn.click()
        WebDriverWait(driver, 12).until(ec.url_matches('/index.html'),
                                     'Timed out waiting for response')
        assert "Index Not Found" not in driver.page_source                 
        # get driver
        username = "1780"
        pwd = "Password1"
        driver = self.driver
        driver.get('http://localhost:5000/login_page.html') # get index.html using selenium
        assert "Login Not Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("userID")
        password = driver.find_element_by_name("password")
        name.send_keys(username)
        password.send_keys(pwd)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/login_access_code.html'),
                                     'Timed out waiting for response')
        assert "Login Access Page Found" not in driver.page_source
        ac = users.query.filter_by(voterID=9468).first().access_code
        driver = self.driver
        driver.get('http://localhost:5000/login_access_code.html') # get index.html using selenium
        assert "Login Access Found" not in driver.page_source
        form = driver.find_element_by_id("LoginForm")
        name = driver.find_element_by_name("accessCode")
        name.send_keys(ac)
        form.submit()
        WebDriverWait(driver, 12).until(ec.url_matches('/account_home.html'),
                                     'Timed out waiting for response')
        assert "Account Home Page Not Found" not in driver.page_source

    #TODO Vote in election
    def test_vote_in_election(self):
        driver = self.driver
        driver.get('http://localhost:5000/view_election.html')
        assert "View Election for activate Not Found" not in driver.page_source
        link = driver.find_element_by_id("voteLink")
        link.click()
        WebDriverWait(driver, 12).until(ec.url_matches('/vote_ballot.html'),
                                     'Timed out waiting for response')
        assert "Vote Ballot Not Found" not in driver.page_source

    #TODO View Results
    def test_view_results(self):
        driver = self.driver
        driver.get('http://localhost:5000/account_home.html')
        assert "Account Home Page Not Found" not in driver.page_source

        
    # cleanup method called after every test performed
    def tearDown(self):
        self.driver.close()
 
# execute the script
if __name__ == "__main__":
    unittest.main()