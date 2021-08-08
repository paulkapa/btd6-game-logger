var bodyScrollHeight = null;
var currPageTitle = document.title;

/**
 * Warn before leaving page.
 */
function warnBeforeLeavingPage() {
    try {
        window.addEventListener("beforeunload", (event) => {
            if (event.origin !== "http://localhost:8080") {
                // Cancel the event as stated by the standard.
                event.preventDefault();
                // Chrome requires returnValue to be set.
                event.returnValue = "";
            } else {
                throw new Error("Request origin does not comply!");
            }
        });
    } catch (error) { console.error("Error - warn before leaving page!\n" + error.name + " | " + error.message); }
}

/**
 * Handle app shutdown.
 */
function handleAppShutdown() {
    try {
        var toggleContainer = document.getElementById("toggleShutdownButton");
        toggleContainer.addEventListener("click", () => {
            var container = document.getElementById("shutdownContainer");
            container.classList.toggle("invisible");
        });
        var shutdownButton = document.getElementById("shutdownButton");
        shutdownButton.addEventListener("click", () => {
            alert("If you pressed the shutdown button by accident you may cancel the action at the following prompt.")
            warnBeforeLeavingPage();
        });
    } catch (error) { console.error("Error - handle app shutdown!\n" + error.name + " | " + error.message); }
}

/**
 * Handle fail messages.
 */
function handleFailMessages() {
    try {
        var alertMessage = document.getElementById("alert-message");
        var alertText = alertMessage.innerText;
        if (alertText != null && alertText != "") {
            var alertContainer = document.getElementById("alertContainer");
            var closeAlert = document.getElementById("closeAlert");
            alertContainer.classList.toggle("collapse");
            alertContainer.classList.toggle("alert-container");
            if (alertText.includes("Success")) {
                alertMessage.classList.remove("text-info");
                alertMessage.classList.remove("text-danger");
                alertMessage.classList.add("text-success");
            } else if (alertText.includes("Info")) {
                alertMessage.classList.add("text-info");
                alertMessage.classList.remove("text-danger");
                alertMessage.classList.remove("text-success");
            } else {
                alertMessage.classList.remove("text-info");
                alertMessage.classList.add("text-danger");
                alertMessage.classList.remove("text-success");
            }
            closeAlert.addEventListener("click", () => {
                closeAlert.classList.toggle("collapse");
                alertContainer.classList.toggle("alert-container");
                alertContainer.classList.toggle("collapse");
            });
        }
    } catch (error) { console.error("Error - handle fail messages!\n" + error.name + " | " + error.message); }
}

/**
 * Handle spinner loading placeholder.
 */
function handleSpinner() {
    try {
        var spinnerContainer = document.getElementById("spinnerContainer");
        var spinnerSVG = document.getElementById("spinnerSVG");
        spinnerSVG.onload = setTimeout(() => {
            spinnerContainer.classList.add("d-none");
            spinnerSVG.classList.add("d-none");
        }, 1500);
    } catch (error) { console.error("Error - handle spinner loading placeholder!\n" + error.name + " | " + error.message); }
}

/**
 * Show page content by minimizing modal header.
 */
function handleAccountSection() {
    try {
        document.getElementById("contentControl").addEventListener("click", () => {
            var nav = document.getElementById("navGrid");
            var navContent = document.getElementById("navbarToggleContent");
            var navBar = document.getElementById("navBar");
            var butt = document.getElementById("contentControl").getElementsByTagName("button").item(0);
            var buttIcon = butt.getElementsByTagName("svg").item(0);
            var bText = butt.getElementsByTagName("p").item(0);
            if (bText.innerText.includes("View")) {
                butt.innerHTML = null;
                butt.append(buttIcon);
                bText.innerText = "Hide Account";
                butt.append(bText);
            } else {
                butt.append(buttIcon);
                bText.innerText = "View Account";
                butt.append(bText);
            }
            nav.classList.toggle("modal");
            nav.classList.toggle("modal-header");
            nav.classList.toggle("border");
            navContent.classList.toggle("position-absolute");
            navContent.classList.toggle("top-0");
            navBar.classList.toggle("position-absolute");
            navBar.classList.toggle("bottom-0");
        });
    } catch (error) { console.error("Error - show page content by minimizing modal header!\n" + error.name + " | " + error.message); }
}

