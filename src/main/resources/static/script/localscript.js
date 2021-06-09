document.onload = function() {
  if(localStorage.getItem("_activityTimeout") == null) {
    localStorage.setItem("_activityTimeout", 0);
    localStorage.setItem("_reloadTime", Date.now());
  }
  setTimeout(function() {
    const timeNow = Date.now();
    var currTimeout = localStorage.getItem("_activityTimeout");
    if(timeNow - localStorage.getItem("_reloadTime") - currTimeout > 0) {
      localStorage.setItem("_activityTimeout", currTimeout)
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

class IdleTimer {
  constructor({ timeout, onTimeout, onExpired }) {
    this.timeout = timeout;
    this.onTimeout = onTimeout;

    const expiredTime = parseInt(localStorage.getItem("_expiredTime"), 10);
    if (expiredTime > 0 && expiredTime < Date.now()) {
      onExpired();
      return;
    }

    this.eventHandler = this.updateExpiredTime.bind(this);
    this.tracker();
    this.startInterval();
  }

  startInterval() {
    this.updateExpiredTime();

    this.interval = setInterval(() => {
      const expiredTime = parseInt(localStorage.getItem("_expiredTime"), 10);
      if (expiredTime < Date.now()) {
        if (this.onTimeout) {
          this.onTimeout();
          this.cleanUp();
        }
      }
    }, 1000);
  }

  updateExpiredTime() {
    if (this.timeoutTracker) {
      clearTimeout(this.timeoutTracker);
    }
    this.timeoutTracker = setTimeout(() => {
      localStorage.setItem("_expiredTime", Date.now() + this.timeout * 1000);
    }, 300);
  }

  tracker() {
    window.addEventListener("mousemove", this.eventHandler);
    window.addEventListener("scroll", this.eventHandler);
    window.addEventListener("keydown", this.eventHandler);
  }

  cleanUp() {
    localStorage.removeItem("_expiredTime");
    clearInterval(this.interval);
    window.removeEventListener("mousemove", this.eventHandler);
    window.removeEventListener("scroll", this.eventHandler);
    window.removeEventListener("keydown", this.eventHandler);
  }
}

new IdleTimer()
