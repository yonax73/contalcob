var activeLink = null;
var oldActiveLink = null;
var ACTIVE_LINK_CLASS = 'active-link';
var RESPONSE_OK = 'OK';
var VIEW_ACTION = {
    CREATE_BUSINESS: false
};
var SESSION_STORAGE = {
    BUSINESS: 'BUSINESS'
};

//PARTNER OBJECT
//============================================
var Partner = {
    KEY: 'PARTNER',
    title: function (value) {
        document.getElementById('page-title-txt').textContent = value;
    },
    Toolbar: {
        hidden: function (value) {
            if (value) document.getElementById('toolbar').classList.add('hidden');
            else document.getElementById('toolbar').classList.remove('hidden');
        }
    },
    getId: function () {
        return sessionStorage.getItem(this.KEY);
    },
    setId: function (id) {
        sessionStorage.setItem(this.KEY, id);
        if (document.getElementById('partnerId')) {
            document.getElementById('partnerId').value = id;
        }
    },
    setFullName: function (value) {
        document.getElementById('partnerName').value = value;
    },
    clear: function () {
        this.title('');
        this.setId(0);
        this.setFullName('');
    }
}
//===========================================
//END PARTNER OBJECT

// NOTIFICATIONS
// =================================================================

var Notification = {
    successContent: '<strong>Bien hecho!</strong> Los datos se actualizaron con &eacute;xito.',
    success: function (content) {
        $.niftyNoty({
            type: 'success',
            container: 'page',
            html: content ? content : Notification.successContent,
            timer: 5000
        });
    }
}
//===============================================================
// END NOTIFICATIONS

//LOGIN OBJECT
//================================================
var Login = {
    LENDER: 39,
    COLLECTOR: 40,
    setEmail: function (value) {
        document.getElementById('emailLogin').value = value;
    },
    setUser: function (value) {
        document.getElementById('userLogin').value = value;
    },
    setPassword: function (value) {
        document.getElementById('password').value = value;
    },
    setConfirmPassword: function (value) {
        document.getElementById('confirmPassword').value = value;
    },
    setId: function (value) {
        document.getElementById('loginId').value = value;
    },
    setLoginTypeId: function (value) {
        document.getElementById('loginTypeId').value = value;
    },
    Content: {
        emailLogin: {
            hidden: function (value) {
                if (value) document.getElementById('emailLogin-content').classList.add('hidden');
                else document.getElementById('emailLogin-content').classList.remove('hidden');
            }
        },
        userLogin: {
            hidden: function (value) {
                if (value) document.getElementById('userLogin-content').classList.add('hidden');
                else document.getElementById('userLogin-content').classList.remove('hidden');
            }
        },
        password: {
            hidden: function (value) {
                if (value) document.getElementById('password-content').classList.add('hidden');
                else document.getElementById('password-content').classList.remove('hidden');
            }
        },
        confirmPassword: {
            hidden: function (value) {
                if (value) document.getElementById('confirmPassword-content').classList.add('hidden');
                else document.getElementById('confirmPassword-content').classList.remove('hidden');
            }
        },
    },
    clear: function () {
        this.setEmail('');
        this.setUser('');
        this.setPassword('');
        this.setConfirmPassword('');
        this.setId(0);
        this.setLoginTypeId(0);
    }
}
//================================================
//END LOGIN OBJECT

//MODAL OBJECT
//=============================================
var Modal = {
    Confirm: {
        show: function () {
            $('#confirm-modal').modal('show');
        },
        close: function () {
            $('#confirm-modal').modal('hide');
        },
        action: function () {
            return document.getElementById('confirm-modal-action-btn');
        }
    },
    Alert: {
        show: function () {
            $('#alert-modal').modal('show');
        },
        close: function () {
            $('#alert-modal').modal('hide');
        },
        action: function () {
            return document.getElementById('alert-modal-action-btn');
        }
    },
    Form: {
        show: function () {
            $('#form-modal').modal('show');
        },
        close: function () {
            $('#form-modal').modal('hide');
        },
        action: function () {
            return document.getElementById('form-modal-action-btn');
        },
        title: function (value) {
            document.getElementById('modal-title').textContent = value;
        }
    }
}
//USER OBJECT
//============================================
var User = {
    KEY: 'USER',
    title: function (value) {
        document.getElementById('page-title-txt').textContent = value;
    },
    Toolbar: {
        hidden: function (value) {
            if (value) document.getElementById('toolbar').classList.add('hidden');
            else document.getElementById('toolbar').classList.remove('hidden');
        }
    },
    getId: function () {
        return sessionStorage.getItem(this.KEY);
    },
    setId: function (id) {
        sessionStorage.setItem(this.KEY, id);
        if (document.getElementById('userId')) {
            document.getElementById('userId').value = id;
        }
    },
    setFullName: function (value) {
        document.getElementById('userFullName').value = value;
    },
    clear: function () {
        this.title('');
        this.setId(0);
        this.setFullName('');
    }
}
//===========================================
//EN USER OBJECT

