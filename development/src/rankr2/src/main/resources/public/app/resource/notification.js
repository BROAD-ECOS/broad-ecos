'use strict';

(function() {
    var app = angular.module('rankr');
    app.factory('Notification', ['$resource', function ($resource) {
        return $resource('/notifications/:id', {}, {
            query: {
                method: "GET", isArray: true
            },
            update: {method: "PUT"}

        });
    }]);
})();