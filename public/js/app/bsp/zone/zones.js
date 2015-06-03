window.onload = function () {
    init();
}

function init() {
    swicthActiveLink(document.getElementById('zone-nav'));
    Zone.setId(0);
    initApp();
    load();
}

function load() {
    Zone.Search.data = [];
    Zone.Search.userId(0);
    Zone.Search.excludeUserId(0);
    Pace.track(function () {
        $.ajax({
            type: 'POST',
            url: '/search-zones',
            data: Zone.Search.serialize(),
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
        var n = business.zones ? business.zones.length : 0;
        if (n > 0) {
            var element = document.getElementById('with-data');
            for (var i = 0; i < n; i++) {
                var zone = business.zones[i];
                var data = {
                    size: 'col-lg-3',
                    id: zone.id,
                    title: zone.name,
                    contentBody: zone.description
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
                  '<a href="/zone" onclick="Zone.setId(' + data.id + ')" class="btn btn-default"><i class="fa fa-chevron-right"></i></a>' +
                '</div>' +
                '<h3 class="panel-title"><i class="fa fa-map-marker"></i>&nbsp;&nbsp;' + data.title + '</h3>' +
              '</div>' +
                '<div class="panel-body">' +
                  '<p>' + data.contentBody + '</p>' +
                '</div>' +
            '</div>' +
          '</div>';
    element.innerHTML += str;
}

