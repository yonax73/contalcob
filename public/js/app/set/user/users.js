window.onload = function () {
    init();
}

function init() {
    swicthActiveLink(document.getElementById('collector-nav'));
    User.setId(0);
    initApp();
    search();
}

function search() {
    Pace.track(function () {
        $.ajax({
            type: 'POST',
            url: '/search-users',
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
        var n = business.users ? business.users.length : 0;
        if (n > 0) {
            var element = document.getElementById('with-data');
            for (var i = 0; i < n; i++) {
                var user = business.users[i];
                var data = {
                    size: 'col-lg-3',
                    id: user.id,
                    title: user.login.user,
                    user: user.name,
                    mobile: user.location.mobilePhone.number
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
                   '<a href="/user" onclick="User.setId(' + data.id + ')" class="btn btn-default"><i class="fa fa-chevron-right"></i></a>' +
                '</div>' +
                '<h3 class="panel-title"><i class="fa fa-map-marker"></i>&nbsp;&nbsp;' + data.title + '</h3>' +
              '</div>' +
                '<div class="panel-body">' +
                  '<ul class="fa-ul">' +
                     '<li><i class="fa-li fa fa-user"></i>' + data.user + '</li>' +
                     '<li><i class="fa-li fa fa-phone"></i>' + data.mobile + '</li>' +
                  '</ul>' +
                '</div>' +
            '</div>' +
          '</div>';
    element.innerHTML += str;
}