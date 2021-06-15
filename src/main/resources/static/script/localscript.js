var loginTimeoutHandler = null;
var bodyScrollHeight = null;
var initialX = null;
var initialY = null;
var timeSinceActive = null;
var inactivityTimeout = null;
var intervalTimeout = 1000;

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
  var registerInfoToggle = document.getElementById("registerInfoToggle");
  if(registerInfoToggle.innerHTML == "Show More Information") {
    var iElement1 = document.createElement("i");
    var iElement2 = document.createElement("i");
    var pElement = document.createElement("p");
    pElement.classList.add("d-inline", "p-2");
    pElement.innerHTML = "Hide More Information";
    iElement1.classList.add("d-inline", "fas", "fa-chevron-up");
    iElement2.classList.add("d-inline", "fas", "fa-chevron-up");
    registerInfoToggle.innerHTML = null;
    registerInfoToggle.appendChild(iElement1);
    registerInfoToggle.appendChild(pElement);
    registerInfoToggle.appendChild(iElement2);
    setTimeout(function() {
      bodyScrollHeight = document.body.scrollHeight;
      window.scrollTo(0, bodyScrollHeight);
    }, 200);
  } else {
    registerInfoToggle.innerHTML = null;
    registerInfoToggle.innerHTML = "Show More Information";
    window.scrollTo(0, 0);
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
 * Redirects to login if no activity detected for a while.
 */
function sessionActivityCheck(timeout) {
  if(timeSinceActive == null) {timeSinceActive = 999;}
  if(timeSinceActive >= inactivityTimeout) {
    timeSinceActive = 0;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/logout", true);
    xhr.send();
    alert("You have been inactive for 10 minutes. In order to save resources you have been logged out...");
    window.location.reload();
  } else {
    timeSinceActive += timeout;
    var timeoutCounterElement;
    try {
      timeoutCounterElement = document.getElementById("timeout");
      timeoutCounterElement.innerHTML = "Inactivity timer (s):" + timeSinceActive/1000;
    } catch(error) {
      timeoutCounterElement = document.createElement("div");
      timeoutCounterElement.id = "timeout";
      timeoutCounterElement.classList.add("row", "flex-row", "m-5", "p-5", "text-info");
      document.body.appendChild(timeoutCounterElement);
      timeoutCounterElement.innerHTML = "Inactivity timer (s):" + timeSinceActive/1000;
    }
  }
}

/**
 * Enables redirect to login if home page was inactive for a while.
 */
function startLoginTimeout() {
  timeSinceActive = 0;
  inactivityTimeout = 10000; //600000
  window.addEventListener("mousemove", (event) => {
    timeSinceActive = 0;
  });
  window.addEventListener("touchmove", (event) => {
    timeSinceActive = 0;
  });
  loginTimeoutHandler = window.setInterval(function() {sessionActivityCheck(intervalTimeout)}, intervalTimeout);
}

/**
 * Saves the point on the screen where a touch move starts.
 * @param {*} e the touch event
 */
function startTouch(e) {
  initialX = e.touches[0].clientX;
  initialY = e.touches[0].clientY;
};

/**
 * Computes the direction of the current touch move.
 * @param {*} e the touch event
 */
function moveTouch(e) {
  var registerElement = document.getElementById("registerInfoToggle");
  if (initialX === null) {
    return;
  }
  if (initialY === null) {
    return;
  }
  var currentX = e.touches[0].clientX;
  var currentY = e.touches[0].clientY;
  var diffX = initialX - currentX;
  var diffY = initialY - currentY;
  if (Math.abs(diffX) > Math.abs(diffY)) {
    // sliding horizontally
    if (diffX > 0) {
      // swiped left
      console.log("swiped left");
    } else {
      // swiped right
      console.log("swiped right");
    }
  } else {
    // sliding vertically
    if (diffY > 0) {
      // swiped up
      if(registerElement.innerHTML == "Show More Information") {
        registerElement.click();
      }
      console.log("swiped up");
    } else {
      // swiped down
      if(registerElement.childElementCount > 1) {
        registerElement.click();
      }
      console.log("swiped down");
    }
  }
  initialX = null;
  initialY = null;
  e.preventDefault();
};

/**
 * Listens for swipes.
 */
if(document.title == "BTD6 G-L | Register") {
  setTimeout(function() {
    try {
        window.addEventListener("touchstart", startTouch, false);
        window.addEventListener("touchmove", moveTouch, false);
    } catch (error) {}
  }, 100);
}

/**
 * Warns if leaving page may lead to loss of unsaved data.
 */
if(document.title == "BTD6 G-L | App") {
  window.addEventListener('beforeunload', (event) => {
    try {
      // Cancel the event as stated by the standard.
      event.preventDefault();
      // Chrome requires returnValue to be set.
      event.returnValue = '';
    } catch(error) {}
  });
}