//ZONE OBJECT
//=========================================
var Zone = {
    KEY: 'ZONE',
    title: function (value) {
        document.getElementById('page-title-txt').textContent = value;
    },
    getId: function () {
        return sessionStorage.getItem(this.KEY);
    },
    setId: function (id) {
        sessionStorage.setItem(this.KEY, id);
        if (document.getElementById('zoneId')) {
            document.getElementById('zoneId').value = id;
        }
    },
    setName: function (value) {
        document.getElementById('zoneName').value = value;
    },
    setDescription: function (value) {
        document.getElementById('zoneDescription').value = value;
    },
    Search: {
        data: [],
        userId: function (value) {
            this.data.push({ name: 'userId', value: value });
        },
        excludeUserId: function (value) {
            this.data.push({ name: 'excludeUserId', value: value });
        },
        serialize: function () {
            return Utils.serializeObject(this.data);
        }
    },
    clear: function () {
        this.title('Crear Zona de trabajo');
        this.setId(0);
        this.setName('');
        this.setDescription('');
    }
}
//=========================================
//ZONE OBJECT

//ZONE LOCATION
//===================================================
var Location = {
    setId: function (value) {
        document.getElementById('locationId').value = value;
    },
    Address: {
        setId: function (value) {
            document.getElementById('addressId').value = value;
        },
        setAddress: function (value) {
            document.getElementById('address').value = value;
        },
        setNeighBorhood: function (value) {
            document.getElementById('neighborhood').value = value;
        },
        City: {
            setId: function (value) {
                document.getElementById('cityId').value = value;
            },
            setCity: function (value) {
                document.getElementById('city').value = value;
            },
            setCountry: function (value) {
                document.getElementById('country').value = value;
            }
        }
    },
    MobilePhone: {
        setId: function (value) {
            document.getElementById('mobilePhoneId').value = value;
        },
        setNumber: function (value) {
            document.getElementById('mobilePhone').value = value;
        }
    },
    Landline: {
        setId: function (value) {
            document.getElementById('landlineId').value = value;
        },
        setNumber: function (value) {
            document.getElementById('landline').value = value;
        },
        setExt: function (value) {
            document.getElementById('extensionLandline').value = value;
        }
    },
    Content: {
        landline: {
            hidden: function (value) {
                if (value) document.getElementById('landline-content').classList.add('hidden');
                else document.getElementById('landline-content').classList.remove('hidden');
            }
        },
        extensionLandline: {
            hidden: function (value) {
                if (value) document.getElementById('extensionLandline-content').classList.add('hidden');
                else document.getElementById('extensionLandline-content').classList.remove('hidden');
            }
        },
        mobilePhone: {
            hidden: function (value) {
                if (value) document.getElementById('mobilePhone-content').classList.add('hidden');
                else document.getElementById('mobilePhone-content').classList.remove('hidden');
            }
        },
        address: {
            hidden: function (value) {
                if (value) document.getElementById('address-content').classList.add('hidden');
                else document.getElementById('address-content').classList.remove('hidden');
            }
        },
        neighborhood: {
            hidden: function (value) {
                if (value) document.getElementById('neighborhood-content').classList.add('hidden');
                else document.getElementById('neighborhood-content').classList.remove('hidden');
            }
        },
        city: {
            hidden: function (value) {
                if (value) document.getElementById('city-content').classList.add('hidden');
                else document.getElementById('city-content').classList.remove('hidden');
            }
        },
        country: {
            hidden: function (value) {
                if (value) document.getElementById('country-content').classList.add('hidden');
                else document.getElementById('country-content').classList.remove('hidden');
            }
        }
    },
    clear: function () {
        this.setId(0);
        this.Address.setId(0);
        this.Address.setAddress('');
        this.Address.setNeighBorhood('');
        this.Address.City.setId(0);
        this.Address.City.setCity('');
        this.Address.City.setCountry('');
        this.MobilePhone.setId(0);
        this.MobilePhone.setNumber('');
        this.Landline.setId(0);
        this.Landline.setNumber('');
        this.Landline.setExt('');
    }
}

// FORM VALIDATION FEEDBACK ICONS
// =================================================================
var faIcon = {
    valid: 'fa fa-check-circle fa-lg text-success',
    invalid: 'fa fa-times-circle fa-lg',
    validating: 'fa fa-refresh'
};

function hiddenContentNavigation() {
    document.getElementById('mainnav-container').classList.add('hidden');
    document.getElementById('navbar-container').classList.add('hidden');
};

function swicthActiveLink(element) {
    if (activeLink) {
        oldActiveLink = activeLink;
        oldActiveLink.classList.remove(ACTIVE_LINK_CLASS);
    }
    activeLink = element;
    activeLink.classList.add(ACTIVE_LINK_CLASS);
};


function initApp() {
    var b = sessionStorage.getItem(SESSION_STORAGE.BUSINESS);
    if (b) {
        fillInitBusiness(JSON.parse(b))
    } else {
        Pace.track(function () {
            $.ajax({
                type: 'GET',
                url: '/init-app',
                success: function (response) {
                    try {
                        fillInitBusiness(JSON.parse(response))
                        sessionStorage.setItem(SESSION_STORAGE.BUSINESS, response);
                    } catch (ex) {
                        console.error(ex);
                        alert(response);
                    }
                },
                error: function (xhr) {
                    alert(xhr.statusText);
                },
                timeout: 60000
            });
        });
    }
}

function fillInitBusiness(b) {
    document.getElementById('user-name-nav').textContent = b.user.name;
    document.getElementById('login-name-nav').textContent = b.user.login.user;
    document.getElementById('business-name-nav').textContent = b.name;
}

