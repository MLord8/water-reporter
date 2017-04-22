// Initialize Firebase
var config = {
	apiKey: "AIzaSyAd45R67ts78jB2xXMKJ4n2YoD2xXU2y7M",
	authDomain: "https://harambe-loved-waters-reporter.firebaseapp.com",
	databaseURL: "https://harambe-loved-waters-reporter.firebaseio.com",
};
firebase.initializeApp(config);

var db = firebase.database();
var instance = new singleton();
var usersDB = db.ref('users');
var waterReportsDB = db.ref('waterReports')
var purityReportsDB = db.ref('waterPurityReports')

instance.registeredUserSet = getUsers();
instance.reportList = getWaterReports();
instance.purityReportList = getPurityReports();

usersDB.on('value', function(snapshot) {
	snapshot.forEach(function(childSnapshot) {
		var childData = childSnapshot.val();
		instance.registeredUserSet.add(childData);
	});
});

waterReportsDB.on('value', function(snapshot) {
	snapshot.forEach(function(childSnapshot) {
		var childData = childSnapshot.val();
		instance.reportList.push(childData);
	});
});

purityReportsDB.on('value', function(snapshot) {
	snapshot.forEach(function(childSnapshot) {
		var childData = childSnapshot.val();
		instance.purityReportList.push(childData);
	});
});

function getUsers() {
	usersDB.on('value', function(snapshot) {
		snapshot.forEach(function(childSnapshot) {
			var childData = childSnapshot.val();
			instance.registeredUserSet.add(childData);
		});
	});
	return instance.registeredUserSet;
}

function getWaterReports() {
	return instance.reportList;
}

function getPurityReports() {
	return instance.purityReportList;
}

function addUser(eMail, usrn, pswd, addr, typeOfUser, iD) {
	var newUser = { email: eMail,
					username: usrn,
					password: pswd,
					homeAddress: addr,
					userType: typeOfUser,
					id: iD };

	var newPostKey = usersDB.push().key;

	var updates = {};
	updates['/waterReports/' + newPostKey] = newReport;

	usersDB.update(updates);
}

function addPurityReport(dateTime, addressStr, usrn,
				reportNum, ppmVirus, ppmContam, waterCondition) {
	var newPurity = { dateAndTime: dateTime,
					address: addressStr,
					username: usrn,
					reportNumber: reportNum,
					virusPPM: ppmVirus,
					contaminantPPM: ppmContam,
					conditionOfWater: waterCondition };

	var newPostKey = purityReportsDB.push().key;

	var updates = {};
	updates['/waterReports/' + newPostKey] = newPurity;

	purityReportsDB.update(updates);
}

function addWaterReport(dateTime, address, usrn,
				reportNum, waterType, waterCondition) {
	var newWater = { dateAndTime: dateTime,
					addressStr: address,
					username: usrn,
					reportNumber: reportNum,
					typeOfWater: waterType,
					conditionOfWater: waterCondition };

	var newPostKey = waterReportsDB.push().key;

	var updates = {};
	updates['/waterReports/' + newPostKey] = newWater;

	waterReportsDB.update(updates);
}

function singleton() {
	this.registeredUserSet = new Set([]);
	this.registeredUserMap = [];
	this.reportList = [];
	this.purityReportList = [];
	this.currUser = {};
}
