// Initialize Firebase
// TODO: Replace with your project's customized code snippet
var config = {
	apiKey: "AIzaSyAd45R67ts78jB2xXMKJ4n2YoD2xXU2y7M",
	authDomain: "https://harambe-loved-waters-reporter.firebaseapp.com",
	databaseURL: "https://harambe-loved-waters-reporter.firebaseio.com",
// storageBucket: "<BUCKET>.appspot.com",
// messagingSenderId: "<SENDER_ID>",
};
firebase.initializeApp(config);

var instance = new singleton();

function singleton() {
	this.registeredUserSet = new Set([]);
	this.registeredUserMap = [];
	this.reportList = [];
	this.purityReportList = [];
	this.currUser = {};
}

getCurrUser() { return instance.getCurrUser; }

setCurrUser(var user) { instance.currUser = user; }

