var intervalTimeout = 1000;
var bodyScrollHeight = null;
var touchInitialX = null;
var touchInitialY = null;
var timeSinceActive = null;
var inactivityTimeout = null;
var loginTimeoutHandler = null;
var currPageTitle = document.title;

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
  }
}

/**
 * Saves the point on the screen where a touch move starts.
 * @param {*} e the touch event
 */
 function startTouch(e) {
  touchInitialX = e.touches[0].clientX;
  touchInitialY = e.touches[0].clientY;
}

/**
 * Computes the direction of the current touch move.
 * @param {*} e the touch event
 */
function moveTouch(e) {
  var registerElement = document.getElementById("registerInfoToggle");
  if (touchInitialX === null) {
    return;
  }
  if (touchInitialY === null) {
    return;
  }
  var currentX = e.touches[0].clientX;
  var currentY = e.touches[0].clientY;
  var diffX = touchInitialX - currentX;
  var diffY = touchInitialY - currentY;
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
  touchInitialX = null;
  touchInitialY = null;
  e.preventDefault();
}

/**
 * Handles function to run at document load.
 */
document.body.onload = function() {
  /**
   * Enables redirect to login if page was inactive for a while.
   */
  timeSinceActive = 0;
  inactivityTimeout = 600000;
  window.addEventListener("mousemove", (event) => {
    timeSinceActive = 0;
  });
  window.addEventListener("touchmove", (event) => {
    timeSinceActive = 0;
  });
  loginTimeoutHandler = window.setInterval(function() {sessionActivityCheck(intervalTimeout)}, intervalTimeout);
  /**
   * Handling touch swipes on pages supporting touch actions.
   */
  /**if(currPageTitle == "BTD6 G-L | Register") {
    try {
      window.addEventListener("touchstart", startTouch, false);
      window.addEventListener("touchmove", moveTouch, false);
    } catch (error) {}
  }*/
  /**
   * Handling user account controls.
   */
  try{
  document.getElementById("navSelectControls").addEventListener("change", (e) => {
    var message1 = document.getElementById("navMess1");
    var message2 = document.getElementById("navMess2");
    var usernameField = document.getElementById("usernameField");
    var passwordField = document.getElementById("passwordField");
    var emailField = document.getElementById("emailField");
    var accountAgeField = document.getElementById("accountAgeField");
    var allowEditButton = document.getElementById("allowEditButton");
    var select = e.target;
    //const value = select.value;
    var desc = select.options[select.selectedIndex].text;
    if(currPageTitle == "BTD6 G-L | Home") {
      switch(desc) {
        case "Profile" : {
          usernameField.classList.remove("collapse");
          passwordField.classList.add("collapse");
          emailField.classList.remove("collapse");
          accountAgeField.classList.add("collapse");
          if(!allowEditButton.classList.contains("collapse") && allowEditButton.classList.contains("btn-info")) {
            allowEditButton.click();
          }
          allowEditButton.classList.add("collapse");
          break;
        } case "Personal Information" : {
          usernameField.classList.remove("collapse");
          passwordField.classList.remove("collapse");
          emailField.classList.remove("collapse");
          accountAgeField.classList.remove("collapse");
          allowEditButton.classList.remove("collapse");
          break;
        } case "Saved Information" : {
        } default : {
          usernameField.classList.add("collapse");
          passwordField.classList.add("collapse");
          emailField.classList.add("collapse");
          accountAgeField.classList.add("collapse");
          if(!allowEditButton.classList.contains("collapse") && allowEditButton.classList.contains("btn-info")) {
            allowEditButton.click();
          }
          allowEditButton.classList.add("collapse");
          break;
        }
      }
    } else {
      switch(desc) {
        case "Profile" : {
          if(currPageTitle == "BTD6 G-L | App") {
            message2.classList.remove("d-none");
            message1.classList.add("d-none");
          } else {
            message2.classList.add("d-none");
            message1.classList.remove("d-none");
          }
          break;
        } case "Personal Information" : {
          if(currPageTitle == "BTD6 G-L | App") {
            message2.classList.remove("d-none");
            message1.classList.add("d-none");
          } else {
            message2.classList.add("d-none");
            message1.classList.remove("d-none");
          }
          break;
        } case "Saved Information" : {
        } default : {
          if(currPageTitle == "BTD6 G-L | App") {
            message2.classList.add("d-none");
            message1.classList.add("d-none");
          } else {
            message2.classList.add("d-none");
            message1.classList.add("d-none");
          }
          break;
        }
      }
    }
  });} catch(error) {console.error(error);}
  /**
   * Handling updating user account details.
   */
  try {
  document.getElementById("allowEditButton").addEventListener("click", (e) => {
    var allowEditButton = document.getElementById("allowEditButton");
    var userControlForm = document.getElementById("userControlForm");
    var saveChangesButton = document.getElementById("saveChangesButton");
    var navUsername = document.getElementById("navUsername");
    var navPassword = document.getElementById("navPassword");
    var navEmail = document.getElementById("navEmail");
    if(allowEditButton.classList.contains("btn-warning")) {
      allowEditButton.classList.add("btn-info");
      allowEditButton.classList.remove("btn-warning");
      saveChangesButton.classList.remove("collapse");
      navUsername.removeAttribute("disabled");
      navPassword.removeAttribute("disabled");
      navEmail.removeAttribute("disabled");
      userControlForm.reset();
    } else {
      allowEditButton.classList.add("btn-warning");
      allowEditButton.classList.remove("btn-info");
      saveChangesButton.classList.add("collapse");
      navUsername.setAttribute("disabled", "");
      navPassword.setAttribute("disabled", "");
      navEmail.setAttribute("disabled", "");
      userControlForm.reset();
    }
  });} catch(error) {console.error(error);}
  /**
   * Handling Home page game mode selection
   */
  if(currPageTitle == "BTD6 G-L | Home") {
    var mapOutput = document.getElementById("selectedMap");
    var diffOutput = document.getElementById("selectedDiff");
    var mapSelect = document.getElementById("mapSelect");
    var diffSelect = document.getElementById("diffSelect");
    var tdout = document.getElementsByTagName("td");
    var mapIndex = 0;
    mapSelect.addEventListener("change", (e) => {
      var selectedMap = e.target;
      mapIndex = selectedMap.selectedIndex;
      var mapDesc = selectedMap.options[mapIndex].text;
      mapOutput.innerText = mapDesc;
      if(mapIndex != 0) {
        for(let i = 0; i < tdout.length; i++) {
          var curr = tdout.item(i);
          if(curr.classList.contains("tdout") && !curr.classList.contains("td" + (mapIndex - 1))) {
            tdout.item(i).classList.add("d-none");
          } else if(curr.classList.contains("tdout")) {
            tdout.item(i).classList.remove("d-none");
          }
        }
      } else {
        for(let i = 0; i < tdout.length; i++) {
          var curr = tdout.item(i);
          if(curr.classList.contains("tdout")) {
            tdout.item(i).classList.add("d-none");
          }
        }
      }
    });
    diffSelect.addEventListener("change", (e) => {
      var selectedDiff = e.target;
      var diffDesc = selectedDiff.options[selectedDiff.selectedIndex].text;
      diffOutput.innerText = diffDesc;
      if(selectedDiff.selectedIndex != 0 && mapIndex != 0) {
        for(let i = 0; i< tdout.length; i++) {
          var curr = tdout.item(i);
          if(curr.classList.contains("td-diff") && curr.classList.contains("td" + (mapIndex - 1))) {
            curr.classList.remove("d-none");
            curr.innerText = diffDesc;
          }
          if(curr.classList.contains("td-mk") && curr.classList.contains("td" + (mapIndex - 1))) {
            curr.classList.remove("d-none");
            curr.innerText = ((diffDesc == "CHIMPS") ? "No" : "Yes");
          }
        }
      }
    });
  }
  //
}

/**
 * Warns if leaving App page may lead to loss of unsaved data.
 */
if(currPageTitle == "BTD6 G-L | App") {
  window.addEventListener("beforeunload", (event) => {
    try {
      // Cancel the event as stated by the standard.
      event.preventDefault();
      // Chrome requires returnValue to be set.
      event.returnValue = "";
    } catch(error) {}
  });
}