/**
 * Toggles display of registration information.
 */
function registerInfoToggleVisibility() {
    var registerInfoToggle = document.getElementById("registerInfoToggle");
    if (registerInfoToggle.innerHTML.includes("Show")) {
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
        setTimeout(() => {
            bodyScrollHeight = document.body.scrollHeight;
            window.scrollTo(0, bodyScrollHeight);
        }, 350);
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
    if (privateEmail.classList.contains("invisible")) {
        privateEmail.classList.remove("invisible");
        privateEmailToggle.innerText = "Hide";
        privateEmail.classList.add("visible");
    } else if (privateEmail.classList.contains("visible")) {
        privateEmail.classList.remove("visible");
        privateEmailToggle.innerText = "Show";
        privateEmail.classList.add("invisible");
    } else {
        privateEmailToggle.innerText = "Show";
        privateEmail.classList.add("invisible");
    }
}

/**
 * Redirect to login if no activity detected for a while.
 */
function sessionActivityCheck() {
    var timeSinceActive = 0;
    var inactivityTimeout = 1200000;
    window.addEventListener("mousemove", () => {
        timeSinceActive = 0;
    });
    window.addEventListener("touchmove", () => {
        timeSinceActive = 0;
    });
    window.setInterval(function () {
        if (timeSinceActive == null) { timeSinceActive = 0; }
        if (timeSinceActive >= inactivityTimeout) {
            timeSinceActive = null;
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/logout", true);
            xhr.send();
            alert("You have been inactive for 10 minutes. In order to save resources you have been logged out...");
            window.location.reload();
        } else {
            timeSinceActive += 1000;
        }
    }, 1000);
}

/**
 * Allow anonymous login.
 */
function allowAnonymousLogin() {
    try {
        if (!currPageTitle.includes("Login") && !currPageTitle.includes("Register")) {
            document.getElementById("anonymousLoginForm").classList.add("d-none");
        } else {
            document.getElementById("anonymousLoginForm").classList.remove("d-none");
        }
    } catch (error) { console.error("Error - allow anonymous login!\n" + error.name + " | " + error.message); }
}

/**
 * Handle user account controls.
 */
function handleAccountSectionControls() {
    try {
        var message1 = document.getElementById("navMess1");
        var message2 = document.getElementById("navMess2");
        var message3 = document.getElementById("navMess3");
        var usernameField = document.getElementById("usernameField");
        var passwordField = document.getElementById("passwordField");
        var emailField = document.getElementById("emailField");
        var accountAgeField = document.getElementById("accountAgeField");
        var allowEditButton = document.getElementById("allowEditButton");
        var deleteUserButton = document.getElementById("deleteUserButton");

        document.getElementById("navSelectControls").addEventListener("change", (e) => {
            var ctrlSelect = e.target;
            //var value = ctrlSelect.value ... For getting the option value.
            var text = ctrlSelect.options[ctrlSelect.selectedIndex].text;

            switch (text) {
                case "Profile": {
                    if (currPageTitle.includes("Home")) {
                        usernameField.classList.remove("collapse");
                        passwordField.classList.add("collapse");
                        emailField.classList.remove("collapse");
                        accountAgeField.classList.add("collapse");
                        if (!allowEditButton.classList.contains("collapse") && allowEditButton.classList.contains("btn-info"))
                            allowEditButton.click();
                        allowEditButton.classList.add("collapse");
                        deleteUserButton.classList.add("collapse");
                        deleteUserButton.setAttribute("disabled", "");
                        message1.classList.add("d-none");
                        message2.classList.add("d-none");
                        message3.classList.add("d-none");
                        break;
                    }
                } case "Personal Information": {
                    if (currPageTitle.includes("Home")) {
                        usernameField.classList.remove("collapse");
                        passwordField.classList.remove("collapse");
                        emailField.classList.remove("collapse");
                        accountAgeField.classList.remove("collapse");
                        allowEditButton.classList.remove("collapse");
                        deleteUserButton.classList.remove("collapse");
                        deleteUserButton.removeAttribute("disabled");
                        message3.classList.add("d-none");
                        handleUpdateUser();
                        handleDeleteUser();
                        message1.classList.add("d-none");
                        message2.classList.add("d-none");
                        message3.classList.add("d-none");
                        break;
                    } else if (currPageTitle.includes("App")) {
                        message1.classList.add("d-none");
                        message2.classList.remove("d-none");
                        message3.classList.add("d-none");
                        break;
                    }
                } case "Saved Data": {
                    if (currPageTitle.includes("Home")) {
                        usernameField.classList.add("collapse");
                        passwordField.classList.add("collapse");
                        emailField.classList.add("collapse");
                        accountAgeField.classList.add("collapse");
                        if (!allowEditButton.classList.contains("collapse") && allowEditButton.classList.contains("btn-info")) {
                            allowEditButton.click();
                        }
                        allowEditButton.classList.add("collapse");
                        deleteUserButton.classList.add("collapse");
                        deleteUserButton.setAttribute("disabled", "");
                        message1.classList.add("d-none");
                        message2.classList.add("d-none");
                        message3.classList.remove("d-none");
                        break;
                    } else if (currPageTitle.includes("App")) {
                        // TODO : remove message3 "... only avaialble on app page" since this is the app page.
                        message1.classList.add("d-none");
                        message2.classList.add("d-none");
                        message3.classList.remove("d-none");
                        break;
                    }
                } default: {
                    if (currPageTitle.includes("Home")) {
                        usernameField.classList.add("collapse");
                        passwordField.classList.add("collapse");
                        emailField.classList.add("collapse");
                        accountAgeField.classList.add("collapse");
                        if (!allowEditButton.classList.contains("collapse") && allowEditButton.classList.contains("btn-info")) {
                            allowEditButton.click();
                        }
                        allowEditButton.classList.add("collapse");
                        deleteUserButton.classList.add("collapse");
                        deleteUserButton.setAttribute("disabled", "");
                        message3.classList.add("d-none");
                        break;
                    } else if (currPageTitle.includes("App")) {
                        message1.classList.add("d-none");
                        message2.classList.add("d-none");
                        message3.classList.add("d-none");
                        break;
                    } else {
                        usernameField.classList.remove("collapse");
                        passwordField.classList.add("collapse");
                        emailField.classList.remove("collapse");
                        accountAgeField.classList.add("collapse");
                        if (!allowEditButton.classList.contains("collapse") && allowEditButton.classList.contains("btn-info"))
                            allowEditButton.click();
                        allowEditButton.classList.add("collapse");
                        deleteUserButton.classList.add("collapse");
                        message1.classList.add("d-none");
                        message2.classList.add("d-none");
                        message3.classList.add("d-none");
                        break;
                    }
                }
            }
        });
    } catch (error) { console.error("Error - user account controls!\n" + error.name + " | " + error.message); }
}

/**
 * Handle updating user account details in nav account controls.
 */
function handleUpdateUser() {
    try {
        document.getElementById("allowEditButton").addEventListener("click", () => {
            var userControlForm = document.getElementById("userControlForm");
            var allowEditButton = document.getElementById("allowEditButton");
            var deleteUserButton = document.getElementById("deleteUserButton");
            var saveChangesButton = document.getElementById("saveChangesButton");
            var navUsername = document.getElementById("navUsername");
            var navPassword = document.getElementById("navPassword");
            var navEmail = document.getElementById("navEmail");
            if (allowEditButton.classList.contains("btn-warning")) {
                userControlForm.reset();
                userControlForm.setAttribute("action", "/updateUserInfo");
                navUsername.addEventListener("focusin", () => {
                    saveChangesButton.removeAttribute("disabled");
                });
                navPassword.addEventListener("focusin", () => {
                    saveChangesButton.removeAttribute("disabled");
                });
                navEmail.addEventListener("focusin", () => {
                    saveChangesButton.removeAttribute("disabled");
                });
                allowEditButton.classList.add("btn-info");
                allowEditButton.classList.remove("btn-warning");
                saveChangesButton.classList.remove("collapse");
                deleteUserButton.classList.add("collapse");
                deleteUserButton.setAttribute("disabled", "");
                navUsername.removeAttribute("disabled");
                navPassword.removeAttribute("disabled");
                navEmail.removeAttribute("disabled");
            } else {
                userControlForm.reset();
                userControlForm.setAttribute("action", "/deleteUserAccount");
                allowEditButton.classList.remove("btn-info");
                allowEditButton.classList.add("btn-warning");
                saveChangesButton.classList.add("collapse");
                saveChangesButton.setAttribute("disabled", "");
                deleteUserButton.classList.remove("collapse");
                deleteUserButton.removeAttribute("disabled");
                navUsername.setAttribute("disabled", "");
                navPassword.setAttribute("disabled", "");
                navEmail.setAttribute("disabled", "");
            }
        });
    } catch (error) { console.error("Error - handle updating user account details!\n" + error.name + " | " + error.message); }
}

/**
 * Handle deleting user account in nav account controls.
 */
function handleDeleteUser() {
    try {
        document.getElementById("deleteUserButton").addEventListener("click", () => {
            alert("If you pressed the delete account button by accident you may cancel the action in the following prompt.")
            warnBeforeLeavingPage();
        });
    } catch (error) { console.error("Error - handle deleting user account!\n" + error.name + " | " + error.message); }
}

/**
 * Handle Home page app setup selection. (map, difficulty and gamemode)
 */
function handleAppSelection() {
    try {
        if (currPageTitle.includes("Home")) {
            var setupForm = document.getElementById("optionSelector");
            var mapOutput = document.getElementById("selectedMap");
            var diffOutput = document.getElementById("selectedDiff");
            var modeOutput = document.getElementById("selectedMode");
            var mapSelect = document.getElementById("mapSelect");
            var diffSelect = document.getElementById("diffSelect");
            var modeSelect = document.getElementById("modeSelect");
            var tdout = document.getElementById("homeSelectionViewGrid").getElementsByTagName("td");
            var odiff = document.getElementsByClassName("odiff");
            var omode = document.getElementsByClassName("omode");
            var mapIndex = 0;
            var diffIndex = 0;
            var modeIndex = 0;
            var curr;
            var allowed;

            setupForm.addEventListener("reset", () => {
                mapOutput.innerText = "Select...";
                diffOutput.innerText = "Select...";
                modeOutput.innerText = "Select...";
                for (let i = 0; i < tdout.length; i++) {
                    tdout.item(i).classList.add("d-none");
                }
                document.getElementsByClassName("odiff-info").item(0).classList.remove("d-none");
                for (let i = 0; i < odiff.length; i++) {
                    odiff.item(i).classList.add("d-none");
                }
                document.getElementsByClassName("omode-info").item(0).classList.remove("d-none");
                for (let i = 0; i < omode.length; i++) {
                    omode.item(i).classList.add("d-none");
                }
            });

            mapSelect.addEventListener("change", (e) => {
                var selectedMap = e.target;
                mapIndex = selectedMap.selectedIndex;
                var mapText = selectedMap.options[mapIndex].text;
                mapOutput.innerText = mapText;
                selectedDiff.innerText = "Select...";
                selectedMode.innerText = "Select...";
                if (mapIndex != 0) {
                    document.getElementsByClassName("odiff-info").item(0).classList.add("d-none");
                    for (let i = 0; i < tdout.length; i++) {
                        curr = tdout.item(i);
                        if (!curr.classList.contains("td" + (mapIndex - 1))) {
                            curr.classList.add("d-none");
                        } else {
                            curr.classList.remove("d-none");
                            if (curr.classList.contains("td-diff")) {
                                curr.innerText = "";
                                diffSelect.selectedIndex = 0;
                            }
                            if (curr.classList.contains("td-mode")) {
                                curr.innerText = "";
                                modeSelect.selectedIndex = 0;
                            }
                            if (curr.classList.contains("td-mk")) {
                                curr.innerText = "";
                            }
                        }
                    }
                } else {
                    document.getElementsByClassName("odiff-info").item(0).classList.remove("d-none");
                    for (let i = 0; i < tdout.length; i++) {
                        curr = tdout.item(i);
                        curr.classList.add("d-none");
                    }
                }
                for (let i = 0; i < odiff.length; i++) {
                    if (!mapText.includes("Map...")) {
                        odiff.item(i).classList.remove("d-none");
                    } else {
                        odiff.item(i).classList.add("d-none");
                    }
                }
            });

            diffSelect.addEventListener("change", (e) => {
                var selectedDiff = e.target;
                diffIndex = selectedDiff.selectedIndex;
                var diffText = selectedDiff.options[diffIndex].text;
                diffOutput.innerText = diffText;
                selectedMode.innerText = "Select...";
                if (diffIndex > 1) {
                    document.getElementsByClassName("omode-info").item(0).classList.add("d-none");
                    for (let i = 0; i < tdout.length; i++) {
                        curr = tdout.item(i);
                        curr.classList.add("d-none");
                        if (curr.classList.contains("td-diff")) {
                            curr.innerText = diffText;
                        }
                        if (curr.classList.contains("td-mode")) {
                            curr.innerText = "";
                            modeSelect.selectedIndex = 0;
                        }
                        if (curr.classList.contains("td-mk")) {
                            curr.innerText = "";
                        }
                        if (curr.classList.contains("td" + (mapIndex - 1))) {
                            curr.classList.remove("d-none");
                        }
                    }
                } else {
                    document.getElementsByClassName("omode-info").item(0).classList.remove("d-none");
                    for (let i = 0; i < tdout.length; i++) {
                        curr = tdout.item(i);
                        curr.classList.add("d-none");
                    }
                }
                switch (diffText) {
                    case "Easy": { allowed = [1, 2]; break; }
                    case "Medium": { allowed = [3, 5]; break; }
                    case "Hard": { allowed = [6, 11]; break; }
                    default: { allowed = [0, 0]; break; }
                }
                for (let i = 0; i < omode.length; i++) {
                    curr = omode.item(i);
                    if (!diffText.includes("Difficulty...") || !diffText.includes("Select")) {
                        if (curr.classList.contains("om0") || curr.classList.contains("om12")) {
                            curr.classList.remove("d-none");
                        } else {
                            curr.classList.add("d-none");
                            if (i >= allowed[0] && i <= allowed[1]) {
                                curr.classList.remove("d-none");
                            }
                        }
                    } else {
                        curr.classList.add("d-none");
                    }
                }
            });

            modeSelect.addEventListener("change", (e) => {
                var selectedMode = e.target;
                modeIndex = selectedMode.selectedIndex;
                var modeText = selectedMode.options[modeIndex].text;
                modeOutput.innerText = modeText;
                if (modeIndex > 1) {
                    for (let i = 0; i < tdout.length; i++) {
                        curr = tdout.item(i);
                        if (!curr.classList.contains("td" + (mapIndex - 1))) {
                            curr.classList.add("d-none");
                        } else {
                            curr.classList.remove("d-none");
                            if (curr.classList.contains("td-mode")) {
                                curr.innerText = modeText;
                            }
                            if (curr.classList.contains("td-mk")) {
                                curr.innerText = ((modeText.includes("CHIMPS") || modeText.includes("Sandbox")) ? "No" : "Yes");
                            }
                            if (curr.classList.contains("td" + (mapIndex - 1))) {
                                curr.classList.remove("d-none");
                            }
                        }
                    }
                }
                for (let i = 0; i < omode.length; i++) {
                    curr = omode.item(i);
                    if (!modeText.includes("Game Mode...") || !modeText.includes("Select")) {
                        if (curr.classList.contains("om0") || curr.classList.contains("om12")) {
                            curr.classList.remove("d-none");
                        } else {
                            curr.classList.add("d-none");
                            if (i >= allowed[0] && i <= allowed[1]) {
                                curr.classList.remove("d-none");
                            }
                        }
                    } else {
                        curr.classList.add("d-none");
                    }
                }
            });
        }
    } catch (error) { console.error("Error - handle home page app setup selection!\n" + error.name + " | " + error.message); }
}

/**
 * Handle function that run at document load complete.
 */
document.onload = setTimeout(() => {
    try {
        if (!(currPageTitle == null && currPageTitle == "")) {
            // All pages
            handleSpinner();
            handleFailMessages();
            if (!currPageTitle.includes("Error")) {
                // All pages, except Error page
                handleAccountSection();
                if (!currPageTitle.includes("Login") && !currPageTitle.includes("Register")) {
                    // Home and App pages
                    warnBeforeLeavingPage();
                    sessionActivityCheck();
                    handleAccountSectionControls();
                    if (!currPageTitle.includes("Home")) {
                        // App
                        // TODO - save progress functionality
                    } else {
                        // Home
                        handleAppSelection();
                    }
                } else {
                    // Login, Register
                    allowAnonymousLogin();
                    handleAppShutdown();
                }
            }
        }
    } catch (error) { console.error("Script error! App might be used irresponibly or something failed. Check the error description to find out!\n" + error.name + " | " + error.message) }
}, 0);
