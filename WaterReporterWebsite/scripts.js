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
var waterReportsDB = db.ref('waterReports');
var purityReportsDB = db.ref('waterPurityReports');
var usertypes = ['USER', 'WORKER', 'MANAGER', 'ADMINISTRATOR'];

// function initializeSingleton() {
// 	instance.registeredUserSet = getUsers();
// 	instance.reportList = getWaterReports();
// 	instance.purityReportList = getPurityReports();
// }

function singleton() {
	this.users = getUsers();
	// this.users = new Set([]);
	// this.registeredUserSet = new Set([]);
	// this.registeredUserMap = [];
	this.reportList = getWaterReports();
	this.purityReportList = getPurityReports();
	this.user = {};
}

function getInstance() {
	return instance;
}

// usersDB.on('value', function(snapshot) {
// 	snapshot.forEach(function(childSnapshot) {
// 		var childData = childSnapshot.val();
// 		instance.registeredUserSet.add(childData);
// 	});
// });
function getUser() {
	return instance.user;
}

function setUserOnSignup(user) {
	if (checkSignup(user)) {
		instance.user = user;
		return true;
	}
	return false;
}

function checkSignup(user) {
	return isEmailValid(user['email'])
		&& Number.isInteger(user['id'])
		&& isPasswordValid(user['password'])
		&& isAddressValid(user['address'])
		&& isUsertypeValid(user['userType'])
		&& isUsernameValid(user['username'])
		&& !userExists(user);
}

function userExists(user) {
	users.forEach(function(u) {
		if (user['email'] === u['email']
			|| user['id'] === u['id']
			|| user['username'] === u['username']) {
			return true;
		}
	});
	return false;
}

function getUsers() {
	var newUsers = [];
	usersDB.on('value', function(snapshot) {
		snapshot.forEach(function(childSnapshot) {
			var childData = childSnapshot.val();
			newUsers.push(childData);
		});
	});
	instance.users = newUsers;
	return instance.users;
	// instance.registeredUserSet = newUsers;
	// return instance.registeredUserSet;
}

function getWaterReports() {
	var newWater = [];
	waterReportsDB.on('value', function(snapshot) {
		snapshot.forEach(function(childSnapshot) {
			var childData = childSnapshot.val();
			newWater.push(childData);
		});
	});
	instance.reportList = newWater;
	return instance.reportList;
}

function getPurityReports() {
	var newPurity = [];
	purityReportsDB.on('value', function(snapshot) {
		snapshot.forEach(function(childSnapshot) {
			var childData = childSnapshot.val();
			newPurity.push(childData);
		});
	});
	instance.purityReportList = newPurity;
	return instance.purityReportList;
}

function addUser(eMail, usrn, pswd, addr, typeOfUser) {
	var newUser = { email: eMail,
					username: usrn,
					password: pswd,
					homeAddress: addr,
					userType: typeOfUser,
					id: users.length };
	if (checkSignup(newUser)) {
		var updates = {};
		updates[iD] = newUser;

		usersDB.update(updates);
		return true;
	}
	return false;
	// var newPostKey = usersDB.push().key;
}

function addWaterPurityReport(dateTime, addressStr, usrn,
				reportNum, ppmVirus, ppmContam, waterCondition) {
	var newPurity = { dateAndTime: dateTime,
					address: addressStr,
					username: usrn,
					reportNumber: reportNum,
					virusPPM: ppmVirus,
					contaminantPPM: ppmContam,
					conditionOfWater: waterCondition };

	// var newPostKey = purityReportsDB.push().key;

	var updates = {};
	updates[reportNum] = newPurity;

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

	// var newPostKey = waterReportsDB.push().key;

	var updates = {};
	updates[reportNum] = newWater;

	waterReportsDB.update(updates);
}

function isEmailValid(email) {
	return (email != null && email.includes("@") && email.includes(".") && email.length >= 4);
}

function isAddressValid(address) {
	return true;
}

function isUsernameValid(username) {
    return (username != null && username != "" && username.length >= 5);
}

function isUserTypeValid(usertype) {
	return (usertypes.indexOf(usertype) != -1);
}

function isPasswordValid(password) {
	return (password.length >= 4 && password.test(".*\\d+.*") && password != password.toLowerCase());
}

function findWaterReportById(id) {
	if (id < 0) {
	    return null;
	}

	getWaterReports().forEach(function(report) {
		if (report != null && report['reportNumber'] === id) {
			return report;
		}
	});

	return null;
}

// public Address findAddressFromName(String address, Geocoder geocoder) throws IOException {
//     List<Address> addrList = geocoder.getFromLocationName(address, 1);
//     if (addrList.size() > 0) {
//         return addrList.get(0);
//     }
//     throw new IOException();
// }

// public HashMap<Integer, Double> getCPPMGraphPoints(String location, String year) { };
// public HashMap<Integer, Double> getVPPMGraphPoints(String location, String year) { };