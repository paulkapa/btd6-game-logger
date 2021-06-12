document.onload = function() {
  if(localStorage.getItem("_activityTimeout") == null) {
    localStorage.setItem("_activityTimeout", 10000);
    localStorage.setItem("_reloadTime", Date.now());
  }
  setTimeout(function() {
    const timeNow = Date.now();
    var currTimeout = localStorage.getItem("_activityTimeout");
    if(timeNow - localStorage.getItem("_reloadTime") <= currTimeout) {
      localStorage.setItem("_reloadTime", timeNow);
      var xhr = new XMLHttpRequest();
      xhr.open("POST", "/logout", true);
      xhr.send();
    }
  }, 100);
};

/**
 * Toggles visibility of extra login information.
 */
function loginInfoToggleVisibility() {
  var loginInfo = document.getElementById("loginInfo");
  if(loginInfo.classList.contains("invisible")) {
    loginInfo.classList.remove("invisible");
    loginInfo.classList.add("visible");
  } else if(loginInfo.classList.contains("visible")) {
    loginInfo.classList.remove("visible");
    loginInfo.classList.add("invisible");
  } else {
    loginInfo.classList.add("invisible");
  }
}

/**
 * Toggles display of registration information.
 */
function registerInfoToggleVisibility() {
  var registerInfo = document.getElementById("registerInfo");
  //var registerForm = document.getElementById("registerForm");
  var registerInfoToggle = document.getElementById("registerInfoToggle");
  if(registerInfo.classList.contains("invisible")) {
    console.log("invisible -> visible");
    registerInfo.classList.remove("invisible");
    registerInfoToggle.innerText = "Hide More Information";
    registerInfo.classList.add("visible");
  } else if(registerInfo.classList.contains("visible")) {
    console.log("visible -> invisible");
    registerInfoToggle.classList.remove("visible");
    registerInfoToggle.innerText = "Show More Information";
    registerInfo.classList.add("invisible");
  } else {
    console.log("else -> invisible");
    registerInfoToggle.innerText = "Show More Information";
    registerInfo.classList.add("invisible");
  }
}

/**
 * Toggles visibility of containers with private email address.
 */
function privateEmailToggleVisibility() {
  var privateEmail = document.getElementById("privateEmail");
  var privateEmailToggle = document.getElementById("privateEmailToggle");
  if(privateEmail.classList.contains("invisible")) {
    privateEmail.classList.remove("invisible");
    privateEmailToggle.innerText = "Hide";
    privateEmail.classList.add("visible");
  } else if(privateEmail.classList.contains("visible")) {
    privateEmail.classList.remove("visible");
    privateEmailToggle.innerText = "Show";
    privateEmail.classList.add("invisible");
  } else {
    privateEmailToggle.innerText = "Show";
    privateEmail.classList.add("invisible");
  }
}
