var loginTimeoutHandler;

/**
 * Enables redirect to login if home page was inactive for 10 seconds.
 */
function startLoginTimeout() {
  window.sessionStorage.setItem("_time_since_active", 0);
  window.sessionStorage.setItem("_timeout", 600000);
  window.addEventListener("mousemove", function(ev) {
    this.window.sessionStorage.setItem("_time_since_active", 0);
  });
  const timeout = 1000;
  loginTimeoutHandler = window.setInterval(function() { sessionActivityCheck(timeout); console.log("pass");}, timeout);
}

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

/**
 * Redirects to login if inactivity detected for 10s.
 */
function sessionActivityCheck(timeout) {
  var time_since_active = Number.parseInt(window.sessionStorage.getItem("_time_since_active"));
  if( time_since_active >= Number.parseInt(window.sessionStorage.getItem("_timeout")) ) {
    window.sessionStorage.setItem("_time_since_active", 0);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/logout", true);
    xhr.send();
    alert("You have been inactive for too long. Redirecting to login...");
    window.location.reload();
  } else {
    window.sessionStorage.setItem("_time_since_active", time_since_active + timeout);
    time_since_active = Number.parseInt(window.sessionStorage.getItem("_time_since_active"));
    var timeoutCounter;
    try {
      timeoutCounter = document.getElementById("timeout");
      timeoutCounter.innerHTML = "Inactivity timer (ms):" + time_since_active;
    } catch(error) {
      timeoutCounter = document.createElement("div");
      timeoutCounter.id = "timeout";
      timeoutCounter.className = "row flex-row m-5 p-5 text-info";
      document.body.appendChild(timeoutCounter);
      timeoutCounter.innerHTML = "Inactivity timer (ms):" + time_since_active;
    }
  }
}
