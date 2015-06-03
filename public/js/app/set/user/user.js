window.onload = function () {
    init();
}

var datas = {};

function init() {
    swicthActiveLink(document.getElementById('collector-nav'));
    Location.Content.landline.hidden(true);
    Location.Content.extensionLandline.hidden(true);
    Location.Content.address.hidden(true);
    Location.Content.neighborhood.hidden(true);
    Location.Content.city.hidden(true);
    Location.Content.country.hidden(true);
    initApp();
    initFormValidation();
    Login.setLoginTypeId(Login.COLLECTOR);
    if (User.getId() > 0) {
        Login.Content.password.hidden(true);
        Login.Content.confirmPassword.hidden(true);
        User.Toolbar.hidden(false);
        $('#user-frm')
             .bootstrapValidator('enableFieldValidators', 'password', false)
             .bootstrapValidator('enableFieldValidators', 'confirmPassword', false);
        load();
    }
}

function load() {
    Pace.track(function () {
        $.ajax({
            type: 'GET',
            url: '/load-user?id=' + User.getId(),
            success: function (response) {
                try {
                    fillData(JSON.parse(response));
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

function fillData(user) {
    var location = user.location;
    var login = user.login;
    //user
    User.title(user.name);
    User.setFullName(user.name);
    User.setId(user.id);
    //mobile
    Location.MobilePhone.setNumber(location.mobilePhone.number);
    Location.MobilePhone.setId(location.mobilePhone.id);
    //login
    Login.setId(login.id);
    Login.setUser(login.user);
    Login.setEmail(login.email);
}

function initFormValidation() {
    // FORM  VALIDATION
    // =================================================================
    $('#user-frm').bootstrapValidator({
        feedbackIcons: faIcon,
        fields: {
            userFullName: {
                validators: {
                    notEmpty: {
                        message: 'El nombre del recaudador requerido.'
                    }
                }
            },
            mobilePhone: {
                validators: {
                    notEmpty: {
                        message: 'El n&uacute;mero del celular es requerido'
                    }
                }
            },
            emailLogin: {
                validators: {
                    notEmpty: {
                        message: 'El correo el&eacute;ctronico es requerido'
                    },
                    emailAddress: {
                        message: 'Ingrese un correo el&eacute;ctronico valido'
                    }
                }
            },
            userLogin: {
                validators: {
                    notEmpty: {
                        message: 'El nombre de usuario es requerido'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'La contrase&ntilde;a es requerida'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: 'La contrase&ntilde;a y la confirmaci&oacute;n no son iguales'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: 'La confirmaci&oacute;n de la contrase&ntilde;a es requerida'
                    },
                    identical: {
                        field: 'password',
                        message: 'La contrase&ntilde;a y la confirmaci&oacute;n no son iguales'
                    }
                }
            },
        }
    }).on('success.field.bv', function (e, data) {
        var $parent = data.element.parents('.form-group');
        $parent.removeClass('has-success');
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        var $form = $(e.target);
        disabledControls(true);
        Pace.track(function () {
            $.ajax({
                url: $form.attr('action'),
                type: 'POST',
                data: $form.serialize(),
                success: function (response) {
                    disabledControls(false);
                    try {
                        fillData(JSON.parse(response));
                        $('#user-frm')
                             .bootstrapValidator('enableFieldValidators', 'password', false)
                             .bootstrapValidator('enableFieldValidators', 'confirmPassword', false);
                        Login.Content.password.hidden(true);
                        Login.Content.confirmPassword.hidden(true);
                        User.Toolbar.hidden(false);
                        Notification.success();
                    } catch (ex) {
                        console.log(ex);
                        disabledControls(false);
                        alert(response);
                    }
                },
                error: function (response) {
                    disabledControls(false);
                    alert(response.responseText);
                }
            });
        });
    });
}

function disabledControls(value) {
    document.getElementById('save-user-btn').disabled = value;
}

function newUser() {
    User.clear();
    User.title('Crear recaudador');
    User.Toolbar.hidden(true);
    Login.clear();
    Login.setLoginTypeId(Login.COLLECTOR);
    Location.clear();
    Login.Content.password.hidden(false);
    Login.Content.confirmPassword.hidden(false);
    $('#user-frm')
       .bootstrapValidator('enableFieldValidators', 'password', true)
       .bootstrapValidator('enableFieldValidators', 'confirmPassword', true);
}

function userZones() {
    datas = [];
    Zone.Search.data = [];
    Zone.Search.userId(User.getId());
    Zone.Search.excludeUserId(0)
    Pace.track(function () {
        $.ajax({
            type: 'POST',
            url: '/search-zones',
            data: Zone.Search.serialize(),
            success: function (response) {
                try {
                    fillUserZones(JSON.parse(response));
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

function fillUserZones(business) {
    if (business) {
        var n = business.zones ? business.zones.length : 0;
        if (n > 0) {
            var element = document.getElementById('user-zones');
            element.innerHTML = '';
            for (var i = 0; i < n; i++) {
                var zone = business.zones[i];
                var data = {
                    size: 'col-lg-3 col-md-4 col-sm-12',
                    cardId: 'user-zones-' + zone.id,
                    id: zone.id,
                    title: zone.name,
                    contentBody: zone.description
                }
                datas[data.id] = data;
                cardUserZones(element, data);
            };
        } else {
            userWithZones(false);
        }
    }
    Modal.Form.action().classList.add('hidden');
    Modal.Form.title('Zonas por recaudador');
    Modal.Form.show();
}

function zonesSearch() {
    Zone.Search.data = [];
    Zone.Search.userId(0);
    Zone.Search.excludeUserId(User.getId());
    Pace.track(function () {
        $.ajax({
            type: 'POST',
            url: '/search-zones',
            data: Zone.Search.serialize(),
            success: function (response) {
                try {
                    fillZonesSearch(JSON.parse(response));
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

function fillZonesSearch(business) {
    if (business) {
        var n = business.zones ? business.zones.length : 0;
        if (n > 0) {
            var element = document.getElementById('search-zone');
            element.innerHTML = '';
            for (var i = 0; i < n; i++) {
                var zone = business.zones[i];
                var data = {
                    size: 'col-lg-3 col-md-4 col-sm-12',
                    cardId: 'zones-search-' + zone.id,
                    id: zone.id,
                    title: zone.name,
                    contentBody: zone.description

                }
                datas[data.id] = data;
                cardBusinessZones(element, data);
            };
        } else {
            document.getElementById('business-without-zones').classList.remove('hidden');
        }
    }
}

function cardUserZones(element, data) {
    var str = '<div id="' + data.cardId + '" class="' + data.size + '">' +
    '<div class="panel">' +
      '<div class="panel-heading">' +
        '<div class="panel-control">' +
            '<a href="#" class="text-danger" onclick="toBusinessZone(' + data.id + ')" ><i class="fa fa-trash"></i></a>' +
          '</div>' +
          '<h3 class="panel-title"><i class="fa fa-map-marker"></i>&nbsp;&nbsp;' + data.title + '</h3>' +
        '</div>' +
      '</div>' +
    '</div>';
    element.innerHTML += str;
}

function cardBusinessZones(element, data) {
    var str = '<div id="' + data.cardId + '" class="' + data.size + '">' +
          '<div class="panel">' +
            '<div class="panel-heading">' +
              '<div class="panel-control">' +
                  '<a href="#" class="text-success" onclick="toUserZone(' + data.id + ')" ><i class="fa fa-plus-circle"></i></a>' +
                '</div>' +
                '<h3 class="panel-title"><i class="fa fa-map-marker"></i>&nbsp;&nbsp;' + data.title + '</h3>' +
              '</div>' +
            '</div>' +
          '</div>';
    element.innerHTML += str;
}

function toBusinessZone(key) {
    var data = datas[key];
    var element = document.getElementById('search-zone');
    Pace.track(function () {
        $.ajax({
            type: 'GET',
            url: '/remove-user-zone?userId=' + User.getId() + '&zoneId=' + data.id,
            success: function (response) {
                try {
                    if (response == RESPONSE_OK) {
                        $('#' + data.cardId).remove();
                        cardBusinessZones(element, data);
                    } else {
                        alert(response);
                    }
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

function toUserZone(key) {
    var data = datas[key];
    var element = document.getElementById('user-zones');
    Pace.track(function () {
        $.ajax({
            type: 'GET',
            url: '/add-user-zone?userId=' + User.getId() + '&zoneId=' + data.id,
            success: function (response) {
                try {
                    if (response == RESPONSE_OK) {
                        $('#' + data.cardId).remove();
                        cardUserZones(element, data);
                        userWithZones(true);
                    } else {
                        alert(response);
                    }
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

function userWithZones(value) {
    var ele = document.getElementById('user-without-zones');
    if (value) {
        if (!ele.classList.contains('hidden')) {
            ele.classList.add('hidden');
        }
    } else {
        if (ele.classList.contains('hidden')) {
            ele.classList.remove('hidden');
        }
    }
}