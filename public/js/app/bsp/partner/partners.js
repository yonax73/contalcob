window.onload = function () {
    init();
}

function init() {
    swicthActiveLink(document.getElementById('clients-nav'));
    Partner.setId(0);
    initApp();
    search();
}

function search() {
    Pace.track(function () {
        $.ajax({
            type: 'POST',
            url: '/search-partners',
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

function fillData(business) {
    if (business) {
        var n = business.partners ? business.partners.length : 0;
        if (n > 0) {
            var element = document.getElementById('with-data');
            for (var i = 0; i < n; i++) {
                var partner = business.partners[i];
                var location = partner.location;

                var data = {
                    size: 'col-lg-3 col-md-6 col-sm-12',
                    id: partner.id,
                    title: partner.name,
                    mobile: location.mobilePhone ? location.mobilePhone.number : '&nbsp;'
                   
                }
                cardView(element, data);
            };
        } else {
            document.getElementById('without-data').classList.remove('hidden');
        }
    }
}

function cardView(element, data) {
    var str = '<div class="' + data.size + '">' +
          '<div class="panel">' +
            '<div class="panel-heading">' +
              '<div class="panel-control">' +
                   '<a href="/partner" onclick="Partner.setId(' + data.id + ')" class="btn btn-default"><i class="fa fa-chevron-right"></i></a>' +
                '</div>' +
                '<h3 class="panel-title"><i class="fa fa-user"></i>&nbsp;&nbsp;' + data.title + '</h3>' +
              '</div>' +
                '<div class="panel-body">' +
                  '<ul class="fa-ul">' +                  
                     '<li><i class="fa-li fa fa-phone"></i>' + data.mobile + '</li>' +
                  '</ul>' +
                '</div>' +
            '</div>' +
          '</div>';
    element.innerHTML += str;
}