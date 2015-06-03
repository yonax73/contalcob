window.onload = function () {
    init();
}

var datas = {};

function init() {
    swicthActiveLink(document.getElementById('clients-nav'));
    Location.Content.extensionLandline.hidden(true);
    Location.Content.city.hidden(true);
    Location.Content.country.hidden(true);
    initApp();
    initFormValidation();
    if (Partner.getId() > 0) {
        Partner.Toolbar.hidden(false);
        load();
    }
}

function load() {
    Pace.track(function () {
        $.ajax({
            type: 'GET',
            url: '/load-partner?id=' + Partner.getId(),
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

function fillData(partner) {
    var location = partner.location;
    //Partner
    Partner.title(partner.name);
    Partner.setFullName(partner.name);
    Partner.setId(partner.id);
    //location
    Location.setId(location.id);
    //ADDRESS
    if (location.address) {
        Location.Address.setId(location.address.id);
        Location.Address.setNeighBorhood(location.address.neighborhood);
        Location.Address.setAddress(location.address.address);
    }
    //mobile
    if (location.mobilePhone) {
        Location.MobilePhone.setNumber(location.mobilePhone.number);
        Location.MobilePhone.setId(location.mobilePhone.id);
    }
    //LANDLINE
    if (location.landline) {
        Location.Landline.setNumber(location.landline.number);
        Location.Landline.setId(location.landline.id);
    }
}

function initFormValidation() {
    // FORM  VALIDATION
    // =================================================================
    $('#partner-frm').bootstrapValidator({
        feedbackIcons: faIcon,
        fields: {
            partnerName: {
                validators: {
                    notEmpty: {
                        message: 'El nombre del cliente requerido.'
                    }
                }
            },
            address: {
                validators: {
                    notEmpty: {
                        message: 'La direcci&oacute;n es requerida.'
                    }
                }
            }
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
                        Partner.Toolbar.hidden(false);
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
    document.getElementById('save-partner-btn').disabled = value;
}

function newPartner() {
    Partner.clear();
    Partner.title('Crear Cliente');
    Partner.Toolbar.hidden(true);
    Location.clear();
}

function newLending() { }

