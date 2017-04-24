// Initialize Firebase
var config = {
	apiKey: "AIzaSyAd45R67ts78jB2xXMKJ4n2YoD2xXU2y7M",
	authDomain: "https://harambe-loved-waters-reporter.firebaseapp.com",
	databaseURL: "https://harambe-loved-waters-reporter.firebaseio.com",
};
firebase.initializeApp(config);

var db = firebase.database();

var usersDB = db.ref('users');
var waterReportsDB = db.ref('waterReports');
var purityReportsDB = db.ref('waterPurityReports');
var usertypes = ['USER', 'WORKER', 'MANAGER', 'ADMINISTRATOR'];
var waterTypes = ['Bottled', 'Well', 'Stream', 'Lake', 'Spring'];
var waterConditions = ['Treatable-Clear', 'Treatable-Muddy', 'Potable', 'Waste'];
var purityConditions = ['Safe', 'Treatable', 'Unsafe'];

var instance = new singleton();

// function initializeSingleton() {
instance.registeredUserSet = getUsers();
instance.reportList = getWaterReports();
instance.purityReportList = getPurityReports();
// }

function singleton() {
	this.users = [];
	// this.users = new Set([]);
	// this.registeredUserSet = new Set([]);
	// this.registeredUserMap = [];
	this.reportList = [];
	this.purityReportList = [];
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
	instance.user = JSON.parse(sessionStorage.getItem('currentUser'));
	return instance.user;
}

function setUserOnSignup(user) {
	if (checkSignup(user)) {
		instance.user = user;
		return true;
	}
	return false;
}

function setUser(user) {
	instance.user = user;
	console.log("User set! " + user + "\n");
}

function checkSignup(user) {
	return isEmailValid(user['email'])
		&& Number.isInteger(user['id'])
		&& isPasswordValid(user['password'])
		&& isAddressValid(user['address'])
		&& isUserTypeValid(user['userType'])
		&& isUsernameValid(user['username'])
		&& !userExists(user);
}

function userExists(user) {
	instance.users.forEach(function(u) {
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
					id: instance.users.length };
	if (checkSignup(newUser)) {
		var updates = {};
		updates[newUser['id']] = newUser;

		usersDB.update(updates);
		alert("Added a user!");
		return true;
	}
	return false;
	// var newPostKey = usersDB.push().key;
}

function addWaterReport(address, waterType, waterCondition) {
	var reps = getWaterReports();
	var newWater = { dateAndTime: getCurrentDateTime(),
					addressStr: address,
					username: getUser(),
					reportNumber: reps[reps.length - 1]['reportNumber'] + 1,
					typeOfWater: waterType,
					conditionOfWater: waterCondition };
	console.log("In water report adder");
	// var bool = isAddressValid(address);
		// && isWaterTypeValid(waterType)
		// && isWaterConditionValid(waterCondition);
	// console.log(bool);
	// var newPostKey = waterReportsDB.push().key;
	// if (isAddressValid(address) && 
	if (isWaterTypeValid(waterType)
		&& isWaterConditionValid(waterCondition)) {
		console.log("in here");
		var updates = {};
		updates[newWater['reportNumber']] = newWater;
		console.log('new num: ' + newWater['reportNumber']);
		waterReportsDB.update(updates);
		alert("Added a water report!");
		return true;
	}
	return false;
}

function addWaterPurityReport(addressStr, ppmVirus, ppmContam, waterCondition) {

	var newPurity = { dateAndTime: getCurrentDateTime(),
					address: addressStr,
					username: getUser(),
					reportNumber: instance.purityReportList,
					virusPPM: ppmVirus,
					contaminantPPM: ppmContam,
					conditionOfWater: waterCondition };

	// var newPostKey = purityReportsDB.push().key;
	if (isAddressValid(addressStr)
		&& isPurityConditionValid(waterCondition)
		&& isNumeric(ppmVirus)
		&& isNumeric(ppmContam)) {

		var updates = {};
		updates[newPurity['reportNumber']] = newPurity;

		purityReportsDB.update(updates);
		alert("Added a purity report!");
		return true;
	}
	return false;
}

function getCurrentDateTime() {
	var d = new Date();
	var dateTime = d.toLocaleDateString();
	if (dateTime.length != 10) {
		dateTime = "0" + dateTime;
	}
	console.log("Datetime after day: " + dateTime);
	var time = d.toLocaleTimeString();
	if (dateTime.length != 11) {
		dateTime.concat("0");
	}
	dateTime.concat(time.concat(" (EDT)"));
	console.log("Datetime after time: " + dateTime);
	return dateTime;
}

function isNumeric(num) {
	return (!isNaN(parseFloat(num)) && isFinite(num));
}

function isEmailValid(email) {
	return (email != null
		&& email.includes("@")
		&& email.includes(".")
		&& email.length >= 4);
}

function isAddressValid(address) {
	geocoder = new google.maps.Geocoder();
	geocoder.geocode({address: address}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			console.log(results);
			return (results.length > 0);
		} else {
			console.log("Could not very " + address + " with Google Maps Geocoder API at this time...");
			return false;
		}
	});
}

function isUsernameValid(username) {
    return (username != null
    	&& username != ""
    	&& username.length >= 5);
}

function isUserTypeValid(usertype) {
	return (usertypes.indexOf(usertype) != -1);
}

function isPasswordValid(password) {
	var pattern = new RegExp(".*\\d+.*");
	return (password.length >= 4
		&& pattern.test(password)
		&& password != password.toLowerCase());
}

function isWaterTypeValid(waterType) {
	console.log(waterTypes.indexOf(waterType));
	return (waterTypes.indexOf(waterType) != -1);
}

function isWaterConditionValid(waterCondition) {
	console.log(waterConditions.indexOf(waterCondition));
	return (waterConditions.indexOf(waterCondition) != -1);
}

function isPurityConditionValid(purityCondition) {
		return (purityConditions.indexOf(purityCondition) != -1);
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

// function tryToAddUser() {
// 	instance.users.forEach(function(user) {
// 		if (user.username === username && !userFound) {
// 			alert("here");
// 			if (String(user.password) === String(password)) {
// 				setUserOnLogin(user);
// 				window.location.href = 'home.html';
// 				return true;
// 			} else {
// 				alert("Password does not match. Please try again.");
// 				userFound = 1;
// 				return false;
// 			}
// 		}
// 	});
// 	return false;
// }

// function attemptLogin() {
// 	var username = document.getElementById('username').value;
// 	var password = document.getElementById('password').value;
// 	var users = getUsers();
// 	alert("Hello");
// 	if (tryToAddUser() != true) {
// 		alert("User not found.");
// 	}
// }

function attemptLogin() {
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	var users = getUsers();
	var userFound = 0;
	users.forEach(function(user) {
		if (user.username == username && !userFound) {
			if (String(user.password) == String(password)) {
				userFound = 1;
				console.log(user);
				sessionStorage.setItem('currentUser', JSON.stringify(user));
				// console.log(JSON.parse(sessionStorage.getItem('currentUser')));
				window.location.href = 'home.html';
			} else {
				alert("Password does not match. Please try again.");
				userFound = 1;
			}
		}
	});
	if (userFound == 0) {
		alert("User not found.");
	}
}

